package com.blamejared.crafttweaker.api.action.tag;

import com.blamejared.crafttweaker.api.tag.MCTag;
import net.minecraft.tags.Tag;

import java.util.List;
import java.util.Set;

public class ActionTagAdd<T> extends ActionTagModify<T> {
    
    public ActionTagAdd(Tag<T> tag, List<T> values, MCTag<?> mcTag) {
        
        super(tag, values, mcTag);
    }
    
    @Override
    protected void applyTo(List<T> immutableContents, Set<T> contents) {
        
        immutableContents.addAll(values);
        contents.addAll(values);
    }
    
    @Override
    public String describe() {
        
        return "Adding: " + describeValues() + " to tag: " + mcTag;
    }
    
}
