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
	public T fromBool(boolean value);

	public T fromByte(byte value);

	public T fromShort(short value);

	public T fromInt(int value);

	public T fromLong(long value);

	public T fromFloat(float value);

	public T fromDouble(double value);

	public T fromString(String value);

	public T fromList(List<IData> values);

	public T fromMap(Map<String, IData> values);

	public T fromByteArray(byte[] value);

	public T fromIntArray(int[] value);
}
