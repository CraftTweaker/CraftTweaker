package com.blamejared.crafttweaker.impl.data;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.StringNBT;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this new StringData("Hello")
 */
@ZenCodeType.Name("crafttweaker.api.data.StringData")
@ZenRegister
@Document("vanilla/api/data/StringData")
public class StringData implements IData {
    
    private final StringNBT internal;
    
    public StringData(StringNBT internal) {
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public StringData(String internal) {
        this.internal = StringNBT.valueOf(internal);
    }
    
    @Override
    public IData copy() {
        return new StringData(internal);
    }
    
    @Override
    public IData copyInternal() {
        return new StringData(getInternal().copy());
    }
    
    @Override
    public StringNBT getInternal() {
        return internal;
    }
    
    
    @Override
    public boolean contains(IData data) {
        return asString().equals(data.asString());
    }
    
    /**
     * Concatenates the two string Datas and returns the result.
     *
     * @param data The other data to append
     *
     * @return A new StringData with the value concatenated.
     * @docParam data new StringData("World")
     */
    @ZenCodeType.Operator(ZenCodeType.OperatorType.ADD)
    public StringData addTogether(StringData data) {
        return new StringData(internal.getString() + data.internal.getString());
    }
    
    @Override
    public String asString() {
        return quoteAndEscape(internal.getString()) + " as string";
    }
    
    @Override
    public String toJsonString() {
        return quoteAndEscape(internal.getString());
    }
    
    private String quoteAndEscape(String p_193588_0_) {
        StringBuilder stringbuilder = new StringBuilder("\"");
        
        for(int i = 0; i < p_193588_0_.length(); ++i) {
            char c0 = p_193588_0_.charAt(i);
            
            if(c0 == '\\' || c0 == '"') {
                stringbuilder.append('\\');
            }
            
            stringbuilder.append(c0);
        }
        
        return stringbuilder.append('"').toString();
    }
}
