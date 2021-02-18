package com.blamejared.crafttweaker.api.item;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.JSONConverter;
import com.blamejared.crafttweaker.impl.data.IntData;
import com.blamejared.crafttweaker.impl.data.MapData;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Consists of an ingredient and an amount.
 */
@ZenRegister
@Document("vanilla/api/items/IIngredientWithAmount")
@ZenCodeType.Name("crafttweaker.api.item.IIngredientWithAmount")
public interface IIngredientWithAmount extends CommandStringDisplayable {
    
    /**
     * The backing ingredient
     */
    @ZenCodeType.Getter("ingredient")
    IIngredient getIngredient();
    
    /**
     * Gets the amount of Items in the ItemStack
     *
     * @return ItemStack's amount
     */
    @ZenCodeType.Getter("amount")
    int getAmount();
    
}
