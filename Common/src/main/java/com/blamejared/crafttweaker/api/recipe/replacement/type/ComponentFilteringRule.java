package com.blamejared.crafttweaker.api.recipe.replacement.type;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.custom.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.component.IRecipeComponent;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.recipe.replacement.DescriptivePredicate;
import com.blamejared.crafttweaker.api.recipe.replacement.IFilteringRule;
import com.blamejared.crafttweaker.api.recipe.replacement.ITargetingStrategy;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.impl.helper.AccessibleElementsProvider;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Filters recipes that have the specified {@link IRecipeComponent}, optionally with a check on its value.
 *
 * <p>In other words, to be able to be processed by the replacer, the target recipe must have the specified
 * {@link IRecipeComponent}. Optionally, the value of the component must match a specific value or a {@link Predicate}
 * to match, for more custom filtering. The value of the recipe component can be checked with any
 * {@link ITargetingStrategy}.</p>
 *
 * @param <T> The type of the component that should be filtered.
 *
 * @since 10.0.0
 */
@Document("vanilla/api/recipe/replacement/type/ComponentFilteringRule")
@ZenCodeType.Name("crafttweaker.api.recipe.replacement.type.ComponentFilteringRule")
@ZenRegister
public final class ComponentFilteringRule<T> implements IFilteringRule {
    
    private final IRecipeComponent<T> component;
    private final DescriptivePredicate<T> thing;
    private final ITargetingStrategy checkStrategy;
    
    private ComponentFilteringRule(final IRecipeComponent<T> component, final DescriptivePredicate<T> thing, final ITargetingStrategy checkStrategy) {
        
        this.component = component;
        this.thing = thing;
        this.checkStrategy = checkStrategy;
    }
    
    /**
     * Creates a new rule that filters recipes that have the given {@link IRecipeComponent}.
     *
     * <p>The value of the component is not checked, merely its presence.</p>
     *
     * @param component The component to check for.
     * @param <T>       The type of the object pointed to by the component.
     *
     * @return A rule carrying out what has been specified.
     *
     * @since 10.0.0
     */
    @ZenCodeType.Method
    public static <T> ComponentFilteringRule<T> of(final IRecipeComponent<T> component) {
        
        return of(component, null);
    }
    
    /**
     * Creates a new rule that filters recipes that have the given {@link IRecipeComponent} and whose value matches the
     * given {@code content}.
     *
     * <p>The strategy used is the default one, so components will be checked directly.</p>
     *
     * @param component The component to check for.
     * @param content   The oracle that represents the element to check for.
     * @param <T>       The type of the object pointed to by the component.
     *
     * @return A rule carrying out what has been specified.
     *
     * @since 10.0.0
     */
    @ZenCodeType.Method
    public static <T> ComponentFilteringRule<T> of(final IRecipeComponent<T> component, final T content) {
        
        return of(component, content, ITargetingStrategy.find(ITargetingStrategy.DEFAULT_STRATEGY_ID));
    }
    
    /**
     * Creates a new rule that filters recipes that have the given {@link IRecipeComponent} and whose value matches the
     * given {@code content} according to the given {@link ITargetingStrategy}.
     *
     * @param component     The component to check for.
     * @param content       The oracle that represents the element to check for.
     * @param checkStrategy The strategy that needs to be used to compare the component's value.
     * @param <T>           The type of the object pointed to by the component.
     *
     * @return A rule carrying out what has been specified.
     *
     * @since 10.0.0
     */
    @ZenCodeType.Method
    public static <T> ComponentFilteringRule<T> of(final IRecipeComponent<T> component, final T content, final ITargetingStrategy checkStrategy) {
        
        final DescriptivePredicate<T> predicate = content == null ? null : DescriptivePredicate.of(it -> component.match(content, it), content.toString());
        return new ComponentFilteringRule<>(component, predicate, checkStrategy);
    }
    
    /**
     * Creates a new rule that filters recipes that have the given {@link IRecipeComponent} and whose value matches
     * the given {@link Predicate} according to the given {@link ITargetingStrategy}.
     *
     * @param component     The component to check for.
     * @param content       A {@link Predicate} that determines whether an element is wanted or not. Its argument
     *                      represents the object to check for.
     * @param checkStrategy The strategy that needs to be used to compare the component's value.
     * @param <T>           The type of object pointed to by the component.
     *
     * @return A rule carrying out what has been specified
     *
     * @since 10.0.0
     */
    @ZenCodeType.Method
    public static <T> ComponentFilteringRule<T> of(final IRecipeComponent<T> component, final Predicate<T> content, final ITargetingStrategy checkStrategy) {
        
        return new ComponentFilteringRule<>(component, content == null ? null : DescriptivePredicate.wrap(content), checkStrategy);
    }
    
    @Override
    public Stream<RecipeHolder<?>> castFilter(final Stream<RecipeHolder<?>> allRecipes) {
        
        return allRecipes.filter(this::castFilter);
    }
    
    @Override
    public String describe() {
        
        return "recipes with component %s%s".formatted(
                this.component.getCommandString(),
                this.thing == null ? "" : " matching %s according to strategy %s".formatted(
                        this.thing.describe(),
                        this.checkStrategy.getCommandString()
                )
        );
    }
    
    private <C extends Container, V extends Recipe<C>> boolean castFilter(final RecipeHolder<?> recipe) {
        
        RecipeHolder<V> typedRecipe = GenericUtil.uncheck(recipe);
        final IRecipeHandler<V> handler = CraftTweakerAPI.getRegistry().getRecipeHandlerFor(typedRecipe);
        final IRecipeManager<? super V> manager = RecipeTypeBracketHandler.getOrDefault(recipe.value().getType());
        final Optional<IDecomposedRecipe> decomposedRecipe = handler.decompose(manager, AccessibleElementsProvider.get()
                .registryAccess(),typedRecipe);
        return decomposedRecipe.isPresent() && this.castFilter(decomposedRecipe.get());
    }
    
    private boolean castFilter(final IDecomposedRecipe recipe) {
        
        return this.thing != null ? this.castFullFilter(recipe) : this.castBasicFilter(recipe);
    }
    
    private boolean castFullFilter(final IDecomposedRecipe recipe) {
        
        return this.castBasicFilter(recipe) && this.castComponentFilter(recipe);
    }
    
    private boolean castBasicFilter(final IDecomposedRecipe recipe) {
        
        return recipe.components().contains(this.component);
    }
    
    private boolean castComponentFilter(final IDecomposedRecipe recipe) {
        
        final List<T> component = recipe.get(this.component);
        
        for(final T element : component) {
            final T result = this.checkStrategy.castStrategy(this.component, element, this::fakeReplacement);
            if(result != null) {
                return true;
            }
        }
        
        return false;
    }
    
    private T fakeReplacement(final T element) {
        // We don't need a proper replacement: the strategy by contract has to return a not-null value if at least
        // one invocation of replacer returns a non-null value. This in turn means that the "replacement" was
        // successful, i.e. at least one thing matched the specified target. This is all we need.
        return this.thing.test(element) ? element : null;
    }
    
}
