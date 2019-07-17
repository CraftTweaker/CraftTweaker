package com.blamejared.crafttweaker.impl.nbt;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.nbt.IData;
import com.blamejared.crafttweaker.api.nbt.NBTConverter;
import net.minecraft.nbt.CompoundNBT;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Map;
import java.util.Set;

@ZenCodeType.Name("crafttweaker.api.tag.MapData")
@ZenRegister
public class MapData implements IData {
    
    private CompoundNBT internal;
    
    public MapData(CompoundNBT internal) {
        this.internal = internal;
    }
    
    public MapData(Map<String, IData> map) {
        this.internal = new CompoundNBT();
        map.forEach((s, iData) -> internal.put(s, iData.getInternal()));
    }
    
    @ZenCodeType.Getter("keySet")
    public Set<String> getKeySet() {
        return internal.keySet();
    }
    
    @ZenCodeType.Getter("size")
    public int getSize() {
        return internal.size();
    }
    
    @ZenCodeType.Method
    public IData put(String key, IData value) {
        return NBTConverter.convert(internal.put(key, value.getInternal()));
    }
    
    @ZenCodeType.Method
    public IData get(String key) {
        return NBTConverter.convert(internal.get(key));
    }
    
    @ZenCodeType.Method
    public boolean contains(String key) {
        return internal.contains(key);
    }
    
    @ZenCodeType.Method
    public void remove(String key) {
        internal.remove(key);
    }
    
    @ZenCodeType.Getter("isEmpty")
    public boolean isEmpty() {
        return internal.isEmpty();
    }
    
    @ZenCodeType.Method
    public MapData merge(MapData other) {
        return new MapData(internal.merge(other.getInternal()));
    }
    
    @Override
    public IData copy() {
        return new MapData(internal);
    }
    
    @Override
    public CompoundNBT getInternal() {
        return internal;
    }
    
    @Override
    public String asString() {
        StringBuilder result = new StringBuilder();
        result.append('{');
        boolean first = true;
        for(String key : internal.keySet()) {
            IData value = this.get(key);
            if(first) {
                first = false;
            } else {
                result.append(", ");
            }
            
            if(isValidIdentifier(key)) {
                result.append(key);
            } else {
                result.append("\"").append(key).append("\"");
            }
            
            result.append(": ");
            result.append(value.asString());
        }
        result.append('}');
        return result.toString();
    }
    
    private boolean isValidIdentifier(String str) {
        if(!Character.isJavaIdentifierStart(str.charAt(0)))
            return false;
        
        for(int i = 1; i < str.length(); i++) {
            if(!Character.isJavaIdentifierPart(str.charAt(i)))
                return false;
        }
        
        return true;
    }
}
