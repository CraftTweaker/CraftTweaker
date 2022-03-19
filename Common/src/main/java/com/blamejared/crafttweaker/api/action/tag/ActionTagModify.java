package com.blamejared.crafttweaker.api.action.tag;

import com.blamejared.crafttweaker.api.tag.MCTag;
import net.minecraft.core.Holder;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class ActionTagModify<T> extends ActionTag<T> {
    
    private final List<T> values;
    
    public ActionTagModify(MCTag<T> mcTag, List<T> values) {
        
        super(mcTag);
        this.values = values;
    }
    
    protected List<Holder<T>> holderValues() {
        
        return values().stream().map(this::makeHolder).collect(Collectors.toList());
    }
    
    public List<T> values() {
        
        return values;
    }
    
    @Override
    public boolean validate(Logger logger) {
        
        if(values() == null) {
            logger.error("Tag entries cannot be null!", new NullPointerException("Tag entries cannot be null!"));
            return false;
        }
        if(values().size() == 0) {
            logger.error("Tag entries cannot be empty!", new IndexOutOfBoundsException("Tag entries cannot be empty!"));
        }
        return super.validate(logger);
    }
    
    public String describeValues() {
        
        return values().stream().map(Objects::toString).collect(Collectors.joining(",", "[", "]"));
    }
    
}
