package com.blamejared.crafttweaker.api.recipe.replacement.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.custom.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.recipe.replacement.IFilteringRule;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Filters recipes according to a custom set of rules.
 *
 * @since 10.0.0
 */
@Document("vanilla/api/recipe/replacement/type/CustomFilteringRule")
@ZenCodeType.Name("crafttweaker.api.recipe.replacement.type.CustomFilteringRule")
@ZenRegister
public final class CustomFilteringRule implements IFilteringRule {
    
    private final BiPredicate<IRecipeManager<?>, Recipe<?>> predicate;
    private final boolean requiresComputation;
    
    private CustomFilteringRule(final BiPredicate<IRecipeManager<?>, Recipe<?>> predicate, final boolean requiresComputation) {
        
        this.predicate = predicate;
        this.requiresComputation = requiresComputation;
    }
    
    /**
     * Creates a new rule filtering recipes based on the given {@link Predicate}.
     *
     * <p>The predicate gets access to the {@link Recipe} instance directly, allowing for it to check directly elements
     * that might be required.</p>
     *
     * @param predicate The predicate for checking.
     *
     * @return A rule carrying out what has been specified.
     *
     * @since 10.0.0
     */
    public static CustomFilteringRule of(final Predicate<Recipe<?>> predicate) {
        
        return new CustomFilteringRule((a, b) -> predicate.test(b), false);
    }
    
    /**
     * Creates a new rule filtering recipes based on the given {@link BiPredicate}.
     *
     * <p>The predicate's first argument represents the {@link IRecipeManager} used by the recipe, whereas the second
     * argument is the {@link Recipe} instance directly, allowing for it to check properties that might be required or
     * perform additional manager-specific lookups.</p>
     *
     * @param predicate The predicate for checking.
     *
     * @return A rule carrying out what has been specified.
     *
     * @since 10.0.0
     */
    public static CustomFilteringRule of(final BiPredicate<IRecipeManager<?>, Recipe<?>> predicate) {
        
        return new CustomFilteringRule(predicate, true);
    }
    
    /**
     * Creates a new rule filtering recipes based on the given {@link Predicate}.
     *
     * <p>The predicate gets access to the {@link Recipe} instance directly, allowing for it to check directly elements
     * that might be required.</p>
     *
     * @param predicate The predicate for checking.
     *
     * @return A rule carrying out what has been specified.
     *
     * @since 11.0.0
     */
    @ZenCodeType.Method("of")
    public static CustomFilteringRule ofZen(final Predicate<Recipe<Container>> predicate) {
        
        return of(GenericUtil.<Predicate<Recipe<?>>>uncheck(predicate));
    }
    
    /**
     * Creates a new rule filtering recipes based on the given {@link BiPredicate}.
     *
     * <p>The predicate's first argument represents the {@link IRecipeManager} used by the recipe, whereas the second
     * argument is the {@link Recipe} instance directly, allowing for it to check properties that might be required or
     * perform additional manager-specific lookups.</p>
     *
     * @param predicate The predicate for checking.
     *
     * @return A rule carrying out what has been specified.
     *
     * @since 11.0.0
     */
    @ZenCodeType.Method("of")
    public static CustomFilteringRule ofZen(final BiPredicate<IRecipeManager<Recipe<Container>>, Recipe<Container>> predicate) {
        
        return of(GenericUtil.<BiPredicate<IRecipeManager<?>, Recipe<?>>>uncheck(predicate));
    }
    
    @Override
    public Stream<? extends Recipe<?>> castFilter(final Stream<? extends Recipe<?>> allRecipes) {
        
        return allRecipes.filter(this::castFilter);
    }
    
    @Override
    public String describe() {
        
        return "a custom set of recipes";
    }
    
    private <C extends Container, T extends Recipe<C>> boolean castFilter(final T recipe) {
        
        final IRecipeManager<? super T> manager = this.requiresComputation ? GenericUtil.uncheck(RecipeTypeBracketHandler.getOrDefault(recipe.getType())) : null;
        return this.predicate.test(manager, recipe);
    }
    
}
