package com.blamejared.crafttweaker.api.action.tag.unknown;

import com.blamejared.crafttweaker.api.tag.type.UnknownTag;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class ActionUnknownTagClear extends ActionUnknownTag {
    
    public ActionUnknownTagClear(UnknownTag mcTag) {
        
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
