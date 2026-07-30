package crafttweaker.api.data.cast;

import crafttweaker.api.data.IData;
import crafttweaker.api.data.IDataConverter;

import java.util.List;
import java.util.Map;

/**
 * @author youyihj
 */
public enum DataConverterLongArray implements IDataConverter<CastResult<long[]>> {
    INSTANCE;

    @Override
    public CastResult<long[]> fromBool(boolean value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<long[]> fromByte(byte value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<long[]> fromShort(short value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<long[]> fromInt(int value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<long[]> fromLong(long value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<long[]> fromFloat(float value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<long[]> fromDouble(double value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<long[]> fromString(String value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<long[]> fromList(List<IData> values) {
        long[] result = new long[values.size()];
        for (int i = 0; i < values.size(); i++) {
            IData data = values.get(i);
            CastResult<Number> castResult = data.convert(DataConverterNumber.INSTANCE);
            if (!castResult.isOk()) {
                return CastResult.fail("Cannot convert " + data + " to long at index " + i);
            }
            result[i] = castResult.get().longValue();
        }
        return CastResult.ok(result);
    }

    @Override
    public CastResult<long[]> fromMap(Map<String, IData> values) {
        return CastResult.nil();
    }

    @Override
    public CastResult<long[]> fromByteArray(byte[] value) {
        long[] result = new long[value.length];
        for (int i = 0; i < value.length; i++) {
            result[i] = value[i];
        }
        return CastResult.ok(result);
    }

    @Override
    public CastResult<long[]> fromIntArray(int[] value) {
        long[] result = new long[value.length];
        for (int i = 0; i < value.length; i++) {
            result[i] = value[i];
        }
        return CastResult.ok(result);
    }

    @Override
    public CastResult<long[]> fromLongArray(long[] value) {
        return CastResult.ok(value);
    }
}
