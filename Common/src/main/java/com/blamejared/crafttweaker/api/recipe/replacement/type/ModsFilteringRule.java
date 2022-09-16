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

/**
 * Filters recipes that have been created by the given set of mods.
 *
 * <p>Every mod is identified according to its mod id. Recipes have been created by a mod if their name as determined
 * by {@link Recipe#getId()} has that mod id as the namespace.</p>
 *
 * @since 10.0.0
 */
@Document("vanilla/api/recipe/replacement/type/ModsFilteringRule")
@ZenCodeType.Name("crafttweaker.api.recipe.replacement.type.ModsFilteringRule")
@ZenRegister
public final class ModsFilteringRule implements IFilteringRule {
    
    private final Set<String> modIds;
    
    private ModsFilteringRule(final Set<String> modIds) {
        
        this.modIds = Set.copyOf(modIds);
    }
    
    /**
     * Creates a new rule filtering recipes based on the given mod ids.
     *
     * @param modIds The mod ids to check for.
     *
     * @return A rule carrying out what has been specified.
     *
     * @since 10.0.0
     */
    @ZenCodeType.Method
    public static IFilteringRule of(final String... modIds) {
        
        if(modIds.length < 1) {
            
            throw new IllegalArgumentException("Unable to create mods filtering rule without any mods");
        }
        
        return new ModsFilteringRule(Set.of(modIds));
    }
    
    /**
     * Creates a new rule filtering recipes based on the ids of the given {@link Mod}s.
     *
     * @param mods The mods to check for.
     *
     * @return A rule carrying out what has been specified.
     *
     * @since 10.0.0
     */
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
