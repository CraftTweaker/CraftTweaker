package com.blamejared.crafttweaker.impl.tag.registry;

import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.brackets.*;
import com.blamejared.crafttweaker.api.util.*;
import com.blamejared.crafttweaker.api.zencode.impl.registry.wrapper.*;
import com.blamejared.crafttweaker.impl.tag.manager.*;
import net.minecraft.util.*;
import net.minecraftforge.common.*;
import net.minecraftforge.registries.*;
import org.openzen.zencode.java.*;

import java.lang.invoke.*;
import java.util.*;


public final class CrTTagRegistryData {
    
    public static final CrTTagRegistryData INSTANCE = new CrTTagRegistryData();
    
    /**
     * Classes that implement {@link TagManager} themselves.
     * The recommended way since this ensure that the proper classes are used
     */
    private final Map<String, TagManager<?>> registeredInstances = new HashMap<>();
    
    /**
     * Implementations that were created by CrT on a best-effort basis
     */
    private final Map<String, TagManagerWrapper<?>> syntheticInstances = new HashMap<>();
    
    /**
     * Maps the ZC type -> TagManager implementation, e.g. ExpandItem -> {@link TagManagerItem}
     */
    private final Map<Class<?>, TagManager<?>> tagFolderByCrTElementType = new HashMap<>();
    
    private CrTTagRegistryData() {
    }
    
    @SuppressWarnings("rawtypes")
    public void addTagImplementationClass(Class<? extends TagManager> cls) {
        final TagManager manager = InstantiationUtil.getOrCreateInstance(cls);
        if(manager == null) {
            throw new IllegalArgumentException("TagManagers need to have a public static final instance field or a no-arg constructor");
        }
        
        CraftTweakerAPI.logDebug("Registering Native TagManager with TagFolder '%s'", manager.getTagFolder());
        register(manager);
        
    }
    
    private void register(TagManager<?> tagManager) {
        final String tagFolder = tagManager.getTagFolder();
        if(getAllInstances().containsKey(tagFolder)) {
            final String message = "There are two tagManagers registered for tagfolder %s! Classes are '%s' and '%s'.";
            final String nameA = tagManager.getClass().getCanonicalName();
            final String nameB = getAllInstances().get(tagFolder).getClass().getCanonicalName();
            CraftTweakerAPI.logError(message, nameA, nameB);
            return;
        }
        
        if(tagManager instanceof TagManagerWrapper) {
            syntheticInstances.put(tagFolder, (TagManagerWrapper<?>) tagManager);
        } else {
            registeredInstances.put(tagFolder, tagManager);
        }
        tagFolderByCrTElementType.put(tagManager.getElementClass(), tagManager);
    }
    
    public void registerForgeTags() {
        final RegistryManager registryManager = RegistryManager.ACTIVE;
        for(final ResourceLocation key : ForgeTagHandler.getCustomTagTypeNames()) {
            if(registryManager.getRegistry(key) == null) {
                CraftTweakerAPI.logWarning("Unsupported TagCollection without registry: " + key);
                continue;
            }
            
            final ForgeRegistry<?> registry = registryManager.getRegistry(key);
            String tagFolder = registry.getTagFolder();
            if(tagFolder == null) {
                if(key.getNamespace().equals("minecraft")) {
                    tagFolder = key.getPath();
                } else {
                    CraftTweakerAPI.logWarning("Could not find tagFolder for registry '%s'", key);
                }
            }
            
            if(hasTagManager(tagFolder)) {
                //We already have a custom TagManager for this.
                continue;
            }
            CraftTweakerAPI.logDebug("Creating Wrapper TagManager for type '%s' with tag folder '%s'", key, tagFolder);
            registerTagManagerFromRegistry(key, registry);
        }
    }
    
    
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void registerTagManagerFromRegistry(ResourceLocation name, ForgeRegistry<?> registry) {
        final WrapperRegistry wrapperRegistry = CraftTweakerRegistry.getWrapperRegistry();
        final WrapperRegistryEntry entry = wrapperRegistry.getEntryFor(registry.getRegistrySuperType());
        if(entry == null) {
            CraftTweakerAPI.logDebug("Could not register synthetic TagManager for name '%s' and folder '%s'. No ZenWrapper found.", name, registry.getTagFolder());
            return;
        }
        
        final MethodHandle wrapperHandle = entry.getWrapperHandle();
        if(wrapperHandle == null) {
            CraftTweakerAPI.logDebug("Could not register synthetic TagManager for name '%s' and folder '%s'. No Wrapper MethodHandle found.", name, registry.getTagFolder());
            return;
        }
        
        final MethodHandle unwrapperHandle = entry.getUnwrapperHandle();
        if(unwrapperHandle == null) {
            CraftTweakerAPI.logDebug("Could not register synthetic TagManager for name '%s' and folder '%s'. No Unwrapper MethodHandle found.", name, registry.getTagFolder());
            return;
        }
        
        register(new TagManagerWrapper(entry.getWrapperClass(), name, wrapperHandle, unwrapperHandle));
    }
    
    
    public boolean hasTagManager(String location) {
        return getAllInstances().containsKey(location);
    }
    
    private Map<String, TagManager<?>> getAllInstances() {
        final HashMap<String, TagManager<?>> result = new HashMap<>(registeredInstances);
        result.putAll(syntheticInstances);
        
        return result;
    }
    
    /**
     * {@code TagRegistry.get<ExpandItem>()}
     */
    @SuppressWarnings({"unchecked"})
    <T extends CommandStringDisplayable> TagManager<T> getForElementType(Class<T> cls) {
        return (TagManager<T>) tagFolderByCrTElementType.get(cls);
    }
    
    /**
     * {@code TagRegistry.get<ExpandItem>("minecraft:item")}
     */
    <T extends CommandStringDisplayable> TagManager<T> getForRegistry(ResourceLocation location) {
        @SuppressWarnings("rawtypes") final ForgeRegistry registry = RegistryManager.ACTIVE.getRegistry(location);
        if(registry == null) {
            throw new IllegalArgumentException("Unknown registry name: " + location);
        }
        
        return getByTagFolder(registry.getTagFolder());
    }
    
    public <T extends TagManager<?>> T getByImplementation(Class<T> cls) {
        //Here it's enough to use registeredInstances, since all syntheticInstances are TagManagerWrapper anyways
        for(TagManager<?> value : registeredInstances.values()) {
            if(cls.isInstance(value)) {
                return cls.cast(value);
            }
        }
        throw new IllegalArgumentException("Unknown tag implementation name: " + cls);
    }
    
    public boolean isSynthetic(String tagFolder) {
        return syntheticInstances.containsKey(tagFolder);
    }
    
    public String getElementZCTypeFor(String tagFolder) {
        
        if(!syntheticInstances.containsKey(tagFolder)) {
            throw new IllegalArgumentException("Could not find registry for name " + tagFolder);
        }
        
        final Class<?> elementClass = syntheticInstances.get(tagFolder).getElementClass();
        final WrapperRegistry wrapperRegistry = CraftTweakerRegistry.getWrapperRegistry();
        final WrapperRegistryEntry wrapperEntry = wrapperRegistry.getEntryFor(elementClass);
        if(wrapperEntry == null) {
            throw new IllegalArgumentException("Could not find element type for " + tagFolder);
        }
        return wrapperEntry.getWrapperClassZCName();
    }
    
    public String getImplementationZCTypeFor(String location) {
        //Is only called for custom impls, so registeredInstances is fine.
        //Also, syntheticInstances would not have a name annotation.
        final TagManager<?> tagManager = registeredInstances.get(location);
        return tagManager.getClass().getAnnotation(ZenCodeType.Name.class).value();
    }
    
    @SuppressWarnings("unchecked")
    public <T extends CommandStringDisplayable> TagManager<T> getByTagFolder(String location) {
        for(TagManager<?> value : getAllInstances().values()) {
            if(value.getTagFolder().equals(location)) {
                return (TagManager<T>) value;
            }
        }
        throw new IllegalArgumentException("No TagManager with tag folder " + location + " is registered");
    }
    
    public Collection<TagManager<?>> getAll() {
        return getAllInstances().values();
    }
}
