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
        final IZenClassRegistry zenClassRegistry = registry.getZenClassRegistry();
        final JavaNativeModule baseModule = this.createModule(creator, zenClassRegistry, loader, this.basePackage, this.basePackage, nativeConverterBuilder);
        
        final List<JavaNativeModule> otherModules = registry.getZenClassRegistry().getRootPackages(loader).stream()
                .filter(it -> !it.equals(this.basePackage))
                .map(it -> this.createModule(creator, zenClassRegistry, loader, it, it, nativeConverterBuilder, baseModule))
                .toList();
        
        final List<JavaNativeModule> expansionDependencies = Stream.concat(Stream.of(baseModule), otherModules.stream())
                .toList();
        final JavaNativeModule expansions = this.createExpansionModule(creator, zenClassRegistry, loader, nativeConverterBuilder, expansionDependencies);
        
        return Stream.concat(expansionDependencies.stream(), Stream.of(expansions)).toList();
    }
    
    private JavaNativeModule createExpansionModule(
            final ModuleCreator creator,
            final IZenClassRegistry registry,
            final IScriptLoader loader,
            final JavaNativeConverterBuilder builder,
            final List<JavaNativeModule> dependencies
    ) {
        
        return this.createModule(
                creator,
                "expansions",
                "",
                builder,
                dependencies.toArray(JavaNativeModule[]::new),
                List.of(),
                registry.getClassData(loader).expansions().values()
        );
    }
    
    private JavaNativeModule createModule(
            final ModuleCreator creator,
            final IZenClassRegistry registry,
            final IScriptLoader loader,
            final String name,
            final String rootPackage,
            final JavaNativeConverterBuilder builder,
            final JavaNativeModule... dependencies
    ) {
        
        return this.createModule(
                creator,
                name,
                rootPackage,
                builder,
                dependencies,
                registry.getGlobalsInPackage(loader, rootPackage),
                registry.getClassesInPackage(loader, rootPackage)
        );
    }
    
    private JavaNativeModule createModule(
            final ModuleCreator creator,
            final String name,
            final String rootPackage,
            final JavaNativeConverterBuilder builder,
            final JavaNativeModule[] dependencies,
            final Collection<Class<?>> globals,
            final Collection<Class<?>> classes
    ) {
        
        final JavaNativeModule module = creator.createNativeModule(name, rootPackage, builder, dependencies);
        globals.forEach(module::addGlobals);
        classes.forEach(module::addClass);
        return module;
    }
    
}
