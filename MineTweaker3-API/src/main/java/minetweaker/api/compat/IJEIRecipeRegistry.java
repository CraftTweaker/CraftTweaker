package minetweaker.api.compat;

import minetweaker.api.item.IIngredient;

import java.util.*;

public interface IJEIRecipeRegistry {
    
    void addRecipe(Object object);
    
    void addRecipe(Object object, String category);
    
    void removeRecipe(Object output);
    
    void removeRecipe(Object output, String category);
    
    
    void addFurnace(List<Object> inputs, Object output);
    
    void removeFurnace(Object output);
    
    void addFuel(Collection<Object> input, int burnTime);
    
    void removeFuel(Object object);

    void invalidateTooltips(IIngredient ingredient);

    //To update the items in 1.10.2
    void reloadItemList();

    //To update the items in 1.11.2 both below are used
    void removeItem(Object stack);

    void addItem(Object stack);
}
