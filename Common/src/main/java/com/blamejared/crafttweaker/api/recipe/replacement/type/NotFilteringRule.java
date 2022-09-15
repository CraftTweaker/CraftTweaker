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

@Document("vanilla/api/recipe/replacement/type/NotFilteringRule")
@ZenCodeType.Name("crafttweaker.api.recipe.replacement.type.NotFilteringRule")
@ZenRegister
public final class NotFilteringRule implements IFilteringRule {
    
    private final IFilteringRule rule;
    
    private NotFilteringRule(final IFilteringRule rule) {
        
        this.rule = rule;
    }
    
    @ZenCodeType.Method
    public static NotFilteringRule of(final IFilteringRule rule) {
        
        return new NotFilteringRule(rule);
    }
    
    @Override
    public Stream<? extends Recipe<?>> castFilter(final Stream<? extends Recipe<?>> allRecipes) {
        
        final Set<? extends Recipe<?>> toYeet = this.rule.castFilter(GenericRecipesManager.INSTANCE.getAllRecipes()
                        .stream())
                .collect(Collectors.toSet());
        return allRecipes.filter(it -> !toYeet.contains(it));
    }
    
    @Override
    public String describe() {
        
        return "the opposite of " + this.rule;
    }
    
}
