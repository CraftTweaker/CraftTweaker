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
    
    MCRecipeWrapper(IRecipe recipe) {
        super(CraftTweakerMC.getIItemStack(recipe.getRecipeOutput()), recipe.getIngredients(), null, null, recipe.isHidden());
        this.recipe = recipe;
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
        if(recipe instanceof IShapedRecipe) {
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
                    commandString.append(ingredient == null ? "null" : iIngredient.toCommandString()).append(", ");
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
