package com.blamejared.crafttweaker.impl.actions.tags;

import com.google.common.collect.ImmutableList;
import net.minecraft.tags.ITag;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.*;

public class ActionTagRemove<T extends ForgeRegistryEntry> extends ActionTagModify<T> {
    
    public ActionTagRemove(ITag<T> tag, T[] values, ResourceLocation id) {
        super(tag, values, id);
    }
    
    @Override
    public void apply() {
        if(tag instanceof Tag) {
            List<T> list = new ArrayList<>(((Tag<T>) tag).immutableContents);
            Set<T> set = new HashSet<>(((Tag<T>) tag).contents);
            List<T> values = Arrays.asList(getValues());
            list.removeAll(values);
            set.removeAll(values);
            ((Tag<T>) tag).immutableContents = ImmutableList.copyOf(list);
            ((Tag<T>) tag).contents = set;
        } else {
            throw new RuntimeException("Only Tag's are supported right now, can't act on: " + tag);
        }
    }
    
    @Override
    public String describe() {
        return "Removing: " + Arrays.toString(getValues()) + " from tag: " + getId();
    }
    
}
