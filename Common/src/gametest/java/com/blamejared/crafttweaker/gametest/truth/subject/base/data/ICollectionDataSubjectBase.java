package com.blamejared.crafttweaker.gametest.truth.subject.base.data;

import com.blamejared.crafttweaker.api.data.base.ICollectionData;
import com.google.common.truth.FailureMetadata;
import com.google.common.truth.IntegerSubject;
import javax.annotation.Nullable;

public abstract class ICollectionDataSubjectBase<T extends ICollectionData, U extends ICollectionDataSubjectBase<T, U>> extends IDataSubjectBase<T, U> {
    
    protected ICollectionDataSubjectBase(FailureMetadata metadata, @Nullable T actual) {
        
        super(metadata, actual);
    }
    
    public IntegerSubject size() {
        
        return check("size()").that(getActual().size());
    }
    
}
