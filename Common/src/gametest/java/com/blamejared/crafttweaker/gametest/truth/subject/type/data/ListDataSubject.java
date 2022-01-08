package com.blamejared.crafttweaker.gametest.truth.subject.type.data;

import com.blamejared.crafttweaker.api.data.ListData;
import com.blamejared.crafttweaker.gametest.truth.subject.base.data.ICollectionDataSubjectBase;
import com.google.common.truth.FailureMetadata;
import com.google.common.truth.SimpleSubjectBuilder;
import javax.annotation.Nullable;

public class ListDataSubject extends ICollectionDataSubjectBase<ListData, ListDataSubject> {
    
    private static final Factory<ListDataSubject, ListData> FACTORY = ListDataSubject::new;
    
    private final ListData actual;
    
    protected ListDataSubject(FailureMetadata metadata, @Nullable ListData actual) {
        
        super(metadata, actual);
        this.actual = actual;
    }
    
    @Override
    public ListData getActual() {
        
        return actual;
    }
    
    public IDataSubject at(int index) {
        
        return check("at()").about(IDataSubject.factory()).that(getActual().getAt(index));
    }
    
    @Override
    public SimpleSubjectBuilder<ListDataSubject, ListData> make() {
        
        return check("make()").about(FACTORY);
    }
    
    public static Factory<ListDataSubject, ListData> factory() {
        
        return FACTORY;
    }
    
}
