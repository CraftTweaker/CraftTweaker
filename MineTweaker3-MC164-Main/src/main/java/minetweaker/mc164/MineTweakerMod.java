/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc164;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.registry.GameRegistry;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import minetweaker.MineTweakerAPI;
import minetweaker.MineTweakerImplementationAPI;
import minetweaker.api.event.PlayerLoggedInEvent;
import minetweaker.api.event.PlayerLoggedOutEvent;
import minetweaker.api.logger.FileLogger;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.mc164.client.MCClient;
import minetweaker.mc164.formatting.MCFormatter;
import minetweaker.mc164.furnace.FuelTweaker;
import minetweaker.mc164.furnace.MCFurnaceManager;
import minetweaker.mc164.game.MCGame;
import minetweaker.mc164.mods.MCLoadedMods;
import minetweaker.mc164.network.MCConnectionHandler;
import minetweaker.mc164.network.MCPacketHandler;
import minetweaker.mc164.oredict.MCOreDict;
import minetweaker.mc164.recipes.MCRecipeManager;
import minetweaker.mc164.server.MCServer;
import minetweaker.mc164.util.MineTweakerHacks;
import minetweaker.mc164.util.MineTweakerPlatformUtils;
import minetweaker.mc164.vanilla.MCVanilla;
import minetweaker.runtime.IScriptProvider;
import minetweaker.runtime.providers.ScriptProviderCascade;
import minetweaker.runtime.providers.ScriptProviderCustom;
import minetweaker.runtime.providers.ScriptProviderDirectory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraftforge.common.MinecraftForge;

/**
 * Main mod class. Performs some general logic, initialization of the API and
 * FML event handling.
 * 
 * @author Stan Hebben
 */
@Mod(modid = MineTweakerMod.MODID, version = "3.0.9")
@NetworkMod(clientSideRequired = true, serverSideRequired = true, channels = {MCPacketHandler.CHANNEL_SERVERSCRIPT, MCPacketHandler.CHANNEL_OPENBROWSER}, packetHandler = MCPacketHandler.class)
public class MineTweakerMod {
	public static final String MODID = "MineTweaker3";
	public static final String MCVERSION = "1.6.4";
	
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
	
	@Mod.Instance(MODID)
	public static MineTweakerMod INSTANCE;
	
	public final MCRecipeManager recipes;
	private final IScriptProvider scriptsGlobal;
	private final ScriptProviderCustom scriptsIMC;
	
	private final Map<String, INetworkManager> networkByUser = new HashMap<String, INetworkManager>();
	private final Map<INetworkManager, NetHandler> userByNetwork = new HashMap<INetworkManager, NetHandler>();
	
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
		if (!globalDir.exists()) {
			globalDir.mkdirs();
		}
		
		scriptsGlobal = new ScriptProviderDirectory(globalDir);
		scriptsIMC = new ScriptProviderCustom("intermod");
		MineTweakerImplementationAPI.setScriptProvider(scriptsGlobal);
	}
	
	public void onPlayerLogin(Player player, NetHandler netHandler, INetworkManager manager) {
		networkByUser.put(netHandler.getPlayer().username, manager);
		userByNetwork.put(manager, netHandler);
		
		MineTweakerImplementationAPI.events.publishPlayerLoggedIn(
				new PlayerLoggedInEvent(MineTweakerMC.getIPlayer(netHandler.getPlayer())));
	}
	
	public void onPlayerLogout(INetworkManager manager) {
		// TODO: there appear to be some problems here
		NetHandler netHandler = userByNetwork.get(manager);
		if (netHandler != null && netHandler.getPlayer() != null) {
			String name = netHandler.getPlayer().username;
			networkByUser.remove(name);
			userByNetwork.remove(manager);
			
			MineTweakerImplementationAPI.events.publishPlayerLoggedOut(
					new PlayerLoggedOutEvent(MineTweakerMC.getIPlayer(netHandler.getPlayer())));
		}
	}
	
	public void openBrowser(String user, String url) {
		if (networkByUser.containsKey(user)) {
			networkByUser.get(user).addToSendQueue(new Packet250CustomPayload(
					MCPacketHandler.CHANNEL_OPENBROWSER,
					MCPacketHandler.UTF8.encode(url).array()));
		}
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
		
	}
	
	@EventHandler
	public void onPostInit(FMLPostInitializationEvent ev) {
		MineTweakerAPI.registerClassRegistry(MineTweakerRegistry.class);
		
		for (int i = 0; i < REGISTRIES.length; i++) {
			MineTweakerAPI.registerClassRegistry(REGISTRIES[i], REGISTRY_DESCRIPTIONS[i]);
		}
		
		FuelTweaker.INSTANCE.register();
		NetworkRegistry.instance().registerConnectionHandler(new MCConnectionHandler());
		GameRegistry.registerCraftingHandler(new MTCraftingHandler());
		
		MinecraftForge.EVENT_BUS.register(new ForgeEventHandler());
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
		System.out.println("[MineTweaker] Server stopped");
		
		MineTweakerImplementationAPI.onServerStop();
		MineTweakerImplementationAPI.setScriptProvider(scriptsGlobal);
	}
}
