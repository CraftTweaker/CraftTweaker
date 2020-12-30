package com.blamejared.crafttweaker.impl.actions.recipes.whole_registry;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

import java.util.IntSummaryStatistics;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public abstract class AbstractActionRemoveFromWholeRegistry extends AbstractActionWholeRegistry {
    
    @Override
    public void apply() {
        final IntSummaryStatistics removalStatistics = getRecipesByType().values()
                .stream()
                .mapToInt(this::applyToRegistry)
                .filter(i -> i != 0)
                .summaryStatistics();
        
        final long numberOfRecipesRemoved = removalStatistics.getSum();
        final long numberOfManagersThatOwnedTheRecipes = removalStatistics.getCount();
        CraftTweakerAPI.logInfo("Removed %s recipes registered in %s recipe managers", numberOfRecipesRemoved, numberOfManagersThatOwnedTheRecipes);
    }
    
    private int applyToRegistry(Map<ResourceLocation, IRecipe<?>> registry) {
        final Set<Map.Entry<ResourceLocation, IRecipe<?>>> entries = registry.entrySet();
        final Iterator<Map.Entry<ResourceLocation, IRecipe<?>>> iterator = entries.iterator();
        
        int numberOfRecipesRemoved = 0;
        while(iterator.hasNext()) {
            final IRecipe<?> recipe = iterator.next().getValue();
            if(shouldRemove(recipe)) {
                iterator.remove();
                numberOfRecipesRemoved++;
            }
        }
        
        return numberOfRecipesRemoved;
    }
    
    protected abstract boolean shouldRemove(IRecipe<?> recipe);
}
