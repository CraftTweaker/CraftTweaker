package com.blamejared.crafttweaker.api.recipe.handler;


import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

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
 * with the closest super-class possible that allows them to correctly elaborate all of the instances (e.g., consider
 * two classes {@code Foo} and {@code Bar}, both extending {@code Baz}; a class annotated with both
 * {@code @For(Foo.class)} and {@code @For(Bar.class)} should implement {@code IRecipeHandler<Baz>}). Classes annotated
 * with {@code @For(IRecipe.class)} will be ignored.
 */
public interface IRecipeHandler<T extends Recipe<?>> {
    
    /**
     * Annotates a {@link IRecipeHandler} indicating which recipe classes it is able to handle.
     *
     * <p>This annotation is {@link Repeatable}.</p>
     *
     * @see IRecipeHandler
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
         */
        @Documented
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.TYPE)
        @interface Container {
            
            /**
             * The container for the repetitions of the {@link For} annotation.
             *
             * @return An array containing all {@link For} instances.
             */
            For[] value();
            
        }
        
        /**
         * Indicates the recipe class the annotated {@link IRecipeHandler} is able to recognize and subsequently handle.
         *
         * @return The recipe class handled by this handler.
         *
         * @see IRecipeHandler
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
     * @param manager The recipe manager responsible for this kind of recipes.
     * @param recipe  The recipe that is currently being dumped.
     *
     * @return A String representing a {@code addRecipe} (or similar) call.
     */
    String dumpToCommandString(final IRecipeManager<? super T> manager, final T recipe);
    
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
     * @param firstRecipe  The recipe which should be checked for conflict.
     * @param secondRecipe The other recipe which {@code firstRecipe} should be checked against. The recipe may or may
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
     */
    <U extends Recipe<?>> boolean doesConflict(final IRecipeManager<? super T> manager, final T firstRecipe, final U secondRecipe);
    
    Optional<IDecomposedRecipe> decompose(final IRecipeManager<? super T> manager, final T recipe);
    
    Optional<T> recompose(final IRecipeManager<? super T> manager, final ResourceLocation name, final IDecomposedRecipe recipe);
    
}
