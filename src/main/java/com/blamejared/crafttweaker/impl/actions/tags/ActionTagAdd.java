package com.blamejared.crafttweaker.impl.actions.tags;

import com.blamejared.crafttweaker.impl.tag.*;
import net.minecraft.tags.ITag;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.*;

public class ActionTagAdd<T extends ForgeRegistryEntry<?>> extends ActionTagModify<T> {
    
    public ActionTagAdd(ITag<T> tag, List<T> values, MCTag<?> mcTag) {
        super(tag, values, mcTag);
    }
    
    @Override
    protected void applyTo(List<T> immutableContents, Set<T> contents) {
        immutableContents.addAll(values);
        contents.addAll(values);
    }
    
    @Override
    public String describe() {
        return "Adding: " + describeValues() + " to tag: " + mcTag;
    }
    
}
