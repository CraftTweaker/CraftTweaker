package com.blamejared.crafttweaker.api.action.tag;

import com.blamejared.crafttweaker.api.tag.MCTag;

import java.util.List;

public class ActionTagAdd<T> extends ActionTagModify<T> {
    
    public ActionTagAdd(MCTag<T> mcTag, List<T> values) {
        
        super(mcTag, values);
    }
    
    @Override
    public void apply() {
        
        tag().getValues().addAll(holderValues());
    }
    
    @Override
    public String describe() {
        
        return "Adding: " + describeValues() + " to tag: " + mcTag();
    }
    
}
