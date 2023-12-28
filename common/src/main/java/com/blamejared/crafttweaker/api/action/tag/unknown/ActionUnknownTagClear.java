package com.blamejared.crafttweaker.api.action.tag.unknown;

import com.blamejared.crafttweaker.api.tag.type.UnknownTag;

public class ActionUnknownTagClear extends ActionUnknownTag {
    
    public ActionUnknownTagClear(UnknownTag mcTag) {
        
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
