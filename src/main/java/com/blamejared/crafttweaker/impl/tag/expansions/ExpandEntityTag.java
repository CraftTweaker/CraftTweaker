package com.blamejared.crafttweaker.impl.tag.expansions;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.entity.CTEntityIngredient;
import com.blamejared.crafttweaker.impl.entity.MCEntityType;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.blamejared.crafttweaker.impl.tag.MCTagWithAmount;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;

@ZenRegister
@Document("vanilla/api/tags/ExpandEntityTag")
@ZenCodeType.Expansion("crafttweaker.api.tag.MCTag<crafttweaker.api.entity.MCEntityType>")
public class ExpandEntityTag {
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public static CTEntityIngredient asIngredient(MCTag<MCEntityType> _this) {
        
        return new CTEntityIngredient.EntityTagWithAmountIngredient(new MCTagWithAmount<>(_this, 1));
    }
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.OR)
    public static CTEntityIngredient asList(MCTag<MCEntityType> _this, CTEntityIngredient other) {
        
        List<CTEntityIngredient> elements = new ArrayList<>();
        elements.add(asIngredient(_this));
        elements.add(other);
        return new CTEntityIngredient.CompoundEntityIngredient(elements);
    }
    
}
