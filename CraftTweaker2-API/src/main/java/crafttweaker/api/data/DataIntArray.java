package crafttweaker.api.data;

import crafttweaker.api.data.cast.CastResult;
import crafttweaker.api.data.cast.DataConverterIntArray;

import java.util.*;

/**
 * Contains an int array.
 *
 * @author Stan Hebben
 */
public class DataIntArray implements IData {
    
    private final int[] data;
    private final boolean immutable;
    
    public DataIntArray(int[] data, boolean immutable) {
        this.data = data;
        this.immutable = immutable;
    }
    
    @Override
    public IData add(IData other) {
        int[] otherData = other.asIntArray();
        int[] result = Arrays.copyOf(data, data.length + otherData.length);
        System.arraycopy(otherData, 0, result, data.length, otherData.length);
        return new DataIntArray(result, immutable);
    }
    
    @Override
    public IData sub(IData other) {
        throw new UnsupportedOperationException("Cannot subtract from an int array");
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
        return new DataInt(data[i]);
    }
    
    @Override
    public void setAt(int i, IData value) {
        if(immutable) {
            throw new UnsupportedOperationException("Cannot modify this byte array");
        } else {
            data[i] = value.asInt();
        }
    }
    
    @Override
    public IData memberGet(String name) {
        throw new UnsupportedOperationException("int[] doesn't have members");
    }
    
    @Override
    public void memberSet(String name, IData data) {
        throw new UnsupportedOperationException("cannot set int[] members");
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
        if (data instanceof DataIntArray) {
            return Arrays.equals(((DataIntArray) data).data, this.data);
        }
        CastResult<int[]> result = data.convert(DataConverterIntArray.INSTANCE);
        return result.isOk() && Arrays.equals(result.get(), this.data);
    }
    
    @Override
    public IData immutable() {
        if(immutable) {
            return this;
        } else {
            return new DataIntArray(Arrays.copyOf(this.data, this.data.length), true);
        }
    }
    
    @Override
    public IData update(IData data) {
        return data;
    }
    
    @Override
    public <T> T convert(IDataConverter<T> converter) {
        return converter.fromIntArray(this.data);
    }
    
    @Override
    public String toString() {
        return asString() + " as int[]";
    }
}
