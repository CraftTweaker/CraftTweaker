package com.blamejared.crafttweaker.api.recipe.replacement.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.recipe.manager.GenericRecipesManager;
import com.blamejared.crafttweaker.api.recipe.replacement.IFilteringRule;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.item.crafting.Recipe;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Inverts a filtering rule.
 *
 * <p>This rule should be used sparingly as it incurs a performance loss due to having to loop over all recipes multiple
 * times to perform the inversion of the rule.</p>
 *
 * @since 10.0.0
 */
@Document("vanilla/api/recipe/replacement/type/NotFilteringRule")
@ZenCodeType.Name("crafttweaker.api.recipe.replacement.type.NotFilteringRule")
@ZenRegister
public final class NotFilteringRule implements IFilteringRule {
    
    private final IFilteringRule rule;
    
    private NotFilteringRule(final IFilteringRule rule) {
        
        this.rule = rule;
    }
    
    /**
     * Inverts the specified rule.
     *
     * @param rule The rule to invert.
     *
     * @return The inverted rule.
     *
     * @since 10.0.0
     */
    @ZenCodeType.Method
    public static NotFilteringRule of(final IFilteringRule rule) {
        
        return new NotFilteringRule(rule);
    }
    
    @Override
    public Stream<? extends Recipe<?>> castFilter(final Stream<? extends Recipe<?>> allRecipes) {
        
        final Set<? extends Recipe<?>> toYeet =
                this.rule.castFilter(GenericRecipesManager.INSTANCE.getAllRecipes().stream())
                        .collect(Collectors.toSet());
        return allRecipes.filter(it -> !toYeet.contains(it));
    }
    
    @Override
    public String describe() {
        
        return "the opposite of " + this.rule;
    }
    
}
