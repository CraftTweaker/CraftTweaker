package com.blamejared.crafttweaker.natives.recipe.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/recipe/type/AbstractCookingRecipe")
@NativeTypeRegistration(value = AbstractCookingRecipe.class, zenCodeName = "crafttweaker.api.recipe.type.AbstractCookingRecipe")
public class ExpandAbstractCookingRecipe {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("experience")
    public static float getExperience(AbstractCookingRecipe internal) {
        
        return internal.getExperience();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("cookingTime")
    public static int getCookingTime(AbstractCookingRecipe internal) {
        
        return internal.getCookingTime();
    }
    
}
