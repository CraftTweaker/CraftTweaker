/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc172;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.mc172.brackets.ItemBracketHandler;
import minetweaker.mc172.brackets.LiquidBracketHandler;
import minetweaker.mc172.brackets.OreBracketHandler;
import minetweaker.mc172.network.MineTweakerLoadScriptsHandler;
import minetweaker.mc172.network.MineTweakerLoadScriptsPacket;
import minetweaker.mc172.oredict.OreDict;
import minetweaker.mc172.recipes.MTRecipeManager;
import minetweaker.mc172.util.MineTweakerHacks;
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
	public static final String MODID = "MineTweaker";
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
		MineTweakerAPI.oreDict = new OreDict();
		MineTweakerAPI.recipes = new MTRecipeManager();
		MineTweakerAPI.logger = new MineTweakerLogger();
		
		List<Class> classes = new ArrayList<Class>();
		MineTweakerRegistry.getClasses(classes);
		
		outer: for (Class cls : classes) {
			for (Annotation annotation : cls.getAnnotations()) {
				if (annotation instanceof ModOnly) {
					String[] value = ((ModOnly) annotation).value();
					for (String mod : value) {
						if (!Loader.isModLoaded(mod)) {
							continue outer; // skip this class
						}
					}
				}
			}
			
			MineTweakerAPI.registerClass(cls);
		}
		
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
		
		// execute script on all connected clients
		NETWORK.sendToAll(new MineTweakerLoadScriptsPacket(MineTweakerAPI.tweaker.getScriptData()));
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
		MineTweakerAPI.registerBracketHandler(new ItemBracketHandler());
		MineTweakerAPI.registerBracketHandler(new LiquidBracketHandler());
		MineTweakerAPI.registerBracketHandler(new OreBracketHandler());
		
		for (String registry : REGISTRIES) {
			try {
				Class cls = Class.forName(registry);
				Method method = cls.getMethod("register");
				if ((method.getModifiers() & Modifier.STATIC) == 0) {
					System.out.println("ERROR: register method in " + registry + " isn't static");
				} else {
					method.invoke(null);
				}
			} catch (ClassNotFoundException ex) {
				
			} catch (NoSuchMethodException ex) {
				
			} catch (IllegalAccessException ex) {
				
			} catch (InvocationTargetException ex) {
				
			}
		}
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
}
