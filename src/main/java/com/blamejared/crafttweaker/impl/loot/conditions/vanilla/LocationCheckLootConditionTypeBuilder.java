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

    @ZenCodeType.Method
    public LocationCheckLootConditionTypeBuilder withOffset(final BlockPos pos) {
        this.offset = pos;
        return this;
    }

    @ZenCodeType.Method
    public LocationCheckLootConditionTypeBuilder withXOffset(final int x) {
        return this.withOffset(new BlockPos(x, this.offset.getY(), this.offset.getZ()));
    }

    @ZenCodeType.Method
    public LocationCheckLootConditionTypeBuilder withYOffset(final int y) {
        return this.withOffset(new BlockPos(this.offset.getX(), y, this.offset.getZ()));
    }

    @ZenCodeType.Method
    public LocationCheckLootConditionTypeBuilder withZOffset(final int z) {
        return this.withOffset(new BlockPos(this.offset.getX(), this.offset.getY(), z));
    }

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
            final Vector3d offset = origin == null? null : origin.add(this.offset.getX(), this.offset.getY(), this.offset.getZ());
            return origin != null && world instanceof ServerWorld && predicate.test((ServerWorld) world, offset.getX(), offset.getY(), offset.getZ());
        };
    }
}
