package com.blamejared.crafttweaker.api.data;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.base.IData;
import com.blamejared.crafttweaker.api.data.base.INumberData;
import com.blamejared.crafttweaker.api.data.base.visitor.DataVisitor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.Tag;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Careful with BoolData: While it works for specifying boolean attributes in JSON syntax,
 * using it in Tags will instead use a {@link ByteData} object. Reason for this is that
 * Minecraft does not have Boolean NBT values.
 *
 * @docParam this true
 */
@ZenCodeType.Name("crafttweaker.api.data.BoolData")
@ZenRegister
@Document("vanilla/api/data/BoolData")
public class BoolData implements IData {
    
    private final boolean internal;
    
    @ZenCodeType.Constructor
    public BoolData(boolean internal) {
        
        this.internal = internal;
    }
    
    /**
     * Converts this BoolData to a {@link ByteData} object.
     * This will be used when this Data is converted to NBT
     */
    @ZenCodeType.Caster
    @ZenCodeType.Method
    public ByteData getByteData() {
        
        return new ByteData(ByteTag.valueOf(getInternalValue()));
    }
    
    @Override
    public BoolData copy() {
        
        return new BoolData(getInternalValue());
    }
    
    @Override
    public BoolData copyInternal() {
        
        return new BoolData(getInternalValue());
    }
    
    @Override
    public Tag getInternal() {
        
        return getByteData().getInternal();
    }
    
    // There is no such thing as a BooleanNBT, it uses the byte nbt, so override this if you need to I guess
    public boolean getInternalValue() {
        
        return internal;
    }
    
    @Override
    public INumberData asNumber() {
        
        return new IntData(getInternalValue() ? 1 : 0);
    }
    
    @Override
    public Type getType() {
        
        return Type.BOOL;
    }
    
    @Override
    public <T> T accept(DataVisitor<T> visitor) {
        
        return visitor.visitBool(this);
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        
        BoolData boolData = (BoolData) o;
        
        return internal == boolData.internal;
    }
    
    @Override
    public int hashCode() {
        
        return (internal ? 1 : 0);
    }
    
}
