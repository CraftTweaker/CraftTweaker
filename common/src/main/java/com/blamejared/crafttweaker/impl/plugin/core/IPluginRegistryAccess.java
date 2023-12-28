package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.natives.NativeTypeInfo;
import com.blamejared.crafttweaker.api.plugin.IBracketParserRegistrationHandler;
import com.blamejared.crafttweaker.api.recipe.component.IRecipeComponent;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.replacement.ITargetingFilter;
import com.blamejared.crafttweaker.api.recipe.replacement.ITargetingStrategy;
import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.api.tag.manager.TagManagerFactory;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.ZenTypeInfo;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunModuleConfigurator;
import com.google.common.reflect.TypeToken;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import org.openzen.zenscript.parser.BracketExpressionParser;

import java.util.Collection;

public interface IPluginRegistryAccess {
    
    void registerLoaders(final Collection<IScriptLoader> loader);
    
    void registerLoadSources(final Collection<IScriptLoadSource> sources);
    
    void registerRunModuleConfigurator(final IScriptLoader loader, final IScriptRunModuleConfigurator configurator);
    
    void registerPreprocessor(final IPreprocessor preprocessor);
    
    void registerNativeType(final IScriptLoader loader, final NativeTypeInfo info);
    
    void registerZenType(final IScriptLoader loader, final Class<?> clazz, final ZenTypeInfo info, final boolean globals);
    
    void registerBracket(
            final IScriptLoader loader,
            final String name,
            final BracketExpressionParser bracketParser,
            final IBracketParserRegistrationHandler.DumperData dumperData
    );
    
    <T extends Enum<T>> void registerEnum(final IScriptLoader loader, final ResourceLocation id, final Class<T> enumClass);
    
    <T> void registerTaggableElement(final ResourceKey<T> key, final Class<T> elementClass);
    
    <T, U extends ITagManager<?>> void registerTaggableElementManager(final ResourceKey<T> key, final TagManagerFactory<T, U> factory);
    
    void registerComponents(final Collection<IRecipeComponent<?>> components);
    
    <T> void registerEventBusMapping(final TypeToken<T> token, final IEventBus<T> bus);
    
    <T extends Recipe<?>> void registerHandler(final Class<? extends T> clazz, final IRecipeHandler<T> handler);
    
    void registerTargetingFilters(final Collection<ITargetingFilter> filters);
    
    void registerTargetingStrategy(final ResourceLocation id, final ITargetingStrategy strategy);
    
    void applyInheritanceRules();
    
    void verifyProperRegistration();
    
}
