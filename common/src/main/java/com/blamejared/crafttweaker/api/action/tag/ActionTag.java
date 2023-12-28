package com.blamejared.crafttweaker.api.action.tag;

import com.blamejared.crafttweaker.api.action.base.IRuntimeAction;
import com.blamejared.crafttweaker.api.action.internal.CraftTweakerAction;
import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.platform.Services;
import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.Logger;

public abstract class ActionTag<T extends MCTag, U extends ITagManager<T>> extends CraftTweakerAction implements IRuntimeAction {
    
    private final T mcTag;
    
    public ActionTag(T mcTag) {
        
        this.mcTag = mcTag;
    }
    
    @SuppressWarnings("unchecked")
    public U manager() {
        
        return (U) mcTag().manager();
    }
    
    public String getType() {
        
        return mcTag().manager().tagFolder();
    }
    
    protected <V> Holder<V> makeHolder(Either<V, ResourceLocation> object) {
        
        return Services.REGISTRY.makeHolder(manager().resourceKey(), object);
    }
    
    public ResourceLocation id() {
        
        return mcTag().id();
    }
    
    public T mcTag() {
        
        return mcTag;
    }
    
    @Override
    public boolean validate(Logger logger) {
        
        if(mcTag().getInternal() == null) {
            logger.error("Tag {} does not exist!", mcTag(), new NullPointerException("Internal tag was null!"));
            return false;
        }
        return true;
    }
    
}
