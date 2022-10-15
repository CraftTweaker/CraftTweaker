package com.blamejared.crafttweaker.api.action.tag.known;

import com.blamejared.crafttweaker.api.tag.type.KnownTag;

public class ActionKnownTagClear<T> extends ActionKnownTag<T> {
    
    public ActionKnownTagClear(KnownTag<T> mcTag) {
        
        super(mcTag);
    }
    
    @Override
    public void apply() {
        
        tag().clear();
    }
    
    @Override
    public String describe() {
        
        return "Clearing all values of tag: " + mcTag();
    }
    
}
