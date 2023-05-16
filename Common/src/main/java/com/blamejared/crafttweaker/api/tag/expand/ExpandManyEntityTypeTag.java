package com.blamejared.crafttweaker.api.tag.expand;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.entity.CTEntityIngredient;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker.api.util.*;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.entity.*;
import org.openzen.zencode.java.ZenCodeType;

import java.util.*;

@ZenRegister
@Document("vanilla/api/tag/ExpandManyEntityTypeTag")
@ZenCodeType.Expansion("crafttweaker.api.util.Many<crafttweaker.api.tag.type.KnownTag<crafttweaker.api.entity.EntityType<crafttweaker.api.entity.Entity>>>")
public class ExpandManyEntityTypeTag {
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public static CTEntityIngredient asEntityIngredient(Many<KnownTag<EntityType<Entity>>> internal) {
        
        return new CTEntityIngredient.EntityTagWithAmountIngredient(GenericUtil.uncheck(internal));
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.OR)
    public static CTEntityIngredient asList(Many<KnownTag<EntityType<Entity>>> internal, CTEntityIngredient other) {
        
        List<CTEntityIngredient> elements = new ArrayList<>();
        elements.add(asEntityIngredient(internal));
        elements.add(other);
        return new CTEntityIngredient.CompoundEntityIngredient(elements);
    }
    
}
