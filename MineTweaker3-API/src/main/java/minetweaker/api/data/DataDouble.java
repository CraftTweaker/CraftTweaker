package minetweaker.api.data;

import java.util.List;
import java.util.Map;

/**
 * Contains a double-precision value.
 * 
 * @author Stan Hebben
 */
public class DataDouble implements IData {
	private final double value;

	public DataDouble(double value) {
		this.value = value;
	}

	@Override
	public boolean asBool() {
		throw new IllegalDataException("Cannot cast a double to a bool");
	}

	@Override
	public byte asByte() {
		return (byte) value;
	}

	@Override
	public short asShort() {
		return (short) value;
	}

	@Override
	public int asInt() {
		return (int) value;
	}

	@Override
	public long asLong() {
		return (long) value;
	}

	@Override
	public float asFloat() {
		return (float) value;
	}

	@Override
	public double asDouble() {
		return value;
	}

	@Override
	public String asString() {
		return Double.toString(value);
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
		throw new UnsupportedOperationException("A double is not indexable");
	}

	@Override
	public void setAt(int i, IData value) {
		throw new UnsupportedOperationException("A double is not indexable");
	}

	@Override
	public IData memberGet(String name) {
		throw new UnsupportedOperationException("A double is not indexable");
	}

	@Override
	public void memberSet(String name, IData data) {
		throw new UnsupportedOperationException("A double is not indexable");
	}

	@Override
	public int length() {
		return 0;
	}

	@Override
	public boolean contains(IData data) {
		return data.asDouble() == value;
	}

	@Override
	public boolean equals(IData other) {
		return value == other.asDouble();
	}

	@Override
	public int compareTo(IData other) {
		return Double.compare(value, other.asDouble());
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
		return converter.fromDouble(value);
	}

	@Override
	public IData add(IData other) {
		return new DataDouble(value + other.asDouble());
	}

	@Override
	public IData sub(IData other) {
		return new DataDouble(value - other.asDouble());
	}

	@Override
	public IData mul(IData other) {
		return new DataDouble(value * other.asDouble());
	}

	@Override
	public IData div(IData other) {
		return new DataDouble(value / other.asDouble());
	}

	@Override
	public IData mod(IData other) {
		return new DataDouble(value % other.asDouble());
	}

	@Override
	public IData and(IData other) {
		throw new UnsupportedOperationException("Cannot perform bitwise operations on double");
	}

	@Override
	public IData or(IData other) {
		throw new UnsupportedOperationException("Cannot perform bitwise operations on double");
	}

	@Override
	public IData xor(IData other) {
		throw new UnsupportedOperationException("Cannot perform bitwise operations on double");
	}

	@Override
	public IData neg() {
		return new DataDouble(-value);
	}

	@Override
	public IData not() {
		throw new UnsupportedOperationException("Cannot perform bitwise operations on double");
	}

	@Override
	public String toString() {
		return asString();
	}
}
