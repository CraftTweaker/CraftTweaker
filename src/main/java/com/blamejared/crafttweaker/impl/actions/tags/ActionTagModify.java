package com.blamejared.crafttweaker.impl.actions.tags;

import com.blamejared.crafttweaker.api.logger.ILogger;
import com.blamejared.crafttweaker.impl.tag.*;
import com.google.common.collect.*;
import net.minecraft.tags.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.*;
import java.util.stream.*;

public abstract class ActionTagModify<T extends ForgeRegistryEntry<?>> extends ActionTag<T> {
    
    protected final List<T> values;
    
    public ActionTagModify(ITag<T> tag, List<T> values, MCTag<?> mcTag) {
        super(tag, mcTag);
        this.values = values;
    }
    
    @Override
    public void apply() {
        if(tag instanceof Tag) {
            List<T> list = new ArrayList<>(((Tag<T>) tag).immutableContents);
            Set<T> set = new HashSet<>(((Tag<T>) tag).contents);
            applyTo(list, set);
            ((Tag<T>) tag).immutableContents = ImmutableList.copyOf(list);
            ((Tag<T>) tag).contents = set;
        } else {
            throw new RuntimeException("Only Tag's are supported right now, can't act on: " + tag);
        }
    }
    
    protected abstract void applyTo(List<T> immutableContents, Set<T> contents);
    
    public List<T> getValues() {
        return values;
    }
    
    @Override
    public boolean validate(ILogger logger) {
        if(getValues() == null) {
            logger.throwingErr("Tag entries cannot be null!", new NullPointerException("Tag entries cannot be null!"));
            return false;
        }
        if(getValues().size() ==0){
            logger.throwingErr("Tag entries cannot be empty!", new IndexOutOfBoundsException("Tag entries cannot be empty!"));
        }
        return super.validate(logger);
    }
    
    public String describeValues() {
        return getValues().stream().map(Objects::toString).collect(Collectors.joining(",", "[", "]"));
    }
}
