/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc18;

import minetweaker.MineTweakerAPI;
import minetweaker.MineTweakerImplementationAPI;
import minetweaker.api.logger.FileLogger;
import minetweaker.mc18.brackets.ItemBracketHandler;
import minetweaker.mc18.brackets.LiquidBracketHandler;
import minetweaker.mc18.brackets.OreBracketHandler;
import minetweaker.mc18.client.MCClient;
import minetweaker.mc18.formatting.MCFormatter;
import minetweaker.mc18.furnace.FuelTweaker;
import minetweaker.mc18.furnace.MCFurnaceManager;
import minetweaker.mc18.game.MCGame;
import minetweaker.mc18.mods.MCLoadedMods;
import minetweaker.mc18.network.*;
import minetweaker.mc18.oredict.MCOreDict;
import minetweaker.mc18.recipes.MCRecipeManager;
import minetweaker.mc18.server.MCServer;
import minetweaker.mc18.util.MineTweakerHacks;
import minetweaker.mc18.util.MineTweakerPlatformUtils;
import minetweaker.mc18.vanilla.MCVanilla;
import minetweaker.runtime.GlobalRegistry;
import minetweaker.runtime.IScriptProvider;
import minetweaker.runtime.providers.ScriptProviderCascade;
import minetweaker.runtime.providers.ScriptProviderCustom;
import minetweaker.runtime.providers.ScriptProviderDirectory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import java.io.File;

/**
 * Main mod class. Performs some general logic, initialization of the API and
 * FML event handling.
 *
 * @author Stan Hebben
 */
@Mod(modid = MineTweakerMod.MODID, version = "3.0.11")
public class MineTweakerMod {
    public static final String MODID = "MineTweaker3";
    public static final String MCVERSION = "1.8.9";

    public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

    private static final String[] REGISTRIES = {"minetweaker.mods.jei.ClassRegistry"};
    private static final String[] REGISTRY_DESCRIPTIONS = {"JEI mod support"};
    public static MinecraftServer server;
    @Mod.Instance(MODID)
    public static MineTweakerMod INSTANCE;

    static {
        NETWORK.registerMessage(MineTweakerLoadScriptsHandler.class, MineTweakerLoadScriptsPacket.class, 0, Side.CLIENT);
        NETWORK.registerMessage(MineTweakerOpenBrowserHandler.class, MineTweakerOpenBrowserPacket.class, 1, Side.CLIENT);
        NETWORK.registerMessage(MineTweakerCopyClipboardHandler.class, MineTweakerCopyClipboardPacket.class, 2, Side.CLIENT);
    }

    public final MCRecipeManager recipes;
    private final IScriptProvider scriptsGlobal;
    private final ScriptProviderCustom scriptsIMC;

    public MineTweakerMod() {
        MCOreDict ore = new MCOreDict();
        recipes = new MCRecipeManager();
        MCFurnaceManager furnace = new MCFurnaceManager();
        MCGame game = new MCGame();
        MCLoadedMods mods = new MCLoadedMods();
        MCFormatter formatter = new MCFormatter();
        MCVanilla vanilla = new MCVanilla();
        MineTweakerImplementationAPI.init(
                ore,
                recipes,
                furnace,
                game,
                mods,
                formatter,
                vanilla);
        MineTweakerImplementationAPI.logger.addLogger(new FileLogger(new File("minetweaker.log")));
        MineTweakerImplementationAPI.platform = MCPlatformFunctions.INSTANCE;

        File globalDir = new File("scripts");
        if (!globalDir.exists())
            globalDir.mkdirs();

        scriptsIMC = new ScriptProviderCustom("intermod");
        scriptsGlobal = new ScriptProviderDirectory(globalDir);
        MineTweakerImplementationAPI.setScriptProvider(scriptsGlobal);
    }

    // ##########################
    // ### FML Event Handlers ###
    // ##########################

    @EventHandler
    public void onIMCEvent(FMLInterModComms.IMCEvent event) {
        for (final FMLInterModComms.IMCMessage imcMessage : event.getMessages()) {
            if (imcMessage.key.equalsIgnoreCase("addMineTweakerScript")) {
                if (imcMessage.isStringMessage()) {
                    scriptsIMC.add(imcMessage.getSender() + "::imc", imcMessage.getStringValue());
                } else if (imcMessage.isNBTMessage()) {
                    NBTTagCompound message = imcMessage.getNBTValue();
                    scriptsIMC.add(imcMessage.getSender() + "::" + message.getString("name"), message.getString("content"));
                }
            }
        }
    }

    @EventHandler
    public void onLoad(FMLPreInitializationEvent ev) {
        MinecraftForge.EVENT_BUS.register(new ForgeEventHandler());
        FMLCommonHandler.instance().bus().register(new FMLEventHandler());
    }

    @EventHandler
    public void onPostInit(FMLPostInitializationEvent ev) {
        MineTweakerAPI.registerClassRegistry(MineTweakerRegistry.class);

        for (int i = 0; i < REGISTRIES.length; i++) {
            MineTweakerAPI.registerClassRegistry(REGISTRIES[i], REGISTRY_DESCRIPTIONS[i]);
        }

        FuelTweaker.INSTANCE.register();
    }

    @EventHandler
    public void onComplete(FMLLoadCompleteEvent ev) {
        MineTweakerAPI.logInfo("MineTweaker: Building registry");
        ItemBracketHandler.rebuildItemRegistry();
        LiquidBracketHandler.rebuildLiquidRegistry();
        MineTweakerAPI.logInfo("MineTweaker: Sucessfully built item registry");
        GlobalRegistry.registerBracketHandler(new ItemBracketHandler());
        GlobalRegistry.registerBracketHandler(new LiquidBracketHandler());
        GlobalRegistry.registerBracketHandler(new OreBracketHandler());
    }

    @EventHandler
    public void onServerAboutToStart(FMLServerAboutToStartEvent ev) {
        this.server = ev.getServer();
        // starts before loading worlds
        // perfect place to start MineTweaker!

        if (MineTweakerPlatformUtils.isClient()) {
            MineTweakerAPI.client = new MCClient();
        }

        File scriptsDir = new File(MineTweakerHacks.getWorldDirectory(ev.getServer()), "scripts");
        if (!scriptsDir.exists()) {
            scriptsDir.mkdir();
        }

        IScriptProvider scriptsLocal = new ScriptProviderDirectory(scriptsDir);
        IScriptProvider cascaded = new ScriptProviderCascade(scriptsIMC, scriptsLocal, scriptsGlobal);

        MineTweakerImplementationAPI.setScriptProvider(cascaded);
        MineTweakerImplementationAPI.onServerStart(new MCServer(ev.getServer()));
    }

    @EventHandler
    public void onServerStarting(FMLServerStartingEvent ev) {
        this.server = ev.getServer();

    }

    @EventHandler
    public void onServerStopped(FMLServerStoppedEvent ev) {
        MineTweakerImplementationAPI.onServerStop();
        MineTweakerImplementationAPI.setScriptProvider(scriptsGlobal);
        this.server = null;
    }
}
