package com.blamejared.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerRegistry;
import com.blamejared.crafttweaker.api.zencode.impl.FileAccessSingle;
import com.blamejared.crafttweaker.impl.commands.CTCommands;
import com.blamejared.crafttweaker.impl.events.CTEventHandler;
import com.blamejared.crafttweaker.impl.ingredients.IngredientNBT;
import com.blamejared.crafttweaker.impl.logger.GroupLogger;
import com.blamejared.crafttweaker.impl.logger.PlayerLogger;
import com.blamejared.crafttweaker.impl.managers.CTRecipeManager;
import com.blamejared.crafttweaker.impl.network.PacketHandler;
import com.blamejared.crafttweaker.impl.recipes.SerializerShaped;
import com.blamejared.crafttweaker.impl.recipes.SerializerShapeless;
import com.blamejared.crafttweaker.impl.script.ScriptRecipe;
import com.blamejared.crafttweaker.impl.script.SerializerScript;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openzen.zencode.shared.SourceFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mod(CraftTweaker.MODID)
public class CraftTweaker {
    
    public static final String MODID = "crafttweaker";
    public static final String NAME = "CraftTweaker";
    public static final String VERSION = "5.0.0";
    
    public static final Logger LOG = LogManager.getLogger(NAME);
    
    public static IRecipeSerializer SHAPELESS_SERIALIZER;
    public static IRecipeSerializer SHAPED_SERIALIZER;
    public static IRecipeSerializer SCRIPT_SERIALIZER;
    
    public static IIngredientSerializer INGREDIENT_NBT_SERIALIZER;
    public static IRecipeType<ScriptRecipe> RECIPE_TYPE_SCRIPTS;
    
    public CraftTweaker() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new CTEventHandler());
        PacketHandler.init();
        SHAPELESS_SERIALIZER = new SerializerShapeless().setRegistryName(new ResourceLocation("crafttweaker:shapeless"));
        SHAPED_SERIALIZER = new SerializerShaped().setRegistryName(new ResourceLocation("crafttweaker:shaped"));
        SCRIPT_SERIALIZER = new SerializerScript().setRegistryName(new ResourceLocation("crafttweaker:scripts"));
        
        
        ForgeRegistries.RECIPE_SERIALIZERS.register(SHAPELESS_SERIALIZER);
        ForgeRegistries.RECIPE_SERIALIZERS.register(SHAPED_SERIALIZER);
        ForgeRegistries.RECIPE_SERIALIZERS.register(SCRIPT_SERIALIZER);
        
        RECIPE_TYPE_SCRIPTS = Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(MODID, "scripts"), new IRecipeType<ScriptRecipe>() {
            @Override
            public String toString() {
                return "scripts";
            }
        });
        INGREDIENT_NBT_SERIALIZER = new IngredientNBT.Serializer();
        CraftingHelper.register(new ResourceLocation(MODID, "nbt"), INGREDIENT_NBT_SERIALIZER);
    }
    
    private void setup(final FMLCommonSetupEvent event) {
        LOG.info("{} has loaded successfully!", NAME);
        CraftTweakerAPI.setupLoggers();
        CraftTweakerAPI.SCRIPT_DIR.mkdirs();
        CraftTweakerAPI.SCRIPT_DIR.mkdir();
        CraftTweakerRegistry.findClasses();
    }
    
    private void setupClient(final FMLClientSetupEvent event) {
        LOG.info("{} client has loaded successfully!", NAME);
    }
    
    
    @SubscribeEvent
    public void playerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        /*
        called on singleplayer join on the client
        Called on multiplayer login on the server
         */
        
        List<String> msgs = new ArrayList<>();
        msgs.add("Thank you for participating in the CraftTweaker Open beta!");
        msgs.add("Things to note: ");
        msgs.add("Quite literally everything has been rewritten, from Forge, the mod, even the ZenScript engine! So things may not work!");
        msgs.add("This is " + TextFormatting.RED + "NOT" + TextFormatting.RESET + " modpack ready! This is a release to help find issues and allow modpack developers to start work on their packs!");
        msgs.add("Script reloading is back!");
        msgs.add("The command is now bundled in /reload!");
        
        msgs.stream().map(StringTextComponent::new).forEach(event.getPlayer()::sendMessage);
        StringTextComponent github = new StringTextComponent("If you find a bug, please report it on the " + TextFormatting.AQUA + "Issue Tracker!" + TextFormatting.RESET);
        github.applyTextStyle(style -> style.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/CraftTweaker/CraftTweaker/issues")));
        github.applyTextStyle(style -> style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new StringTextComponent("Click to go to the issue tracker!"))));
        event.getPlayer().sendMessage(github);
        
        StringTextComponent discord = new StringTextComponent("If you need any help, join the " + TextFormatting.AQUA + "Discord!" + TextFormatting.RESET);
        discord.applyTextStyle(style -> style.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.blamejared.com")));
        discord.applyTextStyle(style -> style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new StringTextComponent("Click to join the discord!"))));
        event.getPlayer().sendMessage(discord);
        
        event.getPlayer().sendMessage(new StringTextComponent("This message will show every time a world is joined. There is no way to remove it, as I said, this is " + TextFormatting.RED + "NOT" + TextFormatting.RESET + " modpack ready!"));
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
        CTRecipeManager.recipeManager = event.getRecipeManager();
        Map<ResourceLocation, IRecipe<?>> map = event.getRecipeManager().recipes.getOrDefault(CraftTweaker.RECIPE_TYPE_SCRIPTS, new HashMap<>());
        Collection<IRecipe<?>> recipes = map.values();
        final Comparator<FileAccessSingle> comparator = FileAccessSingle.createComparator(CraftTweakerRegistry.getPreprocessors());
        SourceFile[] sourceFiles = recipes.stream().map(iRecipe -> (ScriptRecipe) iRecipe).map(recipe -> new FileAccessSingle(recipe.getFileName(), new StringReader(recipe.getContent()), CraftTweakerRegistry.getPreprocessors())).filter(FileAccessSingle::shouldBeLoaded).sorted(comparator).map(FileAccessSingle::getSourceFile).toArray(SourceFile[]::new);
        CraftTweakerAPI.loadScripts(sourceFiles);
    }
    
    @SubscribeEvent
    public void serverStarting(FMLServerStartingEvent event) {
        CTCommands.init(event.getCommandDispatcher());
    }
    
    @SubscribeEvent
    public void startServer(FMLServerAboutToStartEvent event) {
        IReloadableResourceManager manager = event.getServer().getResourceManager();
        manager.addReloadListener((IResourceManagerReloadListener) resourceManager -> {
            //ImmutableMap of ImmutableMaps. Nice.
            RecipeManager recipeManager = event.getServer().getRecipeManager();
            recipeManager.recipes = new HashMap<>(recipeManager.recipes);
            for(IRecipeType<?> type : recipeManager.recipes.keySet()) {
                recipeManager.recipes.put(type, new HashMap<>(recipeManager.recipes.get(type)));
            }
            CTRecipeManager.recipeManager = recipeManager;
            CraftTweakerAPI.loadScripts();
            DistExecutor.runWhenOn(Dist.DEDICATED_SERVER, () -> () -> {
                List<File> scriptFiles = CraftTweakerAPI.getScriptFiles();
                scriptFiles.stream().map(file -> new ScriptRecipe(new ResourceLocation(MODID, file.getPath().substring("scripts\\".length()).replaceAll("[^a-z0-9_.-]", "_")), file.getPath().substring("scripts\\".length()), readContents(file))).forEach(scriptRecipe -> {
                    Map<ResourceLocation, IRecipe<?>> map = recipeManager.recipes.computeIfAbsent(RECIPE_TYPE_SCRIPTS, iRecipeType -> new HashMap<>());
                    map.put(scriptRecipe.getId(), scriptRecipe);
                });
            });
            
            event.getServer().getPlayerList().sendMessage(new StringTextComponent("CraftTweaker reload complete!"));
        });
    }
    
    public String readContents(File file) {
        try {
            return new BufferedReader(new FileReader(file)).lines().collect(Collectors.joining("\r\n"));
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
    
}
