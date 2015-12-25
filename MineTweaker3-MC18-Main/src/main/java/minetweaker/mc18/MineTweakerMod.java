/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc18;

import java.io.File;

import minetweaker.MineTweakerAPI;
import minetweaker.MineTweakerImplementationAPI;
import minetweaker.api.logger.FileLogger;
import minetweaker.mc18.brackets.ItemBracketHandler;
import minetweaker.mc18.client.MCClient;
import minetweaker.mc18.formatting.MCFormatter;
import minetweaker.mc18.furnace.FuelTweaker;
import minetweaker.mc18.furnace.MCFurnaceManager;
import minetweaker.mc18.game.MCGame;
import minetweaker.mc18.mods.MCLoadedMods;
import minetweaker.mc18.network.MineTweakerCopyClipboardHandler;
import minetweaker.mc18.network.MineTweakerCopyClipboardPacket;
import minetweaker.mc18.network.MineTweakerLoadScriptsHandler;
import minetweaker.mc18.network.MineTweakerLoadScriptsPacket;
import minetweaker.mc18.network.MineTweakerOpenBrowserHandler;
import minetweaker.mc18.network.MineTweakerOpenBrowserPacket;
import minetweaker.mc18.oredict.MCOreDict;
import minetweaker.mc18.recipes.MCRecipeManager;
import minetweaker.mc18.server.MCServer;
import minetweaker.mc18.util.MineTweakerHacks;
import minetweaker.mc18.util.MineTweakerPlatformUtils;
import minetweaker.mc18.vanilla.MCVanilla;
import minetweaker.runtime.IScriptProvider;
import minetweaker.runtime.providers.ScriptProviderCascade;
import minetweaker.runtime.providers.ScriptProviderCustom;
import minetweaker.runtime.providers.ScriptProviderDirectory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Main mod class. Performs some general logic, initialization of the API and
 * FML event handling.
 * 
 * @author Stan Hebben
 */
@Mod(modid = MineTweakerMod.MODID, version = "3.0.10")
public class MineTweakerMod {
	public static final String MODID = "MineTweaker3";
	public static final String MCVERSION = "1.8.8";

	public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

	private static final String[] REGISTRIES = {
			"minetweaker.mods.jei.ClassRegistry"
	};
	private static final String[] REGISTRY_DESCRIPTIONS = {
			"JEI mod support",
	};

	static {
		NETWORK.registerMessage(MineTweakerLoadScriptsHandler.class, MineTweakerLoadScriptsPacket.class, 0, Side.CLIENT);
		NETWORK.registerMessage(MineTweakerOpenBrowserHandler.class, MineTweakerOpenBrowserPacket.class, 1, Side.CLIENT);
		NETWORK.registerMessage(MineTweakerCopyClipboardHandler.class, MineTweakerCopyClipboardPacket.class, 2, Side.CLIENT);
	}

	@Mod.Instance(MODID)
	public static MineTweakerMod INSTANCE;

	public final MCRecipeManager recipes;
	private final IScriptProvider scriptsGlobal;
	private final ScriptProviderCustom scriptsIMC;

	public MineTweakerMod() {
		MineTweakerImplementationAPI.init(
				new MCOreDict(),
				recipes = new MCRecipeManager(),
				new MCFurnaceManager(),
				MCGame.INSTANCE,
				new MCLoadedMods(),
				new MCFormatter(),
				new MCVanilla());

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
		MineTweakerAPI.logInfo("MineTweaker: Sucessfully built item registry");
	}

	@EventHandler
	public void onServerAboutToStart(FMLServerAboutToStartEvent ev) {
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
		IScriptProvider cascaded = new ScriptProviderCascade(scriptsIMC, scriptsGlobal, scriptsLocal);

		MineTweakerImplementationAPI.setScriptProvider(cascaded);
		MineTweakerImplementationAPI.onServerStart(new MCServer(ev.getServer()));
	}

	@EventHandler
	public void onServerStarting(FMLServerStartingEvent ev) {

	}

	@EventHandler
	public void onServerStopped(FMLServerStoppedEvent ev) {
		MineTweakerImplementationAPI.onServerStop();
		MineTweakerImplementationAPI.setScriptProvider(scriptsGlobal);
	}
}
