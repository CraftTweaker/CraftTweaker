package crafttweaker.api.data;

import crafttweaker.api.data.cast.CastResult;
import crafttweaker.api.data.cast.DataConverterNumber;

import java.util.List;
import java.util.Map;

/**
 * Contains a 64-bit long value.
 *
 * @author Stan Hebben
 */
public class DataLong implements IData {
    
    private final long value;
    
    public DataLong(long value) {
        this.value = value;
    }
    
    @Override
    public IData getAt(int i) {
        throw new UnsupportedOperationException("A long is not indexable");
    }
    
    @Override
    public void setAt(int i, IData value) {
        throw new UnsupportedOperationException("A long is not indexable");
    }
    
    @Override
    public IData memberGet(String name) {
        throw new UnsupportedOperationException("A long is not indexable");
    }
    
    @Override
    public void memberSet(String name, IData data) {
        throw new UnsupportedOperationException("A long is not indexable");
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
        if (data instanceof DataLong) {
            return value == ((DataLong) data).value;
        }
        CastResult<Number> result = data.convert(DataConverterNumber.INSTANCE);
        return result.isOk() && value == result.get().longValue();
    }
    
    @Override
    public int compareTo(IData data) {
        return Long.compare(value, data.asLong());
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
        return converter.fromLong(value);
    }
    
    @Override
    public IData add(IData other) {
        return new DataLong(value + other.asLong());
    }
    
    @Override
    public IData sub(IData other) {
        return new DataLong(value - other.asLong());
    }
    
    @Override
    public IData mul(IData other) {
        return new DataLong(value * other.asLong());
    }
    
    @Override
    public IData div(IData other) {
        return new DataLong(value / other.asLong());
    }
    
    @Override
    public IData mod(IData other) {
        return new DataLong(value % other.asLong());
    }
    
    @Override
    public IData and(IData other) {
        return new DataLong(value & other.asLong());
    }
    
    @Override
    public IData or(IData other) {
        return new DataLong(value | other.asLong());
    }
    
    @Override
    public IData xor(IData other) {
        return new DataLong(value ^ other.asLong());
    }
    
    @Override
    public IData neg() {
        return new DataLong(-value);
    }
    
    @Override
    public IData not() {
        return new DataLong(~value);
    }
    
    @Override
    public String toString() {
        return asString() + " as long";
    }

    // binary compat
    @Override
    public boolean asBool() {
        return IData.super.asBool();
    }

    @Override
    public byte asByte() {
        return IData.super.asByte();
    }

    @Override
    public short asShort() {
        return IData.super.asShort();
    }

    @Override
    public int asInt() {
        return IData.super.asInt();
    }

    @Override
    public long asLong() {
        return IData.super.asLong();
    }

    @Override
    public float asFloat() {
        return IData.super.asFloat();
    }

    @Override
    public double asDouble() {
        return IData.super.asDouble();
    }

    @Override
    public String asString() {
        return IData.super.asString();
    }

    @Override
    public List<IData> asList() {
        return IData.super.asList();
    }

    @Override
    public Map<String, IData> asMap() {
        return IData.super.asMap();
    }

    @Override
    public byte[] asByteArray() {
        return IData.super.asByteArray();
    }

    @Override
    public int[] asIntArray() {
        return IData.super.asIntArray();
    }
}
