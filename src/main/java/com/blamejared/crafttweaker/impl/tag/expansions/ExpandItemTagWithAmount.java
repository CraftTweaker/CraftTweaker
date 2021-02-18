package com.blamejared.crafttweaker.impl.tag.expansions;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IIngredientWithAmount;
import com.blamejared.crafttweaker.impl.ingredients.ExpandIIngredient;
import com.blamejared.crafttweaker.impl.tag.MCTagWithAmount;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.item.Item;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/tags/ExpandItemTagWithAmount")
@ZenCodeType.Expansion("crafttweaker.api.tag.MCTagWithAmount<crafttweaker.api.item.MCItemDefinition>")
public class ExpandItemTagWithAmount {
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public static IIngredientWithAmount asIngredient(MCTagWithAmount<Item> _this) {
        
        final IIngredient iIngredient = ExpandItemTag.asIIngredient(_this.getTag());
        return ExpandIIngredient.mul(iIngredient, _this.getAmount());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public static IData asIData(MCTagWithAmount<Item> _this) {
        
        return ExpandItemTagWithAmount.asIngredient(_this).asIData();
    }
    
}
