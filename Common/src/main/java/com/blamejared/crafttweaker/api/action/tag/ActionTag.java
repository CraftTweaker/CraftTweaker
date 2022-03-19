package com.blamejared.crafttweaker.api.action.tag;


import com.blamejared.crafttweaker.api.action.base.IRuntimeAction;
import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import org.apache.logging.log4j.Logger;

public abstract class ActionTag<T> implements IRuntimeAction {
    
    private final MCTag<T> mcTag;
    
    public ActionTag(MCTag<T> mcTag) {
        
        this.mcTag = mcTag;
    }
    
    public Tag<Holder<T>> tag() {
        
        return mcTag.getInternal();
    }
    
    public MCTag<T> mcTag() {
        
        return mcTag;
    }
    
    public ResourceLocation id() {
        
        return mcTag.id();
    }
    
    public ITagManager<T> manager() {
        
        return mcTag.manager();
    }
    
    public String getType() {
        
        return mcTag.manager().tagFolder();
    }
    
    protected Holder<T> makeHolder(T object) {
        
        return Services.REGISTRY.makeHolder(manager().resourceKey(), object);
    }
    
    @Override
    public boolean validate(Logger logger) {
        
        if(tag() == null) {
            logger.error("Tag {} does not exist!", mcTag, new NullPointerException("Internal tag was null!"));
            return false;
        }
        return true;
    }
    
    
}
