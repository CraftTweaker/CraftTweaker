package com.blamejared.crafttweaker.gametest.truth.subject.type.data;

import com.blamejared.crafttweaker.api.data.FloatData;
import com.blamejared.crafttweaker.gametest.truth.subject.base.data.IDataSubjectBase;
import com.google.common.truth.FailureMetadata;
import com.google.common.truth.SimpleSubjectBuilder;
import javax.annotation.Nullable;

public class FloatDataSubject extends IDataSubjectBase<FloatData, FloatDataSubject> {
    
    private static final Factory<FloatDataSubject, FloatData> FACTORY = FloatDataSubject::new;
    
    private final FloatData actual;
    
    protected FloatDataSubject(FailureMetadata metadata, @Nullable FloatData actual) {
        
        super(metadata, actual);
        this.actual = actual;
    }
    
    @Override
    public FloatData getActual() {
        
        return actual;
    }
    
    @Override
    public SimpleSubjectBuilder<FloatDataSubject, FloatData> make() {
        
        return check("make()").about(FACTORY);
    }
    
    public static Factory<FloatDataSubject, FloatData> factory() {
        
        return FACTORY;
    }
    
}
