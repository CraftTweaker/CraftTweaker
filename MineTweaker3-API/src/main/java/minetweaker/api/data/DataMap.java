package minetweaker.api.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains a map, mapping strings to data elements.
 * 
 * @author Stan Hebben
 */
public class DataMap implements IData {
	public static final DataMap EMPTY = new DataMap(new HashMap<String, IData>(), true);

	private final Map<String, IData> data;
	private final boolean immutable;

	public DataMap(Map<String, IData> data, boolean immutable) {
		this.data = data;
		this.immutable = immutable;
	}

	@Override
	public IData add(IData other) {
		Map<String, IData> result = new HashMap<String, IData>();
		Map<String, IData> otherMap = other.asMap();

		for (Map.Entry<String, IData> entry : data.entrySet()) {
			result.put(entry.getKey(), entry.getValue());
		}
		for (Map.Entry<String, IData> entry : otherMap.entrySet()) {
			result.put(entry.getKey(), entry.getValue());
		}

		return new DataMap(result, immutable);
	}

	@Override
	public IData sub(IData other) {
		Map<String, IData> result = new HashMap<String, IData>();
		Map<String, IData> otherMap = other.asMap();

		for (Map.Entry<String, IData> entry : data.entrySet()) {
			result.put(entry.getKey(), entry.getValue());
		}
		for (String key : otherMap.keySet()) {
			result.remove(key);
		}

		return new DataMap(result, immutable);
	}

	@Override
	public IData mul(IData other) {
		throw new UnsupportedOperationException("Cannot multiply maps");
	}

	@Override
	public IData div(IData other) {
		throw new UnsupportedOperationException("Cannot divide maps");
	}

	@Override
	public IData mod(IData other) {
		throw new UnsupportedOperationException("Cannot perform modulo with maps");
	}

	@Override
	public IData and(IData other) {
		throw new UnsupportedOperationException("Maps do not support bitwise operations");
	}

	@Override
	public IData or(IData other) {
		throw new UnsupportedOperationException("Maps do not support bitwise operations");
	}

	@Override
	public IData xor(IData other) {
		throw new UnsupportedOperationException("Maps do not support bitwise operations");
	}

	@Override
	public IData neg() {
		throw new UnsupportedOperationException("Cannot negate maps.");
	}

	@Override
	public IData not() {
		throw new UnsupportedOperationException("Maps do not support bitwise operations");
	}

	@Override
	public boolean asBool() {
		throw new UnsupportedOperationException("Cannot cast map to bool");
	}

	@Override
	public byte asByte() {
		throw new UnsupportedOperationException("Cannot cast map to byte");
	}

	@Override
	public short asShort() {
		throw new UnsupportedOperationException("Cannot cast map to short");
	}

	@Override
	public int asInt() {
		throw new UnsupportedOperationException("Cannot cast map to int");
	}

	@Override
	public long asLong() {
		throw new UnsupportedOperationException("Cannot cast map to long");
	}

	@Override
	public float asFloat() {
		throw new UnsupportedOperationException("Cannot cast map to float");
	}

	@Override
	public double asDouble() {
		throw new UnsupportedOperationException("Cannot cast map to double");
	}

	@Override
	public String asString() {
		StringBuilder result = new StringBuilder();
		result.append('{');
		boolean first = true;
		for (Map.Entry<String, IData> entry : data.entrySet()) {
			if (first) {
				first = false;
			} else {
				result.append(", ");
			}

			if (isValidIdentifier(entry.getKey())) {
				result.append(entry.getKey());
			} else {
				result.append("\"").append(entry.getKey()).append("\"");
			}

			result.append(": ");
			result.append(entry.getValue().toString());
		}
		result.append('}');
		return result.toString();
	}

	@Override
	public List<IData> asList() {
		return null;
	}

	@Override
	public Map<String, IData> asMap() {
		if (immutable) {
			return Collections.unmodifiableMap(data);
		} else {
			return data;
		}
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
		throw new UnsupportedOperationException("Cannot index maps with integers");
	}

	@Override
	public void setAt(int i, IData value) {
		throw new UnsupportedOperationException("Cannot index maps with integers");
	}

	@Override
	public IData memberGet(String name) {
		return data.get(name);
	}

	@Override
	public void memberSet(String name, IData data) {
		if (immutable) {
			throw new UnsupportedOperationException("this map is not modifiable");
		} else {
			this.data.put(name, data);
		}
	}

	@Override
	public int length() {
		return data.size();
	}

	@Override
	public boolean contains(IData data) {
		if (data instanceof DataString) {
			return this.data.containsKey(data.asString());
		}

		Map<String, IData> dataMap = data.asMap();
		if (dataMap == null)
			return false;

		for (Map.Entry<String, IData> dataEntry : dataMap.entrySet()) {
			if (!this.data.containsKey(dataEntry.getKey())) {
				return false;
			} else if (!this.data.get(dataEntry.getKey()).contains(dataEntry.getValue())) {
				return false;
			}
		}

		return true;
	}

	@Override
	public int compareTo(IData data) {
		throw new UnsupportedOperationException("Cannot compare maps");
	}

	@Override
	public boolean equals(IData data) {
		if (this == data)
			return true;

		Map<String, IData> dataMap = data.asMap();
		if (dataMap.size() != this.data.size())
			return false;

		for (Map.Entry<String, IData> dataEntry : this.data.entrySet()) {
			if (!dataMap.containsKey(dataEntry.getKey())) {
				return false;
			} else if (!dataMap.get(dataEntry.getKey()).equals(dataEntry.getValue())) {
				return false;
			}
		}

		return true;
	}

	@Override
	public IData immutable() {
		if (immutable) {
			return this;
		} else {
			Map<String, IData> result = new HashMap<String, IData>();
			for (Map.Entry<String, IData> entry : this.data.entrySet()) {
				result.put(entry.getKey(), entry.getValue().immutable());
			}
			return new DataMap(result, true);
		}
	}

	@Override
	public IData update(IData data) {
		if (immutable)
			data = data.immutable();

		Map<String, IData> result = new HashMap<String, IData>();
		for (Map.Entry<String, IData> entry : this.data.entrySet()) {
			result.put(entry.getKey(), entry.getValue());
		}
		for (Map.Entry<String, IData> entry : data.asMap().entrySet()) {
			result.put(entry.getKey(), entry.getValue());
		}
		return new DataMap(result, immutable);
	}

	@Override
	public <T> T convert(IDataConverter<T> converter) {
		return converter.fromMap(data);
	}

	@Override
	public String toString() {
		return asString();
	}

	private boolean isValidIdentifier(String str) {
		if (!Character.isJavaIdentifierStart(str.charAt(0)))
			return false;

		for (int i = 1; i < str.length(); i++) {
			if (!Character.isJavaIdentifierPart(str.charAt(i)))
				return false;
		}

		return true;
	}
}
