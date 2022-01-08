package com.blamejared.crafttweaker.gametest.truth.subject.base.data;

import com.blamejared.crafttweaker.api.data.base.INumberData;
import com.google.common.truth.ComparableSubject;
import com.google.common.truth.DoubleSubject;
import com.google.common.truth.FailureMetadata;
import com.google.common.truth.FloatSubject;
import com.google.common.truth.IntegerSubject;
import com.google.common.truth.LongSubject;
import javax.annotation.Nullable;

public abstract class INumberDataSubjectBase<T extends INumberData, U extends INumberDataSubjectBase<T, U>> extends IDataSubjectBase<T, U> {
    
    protected INumberDataSubjectBase(FailureMetadata metadata, @Nullable T actual) {
        
        super(metadata, actual);
    }
    
    public LongSubject asLong() {
        
        return check("getLong()").that(getActual().getLong());
    }
    
    public IntegerSubject asInt() {
        
        return check("getInt()").that(getActual().getInt());
    }
    
    public ComparableSubject<Short> asShort() {
        
        return check("getShort()").that(getActual().getShort());
    }
    
    public ComparableSubject<Byte> asByte() {
        
        return check("getByte()").that(getActual().getByte());
    }
    
    public DoubleSubject asDouble() {
        
        return check("getDouble()").that(getActual().getDouble());
    }
    
    public FloatSubject asFloat() {
        
        return check("getFloat()").that(getActual().getFloat());
    }
    
}
