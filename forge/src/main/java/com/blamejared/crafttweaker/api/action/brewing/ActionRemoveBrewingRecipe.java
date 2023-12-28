package com.blamejared.crafttweaker.api.action.brewing;

import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.MCItemStackMutable;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ActionRemoveBrewingRecipe extends ActionBrewingBase {
    
    private final List<IBrewingRecipe> removedRecipes = new ArrayList<>();
    private final IItemStack input;
    private final IItemStack output;
    private final IItemStack reagentStack;
    
    public ActionRemoveBrewingRecipe(List<IBrewingRecipe> recipes, IItemStack output, IItemStack reagentStack, IItemStack input) {
        
        super(recipes);
        this.output = output;
        this.reagentStack = reagentStack;
        this.input = input;
        
    }
    
    @Override
    public void apply() {
        
        Iterator<IBrewingRecipe> registryIterator = recipes.iterator();
        while(registryIterator.hasNext()) {
            IBrewingRecipe next = registryIterator.next();
            ItemStack recipeOutput = next.getOutput(input.getInternal(), this.reagentStack.getInternal());
            if(!recipeOutput.isEmpty() && output.matches(new MCItemStackMutable(recipeOutput))) {
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
        
        return "Removing Brewing recipes that have an input of: " + input + ", output of: " + output + " and a reagent of: " + reagentStack;
    }
    
    @Override
    public String describeUndo() {
        
        return "Undoing removal of Brewing recipes that have an input of: " + input + ", output of: " + output + " and a reagent of: " + reagentStack;
    }
    
    
}
