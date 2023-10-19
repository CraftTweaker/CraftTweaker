package com.blamejared.crafttweaker.api.data.op;

import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.StringData;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.RecordBuilder;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

final class IDataMapBuilder implements RecordBuilder<IData> {
    private final DynamicOps<IData> ops;
    private final Supplier<DataResult<ImmutableMap.Builder<IData, IData>>> resetState;
    private DataResult<ImmutableMap.Builder<IData, IData>> builder;
    
    private IDataMapBuilder(final DynamicOps<IData> ops) {
        this.ops = ops;
        this.resetState = () -> DataResult.success(ImmutableMap.builder());
        this.reset();
    }
    
    static IDataMapBuilder of(final DynamicOps<IData> ops) {
        return new IDataMapBuilder(ops);
    }
    
    @Override
    public DynamicOps<IData> ops() {
        
        return this.ops;
    }
    
    @Override
    public RecordBuilder<IData> add(final IData key, final IData value) {
        
        return this.replace(it -> it.map(b -> b.put(key, value)));
    }
    
    @Override
    public RecordBuilder<IData> add(final IData key, final DataResult<IData> value) {
        
        return this.add(DataResult.success(key), value);
    }
    
    @Override
    public RecordBuilder<IData> add(final DataResult<IData> key, final DataResult<IData> value) {
        
        return this.replace(it -> it.apply3(ImmutableMap.Builder::put, key, value));
    }
    
    @Override
    public RecordBuilder<IData> withErrorsFrom(final DataResult<?> result) {
        
        return this.replace(it -> it.flatMap(b -> result.map(x -> b)));
    }
    
    @Override
    public RecordBuilder<IData> setLifecycle(final Lifecycle lifecycle) {
        
        return this.replace(it -> it.setLifecycle(lifecycle));
    }
    
    @Override
    public RecordBuilder<IData> mapError(final UnaryOperator<String> onError) {
        
        return this.replace(it -> it.mapError(onError));
    }
    
    @Override
    public DataResult<IData> build(final IData prefix) {
        
        final IData actualPrefix = prefix.getType() == IData.Type.EMPTY? new MapData() : prefix;
        final DataResult<IData> result = this.builder.flatMap(b -> this.ops().mergeToMap(actualPrefix, b.build()));
        this.reset();
        return result;
    }
    
    @Override
    public DataResult<IData> build(final DataResult<IData> prefix) {
        
        return prefix.flatMap(this::build);
    }
    
    @Override
    public RecordBuilder<IData> add(final String key, final IData value) {
        
        return this.add(new StringData(key), value);
    }
    
    @Override
    public RecordBuilder<IData> add(final String key, final DataResult<IData> value) {
        
        return this.add(new StringData(key), value);
    }
    
    @Override
    public <E> RecordBuilder<IData> add(final String key, final E value, final Encoder<E> encoder) {
        
        return this.add(key, encoder.encode(value, this.ops(), this.ops().empty()));
    }
    
    @Override
    public String toString() {
        
        return "IDataMapBuilder[%s@%s]".formatted(this.ops(), this.builder);
    }
    
    private RecordBuilder<IData> replace(
            final Function<DataResult<ImmutableMap.Builder<IData, IData>>, DataResult<ImmutableMap.Builder<IData, IData>>> block
    ) {
        this.builder = block.apply(this.builder);
        return this;
    }
    
    private void reset() {
        this.builder = this.resetState.get();
    }
    
}
