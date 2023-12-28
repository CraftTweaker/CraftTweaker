package com.blamejared.crafttweaker.api.action.tag.known;

import com.blamejared.crafttweaker.api.tag.type.KnownTag;

import java.util.List;

public class ActionKnownTagAdd<T> extends ActionKnownTagModify<T> {
    
    public ActionKnownTagAdd(KnownTag<T> mcTag, List<T> values) {
        
        super(mcTag, values);
    }
    
    @Override
    public void apply() {
        
        tag().addAll(holderValues());
    }
    
    @Override
    public String describe() {
        
        return "Adding: " + describeValues() + " to tag: " + mcTag();
    }
    
}
