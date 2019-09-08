package com.blamejared.crafttweaker.impl.actions.tags;

import net.minecraft.tags.Tag;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.Arrays;

public class ActionTagAdd<T extends ForgeRegistryEntry> extends ActionTagModify<T> {
    
    public ActionTagAdd(Tag<T> tag, T[] values) {
        super(tag, values);
    }
    
    @Override
    public void apply() {
        for(T item : getValues()) {
            tag.getAllElements().add(item);
            tag.getEntries().add(new Tag.TagEntry<T>(item.getRegistryName()));
        }
    }
    
    @Override
    public String describe() {
        return "Adding: " + Arrays.toString(getValues()) + " to tag: " + getTag().getId().toString();
    }
    
}
