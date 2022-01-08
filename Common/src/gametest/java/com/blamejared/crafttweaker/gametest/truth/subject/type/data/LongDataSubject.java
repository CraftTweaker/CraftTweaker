package com.blamejared.crafttweaker.gametest.truth.subject.type.data;

import com.blamejared.crafttweaker.api.data.LongData;
import com.blamejared.crafttweaker.gametest.truth.subject.base.data.IDataSubjectBase;
import com.google.common.truth.FailureMetadata;
import com.google.common.truth.SimpleSubjectBuilder;
import javax.annotation.Nullable;

public class LongDataSubject extends IDataSubjectBase<LongData, LongDataSubject> {
    
    private static final Factory<LongDataSubject, LongData> FACTORY = LongDataSubject::new;
    
    private final LongData actual;
    
    protected LongDataSubject(FailureMetadata metadata, @Nullable LongData actual) {
        
        super(metadata, actual);
        this.actual = actual;
    }
    
    @Override
    public LongData getActual() {
        
        return actual;
    }
    
    @Override
    public SimpleSubjectBuilder<LongDataSubject, LongData> make() {
        
        return check("make()").about(FACTORY);
    }
    
    public static Factory<LongDataSubject, LongData> factory() {
        
        return FACTORY;
    }
    
}
