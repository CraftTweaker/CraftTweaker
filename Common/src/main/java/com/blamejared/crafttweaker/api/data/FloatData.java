package com.blamejared.crafttweaker.api.data;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.visitor.DataVisitor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.FloatTag;
import org.jetbrains.annotations.NotNull;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

/**
 * @docParam this (8.5 as IData)
 */
@ZenCodeType.Name("crafttweaker.api.data.FloatData")
@ZenRegister
@Document("vanilla/api/data/FloatData")
public class FloatData implements IData {
    
    private final FloatTag internal;
    
    public FloatData(FloatTag internal) {
        
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public FloatData(float internal) {
        
        this.internal = FloatTag.valueOf(internal);
    }
    
    @Override
    public FloatTag getInternal() {
        
        return internal;
    }
    
    @Override
    public IData copy() {
        
        return new FloatData(getInternal());
    }
    
    @Override
    public IData copyInternal() {
        
        return copy();
    }
    
    @Override
    public <T> T accept(DataVisitor<T> visitor) {
        
        return visitor.visitFloat(this);
    }
    
    @Override
    public Type getType() {
        
        return Type.FLOAT;
    }
    
    @Override
    public IData add(IData other) {
        
        return of(asFloat() + other.asFloat());
    }
    
    @Override
    public IData sub(IData other) {
        
        return of(asFloat() - other.asFloat());
    }
    
    @Override
    public IData mul(IData other) {
        
        return of(asFloat() * other.asFloat());
    }
    
    @Override
    public IData div(IData other) {
        
        return of(asFloat() / other.asFloat());
    }
    
    @Override
    public IData mod(IData other) {
        
        return of(asFloat() % other.asFloat());
    }
    
    @Override
    public IData neg() {
        
        return of(-asFloat());
    }
    
    @Override
    public boolean contains(IData other) {
        
        return asFloat() == other.asFloat();
    }
    
    @Override
    public int compareTo(@NotNull IData other) {
        
        return Float.compare(asFloat(), other.asFloat());
    }
    
    @Override
    public boolean equalTo(IData other) {
        
        return asFloat() == other.asFloat();
    }
    
    @Override
    public boolean asBool() {
        
        return asFloat() == 1.0f;
    }
    
    @Override
    public byte asByte() {
        
        return (byte) asFloat();
    }
    
    @Override
    public short asShort() {
        
        return (short) asFloat();
    }
    
    @Override
    public int asInt() {
        
        return (int) asFloat();
    }
    
    @Override
    public long asLong() {
        
        return (long) asFloat();
    }
    
    @Override
    public float asFloat() {
        
        return getInternal().getAsFloat();
    }
    
    @Override
    public double asDouble() {
        
        return asFloat();
    }
    
    private FloatData of(float value) {
        
        return new FloatData(FloatTag.valueOf(value));
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        FloatData iData = (FloatData) o;
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
