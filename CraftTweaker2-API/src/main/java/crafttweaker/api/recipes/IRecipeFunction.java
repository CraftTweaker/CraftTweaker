package crafttweaker.api.recipes;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;

import java.util.Map;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.recipes.IRecipeFunction")
@ZenRegister
public interface IRecipeFunction {
    
    IItemStack process(IItemStack output, Map<String, IItemStack> inputs, ICraftingInfo craftingInfo);
}
