package com.blamejared.crafttweaker.api.action.recipe.replace;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.base.IRuntimeAction;
import com.blamejared.crafttweaker.api.action.internal.CraftTweakerAction;
import com.blamejared.crafttweaker.api.bracket.custom.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.GenericRecipesManager;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.recipe.replacement.IFilteringRule;
import com.blamejared.crafttweaker.api.recipe.replacement.IReplacerRegistry;
import com.blamejared.crafttweaker.api.recipe.replacement.ReplacementRequest;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ActionBatchReplacement extends CraftTweakerAction implements IRuntimeAction {
    
    private final Collection<IFilteringRule> targetingRules;
    private final Collection<ReplacementRequest<?>> requests;
    private final IReplacerRegistry registry;
    
    private ActionBatchReplacement(final Collection<IFilteringRule> targetingRules, final Collection<ReplacementRequest<?>> requests) {
        
        this.targetingRules = targetingRules;
        this.requests = requests;
        this.registry = CraftTweakerAPI.getRegistry().getReplacerRegistry();
    }
    
    public static ActionBatchReplacement of(final Collection<IFilteringRule> targetingRules, final Collection<ReplacementRequest<?>> requests) {
        
        return new ActionBatchReplacement(targetingRules, requests);
    }
    
    @Override
    public void apply() {
        
        this.castFilters(GenericRecipesManager.INSTANCE.getAllRecipes().stream()).forEach(this::replace);
    }
    
    @Override
    public String describe() {
        
        final Collector<CharSequence, ?, String> joiner = Collectors.joining(",", "{", "}");
        return "Replacing in %s according to requests %s".formatted(
                this.targetingRules.isEmpty() ? "everything" : this.targetingRules.stream()
                        .map(IFilteringRule::describe)
                        .collect(joiner),
                this.requests.stream().map(ReplacementRequest::describe).collect(joiner)
        );
    }
    
    private Stream<? extends Recipe<?>> castFilters(final Stream<? extends Recipe<?>> recipeStream) {
        
        return Stream.concat(this.registry.filters().stream(), this.targetingRules.stream())
                .reduce((a, b) -> it -> b.castFilter(a.castFilter(it)))
                .map(it -> it.castFilter(recipeStream))
                .orElseGet(() -> GenericUtil.uncheck(recipeStream));
    }
    
    private <C extends Container, T extends Recipe<C>> void replace(final T recipe) {
        
        final IRecipeHandler<T> handler = CraftTweakerAPI.getRegistry().getRecipeHandlerFor(recipe);
        final IRecipeManager<? super T> manager = GenericUtil.uncheck(RecipeTypeBracketHandler.getOrDefault(recipe.getType()));
        handler.decompose(manager, recipe).ifPresent(it -> this.replace(manager, handler, recipe.getId(), it));
    }
    
    private <C extends Container, T extends Recipe<C>> void replace(
            final IRecipeManager<? super T> manager,
            final IRecipeHandler<T> handler,
            final ResourceLocation name,
            final IDecomposedRecipe recipe
    ) {
        
        if(this.apply(recipe)) {
            
            CraftTweakerAPI.apply(new ActionReplaceRecipe<>(name, manager, newName -> this.rebuild(recipe, manager, handler, newName)));
        }
    }
    
    private boolean apply(final IDecomposedRecipe recipe) {
        
        boolean any = false;
        for(final ReplacementRequest<?> request : this.requests) {
            any |= request.applyRequest(recipe);
        }
        return any;
    }
    
    private <C extends Container, T extends Recipe<C>> T rebuild(
            final IDecomposedRecipe recipe,
            final IRecipeManager<? super T> manager,
            final IRecipeHandler<T> handler,
            final ResourceLocation newName
    ) {
        
        return handler.recompose(manager, newName, recipe)
                .orElseThrow(() -> new IllegalStateException("Recomposition failed due to an error"));
    }
    
}
