package crafttweaker.api.recipes;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.*;
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
	
	@ZenGetter("fullResourceDomain")
	String getFullResourceName();
	
	@ZenGetter("resourceDomain")
    String getResourceDomain();
    
	@ZenGetter("commandString")
    String toCommandString();
	
	@ZenGetter
	boolean hasTransformers();
	
	@ZenGetter
	boolean hasRecipeAction();
	
	@ZenGetter
	boolean hasRecipeFunction();
	
	@Deprecated
	boolean matches(ICraftingInventory inventory);
	
	@Deprecated
	IItemStack getCraftingResult(ICraftingInventory inventory);
	
	@Deprecated
	void applyTransformers(ICraftingInventory inventory, IPlayer byPlayer);
	
	@ZenGetter("ingredients1D")
    IIngredient[] getIngredients1D();
    
    @ZenGetter("ingredients2D")
    IIngredient[][] getIngredients2D();
    
    @ZenGetter("hidden")
    boolean isHidden();
    
    @ZenGetter("shaped")
    boolean isShaped();
    
    @ZenGetter("output")
    IItemStack getOutput();
    
}
