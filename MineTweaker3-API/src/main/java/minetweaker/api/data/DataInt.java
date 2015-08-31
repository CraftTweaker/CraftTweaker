package minetweaker.api.data;

import java.util.List;
import java.util.Map;

/**
 * Contains a 32-bit integer value.
 * 
 * @author Stan Hebben
 */
public class DataInt implements IData {
	private final int value;

	public DataInt(int value) {
		this.value = value;
	}

	@Override
	public boolean asBool() {
		throw new RuntimeException("Cannot cast an int to a bool");
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
		return value;
	}

	@Override
	public long asLong() {
		return value;
	}

	@Override
	public float asFloat() {
		return value;
	}

	@Override
	public double asDouble() {
		return value;
	}

	@Override
	public String asString() {
		return Integer.toString(value);
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
		throw new RuntimeException("An int is not indexable");
	}

	@Override
	public void setAt(int i, IData value) {
		throw new RuntimeException("An int is not indexable");
	}

	@Override
	public IData memberGet(String name) {
		throw new RuntimeException("An int is not indexable");
	}

	@Override
	public void memberSet(String name, IData data) {
		throw new RuntimeException("An int is not indexable");
	}

	@Override
	public int length() {
		return 0;
	}

	@Override
	public boolean contains(IData data) {
		return data.asInt() == value;
	}

	@Override
	public boolean equals(IData data) {
		return value == data.asInt();
	}

	@Override
	public int compareTo(IData data) {
		return Integer.compare(value, data.asInt());
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
		return converter.fromInt(value);
	}

	@Override
	public IData add(IData other) {
		return new DataInt(value + other.asInt());
	}

	@Override
	public IData sub(IData other) {
		return new DataInt(value - other.asInt());
	}

	@Override
	public IData mul(IData other) {
		return new DataInt(value * other.asInt());
	}

	@Override
	public IData div(IData other) {
		return new DataInt(value / other.asInt());
	}

	@Override
	public IData mod(IData other) {
		return new DataInt(value % other.asInt());
	}

	@Override
	public IData and(IData other) {
		return new DataInt(value & other.asInt());
	}

	@Override
	public IData or(IData other) {
		return new DataInt(value | other.asInt());
	}

	@Override
	public IData xor(IData other) {
		return new DataInt(value ^ other.asInt());
	}

	@Override
	public IData neg() {
		return new DataInt(-value);
	}

	@Override
	public IData not() {
		return new DataInt(~value);
	}

	@Override
	public String toString() {
		return asString();
	}
}
