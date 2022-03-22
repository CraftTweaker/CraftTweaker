package com.blamejared.crafttweaker.api.action.tag.known;

import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.tags.Tag;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ActionKnownTagCreate<T> extends ActionKnownTag<T> {
    
    private final List<T> contents;
    
    public ActionKnownTagCreate(KnownTag<T> theTag, List<T> contents) {
        
        super(theTag);
        this.contents = contents;
    }
    
    @Override
    public void apply() {
    
        Tag<Holder<T>> tag = new Tag<>(new ArrayList<>());
        manager().addTag(mcTag().id(), tag);
        tag().getValues().addAll(contents.stream().map(t -> makeHolder(Either.left(t))).toList());
        manager().addTag(id(), tag());
    }
    
    @Override
    public boolean validate(Logger logger) {
        
        if(manager().exists(id())) {
            logger.error(getType() + " Tag: " + mcTag() + " already exists!");
            return false;
        }
        
        return true;
    }
    
    @Override
    public String describe() {
        
        return "Registering new " + getType() + " tag with name " + mcTag();
    }
    
}
