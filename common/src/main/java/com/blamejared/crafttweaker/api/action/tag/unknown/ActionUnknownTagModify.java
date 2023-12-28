package com.blamejared.crafttweaker.api.action.tag.unknown;

import com.blamejared.crafttweaker.api.tag.type.UnknownTag;
import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class ActionUnknownTagModify extends ActionUnknownTag {
    
    private final List<ResourceLocation> values;
    
    public ActionUnknownTagModify(UnknownTag mcTag, List<ResourceLocation> values) {
        
        super(mcTag);
        this.values = values;
    }
    
    public List<ResourceLocation> values() {
        
        return values;
    }
    
    protected List<Holder<?>> holderValues() {
        
        return values().stream()
                .map(resourceLocation -> makeHolder(Either.right(resourceLocation)))
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean validate(Logger logger) {
        
        if(values() == null) {
            logger.error("Tag entries cannot be null!", new NullPointerException("Tag entries cannot be null!"));
            return false;
        }
        if(values().isEmpty()) {
            logger.error("Tag entries cannot be empty!", new IndexOutOfBoundsException("Tag entries cannot be empty!"));
            return false;
        }
        return super.validate(logger);
    }
    
    public String describeValues() {
        
        return values().stream().map(Objects::toString).collect(Collectors.joining(", ", "[", "]"));
    }
    
}
