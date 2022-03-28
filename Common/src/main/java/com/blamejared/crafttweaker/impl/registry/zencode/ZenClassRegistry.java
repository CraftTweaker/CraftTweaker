package com.blamejared.crafttweaker.impl.registry.zencode;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.natives.INativeTypeRegistry;
import com.blamejared.crafttweaker.api.natives.NativeTypeInfo;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.IZenClassRegistry;
import com.blamejared.crafttweaker.api.zencode.ZenTypeInfo;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class ZenClassRegistry implements IZenClassRegistry {
    
    private final Set<Class<?>> blacklistedClasses = new HashSet<>();
    private final Map<IScriptLoader, LoaderSpecificZenClassRegistry> registryMap = new HashMap<>();
    
    @Override
    public boolean isRegistered(final IScriptLoader loader, final Class<?> clazz) {
        
        return this.get(loader).isRegistered(clazz);
    }
    
    @Override
    public Optional<String> getNameFor(final IScriptLoader loader, final Class<?> clazz) {
        
        return this.get(loader).getNameFor(clazz);
    }
    
    @Override
    public <T> List<Class<? extends T>> getImplementationsOf(final IScriptLoader loader, final Class<T> checkFor) {
        
        return this.get(loader).getImplementationsOf(checkFor);
    }
    
    @Override
    public IClassData getClassData(final IScriptLoader loader) {
        
        return this.get(loader).getImmutableDataView();
    }
    
    @Override
    public List<Class<?>> getClassesInPackage(final IScriptLoader loader, final String packageName) {
        
        return this.get(loader).getClassesInPackage(packageName);
    }
    
    @Override
    public List<Class<?>> getGlobalsInPackage(final IScriptLoader loader, final String packageName) {
        
        return this.get(loader).getGlobalsInPackage(packageName);
    }
    
    @Override
    public Set<String> getRootPackages(final IScriptLoader loader) {
        
        return this.get(loader).getRootPackages();
    }
    
    @Override
    public INativeTypeRegistry getNativeTypeRegistry(final IScriptLoader loader) {
        
        return this.get(loader).getNativeTypeRegistry();
    }
    
    @Override
    public boolean isBlacklisted(final Class<?> cls) {
        
        return this.blacklistedClasses.contains(cls);
    }
    
    public void fillLoaderData(final Collection<IScriptLoader> loaders) {
        
        loaders.forEach(it -> this.registryMap.put(it, new LoaderSpecificZenClassRegistry()));
    }
    
    public void registerNativeType(final IScriptLoader loader, final NativeTypeInfo info) {
        
        this.get(loader).registerNativeType(info);
    }
    
    public void registerZenType(final IScriptLoader loader, final Class<?> clazz, final ZenTypeInfo info, final boolean globals) {
        
        if(this.isIncompatible(clazz)) {
            this.blacklistedClasses.add(clazz);
            return;
        }
        
        this.get(loader).registerZenType(clazz, info, globals);
    }
    
    public void applyInheritanceRules() {
        
        this.registryMap.forEach((loader, loaderData) -> {
            try {
                final Collection<LoaderSpecificZenClassRegistry> inheritedData = loader.allInheritedLoaders()
                        .stream()
                        .map(this::get)
                        .toList();
                loaderData.inheritFrom(inheritedData);
            } catch(final Exception e) {
                throw new IllegalStateException("Unable to apply inheritance rules for loader " + loader.name(), e);
            }
        });
    }
    
    private LoaderSpecificZenClassRegistry get(final IScriptLoader loader) {
        
        return this.registryMap.getOrDefault(
                Objects.requireNonNull(loader, "loader"),
                LoaderSpecificZenClassRegistry.EMPTY
        );
    }
    
    /**
     * Checks that the class does not have any fields or methods that would cause errors when converting in the JavaNativeModule.
     * Does so by simply calling the declaredMethods and fields getters.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private boolean isIncompatible(final Class<?> cls) {
        
        try {
            cls.getDeclaredFields();
            cls.getFields();
            cls.getDeclaredMethods();
            cls.getMethods();
            cls.getConstructors();
            cls.getDeclaredConstructors();
            return false;
        } catch(final Throwable t) {
            CraftTweakerAPI.LOGGER.error(
                    "Could not register class '{}'! This is most likely a compatibility issue!",
                    cls.getCanonicalName(),
                    t
            );
            return true;
        }
    }
    
}
