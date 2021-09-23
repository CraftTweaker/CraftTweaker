package com.blamejared.crafttweaker.impl.recipes.replacement;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerRegistry;
import com.blamejared.crafttweaker.api.actions.IRuntimeAction;
import com.blamejared.crafttweaker.api.logger.ILogger;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipes.IReplacementRule;
import com.blamejared.crafttweaker.api.recipes.ITargetingRule;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionReplaceRecipe;
import com.blamejared.crafttweaker.impl.managers.GenericRecipesManager;
import com.blamejared.crafttweaker.impl.recipes.wrappers.WrapperRecipe;
import com.mojang.datafixers.util.Pair;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

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
        
        final Map<ResourceLocation, WrapperRecipe> map = GenericRecipesManager.RECIPES.getRecipeMap(); // Holy hell if it's inefficient otherwise
        
        return Util.make(new HashSet<>(fullExclusions), set -> set.removeIf(it -> {
            final WrapperRecipe recipe = map.get(it);
            return !rule.shouldBeReplaced(recipe.getRecipe(), recipe.getManager());
        }));
    }
    
    @Override
    public void apply() {
        
        this.specificRecipesOrElse(GenericRecipesManager.RECIPES::getAllRecipes)
                .stream()
                .filter(it -> !this.defaultExclusions.contains(it.getId()))
                .map(it -> Pair.of(it.getRecipe(), it.getManager()))
                .filter(pair -> this.targetingRule.shouldBeReplaced(pair.getFirst(), pair.getSecond()))
                .map(pair -> this.execute(pair.getSecond(), pair.getFirst(), this.replacementRules))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(CraftTweakerAPI::apply);
        CraftTweakerAPI.logInfo("Batch replacement completed");
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
    public boolean validate(final ILogger logger) {
        
        if(this.replacementRules.isEmpty()) {
            logger.error("Invalid replacer action: no rules available");
            return false;
        }
        return true;
    }
    
    private Collection<WrapperRecipe> specificRecipesOrElse(final Supplier<Collection<WrapperRecipe>> ifComplex) {
        
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
    
    private <T extends IInventory, U extends IRecipe<T>> Optional<ActionReplaceRecipe> execute(final IRecipeManager manager, final U recipe, final List<IReplacementRule> rules) {
        
        try {
            final IRecipeHandler<U> handler = CraftTweakerRegistry.getHandlerFor(recipe);
            final Optional<Function<ResourceLocation, U>> newRecipeMaybe = handler.replaceIngredients(manager, recipe, rules);
            
            if(newRecipeMaybe.isPresent()) {
                return Optional.of(new ActionReplaceRecipe(manager, this.generatorFunction, recipe, name -> newRecipeMaybe.get()
                        .apply(name)));
            }
        } catch(final IRecipeHandler.ReplacementNotSupportedException e) {
            if(!this.suppressWarnings) {
                CraftTweakerAPI.logWarning("Unable to replace ingredients in recipe %s: %s", recipe.getId(), e.getMessage());
            }
        } catch(final Throwable t) {
            CraftTweakerAPI.logThrowing("An error has occurred while trying to replace ingredients in recipe %s", t, recipe.getId());
        }
        return Optional.empty();
    }
    
}
