package com.blamejared.crafttweaker.impl.actions.tags;

import com.blamejared.crafttweaker.api.actions.IRuntimeAction;
import com.blamejared.crafttweaker.api.logger.ILogger;
import net.minecraft.tags.Tag;

public abstract class ActionTag<T> implements IRuntimeAction {
    
    protected final Tag<T> tag;
    
    public ActionTag(Tag<T> tag) {
        this.tag = tag;
    }
    
    public Tag<T> getTag() {
        return tag;
    }
    
    @Override
    public boolean validate(ILogger logger) {
        if(getTag() == null) {
            logger.throwingErr("Tag cannot be null!", new NullPointerException("Tag cannot be null!"));
            return false;
        }
        return true;
    }
}
