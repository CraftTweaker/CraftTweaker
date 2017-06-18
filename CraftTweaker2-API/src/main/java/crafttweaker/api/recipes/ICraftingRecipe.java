package crafttweaker.api.recipes;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.recipes.ICraftingRecipe")
@ZenRegister
public interface ICraftingRecipe {
    
    boolean matches(ICraftingInventory inventory);
    
    IItemStack getCraftingResult(ICraftingInventory inventory);
    
    boolean hasTransformers();
    
    void applyTransformers(ICraftingInventory inventory, IPlayer byPlayer);
    
    String toCommandString();
    
    String getName();
}
