package com.blamejared.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerRegistry;
import com.blamejared.crafttweaker.api.ScriptLoadingOptions;
import com.blamejared.crafttweaker.api.logger.LogLevel;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.zencode.brackets.CTRegisterBEPEvent;
import com.blamejared.crafttweaker.impl.brackets.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.impl.brackets.tags.TagBracketHandler;
import com.blamejared.crafttweaker.impl.brackets.tags.TagManagerBracketHandler;
import com.blamejared.crafttweaker.impl.commands.CTCommands;
import com.blamejared.crafttweaker.impl.commands.custom.CustomCommands;
import com.blamejared.crafttweaker.impl.events.CTClientEventHandler;
import com.blamejared.crafttweaker.impl.events.CTEventHandler;
import com.blamejared.crafttweaker.impl.logger.GroupLogger;
import com.blamejared.crafttweaker.impl.loot.conditions.LootConditionManager;
import com.blamejared.crafttweaker.impl.managers.CTCraftingTableManager;
import com.blamejared.crafttweaker.impl.network.PacketHandler;
import com.blamejared.crafttweaker.impl.script.ScriptRecipe;
import com.blamejared.crafttweaker.impl.tag.registry.CrTTagRegistryData;
import net.minecraft.block.Block;
import net.minecraft.client.resources.ReloadListener;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mod(CraftTweaker.MODID)
public class CraftTweaker {
    
    public static final String MODID = "crafttweaker";
    public static final String NAME = "CraftTweaker";
    public static final String VERSION = "6.0.0";
    
    public static final Logger LOG = LogManager.getLogger(NAME);
    public static final UUID CRAFTTWEAKER_UUID = UUID.nameUUIDFromBytes(MODID.getBytes());
    public static boolean serverOverride = true;
    private static Set<String> PATRON_LIST = new HashSet<>();
    // TODO - BREAKING (potentially): Move this to it's own class somewhere in the API
    public static IRecipeType<ScriptRecipe> RECIPE_TYPE_SCRIPTS;
    
    public CraftTweaker() {
        
        if(!CraftTweakerAPI.SCRIPT_DIR.exists() && !CraftTweakerAPI.SCRIPT_DIR.mkdirs() && !CraftTweakerAPI.SCRIPT_DIR.mkdir()) {
            final String path = CraftTweakerAPI.SCRIPT_DIR.getAbsolutePath();
            throw new IllegalStateException("Could not create Directory " + path);
        }
        CraftTweakerAPI.setupLoggers();
        CraftTweakerAPI.logger.setLogLevel(LogLevel.INFO);
        CraftTweakerAPI.logInfo("Starting building internal Registries");
        CraftTweakerRegistry.addAdvancedBEPName("recipemanager");
        CraftTweakerRegistry.findClasses();
        LootConditionManager.handleBuilderRegistration();
        CraftTweakerAPI.logInfo("Completed building internal Registries");
        
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
        FMLJavaModLoadingContext.get()
                .getModEventBus()
                .addGenericListener(Block.class, EventPriority.HIGHEST, this::handleTags);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new CTEventHandler());
        PacketHandler.init();
        
        RECIPE_TYPE_SCRIPTS = IRecipeType.register(MODID + ":scripts");
        
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
    
    private static void giveFeedback(ITextComponent msg) {
        
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if(server != null) {
            server.getPlayerList().func_232641_a_(msg, ChatType.SYSTEM, CRAFTTWEAKER_UUID);
        } else {
            System.out.println(msg.getString());
        }
    }
    
    /**
     * By this time, forge tags have been registered, so we can use this.
     * Subscribed to at highest priority to allow other mods to call CrT methods that use tags from within that event.
     */
    private void handleTags(RegistryEvent<Block> ignored) {
        
        CraftTweakerAPI.logDebug("Setting up Tag Managers");
        CrTTagRegistryData.INSTANCE.registerForgeTags();
        CraftTweakerAPI.logDebug("Finished setting up Tag Managers");
    }
    
    private void setup(final FMLCommonSetupEvent event) {
        
        final ScriptLoadingOptions setupCommon = new ScriptLoadingOptions().setLoaderName("setupCommon").execute();
        CraftTweakerAPI.loadScripts(setupCommon);
    
        CTCommands.initArgumentTypes();
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
        
        ((GroupLogger) CraftTweakerAPI.logger).addPlayerLogger(event.getPlayer());
    }
    
    @SubscribeEvent
    public void playerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        
        ((GroupLogger) CraftTweakerAPI.logger).removePlayerLogger(event.getPlayer());
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
        if(event.getRecipeManager().recipes.getOrDefault(CraftTweaker.RECIPE_TYPE_SCRIPTS, new HashMap<>())
                .size() == 0) {
            // probably joining single player, but possible the server doesn't have any recipes as well, either way, don't reload scripts!
            return;
        }
        //ImmutableMap of ImmutableMaps. Nice.
        RecipeManager recipeManager = event.getRecipeManager();
        recipeManager.recipes = new HashMap<>(recipeManager.recipes);
        recipeManager.recipes.replaceAll((t, v) -> new HashMap<>(recipeManager.recipes.get(t)));
        
        CTClientEventHandler.TOOLTIPS.clear();
        serverOverride = false;
        CTCraftingTableManager.recipeManager = event.getRecipeManager();
        final ScriptLoadingOptions scriptLoadingOptions = new ScriptLoadingOptions().execute();
        CraftTweakerAPI.loadScriptsFromRecipeManager(event.getRecipeManager(), scriptLoadingOptions);
    }
    
    @SubscribeEvent
    public void registerBracketExpressionParsers(CTRegisterBEPEvent event) {
        
        final List<Class<? extends IRecipeManager>> recipeManagers = CraftTweakerRegistry.getRecipeManagers();
        event.registerBEP("recipetype", new RecipeTypeBracketHandler(recipeManagers));
        
        final TagManagerBracketHandler tagManagerBEP = new TagManagerBracketHandler(CrTTagRegistryData.INSTANCE);
        event.registerBEP("tagManager", tagManagerBEP);
        event.registerBEP("tag", new TagBracketHandler(tagManagerBEP));
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
                scriptFiles.stream()
                        .map(file -> new ScriptRecipe(new ResourceLocation(MODID, file.getPath()
                                .substring("scripts\\".length())
                                .replaceAll("[^a-z0-9_.-]", "_")), file.getPath()
                                .substring("scripts\\".length()), readContents(file)))
                        .forEach(scriptRecipe -> {
                            Map<ResourceLocation, IRecipe<?>> map = recipeManager.recipes.computeIfAbsent(RECIPE_TYPE_SCRIPTS, iRecipeType -> new HashMap<>());
                            map.put(scriptRecipe.getId(), scriptRecipe);
                        });
                
                TextComponent msg = new StringTextComponent("CraftTweaker reload complete!");
                giveFeedback(msg);
                if(scriptFiles.size() > 0 && !CraftTweakerAPI.NO_BRAND) {
                    String name = PATRON_LIST.stream()
                            .skip(PATRON_LIST.isEmpty() ? 0 : new Random().nextInt(PATRON_LIST.size()))
                            .findFirst()
                            .orElse("");
                    if(!name.isEmpty()) {
                        CraftTweakerAPI.logInfo("This reload was made possible by " + name + " and more! Become a patron at https://patreon.com/jaredlll08?s=crtmod");
                    }
                }
                
            }
        });
        
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
