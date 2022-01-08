package com.blamejared.crafttweaker.gametest.truth.subject.type.data;

import com.blamejared.crafttweaker.api.data.ShortData;
import com.blamejared.crafttweaker.gametest.truth.subject.base.data.IDataSubjectBase;
import com.google.common.truth.FailureMetadata;
import com.google.common.truth.SimpleSubjectBuilder;

import javax.annotation.Nullable;

public class ShortDataSubject extends IDataSubjectBase<ShortData, ShortDataSubject> {
    
    private static final Factory<ShortDataSubject, ShortData> FACTORY = ShortDataSubject::new;
    
    private final ShortData actual;
    
    protected ShortDataSubject(FailureMetadata metadata, @Nullable ShortData actual) {
        
        super(metadata, actual);
        this.actual = actual;
    }
    
    @Override
    public ShortData getActual() {
        
        return actual;
    }
    
    @Override
    public SimpleSubjectBuilder<ShortDataSubject, ShortData> make() {
        
        return check("make()").about(FACTORY);
    }
    
    public static Factory<ShortDataSubject, ShortData> factory() {
        
        return FACTORY;
    }
    
}
