package com.blamejared.crafttweaker.gametest.truth.subject.base.data;

import com.blamejared.crafttweaker.api.data.base.IData;
import com.google.common.truth.BooleanSubject;
import com.google.common.truth.ComparableSubject;
import com.google.common.truth.FailureMetadata;
import com.google.common.truth.IterableSubject;
import com.google.common.truth.MapSubject;
import com.google.common.truth.SimpleSubjectBuilder;
import com.google.common.truth.StringSubject;
import com.google.common.truth.Subject;
import javax.annotation.Nullable;

public abstract class IDataSubjectBase<T extends IData, U extends IDataSubjectBase<T, U>> extends Subject {
    
    protected IDataSubjectBase(FailureMetadata metadata, @Nullable T actual) {
        
        super(metadata, actual);
    }
    
    public abstract T getActual();
    
    public abstract SimpleSubjectBuilder<U, T> make();
    
    public ComparableSubject<IData.Type> type() {
        
        return check("type()").that(getActual().getType());
    }
    
    public BooleanSubject asBoolean() {
        
        return check("asBoolean()").that(getActual().asBoolean());
    }
    
    public StringSubject asString() {
        
        return check("asString()").that(getActual().asString());
    }
    
    public IterableSubject asList() {
        
        return check("asList()").that(getActual().asList());
    }
    
    public MapSubject asMap() {
        
        return check("asMap()").that(getActual().asMap());
    }
    
    public BooleanSubject contains(IData key) {
        
        return check("contains(IData key)").that(getActual().contains(key));
    }
    
    public IDataSubjectBase<T, U> copy() {
        
        return make().that((T) getActual().copy());
    }
    
    public IDataSubjectBase<T, U> copyInternal() {
        
        return make().that((T) getActual().copyInternal());
    }
    
    public ComparableSubject<Byte> getId() {
        
        return check("getId()").that(getActual().getId());
    }
    
}
