package com.blamejared.crafttweaker;

import com.blamejared.crafttweaker.api.*;
import net.minecraft.resources.*;
import net.minecraftforge.common.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.event.server.*;
import net.minecraftforge.fml.javafmlmod.*;
import net.minecraftforge.resource.*;
import org.apache.logging.log4j.*;
import org.openzen.zencode.java.*;
import org.openzen.zencode.shared.*;
import org.openzen.zenscript.codemodel.*;

import java.io.*;
import java.util.*;

@Mod(CraftTweaker.MODID)
public class CraftTweaker {
    
    public static final String MODID = "crafttweaker";
    public static final String NAME = "CraftTweaker";
    
    public static final Logger LOG = LogManager.getLogger(NAME);
    
    public CraftTweaker() throws Exception {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    private void setup(final FMLCommonSetupEvent event) {
        LOG.info("{} has loaded successfully!", NAME);
        CraftTweakerAPI.setupLoggers();
        CraftTweakerAPI.SCRIPT_DIR.mkdirs();
        CraftTweakerAPI.SCRIPT_DIR.mkdir();
        CraftTweakerRegistry.findClasses();
        
    }
    
    private void setupClient(final FMLClientSetupEvent event) {
        LOG.info("{} client has loaded successfully!", NAME);
    }
    
    @SubscribeEvent
    public void startServer(FMLServerAboutToStartEvent event) {
        SimpleReloadableResourceManager manager = (SimpleReloadableResourceManager) event.getServer().getResourceManager();
        manager.func_219534_a((ISelectiveResourceReloadListener) (resourceManager, resourcePredicate) -> {
            try {
                ScriptingEngine engine = new ScriptingEngine();
                engine.debug = true;
                //Register crafttweaker module first to assign deps
                JavaNativeModule crafttweakerModule = engine.createNativeModule(MODID, "crafttweaker");
                CraftTweakerRegistry.getClassesInPackage("crafttweaker").forEach(crafttweakerModule::addClass);
                CraftTweakerRegistry.getZenGlobals().forEach(crafttweakerModule::addGlobals);
                
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
                SourceFile[] sourceFiles = Arrays.stream(files).map(file -> new FileSourceFile(file.getName(), file)).toArray(SourceFile[]::new);
                
                SemanticModule scripts = engine.createScriptedModule("scripts", sourceFiles);
                if(!scripts.isValid()) {
                    CraftTweakerAPI.logger.error("Scripts are invalid!");
                    LOG.info("Scripts are invalid!");
                    return;
                }
                engine.registerCompiled(scripts);
                engine.run(Collections.emptyMap(), this.getClass().getClassLoader());
            } catch(Exception e) {
                e.printStackTrace();
                CraftTweakerAPI.logger.throwing("Error running scripts", e);
            }
        });
    }
    
}
