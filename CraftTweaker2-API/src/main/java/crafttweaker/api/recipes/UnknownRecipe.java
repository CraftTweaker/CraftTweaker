package crafttweaker.api.recipes;

import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IEntityPlayer;

/**
 * @author Stan
 */
public class UnknownRecipe implements ICraftingRecipe {
    
    private final IItemStack output;
    
    public UnknownRecipe(IItemStack output) {
        this.output = output;
    }
    
    @Override
    public boolean matches(ICraftingInventory inventory) {
        return false;
    }
    
    @Override
    public IItemStack getCraftingResult(ICraftingInventory inventory) {
        return output;
    }
    
    @Override
    public boolean hasTransformers() {
        return false;
    }
    
    @Override
    public void applyTransformers(ICraftingInventory inventory, IEntityPlayer byPlayer) {
        
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
