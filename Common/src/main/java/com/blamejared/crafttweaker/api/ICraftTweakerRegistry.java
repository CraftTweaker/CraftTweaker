package com.blamejared.crafttweaker.api;

import com.blamejared.crafttweaker.api.command.type.IBracketDumperInfo;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.IZenClassRegistry;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunModuleConfigurator;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import org.openzen.zenscript.parser.BracketExpressionParser;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Provides access to information stored by all CraftTweaker registries.
 *
 * <p>An instance of this class can be obtained through {@link CraftTweakerAPI}.</p>
 *
 * @since 9.1.0
 */
public interface ICraftTweakerRegistry {
    
    /**
     * Attempts to find a {@link IScriptLoader} with the given name, if available.
     *
     * <p>This method can be called only after all registries have been successfully built: attempting to call this
     * method before that might lead to undefined behavior. It is also illegal to attempt to query the global loader
     * through this method.</p>
     *
     * @param name The name of the loader that should be found. It cannot be the global loader.
     *
     * @return The {@link IScriptLoader} with the given name, if available.
     *
     * @throws IllegalArgumentException If the name does not identify a known loader, or if it refers to the global
     *                                  loader.
     * @since 9.1.0
     */
    IScriptLoader findLoader(final String name);
    
    /**
     * Obtains a {@link Collection} of all {@link IScriptLoader}s known to CraftTweaker.
     *
     * @return A read-only collection of all known loaders.
     *
     * @since 9.1.0
     */
    Collection<IScriptLoader> getAllLoaders();
    
    /**
     * Attempts to find a {@link IScriptLoadSource} with the given id, if possible.
     *
     * <p>This method can be called only after all registries have been successfully built: attempting to call this
     * method before that might lead to undefined behavior.</p>
     *
     * @param id The id of the load source that should be found.
     *
     * @return The {@link IScriptLoadSource} with the given id, if available.
     *
     * @throws IllegalArgumentException If the name does not identify a known load source.
     * @since 9.1.0
     */
    IScriptLoadSource findLoadSource(final ResourceLocation id);
    
    /**
     * Gets the global {@link IZenClassRegistry}.
     *
     * @return The global {@link IZenClassRegistry}.
     *
     * @since 9.1.0
     */
    IZenClassRegistry getZenClassRegistry();
    
    /**
     * Obtains the {@link IScriptRunModuleConfigurator} that has been registered for the given {@link IScriptLoader}.
     *
     * <p>This method must be called only after all registries have been successfully built: earlier calls will result
     * in undefined behavior.</p>
     *
     * @param loader The loader for which the configurator should be obtained for.
     *
     * @return The corresponding {@link IScriptRunModuleConfigurator}.
     *
     * @since 9.1.0
     */
    IScriptRunModuleConfigurator getConfiguratorFor(final IScriptLoader loader);
    
    /**
     * Obtains a read-only {@link Map} of all bracket dumpers registered for the given {@link IScriptLoader}.
     *
     * <p>Each entry has a key representing the name of the bracket expression for which the dumper is for and a value
     * corresponding to the {@link IBracketDumperInfo} for that bracket expression.</p>
     *
     * @param loader The loader for which the bracket dumpers should be obtained.
     *
     * @return A {@link Map} containing the requested data.
     *
     * @since 9.1.0
     */
    Map<String, IBracketDumperInfo> getBracketDumpers(final IScriptLoader loader);
    
    /**
     * Obtains a {@link List} containing all known bracket handlers for the given {@link IScriptLoader} residing in
     * the {@code rootPackage}.
     *
     * <p>The concept of package used in this method corresponds to the ZenCode concept instead of the Java concept.
     * This means that also bracket handlers in the various sub-packages will also be queried.</p>
     *
     * @param loader      The {@link IScriptLoader} for which bracket handlers should be queried.
     * @param rootPackage The root package under which the bracket handlers should reside.
     *
     * @return A {@link List} of {@link Pair}s whose first element represents the name of the bracket and the second the
     * {@link BracketExpressionParser} responsible for resolution.
     *
     * @apiNote The value of {@code rootPackage} is currently ignored as bracket expressions are not tied to ZenCode
     * packages anymore, rather being tied only to the loader.
     * @since 9.1.0
     */
    // TODO("Better API")
    List<Pair<String, BracketExpressionParser>> getBracketHandlers(final IScriptLoader loader, final String rootPackage);
    
    /**
     * Gets a read-only {@link List} of all known {@linkplain IPreprocessor preprocessors}.
     *
     * @return All known preprocessors.
     *
     * @since 9.1.0
     */
    List<IPreprocessor> getPreprocessors();
    
    /**
     * Obtains the {@link IRecipeHandler} responsible for the given recipe.
     *
     * @param recipe The recipe whose handler should be identified.
     * @param <T>    The type of the recipe whose handler should be identified.
     *
     * @return A {@link IRecipeHandler} that is able to deal with the given recipe.
     *
     * @since 9.1.0
     */
    <T extends Recipe<?>> IRecipeHandler<T> getRecipeHandlerFor(final T recipe);
    
    /**
     * Obtains the enumeration constant that corresponds to the given {@link IScriptLoader}, id, and value as if it were
     * looked up from the {@code <}{@code constant>} bracket handler.
     *
     * @param loader The {@link IScriptLoader} under which the enumeration should be queried.
     * @param type   The {@link ResourceLocation} that uniquely identifies the enumeration.
     * @param value  The value of the enumeration that should be obtained.
     * @param <T>    The type of the target enumeration.
     *
     * @return The enumeration value that would be obtained by the bracket handler.
     *
     * @since 9.1.0
     */
    <T extends Enum<T>> T getEnumBracketValue(final IScriptLoader loader, final ResourceLocation type, final String value);
    
    /**
     * Obtains the enumeration class that corresponds to the given id in the queried {@link IScriptLoader}.
     *
     * @param loader The {@link IScriptLoader} under which the enumeration should be queried.
     * @param type   The {@link ResourceLocation} that uniquely identifies the enumeration.
     * @param <T>    The type of the target enumeration.
     *
     * @return An {@link Optional} wrapping the {@link Class} of the enumeration if it can be identified, an
     * {@linkplain Optional#empty() empty optional} otherwise.
     *
     * @since 9.1.0
     */
    <T extends Enum<T>> Optional<Class<T>> getEnumBracketFor(final IScriptLoader loader, final ResourceLocation type);
    
    /**
     * Gets all bracket handler possibilities that can be queried under the given {@link IScriptLoader}.
     *
     * <p>The data of the returned set corresponds to strings of the form
     * {@code <}{@code constant:}<em>id</em>{@code :}<em>constant</em>{@code >}, where <em>id</em> represents the unique
     * identifier of the enumeration in the given loader and <em>constant</em> the name of the constant under that
     * enumeration.</p>
     *
     * @param loader The {@link IScriptLoader} for which all valid {@code constant} brackets should be queried.
     *
     * @return A {@link Set} of strings of the previously mentioned format.
     *
     * @since 9.1.0
     */
    Set<String> getAllEnumsForEnumBracket(final IScriptLoader loader);
    
}
