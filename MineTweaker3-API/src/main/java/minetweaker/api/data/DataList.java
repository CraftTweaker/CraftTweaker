package minetweaker.api.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Contains a list.
 * 
 * @author Stan Hebben
 */
public class DataList implements IData {
	private final List<IData> values;
	private final boolean immutable;
	
	public DataList(List<IData> values, boolean immutable) {
		this.values = values;
		this.immutable = immutable;
	}

	@Override
	public boolean asBool() {
		throw new RuntimeException("Cannot convert a list to a bool");
	}

	@Override
	public byte asByte() {
		throw new RuntimeException("Cannot convert a list to a byte");
	}

	@Override
	public short asShort() {
		throw new RuntimeException("Cannot convert a list to a short");
	}

	@Override
	public int asInt() {
		throw new RuntimeException("Cannot convert a list to an int");
	}

	@Override
	public long asLong() {
		throw new RuntimeException("Cannot convert a list to a long");
	}

	@Override
	public float asFloat() {
		throw new RuntimeException("Cannot convert a list to a float");
	}

	@Override
	public double asDouble() {
		throw new RuntimeException("Cannot convert a list to a double");
	}
	
	@Override
	public List<IData> asList() {
		if (immutable) {
			return Collections.unmodifiableList(values);
		} else {
			return values;
		}
	}
	
	@Override
	public Map<String, IData> asMap() {
		throw new RuntimeException("Cannot convert a list to a map");
	}

	@Override
	public String asString() {
		StringBuilder output = new StringBuilder();
		output.append('[');
		boolean first = true;
		for (IData value : values) {
			if (first) {
				first = false;
			} else {
				output.append(", ");
			}
			output.append(value.toString());
		}
		output.append(']');
		return output.toString();
	}

	@Override
	public byte[] asByteArray() {
		byte[] result = new byte[values.size()];
		for (int i = 0; i < values.size(); i++) {
			result[i] = values.get(i).asByte();
		}
		return result;
	}

	@Override
	public int[] asIntArray() {
		int[] result = new int[values.size()];
		for (int i = 0; i < values.size(); i++) {
			result[i] = values.get(i).asInt();
		}
		return result;
	}

	@Override
	public IData getAt(int i) {
		return values.get(i);
	}

	@Override
	public void setAt(int i, IData value) {
		if (immutable) {
			throw new RuntimeException("this list is immutable");
		} else {
			values.set(i, value);
		}
	}

	@Override
	public IData memberGet(String name) {
		throw new RuntimeException("Lists don't have members");
	}

	@Override
	public void memberSet(String name, IData data) {
		throw new RuntimeException("Lists don't have members");
	}

	@Override
	public int length() {
		return values.size();
	}

	@Override
	public boolean contains(IData data) {
		for (IData value : values) {
			if (value.equals(data)) return true;
		}
		
		return false;
	}
	
	@Override
	public boolean equals(IData data) {
		List<IData> otherValues = data.asList();
		if (otherValues.size() != values.size()) return false;
		
		for (int i = 0; i < values.size(); i++) {
			if (!values.get(i).equals(otherValues.get(i))) return false;
		}
		
		return true;
	}
	
	@Override
	public int compareTo(IData data) {
		throw new UnsupportedOperationException("Cannot compare with a list");
	}

	@Override
	public IData immutable() {
		if (immutable) {
			return this;
		} else {
			List<IData> copy = new ArrayList<IData>();
			for (IData value : values) {
				copy.add(value.immutable());
			}
			return new DataList(copy, true);
		}
	}

	@Override
	public IData update(IData data) {
		if (immutable) data = data.immutable();
		
		List<IData> result = new ArrayList<IData>();
		result.addAll(values);
		result.addAll(data.asList());
		return new DataList(result, immutable);
	}

	@Override
	public <T> T convert(IDataConverter<T> converter) {
		return converter.fromList(values);
	}

	@Override
	public IData add(IData other) {
		return update(other);
	}

	@Override
	public IData sub(IData other) {
		throw new UnsupportedOperationException("Cannot subtract from a list");
	}

	@Override
	public IData mul(IData other) {
		throw new UnsupportedOperationException("Cannot multiply a list");
	}

	@Override
	public IData div(IData other) {
		throw new UnsupportedOperationException("Cannot divide a list");
	}

	@Override
	public IData mod(IData other) {
		throw new UnsupportedOperationException("Cannot perform modulo on a list");
	}

	@Override
	public IData and(IData other) {
		throw new UnsupportedOperationException("Lists don't support bitwise operators");
	}

	@Override
	public IData or(IData other) {
		throw new UnsupportedOperationException("Lists don't support bitwise operators");
	}

	@Override
	public IData xor(IData other) {
		throw new UnsupportedOperationException("Lists don't support bitwise operators");
	}

	@Override
	public IData neg() {
		throw new UnsupportedOperationException("Cannot negate a list");
	}

	@Override
	public IData not() {
		throw new UnsupportedOperationException("Lists don't support bitwise operators");
	}
	
	@Override
	public String toString() {
		return asString();
	}
}
