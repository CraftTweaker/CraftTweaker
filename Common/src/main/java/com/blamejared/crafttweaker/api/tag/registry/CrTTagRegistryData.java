package com.blamejared.crafttweaker.api.tag.registry;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.api.tag.manager.TagManagerWrapper;
import com.blamejared.crafttweaker.api.util.InstantiationUtil;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public final class CrTTagRegistryData {
    
    public static final CrTTagRegistryData INSTANCE = new CrTTagRegistryData();
    
    /**
     * Classes that implement {@link ITagManager} themselves.
     * The recommended way since this ensure that the proper classes are used
     */
    private final Map<String, ITagManager<?>> registeredInstances = new HashMap<>();
    
    /**
     * Implementations that were created by CrT on a best-effort basis
     */
    private final Map<String, TagManagerWrapper<?>> syntheticInstances = new HashMap<>();
    
    /**
     * Maps the ZC type -> ITagManager implementation, e.g. MCItemDefinition -> {@link com.blamejared.crafttweaker.api.tag.manager.TagManagerItem}
     */
    private final Map<Class<?>, ITagManager<?>> tagFolderByCrTElementType = new HashMap<>();
    
    private CrTTagRegistryData() {
    
    }
    
    @SuppressWarnings("rawtypes")
    public void addTagImplementationClass(Class<? extends ITagManager> cls) {
        
        final ITagManager manager = InstantiationUtil.getOrCreateInstance(cls);
        if(manager == null) {
            throw new IllegalArgumentException("ITagManager(" + cls.getName() + ") needs to have a public static final instance field or a no-arg constructor");
        }
        
        CraftTweakerAPI.LOGGER.debug("Registering Native ITagManager with TagFolder '{}'", manager.getTagFolder());
        register(manager);
        
    }
    
    public void register(ITagManager<?> tagManager) {
        
        final String tagFolder = tagManager.getTagFolder();
        if(getAllInstances().containsKey(tagFolder)) {
            handleDuplicateTagManager(tagManager, tagFolder);
            return;
        }
        if(tagManager instanceof TagManagerWrapper) {
            syntheticInstances.put(tagFolder, (TagManagerWrapper<?>) tagManager);
        } else {
            registeredInstances.put(tagFolder, tagManager);
        }
        tagFolderByCrTElementType.put(tagManager.getElementClass(), tagManager);
    }
    
    private void handleDuplicateTagManager(ITagManager<?> tagManager, String tagFolder) {
        
        final String message = "There are two tagManagers registered for tagfolder {}! Classes are '{}' and '{}'.";
        final String nameA = tagManager.getClass().getCanonicalName();
        final String nameB = getAllInstances().get(tagFolder).getClass().getCanonicalName();
        CraftTweakerAPI.LOGGER.error(message, tagFolder, nameA, nameB);
    }
    
    
    public boolean hasTagManager(String location) {
        
        return getAllInstances().containsKey(location);
    }
    
    private Map<String, ITagManager<?>> getAllInstances() {
        
        final HashMap<String, ITagManager<?>> result = new HashMap<>(registeredInstances);
        //TODO reimpl
        result.putAll(syntheticInstances);
        
        return result;
    }
    
    /**
     * {@code TagRegistry.get<Item>()}
     */
    <T> ITagManager<T> getForElementType(Class<T> cls) {
        
        return (ITagManager<T>) tagFolderByCrTElementType.get(cls);
    }
    
    public <T extends ITagManager<?>> T getByImplementation(Class<T> cls) {
        //Here it's enough to use registeredInstances, since all syntheticInstances are TagManagerWrapper anyways
        for(ITagManager<?> value : registeredInstances.values()) {
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
        
        final Map<String, ITagManager<?>> allInstances = getAllInstances();
        if(!allInstances.containsKey(tagFolder)) {
            throw new IllegalArgumentException("Could not find registry for name " + tagFolder);
        }
        
        final IScriptLoader loader = null;
        final Class<?> elementClass = allInstances.get(tagFolder).getElementClass();
        final Optional<String> s = CraftTweakerAPI.getRegistry().getZenClassRegistry().getNameFor(loader, elementClass);
        return s.orElseThrow(() -> new IllegalArgumentException("Cannot find ZC type for name " + tagFolder));
    }
    
    public String getImplementationZCTypeFor(String location) {
        //Is only called for custom impls, so registeredInstances is fine.
        //Also, syntheticInstances would not have a name annotation.
        final ITagManager<?> tagManager = registeredInstances.get(location);
        return tagManager.getClass().getAnnotation(ZenCodeType.Name.class).value();
    }
    
    public <T> ITagManager<T> getByTagFolder(String location) {
        
        for(ITagManager<?> value : getAllInstances().values()) {
            if(value.getTagFolder().equals(location)) {
                return (ITagManager<T>) value;
            }
        }
        throw new IllegalArgumentException("No ITagManager with tag folder '" + location + "' is registered");
    }
    
    public Collection<ITagManager<?>> getAll() {
        
        return getAllInstances().values();
    }
    
}
