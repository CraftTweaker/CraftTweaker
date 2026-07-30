package crafttweaker.api.data.cast;

import crafttweaker.api.data.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author youyihj
 */
public enum DataConverterList implements IDataConverter<CastResult<List<IData>>> {
    INSTANCE;

    @Override
    public CastResult<List<IData>> fromBool(boolean value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<List<IData>> fromByte(byte value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<List<IData>> fromShort(short value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<List<IData>> fromInt(int value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<List<IData>> fromLong(long value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<List<IData>> fromFloat(float value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<List<IData>> fromDouble(double value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<List<IData>> fromString(String value) {
        return CastResult.nil();
    }

    @Override
    public CastResult<List<IData>> fromList(List<IData> values) {
        return CastResult.ok(values);
    }

    @Override
    public CastResult<List<IData>> fromMap(Map<String, IData> values) {
        return CastResult.nil();
    }

    @Override
    public CastResult<List<IData>> fromByteArray(byte[] value) {
        List<IData> result = new ArrayList<>(value.length);
        for (byte b : value) {
            result.add(new DataByte(b));
        }
        return CastResult.ok(result);
    }

    @Override
    public CastResult<List<IData>> fromIntArray(int[] value) {
        List<IData> result = new ArrayList<>(value.length);
        for (int i : value) {
            result.add(new DataInt(i));
        }
        return CastResult.ok(result);
    }

    @Override
    public CastResult<List<IData>> fromLongArray(long[] value) {
        List<IData> result = new ArrayList<>(value.length);
        for (long l : value) {
            result.add(new DataLong(l));
        }
        return CastResult.ok(result);
    }
}
