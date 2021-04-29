package com.blamejared.crafttweaker.api.recipes;

import java.util.Optional;

public interface IReplacementRule<T> {
    IReplacementRule<?> EMPTY = new IReplacementRule<Object>() {
        @Override
        public Optional<Object> getReplacement(final Object initial) {
            return Optional.empty();
        }
    
        @Override
        public String describe() {
            return "NO-OP";
        }
    
        @Override
        public Class<Object> getTargetedType() {
            return Object.class;
        }
    };
    
    Optional<T> getReplacement(final T initial);
    
    String describe();
    
    Class<T> getTargetedType();
    
    @SuppressWarnings("unchecked")
    default <U> IReplacementRule<U> cast() {
        return (IReplacementRule<U>) this;
    }
}
