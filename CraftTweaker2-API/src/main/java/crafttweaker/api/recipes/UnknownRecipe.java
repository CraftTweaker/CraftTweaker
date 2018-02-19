package crafttweaker.api.recipes;

import crafttweaker.api.item.IItemStack;
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
    
    public void applyTransformers(ICraftingInventory inventory, IPlayer byPlayer) {
    
    }
    
    @Override
    public String toCommandString() {
        return "// unknown recipe type for " + output;
    }
    
    @Override
    public String getName() {
        return "unknown";
    }
}