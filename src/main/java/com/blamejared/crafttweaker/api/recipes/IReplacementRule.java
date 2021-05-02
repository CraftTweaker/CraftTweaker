package com.blamejared.crafttweaker.api.recipes;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

public interface IReplacementRule {
    IReplacementRule EMPTY = new IReplacementRule() {
        @Override
        public <T> Optional<T> getReplacement(final T initial, final Class<? super T> type) {
            return Optional.empty();
        }
    
        @Override
        public String describe() {
            return "NO-OP";
        }
    };
    
    @SafeVarargs
    static <T> Optional<T> chain(final Optional<T>... optionals) {
        return Arrays.stream(optionals).filter(Optional::isPresent).findFirst().orElse(Optional.empty());
    }
    
    @SuppressWarnings("unchecked")
    static <T, U> Optional<T> withType(final T ingredient, final Class<? super T> type, final Class<? super U> targetedType, final Function<U, Optional<U>> producer) {
        return targetedType.isAssignableFrom(type)? (Optional<T>) producer.apply((U) ingredient) : Optional.empty();
    }
    
    <T> Optional<T> getReplacement(final T ingredient, final Class<? super T> type);
    
    String describe();
}
