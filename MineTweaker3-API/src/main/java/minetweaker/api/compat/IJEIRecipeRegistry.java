package minetweaker.api.compat;

import java.util.*;

public interface IJEIRecipeRegistry {
	
	void addRecipe(Object object);
	
	void removeRecipe(Object output);
	
	void addFurnace(List<Object> inputs, Object output);
	
	void removeFurnace(Object output);
	
	
	
}
