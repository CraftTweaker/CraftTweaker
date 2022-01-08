package com.blamejared.crafttweaker.api.action.tag;

import com.blamejared.crafttweaker.api.tag.MCTag;
import net.minecraft.tags.Tag;

import java.util.List;
import java.util.Set;

public class ActionTagRemove<T> extends ActionTagModify<T> {
    
    public ActionTagRemove(Tag<T> tag, List<T> values, MCTag<?> mcTag) {
        
        super(tag, values, mcTag);
    }
    
    @Override
    protected void applyTo(List<T> immutableContents, Set<T> contents) {
        
        immutableContents.removeAll(values);
        values.forEach(contents::remove);
    }
    
    @Override
    public String describe() {
        
        return "Removing: " + describeValues() + " from tag: " + mcTag;
    }
    
}
