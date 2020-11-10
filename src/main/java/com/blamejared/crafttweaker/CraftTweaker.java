package com.blamejared.crafttweaker;

import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.logger.*;
import com.blamejared.crafttweaker.impl.commands.*;
import com.blamejared.crafttweaker.impl.commands.custom.*;
import com.blamejared.crafttweaker.impl.events.*;
import com.blamejared.crafttweaker.impl.ingredients.*;
import com.blamejared.crafttweaker.impl.logger.*;
import com.blamejared.crafttweaker.impl.managers.*;
import com.blamejared.crafttweaker.impl.network.*;
import com.blamejared.crafttweaker.impl.recipes.*;
import com.blamejared.crafttweaker.impl.script.*;
import com.blamejared.crafttweaker.impl.tag.registry.*;
import net.minecraft.block.*;
import net.minecraft.client.resources.*;
import net.minecraft.item.crafting.*;
import net.minecraft.profiler.*;
import net.minecraft.resources.*;
import net.minecraft.server.*;
import net.minecraft.util.*;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.*;
import net.minecraftforge.common.crafting.*;
import net.minecraftforge.event.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.*;
import net.minecraftforge.fml.server.*;
import net.minecraftforge.registries.*;
import org.apache.logging.log4j.*;

import javax.annotation.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.stream.*;

@Mod(CraftTweaker.MODID)
public class CraftTweaker {
    
    public static final String MODID = "crafttweaker";
    public static final String NAME = "CraftTweaker";
    public static final String VERSION = "6.0.0";
    
    public static final Logger LOG = LogManager.getLogger(NAME);
    
    
    @SuppressWarnings("rawtypes")
    public static IRecipeSerializer SHAPELESS_SERIALIZER;
    @SuppressWarnings("rawtypes")
    public static IRecipeSerializer SHAPED_SERIALIZER;
    @SuppressWarnings("rawtypes")
    public static IRecipeSerializer SCRIPT_SERIALIZER;
    
    public static IIngredientSerializer<?> INGREDIENT_NBT_SERIALIZER;
    public static IRecipeType<ScriptRecipe> RECIPE_TYPE_SCRIPTS;
    private static Set<String> PATRON_LIST = new HashSet<>();
    
    public static final UUID CRAFTTWEAKER_UUID = UUID.nameUUIDFromBytes(MODID.getBytes());
    public static boolean serverOverride = true;
    
    public CraftTweaker() {
        if(!CraftTweakerAPI.SCRIPT_DIR.exists() && (!CraftTweakerAPI.SCRIPT_DIR.mkdirs() || !CraftTweakerAPI.SCRIPT_DIR.mkdir())) {
            final String path = CraftTweakerAPI.SCRIPT_DIR.getAbsolutePath();
            throw new IllegalStateException("Could not create Directory " + path);
        }
        CraftTweakerAPI.setupLoggers();
        CraftTweakerAPI.logger.setLogLevel(LogLevel.DEBUG);
        CraftTweakerRegistry.addAdvancedBEPName("recipemanager");
        CraftTweakerRegistry.findClasses();
        
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Block.class, EventPriority.HIGHEST, this::handleTags);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new CTEventHandler());
        PacketHandler.init();
        SHAPELESS_SERIALIZER = new SerializerShapeless().setRegistryName(new ResourceLocation("crafttweaker:shapeless"));
        SHAPED_SERIALIZER = new SerializerShaped().setRegistryName(new ResourceLocation("crafttweaker:shaped"));
        SCRIPT_SERIALIZER = new SerializerScript().setRegistryName(new ResourceLocation("crafttweaker:scripts"));
        
        
        ForgeRegistries.RECIPE_SERIALIZERS.register(SHAPELESS_SERIALIZER);
        ForgeRegistries.RECIPE_SERIALIZERS.register(SHAPED_SERIALIZER);
        ForgeRegistries.RECIPE_SERIALIZERS.register(SCRIPT_SERIALIZER);
        
        RECIPE_TYPE_SCRIPTS = IRecipeType.register(MODID + ":scripts");
        
        INGREDIENT_NBT_SERIALIZER = new IngredientNBT.Serializer();
        CraftingHelper.register(new ResourceLocation(MODID, "nbt"), INGREDIENT_NBT_SERIALIZER);
        CraftTweakerRegistries.init();
        
        new Thread(() -> {
            try {
                URL url = new URL("https://blamejared.com/patrons.txt");
                URLConnection urlConnection = url.openConnection();
                urlConnection.setConnectTimeout(15000);
                urlConnection.setReadTimeout(15000);
                urlConnection.setRequestProperty("User-Agent", "CraftTweaker|1.16.4");
                try(BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
                    PATRON_LIST = reader.lines().filter(s -> !s.isEmpty()).collect(Collectors.toSet());
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    
    /**
     * By this time, forge tags have been registered, so we can use this.
     * Subscribed to at highest priority to allow other mods to call CrT methods that use tags from within that event.
     */
    private void handleTags(RegistryEvent<Block> ignored) {
        CrTTagRegistryData.INSTANCE.registerForgeTags();
    }
    
    private void setup(final FMLCommonSetupEvent event) {
        final ScriptLoadingOptions setupCommon = new ScriptLoadingOptions().setLoaderName("setupCommon")
                .firstRun()
                .execute();
        CraftTweakerAPI.loadScripts(setupCommon);
        
        LOG.info("{} has loaded successfully!", NAME);
    }
    
    private void setupClient(final FMLClientSetupEvent event) {
        LOG.info("{} client has loaded successfully!", NAME);
        MinecraftForge.EVENT_BUS.register(new CTClientEventHandler());
    }
    
    
    @SubscribeEvent
    public void playerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        /*
        called on singleplayer join on the client
        Called on multiplayer login on the server
         */
        
        ((GroupLogger) CraftTweakerAPI.logger).addLogger(new PlayerLogger(event.getPlayer()));
    }
    
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void getRecipes(RecipesUpdatedEvent event) {
        /*
         * Called on the client when joining a world and the server.
         *
         * Recipes are only written and read on the server, we should not load scripts on the client if it is a single player world.
         *
         * Use a Recipe serializer to serialize the scripts from the server and run those scripts here.
         *
         *
         * In the recipe serializer we should set a boolean, and only load the scripts on the client if the boolean is true.
         */
        if(event.getRecipeManager().recipes.getOrDefault(CraftTweaker.RECIPE_TYPE_SCRIPTS, new HashMap<>()).size() == 0) {
            // probably joining single player, but possible the server doesn't have any recipes as well, either way, don't reload scripts!
            return;
        }
        CTClientEventHandler.TOOLTIPS.clear();
        serverOverride = false;
        CTCraftingTableManager.recipeManager = event.getRecipeManager();
        final ScriptLoadingOptions scriptLoadingOptions = new ScriptLoadingOptions().execute();
        CraftTweakerAPI.loadScriptsFromRecipeManager(event.getRecipeManager(), scriptLoadingOptions);
    }
    
    
    @SubscribeEvent
    public void registerCommands(RegisterCommandsEvent event) {
        CTCommands.init(event.getDispatcher());
        CustomCommands.init(event.getDispatcher());
    }
    
    @SubscribeEvent(priority = EventPriority.LOW)
    public void resourceReload(AddReloadListenerEvent event) {
        
        event.addListener(new ReloadListener<Void>() {
            @Override
            @Nonnull
            @ParametersAreNonnullByDefault
            protected Void prepare(IResourceManager resourceManagerIn, IProfiler profilerIn) {
                MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
                serverOverride = server == null;
                return null;
            }
            
            @Override
            @ParametersAreNonnullByDefault
            protected void apply(Void objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn) {
                giveFeedback(new StringTextComponent("CraftTweaker reload starting!"));
                //ImmutableMap of ImmutableMaps. Nice.
                RecipeManager recipeManager = event.getDataPackRegistries().getRecipeManager();
                recipeManager.recipes = new HashMap<>(recipeManager.recipes);
                recipeManager.recipes.replaceAll((t, v) -> new HashMap<>(recipeManager.recipes.get(t)));
                CTCraftingTableManager.recipeManager = recipeManager;
                CraftTweakerAPI.loadScripts(new ScriptLoadingOptions().execute());
                List<File> scriptFiles = CraftTweakerAPI.getScriptFiles();
                scriptFiles.stream().map(file -> new ScriptRecipe(new ResourceLocation(MODID, file.getPath().substring("scripts\\".length()).replaceAll("[^a-z0-9_.-]", "_")), file.getPath().substring("scripts\\".length()), readContents(file))).forEach(scriptRecipe -> {
                    Map<ResourceLocation, IRecipe<?>> map = recipeManager.recipes.computeIfAbsent(RECIPE_TYPE_SCRIPTS, iRecipeType -> new HashMap<>());
                    map.put(scriptRecipe.getId(), scriptRecipe);
                });
                
                TextComponent msg = new StringTextComponent("CraftTweaker reload complete!");
                giveFeedback(msg);
                if(scriptFiles.size() > 0 && !CraftTweakerAPI.NO_BRAND) {
                    String name = PATRON_LIST.stream().skip(PATRON_LIST.isEmpty() ? 0 : new Random().nextInt(PATRON_LIST.size())).findFirst().orElse("");
                    if(!name.isEmpty()) {
                        msg = new StringTextComponent("This reload was made possible by " + name + " and more!" + TextFormatting.GREEN + " [Learn more!]");
                        msg.setStyle(Style.EMPTY.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://patreon.com/jaredlll08?s=crtmod")).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new StringTextComponent(TextFormatting.GREEN + "Click to learn more!"))));
                        giveFeedback(msg);
                    }
                }
            }
        });
        
    }
    
    private static void giveFeedback(ITextComponent msg) {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if(server != null) {
            server.getPlayerList().func_232641_a_(msg, ChatType.SYSTEM, CRAFTTWEAKER_UUID);
        } else {
            System.out.println(msg.getString());
        }
    }
    
    
    public String readContents(File file) {
        try(final BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            return bufferedReader.lines().collect(Collectors.joining("\r\n"));
        } catch(IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    
}
