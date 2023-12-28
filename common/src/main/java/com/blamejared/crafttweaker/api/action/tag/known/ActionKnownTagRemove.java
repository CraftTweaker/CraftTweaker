package com.blamejared.crafttweaker.api.action.tag.known;

import com.blamejared.crafttweaker.api.tag.type.KnownTag;

import java.util.List;

public class ActionKnownTagRemove<T> extends ActionKnownTagModify<T> {
    
    public ActionKnownTagRemove(KnownTag<T> mcTag, List<T> values) {
        
        super(mcTag, values);
    }
    
    @Override
    public void apply() {
        
        tag().removeAll(holderValues());
    }
    
    @Override
    public String describe() {
        
        return "Removing: " + describeValues() + " from tag: " + mcTag();
    }
    
}
