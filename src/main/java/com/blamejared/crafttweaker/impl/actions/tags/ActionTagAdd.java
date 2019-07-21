package com.blamejared.crafttweaker.impl.actions.tags;

import net.minecraft.tags.Tag;

import java.util.Arrays;

public class ActionTagAdd<T> extends ActionTagModify<T> {
    
    public ActionTagAdd(Tag<T> tag, T[] values) {
        super(tag, values);
    }
    
    @Override
    public void apply() {
        for(T item : getValues()) {
            tag.getAllElements().add(item);
        }
    }
    
    @Override
    public String describe() {
        return "Adding: " + Arrays.toString(getValues()) + " to tag: " + getTag().getId().toString();
    }
    
}
