package com.blamejared.crafttweaker.impl.actions.tags;

import com.blamejared.crafttweaker.api.logger.*;
import com.blamejared.crafttweaker.impl.tag.*;
import com.google.common.collect.ImmutableMap;
import net.minecraft.tags.*;
import net.minecraftforge.registries.*;

public class ActionTagCreate<T extends ForgeRegistryEntry<?>> extends ActionTag<T> {
    
    private final ITagCollection<T> collection;
    
    public ActionTagCreate(ITagCollection<T> collection, ITag<T> tag, MCTag<?> theTag) {
        super(tag, theTag);
        this.collection = collection;
    }
    
    @Override
    public void apply() {
        collection.getIDTagMap().put(getId(), tag);
    }
    
    @Override
    public boolean validate(ILogger logger) {
        if(collection.get(getId()) != null) {
            logger.error(getType() + " Tag: " + mcTag + " already exists!");
            return false;
        }
        if(collection.getIDTagMap() instanceof ImmutableMap) {
            logger.error(getType() + " Tag Internal error: TagMap is " + collection.getIDTagMap().getClass().getCanonicalName());
            return false;
        }
        
        return true;
    }
    
    @Override
    public String describe() {
        return "Registering new " + getType() + " tag with name " + mcTag;
    }
}
