package com.blamejared.crafttweaker.api.zencode.impl;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.CraftTweakerRegistry;
import com.blamejared.crafttweaker.api.zencode.impl.native_type.CrTJavaNativeConverterBuilder;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IBepRegistrationHandler;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IBepToModuleAdder;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptLoadingOptionsView;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunConfigurator;
import org.openzen.zencode.java.ScriptingEngine;
import org.openzen.zencode.java.module.JavaNativeModule;
import org.openzen.zencode.java.module.converters.JavaNativeConverterBuilder;
import org.openzen.zencode.shared.CompileException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public final class CraftTweakerDefaultScriptRunConfiguration implements IScriptRunConfigurator {
    
    public static final CraftTweakerDefaultScriptRunConfiguration INSTANCE = new CraftTweakerDefaultScriptRunConfiguration();
    
    private CraftTweakerDefaultScriptRunConfiguration() {}
    
    @Override
    public void configure(
            final IBepRegistrationHandler registrationHandler,
            final IBepToModuleAdder moduleAdder,
            final ScriptingEngine engine,
            final IScriptLoadingOptionsView options
    ) throws CompileException {
        
        this.registerModules(registrationHandler, moduleAdder, engine);
    }
    
    private void registerModules(
            final IBepRegistrationHandler registrationHandler,
            final IBepToModuleAdder moduleAdder,
            final ScriptingEngine engine
    ) throws CompileException {
        
        final List<JavaNativeModule> modules = new ArrayList<>();
        final CrTJavaNativeConverterBuilder nativeConverterBuilder = new CrTJavaNativeConverterBuilder();
        
        //Register crafttweaker module first to assign deps
        final JavaNativeModule crafttweakerModule =
                addModule(registrationHandler, moduleAdder, modules::add, engine, CraftTweakerConstants.MOD_ID, CraftTweakerConstants.MOD_ID, nativeConverterBuilder);
        
        final Set<String> rootPackages = new HashSet<>(CraftTweakerRegistry.getRootPackages());
        rootPackages.remove(CraftTweakerConstants.MOD_ID);
        for(String rootPackage : rootPackages) {
            addModule(registrationHandler, moduleAdder, modules::add, engine, rootPackage, rootPackage, nativeConverterBuilder, crafttweakerModule);
        }
        
        addModule(
                registrationHandler,
                moduleAdder,
                it -> CraftTweakerRegistry.getExpansions()
                        .values()
                        .stream()
                        .flatMap(List::stream)
                        .forEach(it::addClass),
                engine,
                "expansions",
                "",
                nativeConverterBuilder,
                modules.toArray(JavaNativeModule[]::new)
        );
        
        nativeConverterBuilder.headerConverter.reinitializeAllLazyValues();
    }
    
    private static JavaNativeModule addModule(
            final IBepRegistrationHandler registrationHandler,
            final IBepToModuleAdder moduleAdder,
            final Consumer<JavaNativeModule> applyLambda,
            final ScriptingEngine engine,
            final String moduleName,
            final String basePackage,
            final JavaNativeConverterBuilder nativeConverterBuilder,
            final JavaNativeModule... dependencies
    ) throws CompileException {
        
        final JavaNativeModule module = createModule(registrationHandler, moduleAdder, engine, moduleName, basePackage, nativeConverterBuilder, dependencies);
        applyLambda.accept(module);
        engine.registerNativeProvided(module);
        return module;
    }
    
    private static JavaNativeModule createModule(
            final IBepRegistrationHandler registrationHandler,
            final IBepToModuleAdder moduleAdder,
            final ScriptingEngine engine,
            final String moduleName,
            final String basePackage,
            final JavaNativeConverterBuilder nativeConverterBuilder,
            final JavaNativeModule... dependencies
    ) {
        
        JavaNativeModule module = engine.createNativeModule(moduleName, basePackage, dependencies, nativeConverterBuilder);
        
        CraftTweakerRegistry.getBracketResolvers(moduleName, engine, module)
                .forEach(registrationHandler::registerBracketHandler);
        moduleAdder.addBepToModule(module);
        
        CraftTweakerRegistry.getGlobalsInPackage(moduleName).forEach(module::addGlobals);
        CraftTweakerRegistry.getClassesInPackage(moduleName).forEach(module::addClass);
        
        return module;
    }
    
}
