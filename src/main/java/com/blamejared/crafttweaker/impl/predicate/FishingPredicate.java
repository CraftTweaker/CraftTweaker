package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Represents a predicate for a fishing hook, as a specialization of {@link EntityPredicate}.
 *
 * This predicate can be used to check various properties of the fishing hook entity, such as being used for fishing in
 * open waters instead of a closed pond.
 *
 * By default, the entity passes the checks without caring about the entity type. If at least one of the properties is
 * set, then the entity must be a fishing hook to pass the checks.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.predicate.FishingPredicate")
@Document("vanilla/api/predicate/FishingPredicate")
public final class FishingPredicate extends IVanillaWrappingPredicate.AnyDefaulting<net.minecraft.loot.FishingPredicate> {
    private TriState inOpenWater;

    public FishingPredicate() {
        super(net.minecraft.loot.FishingPredicate.field_234635_a_);
        this.inOpenWater = TriState.UNSET;
    }

    /**
     * Indicates that the fishing hook has been used in open waters.
     *
     * If the predicate had already been set to check the opposite condition, the setting will be overwritten.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public FishingPredicate withOpenWaters() {
        this.inOpenWater = TriState.TRUE;
        return this;
    }

    /**
     * Indicates that the fishing hook has been used in not open waters, such as a pond.
     *
     * If the predicate had already been set to check the opposite condition, the setting will be overwritten.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public FishingPredicate withClosedWaters() {
        this.inOpenWater = TriState.FALSE;
        return this;
    }

    @Override
    public boolean isAny() {
        return this.inOpenWater.isUnset();
    }

    @Override
    public net.minecraft.loot.FishingPredicate toVanilla() {
        return net.minecraft.loot.FishingPredicate.func_234640_a_(this.inOpenWater.toBoolean());
    }
}
