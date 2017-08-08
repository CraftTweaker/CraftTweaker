package crafttweaker.api.item;

import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.player.IPlayer;

import java.util.*;

/**
 * Contains an ingredient stack. Is an ingredient with a specific stack size
 * assigned to it.
 *
 * @author Stan Hebben
 */
public class IngredientStack implements IIngredient {
    
    private final IIngredient ingredient;
    private final int amount;
    
    public IngredientStack(IIngredient ingredient, int amount) {
        this.ingredient = ingredient;
        this.amount = amount;
    }
    
    @Override
    public String getMark() {
        return null;
    }
    
    @Override
    public int getAmount() {
        return amount;
    }
    
    @Override
    public List<IItemStack> getItems() {
        return ingredient.getItems();
    }
    
    @Override
    public IItemStack[] getItemArray() {
    	return ingredient.getItemArray();
    }
    
    @Override
    public List<ILiquidStack> getLiquids() {
        return Collections.emptyList();
    }
    
    @Override
    public IIngredient amount(int amount) {
        return new IngredientStack(ingredient, amount);
    }
    
    @Override
    public IIngredient transform(IItemTransformer transformer) {
        return new IngredientStack(ingredient.transform(transformer), amount);
    }
    
    @Override
    public IIngredient only(IItemCondition condition) {
        return new IngredientStack(ingredient.only(condition), amount);
    }
    
    @Override
    public IIngredient marked(String mark) {
        return new IngredientStack(ingredient.marked(mark), amount);
    }
    
    @Override
    public IIngredient or(IIngredient ingredient) {
        return new IngredientOr(this, ingredient);
    }
    
    @Override
    public boolean matches(IItemStack item) {
        return item.getAmount() == amount && ingredient.matches(item);
    }
    
    @Override
    public boolean matchesExact(IItemStack item) {
        return item.getAmount() == amount && ingredient.matchesExact(item);
    }
    
    @Override
    public boolean matches(ILiquidStack liquid) {
        return false;
    }
    
    @Override
    public boolean contains(IIngredient ingredient) {
        return this.ingredient.contains(ingredient);
    }
    
    @Override
    public IItemStack applyTransform(IItemStack item, IPlayer byPlayer) {
        return ingredient.applyTransform(item, byPlayer);
    }
    
    @Override
    public Object getInternal() {
        return null;
    }
    
    @Override
    public boolean hasTransformers() {
        return ingredient.hasTransformers();
    }
    
    // #############################
    // ### Object implementation ###
    // #############################
    
    @Override
    public String toString() {
        String s = "(Ingredients) ";
        List<String> names = new ArrayList<>();
        
        for(IItemStack st : ingredient.getItems()) {
            names.add(st.getName());
        }
        s += String.join(",", names);
        
        return s;
    }
}
