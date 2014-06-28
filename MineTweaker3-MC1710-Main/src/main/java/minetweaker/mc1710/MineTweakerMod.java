/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1710;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import java.io.File;
import minetweaker.MineTweakerAPI;
import minetweaker.mc1710.furnace.FuelTweaker;
import minetweaker.mc1710.furnace.MCFurnaceManager;
import minetweaker.mc1710.mods.MCLoadedMods;
import minetweaker.mc1710.network.MineTweakerLoadScriptsHandler;
import minetweaker.mc1710.network.MineTweakerLoadScriptsPacket;
import minetweaker.mc1710.oredict.MCOreDict;
import minetweaker.mc1710.recipes.MCRecipeManager;
import minetweaker.mc1710.util.MineTweakerHacks;
import minetweaker.runtime.IScriptProvider;
import minetweaker.runtime.providers.ScriptProviderCascade;
import minetweaker.runtime.providers.ScriptProviderDirectory;
import net.minecraftforge.common.MinecraftForge;

/**
 * Main mod class. Performs some general logic, initialization of the API and
 * FML event handling.
 * 
 * @author Stan Hebben
 */
@Mod(modid = MineTweakerMod.MODID, version = MineTweakerMod.MCVERSION + "-3.0.0")
public class MineTweakerMod {
	public static final String MODID = "MineTweaker3";
	public static final String MCVERSION = "1.7.2";
	
	public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
	
	private static final String[] REGISTRIES = {
		"minetweaker.mods.ic2.ClassRegistry",
		"minetweaker.mods.nei.ClassRegistry"
	};
	
	static {
		NETWORK.registerMessage(MineTweakerLoadScriptsHandler.class, MineTweakerLoadScriptsPacket.class, 0, Side.CLIENT);
	}
	
	@Mod.Instance(MODID)
	public static MineTweakerMod INSTANCE;
	
	private final IScriptProvider scriptsGlobal;
	
	private boolean iAmServer = false;
	
	public MineTweakerMod() {
		MineTweakerAPI.oreDict = new MCOreDict();
		MineTweakerAPI.recipes = new MCRecipeManager();
		MineTweakerAPI.logger = new MineTweakerLogger();
		MineTweakerAPI.furnace = new MCFurnaceManager();
		MineTweakerAPI.loadedMods = new MCLoadedMods();
		
		File globalDir = new File("scripts");
		if (!globalDir.exists()) {
			globalDir.mkdirs();
		}
		
		scriptsGlobal = new ScriptProviderDirectory(globalDir);
		MineTweakerAPI.tweaker.setScriptProvider(scriptsGlobal);
	}
	
	public boolean iAmServer() {
		return iAmServer;
	}
	
	/**
	 * Reloads all scripts.
	 */
	public void reload() {
		MineTweakerAPI.tweaker.rollback();
		MineTweakerAPI.tweaker.load();
		
		if (iAmServer) {
			// execute script on all connected clients
			NETWORK.sendToAll(new MineTweakerLoadScriptsPacket(MineTweakerAPI.tweaker.getScriptData()));
		}
	}
	
	// ##########################
	// ### FML Event Handlers ###
	// ##########################
	
	@EventHandler
	public void onLoad(FMLPreInitializationEvent ev) {
		MinecraftForge.EVENT_BUS.register(new FMLEventHandler());
		FMLCommonHandler.instance().bus().register(new FMLEventHandler());
	}
	
	@EventHandler
	public void onPostInit(FMLPostInitializationEvent ev) {
		MineTweakerAPI.registerClassRegistry(MineTweakerRegistry.class);
		
		for (String registry : REGISTRIES) {
			MineTweakerAPI.registerClassRegistry(registry);
		}
		
		FuelTweaker.INSTANCE.register();
	}
	
	@EventHandler
	public void onServerAboutToStart(FMLServerAboutToStartEvent ev) {
		// starts before loading worlds
		// perfect place to start MineTweaker!
		
		File scriptsDir = new File(MineTweakerHacks.getWorldDirectory(ev.getServer()), "scripts");
		if (!scriptsDir.exists()) {
			scriptsDir.mkdir();
		}
		
		iAmServer = true;
		
		IScriptProvider scriptsLocal = new ScriptProviderDirectory(scriptsDir);
		IScriptProvider cascaded = new ScriptProviderCascade(scriptsGlobal, scriptsLocal);
		MineTweakerAPI.tweaker.setScriptProvider(cascaded);
		reload();
	}
	
	@EventHandler
	public void onServerStarting(FMLServerStartingEvent ev) {
		ev.registerServerCommand(new MineTweakerCommand());
	}
	
	@EventHandler
	public void onServerStopped(FMLServerStoppedEvent ev) {
		iAmServer = false;
	}
}
