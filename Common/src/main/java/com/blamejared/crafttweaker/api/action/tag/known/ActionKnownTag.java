package com.blamejared.crafttweaker.api.action.tag.known;


import com.blamejared.crafttweaker.api.action.tag.ActionTag;
import com.blamejared.crafttweaker.api.tag.manager.type.KnownTagManager;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import net.minecraft.core.Holder;
import net.minecraft.tags.Tag;

public abstract class ActionKnownTag<T> extends ActionTag<KnownTag<T>, KnownTagManager<T>> {
    
    public ActionKnownTag(KnownTag<T> mcTag) {
        super(mcTag);
    }
    
    public Tag<Holder<T>> tag() {
        
        return mcTag().getInternal();
    }
    
}
