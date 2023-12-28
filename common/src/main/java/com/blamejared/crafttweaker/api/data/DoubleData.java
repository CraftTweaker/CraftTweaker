package com.blamejared.crafttweaker.api.data;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.visitor.DataVisitor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.DoubleTag;
import org.jetbrains.annotations.NotNull;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

/**
 * @docParam this (3.25 as IData)
 */
@ZenCodeType.Name("crafttweaker.api.data.DoubleData")
@ZenRegister
@Document("vanilla/api/data/DoubleData")
public class DoubleData implements IData {
    
    private final DoubleTag internal;
    
    public DoubleData(DoubleTag internal) {
        
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public DoubleData(double internal) {
        
        this.internal = DoubleTag.valueOf(internal);
    }
    
    @Override
    public IData add(IData other) {
        
        return of(asDouble() + other.asDouble());
    }
    
    @Override
    public IData sub(IData other) {
        
        return of(asDouble() - other.asDouble());
    }
    
    @Override
    public IData mul(IData other) {
        
        return of(asDouble() * other.asDouble());
    }
    
    @Override
    public IData div(IData other) {
        
        return of(asDouble() / other.asDouble());
    }
    
    @Override
    public IData mod(IData other) {
        
        return of(asDouble() % other.asDouble());
    }
    
    @Override
    public IData neg() {
        
        return of(-asDouble());
    }
    
    @Override
    public boolean contains(IData other) {
        
        return asDouble() == other.asDouble();
    }
    
    @Override
    public int compareTo(@NotNull IData other) {
        
        return Double.compare(asDouble(), other.asDouble());
    }
    
    @Override
    public boolean equalTo(IData other) {
        
        return asDouble() == other.asDouble();
    }
    
    @Override
    public boolean asBool() {
        
        return asDouble() == 1.0;
    }
    
    @Override
    public byte asByte() {
        
        return (byte) asDouble();
    }
    
    @Override
    public short asShort() {
        
        return (short) asDouble();
    }
    
    @Override
    public int asInt() {
        
        return (int) asDouble();
    }
    
    @Override
    public long asLong() {
        
        return (long) asDouble();
    }
    
    @Override
    public float asFloat() {
        
        return (float) asDouble();
    }
    
    @Override
    public double asDouble() {
        
        return getInternal().getAsDouble();
    }
    
    private DoubleData of(double value) {
        
        return new DoubleData(DoubleTag.valueOf(value));
    }
    
    @Override
    public DoubleTag getInternal() {
        
        return internal;
    }
    
    @Override
    public IData copy() {
        
        return new DoubleData(getInternal());
    }
    
    @Override
    public IData copyInternal() {
        
        return copy();
    }
    
    @Override
    public <T> T accept(DataVisitor<T> visitor) {
        
        return visitor.visitDouble(this);
    }
    
    @Override
    public Type getType() {
        
        return Type.DOUBLE;
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        DoubleData iData = (DoubleData) o;
        return Objects.equals(getInternal(), iData.getInternal());
    }
    
    @Override
    public int hashCode() {
        
        return Objects.hash(getInternal());
    }
    
    @Override
    public String toString() {
        
        return getAsString();
    }
    
}
