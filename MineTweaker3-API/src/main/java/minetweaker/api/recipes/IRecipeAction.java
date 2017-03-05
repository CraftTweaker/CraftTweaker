package minetweaker.api.recipes;

import minetweaker.api.item.IItemStack;
import minetweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;

/**
 * @author Jared
 */
@ZenClass("minetweaker.recipes.IRecipeAction")
public interface IRecipeAction {
    
    void process(IItemStack output, ICraftingInfo craftingInfo, IPlayer player);
    
}
