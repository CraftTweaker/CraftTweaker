/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc11;

import minetweaker.*;
import minetweaker.api.logger.FileLogger;
import minetweaker.mc11.brackets.*;
import minetweaker.mc11.client.MCClient;
import minetweaker.mc11.formatting.MCFormatter;
import minetweaker.mc11.furnace.*;
import minetweaker.mc11.game.MCGame;
import minetweaker.mc11.mods.MCLoadedMods;
import minetweaker.mc11.network.*;
import minetweaker.mc11.oredict.MCOreDict;
import minetweaker.mc11.recipes.MCRecipeManager;
import minetweaker.mc11.server.MCServer;
import minetweaker.mc11.util.*;
import minetweaker.mc11.vanilla.MCVanilla;
import minetweaker.runtime.*;
import minetweaker.runtime.providers.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
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
@Mod(modid = MineTweakerMod.MODID, name = MineTweakerMod.NAME, version = "3.0.14", dependencies = "after:JEI;")
public class MineTweakerMod {
	
	public static final String MODID = "minetweaker3";
	public static final String NAME = "MineTweaker 3";
	public static final String MCVERSION = "1.10.2";
	
	public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
	
	private static final String[] REGISTRIES = {"minetweaker.mods.jei.ClassRegistry", "minetweaker.mods.ic2.ClassRegistry"};
	private static final String[] REGISTRY_DESCRIPTIONS = {"JEI mod support", "IC2 mod support"};
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
		MineTweakerImplementationAPI.init(new MCOreDict(), recipes = new MCRecipeManager(), new MCFurnaceManager(), MCGame.INSTANCE, new MCLoadedMods(), new MCFormatter(), new MCVanilla());
		
		MineTweakerImplementationAPI.logger.addLogger(new FileLogger(new File("minetweaker.log")));
		MineTweakerImplementationAPI.platform = MCPlatformFunctions.INSTANCE;
		
		File globalDir = new File("scripts");
		if(!globalDir.exists())
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
		event.getMessages().stream().filter(imcMessage -> imcMessage.key.equalsIgnoreCase("addMineTweakerScript")).forEach(imcMessage -> {
			if(imcMessage.isStringMessage()) {
				scriptsIMC.add(imcMessage.getSender() + "::imc", imcMessage.getStringValue());
			} else if(imcMessage.isNBTMessage()) {
				NBTTagCompound message = imcMessage.getNBTValue();
				scriptsIMC.add(imcMessage.getSender() + "::" + message.getString("name"), message.getString("content"));
			}
		});
	}
	
	@EventHandler
	public void onLoad(FMLPreInitializationEvent ev) {
		MinecraftForge.EVENT_BUS.register(new ForgeEventHandler());
		MinecraftForge.EVENT_BUS.register(new FMLEventHandler());
	}
	
	@EventHandler
	public void onPostInit(FMLPostInitializationEvent ev) {
		MineTweakerAPI.registerClassRegistry(MineTweakerRegistry.class);
		
		for(int i = 0; i < REGISTRIES.length; i++) {
			MineTweakerAPI.registerClassRegistry(REGISTRIES[i], REGISTRY_DESCRIPTIONS[i]);
		}
		FuelTweaker.INSTANCE.register();
		MineTweakerAPI.logInfo("MineTweaker: Building registry");
		ItemBracketHandler.rebuildItemRegistry();
		LiquidBracketHandler.rebuildLiquidRegistry();
		MineTweakerAPI.logInfo("MineTweaker: Sucessfully built item registry");
		GlobalRegistry.registerBracketHandler(new ItemBracketHandler());
		GlobalRegistry.registerBracketHandler(new LiquidBracketHandler());
		GlobalRegistry.registerBracketHandler(new OreBracketHandler());
	}
	
	@EventHandler
	public void onComplete(FMLLoadCompleteEvent ev) {
		
	}
	
	@EventHandler
	public void onServerAboutToStart(FMLServerAboutToStartEvent ev) {
		server = ev.getServer();
		
	}
	
	@EventHandler
	public void onServerStarting(FMLServerStartingEvent ev) {
		server = ev.getServer();
		// starts before loading worlds
		// perfect place to start MineTweaker!
		
		if(MineTweakerPlatformUtils.isClient()) {
			MineTweakerAPI.client = new MCClient();
		}
		
		File scriptsDir = new File(MineTweakerHacks.getWorldDirectory(ev.getServer()), "scripts");
		if(!scriptsDir.exists()) {
			scriptsDir.mkdir();
		}
		
		IScriptProvider scriptsLocal = new ScriptProviderDirectory(scriptsDir);
		IScriptProvider cascaded = new ScriptProviderCascade(scriptsIMC, scriptsGlobal, scriptsLocal);
		
		MineTweakerImplementationAPI.setScriptProvider(cascaded);
		MineTweakerImplementationAPI.onServerStart(new MCServer(ev.getServer()));
	}
	
	@EventHandler
	public void onServerStopped(FMLServerStoppedEvent ev) {
		MineTweakerImplementationAPI.onServerStop();
		MineTweakerImplementationAPI.setScriptProvider(scriptsGlobal);
		server = null;
	}
}
