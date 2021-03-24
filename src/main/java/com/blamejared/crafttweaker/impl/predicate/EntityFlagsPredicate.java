package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

import java.util.stream.Stream;

/**
 * Represents the predicate for an {@link net.minecraft.entity.Entity}'s status flags.
 *
 * The status flags indicate whether the entity is currently burning, moving and at which speed, and whether it's a baby
 * or an adult, for the entities that have a baby form.
 *
 * By default, the entity passes the check no matter what its status flags are.
 */
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

    /**
     * Indicates that the entity must be on fire.
     *
     * If the predicate had already been set to check the opposite condition, the setting will be overwritten.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public EntityFlagsPredicate withBurningState() {
        this.isBurning = TriState.TRUE;
        return this;
    }

    /**
     * Indicates that the entity must not be on fire.
     *
     * If the predicate had already been set to check the opposite condition, the setting will be overwritten.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public EntityFlagsPredicate withoutBurningState() {
        this.isBurning = TriState.FALSE;
        return this;
    }

    /**
     * Indicates that the entity must be sneaking, if applicable to the current entity.
     *
     * If the predicate had already been set to check the opposite condition, the setting will be overwritten.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public EntityFlagsPredicate withSneakingState() {
        this.isSneaking = TriState.TRUE;
        return this;
    }

    /**
     * Indicates that the entity must not be sneaking.
     *
     * If the predicate had already been set to check the opposite condition, the setting will be overwritten.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public EntityFlagsPredicate withoutSneakingState() {
        this.isSneaking = TriState.FALSE;
        return this;
    }

    /**
     * Indicates that the entity must be sprinting, if applicable to the current entity.
     *
     * If the predicate had already been set to check the opposite condition, the setting will be overwritten.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public EntityFlagsPredicate withSprintingState() {
        this.isSprinting = TriState.TRUE;
        return this;
    }

    /**
     * Indicates that the entity must not be sprinting.
     *
     * If the predicate had already been set to check the opposite condition, the setting will be overwritten.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public EntityFlagsPredicate withoutSprintingState() {
        this.isSprinting = TriState.FALSE;
        return this;
    }

    /**
     * Indicates that the entity must be swimming.
     *
     * If the predicate had already been set to check the opposite condition, the setting will be overwritten.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public EntityFlagsPredicate withSwimmingState() {
        this.isSwimming = TriState.TRUE;
        return this;
    }

    /**
     * Indicates that the entity must not be swimming.
     *
     * If the predicate had already been set to check the opposite condition, the setting will be overwritten.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public EntityFlagsPredicate withoutSwimmingState() {
        this.isSwimming = TriState.FALSE;
        return this;
    }

    /**
     * Indicates that the entity must be in its baby form, if applicable.
     *
     * If the predicate has already been set to check for the entity in its adult form, the setting will be overwritten.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public EntityFlagsPredicate withBaby() {
        this.isBaby = TriState.TRUE;
        return this;
    }

    /**
     * Indicates that the entity must be in its adult form.
     *
     * If the predicate has already been set to check for the entity in its baby form, the setting will be overwritten.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public EntityFlagsPredicate withAdult() {
        this.isBaby = TriState.FALSE;
        return this;
    }

    @Override
    public boolean isAny() {
        return Stream.of(this.isBurning, this.isSprinting, this.isSneaking, this.isSwimming, this.isBaby).allMatch(TriState::isUnset);
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
