package com.blamejared.crafttweaker.api.tag;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.api.tag.manager.type.KnownTagManager;
import com.blamejared.crafttweaker.api.util.InstantiationUtil;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagManager;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.tag.TagManager")
public final class CraftTweakerTagRegistry {
    
    public static final String GLOBAL_NAME = "tags";
    
    @ZenCodeGlobals.Global(GLOBAL_NAME)
    public static final CraftTweakerTagRegistry INSTANCE = new CraftTweakerTagRegistry();
    
    private CraftTweakerTagRegistry() {}
    
    private final Map<ResourceKey<? extends Registry<?>>, ITagManager<?>> registeredManagers = new HashMap<>();
    private final Set<ResourceKey<? extends Registry<?>>> customManagers = new HashSet<>();
    private final Set<ResourceKey<? extends Registry<?>>> customManagersView = Collections.unmodifiableSet(customManagers);
    
    public <T> ITagManager<T> addManager(Class<? extends ITagManager<T>> cls) {
        
        ITagManager<T> manager = InstantiationUtil.getOrCreateInstance(cls);
        Objects.requireNonNull(manager, "Error while creating tag manager from class: '" + cls + "'! Make sure it has a default constructor or a public static INSTANCE field!");
        return addManager(manager);
    }
    
    public <T> ITagManager<T> addManager(ITagManager<T> manager) {
        
        registeredManagers.put(manager.resourceKey(), manager);
        if(!manager.getClass().equals(KnownTagManager.class)) {
            customManagers.add(manager.resourceKey());
        }
        return manager;
    }
    
    public List<ITagManager<?>> managers() {
        
        return new ArrayList<>(registeredManagers.values());
    }
    
    public Set<ResourceKey<? extends Registry<?>>> customManagers() {
        
        return customManagersView;
    }
    
    public <T> Optional<ITagManager<T>> findManager(ResourceKey<? extends Registry<T>> key) {
        
        return Optional.ofNullable((ITagManager<T>) registeredManagers.get(key));
    }
    
    public <T> Optional<? extends ITagManager<?>> tagManagerFromFolder(ResourceLocation tagFolder) {
        
        return this.registeredManagers.values()
                .stream()
                .filter(iTagManager -> tagFolder.toString()
                        .equals(ResourceLocation.tryParse(iTagManager.tagFolder()).toString()))
                .findFirst();
    }
    
    public boolean isCustomManager(ResourceKey<? extends Registry<?>> key) {
        
        return customManagers.contains(key);
    }
    
    public boolean isCustomManager(ResourceLocation tagFolder) {
        
        return tagManagerFromFolder(tagFolder).map(manager -> isCustomManager(manager.resourceKey())).orElse(false);
    }
    
    public <T> ITagManager<T> tagManager(ResourceKey<? extends Registry<T>> key) {
        
        return findManager(key).orElseThrow(() -> new RuntimeException("No tag manager found for given key: " + key));
    }
    
    @ZenCodeType.Method
    public <T extends ITagManager<?>> T tagManager(ResourceLocation registryLocation) {
        
        return (T) findManager(ResourceKey.createRegistryKey(registryLocation)).orElseThrow(() -> new RuntimeException("No tag manager found for given location: " + registryLocation));
    }
    
    public void bind(TagManager tagManager) {
        
        this.registeredManagers.clear();
        for(TagManager.LoadResult loadResult : tagManager.getResult()) {
            Optional<? extends Class<?>> taggableElement = CraftTweakerAPI.getRegistry()
                    .getTaggableElementFor(loadResult.key());
            
            taggableElement.ifPresent(it -> this.addManager(CraftTweakerAPI.getRegistry()
                    .getTaggableElementFactory(loadResult.key())
                    .apply(loadResult.key(), it)).bind(loadResult));
        }
    }
    
}
