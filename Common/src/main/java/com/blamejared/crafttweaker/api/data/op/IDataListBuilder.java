package com.blamejared.crafttweaker.api.data.op;

import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.ListBuilder;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.StreamSupport;

final class IDataListBuilder implements ListBuilder<IData> {
    private final DynamicOps<IData> ops;
    private final Supplier<DataResult<ImmutableList.Builder<IData>>> resetState;
    private DataResult<ImmutableList.Builder<IData>> builder;
    
    private IDataListBuilder(final DynamicOps<IData> ops) {
        this.ops = ops;
        this.resetState = () -> DataResult.success(ImmutableList.builder());
        this.reset();
    }
    
    static IDataListBuilder of(final DynamicOps<IData> ops) {
        return new IDataListBuilder(ops);
    }
    
    @Override
    public DynamicOps<IData> ops() {
        
        return this.ops;
    }
    
    @Override
    public DataResult<IData> build(final IData prefix) {
        
        final DataResult<IData> result = this.builder.flatMap(b -> this.ops().mergeToList(prefix, b.build()));
        this.reset();
        return result;
    }
    
    @Override
    public ListBuilder<IData> add(final IData value) {
        
        return this.replace(it -> it.map(b -> b.add(value)));
    }
    
    @Override
    public ListBuilder<IData> add(final DataResult<IData> value) {
        
        return this.replace(it -> it.apply2(ImmutableList.Builder::add, value));
    }
    
    @Override
    public ListBuilder<IData> withErrorsFrom(final DataResult<?> result) {
        
        return this.replace(it -> it.flatMap(b -> result.map(x -> b)));
    }
    
    @Override
    public ListBuilder<IData> mapError(final UnaryOperator<String> onError) {
        
        return this.replace(it -> it.mapError(onError));
    }
    
    @Override
    public DataResult<IData> build(final DataResult<IData> prefix) {
        
        return prefix.flatMap(this::build);
    }
    
    @Override
    public <E> ListBuilder<IData> add(final E value, final Encoder<E> encoder) {
        
        return this.add(encoder.encode(value, this.ops(), this.ops().empty()));
    }
    
    @Override
    public <E> ListBuilder<IData> addAll(final Iterable<E> values, final Encoder<E> encoder) {
        
        return StreamSupport.stream(values.spliterator(), false)
                .reduce(
                        GenericUtil.uncheck(this),
                        (builder, value) -> builder.add(value, encoder),
                        OpUtils.noCombiner()
                );
    }
    
    private ListBuilder<IData> replace(
            final Function<DataResult<ImmutableList.Builder<IData>>, DataResult<ImmutableList.Builder<IData>>> block
    ) {
        this.builder = block.apply(this.builder);
        return this;
    }
    
    private void reset() {
        this.builder = this.resetState.get();
    }
    
}
