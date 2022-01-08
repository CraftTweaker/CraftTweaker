package com.blamejared.crafttweaker.gametest.truth;

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
import com.blamejared.crafttweaker.gametest.truth.subject.type.data.BoolDataSubject;
import com.blamejared.crafttweaker.gametest.truth.subject.type.data.ByteArrayDataSubject;
import com.blamejared.crafttweaker.gametest.truth.subject.type.data.ByteDataSubject;
import com.blamejared.crafttweaker.gametest.truth.subject.type.data.DoubleDataSubject;
import com.blamejared.crafttweaker.gametest.truth.subject.type.data.FloatDataSubject;
import com.blamejared.crafttweaker.gametest.truth.subject.type.data.IDataSubject;
import com.blamejared.crafttweaker.gametest.truth.subject.type.data.IntArrayDataSubject;
import com.blamejared.crafttweaker.gametest.truth.subject.type.data.IntDataSubject;
import com.blamejared.crafttweaker.gametest.truth.subject.type.data.ListDataSubject;
import com.blamejared.crafttweaker.gametest.truth.subject.type.data.LongArrayDataSubject;
import com.blamejared.crafttweaker.gametest.truth.subject.type.data.LongDataSubject;
import com.blamejared.crafttweaker.gametest.truth.subject.type.data.MapDataSubject;
import com.blamejared.crafttweaker.gametest.truth.subject.type.data.ShortDataSubject;
import com.blamejared.crafttweaker.gametest.truth.subject.type.data.StringDataSubject;
import com.google.common.truth.Subject;
import net.minecraft.Util;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;
import java.util.Map;

public interface IExpectIData extends IExpect {
    
    Map<IData.Type, Subject.Factory<? extends IDataSubjectBase<?, ?>, ? extends IData>> DATA_TO_FACTORY = Util.make(new HashMap<>(), map -> {
        map.put(IData.Type.BOOL, BoolDataSubject.factory());
        map.put(IData.Type.BYTE_ARRAY, ByteArrayDataSubject.factory());
        map.put(IData.Type.BYTE, ByteDataSubject.factory());
        map.put(IData.Type.DOUBLE, DoubleDataSubject.factory());
        map.put(IData.Type.FLOAT, FloatDataSubject.factory());
        map.put(IData.Type.INT_ARRAY, IntArrayDataSubject.factory());
        map.put(IData.Type.INT, IntDataSubject.factory());
        map.put(IData.Type.MAP, MapDataSubject.factory());
        map.put(IData.Type.LIST, ListDataSubject.factory());
        map.put(IData.Type.LONG_ARRAY, LongArrayDataSubject.factory());
        map.put(IData.Type.LONG, LongDataSubject.factory());
        map.put(IData.Type.SHORT, ShortDataSubject.factory());
        map.put(IData.Type.STRING, StringDataSubject.factory());
    });
    
    static <T extends IData, U extends IDataSubjectBase<T, U>> Subject.Factory<U, T> get(@Nullable Object target) {
        
        return (Subject.Factory<U, T>) DATA_TO_FACTORY.get(target);
    }
    
    default <T extends IData, U extends IDataSubjectBase<T, U>> IDataSubjectBase<T, U> assertThat(@Nullable T target) {
        
        if(target instanceof BoolData data) {
            return (U) assertThat(data);
        } else if(target instanceof ByteArrayData data) {
            return (U) assertThat(data);
        } else if(target instanceof ByteData data) {
            return (U) assertThat(data);
        } else if(target instanceof DoubleData data) {
            return (U) assertThat(data);
        } else if(target instanceof FloatData data) {
            return (U) assertThat(data);
        } else if(target instanceof IntArrayData data) {
            return (U) assertThat(data);
        } else if(target instanceof IntData data) {
            return (U) assertThat(data);
        } else if(target instanceof ListData data) {
            return (U) assertThat(data);
        } else if(target instanceof LongArrayData data) {
            return (U) assertThat(data);
        } else if(target instanceof LongData data) {
            return (U) assertThat(data);
        } else if(target instanceof MapData data) {
            return (U) assertThat(data);
        } else if(target instanceof ShortData data) {
            return (U) assertThat(data);
        } else if(target instanceof StringData data) {
            return (U) assertThat(data);
        }
        
        return (U) expect().about(IDataSubject.factory()).that(target);
    }
    
    default BoolDataSubject assertThat(@Nullable BoolData target) {
        
        return expect().about(BoolDataSubject.factory()).that(target);
    }
    
    default ByteArrayDataSubject assertThat(@Nullable ByteArrayData target) {
        
        return expect().about(ByteArrayDataSubject.factory()).that(target);
    }
    
    default ByteDataSubject assertThat(@Nullable ByteData target) {
        
        return expect().about(ByteDataSubject.factory()).that(target);
    }
    
    default DoubleDataSubject assertThat(@Nullable DoubleData target) {
        
        return expect().about(DoubleDataSubject.factory()).that(target);
    }
    
    default FloatDataSubject assertThat(@Nullable FloatData target) {
        
        return expect().about(FloatDataSubject.factory()).that(target);
    }
    
    default IntArrayDataSubject assertThat(@Nullable IntArrayData target) {
        
        return expect().about(IntArrayDataSubject.factory()).that(target);
    }
    
    default IntDataSubject assertThat(@Nullable IntData target) {
        
        return expect().about(IntDataSubject.factory()).that(target);
    }
    
    default ListDataSubject assertThat(@Nullable ListData target) {
        
        return expect().about(ListDataSubject.factory()).that(target);
    }
    
    default LongArrayDataSubject assertThat(@Nullable LongArrayData target) {
        
        return expect().about(LongArrayDataSubject.factory()).that(target);
    }
    
    default LongDataSubject assertThat(@Nullable LongData target) {
        
        return expect().about(LongDataSubject.factory()).that(target);
    }
    
    default MapDataSubject assertThat(@Nullable MapData target) {
        
        return expect().about(MapDataSubject.factory()).that(target);
    }
    
    default ShortDataSubject assertThat(@Nullable ShortData target) {
        
        return expect().about(ShortDataSubject.factory()).that(target);
    }
    
    default StringDataSubject assertThat(@Nullable StringData target) {
        
        return expect().about(StringDataSubject.factory()).that(target);
    }
    
    
}
