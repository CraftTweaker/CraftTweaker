/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc164;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.network.PacketDispatcher;
import java.io.File;
import minetweaker.MineTweakerAPI;
import minetweaker.mc164.furnace.FuelTweaker;
import minetweaker.mc164.furnace.MCFurnaceManager;
import minetweaker.mc164.mods.MCLoadedMods;
import minetweaker.mc164.network.MCPacketHandler;
import minetweaker.mc164.oredict.MCOreDict;
import minetweaker.mc164.recipes.MCRecipeManager;
import minetweaker.mc164.util.MineTweakerHacks;
import minetweaker.runtime.IScriptProvider;
import minetweaker.runtime.providers.ScriptProviderCascade;
import minetweaker.runtime.providers.ScriptProviderDirectory;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

/**
 * Main mod class. Performs some general logic, initialization of the API and
 * FML event handling.
 * 
 * @author Stan Hebben
 */
@Mod(modid = MineTweakerMod.MODID, version = MineTweakerMod.MCVERSION + "-3.0.0")
public class MineTweakerMod {
	public static final String MODID = "MineTweaker";
	public static final String MCVERSION = "1.7.2";
	
	private static final String[] REGISTRIES = {
		"minetweaker.mods.ic2.ClassRegistry",
		"minetweaker.mods.nei.ClassRegistry"
	};
	
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
			Packet packet = new Packet250CustomPayload(
					MCPacketHandler.CHANNEL_SERVERSCRIPT,
					MineTweakerAPI.tweaker.getScriptData());
			PacketDispatcher.sendPacketToAllPlayers(packet);
		}
	}
	
	// ##########################
	// ### FML Event Handlers ###
	// ##########################
	
	@EventHandler
	public void onLoad(FMLPreInitializationEvent ev) {
		
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
