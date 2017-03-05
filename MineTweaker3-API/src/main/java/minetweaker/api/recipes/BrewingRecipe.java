package minetweaker.api.recipes;

import minetweaker.api.item.*;

public class BrewingRecipe implements IBrewingRecipe {
    
    private final IItemStack output;
    private final IItemStack input;
    private final IIngredient ingredient;
    
    public BrewingRecipe(IItemStack output, IItemStack input, IIngredient ingredient) {
        this.input = input;
        this.ingredient = ingredient;
        this.output = output;
    }
    
    @Override
    public String toCommandString() {
        return String.format("brewing.add(%s, %s, %s)", output, input, ingredient);
    }
}
