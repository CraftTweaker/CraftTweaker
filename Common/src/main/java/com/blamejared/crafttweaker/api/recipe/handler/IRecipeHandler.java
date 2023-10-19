package com.blamejared.crafttweaker.api.recipe.handler;


import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Optional;

/**
 * Represents a handler for a specific type of recipe indicated by the generic parameter.
 *
 * <p>Differently from {@link IRecipeManager}, there can be more than one handler for recipe type, since handlers are
 * bound to the actual class type of the recipe in question (e.g. {@code ShapelessRecipe.class}, not
 * {@code minecraft:crafting_shapeless}).</p>
 *
 * @param <T> The generic type the recipe handler can receive. Refer to the implementation specifications for more
 *            information.
 *
 * @implSpec Implementations of this interface will be discovered via classpath scanning for the {@link For} annotation.
 * The generic specialization of the implementation should match the one specified in {@link For#value()} for classes
 * annotated with a single annotation (e.g., a class annotated with {@code @For(MyRecipe.class)} should implement
 * {@code IRecipeHandler<MyRecipe>}). Implementations annotated with more than one annotation should instead specialize
 * with the closest super-class possible that allows them to correctly elaborate all the instances (e.g., consider two
 * classes {@code Foo} and {@code Bar}, both extending {@code Baz}; a class annotated with both {@code @For(Foo.class)}
 * and {@code @For(Bar.class)} should implement {@code IRecipeHandler<Baz>}). Classes annotated with
 * {@code @For(IRecipe.class)} will be ignored. Alternatively, instances of this class can be registered directly
 * through an {@link com.blamejared.crafttweaker.api.plugin.ICraftTweakerPlugin}.
 * @since 9.0.0
 */
public interface IRecipeHandler<T extends Recipe<?>> {
    
    /**
     * Annotates an {@link IRecipeHandler} indicating which recipe classes it is able to handle.
     *
     * <p>This annotation is only required if the recipe handler needs to be discovered via annotation scanning.
     * Registering an instance manually through a plugin does not require the annotation to be present.</p>
     *
     * <p>This annotation is {@link Repeatable}.</p>
     *
     * @see IRecipeHandler
     * @since 9.0.0
     */
    @Documented
    @Repeatable(For.Container.class)
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface For {
        
        /**
         * Container for the {@link For} annotation.
         *
         * @see For
         * @see IRecipeHandler
         * @since 9.0.0
         */
        @Documented
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.TYPE)
        @interface Container {
            
            /**
             * The container for the repetitions of the {@link For} annotation.
             *
             * @return An array containing all {@link For} instances.
             *
             * @since 9.0.0
             */
            For[] value();
            
        }
        
        /**
         * Indicates the recipe class the annotated {@link IRecipeHandler} is able to recognize and subsequently handle.
         *
         * @return The recipe class handled by this handler.
         *
         * @see IRecipeHandler
         * @since 9.0.0
         */
        Class<? extends Recipe<?>> value();
        
    }
    
    /**
     * Creates a String representation of a valid {@code addRecipe} (or alternative) call for the given subclass of
     * {@link Recipe}.
     *
     * <p>Recipe dumps are triggered by the {@code /ct recipes} or {@code /ct recipes hand} commands.</p>
     *
     * <p>All newlines added to either the start or the end of the string will be automatically trimmed.</p>
     *
     * @param manager        The recipe manager responsible for this kind of recipes.
     * @param registryAccess Access to registries to get the output of recipes.
     * @param holder         The recipe that is currently being dumped.
     *
     * @return A String representing a {@code addRecipe} (or similar) call.
     *
     * @since 9.0.0
     */
    String dumpToCommandString(final IRecipeManager<? super T> manager, final RegistryAccess registryAccess, final RecipeHolder<T> holder);
    
    /**
     * Checks if the two recipes conflict with each other.
     *
     * <p>In this case, a conflict is defined as the two recipes being made in the exact same way (e.g. with the same
     * shape and the same ingredients if the two recipes are shaped crafting table ones).</p>
     *
     * <p>Conflicts are also considered symmetrical in this implementation, which means that if {@code firstRecipe}
     * conflicts with {@code secondRecipe}, the opposite is also true.</p>
     *
     * @param manager      The recipe manager responsible for this kind of recipes.
     * @param firstHolder  The recipe which should be checked for conflict.
     * @param secondHolder The other recipe which {@code firstRecipe} should be checked against. The recipe may or may
     *                     not be of the same type of {@code firstRecipe}. See the API note section for more details.
     * @param <U>          The type of {@code secondRecipe}.
     *
     * @return Whether the {@code firstRecipe} conflicts with {@code secondRecipe} or not.
     *
     * @apiNote The reason for which {@code secondRecipe} is specified as simply {@link Recipe} instead of as the
     * generic parameter {@code T} is to allow more flexibility in the conflict checking. In fact, this choice allows
     * for checking to also occur between different types of recipes (e.g. shaped vs shapeless crafting table recipes),
     * allowing for a broader range of checking. Nevertheless, the two recipes are <strong>ensured</strong> to be of the
     * same {@link net.minecraft.world.item.crafting.RecipeType recipe type} (i.e.
     * {@code firstRecipe.getType() == secondRecipe.getType()}).
     * @since 9.0.0
     */
    <U extends Recipe<?>> boolean doesConflict(final IRecipeManager<? super T> manager, final RecipeHolder<T> firstHolder, final RecipeHolder<U> secondHolder);
    
    /**
     * Decomposes a recipe from its complete form into an {@link IDecomposedRecipe}.
     *
     * <p>The decomposition needs to be complete, meaning that any meaningful part of the recipe should be present in
     * the returned decomposed recipe. The only exception is the name, as decomposed recipes only track
     * {@link com.blamejared.crafttweaker.api.recipe.component.IRecipeComponent}s, and names aren't one.</p>
     *
     * <p>It is allowed for an implementation to specify that the given recipe cannot be properly decomposed. Examples
     * of this occurrence might be recipes whose behavior is completely determined by code, such as map cloning in
     * vanilla. In this context, it is allowed to return {@link Optional#empty()}.</p>
     *
     * <p>It is mandatory for a recipe handler to produce a decomposed recipe that can then be converted back into its
     * complete form in {@link #recompose(IRecipeManager, RegistryAccess, ResourceLocation, IDecomposedRecipe)}. In other words, if
     * the return value of this method isn't empty, then
     * {@code recompose(manager, name, decompose(manager, recipe).get())} must not return an empty optional.</p>
     *
     * @param manager        The recipe manager responsible for this kind of recipes.
     * @param registryAccess Access to registries to get the output of recipes.
     * @param holder         The recipe that should be decomposed.
     *
     * @return An {@link Optional} wrapping {@linkplain IDecomposedRecipe decomposed recipe}, or an empty one if need be
     * as specified above.
     *
     * @since 10.0.0
     */
    Optional<IDecomposedRecipe> decompose(final IRecipeManager<? super T> manager, final RegistryAccess registryAccess, final RecipeHolder<T> holder);
    
    /**
     * Reconstructs a complete recipe from its {@linkplain IDecomposedRecipe decomposed form}.
     *
     * <p>The recomposition should be as complete as possible, making sure that all
     * {@link com.blamejared.crafttweaker.api.recipe.component.IRecipeComponent}s that are necessary to properly rebuild
     * the recipe are present in the given {@link IDecomposedRecipe}. If the recipe presents unknown components, i.e.
     * components that this handler doesn't know how to convert, the handler is allowed to throw an exception as
     * detailed in the following paragraphs.</p>
     *
     * <p>It is allowed for an implementation to return {@link Optional#empty()} in case the recomposition fails for
     * any reason, or if no decomposed recipe can be used to rebuild a recipe in its complete form, e.g. for map cloning
     * in vanilla.</p>
     *
     * <p>It is mandatory for a recipe handler that knows how to decompose a recipe to also know how to recompose it. In
     * other words, if {@link #decompose(IRecipeManager, RegistryAccess, RecipeHolder)} returns a non-empty {@code Optional}, then
     * {@code recompose(manager, name, decompose(manager, recipe).get())} must not return an empty optional nor throw
     * an exception.</p>
     *
     * @param manager        The recipe manager responsible for this kind of recipes.
     * @param registryAccess Access to registries.
     * @param name           The name to give to the recomposed recipe once it has been built. It is mandatory that
     *                       {@link RecipeHolder<T>#getId()} and this parameter represent the same name.
     * @param recipe         The {@link IDecomposedRecipe} that should be recomposed back into a complete form.
     *
     * @return An {@link Optional} wrapping the complete form of the recipe, or an empty one if need be as specified
     * above.
     *
     * @throws IllegalArgumentException If any of the required recipe components are not present in the recipe, or they
     *                                  have invalid or meaningless values (e.g. an empty output). Optionally, if any
     *                                  unknown component is present in the decomposed recipe.
     * @since 10.0.0
     */
    Optional<RecipeHolder<T>> recompose(final IRecipeManager<? super T> manager, final RegistryAccess registryAccess, final ResourceLocation name, final IDecomposedRecipe recipe);
    
}
