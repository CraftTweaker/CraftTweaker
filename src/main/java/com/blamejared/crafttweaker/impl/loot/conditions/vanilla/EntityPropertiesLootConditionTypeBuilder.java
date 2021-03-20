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

/**
 * Builder to create an 'EntityProperties' loot condition.
 *
 * This condition checks the {@link net.minecraft.loot.LootContext} for an {@link Entity} that matches the targeted one,
 * specified via {@link TargetedEntity}, along with some additional properties like world and origin. The entity, if
 * present, is matched against an {@link EntityPredicate} which will be used to determine entity-specific properties.
 *
 * The condition passes if and only if there exists a valid entity to target and the predicate matches.
 *
 * A valid 'EntityProperties' loot condition has to specify a targeted entity. The predicate, on the other hand, is
 * optional: a lack of predicate means that the condition will simply check for the presence of the targeted entity.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.vanilla.EntityProperties")
@Document("vanilla/api/loot/conditions/vanilla/EntityProperties")
public final class EntityPropertiesLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    private TargetedEntity targetedEntity;
    private EntityPredicate predicate;

    EntityPropertiesLootConditionTypeBuilder() {
        this.predicate = new EntityPredicate();
    }

    /**
     * Sets the entity that should be targeted by the loot condition.
     *
     * Refer to {@link TargetedEntity} for a full list and their respective meaning.
     *
     * This parameter is <strong>required</strong>.
     *
     * @param entity The entity that should be targeted.
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public EntityPropertiesLootConditionTypeBuilder withTargetedEntity(final TargetedEntity entity) {
        this.targetedEntity = entity;
        return this;
    }
    
    /**
     * Creates and sets the {@link EntityPredicate} that will be matched against the targeted entity.
     *
     * Any changes that have already been made to the predicate will be overwritten, effectively replacing the previous
     * predicate, if any.
     *
     * This parameter is <strong>optional</strong>.
     *
     * @param builder A consumer that will be used to configure the {@link EntityPredicate}.
     * @return This builder for chaining.
     */
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
            throw new IllegalStateException("Targeted entity not defined for an 'EntityProperties' condition");
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
