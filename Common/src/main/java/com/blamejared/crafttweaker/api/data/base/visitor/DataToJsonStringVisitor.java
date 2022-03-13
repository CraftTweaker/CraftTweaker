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
import com.blamejared.crafttweaker.api.data.base.ICollectionData;
import com.blamejared.crafttweaker.api.data.base.IData;
import com.blamejared.crafttweaker.api.data.base.INumberData;
import com.blamejared.crafttweaker.api.util.StringUtil;

import java.util.StringJoiner;
import java.util.stream.Collectors;

public enum DataToJsonStringVisitor implements DataVisitor<String> {
    INSTANCE;
    
    public String visit(IData data) {
        
        return data.accept(this);
    }
    
    private String visitNumber(INumberData data) {
        
        return String.valueOf(data.getInternal().getAsNumber());
    }
    
    private String visitCollection(ICollectionData data) {
        
        return data.asList().stream().map(iData -> iData.accept(INSTANCE)).collect(Collectors.joining(",", "[", "]"));
    }
    
    public String visitBool(BoolData data) {
        
        return Boolean.toString(data.getInternalValue());
    }
    
    @Override
    public String visitByteArray(ByteArrayData data) {
        
        return visitCollection(data);
    }
    
    @Override
    public String visitByte(ByteData data) {
        
        return visitNumber(data);
    }
    
    @Override
    public String visitDouble(DoubleData data) {
        
        return visitNumber(data);
    }
    
    @Override
    public String visitFloat(FloatData data) {
        
        return visitNumber(data);
    }
    
    @Override
    public String visitIntArray(IntArrayData data) {
        
        return visitCollection(data);
    }
    
    @Override
    public String visitInt(IntData data) {
        
        return visitNumber(data);
    }
    
    @Override
    public String visitList(ListData data) {
        
        return visitCollection(data);
    }
    
    
    @Override
    public String visitLongArray(LongArrayData data) {
        
        return visitCollection(data);
    }
    
    @Override
    public String visitLong(LongData data) {
        
        return visitNumber(data);
    }
    
    @Override
    public String visitMap(MapData data) {
        
        final StringJoiner joiner = new StringJoiner(",", "{", "}");
        data.asMap().forEach((key, value) -> joiner.add("\"%s\" : %s".formatted(key, value.accept(INSTANCE))));
        
        return joiner.toString();
    }
    
    @Override
    public String visitShort(ShortData data) {
        
        return visitNumber(data);
    }
    
    @Override
    public String visitString(StringData data) {
        
        return StringUtil.quoteAndEscape(data.getInternal().getAsString());
    }
    
}
