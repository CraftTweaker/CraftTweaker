package crafttweaker.api.data;

import crafttweaker.api.data.cast.CastResult;
import crafttweaker.api.data.cast.DataConverterByteArray;

import java.util.*;

/**
 * Contains a byte array.
 *
 * @author Stan Hebben
 */
public class DataByteArray implements IData {
    
    private final byte[] data;
    private final boolean immutable;
    
    public DataByteArray(byte[] data, boolean immutable) {
        this.data = data;
        this.immutable = immutable;
    }
    
    @Override
    public IData add(IData other) {
        byte[] otherData = other.asByteArray();
        byte[] result = Arrays.copyOf(data, data.length + otherData.length);
        System.arraycopy(otherData, 0, result, data.length, otherData.length);
        return new DataByteArray(result, immutable);
    }
    
    @Override
    public IData sub(IData other) {
        throw new UnsupportedOperationException("Cannot subtract from a byte array");
    }
    
    @Override
    public IData mul(IData other) {
        throw new UnsupportedOperationException("Cannot multiply with an array");
    }
    
    @Override
    public IData div(IData other) {
        throw new UnsupportedOperationException("Cannot divide an array");
    }
    
    @Override
    public IData mod(IData other) {
        throw new UnsupportedOperationException("Cannot perform modulo on an array");
    }
    
    @Override
    public IData and(IData other) {
        throw new UnsupportedOperationException("Arrays don't support bitwise operations");
    }
    
    @Override
    public IData or(IData other) {
        throw new UnsupportedOperationException("Arrays don't support bitwise operations");
    }
    
    @Override
    public IData xor(IData other) {
        throw new UnsupportedOperationException("Arrays don't support bitwise operations");
    }
    
    @Override
    public IData neg() {
        throw new UnsupportedOperationException("Cannot negate arrays");
    }
    
    @Override
    public IData not() {
        throw new UnsupportedOperationException("Arrays don't support bitwise operations");
    }
    
    @Override
    public IData getAt(int i) {
        return new DataByte(data[i]);
    }
    
    @Override
    public void setAt(int i, IData value) {
        if(immutable) {
            throw new UnsupportedOperationException("Cannot modify this byte array");
        } else {
            data[i] = value.asByte();
        }
    }
    
    @Override
    public IData memberGet(String name) {
        throw new UnsupportedOperationException("byte[] doesn't have members");
    }
    
    @Override
    public void memberSet(String name, IData data) {
        throw new UnsupportedOperationException("cannot set byte[] members");
    }
    
    @Override
    public int length() {
        return data.length;
    }
    
    @Override
    public boolean contains(IData data) {
        return equals(data);
    }
    
    @Override
    public int compareTo(IData data) {
        throw new UnsupportedOperationException("Cannot compare arrays");
    }
    
    @Override
    public boolean equals(IData data) {
        if (data instanceof DataByteArray) {
            return Arrays.equals(this.data, ((DataByteArray) data).data);
        }
        CastResult<byte[]> result = data.convert(DataConverterByteArray.INSTANCE);
        return result.isOk() && Arrays.equals(this.data, result.get());
    }
    
    @Override
    public IData immutable() {
        if(immutable) {
            return this;
        } else {
            return new DataByteArray(Arrays.copyOf(this.data, this.data.length), true);
        }
    }
    
    @Override
    public IData update(IData data) {
        return data;
    }
    
    @Override
    public <T> T convert(IDataConverter<T> converter) {
        return converter.fromByteArray(this.data);
    }
    
    @Override
    public String toString() {
        return asString() + " as byte[]";
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
