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
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

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
    
    @ZenCodeType.Method
    public static <T> ComponentFilteringRule<T> of(final IRecipeComponent<T> component) {
        
        return of(component, null);
    }
    
    @ZenCodeType.Method
    public static <T> ComponentFilteringRule<T> of(final IRecipeComponent<T> component, final T content) {
        
        return of(component, content, ITargetingStrategy.find(ITargetingStrategy.DEFAULT_STRATEGY_ID));
    }
    
    @ZenCodeType.Method
    public static <T> ComponentFilteringRule<T> of(final IRecipeComponent<T> component, final T content, final ITargetingStrategy checkStrategy) {
        
        final DescriptivePredicate<T> predicate = content == null ? null : DescriptivePredicate.of(it -> component.match(content, it), content.toString());
        return new ComponentFilteringRule<>(component, predicate, checkStrategy);
    }
    
    @ZenCodeType.Method
    public static <T> ComponentFilteringRule<T> of(final IRecipeComponent<T> component, final Predicate<T> content, final ITargetingStrategy checkStrategy) {
        
        return new ComponentFilteringRule<>(component, content == null ? null : DescriptivePredicate.wrap(content), checkStrategy);
    }
    
    @Override
    public Stream<? extends Recipe<?>> castFilter(final Stream<? extends Recipe<?>> allRecipes) {
        
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
    
    private <C extends Container, V extends Recipe<C>> boolean castFilter(final V recipe) {
        
        final IRecipeHandler<V> handler = CraftTweakerAPI.getRegistry().getRecipeHandlerFor(recipe);
        final IRecipeManager<? super V> manager = GenericUtil.uncheck(RecipeTypeBracketHandler.getOrDefault(recipe.getType()));
        final Optional<IDecomposedRecipe> decomposedRecipe = handler.decompose(manager, recipe);
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
