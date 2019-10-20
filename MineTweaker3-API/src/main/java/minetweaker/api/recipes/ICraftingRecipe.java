package minetweaker.api.recipes;

import minetweaker.api.item.IItemStack;
import minetweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;

/**
 * @author Stan
 */
@ZenClass("minetweaker.recipes.ICraftingRecipe")
public interface ICraftingRecipe {
    
    boolean matches(ICraftingInventory inventory);
    
    IItemStack getCraftingResult(ICraftingInventory inventory);
    
    boolean hasTransformers();
    
    void applyTransformers(ICraftingInventory inventory, IPlayer byPlayer);
    
    String toCommandString();
}
