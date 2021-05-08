package com.blamejared.crafttweaker.impl.recipes;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.recipes.IReplacementRule;
import com.blamejared.crafttweaker.impl.brackets.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.impl.recipes.replacement.FullIngredientReplacementRule;
import com.blamejared.crafttweaker.impl.recipes.replacement.IngredientReplacementRule;
import com.blamejared.crafttweaker.impl.recipes.replacement.ReplacerAction;
import com.blamejared.crafttweaker.impl.recipes.replacement.StackTargetingReplacementRule;
import com.blamejared.crafttweaker.impl.recipes.wrappers.WrapperRecipe;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.item.crafting.IRecipe;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.recipes.Replacer")
@Document("vanilla/api/recipes/Replacer")
public final class Replacer {
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
