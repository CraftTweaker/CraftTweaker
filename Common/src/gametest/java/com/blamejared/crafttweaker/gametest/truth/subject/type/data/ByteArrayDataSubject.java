package com.blamejared.crafttweaker.gametest.truth.subject.type.data;

import com.blamejared.crafttweaker.api.data.ByteArrayData;
import com.blamejared.crafttweaker.gametest.truth.subject.base.data.ICollectionDataSubjectBase;
import com.google.common.truth.FailureMetadata;
import com.google.common.truth.SimpleSubjectBuilder;
import javax.annotation.Nullable;

public class ByteArrayDataSubject extends ICollectionDataSubjectBase<ByteArrayData, ByteArrayDataSubject> {
    
    private static final Factory<ByteArrayDataSubject, ByteArrayData> FACTORY = ByteArrayDataSubject::new;
    
    private final ByteArrayData actual;
    
    protected ByteArrayDataSubject(FailureMetadata metadata, @Nullable ByteArrayData actual) {
        
        super(metadata, actual);
        this.actual = actual;
    }
    
    @Override
    public ByteArrayData getActual() {
        
        return actual;
    }
    
    @Override
    public SimpleSubjectBuilder<ByteArrayDataSubject, ByteArrayData> make() {
        
        return check("make()").about(FACTORY);
    }
    
    public static Factory<ByteArrayDataSubject, ByteArrayData> factory() {
        
        return FACTORY;
    }
    
}
