/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1710;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import minetweaker.MineTweakerAPI;
import minetweaker.MineTweakerImplementationAPI;
import minetweaker.api.logger.FileLogger;
import minetweaker.mc1710.brackets.ItemBracketHandler;
import minetweaker.mc1710.brackets.LiquidBracketHandler;
import minetweaker.mc1710.client.MCClient;
import minetweaker.mc1710.formatting.MCFormatter;
import minetweaker.mc1710.furnace.FuelTweaker;
import minetweaker.mc1710.furnace.MCFurnaceManager;
import minetweaker.mc1710.game.MCGame;
import minetweaker.mc1710.mods.MCLoadedMods;
import minetweaker.mc1710.network.*;
import minetweaker.mc1710.oredict.MCOreDict;
import minetweaker.mc1710.recipes.MCRecipeManager;
import minetweaker.mc1710.server.MCServer;
import minetweaker.mc1710.util.MineTweakerHacks;
import minetweaker.mc1710.util.MineTweakerPlatformUtils;
import minetweaker.mc1710.vanilla.MCVanilla;
import minetweaker.runtime.IScriptProvider;
import minetweaker.runtime.providers.ScriptProviderCascade;
import minetweaker.runtime.providers.ScriptProviderCustom;
import minetweaker.runtime.providers.ScriptProviderDirectory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;

/**
 * Main mod class. Performs some general logic, initialization of the API and
 * FML event handling.
 * 
 * @author Stan Hebben
 */
@Mod(modid = MineTweakerMod.MODID, version = "3.0.13")
public class MineTweakerMod {
	public static final String MODID = "MineTweaker3";
	public static final String MCVERSION = "1.7.10";

	public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

	private static final String[] REGISTRIES = {
			"minetweaker.mods.ic2.ClassRegistry",
			"minetweaker.mods.nei.ClassRegistry",
			"minetweaker.mods.mfr.ClassRegistry",
			"minetweaker.mods.gregtech.ClassRegistry",
			"minetweaker.mods.buildcraft.ClassRegistry"
	};
	private static final String[] REGISTRY_DESCRIPTIONS = {
			"IC2 mod support",
			"NEI mod support",
			"MFR mod support",
			"GregTech mod support",
			"Buildcraft mod support"
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
		LiquidBracketHandler.rebuildLiquidRegistry();
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
		IScriptProvider cascaded = new ScriptProviderCascade(scriptsIMC, scriptsGlobal,scriptsLocal);

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
