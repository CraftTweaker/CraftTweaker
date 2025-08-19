package crafttweaker.api.data;

import java.util.*;

/**
 * Contains a map, mapping strings to data elements.
 *
 * @author Stan Hebben
 */
public class DataMap implements IData {
    
    public static final DataMap EMPTY = new DataMap(new HashMap<>(), true);
    
    private final Map<String, IData> data;
    private final boolean immutable;
    
    public DataMap(Map<String, IData> data, boolean immutable) {
        this.data = data;
        this.immutable = immutable;
    }
    
    @Override
    public IData add(IData other) {
        Map<String, IData> result = new HashMap<>();
        Map<String, IData> otherMap = other.asMap();
        
        for(Map.Entry<String, IData> entry : data.entrySet()) {
            result.put(entry.getKey(), entry.getValue());
        }
        for(Map.Entry<String, IData> entry : otherMap.entrySet()) {
            if((result.containsKey(entry.getKey())) && (entry.getValue() instanceof DataMap) && (result.get(entry.getKey()) instanceof DataMap)) {
                result.put(entry.getKey(), result.get(entry.getKey()).add(entry.getValue()));
            } else if((result.containsKey(entry.getKey())) && (entry.getValue() instanceof DataList) && (result.get(entry.getKey()) instanceof DataList)) {
                result.put(entry.getKey(), result.get(entry.getKey()).add(entry.getValue()));
            } else {
                result.put(entry.getKey(), entry.getValue());
            }
            
        }
        
        return new DataMap(result, immutable);
    }
    
    @Override
    public IData sub(IData other) {
        Map<String, IData> result = new HashMap<>();
        Map<String, IData> otherMap = other.asMap();
        
        for(Map.Entry<String, IData> entry : data.entrySet()) {
            result.put(entry.getKey(), entry.getValue());
        }
        for(Map.Entry<String, IData> entry : otherMap.entrySet()) {
            if(entry.getValue() instanceof DataMap && result.get(entry.getKey()) instanceof DataMap) {
                result.put(entry.getKey(), result.get(entry.getKey()).sub(entry.getValue()));
            } else {
                result.remove(entry.getKey());
            }
        }
        
        return new DataMap(result, immutable);
    }
    
    @Override
    public IData mul(IData other) {
        throw new UnsupportedOperationException("Cannot multiply maps");
    }
    
    @Override
    public IData div(IData other) {
        throw new UnsupportedOperationException("Cannot divide maps");
    }
    
    @Override
    public IData mod(IData other) {
        throw new UnsupportedOperationException("Cannot perform modulo with maps");
    }
    
    @Override
    public IData and(IData other) {
        throw new UnsupportedOperationException("Maps do not support bitwise operations");
    }
    
    @Override
    public IData or(IData other) {
        throw new UnsupportedOperationException("Maps do not support bitwise operations");
    }
    
    @Override
    public IData xor(IData other) {
        throw new UnsupportedOperationException("Maps do not support bitwise operations");
    }
    
    @Override
    public IData neg() {
        throw new UnsupportedOperationException("Cannot negate maps.");
    }
    
    @Override
    public IData not() {
        throw new UnsupportedOperationException("Maps do not support bitwise operations");
    }
    
    @Override
    public IData getAt(int i) {
        return memberGet(Integer.toString(i));
    }
    
    @Override
    public void setAt(int i, IData value) {
        memberSet(Integer.toString(i), value);
    }
    
    @Override
    public IData memberGet(String name) {
        return data.get(name);
    }
    
    @Override
    public void memberSet(String name, IData data) {
        if(immutable) {
            throw new UnsupportedOperationException("this map is not modifiable");
        } else {
            this.data.put(name, data);
        }
    }
    
    @Override
    public int length() {
        return data.size();
    }
    
    @Override
    public boolean contains(IData data) {
        if(data instanceof DataString) {
            return this.data.containsKey(data.asString());
        }
        
        Map<String, IData> dataMap = data.asMap();
        if(dataMap == null)
            return false;
        
        for(Map.Entry<String, IData> dataEntry : dataMap.entrySet()) {
            if(!this.data.containsKey(dataEntry.getKey())) {
                return false;
            } else if(!this.data.get(dataEntry.getKey()).contains(dataEntry.getValue())) {
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public int compareTo(IData data) {
        throw new UnsupportedOperationException("Cannot compare maps");
    }
    
    @Override
    public boolean equals(IData data) {
        if(this == data)
            return true;
        
        Map<String, IData> dataMap = data.asMap();
        if (dataMap == null) return false;
        if(dataMap.size() != this.data.size())
            return false;
        
        for(Map.Entry<String, IData> dataEntry : this.data.entrySet()) {
            if(!dataMap.containsKey(dataEntry.getKey())) {
                return false;
            } else if(!dataMap.get(dataEntry.getKey()).equals(dataEntry.getValue())) {
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public IData immutable() {
        if(immutable) {
            return this;
        } else {
            Map<String, IData> result = new HashMap<>();
            for(Map.Entry<String, IData> entry : this.data.entrySet()) {
                result.put(entry.getKey(), entry.getValue().immutable());
            }
            return new DataMap(result, true);
        }
    }
    
    @Override
    public IData update(IData data) {
        if(immutable)
            data = data.immutable();
        
        Map<String, IData> result = new HashMap<>();
        for(Map.Entry<String, IData> entry : this.data.entrySet()) {
            result.put(entry.getKey(), entry.getValue());
        }
        for(Map.Entry<String, IData> entry : data.asMap().entrySet()) {
            result.put(entry.getKey(), entry.getValue());
        }
        return new DataMap(result, immutable);
    }
    
    @Override
    public <T> T convert(IDataConverter<T> converter) {
        return converter.fromMap(data);
    }
    
    @Override
    public String toString() {
        return asString();
    }
}
