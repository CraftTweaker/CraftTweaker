package crafttweaker.api.recipes;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IEntityPlayer;
import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenOperator;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.recipes.ICraftingRecipe")
@ZenRegister
public interface ICraftingRecipe {
    
	@ZenMethod
	@ZenOperator(OperatorType.CONTAINS)
    boolean matches(ICraftingInventory inventory);
    
	@ZenMethod
    IItemStack getCraftingResult(ICraftingInventory inventory);
    
	@ZenMethod
	@ZenGetter("transformers")
    boolean hasTransformers();
    
	@ZenMethod
    void applyTransformers(ICraftingInventory inventory, IEntityPlayer byPlayer);
    
	@ZenMethod
	@ZenGetter("commandString")
    String toCommandString();
    
	@ZenMethod
	@ZenGetter("name")
    String getName();
}
