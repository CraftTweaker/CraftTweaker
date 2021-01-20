package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.predicate.FishingPredicate")
@Document("vanilla/api/predicate/FishingPredicate")
public final class FishingPredicate extends IVanillaWrappingPredicate.AnyDefaulting<net.minecraft.loot.FishingPredicate> {
    private TriState inOpenWater;

    public FishingPredicate() {
        super(net.minecraft.loot.FishingPredicate.field_234635_a_);
        this.inOpenWater = TriState.UNSET;
    }

    @ZenCodeType.Method
    public FishingPredicate withOpenWaters() {
        this.inOpenWater = TriState.TRUE;
        return this;
    }

    @ZenCodeType.Method
    public FishingPredicate withClosedWaters() {
        this.inOpenWater = TriState.FALSE;
        return this;
    }

    @Override
    public boolean isAny() {
        return this.inOpenWater == TriState.UNSET;
    }

    @Override
    public net.minecraft.loot.FishingPredicate toVanilla() {
        return net.minecraft.loot.FishingPredicate.func_234640_a_(this.inOpenWater.toBoolean());
    }
}
