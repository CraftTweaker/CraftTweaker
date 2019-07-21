package com.blamejared.crafttweaker.impl.actions.tags;

import net.minecraft.tags.Tag;

import java.util.Arrays;

public class ActionTagRemove<T> extends ActionTagModify<T> {
    
    public ActionTagRemove(Tag<T> tag, T[] values) {
        super(tag, values);
    }
    
    @Override
    public void apply() {
        for(T item : getValues()) {
            tag.getAllElements().remove(item);
        }
    }
    
    @Override
    public String describe() {
        return "Removing: " + Arrays.toString(getValues()) + " from tag: " + getTag().getId().toString();
    }
    
}
