package com.blamejared.crafttweaker.impl.tag.expansions;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.entity.CTEntityIngredient;
import com.blamejared.crafttweaker.impl.entity.MCEntityType;
import com.blamejared.crafttweaker.impl.tag.MCTagWithAmount;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;

@ZenRegister
@Document("vanilla/api/tags/ExpandEntityTagWithAmount")
@ZenCodeType.Expansion("crafttweaker.api.tag.MCTagWithAmount<crafttweaker.api.entity.MCEntityType>")
public class ExpandEntityTagWithAmount {
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public static CTEntityIngredient asIngredient(MCTagWithAmount<MCEntityType> _this) {
        
        return new CTEntityIngredient.EntityTagWithAmountIngredient(_this);
    }
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.OR)
    public static CTEntityIngredient asList(MCTagWithAmount<MCEntityType> _this, CTEntityIngredient other) {
        
        List<CTEntityIngredient> elements = new ArrayList<>();
        elements.add(asIngredient(_this));
        elements.add(other);
        return new CTEntityIngredient.CompoundEntityIngredient(elements);
    }
    
}
