package com.blamejared.crafttweaker.api.action.tag.known;


import com.blamejared.crafttweaker.api.action.tag.ActionTag;
import com.blamejared.crafttweaker.api.tag.manager.type.KnownTagManager;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import net.minecraft.core.Holder;

import java.util.Collection;

public abstract class ActionKnownTag<T> extends ActionTag<KnownTag<T>, KnownTagManager<T>> {
    
    public ActionKnownTag(KnownTag<T> mcTag) {
        super(mcTag);
    }
    
    public Collection<Holder<T>> tag() {
        
        //TODO 1.19 confirm
        return GenericUtil.uncheck(mcTag().getInternal());
    }
    
}
