package crafttweaker.mc1120.recipes;

import crafttweaker.api.item.*;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.IShapedRecipe;

import javax.annotation.Nullable;

public class MCRecipeWrapper extends MCRecipeBase {
    
    private final IRecipe recipe;
    private final boolean isShaped;
    
    MCRecipeWrapper(IRecipe recipe) {
        super(CraftTweakerMC.getIItemStack(recipe.getRecipeOutput()), recipe.getIngredients(), null, null, recipe.isDynamic());
        this.recipe = recipe;
        this.isShaped = recipe instanceof IShapedRecipe;
    }
    
    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        return recipe.matches(inv, worldIn);
    }
    
    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        return recipe.getCraftingResult(inv);
    }
    
    @Override
    public boolean canFit(int width, int height) {
        return recipe.canFit(width, height);
    }
    
    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        return recipe.getRemainingItems(inv);
    }
    
    @Override
    public String toCommandString() {
        StringBuilder commandString = new StringBuilder("recipes.addShape");
        if(isShaped) {
            IShapedRecipe shapedRecipe = (IShapedRecipe) recipe;
            int width = shapedRecipe.getRecipeWidth();
            int height = shapedRecipe.getRecipeHeight();
            commandString.append("d(\"").append(this.getRegistryName()).append("\", ");
            IItemStack output = getOutput();
            commandString.append(output == null ? "null" : output.toCommandString()).append(", [");
            if(height > 0 && width > 0) {
                for(int row = 0; row < height; row++) {
                    commandString.append("[");
                    for(int column = 0; column < width; column++) {
                        IIngredient ingredient = CraftTweakerMC.getIIngredient(shapedRecipe.getIngredients().get(row * width + column));
                        commandString.append(ingredient == null ? "null" : ingredient.toCommandString()).append(", ");
                    }
                    //Remove last ,
                    commandString.deleteCharAt(commandString.length() - 1);
                    commandString.deleteCharAt(commandString.length() - 1);
                    commandString.append("], ");
                }
                //Remove last ,
                commandString.deleteCharAt(commandString.length() - 1);
                commandString.deleteCharAt(commandString.length() - 1);
            }
        } else {
            commandString.append("less(\"").append(this.getRegistryName()).append("\", ");
            IItemStack output = getOutput();
            commandString.append(output == null ? "null" : output.toCommandString()).append(", [");
            if(recipe.getIngredients().size() > 0) {
                for(Ingredient ingredient : recipe.getIngredients()) {
                    IIngredient iIngredient = CraftTweakerMC.getIIngredient(ingredient);
                    commandString.append(iIngredient == null ? "null" : iIngredient.toCommandString()).append(", ");
                }
                //Remove last ,
                commandString.deleteCharAt(commandString.length() - 1);
                commandString.deleteCharAt(commandString.length() - 1);
            }
        }
        return commandString.append("]);").toString();
    }
    
    @Override
    public boolean hasTransformers() {
        return false;
    }
    
    @Override
    public IIngredient[] getIngredients1D() {
        return CraftTweakerMC.getIIngredients(ingredientList);
    }
    
    @Override
    public IIngredient[][] getIngredients2D() {
        IIngredient[] ingredients = getIngredients1D();
        if (!isShaped) {
            return new IIngredient[][]{ingredients};
        }
        IShapedRecipe shapedRecipe = (IShapedRecipe) recipe;
        int heigth = shapedRecipe.getRecipeHeight();
        int width = shapedRecipe.getRecipeWidth();
        IIngredient[][] out = new IIngredient[heigth][width];
        
        for(int row = 0; row < heigth; row++) {
            for(int column = 0; column < width; column++) {
                out[row][column] = ingredients[row * width + column];
            }
        }
        return out;
    }
    
    @Override
    public boolean isShaped() {
        return isShaped;
    }
    
    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return recipe.getRegistryName();
    }
    
    @Override
    public void applyTransformers(InventoryCrafting inventory, IPlayer byPlayer) {
    
    }
    
    @Override
    public MCRecipeBase update() {
        return this;
    }
}
