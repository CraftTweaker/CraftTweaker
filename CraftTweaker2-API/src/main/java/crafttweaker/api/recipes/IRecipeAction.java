package crafttweaker.api.recipes;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IEntityPlayer;
import stanhebben.zenscript.annotations.ZenClass;

/**
 * @author Jared
 */
@ZenClass("crafttweaker.recipes.IRecipeAction")
@ZenRegister
public interface IRecipeAction {
    
    void process(IItemStack output, ICraftingInfo craftingInfo, IEntityPlayer player);
    
}
