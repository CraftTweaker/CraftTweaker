package crafttweaker.api.data.cast;

import crafttweaker.api.data.IData;
import crafttweaker.api.data.IDataConverter;

import java.util.List;
import java.util.Map;

/**
 * @author youyihj
 */
public enum DataConverterNumber implements IDataConverter<CastResult<Number>> {
    INSTANCE;

    @Override
    public CastResult<Number> fromBool(boolean value) {
        return CastResult.ok(value ? 1 : 0);
    }

    @Override
    public CastResult<Number> fromByte(byte value) {
        return CastResult.ok(value);
    }

    @Override
    public CastResult<Number> fromShort(short value) {
        return CastResult.ok(value);
    }

    @Override
    public CastResult<Number> fromInt(int value) {
        return CastResult.ok(value);
    }

    @Override
    public CastResult<Number> fromLong(long value) {
        return CastResult.ok(value);
    }

    @Override
    public CastResult<Number> fromFloat(float value) {
        return CastResult.ok(value);
    }

    @Override
    public CastResult<Number> fromDouble(double value) {
        return CastResult.ok(value);
    }

    @Override
    public CastResult<Number> fromString(String value) {
        if (value.contains(".")) {
            try {
                return CastResult.ok(Double.parseDouble(value));
            } catch (NumberFormatException e) {
                return CastResult.fail("DataString " + value + " cannot be parsed to Double!");
            }
        } else {
            try {
                return CastResult.ok(Long.parseLong(value));
            } catch (NumberFormatException e) {
                return CastResult.fail("DataString " + value + " cannot be parsed to Long!");
            }
        }
    }

    @Override
    public CastResult<Number> fromList(List<IData> values) {
        return CastResult.fail("Cannot convert a list to a number");
    }

    @Override
    public CastResult<Number> fromMap(Map<String, IData> values) {
        return CastResult.fail("Cannot convert a map to a number");
    }

    @Override
    public CastResult<Number> fromByteArray(byte[] value) {
        return CastResult.fail("Cannot convert a byte array to a number");
    }

    @Override
    public CastResult<Number> fromIntArray(int[] value) {
        return CastResult.fail("Cannot convert an int array to a number");
    }
}
