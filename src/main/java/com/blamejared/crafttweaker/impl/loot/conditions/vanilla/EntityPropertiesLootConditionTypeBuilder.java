package com.blamejared.crafttweaker.impl.loot.conditions.vanilla;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.conditions.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker.impl.predicate.EntityPredicate;
import com.blamejared.crafttweaker.impl.predicate.TargetedEntity;
import com.blamejared.crafttweaker.impl_native.loot.ExpandLootContext;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.vanilla.EntityProperties")
@Document("vanilla/api/loot/conditions/vanilla/EntityProperties")
public final class EntityPropertiesLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    private TargetedEntity targetedEntity;
    private EntityPredicate predicate;

    EntityPropertiesLootConditionTypeBuilder() {
        this.predicate = new EntityPredicate();
    }

    @ZenCodeType.Method
    public EntityPropertiesLootConditionTypeBuilder withTargetedEntity(final TargetedEntity entity) {
        this.targetedEntity = entity;
        return this;
    }

    @ZenCodeType.Method
    public EntityPropertiesLootConditionTypeBuilder withPredicate(final Consumer<EntityPredicate> builder) {
        final EntityPredicate predicate = new EntityPredicate();
        builder.accept(predicate);
        this.predicate = predicate;
        return this;
    }

    @Override
    public ILootCondition finish() {
        if (this.targetedEntity == null) {
            throw new IllegalStateException("Targeted entity not defined for an 'EntityScores' condition");
        }
        final net.minecraft.advancements.criterion.EntityPredicate vanilla = this.predicate.toVanillaPredicate();
        return context -> {
            final Entity entity = this.targetedEntity.getLootContextDiscriminator().apply(context);
            final Vector3d origin = ExpandLootContext.getOrigin(context);
            final World world = ExpandLootContext.getWorld(context);
            return world instanceof ServerWorld && vanilla.test((ServerWorld) world, origin, entity);
        };
    }
}
