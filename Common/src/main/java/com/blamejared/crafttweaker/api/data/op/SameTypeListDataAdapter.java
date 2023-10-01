package com.blamejared.crafttweaker.api.data.op;

import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import net.minecraft.nbt.CollectionTag;
import net.minecraft.nbt.Tag;

import java.util.function.Supplier;

final class SameTypeListDataAdapter<T extends IData> implements ListDataAdapter {
    private final IData.Type type;
    private final T data;
    
    private SameTypeListDataAdapter(final IData.Type type, final Supplier<T> creator) {
        this.type = type;
        this.data = creator.get();
    }
    
    static <T extends IData> SameTypeListDataAdapter<T> list(final IData data) {
        final IData.Type type = typeFromCollectionType(data);
        return new SameTypeListDataAdapter<>(type, () -> GenericUtil.uncheck(data.copyInternal()));
    }
    
    static <T extends IData> SameTypeListDataAdapter<T> element(final IData data, final Supplier<T> creator) {
        final SameTypeListDataAdapter<T> adapter = new SameTypeListDataAdapter<>(data.getType(), creator);
        return GenericUtil.uncheck(adapter.apply(data));
    }
    
    private static IData.Type typeFromCollectionType(final IData data) {
        final CollectionTag<?> tag = GenericUtil.uncheck(data.getInternal());
        return switch(tag.getElementType()) {
            case Tag.TAG_END -> IData.Type.EMPTY;
            case Tag.TAG_BYTE -> IData.Type.BYTE;
            case Tag.TAG_SHORT -> IData.Type.SHORT;
            case Tag.TAG_INT -> IData.Type.INT;
            case Tag.TAG_LONG -> IData.Type.LONG;
            case Tag.TAG_FLOAT -> IData.Type.FLOAT;
            case Tag.TAG_DOUBLE -> IData.Type.DOUBLE;
            case Tag.TAG_BYTE_ARRAY -> IData.Type.BYTE_ARRAY;
            case Tag.TAG_STRING -> IData.Type.STRING;
            case Tag.TAG_LIST -> IData.Type.LIST;
            case Tag.TAG_COMPOUND -> IData.Type.MAP;
            case Tag.TAG_INT_ARRAY -> IData.Type.INT_ARRAY;
            case Tag.TAG_LONG_ARRAY -> IData.Type.LONG_ARRAY;
            default -> throw new IllegalStateException("Unexpected value: " + tag.getElementType());
        };
    }
    
    @Override
    public IData finish() {
        
        return this.data;
    }
    
    @Override
    public ListDataAdapter apply(final IData data) {
        
        if (data.getType() == this.type) {
            GenericUtil.<CollectionTag<?>>uncheck(this.data.getInternal()).add(GenericUtil.uncheck(data.getInternal()));
            return this;
        }
        
        final AnyTypeListDataAdapter adapter = AnyTypeListDataAdapter.list(this.finish());
        return adapter.apply(data);
    }
}
