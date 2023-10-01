package com.blamejared.crafttweaker.api.data.op;

import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.StringData;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapLike;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

final class MapLikeMapData implements MapLike<IData> {
    private final MapData data;
    
    private MapLikeMapData(final MapData data) {
        this.data = data;
    }
    
    static MapLikeMapData of(final MapData data) {
        return new MapLikeMapData(GenericUtil.uncheck(data.copyInternal()));
    }
    
    @Nullable
    @Override
    public IData get(final IData key) {
        
        return this.get(key.getAsString());
    }
    
    @Nullable
    @Override
    public IData get(final String key) {
        
        return this.data.getAt(key);
    }
    
    @Override
    public Stream<Pair<IData, IData>> entries() {
        
        return this.data.getKeys().stream().map(it -> Pair.of(new StringData(it), this.data.getAt(it)));
    }
    
    @Override
    public String toString() {
        
        return "MapLike[%s]".formatted(this.data);
    }
    
}
