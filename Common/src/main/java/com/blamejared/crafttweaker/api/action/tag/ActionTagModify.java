package com.blamejared.crafttweaker.api.action.tag;

import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker.mixin.common.access.tag.AccessSetTag;
import com.google.common.collect.ImmutableList;
import net.minecraft.tags.SetTag;
import net.minecraft.tags.Tag;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class ActionTagModify<T> extends ActionTag<T> {
    
    protected final List<T> values;
    
    public ActionTagModify(Tag<T> tag, List<T> values, MCTag<?> mcTag) {
        
        super(tag, mcTag);
        this.values = values;
    }
    
    @Override
    public void apply() {
        
        if(tag instanceof SetTag setTag) {
            
            List<T> list = new ArrayList<>(((AccessSetTag) setTag).getValuesList());
            Set<T> set = new HashSet<>(((AccessSetTag) setTag).getValues());
            applyTo(list, set);
            ((AccessSetTag) setTag).setValuesList(ImmutableList.copyOf(list));
            ((AccessSetTag) setTag).setValues(set);
            ((AccessSetTag) setTag).setClosestCommonSuperType(AccessSetTag.findCommonSuperClass(set));
        } else {
            throw new RuntimeException("Only SetTag's are supported right now, can't act on: " + tag);
        }
    }
    
    protected abstract void applyTo(List<T> immutableContents, Set<T> contents);
    
    public List<T> getValues() {
        
        return values;
    }
    
    @Override
    public boolean validate(Logger logger) {
        
        if(getValues() == null) {
            logger.error("Tag entries cannot be null!", new NullPointerException("Tag entries cannot be null!"));
            return false;
        }
        if(getValues().size() == 0) {
            logger.error("Tag entries cannot be empty!", new IndexOutOfBoundsException("Tag entries cannot be empty!"));
        }
        return super.validate(logger);
    }
    
    public String describeValues() {
        
        return getValues().stream().map(Objects::toString).collect(Collectors.joining(",", "[", "]"));
    }
    
}
