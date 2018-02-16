package crafttweaker.mc1120.recipes;

import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.recipes.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.*;

import javax.annotation.Nullable;

public abstract class MCRecipeBase implements IRecipe, ICraftingRecipe {
    
    
    protected final ItemStack outputStack;
    protected final IItemStack output;
    protected final NonNullList<Ingredient> ingredientList;
    protected final IRecipeFunction recipeFunction;
    protected final IRecipeAction recipeAction;
    private final boolean hidden;
    protected ResourceLocation recipeNameLocation = new ResourceLocation("crafttweaker", "unInitializedRecipeName");
    
    MCRecipeBase(IItemStack output, NonNullList<Ingredient> ingredientList, IRecipeFunction recipeFunction, IRecipeAction recipeAction, boolean hidden) {
        this.output = output;
        this.outputStack = CraftTweakerMC.getItemStack(output);
        this.ingredientList = ingredientList;
        this.recipeFunction = recipeFunction;
        this.recipeAction = recipeAction;
        this.hidden = hidden;
    }
    
    @Override
    public ItemStack getRecipeOutput() {
        return outputStack.copy();
    }
    
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return ingredientList;
    }
    
    @Override
    public boolean isHidden() {
        return hidden;
    }
    
    @Override
    public String getGroup() {
        return "";
    }
    
    @Override
    public IRecipe setRegistryName(ResourceLocation name) {
        this.recipeNameLocation = name;
        return this;
    }
    
    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return recipeNameLocation;
    }
    
    @Override
    public Class<IRecipe> getRegistryType() {
        return IRecipe.class;
    }
    
    public IRecipeAction getRecipeAction() {
        return recipeAction;
    }
    
    public boolean hasRecipeAction() {
        return recipeAction != null;
    }
    
    @Override
    public String getName() {
        return getRegistryName().getResourcePath();
    }
    
    public IItemStack getOutput() {
        return output;
    }
    
    public boolean isVisible() {
        return !isHidden();
    }
}
