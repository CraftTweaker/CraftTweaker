package com.blamejared.crafttweaker.gametest.truth.subject.type.data;

import com.blamejared.crafttweaker.api.data.IntData;
import com.blamejared.crafttweaker.gametest.truth.subject.base.data.IDataSubjectBase;
import com.google.common.truth.FailureMetadata;
import com.google.common.truth.SimpleSubjectBuilder;
import javax.annotation.Nullable;

public class IntDataSubject extends IDataSubjectBase<IntData, IntDataSubject> {
    
    private static final Factory<IntDataSubject, IntData> FACTORY = IntDataSubject::new;
    
    private final IntData actual;
    
    protected IntDataSubject(FailureMetadata metadata, @Nullable IntData actual) {
        
        super(metadata, actual);
        this.actual = actual;
    }
    
    @Override
    public IntData getActual() {
        
        return actual;
    }
    
    @Override
    public SimpleSubjectBuilder<IntDataSubject, IntData> make() {
        
        return check("make()").about(FACTORY);
    }
    
    public static Factory<IntDataSubject, IntData> factory() {
        
        return FACTORY;
    }
    
}
