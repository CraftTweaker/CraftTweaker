package com.blamejared.crafttweaker.api.data.base.visitor;

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

public interface DataVisitor<T> {
    
    T visitBool(BoolData data);
    
    T visitByteArray(ByteArrayData data);
    
    T visitByte(ByteData data);
    
    T visitDouble(DoubleData data);
    
    T visitFloat(FloatData data);
    
    T visitIntArray(IntArrayData data);
    
    T visitInt(IntData data);
    
    T visitList(ListData data);
    
    T visitLongArray(LongArrayData data);
    
    T visitLong(LongData data);
    
    T visitMap(MapData data);
    
    T visitShort(ShortData data);
    
    T visitString(StringData data);
    
}
