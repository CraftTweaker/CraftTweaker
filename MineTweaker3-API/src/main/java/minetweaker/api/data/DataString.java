package minetweaker.api.data;

import java.util.List;
import java.util.Map;

/**
 * Contains a string value.
 * 
 * @author Stan Hebben
 */
public class DataString implements IData {
	private final String value;

	public DataString(String value) {
		this.value = value;
	}

	@Override
	public boolean asBool() {
		return Boolean.parseBoolean(value);
	}

	@Override
	public byte asByte() {
		return Byte.parseByte(value);
	}

	@Override
	public short asShort() {
		return Short.parseShort(value);
	}

	@Override
	public int asInt() {
		return Integer.parseInt(value);
	}

	@Override
	public long asLong() {
		return Long.parseLong(value);
	}

	@Override
	public float asFloat() {
		return Float.parseFloat(value);
	}

	@Override
	public double asDouble() {
		return Double.parseDouble(value);
	}

	@Override
	public String asString() {
		return value;
	}

	@Override
	public List<IData> asList() {
		return null;
	}

	@Override
	public Map<String, IData> asMap() {
		return null;
	}

	@Override
	public byte[] asByteArray() {
		return null;
	}

	@Override
	public int[] asIntArray() {
		return null;
	}

	@Override
	public IData getAt(int i) {
		return new DataString(value.substring(i, i + 1));
	}

	@Override
	public void setAt(int i, IData value) {
		throw new UnsupportedOperationException("Strings are immutable");
	}

	@Override
	public IData memberGet(String name) {
		if (name.equals("length")) {
			return new DataInt(value.length());
		} else {
			throw new UnsupportedOperationException("no such member: " + name);
		}
	}

	@Override
	public void memberSet(String name, IData data) {
		throw new UnsupportedOperationException("Strings are immutable");
	}

	@Override
	public int length() {
		return value.length();
	}

	@Override
	public boolean contains(IData data) {
		return data.asString().equals(value);
	}

	@Override
	public boolean equals(IData data) {
		return value.equals(data.asString());
	}

	@Override
	public int compareTo(IData data) {
		return value.compareTo(data.asString());
	}

	@Override
	public IData immutable() {
		return this;
	}

	@Override
	public IData update(IData data) {
		return data;
	}

	@Override
	public <T> T convert(IDataConverter<T> converter) {
		return converter.fromString(value);
	}

	@Override
	public IData add(IData other) {
		return new DataString(value + other.asString());
	}

	@Override
	public IData sub(IData other) {
		throw new UnsupportedOperationException("Cannot subtract from a string");
	}

	@Override
	public IData mul(IData other) {
		throw new UnsupportedOperationException("Cannot multiply a string");
	}

	@Override
	public IData div(IData other) {
		throw new UnsupportedOperationException("Cannot divide a string");
	}

	@Override
	public IData mod(IData other) {
		throw new UnsupportedOperationException("Cannot perform modulo on a string");
	}

	@Override
	public IData and(IData other) {
		throw new UnsupportedOperationException("Cannot perform bitwise arithmetic on a string");
	}

	@Override
	public IData or(IData other) {
		throw new UnsupportedOperationException("Cannot perform bitwise arithmetic on a string");
	}

	@Override
	public IData xor(IData other) {
		throw new UnsupportedOperationException("Cannot perform bitwise arithmetic on a string");
	}

	@Override
	public IData neg() {
		throw new UnsupportedOperationException("Cannot negate a string");
	}

	@Override
	public IData not() {
		throw new UnsupportedOperationException("Cannot perform bitwise arithmetic on a string");
	}

	@Override
	public String toString() {
		return '\"' + value.replace("\"", "\\\"") + "\"";
	}
}
