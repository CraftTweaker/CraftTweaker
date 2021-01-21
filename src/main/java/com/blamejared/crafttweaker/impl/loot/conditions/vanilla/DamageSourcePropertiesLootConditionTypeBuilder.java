package com.blamejared.crafttweaker.impl.loot.conditions.vanilla;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.conditions.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker.impl.predicate.DamageSourcePredicate;
import com.blamejared.crafttweaker.impl_native.loot.ExpandLootContext;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.vanilla.DamageSourceProperties")
@Document("vanilla/api/loot/conditions/vanilla/DamageSourceProperties")
public final class DamageSourcePropertiesLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    private DamageSourcePredicate predicate;

    DamageSourcePropertiesLootConditionTypeBuilder() {
        this.predicate = new DamageSourcePredicate();
    }

    @ZenCodeType.Method
    public DamageSourcePropertiesLootConditionTypeBuilder withPredicate(final Consumer<DamageSourcePredicate> builder) {
        final DamageSourcePredicate predicate = new DamageSourcePredicate();
        builder.accept(predicate);
        this.predicate = predicate;
        return this;
    }

    @Override
    public ILootCondition finish() {
        final net.minecraft.advancements.criterion.DamageSourcePredicate vanilla = this.predicate.toVanillaPredicate();
        return context -> {
            final DamageSource source = ExpandLootContext.getDamageSource(context);
            final Vector3d origin = ExpandLootContext.getOrigin(context);
            final World world = ExpandLootContext.getWorld(context);
            return source != null && origin != null && world instanceof ServerWorld && vanilla.test((ServerWorld) world, origin, source);
        };
    }
}
