package com.blamejared.crafttweaker.api.data;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.base.IData;
import com.blamejared.crafttweaker.api.data.base.converter.tag.TagToDataConverter;
import com.blamejared.crafttweaker.api.data.base.visitor.DataVisitor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.CompoundTag;
import org.openzen.zencode.java.ZenCodeType;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * @docParam this {Hello : "World", Somewhere: "Over the rainbow"}
 */
@ZenCodeType.Name("crafttweaker.api.data.MapData")
@ZenRegister
@Document("vanilla/api/data/MapData")
public class MapData implements IData {
    
    private final CompoundTag internal;
    private final Set<String> boolDataKeys;
    
    public MapData(CompoundTag internal) {
        
        this(internal, new HashSet<>());
    }
    
    @ZenCodeType.Constructor
    public MapData() {
        
        this(new CompoundTag());
    }
    
    @ZenCodeType.Constructor
    public MapData(Map<String, IData> map) {
        
        this();
        putAll(map);
    }
    
    public MapData(CompoundTag internal, Set<String> boolDataKeys) {
        
        this.internal = internal;
        this.boolDataKeys = boolDataKeys;
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
        
        map.forEach((s, iData) -> {
            getInternal().put(s, iData.getInternal());
            if(iData instanceof BoolData) {
                boolDataKeys.add(s);
            }
        });
    }
    
    /**
     * Adds all entries from the given IData to this entry
     */
    @ZenCodeType.Operator(ZenCodeType.OperatorType.ADD)
    public MapData opAdd(IData data) {
        
        putAll(data.asMap());
        return this;
    }
    
    
    @ZenCodeType.Getter("keySet")
    public Set<String> getKeySet() {
        
        return getInternal().getAllKeys();
    }
    
    @ZenCodeType.Getter("size")
    public int getSize() {
        
        return getInternal().size();
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
    @ZenCodeType.Nullable
    @ZenCodeType.Operator(ZenCodeType.OperatorType.MEMBERSETTER)
    public IData put(String key, IData value) {
        
        if(value instanceof BoolData) {
            boolDataKeys.add(key);
        }
        return TagToDataConverter.convert(getInternal().put(key, value.getInternal()));
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
    @ZenCodeType.Nullable
    @ZenCodeType.Operator(ZenCodeType.OperatorType.MEMBERGETTER)
    public IData getAt(String key) {
        
        if(boolDataKeys.contains(key)) {
            return new BoolData(getInternal().getByte(key) == 1);
        }
        return TagToDataConverter.convert(getInternal().get(key));
    }
    
    /**
     * Retrieves the value associated with the key and returns it as the given type.
     *
     * @param key The key to search for.
     *
     * @return The value if present, null otherwise.
     *
     * @docParam key "Hello"
     */
    @ZenCodeType.Method
    @ZenCodeType.Nullable
    public <T extends IData> T getData(Class<T> clazz, String key) {
        
        if((clazz == null || clazz.equals(BoolData.class)) && boolDataKeys.contains(key)) {
            return (T) new BoolData(getInternal().getByte(key) == 1);
        }
        try {
            return TagToDataConverter.convertTo(getInternal().get(key), clazz);
        } catch(NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Unable to convert IData to " + clazz, e);
        }
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
    @ZenCodeType.Operator(ZenCodeType.OperatorType.CONTAINS)
    public boolean contains(String key) {
        
        return getInternal().contains(key);
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
        
        boolDataKeys.remove(key);
        getInternal().remove(key);
    }
    
    @ZenCodeType.Getter("isEmpty")
    public boolean isEmpty() {
        
        return getInternal().isEmpty();
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
     * @docParam other {Doodle: "Do"}
     */
    @ZenCodeType.Method
    public MapData merge(MapData other) {
        
        Set<String> newBoolDataKeys = new HashSet<>(boolDataKeys);
        newBoolDataKeys.addAll(other.boolDataKeys);
        return new MapData(getInternal().merge(other.getInternal()), newBoolDataKeys);
    }
    
    @Override
    public MapData copy() {
        
        return new MapData(getInternal(), new HashSet<>(boolDataKeys));
    }
    
    @Override
    public MapData copyInternal() {
        
        return new MapData(getInternal().copy(), new HashSet<>(boolDataKeys));
    }
    
    @Override
    public CompoundTag getInternal() {
        
        return internal;
    }
    
    @Override
    public Map<String, IData> asMap() {
        
        Map<String, IData> newMap = new HashMap<>();
        getInternal().getAllKeys().forEach(s -> newMap.put(s, getAt(s)));
        return newMap;
    }
    
    @Override
    public boolean contains(IData data) {
        
        if(data instanceof StringData) {
            return this.getInternal().contains(data.asString());
        }
        
        
        Map<String, IData> dataMap = data.asMap();
        if(dataMap == null) {
            return false;
        }
        
        for(Map.Entry<String, IData> dataEntry : dataMap.entrySet()) {
            if(!this.getInternal().contains(dataEntry.getKey())) {
                return false;
            } else if(!TagToDataConverter.convert(this.getInternal()
                    .get(dataEntry.getKey())).contains(dataEntry.getValue())) {
                return false;
            }
        }
        
        return true;
    }
    
    @ZenCodeType.Caster(implicit = true)
    public Map<String, IData> castToMap() {
        
        return this.asMap();
    }
    
    @Override
    public Type getType() {
        
        return Type.MAP;
    }
    
    @Override
    public <T> T accept(DataVisitor<T> visitor) {
        
        return visitor.visitMap(this);
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        
        MapData mapData = (MapData) o;
        
        return internal.equals(mapData.internal);
    }
    
    @Override
    public int hashCode() {
        
        return internal.hashCode();
    }
    
    public Set<String> boolDataKeys() {
        
        return boolDataKeys;
    }
    
}
