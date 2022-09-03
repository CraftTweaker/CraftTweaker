package com.blamejared.crafttweaker.api.data;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.visitor.DataVisitor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.LongTag;
import org.jetbrains.annotations.NotNull;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

/**
 * @docParam this 800000000L as IData
 */
@ZenCodeType.Name("crafttweaker.api.data.LongData")
@ZenRegister
@Document("vanilla/api/data/LongData")
public class LongData implements IData {
    
    private final LongTag internal;
    
    public LongData(LongTag internal) {
        
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public LongData(long internal) {
        
        this.internal = LongTag.valueOf(internal);
    }
    
    @Override
    public IData add(IData other) {
        
        return of(asLong() + other.asLong());
    }
    
    @Override
    public IData sub(IData other) {
        
        return of(asLong() - other.asLong());
    }
    
    @Override
    public IData mul(IData other) {
        
        return of(asLong() * other.asLong());
    }
    
    @Override
    public IData div(IData other) {
        
        return of(asLong() / other.asLong());
    }
    
    @Override
    public IData mod(IData other) {
        
        return of(asLong() % other.asLong());
    }
    
    @Override
    public IData or(IData other) {
        
        return of(asLong() | other.asLong());
    }
    
    @Override
    public IData and(IData other) {
        
        return of(asLong() & other.asLong());
    }
    
    @Override
    public IData xor(IData other) {
        
        return of(asLong() ^ other.asLong());
    }
    
    @Override
    public IData neg() {
        
        return of(-asLong());
    }
    
//    @Override
//    public IData operatorInvert() {
//
//        return of(~asLong());
//    }
    
    @Override
    public boolean contains(IData other) {
        
        return asLong() == other.asLong();
    }
    
    @Override
    public int compareTo(@NotNull IData other) {
        
        return Long.compare(asLong(), other.asLong());
    }
    
    @Override
    public boolean equalTo(IData other) {
        
        return asLong() == other.asLong();
    }
    
    @Override
    public IData shl(IData other) {
        
        return of(asLong() << other.asLong());
    }
    
    @Override
    public IData shr(IData other) {
        
        return of(asLong() >> other.asLong());
    }
    
    @Override
    public boolean asBool() {
        
        return asLong() == 1;
    }
    
    @Override
    public byte asByte() {
        
        return (byte) asLong();
    }
    
    @Override
    public short asShort() {
        
        return (short) asLong();
    }
    
    @Override
    public int asInt() {
        
        return (int) asLong();
    }
    
    @Override
    public long asLong() {
        
        return getInternal().getAsLong();
    }
    
    @Override
    public float asFloat() {
        
        return (float) asLong();
    }
    
    @Override
    public double asDouble() {
        
        return (double) asLong();
    }
    
    @Override
    public LongTag getInternal() {
        
        return internal;
    }
    
    @Override
    public IData copy() {
        
        return new LongData(getInternal().copy());
    }
    
    @Override
    public IData copyInternal() {
        
        return copy();
    }
    
    @Override
    public <T> T accept(DataVisitor<T> visitor) {
        
        return visitor.visitLong(this);
    }
    
    @Override
    public Type getType() {
        
        return Type.LONG;
    }
    
    private LongData of(long value) {
        
        return new LongData(LongTag.valueOf(value));
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        LongData iData = (LongData) o;
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
