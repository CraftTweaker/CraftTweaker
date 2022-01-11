package com.blamejared.crafttweaker.api.action.recipe.replace;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerRegistry;
import com.blamejared.crafttweaker.api.action.base.IRuntimeAction;
import com.blamejared.crafttweaker.api.bracket.custom.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.handler.IReplacementRule;
import com.blamejared.crafttweaker.api.recipe.handler.ITargetingRule;
import com.blamejared.crafttweaker.api.recipe.manager.GenericRecipesManager;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.recipe.replacement.rule.SpecificRecipesTargetingRule;
import com.mojang.datafixers.util.Pair;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class ReplacerAction implements IRuntimeAction {
    
    private final ITargetingRule targetingRule;
    private final boolean isSimple;
    private final List<IReplacementRule> replacementRules;
    private final Collection<ResourceLocation> defaultExclusions;
    private final Function<ResourceLocation, ResourceLocation> generatorFunction;
    private final boolean suppressWarnings;
    
    public ReplacerAction(final ITargetingRule targetingRule, final boolean isSimple, final List<IReplacementRule> replacementRules,
                          final Collection<ResourceLocation> defaultExclusions, final Function<ResourceLocation, ResourceLocation> generatorFunction,
                          final boolean suppressWarnings) {
        
        this.targetingRule = targetingRule;
        this.isSimple = isSimple;
        this.replacementRules = replacementRules;
        this.defaultExclusions = filter(targetingRule, defaultExclusions);
        this.generatorFunction = generatorFunction;
        this.suppressWarnings = suppressWarnings;
    }
    
    private static Collection<ResourceLocation> filter(final ITargetingRule rule, final Collection<ResourceLocation> fullExclusions) {
        
        final Map<ResourceLocation, Recipe<?>> map = GenericRecipesManager.INSTANCE.getRecipeMap(); // Holy hell if it's inefficient otherwise
        
        
        return Util.make(new HashSet<>(fullExclusions), set -> set.removeIf(it -> {
            final Recipe<?> recipe = map.get(it);
            return recipe == null || !rule.shouldBeReplaced(recipe, RecipeTypeBracketHandler.getOrDefault(recipe.getType()));
        }));
    }
    
    @Override
    public void apply() {
        
        this.specificRecipesOrElse(GenericRecipesManager.INSTANCE::getAllRecipes)
                .stream()
                .filter(it -> !this.defaultExclusions.contains(it.getId()))
                .map(it -> Pair.of(it, RecipeTypeBracketHandler.getOrDefault(it.getType())))
                .filter(pair -> this.targetingRule.shouldBeReplaced(pair.getFirst(), pair.getSecond()))
                .map(pair -> this.execute(pair.getSecond(), pair.getFirst(), this.replacementRules))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(CraftTweakerAPI::apply);
        CraftTweakerAPI.LOGGER.info("Batch replacement completed");
    }
    
    @Override
    public String describe() {
        
        return String.format(
                "Batching%s replacement for %s according to replacement rules %s%s%s",
                this.stringifySimplicity(),
                this.stringifyTargets(),
                this.stringifyReplacementRules(),
                this.stringifyExclusionsIfPresent(),
                this.stringifySuppressWarnings()
        );
    }
    
    @Override
    public boolean validate(final Logger logger) {
        
        if(this.replacementRules.isEmpty()) {
            logger.error("Invalid replacer action: no rules available");
            return false;
        }
        return true;
    }
    
    private Collection<Recipe<?>> specificRecipesOrElse(final Supplier<Collection<Recipe<?>>> ifComplex) {
        
        return this.isSimple ? ((SpecificRecipesTargetingRule) this.targetingRule).recipes() : ifComplex.get();
    }
    
    private String stringifySimplicity() {
        
        return this.isSimple ? " simple" : "";
    }
    
    private String stringifyTargets() {
        
        return this.targetingRule.describe();
    }
    
    private String stringifyReplacementRules() {
        
        return this.replacementRules.stream()
                .map(IReplacementRule::describe)
                .collect(Collectors.joining(", ", "{", "}"));
    }
    
    private String stringifyExclusionsIfPresent() {
        
        if(this.defaultExclusions.isEmpty()) {
            return "";
        }
        
        return ", while also automatically excluding {"
                + this.stringifyDefaultExclusionsIfPresent()
                + "} due to mod requests";
    }
    
    private String stringifyDefaultExclusionsIfPresent() {
        
        return this.defaultExclusions.stream()
                .map(ResourceLocation::toString)
                .collect(Collectors.joining(", "));
    }
    
    private String stringifySuppressWarnings() {
        
        return this.suppressWarnings ? " (Warnings are suppressed for this batch replacement)" : "";
    }
    
    private <T extends Container, U extends Recipe<T>> Optional<ActionReplaceRecipe<?>> execute(final IRecipeManager<?> manager, final U recipe, final List<IReplacementRule> rules) {
        
        try {
            final IRecipeHandler<U> handler = CraftTweakerRegistry.getHandlerFor(recipe);
            final Optional<Function<ResourceLocation, U>> newRecipeMaybe = handler.replaceIngredients(manager, recipe, rules);
            
            if(newRecipeMaybe.isPresent()) {
                return Optional.of(new ActionReplaceRecipe<>((IRecipeManager<U>) manager, this.generatorFunction, recipe, name -> newRecipeMaybe.get()
                        .apply(name)));
            }
        } catch(final IRecipeHandler.ReplacementNotSupportedException e) {
            if(!this.suppressWarnings) {
                CraftTweakerAPI.LOGGER.warn("Unable to replace ingredients in recipe {}: {}", recipe.getId(), e.getMessage());
            }
        } catch(final Throwable t) {
            CraftTweakerAPI.LOGGER.error("An error has occurred while trying to replace ingredients in recipe {}", recipe.getId(), t);
        }
        return Optional.empty();
    }
    
}
