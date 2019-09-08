package com.blamejared.crafttweaker.impl.actions.tags;

import net.minecraft.tags.Tag;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.Arrays;

public class ActionTagRemove<T extends ForgeRegistryEntry> extends ActionTagModify<T> {
    
    public ActionTagRemove(Tag<T> tag, T[] values) {
        super(tag, values);
    }
    
    @Override
    public void apply() {
        for(T item : getValues()) {
            tag.getAllElements().remove(item);
            tag.getEntries().remove(new Tag.TagEntry<T>(item.getRegistryName()));
        }
    }
    
    @Override
    public String describe() {
        return "Removing: " + Arrays.toString(getValues()) + " from tag: " + getTag().getId().toString();
    }
    
}
