package com.blamejared.crafttweaker.api.action.tag.unknown;

import com.blamejared.crafttweaker.api.tag.type.UnknownTag;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class ActionUnknownTagRemove extends ActionUnknownTagModify {
    
    public ActionUnknownTagRemove(UnknownTag mcTag, List<ResourceLocation> values) {
        
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
