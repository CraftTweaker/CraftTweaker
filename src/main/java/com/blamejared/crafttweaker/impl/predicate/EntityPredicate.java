package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.potion.Effect;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;
import java.util.stream.Stream;

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

    @ZenCodeType.Method
    public EntityPredicate withEntityTypePredicate(final Consumer<EntityTypePredicate> builder) {
        final EntityTypePredicate predicate = new EntityTypePredicate();
        builder.accept(predicate);
        this.entityTypePredicate = predicate;
        return this;
    }

    @ZenCodeType.Method
    public EntityPredicate withDistancePredicate(final Consumer<DistancePredicate> builder) {
        final DistancePredicate predicate = new DistancePredicate();
        builder.accept(predicate);
        this.distancePredicate = predicate;
        return this;
    }

    @ZenCodeType.Method
    public EntityPredicate withLocationPredicate(final Consumer<LocationPredicate> builder) {
        final LocationPredicate predicate = new LocationPredicate();
        builder.accept(predicate);
        this.locationPredicate = predicate;
        return this;
    }

    @ZenCodeType.Method
    public EntityPredicate withEffectPredicate(final Consumer<MobEffectsPredicate> builder) {
        final MobEffectsPredicate predicate = new MobEffectsPredicate();
        builder.accept(predicate);
        this.effectsPredicate = predicate;
        return this;
    }

    @ZenCodeType.Method
    public EntityPredicate withEffectPredicate(final Effect effect, final Consumer<EffectData> builder) {
        return this.withEffectPredicate(predicate -> predicate.withEffect(effect, builder));
    }

    @ZenCodeType.Method
    public EntityPredicate withDataPredicate(final Consumer<NBTPredicate> builder) {
        final NBTPredicate predicate = new NBTPredicate();
        builder.accept(predicate);
        this.nbtPredicate = predicate;
        return this;
    }

    @ZenCodeType.Method
    public EntityPredicate withFlagsPredicate(final Consumer<EntityFlagsPredicate> builder) {
        final EntityFlagsPredicate predicate = new EntityFlagsPredicate();
        builder.accept(predicate);
        this.entityFlagsPredicate = predicate;
        return this;
    }

    @ZenCodeType.Method
    public EntityPredicate withEquipmentPredicate(final Consumer<EntityEquipmentPredicate> builder) {
        final EntityEquipmentPredicate predicate = new EntityEquipmentPredicate();
        builder.accept(predicate);
        this.equipmentPredicate = predicate;
        return this;
    }

    @ZenCodeType.Method
    public EntityPredicate withPlayerPredicate(final Consumer<PlayerPredicate> builder) {
        final PlayerPredicate predicate = new PlayerPredicate();
        builder.accept(predicate);
        this.player = predicate;
        return this;
    }

    @ZenCodeType.Method
    public EntityPredicate withFishingPredicate(final Consumer<FishingPredicate> builder) {
        final FishingPredicate predicate = new FishingPredicate();
        builder.accept(predicate);
        this.fishing = predicate;
        return this;
    }

    @ZenCodeType.Method
    public EntityPredicate withRiddenEntityPredicate(final Consumer<EntityPredicate> builder) {
        final EntityPredicate riddenEntity = new EntityPredicate();
        builder.accept(riddenEntity);
        this.riddenEntity = riddenEntity;
        return this;
    }

    @ZenCodeType.Method
    public EntityPredicate withTargetedEntityPredicate(final Consumer<EntityPredicate> builder) {
        final EntityPredicate targetedEntity = new EntityPredicate();
        builder.accept(targetedEntity);
        this.targetedEntity = targetedEntity;
        return this;
    }

    @ZenCodeType.Method
    public EntityPredicate withTeam(final String team) {
        this.team = team;
        return this;
    }

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
                && team != null && catType != null;
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
