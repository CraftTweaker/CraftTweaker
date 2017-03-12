package minetweaker.api.compat;

import java.util.*;

public interface IJEIRecipeRegistry {
    
    void addRecipe(Object object);
    
    void removeRecipe(Object output);
    
    void addFurnace(List<Object> inputs, Object output);
    
    void removeFurnace(Object output);
    
    void addFuel(Collection<Object> input, int burnTime);
    
    void removeFuel(Object object);
}
