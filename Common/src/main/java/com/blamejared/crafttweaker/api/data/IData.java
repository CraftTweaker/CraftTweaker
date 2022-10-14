package com.blamejared.crafttweaker.api.data;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.visitor.DataToStringVisitor;
import com.blamejared.crafttweaker.api.data.visitor.DataToTextComponentVisitor;
import com.blamejared.crafttweaker.api.data.visitor.DataVisitor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.openzen.zencode.java.ZenCodeType;
import org.openzen.zenscript.codemodel.OperatorType;
import org.openzen.zenscript.codemodel.type.BasicTypeID;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;


/**
 * @docParam this (1 as IData)
 */
@ZenRegister(loaders = {CraftTweakerConstants.DEFAULT_LOADER_NAME, CraftTweakerConstants.TAGS_LOADER_NAME})
@ZenCodeType.Name("crafttweaker.api.data.IData")
@Document("vanilla/api/data/IData")
public interface IData extends Comparable<IData>, Iterable<IData> {
    
    /**
     * Creates a collection of the given IData members.
     *
     * <p>
     * This attempts to give the most accurate type for the given members, for example, if all the members are bytes, then it returns a ByteArrayData.
     *
     * However if the types are mixed or do not have a *ArrayData version, then a ListData is returned.
     * </p>
     *
     * @param members The members to put in the list.
     *
     * @return A list of the given members.
     *
     * @docParam members 1, 2, 3
     */
    @ZenCodeType.Method
    static IData listOf(IData... members) {
        
        if(members == null) {
            return new ListData();
        }
        
        int type = 0;
        final int byteIndex = 1;
        final int intIndex = 2;
        final int longIndex = 4;
        final int otherIndex = 8;
        
        for(IData member : members) {
            if(member instanceof ByteData) {
                type |= byteIndex;
            } else if(member instanceof IntData || member instanceof ShortData) {
                type |= intIndex;
            } else if(member instanceof LongData) {
                type |= longIndex;
            } else {
                type |= otherIndex;
            }
        }
        
        if((type & otherIndex) != 0) {
            return new ListData(members);
        } else if((type & longIndex) != 0) {
            long[] result = new long[members.length];
            for(int i = 0; i < members.length; i++) {
                result[i] = members[i].asLong();
            }
            return new LongArrayData(result);
        } else if((type & intIndex) != 0) {
            int[] result = new int[members.length];
            for(int i = 0; i < members.length; i++) {
                result[i] = members[i].asInt();
            }
            return new IntArrayData(result);
        } else if((type & byteIndex) != 0) {
            byte[] result = new byte[members.length];
            for(int i = 0; i < members.length; i++) {
                result[i] = members[i].asByte();
            }
            return new ByteArrayData(result);
        }
        
        return new ListData();
    }
    
    
    /**
     * Adds the given IData to this IData.
     *
     * @param other the other data to add.
     *
     * @return A new IData after adding the other data.
     *
     * @docParam other 2
     */
    @ZenCodeType.Operator(ZenCodeType.OperatorType.ADD)
    @ZenCodeType.Method
    default IData add(IData other) {
        
        return notSupportedOperator(OperatorType.ADD);
    }
    
    /**
     * Subtracts the given IData from this IData.
     *
     * @param other the other data to remove.
     *
     * @return A new IData after removing the other data.
     *
     * @docParam other 2
     */
    @ZenCodeType.Operator(ZenCodeType.OperatorType.SUB)
    default IData sub(IData other) {
        
        return notSupportedOperator(OperatorType.SUB);
    }
    
    /**
     * Multiplies the given IData to this IData.
     *
     * @param other the other data to multiply by.
     *
     * @return A new IData after multiplying the other data.
     *
     * @docParam other 2
     */
    @ZenCodeType.Operator(ZenCodeType.OperatorType.MUL)
    default IData mul(IData other) {
        
        return notSupportedOperator(OperatorType.MUL);
    }
    
    /**
     * Divides the given IData from this IData.
     *
     * @param other the other data to divide by.
     *
     * @return A new IData after dividing the other data.
     *
     * @docParam other 2
     */
    @ZenCodeType.Operator(ZenCodeType.OperatorType.DIV)
    default IData div(IData other) {
        
        return notSupportedOperator(OperatorType.DIV);
    }
    
    /**
     * Applies a modulo operation to this IData against the other IData.
     *
     * @param other the other data to modulo by.
     *
     * @return A new IData after applying a modulo operation.
     *
     * @docParam other 2
     */
    @ZenCodeType.Operator(ZenCodeType.OperatorType.MOD)
    default IData mod(IData other) {
        
        return notSupportedOperator(OperatorType.MOD);
    }
    
    /**
     * Concatenates the given IData to this IData.
     *
     * @param other the other data to concatenate.
     *
     * @return A new IData after concatenating the other data.
     *
     * @docParam other 2
     */
    @ZenCodeType.Operator(ZenCodeType.OperatorType.CAT)
    default IData cat(IData other) {
        
        return notSupportedOperator(OperatorType.CAT);
    }
    
    /**
     * Applies a bitwise OR (|) operation to this IData and the other IData
     *
     * @param other the other data.
     *
     * @return A new IData after applying a bitwise OR operation.
     *
     * @docParam other 2
     */
    @ZenCodeType.Operator(ZenCodeType.OperatorType.OR)
    default IData or(IData other) {
        
        return notSupportedOperator(OperatorType.OR);
    }
    
    /**
     * Applies a bitwise AND (&) operation to this IData and the other IData
     *
     * @param other the other data.
     *
     * @return A new IData after applying a bitwise AND operation.
     *
     * @docParam other 2
     */
    @ZenCodeType.Operator(ZenCodeType.OperatorType.AND)
    default IData and(IData other) {
        
        return notSupportedOperator(OperatorType.AND);
    }
    
    /**
     * Applies a bitwise XOR (^) operation to this IData and the other IData
     *
     * @param other the other data.
     *
     * @return A new IData after applying a bitwise XOR operation.
     *
     * @docParam other 2
     */
    @ZenCodeType.Operator(ZenCodeType.OperatorType.XOR)
    default IData xor(IData other) {
        
        return notSupportedOperator(OperatorType.XOR);
    }
    
    /**
     * Negates this IData.
     *
     * @return The negation of this IData.
     */
    @ZenCodeType.Operator(ZenCodeType.OperatorType.NEG)
    default IData neg() {
        
        return notSupportedOperator(OperatorType.NEG);
    }
    
    //TODO ZC bug thinks it is cat instead of invert
    //    @ZenCodeType.Operator(ZenCodeType.OperatorType.INVERT)
    //    default IData operatorInvert() {
    //
    //        return notSupportedOperator(OperatorType.INVERT);
    //    }
    
    /**
     * Applies a NOT (!) operation to this IData.
     *
     * @return The result of the NOT operation.
     *
     * @docParam this true
     */
    @ZenCodeType.Operator(ZenCodeType.OperatorType.NOT)
    default IData not() {
        
        return notSupportedOperator(OperatorType.NOT);
    }
    
    /**
     * Puts the given value inside this IData at the given index.
     *
     * @param index The key to store the data at
     * @param value The data to store.
     *
     * @docParam index "key"
     * @docParam value "value"
     * @docParam this new MapData()
     */
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.INDEXSET)
    default void put(String index, @ZenCodeType.Nullable IData value) {
        
        notSupportedOperator(OperatorType.INDEXSET);
    }
    
    /**
     * Gets the data at the given index.
     *
     * @param index The index to get
     *
     * @return The data at the index.
     *
     * @docParam index 0
     * @docParam this [1, 2, 3] as IData
     */
    @ZenCodeType.Operator(ZenCodeType.OperatorType.INDEXGET)
    default IData getAt(int index) {
        
        return notSupportedOperator(OperatorType.INDEXGET);
    }
    
    /**
     * Gets the data at the given key.
     *
     * @param key The key to get
     *
     * @return The data at the key.
     *
     * @docParam index "key"
     * @docParam this {key: "value"}
     */
    @ZenCodeType.Operator(ZenCodeType.OperatorType.INDEXGET)
    default IData getAt(String key) {
        
        return notSupportedOperator(OperatorType.INDEXGET);
    }
    
    /**
     * Checks if this IData contains the other IData
     *
     * <p>
     * For most data types, this will check equality of the data, but for map data, it will check if the other data is a string, and then check if it contains a key with that name
     * </p>
     *
     * @param other the other data to check
     *
     * @return true if this data contains the other data.
     */
    @ZenCodeType.Operator(ZenCodeType.OperatorType.CONTAINS)
    default boolean contains(IData other) {
        
        return notSupportedOperator(OperatorType.CONTAINS);
    }
    
    /**
     * Compares this IData to the other IData
     *
     * @param other the data to be compared.
     *
     * @return The comparison result.
     *
     * @docParam other 5
     */
    @Override
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.COMPARE)
    default int compareTo(@NotNull IData other) {
        
        return notSupportedOperator(OperatorType.COMPARE);
    }
    
    /**
     * Removes the stored data at the given index.
     *
     * @param index The index to remove.
     *
     * @docParam index 0
     * @docParam this [1, 2, 3] as IData
     */
    @ZenCodeType.Method
    default void remove(int index) {
        
        doesNot("support removal by index");
    }
    
    /**
     * Removes the stored data at the given key.
     *
     * @param key The key to remove.
     *
     * @docParam key "key"
     * @docParam this {key: "value"} as IData
     */
    @ZenCodeType.Method
    default void remove(String key) {
        
        doesNot("support removal by key");
    }
    
    /**
     * Sets the given value inside this IData at the given index.
     *
     * @param name The key to store the data at
     * @param data The data to store.
     *
     * @docParam index "key"
     * @docParam value "value"
     * @docParam this new MapData()
     */
    @ZenCodeType.Method
    default void setAt(String name, @ZenCodeType.Nullable IData data) {
        
        put(name, data);
    }
    
    /**
     * Checks if this IData is equal to the other IData.
     *
     * @param other The data to check equality of.
     *
     * @return True if equal, false otherwise.
     */
    @ZenCodeType.Operator(ZenCodeType.OperatorType.EQUALS)
    default boolean equalTo(IData other) {
        
        return notSupportedOperator(OperatorType.EQUALS);
    }
    
    /**
     * Applies a SHL (<<) operation to this data by the other data
     *
     * @param other The data to SHL by.
     *
     * @return The result of the SHL operation.
     *
     * @docParam other 2
     */
    @ZenCodeType.Operator(ZenCodeType.OperatorType.SHL)
    default IData shl(IData other) {
        
        return notSupportedOperator(OperatorType.SHL);
    }
    
    /**
     * Applies a SHR (>>) operation to this data by the other data
     *
     * @param other The data to SHR by.
     *
     * @return The result of the SHR operation.
     *
     * @docParam other 2
     */
    @ZenCodeType.Operator(ZenCodeType.OperatorType.SHR)
    default IData shr(IData other) {
        
        return notSupportedOperator(OperatorType.SHR);
    }
    
    /**
     * Casts this IData to a boolean.
     *
     * @return this data as a bool
     */
    @ZenCodeType.Caster
    @ZenCodeType.Method
    default boolean asBool() {
        
        return notSupportedCast(BasicTypeID.BOOL);
    }
    
    /**
     * Casts this IData to a byte.
     *
     * @return this data as a byte
     */
    @ZenCodeType.Caster
    @ZenCodeType.Method
    default @ZenCodeType.Unsigned byte asByte() {
        
        return notSupportedCast(BasicTypeID.BYTE);
    }
    
    /**
     * Casts this IData to a short.
     *
     * @return this data as a short
     */
    @ZenCodeType.Caster
    @ZenCodeType.Method
    default short asShort() {
        
        return notSupportedCast(BasicTypeID.SHORT);
    }
    
    /**
     * Casts this IData to an int.
     *
     * @return this data as an int
     */
    @ZenCodeType.Caster
    @ZenCodeType.Method
    default int asInt() {
        
        return notSupportedCast(BasicTypeID.INT);
    }
    
    /**
     * Casts this IData to a long.
     *
     * @return this data as a long
     */
    @ZenCodeType.Caster
    @ZenCodeType.Method
    default long asLong() {
        
        return notSupportedCast(BasicTypeID.LONG);
    }
    
    /**
     * Casts this IData to a float.
     *
     * @return this data as a float
     */
    @ZenCodeType.Caster
    @ZenCodeType.Method
    default float asFloat() {
        
        return notSupportedCast(BasicTypeID.FLOAT);
    }
    
    /**
     * Casts this IData to a double.
     *
     * @return this data as a double
     */
    @ZenCodeType.Caster
    @ZenCodeType.Method
    default double asDouble() {
        
        return notSupportedCast(BasicTypeID.DOUBLE);
    }
    
    /**
     * Gets an escaped string version of this IData, quotes are included in the output
     *
     * <p>E.G {@code println(("hello" as IData).asString())} prints {@code "hello"}</p>
     *
     * @return The escaped string version of this IData.
     */
    @ZenCodeType.Method
    default String asString() {
        
        return this.accept(DataToStringVisitor.ESCAPE);
    }
    
    /**
     * Gets the literal string version of this IData.
     *
     * <p>E.G {@code println(("hello" as IData).getAsString())} prints {@code hello}</p>
     *
     * @return The literal string version of this IData.
     */
    @ZenCodeType.Caster
    @ZenCodeType.Method
    default String getAsString() {
        
        return this.accept(DataToStringVisitor.PLAIN);
    }
    
    /**
     * Casts this IData to a list.
     *
     * @return this data as a list
     */
    @ZenCodeType.Caster
    @ZenCodeType.Method
    default List<IData> asList() {
        
        return notSupportedCast("IData[]");
    }
    
    /**
     * Casts this IData to a map.
     *
     * @return this data as a map
     */
    @ZenCodeType.Caster
    @ZenCodeType.Method
    default Map<String, IData> asMap() {
        
        return notSupportedCast("IData[string]");
    }
    
    /**
     * Casts this IData to a byte array.
     *
     * @return this data as a byte array
     */
    @ZenCodeType.Caster
    @ZenCodeType.Method
    default byte[] asByteArray() {
        //TODO this is actually sbyte[], but @Unsigned doesn't work on arrays right now
        return notSupportedCast("byte[]");
    }
    
    /**
     * Casts this IData to an int array.
     *
     * @return this data as an int array
     */
    @ZenCodeType.Caster
    @ZenCodeType.Method
    default int[] asIntArray() {
        
        return notSupportedCast("int[]");
    }
    
    /**
     * Casts this IData to a long array.
     *
     * @return this data as a long array
     */
    @ZenCodeType.Caster
    @ZenCodeType.Method
    default long[] asLongArray() {
        
        return notSupportedCast("long[]");
    }
    
    /**
     * Gets the length of this IData.
     *
     * @return The length of this IData.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("length")
    default int length() {
        
        return doesNot("have a length");
    }
    
    /**
     * Gets the keys of this IData
     *
     * @return The keys of this IData.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("keys")
    default Set<String> getKeys() {
        
        return doesNot("is not indexable by keys");
    }
    
    @NotNull
    @Override
    default Iterator<IData> iterator() {
        
        return doesNot("support iteration");
    }
    
    /**
     * Merges the given data with this data.
     *
     * @param other the data to merge
     *
     * @return the result of merging the datas.
     *
     * @docParam this {}
     */
    @ZenCodeType.Method
    default IData merge(IData other) {
        
        return doesNot("support merging");
    }
    
    /**
     * Checks if this data is empty.
     *
     * @return True if empty.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("isEmpty")
    default boolean isEmpty() {
        
        return length() == 0;
    }
    
    /**
     * Gets the internal ID of this data.
     *
     * @return the intenral ID of this data.
     */
    @ZenCodeType.Method
    default byte getId() {
        
        return getInternal().getId();
    }
    
    /**
     * Gets the internal Tag stored in this IData.
     *
     * @return the vanilla Tag that this IData represents.
     */
    Tag getInternal();
    
    @ZenCodeType.Method
    IData copy();
    
    IData copyInternal();
    
    <T> T accept(DataVisitor<T> visitor);
    
    /**
     * Maps this IData to another IData based on the given operation.
     *
     * @param operation The operation to apply to this IData
     *
     * @return A new IData from the operation
     *
     * @docParam operation (data) => 3
     */
    @ZenCodeType.Method
    default IData map(Function<IData, IData> operation) {
        
        return operation.apply(this);
    }
    
    /**
     * Gets the type of this IData.
     *
     * @return The type of this IData.
     */
    Type getType();
    
    private <T> T doesNot(String message) {
        
        throw new UnsupportedOperationException("Data type: '%s' does not %s!".formatted(getType(), message));
    }
    
    private <T> T notSupportedOperator(OperatorType operator) {
        
        return doesNot("support the '%s' ('%s') operator".formatted(operator.compiledName, operator.operator));
    }
    
    private <T> T notSupportedCast(String toType) {
        
        return doesNot("support being cast to '%s'".formatted(toType));
    }
    
    private <T> T notSupportedCast(BasicTypeID toType) {
        
        return notSupportedCast("support being cast to '%s'".formatted(toType.name));
    }
    
    /**
     * Checks if this data supports being cast to a list.
     *
     * @return True if it can be cast to a list, false otherwise.
     */
    @ApiStatus.Internal
    default boolean isListable() {
        
        return false;
    }
    
    /**
     * Checks if this data supports being cast to a map.
     *
     * @return True if it can be cast to a map, false otherwise.
     */
    @ApiStatus.Internal
    default boolean isMappable() {
        
        return false;
    }
    
    
    /**
     * Used to specify what "type" of IData this is.
     * <p>
     * This is to make it easier to have a Map with an IData based key.
     * <p>
     * See {@link DataToTextComponentVisitor#DATA_TO_COMPONENT}
     */
    enum Type {
        BOOL, BYTE_ARRAY, BYTE, DOUBLE, FLOAT, INT_ARRAY, INT, LIST, LONG_ARRAY, LONG, MAP, SHORT, STRING;
    }
    
}
