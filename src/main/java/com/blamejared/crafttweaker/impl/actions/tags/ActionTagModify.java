package com.blamejared.crafttweaker.impl.actions.tags;

import com.blamejared.crafttweaker.api.logger.ILogger;
import net.minecraft.tags.Tag;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class ActionTagModify<T extends ForgeRegistryEntry> extends ActionTag<T> {
    
    protected final T[] values;
    
    public ActionTagModify(Tag<T> tag, T[] values) {
        super(tag);
        this.values = values;
    }
    
    public T[] getValues() {
        return values;
    }
    
    @Override
    public boolean validate(ILogger logger) {
        if(getValues() == null) {
            logger.throwingErr("Tag entries cannot be null!", new NullPointerException("Tag entries cannot be null!"));
            return false;
        }
        if(getValues().length ==0){
            logger.throwingErr("Tag entries cannot be empty!", new IndexOutOfBoundsException("Tag entries cannot be empty!"));
        }
        return super.validate(logger);
    }
}
