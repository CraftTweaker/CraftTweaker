package com.blamejared.crafttweaker.impl.registry;

import com.blamejared.crafttweaker.api.natives.NativeTypeInfo;
import com.blamejared.crafttweaker.api.plugin.IBracketParserRegistrationHandler;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.api.tag.manager.TagManagerFactory;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.ZenTypeInfo;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunModuleConfigurator;
import com.blamejared.crafttweaker.impl.plugin.core.IPluginRegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import org.openzen.zenscript.parser.BracketExpressionParser;

import java.util.Collection;

@SuppressWarnings("ClassCanBeRecord")
final class PluginRegistryAccess implements IPluginRegistryAccess {
    
    private final Registries registries;
    
    PluginRegistryAccess(final Registries registries) {
        
        this.registries = registries;
    }
    
    @Override
    public void registerLoaders(final Collection<IScriptLoader> loader) {
        
        this.registries.loaderRegistry().registerLoaders(loader);
        this.registries.bracketResolverRegistry().fillLoaderData(loader);
        this.registries.enumBracketRegistry().fillLoaderData(loader);
        this.registries.zenClassRegistry().fillLoaderData(loader);
    }
    
    @Override
    public void registerLoadSources(final Collection<IScriptLoadSource> sources) {
        
        this.registries.loadSourceRegistry().registerLoadSources(sources);
    }
    
    @Override
    public void registerRunModuleConfigurator(final IScriptLoader loader, final IScriptRunModuleConfigurator configurator) {
        
        this.registries.scriptRunModuleConfiguratorRegistry().register(loader, configurator);
    }
    
    @Override
    public void registerPreprocessor(final IPreprocessor preprocessor) {
        
        this.registries.preprocessorRegistry().register(preprocessor);
    }
    
    @Override
    public void registerNativeType(final IScriptLoader loader, final NativeTypeInfo info) {
        
        this.registries.zenClassRegistry().registerNativeType(loader, info);
    }
    
    @Override
    public void registerZenType(final IScriptLoader loader, final Class<?> clazz, final ZenTypeInfo info, final boolean globals) {
        
        this.registries.zenClassRegistry().registerZenType(loader, clazz, info, globals);
    }
    
    @Override
    public void registerBracket(
            final IScriptLoader loader,
            final String name,
            final BracketExpressionParser bracketParser,
            final IBracketParserRegistrationHandler.DumperData dumperData
    ) {
        
        this.registries.bracketResolverRegistry().registerBracket(loader, name, bracketParser, dumperData);
    }
    
    @Override
    public <T extends Enum<T>> void registerEnum(final IScriptLoader loader, final ResourceLocation id, final Class<T> enumClass) {
        
        this.registries.enumBracketRegistry().register(loader, id, enumClass);
    }
    
    @Override
    public <T> void registerTaggableElement(ResourceKey<T> key, Class<T> elementClass) {
        
        this.registries.taggableElementRegistry().registerElement(key, elementClass);
    }
    
    @Override
    public <T, U extends ITagManager<?>> void registerTaggableElementManager(ResourceKey<T> key, TagManagerFactory<T, U> factory) {
        
        this.registries.taggableElementRegistry().registerManager(key, factory);
    }
    
    @Override
    public <T extends Recipe<?>> void registerHandler(final Class<? extends T> clazz, final IRecipeHandler<T> handler) {
        
        this.registries.recipeHandlerRegistry().register(clazz, handler);
    }
    
    @Override
    public void applyInheritanceRules() {
        
        this.registries.bracketResolverRegistry().applyInheritanceRules();
        this.registries.enumBracketRegistry().applyInheritanceRules();
        this.registries.zenClassRegistry().applyInheritanceRules();
    }
    
    @Override
    public void verifyProperRegistration() {
        
        this.registries.scriptRunModuleConfiguratorRegistry().verify(this.registries.loaderRegistry().getAllLoaders());
    }
    
}
