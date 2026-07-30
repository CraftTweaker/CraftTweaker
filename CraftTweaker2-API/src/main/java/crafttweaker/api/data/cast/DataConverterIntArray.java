package crafttweaker.api.data.cast;

import crafttweaker.api.data.IData;
import crafttweaker.api.data.IDataConverter;

import java.util.List;
import java.util.Map;

/**
 * @author youyihj
 */
public enum DataConverterIntArray implements IDataConverter<CastResult<int[]>> {
    INSTANCE;

    @Override
    public CastResult<int[]> fromBool(boolean value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<int[]> fromByte(byte value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<int[]> fromShort(short value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<int[]> fromInt(int value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<int[]> fromLong(long value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<int[]> fromFloat(float value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<int[]> fromDouble(double value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<int[]> fromString(String value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<int[]> fromList(List<IData> values) {
        int[] result = new int[values.size()];
        for (int i = 0; i < values.size(); i++) {
            IData data = values.get(i);
            CastResult<Number> castResult = data.convert(DataConverterNumber.INSTANCE);
            if (!castResult.isOk()) {
                return CastResult.fail("Cannot convert " + data + " to int at index " + i);
            }
            result[i] = castResult.get().intValue();
        }
        return CastResult.ok(result);
    }

    @Override
    public CastResult<int[]> fromMap(Map<String, IData> values) {
        return CastResult.ok(null);
    }

    @Override
    public CastResult<int[]> fromByteArray(byte[] value) {
        int[] result = new int[value.length];
        for(int i = 0; i < result.length; i++) {
            result[i] = value[i];
        }
        return CastResult.ok(result);
    }

    @Override
    public CastResult<int[]> fromIntArray(int[] value) {
        return CastResult.ok(value);
    }

    @Override
    public CastResult<int[]> fromLongArray(long[] value) {
        int[] result = new int[value.length];
        for(int i = 0; i < result.length; i++) {
            result[i] = (int) value[i];
        }
        return CastResult.ok(result);
    }
}
