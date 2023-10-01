package com.blamejared.crafttweaker.api.data.op;

import com.blamejared.crafttweaker.api.data.BoolData;
import com.blamejared.crafttweaker.api.data.ByteArrayData;
import com.blamejared.crafttweaker.api.data.ByteData;
import com.blamejared.crafttweaker.api.data.DoubleData;
import com.blamejared.crafttweaker.api.data.EmptyData;
import com.blamejared.crafttweaker.api.data.FloatData;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.IntArrayData;
import com.blamejared.crafttweaker.api.data.IntData;
import com.blamejared.crafttweaker.api.data.ListData;
import com.blamejared.crafttweaker.api.data.LongArrayData;
import com.blamejared.crafttweaker.api.data.LongData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.ShortData;
import com.blamejared.crafttweaker.api.data.StringData;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.ListBuilder;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import net.minecraft.Optionull;
import net.minecraft.nbt.Tag;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public final class IDataOps implements DynamicOps<IData> {
    public static final IDataOps INSTANCE = new IDataOps();
    
    private IDataOps() {}
    
    @Override
    public IData empty() {
        
        return EmptyData.INSTANCE;
    }
    
    @Override
    public <U> U convertTo(final DynamicOps<U> outOps, final IData input) {
        if (this == outOps) {
            return GenericUtil.uncheck(input.copy());
        }
        
        return switch(input.getType()) {
            case BOOL -> outOps.createBoolean(input.asBool());
            case BYTE_ARRAY -> outOps.createByteList(ByteBuffer.wrap(input.asByteArray()));
            case BYTE -> outOps.createByte(input.asByte());
            case DOUBLE -> outOps.createDouble(input.asDouble());
            case FLOAT -> outOps.createFloat(input.asFloat());
            case INT_ARRAY -> outOps.createIntList(IntStream.of(input.asIntArray()));
            case INT -> outOps.createInt(input.asInt());
            case LIST -> this.convertList(outOps, input);
            case LONG_ARRAY -> outOps.createLongList(LongStream.of(input.asLongArray()));
            case LONG -> outOps.createLong(input.asLong());
            case MAP -> this.convertMap(outOps, input);
            case SHORT -> outOps.createShort(input.asShort());
            case STRING -> outOps.createString(input.asString());
            case EMPTY -> outOps.empty();
        };
    }
    
    @Override
    public DataResult<Number> getNumberValue(final IData input) {
        
        return switch(input.getType()) {
            case BYTE -> DataResult.success(input.asByte());
            case DOUBLE -> DataResult.success(input.asDouble());
            case FLOAT -> DataResult.success(input.asFloat());
            case INT -> DataResult.success(input.asInt());
            case LONG -> DataResult.success(input.asLong());
            case SHORT -> DataResult.success(input.asShort());
            default -> this.noType(input, "number");
        };
    }
    
    @Override
    public IData createNumeric(final Number i) {
        
        return new DoubleData(i.doubleValue());
    }
    
    @Override
    public DataResult<String> getStringValue(final IData input) {
        
        return input.getType() == IData.Type.STRING? DataResult.success(input.asString()) : this.noType(input, "string");
    }
    
    @Override
    public IData createString(final String value) {
        
        return new StringData(value);
    }
    
    @Override
    public DataResult<IData> mergeToList(final IData list, final IData value) {
        
        return Optionull.mapOrElse(
                this.adaptToStreamedList(list),
                it -> DataResult.success(it.apply(Stream.of(value))),
                () -> this.noType(list, "list")
        );
    }
    
    @Override
    public DataResult<IData> mergeToMap(final IData map, final IData key, final IData value) {
        
        if (map.getType() != IData.Type.MAP) {
            return this.noType(map, "map");
        }
        if (key.getType() != IData.Type.STRING) {
            return this.noType(key, "string");
        }
        
        final MapData other = GenericUtil.uncheck(map.copyInternal());
        other.put(key.getAsString(), value);
        return DataResult.success(other);
    }
    
    @Override
    public DataResult<Stream<Pair<IData, IData>>> getMapValues(final IData input) {
        
        return input.getType() == IData.Type.MAP?
                DataResult.success(input.getKeys().stream().map(it -> Pair.of(new StringData(it), input.getAt(it)))) :
                this.noType(input, "map");
    }
    
    @Override
    public IData createMap(final Stream<Pair<IData, IData>> map) {
        
        final MapData data = new MapData();
        map.forEach(pair -> data.put(pair.getFirst().getAsString(), pair.getSecond().copyInternal()));
        return data;
    }
    
    @Override
    public DataResult<Stream<IData>> getStream(final IData input) {
        
        return switch(input.getType()) {
            case LIST -> {
                final ListData data = GenericUtil.uncheck(input);
                final boolean mightBeWrapper = data.getInternal().getElementType() == Tag.TAG_COMPOUND;
                final Stream<IData> result = data.asList()
                        .stream()
                        .map(mightBeWrapper? this::unwrapIfNeeded : Function.identity());
                yield DataResult.success(result);
            }
            case BYTE_ARRAY, INT_ARRAY, LONG_ARRAY -> DataResult.success(input.asList().stream());
            default -> this.noType(input, "list");
        };
    }
    
    @Override
    public IData createList(final Stream<IData> input) {
        
        return Objects.requireNonNull(this.adaptToStreamedList(this.empty())).apply(input);
    }
    
    @Override
    public IData remove(final IData input, final String key) {
        
        if (input.getType() != IData.Type.MAP) {
            return input;
        }
        
        final MapData data = GenericUtil.uncheck(input.copyInternal());
        data.remove(key);
        return data;
    }
    
    @Override
    public IData emptyMap() {
        
        return new MapData();
    }
    
    @Override
    public IData emptyList() {
        
        return this.empty();
    }
    
    @Override
    public Number getNumberValue(final IData input, final Number defaultValue) {
        
        return this.getNumberValue(input).result().orElse(defaultValue);
    }
    
    @Override
    public IData createByte(final byte value) {
        
        return new ByteData(value);
    }
    
    @Override
    public IData createShort(final short value) {
        
        return new ShortData(value);
    }
    
    @Override
    public IData createInt(final int value) {
        
        return new IntData(value);
    }
    
    @Override
    public IData createLong(final long value) {
        
        return new LongData(value);
    }
    
    @Override
    public IData createFloat(final float value) {
        
        return new FloatData(value);
    }
    
    @Override
    public IData createDouble(final double value) {
        
        return new DoubleData(value);
    }
    
    @Override
    public DataResult<Boolean> getBooleanValue(final IData input) {
        
        return switch (input.getType()) {
            case BOOL -> DataResult.success(input.asBool());
            case BYTE -> DataResult.success(input.asByte() != 0);
            default -> this.noType(input, "boolean");
        };
    }
    
    @Override
    public IData createBoolean(final boolean value) {
        
        return new BoolData(value);
    }
    
    @Override
    public DataResult<IData> mergeToList(final IData list, final List<IData> values) {
        
        return values.stream().reduce(
                DataResult.success(list),
                (result, value) -> result.flatMap(l -> this.mergeToList(l, value)),
                OpUtils.noCombiner()
        );
    }
    
    @Override
    public DataResult<IData> mergeToMap(final IData map, final Map<IData, IData> values) {
        
        return values.entrySet().stream().reduce(
                DataResult.success(map),
                (result, entry) -> result.flatMap(m -> this.mergeToMap(m, entry.getKey(), entry.getValue())),
                OpUtils.noCombiner()
        );
    }
    
    @Override
    public DataResult<IData> mergeToMap(final IData map, final MapLike<IData> values) {
        
        return values.entries().reduce(
                DataResult.success(map),
                (result, pair) -> result.flatMap(m -> this.mergeToMap(m, pair.getFirst(), pair.getSecond())),
                OpUtils.noCombiner()
        );
    }
    
    @Override
    public DataResult<IData> mergeToPrimitive(final IData prefix, final IData value) {
        
        if (prefix.getType() != IData.Type.EMPTY) {
            return DataResult.error(() -> "Unable to append " + value + " to " + prefix + " primitively", value);
        }
        return DataResult.success(value);
    }
    
    @Override
    public DataResult<Consumer<BiConsumer<IData, IData>>> getMapEntries(final IData input) {
        
        if (input.getType() != IData.Type.MAP) {
            return this.noType(input, "map");
        }
        
        final MapData map = GenericUtil.uncheck(input);
        final Stream<Pair<IData, IData>> entries = map.getKeys().stream().map(it -> Pair.of(new StringData(it), map.getAt(it)));
        return DataResult.success(acceptor -> entries.forEach(pair -> acceptor.accept(pair.getFirst(), pair.getSecond())));
    }
    
    @Override
    public DataResult<MapLike<IData>> getMap(final IData input) {
        
        if (input.getType() != IData.Type.MAP) {
            return this.noType(input, "map");
        }
        
        final MapData data = GenericUtil.uncheck(input);
        final MapLike<IData> mapLike = MapLikeMapData.of(data);
        return DataResult.success(mapLike);
    }
    
    @Override
    public IData createMap(final Map<IData, IData> map) {
        
        return this.createMap(map.entrySet().stream().map(it -> Pair.of(it.getKey(), it.getValue())));
    }
    
    @Override
    public DataResult<Consumer<Consumer<IData>>> getList(final IData input) {
        
        return switch(input.getType()) {
            case LIST -> {
                final ListData data = GenericUtil.uncheck(input);
                final boolean mightBeWrapper = data.getInternal().getElementType() == Tag.TAG_COMPOUND;
                final Stream<IData> dataStream = data.asList()
                        .stream()
                        .map(mightBeWrapper? this::unwrapIfNeeded : Function.identity());
                yield DataResult.success(acceptor -> dataStream.forEach(acceptor));
            }
            case BYTE_ARRAY, INT_ARRAY, LONG_ARRAY -> DataResult.success(acceptor -> input.asList().forEach(acceptor));
            default -> this.noType(input, "list");
        };
    }
    
    @Override
    public DataResult<ByteBuffer> getByteBuffer(final IData input) {
        
        return input.getType() == IData.Type.BYTE_ARRAY?
                DataResult.success(ByteBuffer.wrap(input.asByteArray())) :
                DynamicOps.super.getByteBuffer(input);
    }
    
    @Override
    public IData createByteList(final ByteBuffer input) {
        
        final byte[] array = new byte[input.limit()];
        input.get(array, 0, array.length);
        return new ByteArrayData(array);
    }
    
    @Override
    public DataResult<IntStream> getIntStream(final IData input) {
        
        return input.getType() == IData.Type.INT_ARRAY?
                DataResult.success(IntStream.of(input.asIntArray())) :
                DynamicOps.super.getIntStream(input);
    }
    
    @Override
    public IData createIntList(final IntStream input) {
        
        return new IntArrayData(input.toArray());
    }
    
    @Override
    public DataResult<LongStream> getLongStream(final IData input) {
        
        return input.getType() == IData.Type.LONG_ARRAY?
                DataResult.success(LongStream.of(input.asLongArray())) :
                DynamicOps.super.getLongStream(input);
    }
    
    @Override
    public IData createLongList(final LongStream input) {
        
        return new LongArrayData(input.toArray());
    }

    @Override
    public DataResult<IData> get(final IData input, final String key) {
        
        return input.getType() == IData.Type.MAP?
                Optionull.mapOrElse(
                        input.getAt(key),
                        DataResult::success,
                        () -> DataResult.error(() -> "No such key %s in %s".formatted(key, input))
                ) :
                this.noType(input, "map");
    }
    
    @Override
    public DataResult<IData> getGeneric(final IData input, final IData key) {
        
        return key.getType() == IData.Type.STRING?
                this.get(input, key.getAsString()) :
                DataResult.error(() -> "No such key %s in %s".formatted(key, input));
    }
    
    @Override
    public IData set(final IData input, final String key, final IData value) {
        
        return this.mergeToMap(input, new StringData(key), value).result().orElse(input);
    }
    
    @Override
    public IData update(final IData input, final String key, final Function<IData, IData> function) {
        
        return this.updateGeneric(input, new StringData(key), function);
    }
    
    @Override
    public IData updateGeneric(final IData input, final IData key, final Function<IData, IData> function) {
        
        return this.mergeToMap(input, key, function.apply(this.getGeneric(input, key).result().orElseGet(this::empty)))
                .result()
                .orElse(input);
    }
    
    @Override
    public ListBuilder<IData> listBuilder() {
        
        return IDataListBuilder.of(this);
    }
    
    @Override
    public RecordBuilder<IData> mapBuilder() {
        
        return IDataMapBuilder.of(this);
    }
    
    @Override
    public <E> Function<E, DataResult<IData>> withEncoder(final Encoder<E> encoder) {
        
        return element -> encoder.encode(element, this, this.empty());
    }
    
    @Override
    public <E> Function<IData, DataResult<Pair<E, IData>>> withDecoder(final Decoder<E> decoder) {
        
        return data -> decoder.decode(this, data);
    }
    
    @Override
    public <E> Function<IData, DataResult<E>> withParser(final Decoder<E> decoder) {
        
        return data -> decoder.parse(this, data);
    }
    
    @Override
    public <U> U convertList(final DynamicOps<U> outOps, final IData input) {
        
        final Stream<U> listStream = this.getStream(input)
                .map(stream -> stream.map(it -> this.convertTo(outOps, it)))
                .result()
                .orElseGet(Stream::empty);
        return outOps.createList(listStream);
    }
    
    @Override
    public <U> U convertMap(final DynamicOps<U> outOps, final IData input) {
        
        final Stream<Pair<U, U>> mapStream = this.getMapValues(input)
                .map(stream -> stream.map(it -> it.mapFirst(f -> this.convertTo(outOps, f))))
                .map(stream -> stream.map(it -> it.mapSecond(s -> this.convertTo(outOps, s))))
                .result()
                .orElseGet(Stream::empty);
        return outOps.createMap(mapStream);
    }
    
    @Override
    public String toString() {
        
        return "IData";
    }
    
    private <R> DataResult<R> noType(final IData data, final String expect) {
        return DataResult.error(() -> data.getType() + " is not a valid type for " + expect);
    }
    
    private Function<Stream<IData>, IData> adaptToStreamedList(final IData data) {
        final ListDataAdapter adapter = this.adaptToList(data);
        return adapter == null? null : it -> it.reduce(adapter, Function::apply, OpUtils.noCombiner()).finish();
    }
    
    private ListDataAdapter adaptToList(final IData data) {
        return switch(data.getType()) {
            case BYTE_ARRAY, LONG_ARRAY, INT_ARRAY, LIST -> this.orEmpty(data, SameTypeListDataAdapter::list);
            case EMPTY -> DecidingListDataAdapter.of();
            default -> null;
        };
    }
    
    private ListDataAdapter orEmpty(final IData data, final Function<IData, ListDataAdapter> alternative) {
        return data.asList().isEmpty()? DecidingListDataAdapter.of() : alternative.apply(data);
    }
    
    private IData unwrapIfNeeded(final IData other) {
        
        if (other.getType() != IData.Type.MAP) {
            return other;
        }
        
        final MapData data = GenericUtil.uncheck(other);
        final Set<String> keys = data.getKeys();
        if (keys.size() == 1 && keys.contains(AnyTypeListDataAdapter.WRAPPER_KEY)) {
            return data.getAt(AnyTypeListDataAdapter.WRAPPER_KEY);
        }
        
        return data;
    }
    
}
