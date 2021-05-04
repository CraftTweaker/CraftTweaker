package com.blamejared.crafttweaker.impl.recipes;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerRegistry;
import com.blamejared.crafttweaker.api.actions.IRuntimeAction;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.logger.ILogger;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipes.IReplacementRule;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionReplaceRecipe;
import com.blamejared.crafttweaker.impl.brackets.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.impl.recipes.replacement.FullIngredientReplacementRule;
import com.blamejared.crafttweaker.impl.recipes.replacement.IngredientReplacementRule;
import com.blamejared.crafttweaker.impl.recipes.replacement.StackTargetingReplacementRule;
import com.blamejared.crafttweaker.impl.recipes.wrappers.WrapperRecipe;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.mojang.datafixers.util.Pair;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.recipes.Replacer")
@Document("vanilla/api/recipes/Replacer")
public final class Replacer {
    private static final class ReplacerAction implements IRuntimeAction {
        private final Collection<IRecipeManager> managers;
        private final Collection<? extends IRecipe<?>> recipes;
        private final List<IReplacementRule> rules;
        
        private ReplacerAction(final Collection<IRecipeManager> managers, final Collection<? extends IRecipe<?>> recipe, final List<IReplacementRule> rules) {
            this.managers = managers;
            this.recipes = recipe;
            this.rules = rules;
        }
        
        @Override
        public void apply() {
            Stream.concat(this.streamManagers(), this.streamRecipes())
                    .map(pair -> this.execute(pair.getFirst(), pair.getSecond(), this.rules))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .forEach(CraftTweakerAPI::apply);
            CraftTweakerAPI.logInfo("Batch replacement completed");
        }
        
        @Override
        public String describe() {
            return String.format(
                    "Batching replacement for %s%s%s according to replacement rules %s",
                    this.managers.isEmpty()? "" : this.managers.stream()
                            .map(IRecipeManager::getCommandString)
                            .collect(Collectors.joining(", ", "managers {", "}")),
                    !this.managers.isEmpty() && !this.recipes.isEmpty()? " and " : "",
                    this.recipes.isEmpty()? "" : this.recipes.stream()
                            .map(IRecipe::getId)
                            .map(ResourceLocation::toString)
                            .collect(Collectors.joining(", ", "recipes {", "}")),
                    this.rules.stream().map(IReplacementRule::describe).collect(Collectors.joining(", ", "{", "}"))
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
    
        private Stream<Pair<IRecipeManager, IRecipe<?>>> streamManagers() {
            return this.managers.stream()
                    .map(manager -> Pair.of(manager, manager.getAllRecipes().stream().map(WrapperRecipe::getRecipe)))
                    .flatMap(pair -> pair.getSecond().map(recipe -> Pair.of(pair.getFirst(), recipe)));
        }
    
        private Stream<Pair<IRecipeManager, IRecipe<?>>> streamRecipes() {
            return this.recipes.stream()
                    .map(recipe -> Pair.of(RecipeTypeBracketHandler.getCustomManager(Registry.RECIPE_TYPE.getKey(recipe.getType())), recipe));
        }
    
        private <T extends IInventory, U extends IRecipe<T>> Optional<ActionReplaceRecipe> execute(final IRecipeManager manager, final U recipe, final List<IReplacementRule> rules) {
            try {
                final IRecipeHandler<U> handler = CraftTweakerRegistry.getHandlerFor(recipe);
                final Optional<U> newRecipeMaybe = handler.replaceIngredients(manager, recipe, rules);
            
                if (newRecipeMaybe.isPresent()) {
                    return Optional.of(new ActionReplaceRecipe(manager, recipe, newRecipeMaybe.get()));
                }
            } catch (final IRecipeHandler.ReplacementNotSupportedException e) {
                // TODO("Warning maybe?")
                CraftTweakerAPI.logInfo("Unable to replace ingredients in recipe %s: %s", recipe.getId(), e.getMessage());
            } catch (final Throwable t) {
                CraftTweakerAPI.logThrowing("An error has occurred while trying to replace ingredients in recipe %s", t, recipe.getId());
            }
            return Optional.empty();
        }
    }
    
    private final Collection<IRecipeManager> targetedManagers;
    private final Collection<? extends IRecipe<?>> targetedRecipes;
    private final List<IReplacementRule> rules;
    
    private Replacer(final Collection<? extends IRecipe<?>> recipes, final Collection<IRecipeManager> managers) {
        this.targetedManagers = Collections.unmodifiableCollection(managers);
        this.targetedRecipes = Collections.unmodifiableCollection(recipes);
        this.rules = new ArrayList<>();
    }
    
    @ZenCodeType.Method
    public static Replacer forRecipes(final WrapperRecipe... recipes) {
        if (recipes.length <= 0) {
            throw new IllegalArgumentException("Unable to create a replacer without any targeted recipes");
        }
        return new Replacer(Arrays.stream(recipes).map(WrapperRecipe::getRecipe).collect(Collectors.toSet()), Collections.emptyList());
    }
    
    @ZenCodeType.Method
    public static Replacer forTypes(final IRecipeManager... managers) {
        if (managers.length <= 0) {
            throw new IllegalArgumentException("Unable to create a replacer without any targeted recipe types");
        }
        return new Replacer(Collections.emptyList(), new HashSet<>(Arrays.asList(managers)));
    }
    
    @ZenCodeType.Method
    public static Replacer forAllTypes() {
        return forAllTypesExcluding();
    }
    
    @ZenCodeType.Method
    public static Replacer forAllTypesExcluding(final IRecipeManager... managers) {
        final List<IRecipeManager> managerList = Arrays.asList(managers);
        return new Replacer(
                Collections.emptyList(),
                RecipeTypeBracketHandler.getManagerInstances().stream().filter(manager -> !managerList.contains(manager)).collect(Collectors.toSet())
        );
    }
    
    @ZenCodeType.Method
    public Replacer replace(final IIngredient from, final IIngredient to) {
        if (from instanceof IItemStack) return this.replaceStack((IItemStack) from, to);
        return this.addReplacementRule(IngredientReplacementRule.create(from, to));
    }
    
    @ZenCodeType.Method
    public Replacer replaceStack(final IItemStack from, final IIngredient to) {
        return this.addReplacementRule(StackTargetingReplacementRule.create(from, to));
    }
    
    @ZenCodeType.Method
    public Replacer replaceFully(final IIngredient from, final IIngredient to) {
        return this.addReplacementRule(FullIngredientReplacementRule.create(from, to));
    }
    
    @ZenCodeType.Method
    public void execute() {
        if (this.rules.isEmpty()) return;
        CraftTweakerAPI.apply(new ReplacerAction(this.targetedManagers, this.targetedRecipes, Collections.unmodifiableList(this.rules)));
    }
    
    private Replacer addReplacementRule(final IReplacementRule rule) {
        if (rule == IReplacementRule.EMPTY) return this;
        this.rules.add(rule);
        return this;
    }
}
