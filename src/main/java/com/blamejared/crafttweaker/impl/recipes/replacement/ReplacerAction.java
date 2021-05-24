package com.blamejared.crafttweaker.impl.recipes.replacement;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerRegistry;
import com.blamejared.crafttweaker.api.actions.IRuntimeAction;
import com.blamejared.crafttweaker.api.logger.ILogger;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipes.IReplacementRule;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionReplaceRecipe;
import com.blamejared.crafttweaker.impl.brackets.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.impl.recipes.wrappers.WrapperRecipe;
import com.mojang.datafixers.util.Pair;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ReplacerAction implements IRuntimeAction {
    private final Collection<IRecipeManager> managers;
    private final Collection<? extends IRecipe<?>> recipes;
    private final List<IReplacementRule> rules;
    private final Collection<ResourceLocation> defaultExclusions;
    private final Collection<ResourceLocation> userExclusions;
    private final Function<ResourceLocation, ResourceLocation> generatorFunction;
    
    public ReplacerAction(final Collection<IRecipeManager> managers, final Collection<? extends IRecipe<?>> recipe, final List<IReplacementRule> rules,
                          final Collection<ResourceLocation> defaultExclusions, final Collection<ResourceLocation> userExclusions,
                          final Function<ResourceLocation, ResourceLocation> generatorFunction) {
        this.managers = managers;
        this.recipes = recipe;
        this.rules = rules;
        this.defaultExclusions = defaultExclusions;
        this.userExclusions = userExclusions;
        this.generatorFunction = generatorFunction;
    }
    
    @Override
    public void apply() {
        final Collection<ResourceLocation> exclusions = Util.make(new HashSet<>(this.defaultExclusions), it -> it.addAll(this.userExclusions));
        Stream.concat(this.streamManagers(), this.streamRecipes())
                .filter(pair -> !exclusions.contains(pair.getSecond().getId()))
                .map(pair -> this.execute(pair.getFirst(), pair.getSecond(), this.rules))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(CraftTweakerAPI::apply);
        CraftTweakerAPI.logInfo("Batch replacement completed");
    }
    
    @Override
    public String describe() {
        return String.format(
                "Batching replacement for %s according to replacement rules %s%s",
                this.stringifyTargets(),
                this.stringifyReplacementRules(),
                this.stringifyExclusionsIfPresent()
        );
    }
    
    @Override
    public boolean validate(final ILogger logger) {
        if (this.recipes.isEmpty() && this.managers.isEmpty()) {
            logger.error("Invalid replacer action: no targeted recipes nor managers");
            return false;
        }
        if (this.rules.isEmpty()) {
            logger.error("Invalid replacer action: no rules available");
            return false;
        }
        return true;
    }
    
    private String stringifyTargets() {
        return this.stringifyTargetedManagersIfPresent()
                + (this.areWeTargetingBothManagersAndRecipes()? " and " : "")
                + this.stringifyTargetedRecipesIfPresent();
    }
    
    private boolean areWeTargetingBothManagersAndRecipes() {
        return !this.managers.isEmpty() && !this.recipes.isEmpty();
    }
    
    private String stringifyTargetedManagersIfPresent() {
        if (this.managers.isEmpty()) {
            return "";
        }
        
        return this.managers.stream()
                .map(IRecipeManager::getCommandString)
                .collect(Collectors.joining(", ", "managers {", "}"));
    }
    
    private String stringifyTargetedRecipesIfPresent() {
        if (this.recipes.isEmpty()) {
            return "";
        }
        
        return this.recipes.stream()
                .map(IRecipe::getId)
                .map(ResourceLocation::toString)
                .collect(Collectors.joining(", ", "recipes {", "}"));
    }
    
    private String stringifyReplacementRules() {
        return this.rules.stream()
                .map(IReplacementRule::describe)
                .collect(Collectors.joining(", ", "{", "}"));
    }
    
    private String stringifyExclusionsIfPresent() {
        if (this.userExclusions.isEmpty() && this.defaultExclusions.isEmpty()) return "";
        
        return " excluding {"
                + this.stringifyUserExclusionsIfPresent()
                + (this.doBothUserAndDefaultExclusionsExist()? ", " : "")
                + this.stringifyDefaultExclusionsIfPresent()
                + '}';
    }
    
    private boolean doBothUserAndDefaultExclusionsExist() {
        return !this.userExclusions.isEmpty() && !this.defaultExclusions.isEmpty();
    }
    
    private String stringifyUserExclusionsIfPresent() {
        if (this.userExclusions.isEmpty()) return "";
        
        return this.userExclusions.stream()
                .map(ResourceLocation::toString)
                .collect(Collectors.joining("\", \"", "\"", "\""));
    }
    
    private String stringifyDefaultExclusionsIfPresent() {
        if (this.defaultExclusions.isEmpty()) return "";
        
        return this.defaultExclusions.stream()
                .map(ResourceLocation::toString)
                .collect(Collectors.joining("\" (automatic), \"", "\"", "\" (automatic)"));
    }
    
    private Stream<Pair<IRecipeManager, IRecipe<?>>> streamManagers() {
        return this.managers.stream()
                .map(manager -> Pair.of(manager, manager.getAllRecipes().stream().map(WrapperRecipe::getRecipe)))
                .flatMap(pair -> pair.getSecond().map(recipe -> Pair.of(pair.getFirst(), recipe)));
    }
    
    private Stream<Pair<IRecipeManager, IRecipe<?>>> streamRecipes() {
        return this.recipes.stream()
                .map(recipe -> Pair.of(RecipeTypeBracketHandler.getOrDefault(recipe.getType()), recipe));
    }
    
    private <T extends IInventory, U extends IRecipe<T>> Optional<ActionReplaceRecipe> execute(final IRecipeManager manager, final U recipe, final List<IReplacementRule> rules) {
        try {
            final IRecipeHandler<U> handler = CraftTweakerRegistry.getHandlerFor(recipe);
            final Optional<Function<ResourceLocation, U>> newRecipeMaybe = handler.replaceIngredients(manager, recipe, rules);
            
            if (newRecipeMaybe.isPresent()) {
                return Optional.of(new ActionReplaceRecipe(manager, this.generatorFunction, recipe, name -> newRecipeMaybe.get().apply(name)));
            }
        } catch (final IRecipeHandler.ReplacementNotSupportedException e) {
            CraftTweakerAPI.logWarning("Unable to replace ingredients in recipe %s: %s", recipe.getId(), e.getMessage());
        } catch (final Throwable t) {
            CraftTweakerAPI.logThrowing("An error has occurred while trying to replace ingredients in recipe %s", t, recipe.getId());
        }
        return Optional.empty();
    }
}
