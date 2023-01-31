package com.blamejared.crafttweaker.api.item;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.IntData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.base.IData;
import com.blamejared.crafttweaker.api.ingredient.IIngredientWithAmount;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/item/ExpandIIngredientWithAmount")
@ZenCodeType.Expansion("crafttweaker.api.ingredient.IIngredientWithAmount")
public class ExpandIIngredientWithAmount {
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public static IData asIData(IIngredientWithAmount internal) {
        MapData data = internal.getIngredient().asMapData();
        data.put("count", new IntData(internal.getAmount()));
        return data;
    }
    
}
