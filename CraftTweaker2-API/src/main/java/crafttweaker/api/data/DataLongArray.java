package crafttweaker.api.data;

import crafttweaker.api.data.cast.CastResult;
import crafttweaker.api.data.cast.DataConverterLongArray;

import java.util.Arrays;

/**
 * @author youyihj
 */
public class DataLongArray implements IData {
    private final long[] data;
    private final boolean immutable;

    public DataLongArray(long[] data, boolean immutable) {
        this.data = data;
        this.immutable = immutable;
    }

    @Override
    public IData add(IData other) {
        long[] otherData = other.asLongArray();
        long[] result = Arrays.copyOf(data, data.length + otherData.length);
        System.arraycopy(otherData, 0, result, data.length, otherData.length);
        return new DataLongArray(result, immutable);
    }

    @Override
    public IData sub(IData other) {
        throw new UnsupportedOperationException("Cannot subtract from a long array");
    }

    @Override
    public IData mul(IData other) {
        throw new UnsupportedOperationException("Cannot multiply with a long array");
    }

    @Override
    public IData div(IData other) {
        throw new UnsupportedOperationException("Cannot divide from a long array");
    }

    @Override
    public IData mod(IData other) {
        throw new UnsupportedOperationException("Cannot perform modulo on a long array");
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
        return new DataLong(data[i]);
    }

    @Override
    public void setAt(int i, IData value) {
        if (immutable) {
            throw new UnsupportedOperationException("Cannot modify this long array");
        } else {
            data[i] = value.asLong();
        }
    }

    @Override
    public IData memberGet(String name) {
        throw new UnsupportedOperationException("long[] doesn't have members");
    }

    @Override
    public void memberSet(String name, IData data) {
        throw new UnsupportedOperationException("cannot set long[] members");
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
        throw new UnsupportedOperationException("cannot compare to a long array");
    }

    @Override
    public boolean equals(IData data) {
        if (data instanceof DataLongArray) {
            return Arrays.equals(this.data, ((DataLongArray) data).data);
        } else {
            CastResult<long[]> result = data.convert(DataConverterLongArray.INSTANCE);
            return result.isOk() && Arrays.equals(this.data, result.get());
        }
    }

    @Override
    public IData immutable() {
        if (immutable) {
            return this;
        } else {
            return new DataLongArray(Arrays.copyOf(data, data.length), true);
        }
    }

    @Override
    public IData update(IData data) {
        return data;
    }

    @Override
    public <T> T convert(IDataConverter<T> converter) {
        return converter.fromLongArray(data);
    }
}
