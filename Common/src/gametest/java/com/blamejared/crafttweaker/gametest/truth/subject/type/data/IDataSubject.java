package com.blamejared.crafttweaker.gametest.truth.subject.type.data;

import com.blamejared.crafttweaker.api.data.BoolData;
import com.blamejared.crafttweaker.api.data.ByteArrayData;
import com.blamejared.crafttweaker.api.data.ByteData;
import com.blamejared.crafttweaker.api.data.DoubleData;
import com.blamejared.crafttweaker.api.data.FloatData;
import com.blamejared.crafttweaker.api.data.IntArrayData;
import com.blamejared.crafttweaker.api.data.IntData;
import com.blamejared.crafttweaker.api.data.ListData;
import com.blamejared.crafttweaker.api.data.LongArrayData;
import com.blamejared.crafttweaker.api.data.LongData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.ShortData;
import com.blamejared.crafttweaker.api.data.StringData;
import com.blamejared.crafttweaker.api.data.base.IData;
import com.blamejared.crafttweaker.gametest.truth.subject.base.data.IDataSubjectBase;
import com.google.common.truth.FailureMetadata;
import com.google.common.truth.SimpleSubjectBuilder;
import com.google.common.truth.Subject;
import javax.annotation.Nullable;

public class IDataSubject extends IDataSubjectBase<IData, IDataSubject> {
    
    private static final Subject.Factory<IDataSubject, IData> FACTORY = IDataSubject::new;
    
    private final IData actual;
    
    protected IDataSubject(FailureMetadata metadata, @Nullable IData actual) {
        
        super(metadata, actual);
        this.actual = actual;
    }
    
    @Override
    public IData getActual() {
        
        return actual;
    }
    
    public BoolDataSubject asBoolData() {
        
        return check("asBoolData()").about(BoolDataSubject.factory()).that((BoolData) getActual());
    }
    
    public ByteArrayDataSubject asByteArrayData() {
        
        return check("asByteArrayData()").about(ByteArrayDataSubject.factory()).that((ByteArrayData) getActual());
    }
    
    public ByteDataSubject asByteData() {
        
        return check("asByteData()").about(ByteDataSubject.factory()).that((ByteData) getActual());
    }
    
    public DoubleDataSubject asDoubleData() {
        
        return check("asDoubleData()").about(DoubleDataSubject.factory()).that((DoubleData) getActual());
    }
    
    public FloatDataSubject asFloatData() {
        
        return check("asFloatData()").about(FloatDataSubject.factory()).that((FloatData) getActual());
    }
    
    public IntArrayDataSubject asIntArrayData() {
        
        return check("asIntArrayData()").about(IntArrayDataSubject.factory()).that((IntArrayData) getActual());
    }
    
    public IntDataSubject asIntData() {
        
        return check("asIntData()").about(IntDataSubject.factory()).that((IntData) getActual());
    }
    
    public ListDataSubject asListData() {
        
        return check("asListData()").about(ListDataSubject.factory()).that((ListData) getActual());
    }
    
    public LongArrayDataSubject asLongArrayData() {
        
        return check("asLongArrayData()").about(LongArrayDataSubject.factory()).that((LongArrayData) getActual());
    }
    
    public LongDataSubject asLongData() {
        
        return check("asLongData()").about(LongDataSubject.factory()).that((LongData) getActual());
    }
    
    public MapDataSubject asMapData() {
        
        return check("asMapData()").about(MapDataSubject.factory()).that((MapData) getActual());
    }
    
    public ShortDataSubject asShortData() {
        
        return check("asShortData()").about(ShortDataSubject.factory()).that((ShortData) getActual());
    }
    
    public StringDataSubject asStringData() {
        
        return check("asStringData()").about(StringDataSubject.factory()).that((StringData) getActual());
    }
    
    @Override
    public SimpleSubjectBuilder<IDataSubject, IData> make() {
        
        return check("make()").about(FACTORY);
    }
    
    public static Subject.Factory<IDataSubject, IData> factory() {
        
        return FACTORY;
    }
    
}
