package com.blamejared.crafttweaker.gametest.truth.subject.type.data;

import com.blamejared.crafttweaker.api.data.StringData;
import com.blamejared.crafttweaker.gametest.truth.subject.base.data.IDataSubjectBase;
import com.google.common.truth.FailureMetadata;
import com.google.common.truth.SimpleSubjectBuilder;

import javax.annotation.Nullable;

public class StringDataSubject extends IDataSubjectBase<StringData, StringDataSubject> {
    
    private static final Factory<StringDataSubject, StringData> FACTORY = StringDataSubject::new;
    
    private final StringData actual;
    
    protected StringDataSubject(FailureMetadata metadata, @Nullable StringData actual) {
        
        super(metadata, actual);
        this.actual = actual;
    }
    
    @Override
    public StringData getActual() {
        
        return actual;
    }
    
    @Override
    public SimpleSubjectBuilder<StringDataSubject, StringData> make() {
        
        return check("make()").about(FACTORY);
    }
    
    public static Factory<StringDataSubject, StringData> factory() {
        
        return FACTORY;
    }
    
}
