package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.natives.NativeTypeInfo;
import com.blamejared.crafttweaker.api.plugin.IBracketParserRegistrationHandler;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.ZenTypeInfo;
import com.blamejared.crafttweaker.api.zencode.impl.IScriptLoadSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Collection;

public interface IPluginRegistryAccess {
    
    void registerLoaders(final Collection<IScriptLoader> loader);
    
    void registerLoadSources(final Collection<IScriptLoadSource> sources);
    
    void registerPreprocessor(final IPreprocessor preprocessor);
    
    void registerNativeType(final IScriptLoader loader, final NativeTypeInfo info);
    
    void registerZenType(final IScriptLoader loader, final Class<?> clazz, final ZenTypeInfo info, final boolean globals);
    
    void registerBracket(
            final IScriptLoader loader,
            final String name,
            final IBracketParserRegistrationHandler.Creator bracketCreator,
            final IBracketParserRegistrationHandler.DumperData dumperData
    );
    
    <T extends Enum<T>> void registerEnum(final IScriptLoader loader, final ResourceLocation id, final Class<T> enumClass);
    
    <T extends Recipe<?>> void registerHandler(final Class<? extends T> clazz, final IRecipeHandler<T> handler);
    
    void applyInheritanceRules();
    
}
