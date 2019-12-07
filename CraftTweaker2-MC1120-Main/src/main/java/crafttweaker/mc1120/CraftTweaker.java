package crafttweaker.mc1120;

import crafttweaker.*;
import crafttweaker.annotations.*;
import crafttweaker.api.network.NetworkSide;
import crafttweaker.mc1120.brewing.MCBrewing;
import crafttweaker.mc1120.client.MCClient;
import crafttweaker.mc1120.commands.CTChatCommand;
import crafttweaker.mc1120.events.ActionApplyEvent;
import crafttweaker.mc1120.formatting.MCFormatter;
import crafttweaker.mc1120.furnace.MCFurnaceManager;
import crafttweaker.mc1120.game.MCGame;
import crafttweaker.mc1120.item.MCItemUtils;
import crafttweaker.mc1120.logger.MCLogger;
import crafttweaker.mc1120.mods.MCLoadedMods;
import crafttweaker.mc1120.network.*;
import crafttweaker.mc1120.oredict.MCOreDict;
import crafttweaker.mc1120.preprocessors.*;
import crafttweaker.mc1120.proxies.CommonProxy;
import crafttweaker.mc1120.recipes.MCRecipeManager;
import crafttweaker.mc1120.server.MCServer;
import crafttweaker.mc1120.util.CraftTweakerPlatformUtils;
import crafttweaker.mc1120.vanilla.MCVanilla;
import crafttweaker.runtime.IScriptProvider;
import crafttweaker.runtime.providers.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.*;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.*;

import java.io.File;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main mod class. Performs some general logic, initialization of the API and
 * FML event handling.
 */
@Mod(modid = CraftTweaker.MODID, version = "4.1.19", name = CraftTweaker.NAME, acceptedMinecraftVersions = "[1.12, 1.13)")
public class CraftTweaker {
    
    public static final String MODID = "crafttweaker";
    public static final String NAME = "Crafttweaker";
    public static final Logger LOG = LogManager.getLogger(NAME);
    public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
    
    public static MinecraftServer server;
    @Mod.Instance(MODID)
    public static CraftTweaker INSTANCE;
    
    @SidedProxy(clientSide = "crafttweaker.mc1120.proxies.ClientProxy", serverSide = "crafttweaker.mc1120.proxies.CommonProxy")
    public static CommonProxy PROXY;
    
    
    public static List<IAction> LATE_ACTIONS = new LinkedList<>();
    static {
        int ID = 0;
        NETWORK.registerMessage(MessageOpenBrowser.class, MessageOpenBrowser.class, ID++, Side.CLIENT);
        NETWORK.registerMessage(MessageCopyClipboard.class, MessageCopyClipboard.class, ID, Side.CLIENT);
    }
    
    public MCRecipeManager recipes;
    private IScriptProvider scriptsGlobal;
    
    
    @EventHandler
    public void onConstruction(FMLConstructionEvent event) {
        CrafttweakerImplementationAPI.init(new MCOreDict(), recipes = new MCRecipeManager(), new MCFurnaceManager(), MCGame.INSTANCE, new MCLoadedMods(), new MCFormatter(), new MCVanilla(), new MCItemUtils(), new MCBrewing());
        CrafttweakerImplementationAPI.logger.addLogger(new MCLogger(new File("crafttweaker.log")));
        CrafttweakerImplementationAPI.platform = MCPlatformFunctions.INSTANCE;
        
        File globalDir = new File("scripts");
        if(!globalDir.exists())
            globalDir.mkdirs();
        
        scriptsGlobal = new ScriptProviderDirectory(globalDir);
        CrafttweakerImplementationAPI.setScriptProvider(scriptsGlobal);
        
        if(event.getSide().isServer()) {
            CraftTweakerAPI.tweaker.setNetworkSide(NetworkSide.SIDE_SERVER);
        } else {
            CraftTweakerAPI.tweaker.setNetworkSide(NetworkSide.SIDE_CLIENT);
        }
        
        // register the modloaded preprocessor which can't be in the API package as it needs access to MC
        CraftTweakerAPI.tweaker.getPreprocessorManager().registerPreprocessorAction("modloaded", ModLoadedPreprocessor::new);
        CraftTweakerAPI.tweaker.getPreprocessorManager().registerPreprocessorAction("zslint", ZsLintPreprocessor::new);
    }
    
    @EventHandler
    public void onPreInitialization(FMLPreInitializationEvent ev) {
        PROXY.registerEvents();
        ev.getAsmData().getAll(ZenRegister.class.getCanonicalName()).forEach(clazz -> {
            try {
                Class claz = Class.forName(clazz.getClassName(), false, CraftTweaker.class.getClassLoader());
                if(claz.isAnnotationPresent(ModOnly.class)) {
                    if(Loader.isModLoaded(((ModOnly) claz.getAnnotation(ModOnly.class)).value())) {
                        CraftTweakerAPI.registerClass(claz);
                    }
                } else {
                    CraftTweakerAPI.registerClass(claz);
                }
                
            } catch(ClassNotFoundException e) {
                CraftTweaker.LOG.catching(e);
            }
        });
        if(CraftTweakerPlatformUtils.isClient()) {
            CraftTweakerAPI.client = new MCClient();
        }
        
        IScriptProvider cascaded = new ScriptProviderCascade(scriptsGlobal);
        CrafttweakerImplementationAPI.setScriptProvider(cascaded);
        
        CraftTweakerAPI.tweaker.getOrCreateLoader("preinit").setMainName("preinit");
        CraftTweakerAPI.tweaker.loadScript(false, "preinit");
        
    }
    
    @EventHandler
    public void onPostInit(FMLPostInitializationEvent ev) {
        MinecraftForge.EVENT_BUS.post(new ActionApplyEvent.Pre());
        try {
            MCRecipeManager.recipes = ForgeRegistries.RECIPES.getEntries();
            CraftTweakerAPI.apply(MCRecipeManager.actionRemoveRecipesNoIngredients);
            MCRecipeManager.recipesToRemove.forEach(CraftTweakerAPI::apply);
            MCRecipeManager.recipesToAdd.forEach(CraftTweakerAPI::apply);
            MCFurnaceManager.recipesToRemove.forEach(CraftTweakerAPI::apply);
            MCFurnaceManager.recipesToAdd.forEach(CraftTweakerAPI::apply);
            LATE_ACTIONS.forEach(CraftTweakerAPI::apply);
            MCRecipeManager.recipes = ForgeRegistries.RECIPES.getEntries();
            
            //Cleanup
            MCRecipeManager.cleanUpRecipeList();
        } catch(Exception e) {
            CraftTweaker.LOG.catching(e);
            CraftTweakerAPI.logError("Error while applying actions", e);
        }
        MinecraftForge.EVENT_BUS.post(new ActionApplyEvent.Post());
    }
    
    private static boolean alreadyChangedThePlayer = false;
    
    @EventHandler
    @SideOnly(Side.CLIENT)
    public void onFMLLoadComplete(FMLLoadCompleteEvent event) {
        
        final Minecraft minecraft = Minecraft.getMinecraft();
        if(!alreadyChangedThePlayer) {
            alreadyChangedThePlayer = true;
            RecipeBookClient.rebuildTable();
            if(ENABLE_SEARCH_TREE_RECALCULATION) {
                minecraft.populateSearchTreeManager();
                ((SearchTree) minecraft.getSearchTreeManager().get(SearchTreeManager.ITEMS)).recalculate();
                ((SearchTree) minecraft.getSearchTreeManager().get(SearchTreeManager.RECIPES)).recalculate();
            }
            CraftTweakerAPI.logInfo("Fixed the RecipeBook");
        }
    }
    
    @EventHandler
    public void onInit(FMLInitializationEvent ev) {
        MCBrewing.fixBrewingRecipes();
        PROXY.registerReloadListener();
    }
    
    
    @EventHandler
    public void onServerAboutToStart(FMLServerAboutToStartEvent ev) {
        server = ev.getServer();
    }
    
    @EventHandler
    public void onServerStarting(FMLServerStartingEvent ev) {
        server = ev.getServer();
        if(CraftTweakerPlatformUtils.isClient()) {
            CraftTweakerAPI.client = new MCClient();
        }
        IScriptProvider cascaded = new ScriptProviderCascade(scriptsGlobal);
        CrafttweakerImplementationAPI.setScriptProvider(cascaded);
        CrafttweakerImplementationAPI.onServerStart(new MCServer(ev.getServer()));
        
        // registering the CraftTweaker command
        CTChatCommand.register(ev);
    }
    
    @EventHandler
    public void onServerStopped(FMLServerStoppedEvent ev) {
        CrafttweakerImplementationAPI.onServerStop();
        CrafttweakerImplementationAPI.setScriptProvider(scriptsGlobal);
        server = null;
    }
}
