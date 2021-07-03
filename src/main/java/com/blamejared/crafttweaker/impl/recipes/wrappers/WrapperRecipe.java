package com.blamejared.crafttweaker.impl.recipes.wrappers;

import com.blamejared.crafttweaker.api.CraftTweakerRegistry;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.impl.brackets.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.crafttweaker.impl.recipes.CTRecipeShapeless;
import com.blamejared.crafttweaker.impl.recipes.Replacer;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.IShapedRecipe;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ZenRegister
@Document("vanilla/api/recipe/WrapperRecipe")
@ZenCodeType.Name("crafttweaker.api.recipes.WrapperRecipe")
public class WrapperRecipe implements CommandStringDisplayable {
    
    private final IRecipe<?> recipe;
    
    public WrapperRecipe(@Nonnull IRecipe<?> recipe) {
        this.recipe = Objects.requireNonNull(recipe);
    }
    
    @ZenCodeType.Getter("id")
    public ResourceLocation getId() {
        return getRecipe().getId();
    }
    
    @ZenCodeType.Getter("group")
    public String getGroup() {
        return getRecipe().getGroup();
    }
    
    @ZenCodeType.Getter("output")
    public IItemStack getOutput() {
        return new MCItemStack(getRecipe().getRecipeOutput());
    }
    
    @ZenCodeType.Getter("dynamic")
    public boolean isDynamic() {
        return getRecipe().isDynamic();
    }
    
    @ZenCodeType.Method
    public boolean canFit(int width, int height) {
        return getRecipe().canFit(width, height);
    }
    
    @ZenCodeType.Getter("icon")
    public IItemStack getIcon() {
        return new MCItemStackMutable(getRecipe().getIcon());
    }
    
    @ZenCodeType.Getter("ingredients")
    public List<IIngredient> getIngredients() {
        return getRecipe().getIngredients()
                .stream()
                .map(IIngredient::fromIngredient)
                .collect(Collectors.toList());
    }
    
    @ZenCodeType.Getter("recipeManager")
    public IRecipeManager getManager() {
        
        return RecipeTypeBracketHandler.getOrDefault(this.recipe.getType());
    }
    
    public IRecipe<?> getRecipe() {
        return recipe;
    }

    @ZenCodeType.Getter("isShapedCraftingRecipe")
    public boolean isShapedCraftingRecipe() {
        return this.recipe instanceof IShapedRecipe;
    }

    @ZenCodeType.Getter("isShapelessCraftingRecipe")
    public boolean isShapelessCraftingRecipe() {
        return this.recipe instanceof ShapelessRecipe || this.recipe instanceof CTRecipeShapeless;
    }
    
    @Override
    @ZenCodeType.Getter("commandString")
    public String getCommandString() {
        
        return this.dump(CraftTweakerRegistry.getHandlerFor(this.recipe), this.recipe);
    }
    
    @ZenCodeType.Method
    public void replace(final IIngredient from, final IIngredient to) {
        
        Replacer.forRecipes(this).replaceFully(from, to).execute();
    }
    
    private <T extends IRecipe<?>> String dump(final IRecipeHandler<T> handler, final T recipe) {
        
        return handler.dumpToCommandString(this.getManager(), recipe);
    }
    
    @Override
    public String toString() {
        
        final StringBuilder sb = new StringBuilder("WrapperRecipe{");
        sb.append("recipe=").append(recipe.getId());
        sb.append('}');
        return sb.toString();
    }
    
}
