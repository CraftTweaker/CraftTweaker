package com.blamejared.crafttweaker.api.action.tag;

import com.blamejared.crafttweaker.api.tag.MCTag;

import java.util.List;

public class ActionTagRemove<T> extends ActionTagModify<T> {
    
    public ActionTagRemove(MCTag<T> mcTag, List<T> values) {
        
        super(mcTag, values);
    }
    
    @Override
    public void apply() {
        
        tag().getValues().removeAll(holderValues());
    }
    
    @Override
    public String describe() {
        
        return "Removing: " + describeValues() + " from tag: " + mcTag();
    }
    
}
