package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.impl.data.MapData;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister()
@ZenCodeType.Name("crafttweaker.api.predicate.NBTPredicate")
@Document("vanilla/api/predicate/NBTPredicate")
public final class NBTPredicate extends IVanillaWrappingPredicate.AnyDefaulting<net.minecraft.advancements.criterion.NBTPredicate> {
    private IData data;

    public NBTPredicate() {
        super(net.minecraft.advancements.criterion.NBTPredicate.ANY);
    }

    @ZenCodeType.Method
    public NBTPredicate withData(final IData data) {
        if (!(data instanceof MapData)) throw new IllegalArgumentException("Data inside an 'NBTPredicate' must be an instance of MapData");
        this.data = data;
        return this;
    }

    @Override
    public boolean isAny() {
        return this.data == null || this.data.getInternal() == null;
    }

    @Override
    public net.minecraft.advancements.criterion.NBTPredicate toVanilla() {
        assert this.data instanceof MapData;
        return new net.minecraft.advancements.criterion.NBTPredicate(((MapData) this.data).getInternal());
    }
}
