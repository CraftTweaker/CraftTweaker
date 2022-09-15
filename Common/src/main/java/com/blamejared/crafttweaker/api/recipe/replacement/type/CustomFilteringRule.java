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

@Document("vanilla/api/recipe/replacement/type/CustomFilteringRule")
@ZenCodeType.Name("crafttweaker.api.recipe.replacement.type.CustomFilteringRule")
@ZenRegister
public final class CustomFilteringRule implements IFilteringRule {
    
    private final BiPredicate<IRecipeManager<?>, Recipe<?>> predicate;
    
    private CustomFilteringRule(final BiPredicate<IRecipeManager<?>, Recipe<?>> predicate) {
        
        this.predicate = predicate;
    }
    
    @ZenCodeType.Method
    public static CustomFilteringRule of(final Predicate<Recipe<?>> predicate) {
        
        return of((a, b) -> predicate.test(b));
    }
    
    @ZenCodeType.Method
    public static CustomFilteringRule of(final BiPredicate<IRecipeManager<?>, Recipe<?>> predicate) {
        
        return new CustomFilteringRule(predicate);
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
        
        final IRecipeManager<? super T> manager = GenericUtil.uncheck(RecipeTypeBracketHandler.getOrDefault(recipe.getType()));
        return this.predicate.test(manager, recipe);
    }
    
}
