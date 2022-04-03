package com.blamejared.crafttweaker.impl.registry;

import com.blamejared.crafttweaker.impl.registry.recipe.RecipeHandlerRegistry;
import com.blamejared.crafttweaker.impl.registry.zencode.BracketResolverRegistry;
import com.blamejared.crafttweaker.impl.registry.zencode.EnumBracketRegistry;
import com.blamejared.crafttweaker.impl.registry.zencode.PreprocessorRegistry;
import com.blamejared.crafttweaker.impl.registry.zencode.TaggableElementRegistry;
import com.blamejared.crafttweaker.impl.registry.zencode.ZenClassRegistry;

record Registries(
        BracketResolverRegistry bracketResolverRegistry,
        EnumBracketRegistry enumBracketRegistry,
        LoaderRegistry loaderRegistry,
        LoadSourceRegistry loadSourceRegistry,
        PreprocessorRegistry preprocessorRegistry,
        RecipeHandlerRegistry recipeHandlerRegistry,
        ScriptRunModuleConfiguratorRegistry scriptRunModuleConfiguratorRegistry,
        TaggableElementRegistry taggableElementRegistry,
        ZenClassRegistry zenClassRegistry
) {}
