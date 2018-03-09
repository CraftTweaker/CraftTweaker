package crafttweaker.api.recipes;

import crafttweaker.api.item.*;
import crafttweaker.api.player.IPlayer;

/**
 * @author Stan
 */
@Deprecated
public class UnknownRecipe implements ICraftingRecipe {
    
    private final IItemStack output;
    
    public UnknownRecipe(IItemStack output) {
        this.output = output;
    }
    
    public boolean matches(ICraftingInventory inventory) {
        return false;
    }
    
    public IItemStack getCraftingResult(ICraftingInventory inventory) {
        return output;
    }
    
    public boolean hasTransformers() {
        return false;
    }
    
    @Override
    public boolean hasRecipeAction() {
        return false;
    }
    
    @Override
    public boolean hasRecipeFunction() {
        return false;
    }
    
    public void applyTransformers(ICraftingInventory inventory, IPlayer byPlayer) {
    
    }
    
    @Override
    public IIngredient[] getIngredients1D() {
        return new IIngredient[0];
    }
    
    @Override
    public IIngredient[][] getIngredients2D() {
        return new IIngredient[0][];
    }
    
    @Override
    public boolean isHidden() {
        return false;
    }
    
    @Override
    public boolean isShaped() {
        return false;
    }
    
    @Override
    public IItemStack getOutput() {
        return output;
    }
    
    @Override
    public String toCommandString() {
        return "// unknown recipe type for " + output.toCommandString();
    }
    
    @Override
    public String getName() {
        return "unknown";
    }
    
    @Override
    public String getFullResourceName() {
        return null;
    }
    
    @Override
    public String getResourceDomain() {
        return null;
    }
}