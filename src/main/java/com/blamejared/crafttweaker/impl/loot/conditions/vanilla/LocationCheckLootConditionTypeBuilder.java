package com.blamejared.crafttweaker.impl.loot.conditions.vanilla;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.conditions.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker.impl.predicate.LocationPredicate;
import com.blamejared.crafttweaker.impl_native.loot.ExpandLootContext;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;

/**
 * Builder for a 'LocationCheck' loot condition.
 *
 * This condition checks the location at which a specific loot table has been rolled, barring a user-specified offset,
 * as obtained via the {@link net.minecraft.loot.LootContext}. The check is performed according to the specified
 * {@link LocationPredicate}.
 *
 * The condition passes when the origin location of the loot table passes the checks of the predicate, after having
 * been offset by the amount specified by the user, if present.
 *
 * If no predicate is specified, the condition simply acts as a check on whether an origin location is available or
 * not.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.vanilla.LocationCheck")
@Document("vanilla/api/loot/conditions/vanilla/LocationCheck")
public final class LocationCheckLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    
    private BlockPos offset;
    private LocationPredicate predicate;
    
    LocationCheckLootConditionTypeBuilder() {
        
        this.offset = new BlockPos(0, 0, 0);
        this.predicate = new LocationPredicate();
    }
    
    /**
     * Sets the offset which should be used to offset the location prior to the predicate check.
     *
     * This parameter is <strong>optional</strong>.
     *
     * @param pos The offset that should be applied.
     *
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public LocationCheckLootConditionTypeBuilder withOffset(final BlockPos pos) {
        
        this.offset = pos;
        return this;
    }
    
    /**
     * Sets the offset along the X axis that should be used to offset the location prior to the predicate check.
     *
     * If an offset has already been specified along any other axis, the offset along those axis is preserved. On the
     * other hand, any previous offset that had already been set on this axis will be overwritten.
     *
     * This parameter is <strong>optional</strong>.
     *
     * @param x The offset that should be applied to the X axis.
     *
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public LocationCheckLootConditionTypeBuilder withXOffset(final int x) {
        
        return this.withOffset(new BlockPos(x, this.offset.getY(), this.offset.getZ()));
    }
    
    /**
     * Sets the offset along the Y axis that should be used to offset the location prior to the predicate check.
     *
     * If an offset has already been specified along any other axis, the offset along those axis is preserved. On the
     * other hand, any previous offset that had already been set on this axis will be overwritten.
     *
     * This parameter is <strong>optional</strong>.
     *
     * @param y The offset that should be applied to the Y axis.
     *
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public LocationCheckLootConditionTypeBuilder withYOffset(final int y) {
        
        return this.withOffset(new BlockPos(this.offset.getX(), y, this.offset.getZ()));
    }
    
    /**
     * Sets the offset along the Z axis that should be used to offset the location prior to the predicate check.
     *
     * If an offset has already been specified along any other axis, the offset along those axis is preserved. On the
     * other hand, any previous offset that had already been set on this axis will be overwritten.
     *
     * This parameter is <strong>optional</strong>.
     *
     * @param z The offset that should be applied to the Z axis.
     *
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public LocationCheckLootConditionTypeBuilder withZOffset(final int z) {
        
        return this.withOffset(new BlockPos(this.offset.getX(), this.offset.getY(), z));
    }
    
    /**
     * Creates and sets the {@link LocationPredicate} that will be matched against the offset location.
     *
     * Any changes that have already been made to the predicate will be overwritten, effectively replacing the previous
     * predicate, if any.
     *
     * This parameter is <strong>optional</strong>.
     *
     * @param builder A consumer that will be used to configure the {@link LocationPredicate}.
     *
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public LocationCheckLootConditionTypeBuilder withLocationPredicate(final Consumer<LocationPredicate> builder) {
        
        final LocationPredicate predicate = new LocationPredicate();
        builder.accept(predicate);
        this.predicate = predicate;
        return this;
    }
    
    @Override
    public ILootCondition finish() {
        
        final net.minecraft.advancements.criterion.LocationPredicate predicate = this.predicate.toVanillaPredicate();
        return context -> {
            final Vector3d origin = ExpandLootContext.getOrigin(context);
            final World world = ExpandLootContext.getWorld(context);
            final Vector3d offset = origin == null ? null : origin.add(this.offset.getX(), this.offset.getY(), this.offset.getZ());
            return offset != null && world instanceof ServerWorld && predicate.test((ServerWorld) world, offset.getX(), offset.getY(), offset.getZ());
        };
    }
    
}
