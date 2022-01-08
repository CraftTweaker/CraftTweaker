package com.blamejared.crafttweaker.api.tag.registry;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.tag.CrTTagRegistryData")
public final class CrTTagRegistry {
    
    public static final String GLOBAL_NAME = "tags";
    
    @ZenCodeGlobals.Global(GLOBAL_NAME)
    public static final CrTTagRegistry INSTANCE = new CrTTagRegistry(CrTTagRegistryData.INSTANCE);
    
    private final CrTTagRegistryData data;
    
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
    
}
