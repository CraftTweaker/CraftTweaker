package crafttweaker.api.data;

import crafttweaker.api.data.cast.CastResult;
import crafttweaker.api.data.cast.DataConverterNumber;

/**
 * Contains a byte value (-128 .. 127)
 *
 * @author Stan Hebben
 */
public class DataByte implements IData {
    
    private final byte value;
    
    public DataByte(byte value) {
        this.value = value;
    }
    
    @Override
    public IData getAt(int i) {
        throw new UnsupportedOperationException("A byte is not indexable");
    }
    
    @Override
    public void setAt(int i, IData value) {
        throw new UnsupportedOperationException("A byte is not indexable");
    }
    
    @Override
    public IData memberGet(String name) {
        throw new UnsupportedOperationException("A byte is not indexable");
    }
    
    @Override
    public void memberSet(String name, IData data) {
        throw new UnsupportedOperationException("A byte is not indexable");
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
        if (data instanceof DataByte) {
            return ((DataByte) data).value == value;
        }
        CastResult<Number> result = data.convert(DataConverterNumber.INSTANCE);
        return result.isOk() && result.get().byteValue() == value;
    }
    
    @Override
    public int compareTo(IData data) {
        return Byte.compare(value, data.asByte());
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
        return converter.fromByte(value);
    }
    
    @Override
    public IData add(IData other) {
        return new DataByte((byte) (value + other.asByte()));
    }
    
    @Override
    public IData sub(IData other) {
        return new DataByte((byte) (value - other.asByte()));
    }
    
    @Override
    public IData mul(IData other) {
        return new DataByte((byte) (value * other.asByte()));
    }
    
    @Override
    public IData div(IData other) {
        return new DataByte((byte) (value / other.asByte()));
    }
    
    @Override
    public IData mod(IData other) {
        return new DataByte((byte) (value % other.asByte()));
    }
    
    @Override
    public IData and(IData other) {
        return new DataByte((byte) (value & other.asByte()));
    }
    
    @Override
    public IData or(IData other) {
        return new DataByte((byte) (value | other.asByte()));
    }
    
    @Override
    public IData xor(IData other) {
        return new DataByte((byte) (value ^ other.asByte()));
    }
    
    @Override
    public IData neg() {
        return new DataByte((byte) (-value));
    }
    
    @Override
    public IData not() {
        return new DataByte((byte) (~value));
    }
    
    @Override
    public String toString() {
        return asString() + " as byte";
    }
}
