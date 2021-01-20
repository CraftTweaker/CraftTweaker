package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

import java.util.stream.Stream;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.predicate.EntityFlagsPredicate")
@Document("vanilla/api/predicate/EntityFlagsPredicate")
public final class EntityFlagsPredicate extends IVanillaWrappingPredicate.AnyDefaulting<net.minecraft.advancements.criterion.EntityFlagsPredicate> {
    private TriState isBurning;
    private TriState isSneaking;
    private TriState isSprinting;
    private TriState isSwimming;
    private TriState isBaby;

    public EntityFlagsPredicate() {
        super(net.minecraft.advancements.criterion.EntityFlagsPredicate.ALWAYS_TRUE);
        this.isBurning = TriState.UNSET;
        this.isSneaking = TriState.UNSET;
        this.isSprinting = TriState.UNSET;
        this.isSwimming = TriState.UNSET;
        this.isBaby = TriState.UNSET;
    }

    @ZenCodeType.Method
    public EntityFlagsPredicate withBurningState() {
        this.isBurning = TriState.TRUE;
        return this;
    }

    @ZenCodeType.Method
    public EntityFlagsPredicate withoutBurningState() {
        this.isBurning = TriState.FALSE;
        return this;
    }

    @ZenCodeType.Method
    public EntityFlagsPredicate withSneakingState() {
        this.isSneaking = TriState.TRUE;
        return this;
    }

    @ZenCodeType.Method
    public EntityFlagsPredicate withoutSneakingState() {
        this.isSneaking = TriState.FALSE;
        return this;
    }

    @ZenCodeType.Method
    public EntityFlagsPredicate withSprintingState() {
        this.isSprinting = TriState.TRUE;
        return this;
    }

    @ZenCodeType.Method
    public EntityFlagsPredicate withoutSprintingState() {
        this.isSprinting = TriState.FALSE;
        return this;
    }

    @ZenCodeType.Method
    public EntityFlagsPredicate withSwimmingState() {
        this.isSwimming = TriState.TRUE;
        return this;
    }

    @ZenCodeType.Method
    public EntityFlagsPredicate withoutSwimmingState() {
        this.isSwimming = TriState.FALSE;
        return this;
    }

    @ZenCodeType.Method
    public EntityFlagsPredicate withBaby() {
        this.isBaby = TriState.TRUE;
        return this;
    }

    @ZenCodeType.Method
    public EntityFlagsPredicate withAdult() {
        this.isBaby = TriState.FALSE;
        return this;
    }

    @Override
    public boolean isAny() {
        return Stream.of(this.isBurning, this.isSprinting, this.isSneaking, this.isSwimming, this.isBaby).allMatch(it -> it == TriState.UNSET);
    }

    @Override
    public net.minecraft.advancements.criterion.EntityFlagsPredicate toVanilla() {
        return new net.minecraft.advancements.criterion.EntityFlagsPredicate(
                this.isBurning.toBoolean(),
                this.isSneaking.toBoolean(),
                this.isSprinting.toBoolean(),
                this.isSwimming.toBoolean(),
                this.isBaby.toBoolean()
        );
    }
}
