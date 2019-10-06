package com.blamejared.crafttweaker.impl.data;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.NBTConverter;
import net.minecraft.nbt.CompoundNBT;
import org.openzen.zencode.java.ZenCodeType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@ZenCodeType.Name("crafttweaker.api.data.MapData")
@ZenRegister
public class MapData implements IData {
    
    private CompoundNBT internal;
    
    public MapData(CompoundNBT internal) {
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public MapData() {
        this.internal = new CompoundNBT();
    }
    
    public MapData(Map<String, IData> map) {
        this.internal = new CompoundNBT();
        putAll(map);
    }
    
    @ZenCodeType.Method
    public void putAll(Map<String, IData> map) {
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
    public Map<String, IData> asMap() {
        Map<String, IData> newMap = new HashMap<>();
        internal.keySet().forEach(s -> newMap.put(s, get(s)));
        return newMap;
    }
    
    @Override
    public boolean contains(IData data) {
        if(data instanceof StringData) {
            return this.internal.contains(data.asString());
        }
        
        
        Map<String, IData> dataMap = data.asMap();
        if(dataMap == null)
            return false;
        
        for(Map.Entry<String, IData> dataEntry : dataMap.entrySet()) {
            if(!this.getInternal().contains(dataEntry.getKey())) {
                return false;
            } else if(!NBTConverter.convert(this.getInternal().get(dataEntry.getKey())).contains(dataEntry.getValue())) {
                return false;
            }
        }
        
        return true;
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
        result.append('}').append(" as IData");
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
    
    @ZenCodeType.Caster(implicit = true)
    public static Map<String, IData> castToMap(MapData data) {
        return data.asMap();
    }
}
