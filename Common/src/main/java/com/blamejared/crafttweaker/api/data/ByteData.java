package com.blamejared.crafttweaker.api.data;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.visitor.DataVisitor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.ByteTag;
import org.jetbrains.annotations.NotNull;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

/**
 * @docParam this (4 as IData)
 */
@ZenCodeType.Name("crafttweaker.api.data.ByteData")
@ZenRegister
@Document("vanilla/api/data/ByteData")
public class ByteData implements IData {
    
    private final ByteTag internal;
    
    public ByteData(ByteTag internal) {
        
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public ByteData(byte internal) {
        
        this.internal = ByteTag.valueOf(internal);
    }
    
    @Override
    public IData add(IData other) {
        
        return of(asByte() + other.asByte());
    }
    
    @Override
    public IData sub(IData other) {
        
        return of(asByte() - other.asByte());
    }
    
    @Override
    public IData mul(IData other) {
        
        return of(asByte() * other.asByte());
    }
    
    @Override
    public IData div(IData other) {
        
        return of(asByte() / other.asByte());
    }
    
    @Override
    public IData mod(IData other) {
        
        return of(asByte() % other.asByte());
    }
    
    @Override
    public IData or(IData other) {
        
        return of(asByte() | other.asByte());
    }
    
    @Override
    public IData and(IData other) {
        
        return of(asByte() & other.asByte());
    }
    
    @Override
    public IData xor(IData other) {
        
        return of(asByte() ^ other.asByte());
    }
    
    @Override
    public IData neg() {
        
        return of(-asByte());
    }
    
    //    @Override
    //    public IData operatorInvert() {
    //
    //        return of(~asByte());
    //    }
    
    @Override
    public boolean contains(IData other) {
        
        return asByte() == other.asByte();
    }
    
    @Override
    public int compareTo(@NotNull IData other) {
        
        return Byte.compare(asByte(), other.asByte());
    }
    
    @Override
    public boolean equalTo(IData other) {
        
        return asByte() == other.asByte();
    }
    
    @Override
    public boolean asBool() {
        
        return asByte() == 1;
    }
    
    @Override
    public byte asByte() {
        
        return getInternal().getAsByte();
    }
    
    @Override
    public short asShort() {
        
        return asByte();
    }
    
    @Override
    public int asInt() {
        
        return asByte();
    }
    
    @Override
    public long asLong() {
        
        return asByte();
    }
    
    @Override
    public float asFloat() {
        
        return asByte();
    }
    
    @Override
    public double asDouble() {
        
        return asByte();
    }
    
    @Override
    public ByteTag getInternal() {
        
        return internal;
    }
    
    @Override
    public IData copy() {
        
        return new ByteData(getInternal());
    }
    
    @Override
    public IData copyInternal() {
        
        return copy();
    }
    
    @Override
    public <T> T accept(DataVisitor<T> visitor) {
        
        return visitor.visitByte(this);
    }
    
    @Override
    public Type getType() {
        
        return Type.BYTE;
    }
    
    private ByteData of(int value) {
        
        return new ByteData(ByteTag.valueOf((byte) value));
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        ByteData iData = (ByteData) o;
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
