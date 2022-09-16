package com.blamejared.crafttweaker.api.recipe.replacement.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.recipe.replacement.IFilteringRule;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Filters recipes based on their {@link IRecipeManager}.
 *
 * @since 10.0.0
 */
@Document("vanilla/api/recipe/replacement/type/ManagerFilteringRule")
@ZenCodeType.Name("crafttweaker.api.recipe.replacement.type.ManagerFilteringRule")
@ZenRegister
public final class TypeFilteringRule implements IFilteringRule {
    
    private final Set<IRecipeManager<?>> managers;
    private final Set<RecipeType<?>> types;
    
    private TypeFilteringRule(final Set<IRecipeManager<?>> managers) {
        
        this.managers = Set.copyOf(managers);
        this.types = this.managers.stream().map(IRecipeManager::getRecipeType).collect(Collectors.toSet());
    }
    
    /**
     * Creates a new rule filtering recipes based on the given managers.
     *
     * @param managers The managers that should be filtered.
     *
     * @return A new rule carrying out the specified operation.
     *
     * @since 10.0.0
     */
    @ZenCodeType.Method
    public static IFilteringRule of(final IRecipeManager<?>... managers) {
        
        return new TypeFilteringRule(Set.of(managers));
        
    }
    
    @Override
    public Stream<? extends Recipe<?>> castFilter(Stream<? extends Recipe<?>> allRecipes) {
        
        return allRecipes.filter(it -> this.types.contains(it.getType()));
    }
    
    @Override
    public String describe() {
        
        return this.managers.stream()
                .map(IRecipeManager::getCommandString)
                .collect(Collectors.joining(", ", "recipes from managers {", "}"));
    }
    
};
