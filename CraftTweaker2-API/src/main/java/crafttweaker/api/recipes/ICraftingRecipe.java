package crafttweaker.api.recipes;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenOperator;

import java.util.List;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.recipes.ICraftingRecipe")
@ZenRegister
public interface ICraftingRecipe {
 
	@ZenMethod
	@ZenGetter("name")
    String getName();
    
	@ZenGetter("commandString")
    String toCommandString();
}
