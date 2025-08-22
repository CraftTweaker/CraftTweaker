package crafttweaker.api.data.cast;

import crafttweaker.api.data.IData;
import crafttweaker.api.data.IDataConverter;

import java.util.List;
import java.util.Map;

/**
 * @author youyihj
 */
public enum DataConverterString implements IDataConverter<CastResult<String>> {
    INSTANCE;

    @Override
    public CastResult<String> fromBool(boolean value) {
        return CastResult.ok(Boolean.toString(value));
    }

    @Override
    public CastResult<String> fromByte(byte value) {
        return CastResult.ok(Byte.toString(value));
    }

    @Override
    public CastResult<String> fromShort(short value) {
        return CastResult.ok(Short.toString(value));
    }

    @Override
    public CastResult<String> fromInt(int value) {
        return CastResult.ok(Integer.toString(value));
    }

    @Override
    public CastResult<String> fromLong(long value) {
        return CastResult.ok(Long.toString(value));
    }

    @Override
    public CastResult<String> fromFloat(float value) {
        return CastResult.ok(Float.toString(value));
    }

    @Override
    public CastResult<String> fromDouble(double value) {
        return CastResult.ok(Double.toString(value));
    }

    @Override
    public CastResult<String> fromString(String value) {
        return CastResult.ok(value);
    }

    @Override
    public CastResult<String> fromList(List<IData> values) {
        StringBuilder output = new StringBuilder();
        output.append('[');
        boolean first = true;
        for (IData value : values) {
            if (first) {
                first = false;
            } else {
                output.append(", ");
            }
            output.append(value);
        }
        output.append(']');
        return CastResult.ok(output.toString());
    }

    @Override
    public CastResult<String> fromMap(Map<String, IData> values) {
        StringBuilder result = new StringBuilder();
        result.append('{');
        boolean first = true;
        for (Map.Entry<String, IData> entry : values.entrySet()) {
            if (first) {
                first = false;
            } else {
                result.append(", ");
            }

            if (isValidIdentifier(entry.getKey())) {
                result.append(entry.getKey());
            } else {
                result.append("\"").append(entry.getKey()).append("\"");
            }

            result.append(": ");
            result.append(entry.getValue());
        }
        result.append('}');
        return CastResult.ok(result.toString());
    }

    @Override
    public CastResult<String> fromByteArray(byte[] value) {
        StringBuilder result = new StringBuilder();
        result.append("[");
        boolean first = true;
        for (byte b : value) {
            if (first)
                first = false;
            else
                result.append(", ");

            result.append(b);
        }
        result.append("] as byte[]");
        return CastResult.ok(result.toString());
    }

    @Override
    public CastResult<String> fromIntArray(int[] value) {
        StringBuilder result = new StringBuilder();
        result.append('[');
        boolean first = true;
        for (int i : value) {
            if (first) {
                first = false;
            } else {
                result.append(", ");
            }
            result.append(i);
        }
        result.append(']');
        return CastResult.ok(result.toString());
    }

    private boolean isValidIdentifier(String str) {
        if (!Character.isJavaIdentifierStart(str.charAt(0)))
            return false;

        for (int i = 1; i < str.length(); i++) {
            if (!Character.isJavaIdentifierPart(str.charAt(i)))
                return false;
        }

        return true;
    }
}
