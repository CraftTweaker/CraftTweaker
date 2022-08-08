package com.blamejared.crafttweaker.api.recipe.replacement.rule;

import com.blamejared.crafttweaker.CraftTweakerRegistries;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.recipe.replacement.event.IGatherReplacementExclusionEvent;
import com.blamejared.crafttweaker.impl.script.ScriptRecipeType;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableSet;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.Set;
import java.util.function.Supplier;

public final class DefaultExclusionReplacements {
    
    
    // Note: we are using a lazy here to avoid initializing the recipe type list statically and out of order
    private static final Supplier<Set<RecipeType<?>>> VANILLA_RECIPE_TYPES = Suppliers.memoize(
            () -> ImmutableSet.of(
                    RecipeType.CRAFTING,
                    RecipeType.SMELTING,
                    RecipeType.BLASTING,
                    RecipeType.SMOKING,
                    RecipeType.CAMPFIRE_COOKING,
                    RecipeType.STONECUTTING,
                    RecipeType.SMITHING
            )
    );
    
    public static void handleDefaultExclusions(IGatherReplacementExclusionEvent event){
        final IRecipeManager<?> manager = event.getTargetedManager();
    
        if(VANILLA_RECIPE_TYPES.get().contains(manager.getRecipeType())) {
            manager.getAllRecipes()
                    .stream()
                    .filter(Recipe::isSpecial)
                    .forEach(event::addExclusion);
        }
    
        if(manager.getRecipeType() == ScriptRecipeType.INSTANCE) {
            manager.getAllRecipes()
                    .forEach(event::addExclusion);
        }
    }
    
    
}
