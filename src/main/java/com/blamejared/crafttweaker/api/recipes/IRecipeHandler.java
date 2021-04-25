package com.blamejared.crafttweaker.api.recipes;

import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import net.minecraft.item.crafting.IRecipe;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Optional;

/**
 * Represents a handler for a specific type of recipe indicated by the generic parameter.
 *
 * <p>Differently from {@link IRecipeManager}, there can be more than one handler for recipe type, since handlers are
 * bound to the actual class type of the recipe in question (e.g. {@code ShapelessRecipe.class}, not
 * {@code minecraft:crafting_shapeless}).</p>
 *
 * <p>A recipe handler is responsible for recipe-class-specific behavior, documented in the following table:</p>
 *
 * <table>
 *     <thead>
 *         <tr>
 *             <th>Behavior</th>
 *             <th>Description</th>
 *             <th>Method Responsible</th>
 *         </tr>
 *     </thead>
 *     <tbody>
 *         <tr>
 *             <td><strong>Recipe Dumping</strong></td>
 *             <td>
 *                 Following a dump command, a recipe may need to be converted into a string that represents how that
 *                 same recipe can be added via a CraftTweaker script.
 *             </td>
 *             <td>{@link IRecipeHandler#dumpToCommandString(IRecipeManager, IRecipe)}</td>
 *         </tr>
 *         <tr>
 *             <td><strong>Ingredient Replacement</strong></td>
 *             <td>
 *                 Following script method calls, a recipe may need to be replaced with an equivalent one, albeit with
 *                 some ingredients replaced with others according to certain {@link ReplacementRule}s.
 *             </td>
 *             <td>{@link IRecipeHandler#replaceIngredients(IRecipeManager, IRecipe, List)}</td>
 *         </tr>
 *     </tbody>
 * </table>
 *
 * @implSpec Implementations of this interface will be discovered via classpath scanning for the {@link For} annotation.
 * The generic specialization of the implementation should match the one specified in {@link For#value()} for classes
 * annotated with a single annotation (e.g., a class annotated with {@code @For(MyRecipe.class)} should implement
 * {@code IRecipeHandler<MyRecipe>}). Implementations annotated with more than one annotation should instead specialize
 * with the closest super-class possible that allows them to correctly elaborate all of the instances (e.g., consider
 * two classes {@code Foo} and {@code Bar}, both extending {@code Baz}; a class annotated with both
 * {@code @For(Foo.class)} and {@code @For(Bar.class)} should implement {@code IRecipeHandler<Baz>}). Classes annotated
 * with {@code @For(IRecipe.class)} will be ignored.
 *
 * @param <T> The generic type the recipe handler can receive. Refer to the implementation specifications for more
 *            information.
 */
public interface IRecipeHandler<T extends IRecipe<?>> {

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
        Class<? extends IRecipe<?>> value();
    }
    
    /**
     * Creates a String representation of a valid {@code addRecipe} (or alternative) call for the given subclass of
     * {@link IRecipe}.
     *
     * <p>Recipe dumps are triggered by the {@code /ct recipes} or {@code /ct recipes hand} commands.</p>
     *
     * <p>All newlines added to either the start or the end of the string will be automatically trimmed.</p>
     *
     * @param manager The recipe manager responsible for this kind of recipes.
     * @param recipe The recipe that is currently being dumped.
     * @return A String representing a {@code addRecipe} (or similar) call.
     */
    String dumpToCommandString(final IRecipeManager manager, final T recipe);
    
    /**
     * Handles the replacement of ingredients according to the given set of {@link ReplacementRule}s for the given
     * subclass of {@link IRecipe}.
     *
     * <p>This method should try to apply all of the applicable rules to the recipe. If one of the rules fails to apply,
     * an error message should be generated via
     * {@link com.blamejared.crafttweaker.api.CraftTweakerAPI#logError(String, Object...)}. Incomplete application of
     * the replacement rules may or may not apply depending on the specific implementation: no specific contracts are
     * enforced by this method.</p>
     *
     * <p>If a particular recipe handler does not support replacement, an {@link UnsupportedOperationException} should
     * be raised, along with a helpful error message. A recipe handler <strong>must</strong> be consistent, meaning that
     * given the same recipe class, the behavior should be consistent: either an exception gets thrown or the
     * replacement gets carried out.</p>
     *
     * @implSpec The {@code rules} list not only indicates the {@link ReplacementRule}s that should be applied, but also
     * the order in which these should be applied. In other words, the rule at position {@code 0} should be applied to
     * the {@code recipe} before the rule in position {@code 1}. <strong>However</strong>, implementations are free to
     * ignore this detail and reorder the rule application to optimize certain applications if needed. This reordering
     * <strong>must</strong> guarantee that the resulting recipe behaves exactly as if the replacements were carried out
     * in order.
     *
     * @implNote By default, this method throws an {@link UnsupportedOperationException}.
     *
     * @param manager The recipe manager responsible for this kind of recipes.
     * @param recipe The recipe whose ingredients should be replaced.
     * @param rules A series of {@link ReplacementRule}s in the order they should be applied. Implementations are
     *              nevertheless allowed to reorder these rules as they see fit. Refer to the implementation
     *              specifications for more details.
     * @return An {@link Optional} holding the replaced recipe, if any replacements have been carried out. If no
     * replacement rule affected the current recipe, the return value should be {@link Optional#empty()}. It is
     * customary, though not required, that the value wrapped by the optional is a completely different object from
     * {@code recipe} (i.e. {@code recipe != result.get()}).
     * @throws UnsupportedOperationException If the current handler does not support replacing for the given recipe
     * class.
     */
    default Optional<T> replaceIngredients(final IRecipeManager manager, final T recipe, final List<ReplacementRule> rules) {
        throw new UnsupportedOperationException("Replacement is not supported for this recipe type");
    }
}
