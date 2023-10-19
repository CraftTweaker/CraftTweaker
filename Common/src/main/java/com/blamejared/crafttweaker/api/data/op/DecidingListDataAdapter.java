package com.blamejared.crafttweaker.api.data.op;

import com.blamejared.crafttweaker.api.data.ByteArrayData;
import com.blamejared.crafttweaker.api.data.EmptyData;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.IntArrayData;
import com.blamejared.crafttweaker.api.data.ListData;
import com.blamejared.crafttweaker.api.data.LongArrayData;

final class DecidingListDataAdapter implements ListDataAdapter {
    
    private DecidingListDataAdapter() {}
    
    public static DecidingListDataAdapter of() {
        return new DecidingListDataAdapter();
    }
    
    @Override
    public IData finish() {
        
        return EmptyData.INSTANCE;
    }
    
    @Override
    public ListDataAdapter apply(final IData data) {
        
        return switch (data.getType()) {
            case BYTE -> SameTypeListDataAdapter.element(data, () -> new ByteArrayData(new byte[0]));
            case INT -> SameTypeListDataAdapter.element(data, () -> new IntArrayData(new int[0]));
            case LONG -> SameTypeListDataAdapter.element(data, () -> new LongArrayData(new long[0]));
            case MAP -> AnyTypeListDataAdapter.element(data);
            case EMPTY -> this;
            default -> SameTypeListDataAdapter.element(data, ListData::new);
        };
    }
    
    @Override
    public String toString() {
        
        return "ListDataAdapter[NONE/%s]".formatted(this.finish());
    }
    
}
