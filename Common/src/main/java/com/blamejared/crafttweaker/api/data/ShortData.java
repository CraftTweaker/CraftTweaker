package com.blamejared.crafttweaker.api.data;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.visitor.DataVisitor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.ShortTag;
import org.jetbrains.annotations.NotNull;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

/**
 * @docParam this (1058 as IData)
 */
@ZenCodeType.Name("crafttweaker.api.data.ShortData")
@ZenRegister
@Document("vanilla/api/data/ShortData")
public class ShortData implements IData {
    
    private final ShortTag internal;
    
    public ShortData(ShortTag internal) {
        
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public ShortData(short internal) {
        
        this.internal = ShortTag.valueOf(internal);
    }
    
    @Override
    public IData add(IData other) {
        
        return of(asShort() + other.asShort());
    }
    
    @Override
    public IData sub(IData other) {
        
        return of(asShort() - other.asShort());
    }
    
    @Override
    public IData mul(IData other) {
        
        return of(asShort() * other.asShort());
    }
    
    @Override
    public IData div(IData other) {
        
        return of(asShort() / other.asShort());
    }
    
    @Override
    public IData mod(IData other) {
        
        return of(asShort() % other.asShort());
    }
    
    @Override
    public IData or(IData other) {
        
        return of(asShort() | other.asShort());
    }
    
    @Override
    public IData and(IData other) {
        
        return of(asShort() & other.asShort());
    }
    
    @Override
    public IData xor(IData other) {
        
        return of(asShort() ^ other.asShort());
    }
    
    @Override
    public IData neg() {
        
        return of(-asShort());
    }
    
//    @Override
//    public IData operatorInvert() {
//
//        return of(~asShort());
//    }
    
    @Override
    public boolean contains(IData other) {
        
        return asShort() == other.asShort();
    }
    
    @Override
    public int compareTo(@NotNull IData other) {
        
        return Short.compare(asShort(), other.asShort());
    }
    
    @Override
    public boolean equalTo(IData other) {
        
        return asShort() == other.asShort();
    }
    
    @Override
    public IData shl(IData other) {
        
        return of(asShort() << other.asShort());
    }
    
    @Override
    public IData shr(IData other) {
        
        return of(asShort() >> other.asShort());
    }
    
    @Override
    public boolean asBool() {
        
        return asShort() == 1;
    }
    
    @Override
    public byte asByte() {
        
        return (byte) asShort();
    }
    
    @Override
    public short asShort() {
        
        return getInternal().getAsShort();
    }
    
    @Override
    public int asInt() {
        
        return asShort();
    }
    
    @Override
    public long asLong() {
        
        return asShort();
    }
    
    @Override
    public float asFloat() {
        
        return asShort();
    }
    
    @Override
    public double asDouble() {
        
        return asShort();
    }
    
    @Override
    public ShortTag getInternal() {
        
        return internal;
    }
    
    @Override
    public IData copy() {
        
        return new ShortData(getInternal().copy());
    }
    
    @Override
    public IData copyInternal() {
        
        return copy();
    }
    
    @Override
    public <T> T accept(DataVisitor<T> visitor) {
        
        return visitor.visitShort(this);
    }
    
    @Override
    public Type getType() {
        
        return Type.SHORT;
    }
    
    private ShortData of(int value) {
        
        return new ShortData(ShortTag.valueOf((short) value));
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        ShortData iData = (ShortData) o;
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
