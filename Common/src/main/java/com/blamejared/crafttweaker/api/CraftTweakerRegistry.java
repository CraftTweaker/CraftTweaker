package com.blamejared.crafttweaker.api;

import com.blamejared.crafttweaker.CraftTweakerCommon;
import com.blamejared.crafttweaker.api.annotation.Preprocessor;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.command.type.BracketDumperInfo;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.handler.RecipeHandlerRegistry;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.api.tag.manager.TagManagerWrapper;
import com.blamejared.crafttweaker.api.tag.registry.CrTTagRegistryData;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.bracket.ValidatedEscapableBracketParser;
import com.blamejared.crafttweaker.api.zencode.impl.registry.BracketEnumRegistry;
import com.blamejared.crafttweaker.api.zencode.impl.registry.BracketResolverRegistry;
import com.blamejared.crafttweaker.api.zencode.impl.registry.PreprocessorRegistry;
import com.blamejared.crafttweaker.api.zencode.impl.registry.ZenClassRegistry;
import com.blamejared.crafttweaker.platform.Services;
import com.google.common.collect.BiMap;
import com.mojang.datafixers.util.Either;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import org.objectweb.asm.Type;
import org.openzen.zencode.java.ScriptingEngine;
import org.openzen.zencode.java.module.JavaNativeModule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class CraftTweakerRegistry {
    
    private static final BracketResolverRegistry BRACKET_RESOLVER_REGISTRY = new BracketResolverRegistry();
    private static final BracketEnumRegistry BRACKET_ENUM_REGISTRY = new BracketEnumRegistry();
    private static final PreprocessorRegistry PREPROCESSOR_REGISTRY = new PreprocessorRegistry();
    private static final RecipeHandlerRegistry RECIPE_HANDLER_REGISTRY = new RecipeHandlerRegistry();
    private static final ZenClassRegistry ZEN_CLASS_REGISTRY = new ZenClassRegistry();
    
    /**
     * Find all classes that have a {@link ZenRegister} annotation and registers them to the class list for loading.
     */
    public static void findClasses() {
        
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
    }
    
    public static Class<?> getClassFromType(Type type) {
        
        try {
            return Class.forName(type.getClassName(), false, CraftTweakerCommon.class.getClassLoader());
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    //<editor-fold desc="ZenClassRegistry Delegates">
    // ##################################
    // ### ZenClassRegistry Delegates ###
    // ##################################
    
    public static ZenClassRegistry getZenClassRegistry() {
        
        return ZEN_CLASS_REGISTRY;
    }
    
    /**
     * Gets an ImmutableMap of the classMap.
     * <p>
     * Classes should not be added manually to this map. See {@link ZenRegister}
     *
     * @return Map of String -> Class for ZenName -> Java class
     */
    public static BiMap<String, Class<?>> getZenClassMap() {
        
        return ZEN_CLASS_REGISTRY.getZenClasses();
    }
    
    /**
     * Gets all classes with a given package root.
     * <p>
     * Providing "crafttweaker" as the name can return:
     * <p>
     * {@code "crafttweaker.sub.package.Class}
     * and
     * {@code "crafttweaker.sub.other.package.Class}
     *
     * @param name Name of the Zen Package.
     *
     * @return list of classes in the Zen Package.
     */
    public static List<Class<?>> getClassesInPackage(String name) {
        
        return ZEN_CLASS_REGISTRY.getClassesInPackage(name);
    }
    
    /**
     * Gets all the top level Zen Packages.
     *
     * @return Set of top level packages
     */
    public static Set<String> getRootPackages() {
        
        return ZEN_CLASS_REGISTRY.getRootPackages();
    }
    
    /**
     * Gets the ZenGlobals map.
     *
     * @return BiMap of the ZenGlobals
     */
    public static BiMap<String, Class<?>> getZenGlobals() {
        
        return ZEN_CLASS_REGISTRY.getZenGlobals();
    }
    
    /**
     * Gets all classes that have at least one global with a given package root.
     * <p>
     * Providing "crafttweaker" as the name can return:
     * <p>
     * {@code "crafttweaker.sub.package.Class}
     * and
     * {@code "crafttweaker.sub.other.package.Class}
     *
     * @param name Name of the Zen Package.
     *
     * @return list of classes with at least one globa in the Zen Package.
     */
    public static List<Class<?>> getGlobalsInPackage(String name) {
        
        return ZEN_CLASS_REGISTRY.getGlobalsInPackage(name);
    }
    
    /**
     * Gets the registered Expansions by expanded name
     *
     * @return The Map
     */
    public static Map<String, List<Class<?>>> getExpansions() {
        
        return ZEN_CLASS_REGISTRY.getExpansionsByExpandedName();
    }
    
    /**
     * Returns the ZenCode name for the given Java Class.
     * Contains registered native types (e.g. ItemStack)
     *
     * @param cls The class to check for
     *
     * @return An optional that contains the class, if found
     */
    public static Optional<String> tryGetZenClassNameFor(Class<?> cls) {
        
        return ZEN_CLASS_REGISTRY.tryGetNameFor(cls);
    }
    
    /**
     * Gets the found recipe managers
     *
     * @return ImmutableList of the recipe managers.
     */
    public static List<Class<? extends IRecipeManager>> getRecipeManagers() {
        
        return ZEN_CLASS_REGISTRY.getRecipeManagers();
    }
    //</editor-fold>
    
    
    //<editor-fold desc="BracketResolverRegistry Delegates">
    // #########################################
    // ### BracketResolverRegistry Delegates ###
    // #########################################
    
    /**
     * Gets the Bracket dumper Map
     *
     * @return ImmutableMap of the Bracket dumpers
     */
    public static Map<String, BracketDumperInfo> getBracketDumpers() {
        
        return BRACKET_RESOLVER_REGISTRY.getBracketDumpers();
    }
    
    /**
     * Adds a name as advanced BEP name.
     * This means that a Bracket Validator may exist that has no matching BEP method in here.
     *
     * @param name The name to add
     */
    public static void addAdvancedBEPName(String name) {
        
        BRACKET_RESOLVER_REGISTRY.addAdvancedBEPName(name);
    }
    
    public static List<ValidatedEscapableBracketParser> getBracketResolvers(String name, ScriptingEngine scriptingEngine, JavaNativeModule crafttweakerModule) {
        
        return BRACKET_RESOLVER_REGISTRY.getBracketResolvers(name, scriptingEngine, crafttweakerModule);
    }
    //</editor-fold>
    
    
    //<editor-fold desc="Preprocessor Delegates">
    // ##############################
    // ### Preprocessor Delegates ###
    // ##############################
    public static List<IPreprocessor> getPreprocessors() {
        
        return PREPROCESSOR_REGISTRY.getPreprocessors();
    }
    //</editor-fold>
    
    //<editor-fold desc="Recipe Handler Delegates">
    // ################################
    // ### Recipe Handler Delegates ###
    // ################################
    public static <T extends Recipe<?>> IRecipeHandler<T> getHandlerFor(final T recipe) {
        
        return RECIPE_HANDLER_REGISTRY.getHandlerFor(recipe);
    }
    
    private static boolean checkModDeps(Either<ZenRegister, Map<String, Object>> annotationData) {
        
        return annotationData.map(zenRegister -> Arrays.stream(zenRegister.modDeps())
                .allMatch(Services.PLATFORM::isModLoaded), map ->
                ((List<String>) map.getOrDefault("modDeps", new ArrayList<String>(0))).stream()
                        .allMatch(Services.PLATFORM::isModLoaded));
    }
    //</editor-fold>
    
    //<editor-fold desc="Bracket Enums">
    public static <T extends Enum<T>> T getBracketEnumValue(final ResourceLocation type, final String value) {
        
        if(BRACKET_ENUM_REGISTRY.getEnums().containsKey(type)) {
            Class<T> enumClass = (Class<T>) BRACKET_ENUM_REGISTRY.getEnums().get(type);
            return Enum.valueOf(enumClass, value.toUpperCase(Locale.ROOT));
        }
        throw new IllegalArgumentException("No enum found for given type: '" + type + "'");
    }
    
    public static <T extends Enum<T>> Class<Enum<?>> getBracketEnum(final ResourceLocation type) {
        
        if(BRACKET_ENUM_REGISTRY.getEnums().containsKey(type)) {
            return BRACKET_ENUM_REGISTRY.getEnums().get(type);
        }
        throw new IllegalArgumentException("No enum found for given type: '" + type + "'");
    }
    
    public static boolean hasBracketEnum(final ResourceLocation type) {
        
        return BRACKET_ENUM_REGISTRY.getEnums().containsKey(type);
    }
    
    // To keep the encapsulation on getEnums
    public static Set<String> collectBracketEnums() {
        
        Set<String> set = new HashSet<>();
        
        for(ResourceLocation rl : BRACKET_ENUM_REGISTRY.getEnums().keySet()) {
            Class<Enum<?>> enumClass = BRACKET_ENUM_REGISTRY.getEnums().get(rl);
            Arrays.stream(enumClass.getEnumConstants())
                    .map(anEnum -> "<constant:" + rl + ":" + anEnum.name().toLowerCase(Locale.ROOT) + ">")
                    .forEach(set::add);
        }
        
        return set;
        
    }
    // </editor-fold>
}
