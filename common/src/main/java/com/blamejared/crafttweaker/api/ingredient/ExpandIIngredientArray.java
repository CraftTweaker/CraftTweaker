package com.blamejared.crafttweaker.api.ingredient;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.ListData;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Expansion(value = "crafttweaker.api.ingredient.IIngredient[]")
public class ExpandIIngredientArray {
    
    @ZenCodeType.Caster(implicit = true)
    public static IData asIData(IIngredient[] instance) {
        ListData data = new ListData();
        for (IIngredient ing : instance) {
            data.add(ing.asIData());
        }
        return data;
    }
    
}
