package minetweaker.api.data;

import java.util.List;
import java.util.Map;

/**
 * Contains a boolean value (true or false).
 * 
 * @author Stan Hebben
 */
public class DataBool implements IData {
	private final boolean value;

	public DataBool(boolean value) {
		this.value = value;
	}

	@Override
	public boolean asBool() {
		return value;
	}

	@Override
	public byte asByte() {
		return (byte) (value ? 1 : 0);
	}

	@Override
	public short asShort() {
		return (short) (value ? 1 : 0);
	}

	@Override
	public int asInt() {
		return value ? 1 : 0;
	}

	@Override
	public long asLong() {
		return value ? 1 : 0;
	}

	@Override
	public float asFloat() {
		return value ? 1 : 0;
	}

	@Override
	public double asDouble() {
		return value ? 1 : 0;
	}

	@Override
	public String asString() {
		return Boolean.toString(value);
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
		throw new UnsupportedOperationException("a bool is not indexable");
	}

	@Override
	public void setAt(int i, IData value) {
		throw new UnsupportedOperationException("a bool is not indexable");
	}

	@Override
	public IData memberGet(String name) {
		throw new UnsupportedOperationException("a bool is not indexable");
	}

	@Override
	public void memberSet(String name, IData data) {
		throw new UnsupportedOperationException("a bool is not indexable");
	}

	@Override
	public int length() {
		return 0;
	}

	@Override
	public boolean contains(IData data) {
		return data.asBool() == value;
	}

	@Override
	public boolean equals(IData other) {
		return value == other.asBool();
	}

	@Override
	public int compareTo(IData other) {
		throw new UnsupportedOperationException("Cannot compare bool values");
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
		return converter.fromBool(value);
	}

	@Override
	public IData add(IData other) {
		throw new UnsupportedOperationException("Cannot add bool values");
	}

	@Override
	public IData sub(IData other) {
		throw new UnsupportedOperationException("Cannot subtract bool values");
	}

	@Override
	public IData mul(IData other) {
		throw new UnsupportedOperationException("Cannot multiply bool values");
	}

	@Override
	public IData div(IData other) {
		throw new UnsupportedOperationException("Cannot divide bool values");
	}

	@Override
	public IData mod(IData other) {
		throw new UnsupportedOperationException("Cannot perform modulo on bool values");
	}

	@Override
	public IData and(IData other) {
		return new DataBool(value & other.asBool());
	}

	@Override
	public IData or(IData other) {
		return new DataBool(value | other.asBool());
	}

	@Override
	public IData xor(IData other) {
		return new DataBool(value ^ other.asBool());
	}

	@Override
	public IData neg() {
		throw new UnsupportedOperationException("Cannot negate a bool value");
	}

	@Override
	public IData not() {
		return new DataBool(!value);
	}

	@Override
	public String toString() {
		return asString();
	}
}
