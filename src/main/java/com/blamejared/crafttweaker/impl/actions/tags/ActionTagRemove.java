package com.blamejared.crafttweaker.impl.actions.tags;

import com.blamejared.crafttweaker.impl.tag.MCTag;
import net.minecraft.tags.ITag;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.List;
import java.util.Set;

public class ActionTagRemove<T extends ForgeRegistryEntry<?>> extends ActionTagModify<T> {
    
    public ActionTagRemove(ITag<T> tag, List<T> values, MCTag<?> mcTag) {
        
        super(tag, values, mcTag);
    }
    
    @Override
    protected void applyTo(List<T> immutableContents, Set<T> contents) {
        
        immutableContents.removeAll(values);
        contents.removeAll(values);
    }
    
    @Override
    public String describe() {
        
        return "Removing: " + describeValues() + " from tag: " + mcTag;
    }
    
}
