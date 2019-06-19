package com.blamejared.crafttweaker;

import com.blamejared.crafttweaker.api.*;
import net.minecraft.resources.SimpleReloadableResourceManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;
import org.apache.logging.log4j.*;
import org.openzen.zencode.java.*;
import org.openzen.zencode.shared.*;
import org.openzen.zenscript.codemodel.*;
import org.openzen.zenscript.lexer.ParseException;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Mod(CraftTweaker.MODID)
public class CraftTweaker {
    
    public static final String MODID = "crafttweaker";
    public static final String NAME = "CraftTweaker";
    
    public static final Logger LOG = LogManager.getLogger(NAME);
    
    public CraftTweaker() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    private void setup(final FMLCommonSetupEvent event) {
        LOG.info("{} has loaded successfully!", NAME);
        CraftTweakerAPI.SCRIPT_DIR.mkdirs();
        CraftTweakerAPI.SCRIPT_DIR.mkdir();
        
        CraftTweakerRegistry.findClasses();
    }
    
    private void setupClient(final FMLClientSetupEvent event) {
        LOG.info("{} client has loaded successfully!", NAME);
    }
    
    @SubscribeEvent
    public void startServer(FMLServerAboutToStartEvent event) {
        //TODO this is still untested code, scripts aren't compiled yet, just discovering a registering classes
        SimpleReloadableResourceManager manager = (SimpleReloadableResourceManager) event.getServer().getResourceManager();
        manager.func_219534_a((ISelectiveResourceReloadListener) (resourceManager, resourcePredicate) -> {
            ScriptingEngine engine = new ScriptingEngine();
            engine.debug = true;
            //Register crafttweaker module first to assign deps
            JavaNativeModule crafttweakerModule = engine.createNativeModule(MODID, "crafttweaker");
            CraftTweakerRegistry.getClassesInPackage("crafttweaker").forEach(crafttweakerModule::addClass);
            CraftTweakerRegistry.getZenGlobals().forEach(crafttweakerModule::addGlobals);
            try {
                engine.registerNativeProvided(crafttweakerModule);
                for(String key : CraftTweakerRegistry.getRootPackages()) {
                    //module already registered
                    if(key.equals("crafttweaker")) {
                        continue;
                    }
                    JavaNativeModule module = engine.createNativeModule(key, key, crafttweakerModule);
                    CraftTweakerRegistry.getClassesInPackage(key).forEach(module::addClass);
                    engine.registerNativeProvided(module);
                }
                
                File[] files = CraftTweakerAPI.SCRIPT_DIR.listFiles((dir, name) -> {
                    name = name.toLowerCase();
                    return name.endsWith(".zs") || name.endsWith(".zc");
                });
                SourceFile[] sourceFiles = Arrays.stream(files).map(file -> new FileSourceFile(file.getName(), file)).collect(Collectors.toList()).toArray(new SourceFile[0]);
                
                SemanticModule scripts = engine.createScriptedModule("scripts", sourceFiles);
                if(!scripts.isValid()) {
                    LOG.info("Scripts are invalid!");
                    return;
                }
                engine.registerCompiled(scripts);
                engine.run();
                
            } catch(CompileException | ParseException e) {
                e.printStackTrace();
            }
        });
    }
}
