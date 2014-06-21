/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.data;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Stan
 */
public class DataByte implements IData {
	private final byte value;
	
	public DataByte(byte value) {
		this.value = value;
	}

	@Override
	public boolean asBool() {
		throw new RuntimeException("Cannot cast a byte to a bool");
	}

	@Override
	public byte asByte() {
		return value;
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
		return Byte.toString(value);
	}
	
	@Override
	public List<IData> asList() {
		throw new UnsupportedOperationException("Cannot convert a byte to a list");
	}
	
	@Override
	public Map<String, IData> asMap() {
		throw new UnsupportedOperationException("Cannot convert a byte to a map");
	}

	@Override
	public byte[] asByteArray() {
		throw new RuntimeException("Cannot cast a byte to a byte array");
	}

	@Override
	public int[] asIntArray() {
		throw new RuntimeException("Cannot cast a byte to an int array");
	}

	@Override
	public IData getAt(int i) {
		throw new RuntimeException("A byte is not indexable");
	}

	@Override
	public void setAt(int i, IData value) {
		throw new RuntimeException("A byte is not indexable");
	}

	@Override
	public IData memberGet(String name) {
		throw new RuntimeException("A byte is not indexable");
	}

	@Override
	public void memberSet(String name, IData data) {
		throw new RuntimeException("A byte is not indexable");
	}

	@Override
	public int length() {
		return 0;
	}

	@Override
	public boolean contains(IData data) {
		return data.asByte() == value;
	}
	
	@Override
	public boolean equals(IData data) {
		return value == data.asByte();
	}
	
	@Override
	public int compareTo(IData data) {
		return Byte.compare(value, data.asByte());
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
		return converter.fromByte(value);
	}

	@Override
	public IData add(IData other) {
		return new DataByte((byte) (value + other.asByte()));
	}

	@Override
	public IData sub(IData other) {
		return new DataByte((byte) (value - other.asByte()));
	}

	@Override
	public IData mul(IData other) {
		return new DataByte((byte) (value * other.asByte()));
	}

	@Override
	public IData div(IData other) {
		return new DataByte((byte) (value / other.asByte()));
	}

	@Override
	public IData mod(IData other) {
		return new DataByte((byte) (value % other.asByte()));
	}

	@Override
	public IData and(IData other) {
		return new DataByte((byte) (value & other.asByte()));
	}

	@Override
	public IData or(IData other) {
		return new DataByte((byte) (value | other.asByte()));
	}

	@Override
	public IData xor(IData other) {
		return new DataByte((byte) (value ^ other.asByte()));
	}

	@Override
	public IData neg() {
		return new DataByte((byte) (- value));
	}

	@Override
	public IData not() {
		return new DataByte((byte) (~ value));
	}
}
