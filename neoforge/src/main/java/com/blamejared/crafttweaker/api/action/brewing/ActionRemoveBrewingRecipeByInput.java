package com.blamejared.crafttweaker.api.action.brewing;

import com.blamejared.crafttweaker.api.item.IItemStack;
import net.neoforged.neoforge.common.brewing.BrewingRecipeRegistry;
import net.neoforged.neoforge.common.brewing.IBrewingRecipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ActionRemoveBrewingRecipeByInput extends ActionBrewingBase {
    
    private final IItemStack input;
    private final List<IBrewingRecipe> removedRecipes = new ArrayList<>();
    
    public ActionRemoveBrewingRecipeByInput(List<IBrewingRecipe> recipes, IItemStack input) {
        
        super(recipes);
        this.input = input;
    }
    
    @Override
    public void apply() {
        
        Iterator<IBrewingRecipe> registryIterator = recipes.iterator();
        while(registryIterator.hasNext()) {
            IBrewingRecipe next = registryIterator.next();
            if(next.isInput(input.getInternal())) {
                removedRecipes.add(next);
                registryIterator.remove();
            }
        }
    }
    
    @Override
    public void undo() {
        
        removedRecipes.forEach(BrewingRecipeRegistry::addRecipe);
    }
    
    @Override
    public String describe() {
        
        return "Removing Brewing recipes that have an input of: " + input;
    }
    
    @Override
    public String describeUndo() {
        
        return "Undoing removal of Brewing recipes that have an input of: " + input;
    }
    
}
