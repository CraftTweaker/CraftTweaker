package com.blamejared.crafttweaker.api.action.tag.known;

import com.blamejared.crafttweaker.api.tag.type.KnownTag;

import java.util.List;

public class ActionKnownTagClear<T> extends ActionKnownTag<T> {
    
    public ActionKnownTagClear(KnownTag<T> mcTag) {
        
        super(mcTag);
    }
    
    @Override
    public void apply() {
        
        tag().getValues().clear();
    }
    
    @Override
    public String describe() {
        
        return "Clearing all values of tag: " + mcTag();
    }
    
}
