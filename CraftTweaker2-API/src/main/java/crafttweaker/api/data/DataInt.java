package crafttweaker.api.data;

import crafttweaker.api.data.cast.CastResult;
import crafttweaker.api.data.cast.DataConverterNumber;

/**
 * Contains a 32-bit integer value.
 *
 * @author Stan Hebben
 */
public class DataInt implements IData {
    
    private final int value;
    
    public DataInt(int value) {
        this.value = value;
    }
    
    @Override
    public IData getAt(int i) {
        throw new RuntimeException("An int is not indexable");
    }
    
    @Override
    public void setAt(int i, IData value) {
        throw new RuntimeException("An int is not indexable");
    }
    
    @Override
    public IData memberGet(String name) {
        throw new RuntimeException("An int is not indexable");
    }
    
    @Override
    public void memberSet(String name, IData data) {
        throw new RuntimeException("An int is not indexable");
    }
    
    @Override
    public int length() {
        return 0;
    }
    
    @Override
    public boolean contains(IData data) {
        return equals(data);
    }
    
    @Override
    public boolean equals(IData data) {
        if (data instanceof DataInt) {
            return ((DataInt) data).value == value;
        }
        CastResult<Number> result = data.convert(DataConverterNumber.INSTANCE);
        return result.isOk() && result.get().intValue() == value;
    }
    
    @Override
    public int compareTo(IData data) {
        return Integer.compare(value, data.asInt());
    }
    
    @Override
    public IData immutable() {
        return this;
    }
    
    @Override
    public IData update(IData data) {
        return data;
    }
    
    @Override
    public <T> T convert(IDataConverter<T> converter) {
        return converter.fromInt(value);
    }
    
    @Override
    public IData add(IData other) {
        return new DataInt(value + other.asInt());
    }
    
    @Override
    public IData sub(IData other) {
        return new DataInt(value - other.asInt());
    }
    
    @Override
    public IData mul(IData other) {
        return new DataInt(value * other.asInt());
    }
    
    @Override
    public IData div(IData other) {
        return new DataInt(value / other.asInt());
    }
    
    @Override
    public IData mod(IData other) {
        return new DataInt(value % other.asInt());
    }
    
    @Override
    public IData and(IData other) {
        return new DataInt(value & other.asInt());
    }
    
    @Override
    public IData or(IData other) {
        return new DataInt(value | other.asInt());
    }
    
    @Override
    public IData xor(IData other) {
        return new DataInt(value ^ other.asInt());
    }
    
    @Override
    public IData neg() {
        return new DataInt(-value);
    }
    
    @Override
    public IData not() {
        return new DataInt(~value);
    }
    
    @Override
    public String toString() {
        return asString();
    }
}
