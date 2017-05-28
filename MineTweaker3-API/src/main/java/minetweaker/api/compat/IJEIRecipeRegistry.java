package minetweaker.api.compat;

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
}
