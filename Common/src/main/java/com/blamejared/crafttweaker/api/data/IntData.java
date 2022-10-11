package com.blamejared.crafttweaker.api.data;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.visitor.DataVisitor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.IntTag;
import org.jetbrains.annotations.NotNull;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

/**
 * @docParam this (8192 as IData)
 */
@ZenCodeType.Name("crafttweaker.api.data.IntData")
@ZenRegister
@Document("vanilla/api/data/IntData")
public class IntData implements IData {
    
    private final IntTag internal;
    
    public IntData(IntTag internal) {
        
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public IntData(int internal) {
        
        this.internal = IntTag.valueOf(internal);
    }
    
    @Override
    public IData add(IData other) {
        
        return of(asInt() + other.asInt());
    }
    
    @Override
    public IData sub(IData other) {
        
        return of(asInt() - other.asInt());
    }
    
    @Override
    public IData mul(IData other) {
        
        return of(asInt() * other.asInt());
    }
    
    @Override
    public IData div(IData other) {
        
        return of(asInt() / other.asInt());
    }
    
    @Override
    public IData mod(IData other) {
        
        return of(asInt() % other.asInt());
    }
    
    @Override
    public IData or(IData other) {
        
        return of(asInt() | other.asInt());
    }
    
    @Override
    public IData and(IData other) {
        
        return of(asInt() & other.asInt());
    }
    
    @Override
    public IData xor(IData other) {
        
        return of(asInt() ^ other.asInt());
    }
    
    @Override
    public IData neg() {
        
        return of(-asInt());
    }
    
//    @Override
//    public IData operatorInvert() {
//
//        return of(~asInt());
//    }
    
    @Override
    public boolean contains(IData other) {
        
        return asInt() == other.asInt();
    }
    
    @Override
    public int compareTo(@NotNull IData other) {
        
        return Integer.compare(asInt(), other.asInt());
    }
    
    @Override
    public boolean equalTo(IData other) {
        
        return asInt() == other.asInt();
    }
    
    @Override
    public IData shl(IData other) {
        
        return of(asInt() << other.asInt());
    }
    
    @Override
    public IData shr(IData other) {
        
        return of(asInt() >> other.asInt());
    }
    
    @Override
    public boolean asBool() {
        
        return asInt() == 1;
    }
    
    @Override
    public byte asByte() {
        
        return (byte) asInt();
    }
    
    @Override
    public short asShort() {
        
        return (short) asInt();
    }
    
    @Override
    public int asInt() {
        
        return getInternal().getAsInt();
    }
    
    @Override
    public long asLong() {
        
        return asInt();
    }
    
    @Override
    public float asFloat() {
        
        return asInt();
    }
    
    @Override
    public double asDouble() {
        
        return asInt();
    }
    
    @Override
    public IntTag getInternal() {
        
        return internal;
    }
    
    @Override
    public IData copy() {
        
        return new IntData(getInternal());
    }
    
    @Override
    public IData copyInternal() {
        
        return copy();
    }
    
    @Override
    public <T> T accept(DataVisitor<T> visitor) {
        
        return visitor.visitInt(this);
    }
    
    @Override
    public Type getType() {
        
        return Type.INT;
    }
    
    private IntData of(int value) {
        
        return new IntData(IntTag.valueOf(value));
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        IntData iData = (IntData) o;
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
