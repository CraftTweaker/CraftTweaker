package minetweaker.mc1102;

import minetweaker.*;
import minetweaker.annotations.BracketHandler;
import minetweaker.mc1102.brackets.*;
import minetweaker.mc1102.client.MCClient;
import minetweaker.mc1102.formatting.MCFormatter;
import minetweaker.mc1102.furnace.*;
import minetweaker.mc1102.game.MCGame;
import minetweaker.mc1102.logger.MCLogger;
import minetweaker.mc1102.mods.MCLoadedMods;
import minetweaker.mc1102.network.*;
import minetweaker.mc1102.oredict.MCOreDict;
import minetweaker.mc1102.recipes.MCRecipeManager;
import minetweaker.mc1102.server.MCServer;
import minetweaker.mc1102.util.*;
import minetweaker.mc1102.vanilla.MCVanilla;
import minetweaker.runtime.*;
import minetweaker.runtime.providers.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import stanhebben.zenscript.annotations.*;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Main mod class. Performs some general logic, initialization of the API and
 * FML event handling.
 *
 * @author Stan Hebben
 */
@Mod(modid = MineTweakerMod.MODID, version = "3.0.25", name = MineTweakerMod.NAME, dependencies = "after:JEI@[3.14.2.398,)")
public class MineTweakerMod {
    
    public static final String MODID = "MineTweaker3";
    public static final String NAME = "MineTweaker3";
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
        MineTweakerImplementationAPI.logger.addLogger(new MCLogger(new File("minetweaker.log")));
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
//        List<Class> apiClasses = new ArrayList<>();
//        String[] classNames = new String[]{ZenExpansion.class.getCanonicalName(), ZenClass.class.getCanonicalName(), BracketHandler.class.getCanonicalName()};
//        for(String name : classNames) {
//            ev.getAsmData().getAll(name).forEach(clazz -> {
//                boolean valid = true;
//                try {
//                    for(ModContainer mod : clazz.getCandidate().getContainedMods()) {
//                        if(!mod.getName().equals("MineTweaker 3") || !mod.getName().equals("CT-GUI")) {
//                            valid = false;
//                        }
//                    }
//                    if(valid) {
//                        Class<?> asmClass = Class.forName(clazz.getClassName());
//                        apiClasses.add(asmClass);
//                    }
//                } catch(ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
//            });
//        }
//        apiClasses.forEach(i -> {
//            System.out.println(">>> " + i.getName());
//            MineTweakerAPI.registerClass(i);
//        });
    }
    
    @EventHandler
    public void onPostInit(FMLPostInitializationEvent ev) {
        MineTweakerAPI.registerClassRegistry(MineTweakerRegistry.class);
        
        for(int i = 0; i < REGISTRIES.length; i++) {
            MineTweakerAPI.registerClassRegistry(REGISTRIES[i], REGISTRY_DESCRIPTIONS[i]);
        }
    }
    
    @EventHandler
    public void onComplete(FMLLoadCompleteEvent ev) {
        MineTweakerAPI.logInfo("MineTweaker: Building registry");
        ItemBracketHandler.rebuildItemRegistry();
        LiquidBracketHandler.rebuildLiquidRegistry();
        EntityBracketHandler.rebuildEntityRegistry();
        MineTweakerAPI.logInfo("MineTweaker: Sucessfully built item registry");
        GlobalRegistry.registerBracketHandler(new ItemBracketHandler());
        GlobalRegistry.registerBracketHandler(new LiquidBracketHandler());
        GlobalRegistry.registerBracketHandler(new OreBracketHandler());
        GlobalRegistry.registerBracketHandler(new EntityBracketHandler());
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
        MineTweakerAPI.logInfo("Server stopping, Scripts are being rolled back!");
        MineTweakerAPI.tweaker.rollback();
        server = null;
    }
}
