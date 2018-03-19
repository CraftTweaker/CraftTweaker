package crafttweaker.api.recipes;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("crafttweaker.recipes.IFurnaceRecipe")
@ZenRegister
public interface IFurnaceRecipe {
    
	@ZenMethod
	@ZenGetter("commandString")
    String toCommandString();
    
    @ZenGetter("input")
    IItemStack getInput();
    
    @ZenGetter("output")
    IItemStack getOutput();
    
    @ZenGetter("xp")
    float getXp();
}
