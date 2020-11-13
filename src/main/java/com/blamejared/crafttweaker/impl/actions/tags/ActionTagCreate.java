package com.blamejared.crafttweaker.impl.actions.tags;

import com.blamejared.crafttweaker.api.logger.*;
import com.blamejared.crafttweaker.impl.tag.*;
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
            logger.error(getType() + " Tag: " + getId() + " already exists!");
            return false;
        }
        return true;
    }
    
    @Override
    public String describe() {
        return "Registering new " + getType() + " tag with name " + mcTag;
    }
}
