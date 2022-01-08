package com.blamejared.crafttweaker.gametest.truth.subject.type.data;

import com.blamejared.crafttweaker.api.data.IntArrayData;
import com.blamejared.crafttweaker.gametest.truth.subject.base.data.ICollectionDataSubjectBase;
import com.google.common.truth.FailureMetadata;
import com.google.common.truth.SimpleSubjectBuilder;
import javax.annotation.Nullable;

public class IntArrayDataSubject extends ICollectionDataSubjectBase<IntArrayData, IntArrayDataSubject> {
    
    private static final Factory<IntArrayDataSubject, IntArrayData> FACTORY = IntArrayDataSubject::new;
    
    private final IntArrayData actual;
    
    protected IntArrayDataSubject(FailureMetadata metadata, @Nullable IntArrayData actual) {
        
        super(metadata, actual);
        this.actual = actual;
    }
    
    @Override
    public IntArrayData getActual() {
        
        return actual;
    }
    
    @Override
    public SimpleSubjectBuilder<IntArrayDataSubject, IntArrayData> make() {
        
        return check("make()").about(FACTORY);
    }
    
    public static Factory<IntArrayDataSubject, IntArrayData> factory() {
        
        return FACTORY;
    }
    
}
