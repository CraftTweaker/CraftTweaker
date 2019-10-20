package minetweaker.api.recipes;

import minetweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;

import java.util.Map;

/**
 * @author Stan
 */
@ZenClass("minetweaker.recipes.IRecipeFunction")
public interface IRecipeFunction {
    
    IItemStack process(IItemStack output, Map<String, IItemStack> inputs, ICraftingInfo craftingInfo);
}
