package minetweaker.api.data;

import stanhebben.zenscript.annotations.*;

import java.util.*;

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
    IData add(IData other);

    @ZenOperator(OperatorType.SUB)
    IData sub(IData other);

    @ZenOperator(OperatorType.MUL)
    IData mul(IData other);

    @ZenOperator(OperatorType.DIV)
    IData div(IData other);

    @ZenOperator(OperatorType.MOD)
    IData mod(IData other);

    @ZenOperator(OperatorType.AND)
    IData and(IData other);

    @ZenOperator(OperatorType.OR)
    IData or(IData other);

    @ZenOperator(OperatorType.XOR)
    IData xor(IData other);

    @ZenOperator(OperatorType.NEG)
    IData neg();

    @ZenOperator(OperatorType.NOT)
    IData not();

    @ZenCaster
    boolean asBool();

    @ZenCaster
    byte asByte();

    @ZenCaster
    short asShort();

    @ZenCaster
    int asInt();

    @ZenCaster
    long asLong();

    @ZenCaster
    float asFloat();

    @ZenCaster
    double asDouble();

    @ZenCaster
    String asString();

    /**
     * Attempts to convert this value to a List. Returns null if this value
     * cannot be converted to a list.
     *
     * @return list data of this value, if any
     */
    @ZenCaster
    List<IData> asList();

    /**
     * Attempts to convert this value to a Map. Returns null if this value
     * cannot be converted to a map.
     *
     * @return map data of this value, if any
     */
    @ZenCaster
    Map<String, IData> asMap();

    /**
     * Attempts to convert this value to a byte array. Returns null if this
     * value cannot be converted to a byte array.
     *
     * @return byte array data of this value, if any
     */
    @ZenCaster
    byte[] asByteArray();

    /**
     * Attempts to convert this value to an int array. Returns null if this
     * value cannot be converted to an int array.
     *
     * @return int array data of this value, if any
     */
    @ZenCaster
    int[] asIntArray();

    @ZenOperator(OperatorType.INDEXGET)
    IData getAt(int i);

    @ZenOperator(OperatorType.INDEXSET)
    void setAt(int i, IData value);

    @ZenMemberGetter
    IData memberGet(String name);

    @ZenMemberSetter
    void memberSet(String name, IData data);

    @ZenGetter
    int length();

    @ZenOperator(OperatorType.CONTAINS)
    boolean contains(IData data);

    @ZenOperator(OperatorType.COMPARE)
    int compareTo(IData data);

    @ZenOperator(OperatorType.EQUALS)
    boolean equals(IData data);

    @ZenGetter
    IData immutable();

    @ZenMethod
    IData update(IData data);

    <T> T convert(IDataConverter<T> converter);

    @Override
    String toString();
}
