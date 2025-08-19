package crafttweaker.api.data.cast;

import crafttweaker.api.data.IData;
import crafttweaker.api.data.IDataConverter;

import java.util.List;
import java.util.Map;

/**
 * @author youyihj
 */
public enum DataConverterBool implements IDataConverter<CastResult<Boolean>> {
    INSTANCE;

    @Override
    public CastResult<Boolean> fromBool(boolean value) {
        return CastResult.ok(value);
    }

    @Override
    public CastResult<Boolean> fromByte(byte value) {
        return CastResult.ok(value == 1);
    }

    @Override
    public CastResult<Boolean> fromShort(short value) {
        return CastResult.ok(value == 1);
    }

    @Override
    public CastResult<Boolean> fromInt(int value) {
        return CastResult.ok(value == 1);
    }

    @Override
    public CastResult<Boolean> fromLong(long value) {
        return CastResult.ok(value == 1L);
    }

    @Override
    public CastResult<Boolean> fromFloat(float value) {
        return CastResult.ok(value == 1.0f);
    }

    @Override
    public CastResult<Boolean> fromDouble(double value) {
        return CastResult.ok(value == 1.0d);
    }

    @Override
    public CastResult<Boolean> fromString(String value) {
        return CastResult.ok(Boolean.parseBoolean(value));
    }

    @Override
    public CastResult<Boolean> fromList(List<IData> values) {
        return CastResult.fail("Cannot convert a list to a boolean value.");
    }

    @Override
    public CastResult<Boolean> fromMap(Map<String, IData> values) {
        return CastResult.fail("Cannot convert a map to a boolean value.");
    }

    @Override
    public CastResult<Boolean> fromByteArray(byte[] value) {
        return CastResult.fail("Cannot convert a byte array to a boolean value.");
    }

    @Override
    public CastResult<Boolean> fromIntArray(int[] value) {
        return CastResult.fail("Cannot convert an int array to a boolean value.");
    }
}
