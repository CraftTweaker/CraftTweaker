package com.blamejared.crafttweaker.gametest.truth;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.truth.IntStreamSubject;
import com.google.common.truth.LongStreamSubject;
import com.google.common.truth.OptionalDoubleSubject;
import com.google.common.truth.OptionalIntSubject;
import com.google.common.truth.OptionalLongSubject;
import com.google.common.truth.OptionalSubject;
import com.google.common.truth.PathSubject;
import com.google.common.truth.StreamSubject;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.nio.file.Path;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Basic wrapper around {@link com.google.common.truth.Truth} to account for the custom fail strategy.
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public interface ITruth8Wrapper extends IExpect {
    
    default OptionalSubject assertThat(@Nullable Optional<?> target) {
        
        return expect().about(OptionalSubject.optionals()).that(target);
    }
    
    default OptionalIntSubject assertThat(@Nullable OptionalInt target) {
        
        return expect().about(OptionalIntSubject.optionalInts()).that(target);
    }
    
    default OptionalLongSubject assertThat(@Nullable OptionalLong target) {
        
        return expect().about(OptionalLongSubject.optionalLongs()).that(target);
    }
    
    default OptionalDoubleSubject assertThat(@Nullable OptionalDouble target) {
        
        return expect().about(OptionalDoubleSubject.optionalDoubles()).that(target);
    }
    
    default StreamSubject assertThat(@Nullable Stream<?> target) {
        
        return expect().about(StreamSubject.streams()).that(target);
    }
    
    default IntStreamSubject assertThat(@Nullable IntStream target) {
        
        return expect().about(IntStreamSubject.intStreams()).that(target);
    }
    
    default LongStreamSubject assertThat(@Nullable LongStream target) {
        
        return expect().about(LongStreamSubject.longStreams()).that(target);
    }
    
    @GwtIncompatible
    default PathSubject assertThat(@Nullable Path target) {
        
        return expect().about(PathSubject.paths()).that(target);
    }
    
}
