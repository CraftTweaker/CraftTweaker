package com.blamejared.crafttweaker.natives.recipe.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.IngredientConverter;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.helper.AccessibleElementsProvider;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.stream.Collectors;

@ZenRegister
@Document("vanilla/api/recipe/type/Recipe")
@NativeTypeRegistration(value = Recipe.class, zenCodeName = "crafttweaker.api.recipe.type.Recipe")
//@NativeMethod(name = "getResultItem", parameters = {}, getterName = "resultItem")
public class ExpandRecipe {
    
    @ZenCodeType.Method
    public static boolean canCraftInDimensions(Recipe internal, int var1, int var2) {
        
        return internal.canCraftInDimensions(var1, var2);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("ingredients")
    public static List<IIngredient> getIngredients(Recipe internal) {
        
        NonNullList<Ingredient> ingredients = internal.getIngredients();
        return ingredients.stream().map(IngredientConverter::fromIngredient).collect(Collectors.toList());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isSpecial")
    public static boolean isSpecial(Recipe internal) {
        
        return internal.isSpecial();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("group")
    public static String getGroup(Recipe internal) {
        
        return internal.getGroup();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("toastSymbol")
    public static ItemStack getToastSymbol(Recipe internal) {
        
        return internal.getToastSymbol();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isIncomplete")
    public static boolean isIncomplete(Recipe internal) {
        
        return internal.isIncomplete();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("resultItem")
    public static IItemStack getResultItem(Recipe internal) {
        
        return IItemStack.of(AccessibleElementsProvider.get().registryAccess(internal::getResultItem));
    }
    
}
