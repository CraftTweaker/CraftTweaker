package com.blamejared.crafttweaker.impl.script.scriptrun;

import com.blamejared.crafttweaker.api.ICraftTweakerRegistry;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.IZenClassRegistry;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunModuleConfigurator;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptRunConfiguration;
import org.openzen.zencode.java.module.JavaNativeModule;
import org.openzen.zencode.shared.CompileException;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
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
            final ICraftTweakerRegistry registry,
            final ScriptRunConfiguration configuration,
            final ModuleCreator creator
    ) throws CompileException {
        
        final IScriptLoader loader = configuration.loader();
        final IZenClassRegistry zenClassRegistry = registry.getZenClassRegistry();
        final JavaNativeModule baseModule = this.createModule(creator, zenClassRegistry, loader, this.basePackage, this.basePackage);
        final List<JavaNativeModule> otherModules = this.createOtherModules(creator, zenClassRegistry, loader, baseModule);
        
        final List<JavaNativeModule> expansionDependencies = Stream.concat(Stream.of(baseModule), otherModules.stream())
                .toList();
        final JavaNativeModule expansions = this.createExpansionModule(creator, zenClassRegistry, loader, expansionDependencies);
        
        return Stream.concat(expansionDependencies.stream(), Stream.of(expansions)).toList();
    }
    
    private List<JavaNativeModule> createOtherModules(
            final ModuleCreator creator,
            final IZenClassRegistry registry,
            final IScriptLoader loader,
            final JavaNativeModule baseModule
    ) throws CompileException {
        
        final AtomicReference<CompileException> exception = new AtomicReference<>(null);
        final List<JavaNativeModule> otherModules = registry.getRootPackages(loader).stream()
                .filter(it -> !it.equals(this.basePackage))
                .map(it -> {
                    try {
                        return this.createModule(creator, registry, loader, it, it, baseModule);
                    } catch(final CompileException e) {
                        if(!exception.compareAndSet(null, e)) {
                            exception.get().addSuppressed(e);
                        }
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
        
        if(exception.get() != null) {
            throw exception.get();
        }
        
        return otherModules;
    }
    
    private JavaNativeModule createExpansionModule(
            final ModuleCreator creator,
            final IZenClassRegistry registry,
            final IScriptLoader loader,
            final List<JavaNativeModule> dependencies
    ) throws CompileException {
        
        return this.createModule(
                creator,
                "expansions",
                "",
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
            final JavaNativeModule... dependencies
    ) throws CompileException {
        
        return this.createModule(
                creator,
                name,
                rootPackage,
                dependencies,
                registry.getGlobalsInPackage(loader, rootPackage),
                registry.getClassesInPackage(loader, rootPackage)
        );
    }
    
    private JavaNativeModule createModule(
            final ModuleCreator creator,
            final String name,
            final String rootPackage,
            final JavaNativeModule[] dependencies,
            final Collection<Class<?>> globals,
            final Collection<Class<?>> classes
    ) throws CompileException {
        
        return creator.createNativeModule(name, rootPackage, Arrays.asList(dependencies), it -> {
            globals.forEach(it::addGlobals);
            classes.forEach(it::addClass);
        });
    }
    
}
