package com.blamejared.crafttweaker.gametest.truth;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Table;
import com.google.common.truth.BigDecimalSubject;
import com.google.common.truth.BooleanSubject;
import com.google.common.truth.ClassSubject;
import com.google.common.truth.ComparableSubject;
import com.google.common.truth.CustomSubjectBuilder;
import com.google.common.truth.DoubleSubject;
import com.google.common.truth.FloatSubject;
import com.google.common.truth.IntegerSubject;
import com.google.common.truth.IterableSubject;
import com.google.common.truth.LongSubject;
import com.google.common.truth.MapSubject;
import com.google.common.truth.MultimapSubject;
import com.google.common.truth.MultisetSubject;
import com.google.common.truth.ObjectArraySubject;
import com.google.common.truth.PrimitiveBooleanArraySubject;
import com.google.common.truth.PrimitiveByteArraySubject;
import com.google.common.truth.PrimitiveCharArraySubject;
import com.google.common.truth.PrimitiveDoubleArraySubject;
import com.google.common.truth.PrimitiveFloatArraySubject;
import com.google.common.truth.PrimitiveIntArraySubject;
import com.google.common.truth.PrimitiveLongArraySubject;
import com.google.common.truth.PrimitiveShortArraySubject;
import com.google.common.truth.SimpleSubjectBuilder;
import com.google.common.truth.StandardSubjectBuilder;
import com.google.common.truth.StringSubject;
import com.google.common.truth.Subject;
import com.google.common.truth.TableSubject;
import com.google.common.truth.ThrowableSubject;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Map;

/**
 * Basic wrapper around {@link com.google.common.truth.Truth} to account for the custom fail strategy.
 */
public interface ITruthWrapper extends IExpect {
    
    default StandardSubjectBuilder assertWithMessage(String format, Object... args) {
        
        return expect().withMessage(MessageFormat.format(format, args));
    }
    
    default <S extends Subject, T> SimpleSubjectBuilder<S, T> assertAbout(Subject.Factory<S, T> factory) {
        
        return expect().about(factory);
    }
    
    default <CustomSubjectBuilderT extends CustomSubjectBuilder> CustomSubjectBuilderT assertAbout(CustomSubjectBuilder.Factory<CustomSubjectBuilderT> factory) {
        
        return expect().about(factory);
    }
    
    default <T extends Comparable<?>> ComparableSubject<T> assertThat(@Nullable T actual) {
        
        return expect().that(actual);
    }
    
    default BigDecimalSubject assertThat(@Nullable BigDecimal actual) {
        
        return expect().that(actual);
    }
    
    default Subject assertThat(@Nullable Object actual) {
        
        return expect().that(actual);
    }
    
    default ClassSubject assertThat(@Nullable Class<?> actual) {
        
        return expect().that(actual);
    }
    
    default ThrowableSubject assertThat(@Nullable Throwable actual) {
        
        return expect().that(actual);
    }
    
    default LongSubject assertThat(@Nullable Long actual) {
        
        return expect().that(actual);
    }
    
    default DoubleSubject assertThat(@Nullable Double actual) {
        
        return expect().that(actual);
    }
    
    default FloatSubject assertThat(@Nullable Float actual) {
        
        return expect().that(actual);
    }
    
    default IntegerSubject assertThat(@Nullable Integer actual) {
        
        return expect().that(actual);
    }
    
    default BooleanSubject assertThat(@Nullable Boolean actual) {
        
        return expect().that(actual);
    }
    
    default StringSubject assertThat(@Nullable String actual) {
        
        return expect().that(actual);
    }
    
    default IterableSubject assertThat(@Nullable Iterable<?> actual) {
        
        return expect().that(actual);
    }
    
    default <T> ObjectArraySubject<T> assertThat(@Nullable T[] actual) {
        
        return expect().that(actual);
    }
    
    default PrimitiveBooleanArraySubject assertThat(boolean[] actual) {
        
        return expect().that(actual);
    }
    
    default PrimitiveShortArraySubject assertThat(short[] actual) {
        
        return expect().that(actual);
    }
    
    default PrimitiveIntArraySubject assertThat(int[] actual) {
        
        return expect().that(actual);
    }
    
    default PrimitiveLongArraySubject assertThat(long[] actual) {
        
        return expect().that(actual);
    }
    
    default PrimitiveByteArraySubject assertThat(byte[] actual) {
        
        return expect().that(actual);
    }
    
    default PrimitiveCharArraySubject assertThat(char[] actual) {
        
        return expect().that(actual);
    }
    
    default PrimitiveFloatArraySubject assertThat(float[] actual) {
        
        return expect().that(actual);
    }
    
    default PrimitiveDoubleArraySubject assertThat(double[] actual) {
        
        return expect().that(actual);
    }
    
    default MapSubject assertThat(@Nullable Map<?, ?> actual) {
        
        return expect().that(actual);
    }
    
    default MultimapSubject assertThat(@Nullable Multimap<?, ?> actual) {
        
        return expect().that(actual);
    }
    
    default MultisetSubject assertThat(@Nullable Multiset<?> actual) {
        
        return expect().that(actual);
    }
    
    default TableSubject assertThat(@Nullable Table<?, ?, ?> actual) {
        
        return expect().that(actual);
    }
    
}
