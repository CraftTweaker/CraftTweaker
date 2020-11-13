package com.blamejared.crafttweaker.impl.actions.tags;

import com.blamejared.crafttweaker.api.actions.IRuntimeAction;
import com.blamejared.crafttweaker.api.logger.ILogger;
import com.blamejared.crafttweaker.impl.tag.*;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class ActionTag<T extends ForgeRegistryEntry<?>> implements IRuntimeAction {
    
    protected final ITag<T> tag;
    protected final MCTag<?> mcTag;
    
    public ActionTag(ITag<T> tag, MCTag<?> mcTag) {
        this.tag = tag;
        this.mcTag = mcTag;
    }
    
    public ITag<T> getTag() {
        return tag;
    }
    
    public ResourceLocation getId() {
        return mcTag.getIdInternal();
    }
    
    public String getType(){
        return mcTag.getManager().getTagFolder();
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
