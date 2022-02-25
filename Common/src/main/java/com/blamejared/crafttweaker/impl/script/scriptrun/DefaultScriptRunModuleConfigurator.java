package com.blamejared.crafttweaker.impl.script.scriptrun;

import com.blamejared.crafttweaker.api.ICraftTweakerRegistry;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.IZenClassRegistry;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunModuleConfigurator;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptRunConfiguration;
import org.openzen.zencode.java.module.JavaNativeModule;
import org.openzen.zencode.java.module.converters.JavaNativeConverterBuilder;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings("ClassCanBeRecord")
public final class DefaultScriptRunModuleConfigurator implements IScriptRunModuleConfigurator {
    
    private final String basePackage;
    
    private DefaultScriptRunModuleConfigurator(final String basePackage) {
        
        this.basePackage = basePackage;
    }
    
    public static IScriptRunModuleConfigurator of(final String basePackage) {
        
        return new DefaultScriptRunModuleConfigurator(basePackage);
    }
    
    @Override
    public Collection<JavaNativeModule> populateModules(
            final JavaNativeConverterBuilder nativeConverterBuilder,
            final ICraftTweakerRegistry registry,
            final ScriptRunConfiguration configuration,
            final ModuleCreator creator
    ) {
        
        final IScriptLoader loader = configuration.loader();
        final JavaNativeModule baseModule = this.createModule(creator, registry, loader, this.basePackage, this.basePackage, nativeConverterBuilder);
        
        final List<JavaNativeModule> otherModules = registry.getZenClassRegistry().getRootPackages(loader).stream()
                .filter(it -> it.equals(this.basePackage))
                .map(it -> this.createModule(creator, registry, loader, it, it, nativeConverterBuilder, baseModule))
                .toList();
        
        final List<JavaNativeModule> expansionDependencies = Stream.concat(Stream.of(baseModule), otherModules.stream())
                .toList();
        final JavaNativeModule expansions = this.createModule(creator, registry, loader, "expansions", "", nativeConverterBuilder, expansionDependencies);
        
        return Stream.concat(expansionDependencies.stream(), Stream.of(expansions)).toList();
    }
    
    @SuppressWarnings("SameParameterValue")
    private JavaNativeModule createModule(
            final ModuleCreator creator,
            final ICraftTweakerRegistry registry,
            final IScriptLoader loader,
            final String name,
            final String rootPackage,
            final JavaNativeConverterBuilder builder,
            final List<JavaNativeModule> dependencies
    ) {
        
        return this.createModule(creator, registry, loader, name, rootPackage, builder, dependencies.toArray(JavaNativeModule[]::new));
    }
    
    private JavaNativeModule createModule(
            final ModuleCreator creator,
            final ICraftTweakerRegistry registry,
            final IScriptLoader loader,
            final String name,
            final String rootPackage,
            final JavaNativeConverterBuilder builder,
            final JavaNativeModule... dependencies
    ) {
        
        final JavaNativeModule module = creator.createNativeModule(name, rootPackage, builder, dependencies);
        final IZenClassRegistry zenRegistry = registry.getZenClassRegistry();
        
        zenRegistry.getGlobalsInPackage(loader, name).forEach(module::addGlobals);
        zenRegistry.getClassesInPackage(loader, name).forEach(module::addClass);
        
        return module;
    }
    
}
