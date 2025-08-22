package crafttweaker.api.data.cast;

import crafttweaker.api.data.DataString;
import crafttweaker.api.data.IData;
import crafttweaker.api.data.IDataConverter;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author youyihj
 */
public enum DataConverterMap implements IDataConverter<CastResult<Map<String, IData>>> {
    INSTANCE;

    @Override
    public CastResult<Map<String, IData>> fromBool(boolean value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<Map<String, IData>> fromByte(byte value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<Map<String, IData>> fromShort(short value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<Map<String, IData>> fromInt(int value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<Map<String, IData>> fromLong(long value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<Map<String, IData>> fromFloat(float value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<Map<String, IData>> fromDouble(double value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<Map<String, IData>> fromString(String value) {
        return CastResult.ok(Collections.singletonMap(value, new DataString(value)));
    }

    @Override
    public CastResult<Map<String, IData>> fromList(List<IData> values) {
        return CastResult.nil();
    }

    @Override
    public CastResult<Map<String, IData>> fromMap(Map<String, IData> values) {
        return CastResult.ok(values);
    }

    @Override
    public CastResult<Map<String, IData>> fromByteArray(byte[] value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<Map<String, IData>> fromIntArray(int[] value) {
        return CastResult.nil();
    }
}
