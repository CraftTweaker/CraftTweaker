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
import com.blamejared.crafttweaker.api.data.base.IData;
import com.blamejared.crafttweaker.api.data.base.converter.tag.TagToDataConverter;
import com.blamejared.crafttweaker.api.util.StringUtils;
import com.blamejared.crafttweaker.api.zencode.impl.util.ZenKeywordUtil;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.Tag;

public enum DataToStringVisitor implements DataVisitor<String> {
    INSTANCE;
    
    public String visit(IData data) {
        
        return data.accept(this);
    }
    
    private String visitSimple(Object value, String type) {
        
        return value + " as " + type;
    }
    
    public String visitBool(BoolData data) {
        
        return visitSimple(data.getInternalValue(), "bool");
    }
    
    @Override
    public String visitByteArray(ByteArrayData data) {
        
        StringBuilder result = new StringBuilder();
        result.append("[");
        boolean first = true;
        for(ByteTag nbt : data.getInternal()) {
            if(first) {
                first = false;
            } else {
                result.append(", ");
            }
            result.append(nbt.getAsByte());
        }
        result.append("]");
        return result.toString();
    }
    
    @Override
    public String visitByte(ByteData data) {
        
        return visitSimple(data.getInternal().getAsByte(), "byte");
    }
    
    @Override
    public String visitDouble(DoubleData data) {
        
        return visitSimple(data.getInternal().getAsDouble(), "double");
    }
    
    @Override
    public String visitFloat(FloatData data) {
        
        return visitSimple(data.getInternal().getAsFloat(), "float");
    }
    
    @Override
    public String visitIntArray(IntArrayData data) {
        
        StringBuilder result = new StringBuilder();
        result.append('[');
        boolean first = true;
        for(IntTag nbt : data.getInternal()) {
            if(first) {
                first = false;
            } else {
                result.append(", ");
            }
            result.append(nbt.getAsInt());
        }
        result.append(']');
        return result.toString();
    }
    
    @Override
    public String visitInt(IntData data) {
        
        return visitSimple(data.getInternal().getAsInt(), "int");
    }
    
    @Override
    public String visitList(ListData data) {
        
        StringBuilder output = new StringBuilder();
        output.append('[');
        boolean first = true;
        for(Tag inbt : data.getInternal()) {
            if(first) {
                first = false;
            } else {
                output.append(", ");
            }
            output.append(TagToDataConverter.convert(inbt).asString());
        }
        output.append(']');
        return output.toString();
    }
    
    
    @Override
    public String visitLongArray(LongArrayData data) {
        
        StringBuilder result = new StringBuilder();
        result.append('[');
        boolean first = true;
        for(LongTag nbt : data.getInternal()) {
            if(first) {
                first = false;
            } else {
                result.append(", ");
            }
            result.append(nbt.getAsLong());
        }
        result.append(']');
        return result.toString();
    }
    
    @Override
    public String visitLong(LongData data) {
        
        return visitSimple(data.getInternal().getAsLong(), "long");
    }
    
    @Override
    public String visitMap(MapData data) {
        
        StringBuilder result = new StringBuilder();
        result.append('{');
        boolean first = true;
        for(String key : data.getInternal().getAllKeys()) {
            IData value = data.getAt(key);
            if(first) {
                first = false;
            } else {
                result.append(", ");
            }
            
            if(isValidIdentifier(key)) {
                result.append(ZenKeywordUtil.sanitize(key));
            } else {
                result.append("\"").append(ZenKeywordUtil.sanitize(key)).append("\"");
            }
            
            result.append(": ");
            result.append(value.asString());
        }
        result.append('}');
        return result.toString();
        
    }
    
    @Override
    public String visitShort(ShortData data) {
        
        return visitSimple(data.getInternal().getAsShort(), "short");
    }
    
    @Override
    public String visitString(StringData data) {
        
        return visitSimple(StringUtils.quoteAndEscape(data.getInternal().getAsString()), "string");
    }
    
    private boolean isValidIdentifier(String str) {
        
        if(!Character.isJavaIdentifierStart(str.charAt(0))) {
            return false;
        }
        
        for(int i = 1; i < str.length(); i++) {
            if(!Character.isJavaIdentifierPart(str.charAt(i))) {
                return false;
            }
        }
        
        return true;
    }
    
}
