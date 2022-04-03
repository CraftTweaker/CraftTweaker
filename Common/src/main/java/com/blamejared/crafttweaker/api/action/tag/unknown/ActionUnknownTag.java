package com.blamejared.crafttweaker.api.action.tag.unknown;


import com.blamejared.crafttweaker.api.action.tag.ActionTag;
import com.blamejared.crafttweaker.api.tag.manager.type.UnknownTagManager;
import com.blamejared.crafttweaker.api.tag.type.UnknownTag;
import net.minecraft.core.Holder;
import net.minecraft.tags.Tag;

public abstract class ActionUnknownTag extends ActionTag<UnknownTag, UnknownTagManager> {
    
    public ActionUnknownTag(UnknownTag mcTag) {
        
        super(mcTag);
    }
    
    public Tag<Holder<?>> tag() {
        
        return mcTag().getInternal();
    }
    
}
