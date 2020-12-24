package com.blamejared.crafttweaker.impl.tag.registry;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.crafttweaker.impl.tag.manager.TagManager;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.tags.CrTTagRegistryData")
public final class CrTTagRegistry {
    
    public static final String GLOBAL_NAME = "tags";
    
    
    @ZenCodeGlobals.Global(GLOBAL_NAME)
    public static final CrTTagRegistry instance = new CrTTagRegistry(CrTTagRegistryData.INSTANCE);
    
    private final CrTTagRegistryData data;
    
    public CrTTagRegistry(CrTTagRegistryData data) {
        this.data = data;
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("allManagers")
    public List<TagManager<?>> getAllManagers() {
        return new ArrayList<>(data.getAll());
    }
    
    @ZenCodeType.Method
    public <T extends CommandStringDisplayable> TagManager<T> getForElementType(Class<T> cls) {
        return data.getForElementType(cls);
    }
    
    @ZenCodeType.Method
    public <T extends TagManager<?>> T getByImplementation(Class<T> cls) {
        return data.getByImplementation(cls);
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////
    // Do we need them? They are for getting a TagManager based on its folder or the registry name
    ///////////////////////////////////////////////////////////////////////////////////////////////
    
    @ZenCodeType.Method
    public <T> TagManager<T> getForRegistry(ResourceLocation location) {
        return data.getForRegistry(location);
    }
    
    @ZenCodeType.Method
    public <T> TagManager<T> getByTagFolder(String tagFolder) {
        return data.getByTagFolder(tagFolder);
    }
}
