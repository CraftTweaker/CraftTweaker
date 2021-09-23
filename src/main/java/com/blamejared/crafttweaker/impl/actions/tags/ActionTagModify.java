package com.blamejared.crafttweaker.impl.actions.tags;

import com.blamejared.crafttweaker.api.logger.ILogger;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.google.common.collect.ImmutableList;
import net.minecraft.tags.ITag;
import net.minecraft.tags.Tag;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
            ((Tag<T>) tag).contentsClassType = Tag.getContentsClass(set);
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
        if(getValues().size() == 0) {
            logger.throwingErr("Tag entries cannot be empty!", new IndexOutOfBoundsException("Tag entries cannot be empty!"));
        }
        return super.validate(logger);
    }
    
    public String describeValues() {
        
        return getValues().stream().map(Objects::toString).collect(Collectors.joining(",", "[", "]"));
    }
    
}
