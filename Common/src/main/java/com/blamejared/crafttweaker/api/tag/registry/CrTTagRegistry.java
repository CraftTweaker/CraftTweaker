package com.blamejared.crafttweaker.api.tag.registry;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import net.minecraft.tags.SerializationTags;
import net.minecraft.tags.TagContainer;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.tag.CrTTagRegistryData")
public final class CrTTagRegistry {
    
    public static final String GLOBAL_NAME = "tags";
    
    @ZenCodeGlobals.Global(GLOBAL_NAME)
    public static final CrTTagRegistry INSTANCE = new CrTTagRegistry(CrTTagRegistryData.INSTANCE);
    
    private final CrTTagRegistryData data;
    // Holds the current tag container that should be queried, updated from the tag update package on fabric.
    private Supplier<TagContainer> currentTagContainer = SerializationTags::getInstance;
    
    public CrTTagRegistry(CrTTagRegistryData data) {
        
        this.data = data;
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("allManagers")
    public List<ITagManager<?>> getAllManagers() {
        
        return new ArrayList<>(data.getAll());
    }
    
    @ZenCodeType.Method
    public <T extends CommandStringDisplayable> ITagManager<T> getForElementType(Class<T> cls) {
        
        return data.getForElementType(cls);
    }
    
    @ZenCodeType.Method
    public <T extends ITagManager<?>> T getByImplementation(Class<T> cls) {
        
        return data.getByImplementation(cls);
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Do we need them? They are for getting a ITagManager based on its folder or the registry name
    ///////////////////////////////////////////////////////////////////////////////////////////////
    
    @ZenCodeType.Method
    public <T> ITagManager<T> getByTagFolder(String tagFolder) {
        
        return data.getByTagFolder(tagFolder);
    }
    
    public Supplier<TagContainer> getCurrentTagContainer() {
        
        return currentTagContainer;
    }
    
    public void setCurrentTagContainer(Supplier<TagContainer> currentTagContainer) {
        
        this.currentTagContainer = currentTagContainer;
    }
    
}
