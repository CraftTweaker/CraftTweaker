package com.blamejared.crafttweaker.natives.recipe.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/recipe/type/ShapelessRecipe")
@NativeTypeRegistration(value = ShapelessRecipe.class, zenCodeName = "crafttweaker.api.recipe.type.ShapelessRecipe")
public class ExpandShapelessRecipe {
    
    /**
     * Gets the ingredients of this recipe as an array that can be passed into a craftingTable.addShapeless method call.
     *
     * @return the ingredients of this recipe as an array that can be passed into a craftingTable.addShapeless method call.
     */
    @ZenCodeType.Method
    public static IIngredient[] getIngredientArray(ShapelessRecipe internal) {
        
        return internal.getIngredients().stream().map(IIngredient::fromIngredient).toArray(IIngredient[]::new);
    }
    
}
