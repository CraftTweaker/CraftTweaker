package com.blamejared.crafttweaker.api.data;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.converter.tag.TagToDataConverter;
import com.blamejared.crafttweaker.api.data.visitor.DataVisitor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.mojang.datafixers.util.Pair;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;
import org.openzen.zencode.java.ZenCodeType;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @docParam this {Hello: "World", Somewhere: "Over the rainbow"}
 */
@ZenCodeType.Name("crafttweaker.api.data.MapData")
@ZenRegister
@Document("vanilla/api/data/MapData")
public class MapData implements IData {
    
    private final CompoundTag internal;
    private final Set<String> boolDataKeys;
    
    public MapData(CompoundTag internal, Set<String> boolDataKeys) {
        
        this.internal = internal;
        this.boolDataKeys = boolDataKeys;
    }
    
    public MapData(CompoundTag internal) {
        
        this.internal = internal;
        this.boolDataKeys = new HashSet<>();
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
    
    @Override
    public void remove(int index) {
        
        this.put(String.valueOf(index), null);
    }
    
    @Override
    public void remove(String key) {
        
        put(key, null);
    }
    
    @Override
    public IData getAt(int index) {
        
        return getAt(Integer.toString(index));
    }
    
    @Override
    public IData getAt(String key) {
        
        if(boolDataKeys.contains(key)) {
            return getInternal().getByte(key) == 1 ? BoolData.TRUE : BoolData.FALSE;
        }
        
        return TagToDataConverter.convert(getInternal().get(key));
    }
    
    @Override
    public boolean contains(IData other) {
        
        CompoundTag internal = this.getInternal();
        if(other instanceof StringData) {
            return internal.contains(other.getAsString());
        }
        
        if(!other.isMappable()) {
            return false;
        }
        Map<String, IData> dataMap = other.asMap();
        
        for(Map.Entry<String, IData> dataEntry : dataMap.entrySet()) {
            if(!internal.contains(dataEntry.getKey())) {
                return false;
            } else if(!TagToDataConverter.convert(internal.get(dataEntry.getKey())).contains(dataEntry.getValue())) {
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public void put(String name, @ZenCodeType.Nullable IData data) {
        
        if(data == null) {
            boolDataKeys.remove(name);
            getInternal().remove(name);
        } else {
            if(data instanceof BoolData) {
                boolDataKeys.add(name);
            }
            getInternal().put(name, data.getInternal());
        }
    }
    
    @Override
    public boolean equalTo(IData other) {
        
        if(this == other) {
            return true;
        }
        Map<String, IData> thisMap = asMap();
        Map<String, IData> otherMap = other.asMap();
        if(thisMap.size() != otherMap.size()) {
            return false;
        }
        
        for(Map.Entry<String, IData> entry : this.asMap().entrySet()) {
            if(!otherMap.containsKey(entry.getKey())) {
                return false;
            }
            if(!otherMap.get(entry.getKey()).equalTo(entry.getValue())) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean isMappable() {
        
        return true;
    }
    
    @Override
    public Map<String, IData> asMap() {
        
        return getInternal().getAllKeys()
                .stream()
                .map(s -> Pair.of(s, getAt(s)))
                .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
    }
    
    @Override
    public int length() {
        
        return getInternal().size();
    }
    
    @Override
    public Set<String> getKeys() {
        
        return getInternal().getAllKeys();
    }
    
    @Override
    public @NotNull Iterator<IData> iterator() {
        
        return getInternal().getAllKeys().stream().map(StringData::new).map(iData -> (IData) iData).toList().iterator();
    }
    
    @Override
    public CompoundTag getInternal() {
        
        return internal;
    }
    
    @Override
    public IData copy() {
        
        return new MapData(getInternal(), boolDataKeys);
    }
    
    @Override
    public IData copyInternal() {
        
        return new MapData(getInternal().copy(), new HashSet<>(boolDataKeys));
    }
    
    @Override
    public <T> T accept(DataVisitor<T> visitor) {
        
        return visitor.visitMap(this);
    }
    
    @Override
    public Type getType() {
        
        return Type.MAP;
    }
    
    public Set<String> boolDataKeys() {
        
        return boolDataKeys;
    }
    
    @Override
    public IData merge(IData other) {
        
        if(other instanceof MapData map) {
            Set<String> newBoolDataKeys = new HashSet<>(boolDataKeys);
            newBoolDataKeys.addAll(map.boolDataKeys);
            return new MapData(getInternal().merge(map.getInternal()), newBoolDataKeys);
        }
        throw new IllegalArgumentException("Cannot merge incompatible data type: " + other.getType());
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        MapData iData = (MapData) o;
        return Objects.equals(getInternal(), iData.getInternal()) && Objects.equals(boolDataKeys, iData.boolDataKeys);
    }
    
    @Override
    public int hashCode() {
        
        return Objects.hash(getInternal(), boolDataKeys);
    }
    
    @Override
    public String toString() {
        
        return getAsString();
    }
    
}
