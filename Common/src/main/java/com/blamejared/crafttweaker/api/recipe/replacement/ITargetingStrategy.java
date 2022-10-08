package com.blamejared.crafttweaker.api.recipe.replacement;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.recipe.component.IRecipeComponent;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Function;

/**
 * Represents a way of targeting components for replacement.
 *
 * <p>A targeting strategy is thus responsible for determining how {@link IRecipeComponent}s are treated when dealing
 * with replacement. For example, a strategy might determine that all composite components should be decomposed into
 * atoms.</p>
 *
 * <p>For script writers, a strategy can be obtained with the {@code <targetinstrategy>} bracket handler.</p>
 *
 * <p>For integration writers, an instance of a targeting strategy can be obtained through the {@code find} method. Any
 * newly created strategy must be registered in the CraftTweaker plugin to be available to users.</p>
 *
 * <p>This is a {@link FunctionalInterface} whose functional method is
 * {@link #castStrategy(IRecipeComponent, Object, Function)}.</p>
 *
 * @see #find(ResourceLocation)
 * @see com.blamejared.crafttweaker.api.plugin.ICraftTweakerPlugin#registerRecipeHandlers(com.blamejared.crafttweaker.api.plugin.IRecipeHandlerRegistrationHandler)
 * @since 10.0.0
 */
@Document("vanilla/api/recipe/replacement/ITargetingStrategy")
@FunctionalInterface
@ZenCodeType.Name("crafttweaker.api.recipe.replacement.ITargetingStrategy")
@ZenRegister
public interface ITargetingStrategy extends CommandStringDisplayable {
    
    /**
     * The ID that identifies the strategy used in case of no other strategy being specified.
     *
     * <p>This strategy is guaranteed to always exist.</p>
     *
     * @since 10.0.0
     */
    ResourceLocation DEFAULT_STRATEGY_ID = CraftTweakerConstants.rl("shallow");
    
    /**
     * Obtains a {@link ITargetingStrategy} with the given id, if available.
     *
     * <p>It is invalid to call this method before the {@link IReplacerRegistry} has been fully built: attempting to do
     * that will result in undefined behavior.</p>
     *
     * @param id The id uniquely identifying the strategy to obtain.
     *
     * @return The strategy with the given name.
     *
     * @throws NullPointerException If no strategy with the given name exists
     * @since 10.0.0
     */
    static ITargetingStrategy find(final ResourceLocation id) {
        
        return CraftTweakerAPI.getRegistry().getReplacerRegistry().findStrategy(id);
    }
    
    /**
     * Executes the strategy determined by this {@link ITargetingStrategy} on the given object.
     *
     * <p>It is allowed for a strategy to exhibit different behavior according to the given {@link IRecipeComponent},
     * although such behavior is discouraged.</p>
     *
     * @param component The component that is being analyzed by this strategy.
     * @param object    The object that must be examined for potential replacement.
     * @param replacer  A {@link Function} that is responsible for carrying out the replacement. The argument given to
     *                  the function is the object that should be replaced as determined by the current strategy. It
     *                  might or might not be the same as {@code object} or related to it. If the given object should
     *                  not be replaced, then {@code null} should be returned to indicate this. On the contrary, any
     *                  non-{@code null} object is implicitly determined to be a replacement, even if it matches the
     *                  input parameter. This allows for further flexibility in the replacement behavior.
     * @param <T>       The type of the object pointed to by the component.
     *
     * @return The replaced object as determined by a combination of the {@code replacer} {@link Function} and this
     * strategy. In particular, this strategy must return {@code null} if all invocations of the {@code replacer} have
     * returned {@code null}. This means that no replacement has been carried out. On the contrary, if at least one of
     * the invocations has returned a non-{@code null} value, then the return value of this function must also be
     * non-{@code null}. It is up to the implementation to determine what to return.
     *
     * @since 10.0.0
     */
    @ZenCodeType.Nullable <T> T castStrategy(final IRecipeComponent<T> component, final T object, final Function<T, @ZenCodeType.Nullable T> replacer);
    
    @Override
    default String getCommandString() {
        
        final IReplacerRegistry registry = CraftTweakerAPI.getRegistry().getReplacerRegistry();
        return registry.allStrategyNames()
                .stream()
                .filter(it -> registry.findStrategy(it) == this)
                .findFirst()
                .map(ResourceLocation::toString)
                .map("<targetingstrategy:%s>"::formatted)
                .orElseThrow();
    }
    
}
