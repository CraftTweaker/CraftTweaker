package minetweaker.api.data;

import java.util.List;
import java.util.Map;
import stanhebben.zenscript.annotations.ZenCaster;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMemberGetter;
import stanhebben.zenscript.annotations.ZenMemberSetter;
import stanhebben.zenscript.annotations.ZenOperator;
import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Generic data interface. A data element may contain any kind of basic data
 * element (bool, byte, short, int, long, float, double, string, list, map, int
 * array or byte array). Used to store data with stacks, blocks, world, ...
 * 
 * @author Stan Hebben
 */
@ZenClass("minetweaker.data.IData")
public interface IData {
	@ZenOperator(OperatorType.ADD)
	public IData add(IData other);

	@ZenOperator(OperatorType.SUB)
	public IData sub(IData other);

	@ZenOperator(OperatorType.MUL)
	public IData mul(IData other);

	@ZenOperator(OperatorType.DIV)
	public IData div(IData other);

	@ZenOperator(OperatorType.MOD)
	public IData mod(IData other);

	@ZenOperator(OperatorType.AND)
	public IData and(IData other);

	@ZenOperator(OperatorType.OR)
	public IData or(IData other);

	@ZenOperator(OperatorType.XOR)
	public IData xor(IData other);

	@ZenOperator(OperatorType.NEG)
	public IData neg();

	@ZenOperator(OperatorType.NOT)
	public IData not();

	@ZenCaster
	public boolean asBool();

	@ZenCaster
	public byte asByte();

	@ZenCaster
	public short asShort();

	@ZenCaster
	public int asInt();

	@ZenCaster
	public long asLong();

	@ZenCaster
	public float asFloat();

	@ZenCaster
	public double asDouble();

	@ZenCaster
	public String asString();

	/**
	 * Attempts to convert this value to a List. Returns null if this value
	 * cannot be converted to a list.
	 * 
	 * @return list data of this value, if any
	 */
	@ZenCaster
	public List<IData> asList();

	/**
	 * Attempts to convert this value to a Map. Returns null if this value
	 * cannot be converted to a map.
	 * 
	 * @return map data of this value, if any
	 */
	@ZenCaster
	public Map<String, IData> asMap();

	/**
	 * Attempts to convert this value to a byte array. Returns null if this
	 * value cannot be converted to a byte array.
	 * 
	 * @return byte array data of this value, if any
	 */
	@ZenCaster
	public byte[] asByteArray();

	/**
	 * Attempts to convert this value to an int array. Returns null if this
	 * value cannot be converted to an int array.
	 * 
	 * @return int array data of this value, if any
	 */
	@ZenCaster
	public int[] asIntArray();

	@ZenOperator(OperatorType.INDEXGET)
	public IData getAt(int i);

	@ZenOperator(OperatorType.INDEXSET)
	public void setAt(int i, IData value);

	@ZenMemberGetter
	public IData memberGet(String name);

	@ZenMemberSetter
	public void memberSet(String name, IData data);

	@ZenGetter
	public int length();

	@ZenOperator(OperatorType.CONTAINS)
	public boolean contains(IData data);

	@ZenOperator(OperatorType.COMPARE)
	public int compareTo(IData data);

	@ZenOperator(OperatorType.EQUALS)
	public boolean equals(IData data);

	@ZenGetter
	public IData immutable();

	@ZenMethod
	public IData update(IData data);

	public <T> T convert(IDataConverter<T> converter);

	@Override
	public String toString();
}
