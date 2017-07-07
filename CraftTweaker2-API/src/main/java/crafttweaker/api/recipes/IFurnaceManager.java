package crafttweaker.api.recipes;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.*;
import stanhebben.zenscript.annotations.*;

import java.util.List;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.recipes.IFurnaceManager")
@ZenRegister
public interface IFurnaceManager {
    
    @ZenMethod
    void remove(IIngredient output, @Optional IIngredient input);
    
    @ZenMethod
    void removeAll();
    
    @ZenMethod
    void addRecipe(IItemStack output, IIngredient input, @Optional double xp);
    
    @ZenMethod
    void setFuel(IIngredient item, int fuel);
    
    @ZenMethod
    int getFuel(IItemStack item);
    
    /**
     * Returns all crafting recipes.
     *
     * @return all crafting recipes
     */
    @ZenGetter("all")
    List<IFurnaceRecipe> getAll();
}
