package com.blamejared.crafttweaker.api.action.tag.unknown;

import com.blamejared.crafttweaker.api.tag.type.UnknownTag;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class ActionUnknownTagAdd extends ActionUnknownTagModify {
    
    public ActionUnknownTagAdd(UnknownTag mcTag, List<ResourceLocation> values) {
        
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
