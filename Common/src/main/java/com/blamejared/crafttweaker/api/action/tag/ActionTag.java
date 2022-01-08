package com.blamejared.crafttweaker.api.action.tag;


import com.blamejared.crafttweaker.api.action.base.IRuntimeAction;
import com.blamejared.crafttweaker.api.tag.MCTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import org.apache.logging.log4j.Logger;

public abstract class ActionTag<T> implements IRuntimeAction {
    
    protected final Tag<T> tag;
    protected final MCTag<?> mcTag;
    
    public ActionTag(Tag<T> tag, MCTag<?> mcTag) {
        
        this.tag = tag;
        this.mcTag = mcTag;
    }
    
    public Tag<T> getTag() {
        
        return tag;
    }
    
    public ResourceLocation getId() {
        
        return mcTag.id();
    }
    
    public String getType() {
        
        return mcTag.manager().getTagFolder();
    }
    
    @Override
    public boolean validate(Logger logger) {
        
        if(getTag() == null) {
            logger.error("Tag {} does not exist!", mcTag, new NullPointerException("Internal tag was null!"));
            return false;
        }
        return true;
    }
    
    
}
