package com.blamejared.crafttweaker.gametest.truth.subject.type.data;

import com.blamejared.crafttweaker.api.data.LongArrayData;
import com.blamejared.crafttweaker.gametest.truth.subject.base.data.ICollectionDataSubjectBase;
import com.google.common.truth.FailureMetadata;
import com.google.common.truth.SimpleSubjectBuilder;
import javax.annotation.Nullable;

public class LongArrayDataSubject extends ICollectionDataSubjectBase<LongArrayData, LongArrayDataSubject> {
    
    private static final Factory<LongArrayDataSubject, LongArrayData> FACTORY = LongArrayDataSubject::new;
    
    private final LongArrayData actual;
    
    protected LongArrayDataSubject(FailureMetadata metadata, @Nullable LongArrayData actual) {
        
        super(metadata, actual);
        this.actual = actual;
    }
    
    @Override
    public LongArrayData getActual() {
        
        return actual;
    }
    
    @Override
    public SimpleSubjectBuilder<LongArrayDataSubject, LongArrayData> make() {
        
        return check("make()").about(FACTORY);
    }
    
    public static Factory<LongArrayDataSubject, LongArrayData> factory() {
        
        return FACTORY;
    }
    
}
