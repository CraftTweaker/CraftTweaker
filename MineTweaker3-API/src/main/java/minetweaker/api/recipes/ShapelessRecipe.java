package minetweaker.api.recipes;

import minetweaker.api.item.*;
import minetweaker.api.player.IPlayer;

import java.util.*;

/**
 * @author Stan
 */
public class ShapelessRecipe implements ICraftingRecipe {
    
    private final IItemStack output;
    private final IRecipeFunction function;
    private final IRecipeAction action;
    private final IIngredient[] ingredients;
    
    public ShapelessRecipe(IItemStack output, IIngredient[] ingredients, IRecipeFunction function, IRecipeAction action) {
        this.output = output;
        this.function = function;
        this.ingredients = ingredients;
        this.action = action;
    }
    
    /**
     * Reorders ingredients to match the given shapeless recipe.
     *
     * @param recipe      shapeless recipe
     * @param ingredients crafting input
     *
     * @return reordered inputs, or null if no match was found
     */
    private static RecipeMatching matchShapeless(IIngredient[] recipe, ICraftingInventory ingredients) {
        int numItems = 0;
        for(int i = 0; i < ingredients.getSize(); i++) {
            if(ingredients.getStack(i) != null) {
                numItems++;
            }
        }
        
        if(numItems != recipe.length) {
            return null;
        }
        
        IItemStack[] matched = new IItemStack[recipe.length];
        int[] indices = new int[recipe.length];
        
        outer:
        for(int i = 0; i < ingredients.getSize(); i++) {
            IItemStack ingredient = ingredients.getStack(i);
            if(ingredient == null)
                continue;
            
            for(int j = 0; j < recipe.length; j++) {
                if(matched[j] != null)
                    continue;
                if(recipe[j].matches(ingredient)) {
                    matched[j] = ingredient;
                    indices[j] = i;
                    continue outer;
                }
            }
            
            return null;
        }
        
        return new RecipeMatching(matched, indices);
    }
    
    public int getSize() {
        return ingredients.length;
    }
    
    public IIngredient[] getIngredients() {
        return ingredients;
    }
    
    public IItemStack getOutput() {
        return output;
    }
    
    @Override
    public boolean matches(ICraftingInventory inventory) {
        return matchShapeless(ingredients, inventory) != null;
    }
    
    @Override
    public IItemStack getCraftingResult(ICraftingInventory inventory) {
        RecipeMatching matching = matchShapeless(ingredients, inventory);
        
        IItemStack actualOutput = output;
        if(function != null) {
            Map<String, IItemStack> map = new HashMap<>();
            for(int i = 0; i < ingredients.length; i++) {
                if(ingredients[i].getMark() != null) {
                    map.put(ingredients[i].getMark(), matching.inputs[i]);
                }
            }
            
            actualOutput = function.process(actualOutput, map, new CraftingInfo(inventory, null));
        }
        
        if(actualOutput == null) {
            return null;
        }
        
        return actualOutput;
    }
    
    @Override
    public void applyTransformers(ICraftingInventory inventory, IPlayer byPlayer) {
        RecipeMatching matching = matchShapeless(ingredients, inventory);
        
        for(int i = 0; i < ingredients.length; i++) {
            IIngredient ingredient = ingredients[i];
            IItemStack transformed = ingredient.applyTransform(matching.inputs[i], byPlayer);
            if(transformed != matching.inputs[i]) {
                inventory.setStack(matching.indices[i], transformed);
            }
        }
    }
    
    @Override
    public String toCommandString() {
        StringBuilder result = new StringBuilder();
        
        result.append("recipes.addShapeless(");
        if(output != null){
            result.append(output);
        }else{
            result.append("null");
        }
        result.append(", [");
        
        for(int i = 0; i < ingredients.length; i++) {
            if(i > 0)
                result.append(", ");
            
            result.append(ingredients[i]);
        }
        
        result.append("]);");
        
        return result.toString();
    }
    
    @Override
    public boolean hasTransformers() {
        for(IIngredient ingredient : ingredients) {
            if(ingredient.hasTransformers()) {
                return true;
            }
        }
        
        return false;
    }
    
    public IRecipeAction getAction() {
        return action;
    }
    
    private static class RecipeMatching {
        
        public final IItemStack[] inputs;
        public final int[] indices;
        
        public RecipeMatching(IItemStack[] inputs, int[] indices) {
            this.inputs = inputs;
            this.indices = indices;
        }
    }
}
