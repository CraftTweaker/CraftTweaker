package com.blamejared.crafttweaker.api.recipe.type;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.MirrorAxis;
import com.blamejared.crafttweaker.api.recipe.func.RecipeFunctionMatrix;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraftforge.common.crafting.IShapedRecipe;
import javax.annotation.Nullable;

// Required so we can implement IShapedRecipe
public class CTShapedRecipe extends CTShapedRecipeBase implements IShapedRecipe<CraftingContainer> {
    
    public CTShapedRecipe(String name, IItemStack output, IIngredient[][] ingredients, MirrorAxis mirrorAxis, @Nullable RecipeFunctionMatrix function) {
        
        super(name, output, ingredients, mirrorAxis, function);
    }
    
}
