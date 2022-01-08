package com.blamejared.crafttweaker.gametest.truth.subject.type.data;

import com.blamejared.crafttweaker.api.data.ByteData;
import com.blamejared.crafttweaker.gametest.truth.subject.base.data.IDataSubjectBase;
import com.google.common.truth.FailureMetadata;
import com.google.common.truth.SimpleSubjectBuilder;
import javax.annotation.Nullable;

public class ByteDataSubject extends IDataSubjectBase<ByteData, ByteDataSubject> {
    
    private static final Factory<ByteDataSubject, ByteData> FACTORY = ByteDataSubject::new;
    
    private final ByteData actual;
    
    protected ByteDataSubject(FailureMetadata metadata, @Nullable ByteData actual) {
        
        super(metadata, actual);
        this.actual = actual;
    }
    
    @Override
    public ByteData getActual() {
        
        return actual;
    }
    
    @Override
    public SimpleSubjectBuilder<ByteDataSubject, ByteData> make() {
        
        return check("make()").about(FACTORY);
    }
    
    public static Factory<ByteDataSubject, ByteData> factory() {
        
        return FACTORY;
    }
    
}
