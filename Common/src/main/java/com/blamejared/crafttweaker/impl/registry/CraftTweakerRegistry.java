package com.blamejared.crafttweaker.impl.registry;

import com.blamejared.crafttweaker.api.ICraftTweakerRegistry;
import com.blamejared.crafttweaker.api.command.type.IBracketDumperInfo;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.api.tag.manager.TagManagerFactory;
import com.blamejared.crafttweaker.api.tag.manager.type.KnownTagManager;
import com.blamejared.crafttweaker.api.tag.manager.type.UnknownTagManager;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.IZenClassRegistry;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunModuleConfigurator;
import com.blamejared.crafttweaker.impl.plugin.core.IPluginRegistryAccess;
import com.blamejared.crafttweaker.impl.plugin.core.PluginManager;
import com.blamejared.crafttweaker.impl.registry.recipe.RecipeHandlerRegistry;
import com.blamejared.crafttweaker.impl.registry.zencode.BracketResolverRegistry;
import com.blamejared.crafttweaker.impl.registry.zencode.EnumBracketRegistry;
import com.blamejared.crafttweaker.impl.registry.zencode.PreprocessorRegistry;
import com.blamejared.crafttweaker.impl.registry.zencode.TaggableElementRegistry;
import com.blamejared.crafttweaker.impl.registry.zencode.ZenClassRegistry;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import org.openzen.zenscript.parser.BracketExpressionParser;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class CraftTweakerRegistry implements ICraftTweakerRegistry {
    
    private static final Supplier<CraftTweakerRegistry> REGISTRY = Suppliers.memoize(CraftTweakerRegistry::new);
    
    private final Registries registries;
    private final IPluginRegistryAccess access;
    
    private CraftTweakerRegistry() {
        
        this.registries = new Registries(
                new BracketResolverRegistry(),
                new EnumBracketRegistry(),
                new LoaderRegistry(),
                new LoadSourceRegistry(),
                new PreprocessorRegistry(),
                new RecipeHandlerRegistry(),
                new ScriptRunModuleConfiguratorRegistry(),
                new TaggableElementRegistry(),
                new ZenClassRegistry()
        );
        this.access = new PluginRegistryAccess(this.registries);
    }
    
    public static ICraftTweakerRegistry get() {
        
        return REGISTRY.get();
    }
    
    public static IPluginRegistryAccess pluginAccess(final PluginManager.Req req) {
        
        Objects.requireNonNull(req);
        return REGISTRY.get().access;
    }
    
    @Override
    public IScriptLoader findLoader(final String name) {
        
        return this.registries.loaderRegistry().find(name);
    }
    
    @Override
    public Collection<IScriptLoader> getAllLoaders() {
        
        return this.registries.loaderRegistry().getAllLoaders();
    }
    
    @Override
    public IScriptLoadSource findLoadSource(final ResourceLocation id) {
        
        return this.registries.loadSourceRegistry().get(id);
    }
    
    @Override
    public IZenClassRegistry getZenClassRegistry() {
        
        return this.registries.zenClassRegistry();
    }
    
    @Override
    public IScriptRunModuleConfigurator getConfiguratorFor(final IScriptLoader loader) {
        
        return this.registries.scriptRunModuleConfiguratorRegistry().find(loader);
    }
    
    @Override
    public Map<String, IBracketDumperInfo> getBracketDumpers(final IScriptLoader loader) {
        
        return ImmutableMap.copyOf(this.registries.bracketResolverRegistry().getBracketDumpers(loader));
    }
    
    @Override
    public Map<String, BracketExpressionParser> getBracketHandlers(final IScriptLoader loader, final String rootPackage) {
        
        // TODO("Is rootPackage really needed?")
        return ImmutableMap.copyOf(this.registries.bracketResolverRegistry().getBracketResolvers(loader));
    }
    
    @Override
    public List<IPreprocessor> getPreprocessors() {
        
        return ImmutableList.copyOf(this.registries.preprocessorRegistry().getPreprocessors());
    }
    
    @Override
    public <T extends Recipe<?>> IRecipeHandler<T> getRecipeHandlerFor(final T recipe) {
        
        return this.registries.recipeHandlerRegistry().getRecipeHandlerFor(recipe);
    }
    
    @Override
    public <T extends Recipe<?>> IRecipeHandler<T> getRecipeHandlerFor(Class<T> recipeClazz) {
        
        return this.registries.recipeHandlerRegistry().getRecipeHandlerFor(recipeClazz);
    }
    
    @Override
    @SuppressWarnings("unchecked") // why? how?
    public <T extends Enum<T>> T getEnumBracketValue(final IScriptLoader loader, final ResourceLocation type, final String value) {
        
        final Class<T> clazz = (Class<T>) this.getEnumBracketFor(loader, type)
                .orElseThrow(() -> new IllegalArgumentException(String.format("No enum found for type '%s'", type)));
        
        return Enum.valueOf(clazz, value.toUpperCase(Locale.ENGLISH));
    }
    
    @Override
    public <T extends Enum<T>> Optional<Class<T>> getEnumBracketFor(final IScriptLoader loader, final ResourceLocation type) {
        
        return this.registries.enumBracketRegistry().getEnum(loader, type);
    }
    
    @Override
    public <T> Optional<Class<T>> getTaggableElementFor(ResourceKey<T> key) {
        
        return this.registries.taggableElementRegistry().getTaggableElement(key);
    }
    
    public <T> TagManagerFactory<T, ? extends ITagManager<?>> getTaggableElementFactory(ResourceKey<Registry<T>> key) {
        
        if(this.registries.taggableElementRegistry().getTaggableElement(key).isPresent()) {
            return this.registries.taggableElementRegistry()
                    .getManagerFactory(key)
                    .orElseGet(() -> KnownTagManager::new);
        }
        
        return (resourceKey1, tClass) -> new UnknownTagManager(resourceKey1);
    }
    
    @Override
    public Set<String> getAllEnumStringsForEnumBracket(final IScriptLoader loader) {
        
        return this.registries.enumBracketRegistry()
                .getEnums(loader)
                .entrySet()
                .stream()
                .flatMap(it -> Arrays.stream(it.getValue().getEnumConstants()).map(c -> Pair.of(it.getKey(), c)))
                .map(it -> String.format("<constant:%s:%s>", it.getFirst(), it.getSecond()
                        .name()
                        .toLowerCase(Locale.ENGLISH)))
                .collect(Collectors.toUnmodifiableSet());
    }
    
}
