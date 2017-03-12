package minetweaker.api.compat;

import java.util.*;

public class DummyJEIRecipeRegistry implements IJEIRecipeRegistry {
    
    @Override
    public void addRecipe(Object object) {
        System.out.println("adding recipe " + object);
    }
    
    @Override
    public void removeRecipe(Object object) {
        System.out.println("Removing Recipe" + object);
    }
    
    @Override
    public void addFurnace(List<Object> inputs, Object output) {
        System.out.println("adding furnace " + inputs + ":" + output);
    }
    
    @Override
    public void removeFurnace(Object object) {
        System.out.println("Removing Furnace" + object);
    }
    
    @Override
    public void addFuel(Collection<Object> input, int burnTime) {
    
    }
    
    @Override
    public void removeFuel(Object object) {
    
    }
}
