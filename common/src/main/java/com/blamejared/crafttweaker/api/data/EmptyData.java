package com.blamejared.crafttweaker.api.data;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.visitor.DataVisitor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.EndTag;
import net.minecraft.nbt.Tag;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Represents a marker object for data.
 *
 * <p>Note that this is not supposed to be used in scripts, and as such it cannot be obtained in any way.</p>
 */
@ZenCodeType.Name("crafttweaker.api.data.EmptyData")
@ZenRegister
@Document("vanilla/api/data/EmptyData")
public final class EmptyData implements IData {
    public static final EmptyData INSTANCE = new EmptyData();
    
    private EmptyData() {}
    
    @Override
    public Tag getInternal() {
        
        return EndTag.INSTANCE;
    }
    
    @Override
    public IData copy() {
        
        return this;
    }
    
    @Override
    public IData copyInternal() {
        
        return this;
    }
    
    @Override
    public <T> T accept(final DataVisitor<T> visitor) {
        
        return visitor.visitEmpty(this);
    }
    
    @Override
    public Type getType() {
        
        return Type.EMPTY;
    }
    
    @Override
    public String toString() {
        
        return "<EMPTY>";
    }
    
}
