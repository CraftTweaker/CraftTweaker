/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.minecraft.data;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Stan
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
