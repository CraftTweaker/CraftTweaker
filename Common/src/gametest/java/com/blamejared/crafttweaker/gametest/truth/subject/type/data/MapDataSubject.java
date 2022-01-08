package com.blamejared.crafttweaker.gametest.truth.subject.type.data;

import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.gametest.truth.subject.base.data.IDataSubjectBase;
import com.google.common.truth.BooleanSubject;
import com.google.common.truth.FailureMetadata;
import com.google.common.truth.IntegerSubject;
import com.google.common.truth.IterableSubject;
import com.google.common.truth.SimpleSubjectBuilder;
import javax.annotation.Nullable;

public class MapDataSubject extends IDataSubjectBase<MapData, MapDataSubject> {
    
    private static final Factory<MapDataSubject, MapData> FACTORY = MapDataSubject::new;
    
    private final MapData actual;
    
    protected MapDataSubject(FailureMetadata metadata, @Nullable MapData actual) {
        
        super(metadata, actual);
        this.actual = actual;
    }
    
    @Override
    public MapData getActual() {
        
        return actual;
    }
    
    public IterableSubject keySet() {
        
        return check("getKeySet()").that(getActual().getKeySet());
    }
    
    public IntegerSubject size() {
        
        return check("getSize()").that(getActual().getSize());
    }
    
    public BooleanSubject contains(String key) {
        
        return check("contains(String key)").that(getActual().contains(key));
    }
    
    public IDataSubject at(String key) {
        
        return check("at(String key)").about(IDataSubject.factory()).that(getActual().getAt(key));
    }
    
    public BooleanSubject isEmpty() {
        
        return check("isEmpty()").that(getActual().isEmpty());
    }
    
    @Override
    public SimpleSubjectBuilder<MapDataSubject, MapData> make() {
        
        return check("make()").about(FACTORY);
    }
    
    public static Factory<MapDataSubject, MapData> factory() {
        
        return FACTORY;
    }
    
}
