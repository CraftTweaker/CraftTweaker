package com.blamejared.crafttweaker.impl.registry;

import com.blamejared.crafttweaker.api.ICraftTweakerRegistry;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.command.type.BracketDumperInfo;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.IZenClassRegistry;
import com.blamejared.crafttweaker.impl.registry.recipe.RecipeHandlerRegistry;
import com.blamejared.crafttweaker.impl.registry.zencode.BracketResolverRegistry;
import com.blamejared.crafttweaker.impl.registry.zencode.EnumBracketRegistry;
import com.blamejared.crafttweaker.impl.registry.zencode.PreprocessorRegistry;
import com.blamejared.crafttweaker.impl.registry.zencode.ZenClassRegistry;
import com.blamejared.crafttweaker.platform.Services;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import org.openzen.zencode.java.ScriptingEngine;
import org.openzen.zencode.java.module.JavaNativeModule;
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
    
    private CraftTweakerRegistry() {
        
        this.registries = new Registries(
                new BracketResolverRegistry(),
                new EnumBracketRegistry(),
                new LoaderRegistry(),
                new PreprocessorRegistry(),
                new RecipeHandlerRegistry(),
                new ZenClassRegistry()
        );
    }
    
    public static ICraftTweakerRegistry get() {
        
        return REGISTRY.get();
    }
    
    
    public void populateRegistries() {
        /*
        final CraftTweakerModList craftTweakerModList = new CraftTweakerModList();
        
        final List<? extends Class<?>> collect = Services.PLATFORM.findClassesWithAnnotation(ZenRegister.class, craftTweakerModList::add, CraftTweakerRegistry::checkModDeps)
                .filter(Objects::nonNull)
                .toList();
        
        
        craftTweakerModList.printToLog();
        
        collect.forEach(ZEN_CLASS_REGISTRY::addNativeType);
        ZEN_CLASS_REGISTRY.initNativeTypes();
        collect.forEach(ZEN_CLASS_REGISTRY::addClass);
        
        BRACKET_RESOLVER_REGISTRY.addClasses(ZEN_CLASS_REGISTRY.getAllRegisteredClasses());
        BRACKET_RESOLVER_REGISTRY.validateBrackets();
        
        BRACKET_ENUM_REGISTRY.addClasses(ZEN_CLASS_REGISTRY.getAllRegisteredClasses());
        
        Services.PLATFORM.findClassesWithAnnotation(Preprocessor.class).forEach(PREPROCESSOR_REGISTRY::addClass);
        
        ZEN_CLASS_REGISTRY.getImplementationsOf(ITagManager.class)
                .stream()
                .filter(aClass -> !aClass.equals(TagManagerWrapper.class))
                .forEach(CrTTagRegistryData.INSTANCE::addTagImplementationClass);
        
        Stream.concat(Services.PLATFORM.findClassesWithAnnotation(IRecipeHandler.For.class), Services.PLATFORM.findClassesWithAnnotation(IRecipeHandler.For.Container.class))
                .distinct()
                .forEach(RECIPE_HANDLER_REGISTRY::addClass);
         */
    }
    
    @Override
    public IScriptLoader findLoader(final String name) {
        
        return this.registries.loaderRegistry().find(name);
    }
    
    @Override
    public IZenClassRegistry getZenClassRegistry() {
        
        return this.registries.zenClassRegistry();
    }
    
    @Override
    public Collection<IScriptLoader> getAllLoaders() {
        
        return this.registries.loaderRegistry().getAllLoaders();
    }
    
    @Override
    public Map<String, BracketDumperInfo> getBracketDumpers(final IScriptLoader loader) {
        
        return ImmutableMap.copyOf(this.registries.bracketResolverRegistry().getBracketDumpers(loader));
    }
    
    @Override
    public List<Pair<String, BracketExpressionParser>> getBracketHandlers(final IScriptLoader loader, final String rootPackage, final ScriptingEngine engine, final JavaNativeModule ctModule) {
        
        return ImmutableList.copyOf(this.registries.bracketResolverRegistry()
                .getBracketResolvers(loader, rootPackage, engine, ctModule));
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
    @SuppressWarnings("unchecked") // why? how?
    public <T extends Enum<T>> T getEnumBracketValue(final ResourceLocation type, final String value) {
        
        final Class<T> clazz = (Class<T>) this.getEnumBracketFor(type)
                .orElseThrow(() -> new IllegalArgumentException(String.format("No enum found for type '%s'", type)));
        
        return Enum.valueOf(clazz, value.toUpperCase(Locale.ROOT));
    }
    
    @Override
    public <T extends Enum<T>> Optional<Class<T>> getEnumBracketFor(final ResourceLocation type) {
        
        return this.registries.enumBracketRegistry().getEnum(type);
    }
    
    @Override
    public Set<String> getAllEnumsForEnumBracket() {
        
        return this.registries.enumBracketRegistry()
                .getEnums()
                .entrySet()
                .stream()
                .flatMap(it -> Arrays.stream(it.getValue().getEnumConstants()).map(c -> Pair.of(it.getKey(), c)))
                .map(it -> String.format("<constant:%s:%s>", it.getFirst(), it.getSecond()
                        .name()
                        .toLowerCase(Locale.ROOT)))
                .collect(Collectors.toUnmodifiableSet());
    }
    
    @SuppressWarnings("unchecked")
    private boolean checkModDependencies(final Either<ZenRegister, Map<String, Object>> annotationData) {
        
        return annotationData.map(zenRegister -> List.of(zenRegister.modDeps()), map -> (List<String>) map.getOrDefault("modDeps", List.of()))
                .stream()
                .allMatch(Services.PLATFORM::isModLoaded);
    }
    
}
