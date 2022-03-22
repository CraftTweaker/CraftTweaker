package com.blamejared.crafttweaker.api.action.tag.unknown;

import com.blamejared.crafttweaker.api.tag.type.UnknownTag;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ActionUnknownTagCreate extends ActionUnknownTag {
    
    private final List<ResourceLocation> contents;
    
    public ActionUnknownTagCreate(UnknownTag theTag, List<ResourceLocation> contents) {
        
        super(theTag);
        this.contents = contents;
    }
    
    @Override
    public void apply() {
    
        Tag<Holder<?>> tag = new Tag<>(new ArrayList<>());
        manager().addTag(mcTag().id(), GenericUtil.uncheck(tag));
        tag().getValues().addAll(contents.stream().map(resourceLocation -> makeHolder(Either.right(resourceLocation))).toList());
        manager().addTag(id(), GenericUtil.uncheck(tag()));
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
