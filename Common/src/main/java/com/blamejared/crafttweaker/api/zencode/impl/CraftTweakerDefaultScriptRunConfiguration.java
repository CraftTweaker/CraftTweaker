package com.blamejared.crafttweaker.api.zencode.impl;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.CraftTweakerRegistry;
import com.blamejared.crafttweaker.api.ScriptLoadingOptions;
import com.blamejared.crafttweaker.api.zencode.impl.native_type.CrTJavaNativeConverterBuilder;
import org.openzen.zencode.java.ScriptingEngine;
import org.openzen.zencode.java.module.JavaNativeModule;
import org.openzen.zencode.java.module.converters.JavaNativeConverterBuilder;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zenscript.parser.BracketExpressionParser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class CraftTweakerDefaultScriptRunConfiguration {
    
    public static final ScriptLoadingOptions.ScriptRunConfiguration DEFAULT_CONFIGURATION = CraftTweakerDefaultScriptRunConfiguration::configure;
    
    private static void configure(
            final BiConsumer<String, BracketExpressionParser> registrationFunction,
            final Consumer<JavaNativeModule> addingFunction,
            final ScriptingEngine engine
    ) throws CompileException {
        
        registerModules(registrationFunction, addingFunction, engine);
    }
    
    private static void registerModules(
            final BiConsumer<String, BracketExpressionParser> registrationFunction,
            final Consumer<JavaNativeModule> addingFunction,
            final ScriptingEngine engine
    ) throws CompileException {
        
        final List<JavaNativeModule> modules = new ArrayList<>();
        final CrTJavaNativeConverterBuilder nativeConverterBuilder = new CrTJavaNativeConverterBuilder();
        
        //Register crafttweaker module first to assign deps
        final JavaNativeModule crafttweakerModule =
                addModule(registrationFunction, addingFunction, modules::add, engine, CraftTweakerConstants.MOD_ID, CraftTweakerConstants.MOD_ID, nativeConverterBuilder);
        
        final Set<String> rootPackages = new HashSet<>(CraftTweakerRegistry.getRootPackages());
        rootPackages.remove(CraftTweakerConstants.MOD_ID);
        for(String rootPackage : rootPackages) {
            addModule(registrationFunction, addingFunction, modules::add, engine, rootPackage, rootPackage, nativeConverterBuilder, crafttweakerModule);
        }
        
        addModule(
                registrationFunction,
                addingFunction,
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
            final BiConsumer<String, BracketExpressionParser> registrationFunction,
            final Consumer<JavaNativeModule> addingFunction,
            final Consumer<JavaNativeModule> applyFunction,
            final ScriptingEngine engine,
            final String moduleName,
            final String basePackage,
            final JavaNativeConverterBuilder nativeConverterBuilder,
            final JavaNativeModule... dependencies
    ) throws CompileException {
        
        final JavaNativeModule module = createModule(registrationFunction, addingFunction, engine, moduleName, basePackage, nativeConverterBuilder, dependencies);
        applyFunction.accept(module);
        engine.registerNativeProvided(module);
        return module;
    }
    
    private static JavaNativeModule createModule(
            final BiConsumer<String, BracketExpressionParser> registrationFunction,
            final Consumer<JavaNativeModule> addingFunction,
            final ScriptingEngine engine,
            final String moduleName,
            final String basePackage,
            final JavaNativeConverterBuilder nativeConverterBuilder,
            final JavaNativeModule... dependencies
    ) {
        
        JavaNativeModule module = engine.createNativeModule(moduleName, basePackage, dependencies, nativeConverterBuilder);
        
        CraftTweakerRegistry.getBracketResolvers(moduleName, engine, module)
                .forEach(it -> registrationFunction.accept(it.getName(), it));
        addingFunction.accept(module);
        
        CraftTweakerRegistry.getGlobalsInPackage(moduleName).forEach(module::addGlobals);
        CraftTweakerRegistry.getClassesInPackage(moduleName).forEach(module::addClass);
        
        return module;
    }
    
}
