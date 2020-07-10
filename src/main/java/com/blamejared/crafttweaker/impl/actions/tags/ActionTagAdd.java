package com.blamejared.crafttweaker.impl.actions.tags;

import com.google.common.collect.ImmutableList;
import net.minecraft.tags.ITag;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.*;

public class ActionTagAdd<T extends ForgeRegistryEntry> extends ActionTagModify<T> {
    
    public ActionTagAdd(ITag<T> tag, T[] values, ResourceLocation id) {
        super(tag, values, id);
    }
    
    @Override
    public void apply() {
        if(tag instanceof Tag) {
            List<T> list = new ArrayList<>(((Tag<T>) tag).field_241282_b_);
            Set<T> set = new HashSet<>(((Tag<T>) tag).field_241283_c_);
            List<T> values = Arrays.asList(getValues());
            list.addAll(values);
            set.addAll(values);
            ((Tag<T>) tag).field_241282_b_ = ImmutableList.copyOf(list);
            ((Tag<T>) tag).field_241283_c_ = set;
        } else {
            throw new RuntimeException("Only Tag's are supported right now, can't act on: " + tag);
        }
    }
    
    @Override
    public String describe() {
        return "Adding: " + Arrays.toString(getValues()) + " to tag: " + getId();
    }
    
}
