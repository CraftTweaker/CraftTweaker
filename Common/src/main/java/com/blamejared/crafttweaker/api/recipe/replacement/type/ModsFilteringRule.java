package com.blamejared.crafttweaker.api.recipe.replacement.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.mod.Mod;
import com.blamejared.crafttweaker.api.recipe.replacement.IFilteringRule;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.item.crafting.Recipe;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Document("vanilla/api/recipe/replacement/type/ModsFilteringRule")
@ZenCodeType.Name("crafttweaker.api.recipe.replacement.type.ModsFilteringRule")
@ZenRegister
public final class ModsFilteringRule implements IFilteringRule {
    
    private final Set<String> modIds;
    
    private ModsFilteringRule(final Set<String> modIds) {
        
        this.modIds = Set.copyOf(modIds);
    }
    
    @ZenCodeType.Method
    public static IFilteringRule of(final String... modIds) {
        
        if(modIds.length < 1) {
            
            throw new IllegalArgumentException("Unable to create mods filtering rule without any mods");
        }
        
        return new ModsFilteringRule(Set.of(modIds));
    }
    
    @ZenCodeType.Method
    public static IFilteringRule of(final Mod... mods) {
        
        final int l = mods.length;
        final String[] ids = new String[l];
        for(int i = 0; i < l; ++i) {
            ids[i] = mods[i].id();
        }
        return of(ids);
    }
    
    @Override
    public Stream<? extends Recipe<?>> castFilter(final Stream<? extends Recipe<?>> allRecipes) {
        
        return allRecipes.filter(it -> this.modIds.contains(it.getId().getNamespace()));
    }
    
    @Override
    public String describe() {
        
        return this.modIds.stream().collect(Collectors.joining(", ", "recipes from mods {", "}"));
    }
    
}
