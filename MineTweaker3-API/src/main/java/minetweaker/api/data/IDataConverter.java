package minetweaker.api.data;

import java.util.List;
import java.util.Map;

/**
 * Data converter interface. Used to convert data elements to other types.
 * 
 * @author Stan Hebben
 * @param <T> output type
 */
public interface IDataConverter<T> {
	T fromBool(boolean value);

	T fromByte(byte value);

	T fromShort(short value);

	T fromInt(int value);

	T fromLong(long value);

	T fromFloat(float value);

	T fromDouble(double value);

	T fromString(String value);

	T fromList(List<IData> values);

	T fromMap(Map<String, IData> values);

	T fromByteArray(byte[] value);

	T fromIntArray(int[] value);
}
