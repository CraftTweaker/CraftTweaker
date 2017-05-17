package minetweaker.api.compat;

import java.util.*;

public class DummyJEIRecipeRegistry implements IJEIRecipeRegistry {
    
    @Override
    public void addRecipe(Object object) {
    }
    
    @Override
    public void removeRecipe(Object object) {
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
}
