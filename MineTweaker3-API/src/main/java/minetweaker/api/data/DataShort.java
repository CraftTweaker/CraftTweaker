package minetweaker.api.data;

import java.util.List;
import java.util.Map;

/**
 * Contains a 16-bit short value.
 * 
 * @author Stan Hebben
 */
public class DataShort implements IData {
	private final short value;

	public DataShort(short value) {
		this.value = value;
	}

	@Override
	public boolean asBool() {
		throw new IllegalDataException("Cannot cast a short to a bool");
	}

	@Override
	public byte asByte() {
		return (byte) value;
	}

	@Override
	public short asShort() {
		return value;
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
		return Short.toString(value);
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
		throw new UnsupportedOperationException("A short is not indexable");
	}

	@Override
	public void setAt(int i, IData value) {
		throw new UnsupportedOperationException("A short is not indexable");
	}

	@Override
	public IData memberGet(String name) {
		throw new UnsupportedOperationException("A short is not indexable");
	}

	@Override
	public void memberSet(String name, IData data) {
		throw new UnsupportedOperationException("A short is not indexable");
	}

	@Override
	public int length() {
		return 0;
	}

	@Override
	public boolean contains(IData data) {
		return data.asShort() == value;
	}

	@Override
	public boolean equals(IData data) {
		return value == data.asShort();
	}

	@Override
	public int compareTo(IData data) {
		return Short.compare(value, data.asShort());
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
		return converter.fromShort(value);
	}

	@Override
	public IData add(IData other) {
		return new DataShort((short) (value + other.asShort()));
	}

	@Override
	public IData sub(IData other) {
		return new DataShort((short) (value - other.asShort()));
	}

	@Override
	public IData mul(IData other) {
		return new DataShort((short) (value * other.asShort()));
	}

	@Override
	public IData div(IData other) {
		return new DataShort((short) (value / other.asShort()));
	}

	@Override
	public IData mod(IData other) {
		return new DataShort((short) (value % other.asShort()));
	}

	@Override
	public IData and(IData other) {
		return new DataShort((short) (value & other.asShort()));
	}

	@Override
	public IData or(IData other) {
		return new DataShort((short) (value | other.asShort()));
	}

	@Override
	public IData xor(IData other) {
		return new DataShort((short) (value ^ other.asShort()));
	}

	@Override
	public IData neg() {
		return new DataShort((short) (-value));
	}

	@Override
	public IData not() {
		return new DataShort((short) (~value));
	}

	@Override
	public String toString() {
		return asString() + " as short";
	}
}
