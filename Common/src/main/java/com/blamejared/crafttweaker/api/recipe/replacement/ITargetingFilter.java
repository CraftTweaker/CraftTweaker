package com.blamejared.crafttweaker.api.recipe.replacement;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.item.crafting.Recipe;
import org.openzen.zencode.java.ZenCodeType;

import java.util.stream.Stream;

@Document("vanilla/api/recipe/replacement/ITargetingFilter")
@FunctionalInterface
@ZenCodeType.Name("crafttweaker.api.recipe.replacement.ITargetingFilter")
@ZenRegister
public interface ITargetingFilter {
    
    Stream<? extends Recipe<?>> castFilter(final Stream<? extends Recipe<?>> allRecipes);
    
}
