package com.blamejared.crafttweaker.impl.recipe.replacement;

import com.blamejared.crafttweaker.impl.script.ScriptRecipeType;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableSet;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class DefaultTargetingFilters {
    
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
    
    private DefaultTargetingFilters() {}
    
    public static Stream<? extends Recipe<?>> vanillaSpecial(final Stream<? extends Recipe<?>> allRecipes) {
        
        return allRecipes.filter(it -> !VANILLA_RECIPE_TYPES.get().contains(it.getType()) || !it.isSpecial());
    }
    
    public static Stream<? extends Recipe<?>> scripts(final Stream<? extends Recipe<?>> allRecipes) {
        
        return allRecipes.filter(it -> it.getType() != ScriptRecipeType.INSTANCE);
    }
    
}
