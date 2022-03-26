package com.blamejared.crafttweaker.api.action.tag.known;

import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import net.minecraft.tags.Tag;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class ActionKnownTagCreate<T> extends ActionKnownTag<T> {
    
    public ActionKnownTagCreate(KnownTag<T> theTag) {
        
        super(theTag);
    }
    
    @Override
    public void apply() {
    
        manager().addTag(mcTag().id(), new Tag<>(new ArrayList<>()));
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
