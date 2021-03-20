package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.potion.Effect;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Represents a predicate for an {@link net.minecraft.entity.Entity}.
 *
 * This predicate leverages many children predicates to check for the many entity parameters. In fact, this predicate
 * can be used to check an entity's type via an {@link EntityTypePredicate}, along with the distance from a certain
 * position via {@link DistancePredicate}. The predicate is also able to check whether the entity is in a certain
 * location using a {@link LocationPredicate} and whether there are potion effects currently active
 * ({@link MobEffectsPredicate}). The entity's internal NBT data can also be checked with an {@link NBTPredicate} along
 * with its status flags ({@link EntityFlagsPredicate}) and equipment ({@link EntityEquipmentPredicate}). The entity
 * can also be checked for its team, if available, and for the entity that is currently riding or targeting for its
 * attacks, via additional entity predicates.
 *
 * Moreover, the predicate provides additional specialization for specific types of entities such as players
 * ({@link PlayerPredicate}), cats (by allowing to query their type), and fishing hooks ({@link FishingPredicate}). If
 * any of these specialization is added, then the predicate will also ensure that the entity type is applicable for that
 * specific specialization (e.g., if a player-only predicate is specified, this predicate will only pass if the entity
 * is a player).
 *
 * By default, any entity will pass this check, without caring about any of its properties.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.predicate.EntityPredicate")
@Document("vanilla/api/predicate/EntityPredicate")
public final class EntityPredicate extends IVanillaWrappingPredicate.AnyDefaulting<net.minecraft.advancements.criterion.EntityPredicate> {
    private EntityTypePredicate entityTypePredicate;
    private DistancePredicate distancePredicate;
    private LocationPredicate locationPredicate;
    private MobEffectsPredicate effectsPredicate;
    private NBTPredicate nbtPredicate;
    private EntityFlagsPredicate entityFlagsPredicate;
    private EntityEquipmentPredicate equipmentPredicate;
    private PlayerPredicate player;
    private FishingPredicate fishing;
    private EntityPredicate riddenEntity; // -.-
    private EntityPredicate targetedEntity; // thanks Mojang
    private String team;
    private ResourceLocation catType;

    public EntityPredicate() {
        super(net.minecraft.advancements.criterion.EntityPredicate.ANY);
        this.entityTypePredicate = new EntityTypePredicate();
        this.distancePredicate = new DistancePredicate();
        this.locationPredicate = new LocationPredicate();
        this.effectsPredicate = new MobEffectsPredicate();
        this.nbtPredicate = new NBTPredicate();
        this.entityFlagsPredicate = new EntityFlagsPredicate();
        this.equipmentPredicate = new EntityEquipmentPredicate();
        this.player = new PlayerPredicate();
        this.fishing = new FishingPredicate();
    }

    /**
     * Creates and sets the {@link EntityTypePredicate} that will be used to match the entity's type.
     *
     * Any changes that have been made previously to the entity type predicate will be discarded, if any.
     *
     * @param builder A consumer that will be used to configure the {@link EntityTypePredicate}.
     * @return The predicate itself for chaining.
     */
    @ZenCodeType.Method
    public EntityPredicate withEntityTypePredicate(final Consumer<EntityTypePredicate> builder) {
        final EntityTypePredicate predicate = new EntityTypePredicate();
        builder.accept(predicate);
        this.entityTypePredicate = predicate;
        return this;
    }

    /**
     * Creates and sets the {@link DistancePredicate} that will be used to match the entity's distance from a point.
     *
     * Any changes that have been made previously to the distance predicate will be discarded, if any.
     *
     * @param builder A consumer that will be used to configure the {@link DistancePredicate}.
     * @return The predicate itself for chaining.
     */
    @ZenCodeType.Method
    public EntityPredicate withDistancePredicate(final Consumer<DistancePredicate> builder) {
        final DistancePredicate predicate = new DistancePredicate();
        builder.accept(predicate);
        this.distancePredicate = predicate;
        return this;
    }

    /**
     * Creates and sets the {@link LocationPredicate} that will be used to match the entity's location.
     *
     * Any changes that have been made previously to the location predicate will be discarded, if any.
     *
     * @param builder A consumer that will be used to configure the {@link LocationPredicate}.
     * @return The predicate itself for chaining.
     */
    @ZenCodeType.Method
    public EntityPredicate withLocationPredicate(final Consumer<LocationPredicate> builder) {
        final LocationPredicate predicate = new LocationPredicate();
        builder.accept(predicate);
        this.locationPredicate = predicate;
        return this;
    }

    /**
     * Creates and sets the {@link MobEffectsPredicate} that will be used to match the entity's current potion effects.
     *
     * Any changes that have been made previously to the potion effects predicate will be discarded, if any.
     *
     * @param builder A consumer that will be used to configure the {@link MobEffectsPredicate}.
     * @return The predicate itself for chaining.
     */
    @ZenCodeType.Method
    public EntityPredicate withEffectPredicate(final Consumer<MobEffectsPredicate> builder) {
        final MobEffectsPredicate predicate = new MobEffectsPredicate();
        builder.accept(predicate);
        this.effectsPredicate = predicate;
        return this;
    }

    /**
     * Sets the effect that should be present on the entity.
     *
     * The effect should also match the data that gets configured in the {@link EffectData} predicate.
     *
     * Any changes that have been previously made to the effect predicate, if any, are discarded. Any other effects that
     * have been added using this method are also discarded, keeping only the currently specified one.
     *
     * @param effect The effect that should be present on the entity.
     * @param builder A consumer that will be used to configure and provide the effect's data.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public EntityPredicate withEffectPredicate(final Effect effect, final Consumer<EffectData> builder) {
        return this.withEffectPredicate(predicate -> predicate.withEffect(effect, builder));
    }

    /**
     * Creates and sets the {@link NBTPredicate} that will be used to match the entity's NBT data.
     *
     * Any changes that have been made previously to the NBT data predicate will be discarded, if any.
     *
     * @param builder A consumer that will be used to configure the {@link NBTPredicate}.
     * @return The predicate itself for chaining.
     */
    @ZenCodeType.Method
    public EntityPredicate withDataPredicate(final Consumer<NBTPredicate> builder) {
        final NBTPredicate predicate = new NBTPredicate();
        builder.accept(predicate);
        this.nbtPredicate = predicate;
        return this;
    }

    /**
     * Creates and sets the {@link EntityFlagsPredicate} that will be used to match the entity's status flags.
     *
     * Any changes that have been made previously to the status flags predicate will be discarded, if any.
     *
     * @param builder A consumer that will be used to configure the {@link EntityFlagsPredicate}.
     * @return The predicate itself for chaining.
     */
    @ZenCodeType.Method
    public EntityPredicate withFlagsPredicate(final Consumer<EntityFlagsPredicate> builder) {
        final EntityFlagsPredicate predicate = new EntityFlagsPredicate();
        builder.accept(predicate);
        this.entityFlagsPredicate = predicate;
        return this;
    }

    /**
     * Creates and sets the {@link EntityEquipmentPredicate} that will be used to match the entity's equipment.
     *
     * Any changes that have been made previously to the equipment predicate will be discarded, if any.
     *
     * @param builder A consumer that will be used to configure the {@link EntityEquipmentPredicate}.
     * @return The predicate itself for chaining.
     */
    @ZenCodeType.Method
    public EntityPredicate withEquipmentPredicate(final Consumer<EntityEquipmentPredicate> builder) {
        final EntityEquipmentPredicate predicate = new EntityEquipmentPredicate();
        builder.accept(predicate);
        this.equipmentPredicate = predicate;
        return this;
    }

    /**
     * Creates and sets the {@link PlayerPredicate} that will be used as specialization for players.
     *
     * As a side effect of this method, this entity predicate will require the entity to be a player, failing the check
     * otherwise.
     *
     * Any changes that have been made previously to the player predicate will be discarded, if any.
     *
     * @param builder A consumer that will be used to configure the {@link PlayerPredicate}.
     * @return The predicate itself for chaining.
     */
    @ZenCodeType.Method
    public EntityPredicate withPlayerPredicate(final Consumer<PlayerPredicate> builder) {
        final PlayerPredicate predicate = new PlayerPredicate();
        builder.accept(predicate);
        this.player = predicate;
        return this;
    }

    /**
     * Creates and sets the {@link FishingPredicate} that will be used as specialization for fishing hooks.
     *
     * As a side effect of this method, this entity predicate will require the entity to be a fishing hook, failing the
     * check otherwise.
     *
     * Any changes that have been made previously to the fishing hook predicate will be discarded, if any.
     *
     * @param builder A consumer that will be used to configure the {@link FishingPredicate}.
     * @return The predicate itself for chaining.
     */
    @ZenCodeType.Method
    public EntityPredicate withFishingPredicate(final Consumer<FishingPredicate> builder) {
        final FishingPredicate predicate = new FishingPredicate();
        builder.accept(predicate);
        this.fishing = predicate;
        return this;
    }

    /**
     * Creates and sets the {@link EntityPredicate} that will be used for the entity that is being ridden by this
     * entity.
     *
     * As a side effect of this method, this entity predicate will require the entity to be riding something, failing
     * the check otherwise.
     *
     * Any changes that have been made previously to the ridden entity predicate will be discarded, if any.
     *
     * @param builder A consumer that will be used to configure the {@link EntityPredicate}.
     * @return The predicate itself for chaining.
     */
    @ZenCodeType.Method
    public EntityPredicate withRiddenEntityPredicate(final Consumer<EntityPredicate> builder) {
        final EntityPredicate riddenEntity = new EntityPredicate();
        builder.accept(riddenEntity);
        this.riddenEntity = riddenEntity;
        return this;
    }

    /**
     * Creates and sets the {@link EntityPredicate} that will be used for the entity that is being targeted for attacks
     * by this entity.
     *
     * As a side effect of this method, this entity predicate will require the entity to be attacking something, failing
     * the check otherwise.
     *
     * Any changes that have been made previously to the targeted entity predicate will be discarded, if any.
     *
     * @param builder A consumer that will be used to configure the {@link EntityPredicate}.
     * @return The predicate itself for chaining.
     */
    @ZenCodeType.Method
    public EntityPredicate withTargetedEntityPredicate(final Consumer<EntityPredicate> builder) {
        final EntityPredicate targetedEntity = new EntityPredicate();
        builder.accept(targetedEntity);
        this.targetedEntity = targetedEntity;
        return this;
    }

    /**
     * Sets the scoreboard team this entity should be a part of.
     *
     * @param team The scoreboard team this entity should be a part of.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public EntityPredicate withTeam(final String team) {
        this.team = team;
        return this;
    }

    /**
     * Sets the type of cat this entity should be.
     *
     * As a side effect of this method, this entity predicate will require the entity to be a cat, failing the check
     * otherwise.
     *
     * @param catType The type of cat this entity should be.
     * @return The predicate itself for chaining.
     */
    @ZenCodeType.Method
    public EntityPredicate withCatType(final ResourceLocation catType) {
        this.catType = catType;
        return this;
    }

    @Override
    public boolean isAny() {
        return Stream.of(this.entityTypePredicate, this.distancePredicate, this.locationPredicate, this.effectsPredicate, this.nbtPredicate,
                this.entityFlagsPredicate, this.equipmentPredicate, this.player, this.fishing).allMatch(AnyDefaulting::isAny)
                && (this.riddenEntity == null || this.riddenEntity.isAny()) && (this.targetedEntity == null || this.targetedEntity.isAny())
                && this.team != null && this.catType != null;
    }

    public net.minecraft.advancements.criterion.EntityPredicate toVanilla() {
        return net.minecraft.advancements.criterion.EntityPredicate.Builder.create()
                .type(this.entityTypePredicate.toVanillaPredicate())
                .distance(this.distancePredicate.toVanillaPredicate())
                .location(this.locationPredicate.toVanillaPredicate())
                .effects(this.effectsPredicate.toVanillaPredicate())
                .nbt(this.nbtPredicate.toVanillaPredicate())
                .flags(this.entityFlagsPredicate.toVanillaPredicate())
                .equipment(this.equipmentPredicate.toVanillaPredicate())
                .player(this.player.toVanillaPredicate())
                .fishing(this.fishing.toVanillaPredicate())
                .mount(this.riddenEntity == null? net.minecraft.advancements.criterion.EntityPredicate.ANY : this.riddenEntity.toVanillaPredicate())
                .target(this.targetedEntity == null? net.minecraft.advancements.criterion.EntityPredicate.ANY : this.targetedEntity.toVanillaPredicate())
                .team(this.team)
                .catTypeOrNull(this.catType)
                .build();
    }
}
