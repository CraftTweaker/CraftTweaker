/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Stan
 */
public class DataIntArray implements IData {
	private final int[] data;
	private final boolean immutable;
	
	public DataIntArray(int[] data, boolean immutable) {
		this.data = data;
		this.immutable = immutable;
	}

	@Override
	public IData add(IData other) {
		int[] otherData = other.asIntArray();
		int[] result = Arrays.copyOf(data, data.length + otherData.length);
		System.arraycopy(otherData, 0, result, data.length, otherData.length);
		return new DataIntArray(result, immutable);
	}

	@Override
	public IData sub(IData other) {
		throw new UnsupportedOperationException("Cannot subtract from an int array");
	}

	@Override
	public IData mul(IData other) {
		throw new UnsupportedOperationException("Cannot multiply with an arary");
	}

	@Override
	public IData div(IData other) {
		throw new UnsupportedOperationException("Cannot divide an array");
	}

	@Override
	public IData mod(IData other) {
		throw new UnsupportedOperationException("Cannot perform modulo on an array");
	}

	@Override
	public IData and(IData other) {
		throw new UnsupportedOperationException("Arrays don't support bitwise operations");
	}

	@Override
	public IData or(IData other) {
		throw new UnsupportedOperationException("Arrays don't support bitwise operations");
	}

	@Override
	public IData xor(IData other) {
		throw new UnsupportedOperationException("Arrays don't support bitwise operations");
	}

	@Override
	public IData neg() {
		throw new UnsupportedOperationException("Cannot negate arrays");
	}

	@Override
	public IData not() {
		throw new UnsupportedOperationException("Arrays don't support bitwise operations");
	}

	@Override
	public boolean asBool() {
		throw new UnsupportedOperationException("Cannot cast an array to bool");
	}

	@Override
	public byte asByte() {
		throw new UnsupportedOperationException("Cannot cast an array to byte");
	}

	@Override
	public short asShort() {
		throw new UnsupportedOperationException("Cannot cast an array to short");
	}

	@Override
	public int asInt() {
		throw new UnsupportedOperationException("Cannot cast an array to int");
	}

	@Override
	public long asLong() {
		throw new UnsupportedOperationException("Cannot cast an array to long");
	}

	@Override
	public float asFloat() {
		throw new UnsupportedOperationException("Cannot cast an array to float");
	}

	@Override
	public double asDouble() {
		throw new UnsupportedOperationException("Cannot cast an array to double");
	}

	@Override
	public String asString() {
		StringBuilder result = new StringBuilder();
		result.append('[');
		boolean first = true;
		for (int value : data) {
			if (first) {
				first = false;
			} else {
				result.append(", ");
			}
			result.append(value);
		}
		result.append(']');
		return result.toString();
	}

	@Override
	public List<IData> asList() {
		List<IData> result = new ArrayList<IData>();
		for (int value : data) {
			result.add(new DataInt(value));
		}
		return result;
	}

	@Override
	public Map<String, IData> asMap() {
		throw new UnsupportedOperationException("Cannot convert list to map");
	}

	@Override
	public byte[] asByteArray() {
		byte[] result = new byte[data.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = (byte) data[i];
		}
		return result;
	}

	@Override
	public int[] asIntArray() {
		return data;
	}

	@Override
	public IData getAt(int i) {
		return new DataInt(data[i]);
	}

	@Override
	public void setAt(int i, IData value) {
		if (immutable) {
			throw new UnsupportedOperationException("Cannot modify this byte array");
		} else {
			data[i] = value.asInt();
		}
	}

	@Override
	public IData memberGet(String name) {
		throw new UnsupportedOperationException("int[] doesn't have members");
	}

	@Override
	public void memberSet(String name, IData data) {
		throw new UnsupportedOperationException("cannot set int[] members");
	}

	@Override
	public int length() {
		return data.length;
	}

	@Override
	public boolean contains(IData data) {
		return equals(data);
	}

	@Override
	public int compareTo(IData data) {
		throw new UnsupportedOperationException("Cannot compare arrays");
	}

	@Override
	public boolean equals(IData data) {
		return Arrays.equals(this.data, data.asIntArray());
	}

	@Override
	public IData immutable() {
		if (immutable) {
			return this;
		} else {
			return new DataIntArray(Arrays.copyOf(this.data, this.data.length), true);
		}
	}

	@Override
	public IData update(IData data) {
		return data;
	}

	@Override
	public <T> T convert(IDataConverter<T> converter) {
		return converter.fromIntArray(this.data);
	}
}
