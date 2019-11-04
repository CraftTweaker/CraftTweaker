package com.blamejared.crafttweaker.impl.data;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.NBTConverter;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NumberNBT;
import org.openzen.zencode.java.StorageTagType;
import org.openzen.zencode.java.ZenCodeStorageTag;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * @docParam this {Hello : "World", Somewhere: "Over the rainbow"}
 */
@ZenCodeType.Name("crafttweaker.api.data.MapData")
@ZenRegister
@Document("vanilla/data/MapData")
public class MapData implements IData {
    
    private CompoundNBT internal;
    
    public MapData(CompoundNBT internal) {
        this.internal = internal;
    }
    
    @ZenCodeType.Constructor
    public MapData() {
        this.internal = new CompoundNBT();
    }
    
    @ZenCodeType.Constructor
    public MapData(Map<@ZenCodeStorageTag(StorageTagType.STATIC) String, IData> map) {
        this.internal = new CompoundNBT();
        putAll(map);
    }
    
    /**
     * Adds all entries from the given map into this one.
     * Can override existing keys.
     *
     * @param map The other entries to be added to this map
     *
     * @docParam map {Hello: "Goodbye", Item: "Bedrock"}
     */
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
    
    /**
     * Adds sets the value for the given key or creates a new entry if it did not exist before.
     *
     * @param key   The key to set the value for.
     * @param value The value to set.
     *
     * @return The previous value if present, null otherwise
     *
     * @docParam key "Hello"
     * @docParam value "Goodbye"
     */
    @ZenCodeType.Method
    public IData put(String key, IData value) {
        return NBTConverter.convert(internal.put(key, value.getInternal()));
    }
    
    /**
     * Retrieves the value associated with the key
     *
     * @param key The key to search for
     *
     * @return The value if present, null otherwise
     *
     * @docParam key "Hello"
     */
    @ZenCodeType.Method
    public IData get(String key) {
        return NBTConverter.convert(internal.get(key));
    }
    
    /**
     * Checks if the Map contains the given key.
     *
     * @param key The key to search for
     *
     * @return True if the Map contains the key
     *
     * @docParam key "Hello"
     */
    @ZenCodeType.Method
    public boolean contains(String key) {
        return internal.contains(key);
    }
    
    /**
     * Removes the entry with the given key from the Map
     *
     * @param key The key of the entry to remove
     *
     * @docParam key "Somewhere"
     */
    @ZenCodeType.Method
    public void remove(String key) {
        internal.remove(key);
    }
    
    @ZenCodeType.Getter("isEmpty")
    public boolean isEmpty() {
        return internal.isEmpty();
    }
    
    /**
     * Merges this map and the other map.
     * If entries from this map and the other map share the values are tried to be merged.
     * If that does not work, then the value from the other map is used.
     *
     * @param other The other map.
     *
     * @return This map, after the merge
     *
     * @docParam other {Doodle: "Do}
     */
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
    
    public String toJsonString() {
        
        StringBuilder stringbuilder = new StringBuilder("{");
        Collection<String> collection = getInternal().keySet();
        
        for(String s : collection) {
            if(stringbuilder.length() != 1) {
                stringbuilder.append(',');
            }
            
            INBT obj = getInternal().get(s);
            String value = obj.toString();
            // some numbers such as float are represented as "0.35f" which isn't exactly valid json
            if(obj instanceof NumberNBT) {
                value = ((NumberNBT) obj).getAsNumber().toString();
            }
            stringbuilder.append("\"").append(CompoundNBT.handleEscape(s)).append("\"").append(':').append(value);
        }
        
        return stringbuilder.append('}').toString();
    }
}
