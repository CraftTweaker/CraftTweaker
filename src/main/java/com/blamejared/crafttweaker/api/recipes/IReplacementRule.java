package com.blamejared.crafttweaker.api.recipes;

import net.minecraft.item.crafting.IRecipe;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 * Represents a rule used for replacement of various ingredients inside a recipe.
 *
 * <p>Each replacement rule is responsible for replacing ingredients based on their type class and the rule that the
 * implementation defines. It is not required for a replacement rule to apply the same way to different ingredient types
 * nor to know how to apply itself to every possible ingredient type.</p>
 */
public interface IReplacementRule {
    
    /**
     * Represents a rule that does nothing.
     *
     * <p>This replacement rule simply returns {@link Optional#empty()} in
     * {@link #getReplacement(Object, Class, IRecipe)} for any possible set of parameters, representing effectively a
     * no-op replacement rule.</p>
     */
    IReplacementRule EMPTY = new IReplacementRule() {
        @Override
        public <T, U extends IRecipe<?>> Optional<T> getReplacement(T ingredient, Class<T> type, U recipe) {
            
            return Optional.empty();
        }
        
        @Override
        public String describe() {
            
            return "NO-OP";
        }
    };
    
    /**
     * Chains a series of {@link Optional}s together, returning the first non-empty one, if available.
     *
     * @param optionals The series of {@code Optional}s to check.
     * @param <T>       The type parameter of the various optionals.
     *
     * @return The first non-empty optional, if present, or {@link Optional#empty()} otherwise.
     *
     * @see #withType(Object, Class, IRecipe, Class, BiFunction)
     */
    @SafeVarargs
    static <T> Optional<T> chain(final Optional<T>... optionals) {
        
        return Arrays.stream(optionals).filter(Optional::isPresent).findFirst().orElseGet(Optional::empty);
    }
    
    /**
     * Attempts to apply the given {@code producer} if the ingredient type matches the specified one.
     *
     * <p>The function effectively represents the replacement rule that will be applied to {@code ingredient}, as long
     * as {@code type} is the same as {@code targetedType}. This effectively provides a self-contained method to perform
     * these checks without having to resort to unchecked casts in the implementation of the replacement rule.</p>
     *
     * <p>If the ingredient doesn't match the given type, then it's assumed that the given {@code producer} cannot
     * operate on the ingredient, and {@link Optional#empty()} is returned.</p>
     *
     * @param ingredient   The ingredient that should be replaced; its value <strong>should</strong> match the input of
     *                     {@link #getReplacement(Object, Class, IRecipe)}.
     * @param type         The type of the {@code ingredient} that should be replaced; its value <strong>should</strong> match
     *                     the input of {@link #getReplacement(Object, Class, IRecipe)}.
     * @param recipe       The recipe that is currently being acted upon, or {@code null} if this information cannot be
     *                     provided; its value <strong>should</strong> match the input of
     *                     {@link #getReplacement(Object, Class, IRecipe)}.
     * @param targetedType The type the ingredient should have for it to be operated upon by the {@code producer}. This
     *                     value will be compared to {@code type} with a direct equality check (i.e.
     *                     {@code type == targetedType}).
     * @param producer     A {@link BiFunction} that takes an ingredient of type {@code targetedType} and the targeted
     *                     recipe as an input and replaces the ingredient, returning either an {@link Optional} with the
     *                     ingredient, or {@link Optional#empty()} if the ingredient cannot be replaced.
     * @param <T>          The type of the ingredient that is passed to the function; its value <strong>should</strong> match the
     *                     one of {@link #getReplacement(Object, Class, IRecipe)}.
     * @param <U>          The type of the ingredient that the {@code producer} recognizes.
     * @param <S>          The type of the recipe that is currently being replaced; its value <strong>should</strong> match the
     *                     one of {@link #getReplacement(Object, Class, IRecipe)}.
     *
     * @return An {@link Optional} containing the replaced ingredient, if {@code type} matches {@code targetedType} and
     * {@code producer} determines that a replacement of the ingredient is needed; {@link Optional#empty()} in all other
     * cases.
     *
     * @see #chain(Optional[])
     */
    static <T, U, S extends IRecipe<?>> Optional<T> withType(final T ingredient, final Class<T> type, final S recipe,
                                                             final Class<U> targetedType, final BiFunction<U, S, Optional<U>> producer) {
        // Those casts are effectively no-ops
        return targetedType == type ? producer.apply(targetedType.cast(ingredient), recipe)
                .map(type::cast) : Optional.empty();
    }
    
    /**
     * Attempts to replace the given ingredient, with type {@code type}, according to the rules defined by this
     * replacement rule.
     *
     * @param ingredient The ingredient that should be replaced.
     * @param type       The type of the ingredient that should be replaced. Its value may or may not correspond to the actual
     *                   ingredient's class, although it's guaranteed to be one of its superclasses (in other words, it is
     *                   guaranteed that {@code type.isAssignableFrom(ingredient.getClass())}, but the equality check
     *                   {@code type == ingredient.getClass()} is not guaranteed).
     * @param recipe     The recipe that is currently being subjected to replacement, if any; {@code null} otherwise.
     * @param <T>        The type of the ingredient that should be replaced.
     * @param <U>        The type of the recipe that is currently being replaced.
     *
     * @return An {@link Optional} containing the replaced ingredient, if this rule knows how to operate on the
     * ingredient's type and deems that the ingredient should be replaced; {@link Optional#empty()} otherwise.
     *
     * @implSpec The ingredient should be replaced only according to its {@code type}. No additional checks on the
     * actual runtime type of the ingredient should be performed.
     */
    <T, U extends IRecipe<?>> Optional<T> getReplacement(final T ingredient, final Class<T> type, final U recipe);
    
    /**
     * Describes in a short and simple sentence the behavior of this rule.
     *
     * @return The description of this rule.
     *
     * @apiNote This string will be used in user interactions and log output. For this reason, it should be descriptive
     * yet concise at the same time.
     */
    String describe();
    
}
