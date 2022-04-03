package com.blamejared.crafttweaker.api.zencode;

import com.blamejared.crafttweaker.api.natives.INativeTypeRegistry;
import com.google.common.collect.BiMap;
import com.google.common.collect.Multimap;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Holds information related to the various classes and expansions exposed to ZenCode.
 *
 * <p>An instance of this class can be obtained through {@link IZenClassRegistry}.</p>
 *
 * @since 9.1.0
 */
public interface IZenClassRegistry {
    
    /**
     * Identifies raw information on the classes registered to a specific {@link IScriptLoader}.
     *
     * <p>The provided information is read-only and exists merely to allow raw data access. It is suggested to use
     * the other methods exposed by {@link IZenClassRegistry} instead.</p>
     *
     * @since 9.1.0
     */
    interface IClassData {
        
        /**
         * Gets a read-only list of all the classes registered to this loader, be those regular classes or expansions.
         *
         * @return A list of all classes registered to this loader.
         *
         * @since 9.1.0
         */
        List<Class<?>> registeredClasses();
        
        /**
         * Gets a read-only {@link BiMap} referencing all classes containing globals that should be exposed.
         *
         * <p>The key of the map represents the ZenCode name under which its corresponding value is exposed as.</p>
         *
         * @return A map with all classes containing globals to expose.
         *
         * @since 9.1.0
         */
        BiMap<String, Class<?>> globals();
        
        /**
         * Gets a read-only {@link BiMap} referencing all classes that are exposed to ZenCode in this loader.
         *
         * <p>The key of the map represents the ZenCode name under which its corresponding value is exposed as.</p>
         *
         * @return A map with all exposed classes.
         *
         * @since 9.1.0
         */
        BiMap<String, Class<?>> classes();
        
        /**
         * Gets a read-only {@link Multimap} referencing all expansions for the various types known to ZenCode for this
         * loader.
         *
         * <p>The key of the map represents the ZenCode name for the type that the corresponding values are expanding.
         * It does <strong>not</strong> represent the name of the expansion themselves.</p>
         *
         * @return A map with all exposed expansions.
         *
         * @since 9.1.0
         */
        Multimap<String, Class<?>> expansions();
        
    }
    
    /**
     * Verifies whether the given class is registered and thus exposed to the given loader.
     *
     * @param loader The {@link IScriptLoader} for which exposure data should be checked for.
     * @param clazz  The class whose exposure should be checked.
     *
     * @return Whether the class is exposed to the loader or not.
     *
     * @since 9.1.0
     */
    boolean isRegistered(final IScriptLoader loader, final Class<?> clazz);
    
    /**
     * Attempts to identify the name under which the target class is exposed to ZenCode in the given loader.
     *
     * @param loader The {@link IScriptLoader} for which the name should be identified.
     * @param clazz  The class whose name should be identified.
     *
     * @return An {@link Optional} wrapping the name of the class if it is exposed, an
     * {@linkplain Optional#empty() empty optional} if the class is not exposed.
     *
     * @since 9.1.0
     */
    Optional<String> getNameFor(final IScriptLoader loader, final Class<?> clazz);
    
    /**
     * Gets all non-abstract classes that extend or implement the given target for the specified loader.
     *
     * @param loader The {@link IScriptLoader} for which implementations should be found.
     * @param target The class or interface for which implementations should be identified.
     * @param <T>    The type the returned classes should be implementing or extending.
     *
     * @return A {@link List} of non-abstract classes that extend or implement the given target, if any.
     *
     * @since 9.1.0
     */
    <T> List<Class<? extends T>> getImplementationsOf(final IScriptLoader loader, final Class<T> target);
    
    /**
     * Gets the raw class data for the given loader.
     *
     * <p>Access to the raw class data is discouraged, as other methods of querying classes should be preferred.
     * Nevertheless, access is permitted as there might be some instances where the raw data needs to be used. Such data
     * access is regardless provided as read-only, to avoid unwanted tampering.</p>
     *
     * @param loader The {@link IScriptLoader} for which the raw class data should be obtained.
     *
     * @return An instance of {@link IClassData} containing the raw class data for the target loader.
     *
     * @since 9.1.0
     */
    IClassData getClassData(final IScriptLoader loader);
    
    /**
     * Gets all classes that are located in the given package among the ones exposed to the targeted loader.
     *
     * <p>The concept of package used in this context is the ZenCode concept, instead of the Java one. This means that
     * subpackages of the given package are also queried.</p>
     *
     * @param loader      The {@link IScriptLoader} for which the data should be gathered.
     * @param packageName The name of the package the various classes should be located in.
     *
     * @return A {@link List} of all the classes located in the target package.
     *
     * @since 9.1.0
     */
    List<Class<?>> getClassesInPackage(final IScriptLoader loader, final String packageName);
    
    /**
     * Gets all classes that expose globals located in the given package among the ones exposed to the targeted loader.
     *
     * <p>The concept of package used in this context is the ZenCode concept, instead of the Java one. This means that
     * subpackages of the given package are also queried.</p>
     *
     * @param loader      The {@link IScriptLoader} for which the data should be gathered.
     * @param packageName The name of the package the various global-exposing classes should be located in.
     *
     * @return A {@link List} of all the global-exposing classes located in the target package.
     *
     * @since 9.1.0
     */
    List<Class<?>> getGlobalsInPackage(final IScriptLoader loader, final String packageName);
    
    /**
     * Gets the set of all the packages that act as root for the given loader.
     *
     * @param loader The {@link IScriptLoader} for which root packages should be gathered.
     *
     * @return A {@link Set} containing all root packages.
     *
     * @since 9.1.0
     */
    Set<String> getRootPackages(final IScriptLoader loader);
    
    /**
     * Obtains the {@link INativeTypeRegistry} for the given loader.
     *
     * @param loader The {@link IScriptLoader} for which the native type registry should be obtained.
     *
     * @return The loader's corresponding {@link INativeTypeRegistry}.
     *
     * @since 9.1.0
     */
    INativeTypeRegistry getNativeTypeRegistry(final IScriptLoader loader);
    
    /**
     * Checks whether the given class has been blacklisted from registration.
     *
     * <p>A blacklisted class will never be registered to any loader because its registration might fail due to
     * unsatisfied requirements. A reason for the blacklist can be identified through the log.</p>
     *
     * <!-- TODO("Expose something like getBlacklistReason") -->
     *
     * @param clazz The class to check for blacklisting.
     *
     * @since 9.1.0
     */
    boolean isBlacklisted(final Class<?> clazz);
    
}
