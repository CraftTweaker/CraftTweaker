package com.blamejared.crafttweaker.api.recipe.manager;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.recipe.RecipeManagerWrapper")
@Document("vanilla/api/recipe/manager/RecipeManagerWrapper")
public class RecipeManagerWrapper implements IRecipeManager<Recipe<?>> {
    
    private final RecipeType<Recipe<?>> recipeType;
    
    public RecipeManagerWrapper(RecipeType<Recipe<?>> recipeType) {
        
        this.recipeType = recipeType;
    }
    
    public static RecipeManagerWrapper makeOrNull(final RecipeType<Recipe<?>> recipeType) {
        
        return recipeType == null ? null : new RecipeManagerWrapper(recipeType);
    }
    
    @Override
    public RecipeType<Recipe<?>> getRecipeType() {
        
        return recipeType;
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        
        RecipeManagerWrapper that = (RecipeManagerWrapper) o;
        
        return recipeType.equals(that.recipeType);
    }
    
    @Override
    public int hashCode() {
        
        return recipeType.hashCode();
    }
    
    
}
