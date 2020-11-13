package com.blamejared.crafttweaker.impl.actions.tags;

import com.blamejared.crafttweaker.impl.tag.*;
import net.minecraft.tags.*;
import net.minecraftforge.registries.*;

import java.util.*;

public class ActionTagRemove<T extends ForgeRegistryEntry<?>> extends ActionTagModify<T> {
    
    public ActionTagRemove(ITag<T> tag, List<T> values, MCTag<?> mcTag) {
        super(tag, values, mcTag);
    }
    
    @Override
    protected void applyTo(List<T> immutableContents, Set<T> contents) {
        immutableContents.removeAll(values);
        contents.removeAll(values);
    }
    
    @Override
    public String describe() {
        return "Removing: " + describeValues() + " from tag: " + mcTag;
    }
    
}
