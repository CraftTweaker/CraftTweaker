package com.blamejared.crafttweaker.api.zencode.scriptrun;

import com.blamejared.crafttweaker.api.ICraftTweakerRegistry;
import com.blamejared.crafttweaker.platform.Services;
import org.openzen.zencode.java.module.JavaNativeModule;
import org.openzen.zencode.shared.CompileException;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

@FunctionalInterface
public interface IScriptRunModuleConfigurator {
    
    @FunctionalInterface
    interface ModuleCreator {
        
        JavaNativeModule createNativeModule(
                final String name,
                final String rootPackage,
                final List<JavaNativeModule> dependencies,
                final Consumer<JavaNativeModule> configurator
        ) throws CompileException;
        
    }
    
    static IScriptRunModuleConfigurator createDefault(final String basePackage) {
        
        return Services.BRIDGE.defaultScriptRunModuleConfigurator(basePackage);
    }
    
    Collection<JavaNativeModule> populateModules(
            final ICraftTweakerRegistry registry,
            final ScriptRunConfiguration configuration,
            final ModuleCreator creator
    ) throws CompileException;
    
}
