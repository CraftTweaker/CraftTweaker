package com.blamejared.crafttweaker.natives.recipe.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.crafting.ShapedRecipe;
import org.openzen.zencode.java.ZenCodeType;

import java.util.stream.IntStream;

@ZenRegister
@Document("vanilla/api/recipe/type/ShapedRecipe")
@NativeTypeRegistration(value = ShapedRecipe.class, zenCodeName = "crafttweaker.api.recipe.type.ShapedRecipe")
public class ExpandShapedRecipe {
    
    /**
     * Gets the ingredients of this recipe as an array that can be passed into a craftingTable.addShaped method call.
     *
     * @return the ingredients of this recipe as an array that can be passed into a craftingTable.addShaped method call.
     */
    @ZenCodeType.Method
    public static IIngredient[][] getIngredientArray(ShapedRecipe internal) {
        
        return IntStream.range(0, internal.getHeight())
                .mapToObj(y -> IntStream.range(0, internal.getWidth())
                        .mapToObj(x -> internal.getIngredients().get(y * internal.getWidth() + x))
                        .map(IIngredient::fromIngredient).toArray(IIngredient[]::new)).toArray(IIngredient[][]::new);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("width")
    public static int getWidth(ShapedRecipe internal) {
        
        return internal.getWidth();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("height")
    public static int getHeight(ShapedRecipe internal) {
        
        return internal.getHeight();
    }
    
}
