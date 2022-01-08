package com.blamejared.crafttweaker.gametest.truth.subject.type.data;

import com.blamejared.crafttweaker.api.data.BoolData;
import com.blamejared.crafttweaker.gametest.truth.subject.base.data.IDataSubjectBase;
import com.google.common.truth.FailureMetadata;
import com.google.common.truth.SimpleSubjectBuilder;
import com.google.common.truth.Subject;
import javax.annotation.Nullable;

public class BoolDataSubject extends IDataSubjectBase<BoolData, BoolDataSubject> {
    
    private static final Subject.Factory<BoolDataSubject, BoolData> FACTORY = BoolDataSubject::new;
    
    private final BoolData actual;
    
    protected BoolDataSubject(FailureMetadata metadata, @Nullable BoolData actual) {
        
        super(metadata, actual);
        this.actual = actual;
    }
    
    @Override
    public BoolData getActual() {
        
        return actual;
    }
    
    public void isTrue() {
        
        check("isTrue()").that(getActual().getInternalValue()).isTrue();
    }
    
    public void isFalse() {
        
        check("isFalse()").that(getActual().getInternalValue()).isFalse();
    }
    
    @Override
    public SimpleSubjectBuilder<BoolDataSubject, BoolData> make() {
        
        return check("make()").about(FACTORY);
    }
    
    public static Subject.Factory<BoolDataSubject, BoolData> factory() {
        
        return FACTORY;
    }
    
}
