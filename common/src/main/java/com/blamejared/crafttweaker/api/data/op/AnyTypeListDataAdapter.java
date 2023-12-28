package com.blamejared.crafttweaker.api.data.op;

import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.ListData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.util.GenericUtil;

import java.util.function.Function;

final class AnyTypeListDataAdapter implements ListDataAdapter {
    static final String WRAPPER_KEY = "$$wrapped$$";
    
    private final ListData data;
    
    private AnyTypeListDataAdapter() {
        this.data = new ListData();
    }
    
    static AnyTypeListDataAdapter element(final IData data) {
        final AnyTypeListDataAdapter adapter = new AnyTypeListDataAdapter();
        return GenericUtil.uncheck(adapter.apply(data));
    }
    
    // IData is already a list (it comes from SameTypeList or ByteList or similar); it needs conversion and cloning
    static AnyTypeListDataAdapter list(final IData data) {
        final ListDataAdapter adapter = new AnyTypeListDataAdapter();
        final ListDataAdapter result = data.asList()
                .stream()
                .reduce(adapter, Function::apply, OpUtils.noCombiner());
        return GenericUtil.uncheck(result);
    }
    
    @Override
    public IData finish() {
        
        return this.data;
    }
    
    @Override
    public ListDataAdapter apply(final IData data) {
        
        this.append(data);
        return this;
    }
    
    @Override
    public String toString() {
        
        return "ListDataAdapter[ANY/%s]".formatted(this.data);
    }
    
    private void append(final IData data) {
        
        this.data.add(data.getType() == IData.Type.MAP && !this.wrapper(data)? data : this.wrap(data));
    }
    
    private IData wrap(final IData data) {
        final MapData wrapper = new MapData();
        wrapper.put(WRAPPER_KEY, data);
        return wrapper;
    }
    
    private boolean wrapper(final IData data) {
        return data.getType() == IData.Type.MAP && data.getKeys().size() == 1 && data.getAt(WRAPPER_KEY) != null;
    }
    
}
