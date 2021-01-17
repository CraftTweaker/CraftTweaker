package com.blamejared.crafttweaker.impl.loot.conditions.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.loot.conditions.TriState;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.predicate.FishingPredicate")
@Document("vanilla/api/loot/conditions/predicate/FishingPredicate")
public final class FishingPredicate {
    private TriState inOpenWater;

    public FishingPredicate() {
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

    boolean isAny() {
        return this.inOpenWater == TriState.UNSET;
    }

    public net.minecraft.loot.FishingPredicate toVanilla() {
        return this.isAny()? net.minecraft.loot.FishingPredicate.field_234635_a_ : net.minecraft.loot.FishingPredicate.func_234640_a_(this.inOpenWater.toBoolean());
    }
}
