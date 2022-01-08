package com.blamejared.crafttweaker.gametest.truth.subject.type.data;

import com.blamejared.crafttweaker.api.data.DoubleData;
import com.blamejared.crafttweaker.gametest.truth.subject.base.data.IDataSubjectBase;
import com.google.common.truth.FailureMetadata;
import com.google.common.truth.SimpleSubjectBuilder;
import javax.annotation.Nullable;

public class DoubleDataSubject extends IDataSubjectBase<DoubleData, DoubleDataSubject> {
    
    private static final Factory<DoubleDataSubject, DoubleData> FACTORY = DoubleDataSubject::new;
    
    private final DoubleData actual;
    
    protected DoubleDataSubject(FailureMetadata metadata, @Nullable DoubleData actual) {
        
        super(metadata, actual);
        this.actual = actual;
    }
    
    @Override
    public DoubleData getActual() {
        
        return actual;
    }
    
    @Override
    public SimpleSubjectBuilder<DoubleDataSubject, DoubleData> make() {
        
        return check("make()").about(FACTORY);
    }
    
    public static Factory<DoubleDataSubject, DoubleData> factory() {
        
        return FACTORY;
    }
    
}
