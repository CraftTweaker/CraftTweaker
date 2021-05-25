package com.blamejared.crafttweaker.api.recipes;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

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
     * <p>This replacement rule simply returns {@link Optional#empty()} in {@link #getReplacement(Object, Class)} for
     * any possible set of parameters, representing effectively a no-op replacement rule.</p>
     */
    IReplacementRule EMPTY = new IReplacementRule() {
        @Override
        public <T> Optional<T> getReplacement(final T initial, final Class<T> type) {
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
     * @param <T> The type parameter of the various optionals.
     * @return The first non-empty optional, if present, or {@link Optional#empty()} otherwise.
     *
     * @see #withType(Object, Class, Class, Function)
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
     * @param ingredient The ingredient that should be replaced; its value <strong>should</strong> match the input of
     *                   {@link #getReplacement(Object, Class)}.
     * @param type The type of the {@code ingredient} that should be replaced; its value <strong>should</strong> match
     *             the input of {@link #getReplacement(Object, Class)}.
     * @param targetedType The type the ingredient should have for it to be operated upon by the {@code producer}. This
     *                     value will be compared to {@code type} with a direct equality check (i.e.
     *                     {@code type == targetedType}).
     * @param producer A {@link Function} that takes an ingredient of type {@code targetedType} as an input and replaces
     *                 it, returning either an {@link Optional} with the ingredient, or {@link Optional#empty()} if the
     *                 ingredient cannot be replaced.
     * @param <T> The type of the ingredient that is passed to the function; its value <strong>should</strong> match the
     *            one of {@link #getReplacement(Object, Class)}.
     * @param <U> The type of the ingredient that the {@code producer} recognizes.
     * @return An {@link Optional} containing the replaced ingredient, if {@code type} matches {@code targetedType} and
     * {@code producer} determines that a replacement of the ingredient is needed; {@link Optional#empty()} in all other
     * cases.
     *
     * @see #chain(Optional[])
     */
    static <T, U> Optional<T> withType(final T ingredient, final Class<T> type, final Class<U> targetedType, final Function<U, Optional<U>> producer) {
        // Those casts are effectively no-ops
        return targetedType == type? producer.apply(targetedType.cast(ingredient)).map(type::cast) : Optional.empty();
    }
    
    /**
     * Attempts to replace the given ingredient, with type {@code type}, according to the rules defined by this
     * replacement rule.
     *
     * @implSpec The ingredient should be replaced only according to its {@code type}. No additional checks on the
     * actual runtime type of the ingredient should be performed.
     *
     * @param ingredient The ingredient that should be replaced.
     * @param type The type of the ingredient that should be replaced. Its value may or may not correspond to the actual
     *             ingredient's class, although it's guaranteed to be one of its superclasses (in other words, it is
     *             guaranteed that {@code type.isAssignableFrom(ingredient.getClass())}, but the equality check
     *             {@code type == ingredient.getClass()} is not guaranteed).
     * @param <T> The type of the ingredient that should be replaced.
     * @return An {@link Optional} containing the replaced ingredient, if this rule knows how to operate on the
     * ingredient's type and deems that the ingredient should be replaced; {@link Optional#empty()} otherwise.
     */
    <T> Optional<T> getReplacement(final T ingredient, final Class<T> type);
    
    /**
     * Describes in a short and simple sentence the behavior of this rule.
     *
     * @apiNote This string will be used in user interactions and log output. For this reason, it should be descriptive
     * yet concise at the same time.
     *
     * @return The description of this rule.
     */
    String describe();
}
