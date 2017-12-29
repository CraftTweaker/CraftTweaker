package crafttweaker.api.recipes;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("crafttweaker.recipes.IFurnaceRecipe")
@ZenRegister
public interface IFurnaceRecipe {
    
	@ZenMethod
	@ZenGetter("commandString")
    String toCommandString();
}
