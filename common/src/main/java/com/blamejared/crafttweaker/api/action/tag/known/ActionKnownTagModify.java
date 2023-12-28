package com.blamejared.crafttweaker.api.action.tag.known;

import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker.platform.Services;
import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class ActionKnownTagModify<T> extends ActionKnownTag<T> {
    
    private final List<T> values;
    
    public ActionKnownTagModify(KnownTag<T> mcTag, List<T> values) {
        
        super(mcTag);
        this.values = values;
    }
    
    protected List<Holder<T>> holderValues() {
        
        return values().stream().map(t -> makeHolder(Either.left(t))).collect(Collectors.toList());
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
        if(values().isEmpty()) {
            logger.error("Tag entries cannot be empty!", new IndexOutOfBoundsException("Tag entries cannot be empty!"));
            return false;
        }
        return super.validate(logger);
    }
    
    public String describeValues() {
        
        return values().stream().map(it -> Services.REGISTRY.maybeGetRegistryKey(it).map(ResourceLocation::toString).orElseGet(() -> Objects.toString(it))).collect(Collectors.joining(", ", "[", "]"));
    }
    
}
