package minetweaker.api.compat;

import minetweaker.api.item.IIngredient;

import java.util.*;

public class DummyJEIRecipeRegistry implements IJEIRecipeRegistry {
    
    @Override
    public void addRecipe(Object object) {
    }
    
    @Override
    public void addRecipe(Object object, String category) {
    
    }
    
    @Override
    public void removeRecipe(Object object) {
    }
    
    @Override
    public void removeRecipe(Object output, String category) {
    
    }
    
    @Override
    public void addFurnace(List<Object> inputs, Object output) {
    }
    
    @Override
    public void removeFurnace(Object object) {
    }
    
    @Override
    public void addFuel(Collection<Object> input, int burnTime) {
    
    }
    
    @Override
    public void removeFuel(Object object) {
    
    }

    @Override
    public void reloadItemList() {

    }

    @Override
    public void removeItem(Object stack) {

    }

    @Override
    public void addItem(Object stack) {

    }

    @Override
    public void invalidateTooltips(IIngredient ingredient) {

    }
}
