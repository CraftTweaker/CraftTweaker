package crafttweaker.api.data.cast;

import crafttweaker.api.data.IData;
import crafttweaker.api.data.IDataConverter;

import java.util.List;
import java.util.Map;

/**
 * @author youyihj
 */
public enum DataConverterByteArray implements IDataConverter<CastResult<byte[]>> {
    INSTANCE;

    @Override
    public CastResult<byte[]> fromBool(boolean value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<byte[]> fromByte(byte value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<byte[]> fromShort(short value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<byte[]> fromInt(int value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<byte[]> fromLong(long value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<byte[]> fromFloat(float value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<byte[]> fromDouble(double value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<byte[]> fromString(String value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<byte[]> fromList(List<IData> values) {
        byte[] result = new byte[values.size()];
        for (int i = 0; i < values.size(); i++) {
            IData data = values.get(i);
            CastResult<Number> castResult = data.convert(DataConverterNumber.INSTANCE);
            if (!castResult.isOk()) {
                return CastResult.fail("Cannot convert " + data + " to byte at index " + i);
            }
            result[i] = castResult.get().byteValue();
        }
        return CastResult.ok(result);
    }

    @Override
    public CastResult<byte[]> fromMap(Map<String, IData> values) {
        return CastResult.nil();
    }

    @Override
    public CastResult<byte[]> fromByteArray(byte[] value) {
        return CastResult.ok(value);
    }

    @Override
    public CastResult<byte[]> fromIntArray(int[] value) {
        byte[] result = new byte[value.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) value[i];
        }
        return CastResult.ok(result);
    }

    @Override
    public CastResult<byte[]> fromLongArray(long[] value) {
        byte[] result = new byte[value.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) value[i];
        }
        return CastResult.ok(result);
    }
}
