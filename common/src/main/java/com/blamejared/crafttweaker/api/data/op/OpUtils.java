package com.blamejared.crafttweaker.api.data.op;

import java.util.function.BinaryOperator;

final class OpUtils {
    private OpUtils() {}
    
    static <S> BinaryOperator<S> noCombiner() {
        return (a, b) -> {
            throw new IllegalStateException("Combiner unavailable: this should never be reached");
        };
    }
}
