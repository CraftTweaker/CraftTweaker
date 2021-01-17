package com.blamejared.crafttweaker.impl.loot.conditions.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.impl.data.MapData;
import com.blamejared.crafttweaker.impl.entity.MCEntityType;
import com.blamejared.crafttweaker.impl.loot.conditions.FloatRange;
import com.blamejared.crafttweaker.impl.loot.conditions.TriState;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.advancements.criterion.DistancePredicate;
import net.minecraft.advancements.criterion.EntityEquipmentPredicate;
import net.minecraft.advancements.criterion.EntityFlagsPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.MobEffectsPredicate;
import net.minecraft.advancements.criterion.NBTPredicate;
import net.minecraft.potion.Effect;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.predicate.EntityPredicate")
@Document("vanilla/api/loot/conditions/predicate/EntityPredicate")
public final class EntityPredicate {
    private final Map<Effect, EffectData> effects;

    private MCEntityType entityType;
    private MCTag<MCEntityType> entityTypeTag;
    private FloatRange absoluteDistance;
    private FloatRange horizontalDistance;
    private FloatRange xDistance;
    private FloatRange yDistance;
    private FloatRange zDistance;
    private LocationPredicate locationPredicate;
    private IData data;
    private TriState isBurning;
    private TriState isSneaking;
    private TriState isSprinting;
    private TriState isSwimming;
    private TriState isBaby;
    private ItemPredicate head;
    private ItemPredicate chest;
    private ItemPredicate legs;
    private ItemPredicate feet;
    private ItemPredicate mainHand;
    private ItemPredicate offHand;
    private PlayerPredicate player;
    private FishingPredicate fishing;
    private EntityPredicate riddenEntity; // -.-
    private EntityPredicate targetedEntity; // thanks Mojang
    private String team;
    private ResourceLocation catType;

    public EntityPredicate() {
        this.locationPredicate = new LocationPredicate();
        this.effects = new LinkedHashMap<>();
        this.isBurning = TriState.UNSET;
        this.isSneaking = TriState.UNSET;
        this.isSprinting = TriState.UNSET;
        this.isSwimming = TriState.UNSET;
        this.isBaby = TriState.UNSET;
        this.head = new ItemPredicate();
        this.chest = new ItemPredicate();
        this.legs = new ItemPredicate();
        this.feet = new ItemPredicate();
        this.mainHand = new ItemPredicate();
        this.offHand = new ItemPredicate();
        this.player = new PlayerPredicate();
        this.fishing = new FishingPredicate();
    }

    @ZenCodeType.Method
    public EntityPredicate withEntityType(final MCEntityType type) {
        this.entityType = type;
        return this;
    }

    @ZenCodeType.Method
    public EntityPredicate withEntityTypeTag(final MCTag<MCEntityType> tag) {
        this.entityTypeTag = tag;
        return this;
    }

    @ZenCodeType.Method
    public EntityPredicate withAbsoluteDistanceBounds(final float min, final float max) {
        this.absoluteDistance = new FloatRange(min, max);
        return this;
    }

    @ZenCodeType.Method
    public EntityPredicate withHorizontalDistanceBounds(final float min, final float max) {
        this.horizontalDistance = new FloatRange(min, max);
        return this;
    }

    @ZenCodeType.Method
    public EntityPredicate withXBounds(final float min, final float max) {
        this.xDistance = new FloatRange(min, max);
        return this;
    }

    @ZenCodeType.Method
    public EntityPredicate withYBounds(final float min, final float max) {
        this.yDistance = new FloatRange(min, max);
        return this;
    }

    @ZenCodeType.Method
    public EntityPredicate withZBounds(final float min, final float max) {
        this.zDistance = new FloatRange(min, max);
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
    public EntityPredicate withEffect(final Effect effect, final Consumer<EffectData> builder) {
        final EffectData data = new EffectData();
        builder.accept(data);
        this.effects.put(effect, data);
        return this;
    }

    @ZenCodeType.Method
    public EntityPredicate withData(final IData data) {
        this.data = data;
        return this;
    }

    @ZenCodeType.Method
    public EntityPredicate withBurningState() {
        this.isBurning = TriState.TRUE;
        return this;
    }

    @ZenCodeType.Method
    public EntityPredicate withoutBurningState() {
        this.isBurning = TriState.FALSE;
        return this;
    }

    @ZenCodeType.Method
    public EntityPredicate withSneakingState() {
        this.isSneaking = TriState.TRUE;
        return this;
    }

    @ZenCodeType.Method
    public EntityPredicate withoutSneakingState() {
        this.isSneaking = TriState.FALSE;
        return this;
    }

    @ZenCodeType.Method
    public EntityPredicate withSprintingState() {
        this.isSprinting = TriState.TRUE;
        return this;
    }

    @ZenCodeType.Method
    public EntityPredicate withoutSprintingState() {
        this.isSprinting = TriState.FALSE;
        return this;
    }

    @ZenCodeType.Method
    public EntityPredicate withSwimmingState() {
        this.isSwimming = TriState.TRUE;
        return this;
    }

    @ZenCodeType.Method
    public EntityPredicate withoutSwimmingState() {
        this.isSwimming = TriState.FALSE;
        return this;
    }

    @ZenCodeType.Method
    public EntityPredicate withBaby() {
        this.isBaby = TriState.TRUE;
        return this;
    }

    @ZenCodeType.Method
    public EntityPredicate withAdult() {
        this.isBaby = TriState.FALSE;
        return this;
    }

    @ZenCodeType.Method
    public EntityPredicate withHeadItem(final Consumer<ItemPredicate> builder) {
        final ItemPredicate head = new ItemPredicate();
        builder.accept(head);
        this.head = head;
        return this;
    }

    @ZenCodeType.Method
    public EntityPredicate withChestItem(final Consumer<ItemPredicate> builder) {
        final ItemPredicate chest = new ItemPredicate();
        builder.accept(chest);
        this.chest = chest;
        return this;
    }

    @ZenCodeType.Method
    public EntityPredicate withLegsItem(final Consumer<ItemPredicate> builder) {
        final ItemPredicate legs = new ItemPredicate();
        builder.accept(legs);
        this.legs = legs;
        return this;
    }

    @ZenCodeType.Method
    public EntityPredicate withFeetItem(final Consumer<ItemPredicate> builder) {
        final ItemPredicate feet = new ItemPredicate();
        builder.accept(feet);
        this.feet = feet;
        return this;
    }

    @ZenCodeType.Method
    public EntityPredicate withItemInHand(final Consumer<ItemPredicate> builder) {
        return this.withItemInMainHand(builder);
    }

    @ZenCodeType.Method
    public EntityPredicate withItemInMainHand(final Consumer<ItemPredicate> builder) {
        final ItemPredicate mainHand = new ItemPredicate();
        builder.accept(mainHand);
        this.mainHand = mainHand;
        return this;
    }

    @ZenCodeType.Method
    public EntityPredicate withItemInOffHand(final Consumer<ItemPredicate> builder) {
        final ItemPredicate offHand = new ItemPredicate();
        builder.accept(offHand);
        this.offHand = offHand;
        return this;
    }

    @ZenCodeType.Method
    public EntityPredicate withPlayerData(final Consumer<PlayerPredicate> builder) {
        final PlayerPredicate predicate = new PlayerPredicate();
        builder.accept(predicate);
        this.player = predicate;
        return this;
    }

    @ZenCodeType.Method
    public EntityPredicate withFishingHookProperties(final Consumer<FishingPredicate> builder) {
        final FishingPredicate predicate = new FishingPredicate();
        builder.accept(predicate);
        this.fishing = predicate;
        return this;
    }

    @ZenCodeType.Method
    public EntityPredicate withRiddenEntity(final Consumer<EntityPredicate> builder) {
        final EntityPredicate riddenEntity = new EntityPredicate();
        builder.accept(riddenEntity);
        this.riddenEntity = riddenEntity;
        return this;
    }

    @ZenCodeType.Method
    public EntityPredicate withTargetedEntity(final Consumer<EntityPredicate> builder) {
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

    boolean isAny() {
        return this.entityType == null && this.entityTypeTag == null && this.absoluteDistance == null && this.horizontalDistance == null
                && this.xDistance == null && this.yDistance == null && this.zDistance == null && this.locationPredicate.isAny()
                && this.effects.isEmpty() && this.data == null && this.isBurning == TriState.UNSET && this.isSneaking == TriState.UNSET
                && this.isSprinting == TriState.UNSET && this.isSwimming == TriState.UNSET && this.isBaby == TriState.UNSET
                && this.head.isAny() && this.chest.isAny() && this.legs.isAny() && this.feet.isAny() && this.mainHand.isAny()
                && this.offHand.isAny() && this.player.isAny() && this.fishing.isAny() && (this.riddenEntity == null || this.riddenEntity.isAny())
                && (this.targetedEntity == null || this.targetedEntity.isAny()) && this.team == null && this.catType == null;
    }

    public net.minecraft.advancements.criterion.EntityPredicate toVanilla() {
        if (this.entityType != null && this.entityTypeTag != null) {
            throw new IllegalStateException("'EntityPredicate' cannot supply both a single entity type and a tag.");
        }
        if (this.data != null && !(this.data instanceof MapData)) {
            throw new IllegalStateException("Data specified in an 'EntityPredicate' must be a map");
        }
        if (this.isAny()) return net.minecraft.advancements.criterion.EntityPredicate.ANY;

        final net.minecraft.advancements.criterion.EntityPredicate.Builder builder = net.minecraft.advancements.criterion.EntityPredicate.Builder.create();

        if (this.entityType != null) builder.type(this.entityType.getInternal());
        if (this.entityTypeTag != null) builder.type(this.toVanilla(this.entityTypeTag));
        builder.distance(this.toVanilla(this.absoluteDistance, this.horizontalDistance, this.xDistance, this.yDistance, this.zDistance));
        builder.location(this.locationPredicate.toVanilla());
        builder.effects(this.toVanilla(this.effects));
        if (this.data != null) builder.nbt(this.toVanilla(this.data));
        builder.flags(this.toVanilla(this.isBurning, this.isSneaking, this.isSprinting, this.isSwimming, this.isBaby));
        builder.equipment(this.toVanilla(this.head, this.chest, this.legs, this.feet, this.mainHand, this.offHand));
        builder.player(this.player.toVanilla());
        builder.fishing(this.fishing.toVanilla());
        builder.mount(this.riddenEntity.toVanilla());
        builder.target(this.targetedEntity.toVanilla());
        builder.team(this.team);
        builder.catTypeOrNull(this.catType);

        return builder.build();
    }

    @SuppressWarnings("unchecked")
    private <T, U> ITag<T> toVanilla(final MCTag<U> tag) {
        return (ITag<T>) tag.getInternal();
    }

    private DistancePredicate toVanilla(final FloatRange abs, final FloatRange hor, final FloatRange x, final FloatRange y, final FloatRange z) {
        if (abs == null && hor == null && x == null && y == null && z == null) return DistancePredicate.ANY;
        return new DistancePredicate(this.toVanilla(x), this.toVanilla(y), this.toVanilla(z), this.toVanilla(hor), this.toVanilla(abs));
    }

    private MinMaxBounds.FloatBound toVanilla(final FloatRange bound) {
        if (bound == null) return MinMaxBounds.FloatBound.UNBOUNDED;
        return bound.toVanillaFloatBound();
    }

    private MobEffectsPredicate toVanilla(final Map<Effect, EffectData> map) {
        if (map.isEmpty()) return MobEffectsPredicate.ANY;
        return new MobEffectsPredicate(map.entrySet().stream().map(this::toVanilla).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    private Map.Entry<Effect, MobEffectsPredicate.InstancePredicate> toVanilla(final Map.Entry<Effect, EffectData> entry) {
        return new AbstractMap.SimpleImmutableEntry<>(entry.getKey(), entry.getValue().toVanillaEffectsPredicate());
    }

    private NBTPredicate toVanilla(final IData data) {
        return new NBTPredicate(((MapData) data).getInternal()); // Safe otherwise we would have thrown already
    }

    private EntityFlagsPredicate toVanilla(final TriState burn, final TriState sneak, final TriState sprint, final TriState swim, final TriState baby) {
        if (burn == TriState.UNSET && sneak == TriState.UNSET && sprint == TriState.UNSET && swim == TriState.UNSET && baby == TriState.UNSET) {
            return EntityFlagsPredicate.ALWAYS_TRUE;
        }
        return new EntityFlagsPredicate(burn.toBoolean(), sneak.toBoolean(), sprint.toBoolean(), swim.toBoolean(), baby.toBoolean());
    }

    private EntityEquipmentPredicate toVanilla(final ItemPredicate head, final ItemPredicate chest, final ItemPredicate legs, final ItemPredicate feet,
                                               final ItemPredicate hand, final ItemPredicate otherHand) {
        if (head.isAny() && chest.isAny() && legs.isAny() && feet.isAny() && hand.isAny() && otherHand.isAny()) {
            return EntityEquipmentPredicate.ANY;
        }
        return new EntityEquipmentPredicate(head.toVanilla(), chest.toVanilla(), legs.toVanilla(), feet.toVanilla(), hand.toVanilla(), otherHand.toVanilla());
    }
}
