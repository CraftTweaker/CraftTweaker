package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.GameType;
import net.minecraftforge.registries.ForgeRegistries;
import org.openzen.zencode.java.ZenCodeType;

import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.predicate.PlayerPredicate")
@Document("vanilla/api/predicate/PlayerPredicate")
public final class PlayerPredicate extends IVanillaWrappingPredicate.AnyDefaulting<net.minecraft.advancements.criterion.PlayerPredicate> {
    private final Map<Pair<ResourceLocation, ResourceLocation>, IntRangePredicate> statistics;
    private final Map<String, Boolean> recipes;
    private final Map<String, AdvancementPredicate> advancements;

    private IntRangePredicate experienceLevels;
    private GameMode gameMode;

    public PlayerPredicate() {
        super(net.minecraft.advancements.criterion.PlayerPredicate.ANY);
        this.statistics = new LinkedHashMap<>();
        this.recipes = new LinkedHashMap<>();
        this.advancements = new LinkedHashMap<>();
        this.experienceLevels = IntRangePredicate.unbounded();
    }

    @ZenCodeType.Method
    public PlayerPredicate withMinimumExperienceLevel(final int min) {
        this.experienceLevels = IntRangePredicate.mergeLowerBound(this.experienceLevels, min);
        return this;
    }

    @ZenCodeType.Method
    public PlayerPredicate withMaximumExperienceLevel(final int max) {
        this.experienceLevels = IntRangePredicate.mergeUpperBound(this.experienceLevels, max);
        return this;
    }

    @ZenCodeType.Method
    public PlayerPredicate withBoundedExperienceLevel(final int min, final int max) {
        this.experienceLevels = IntRangePredicate.bounded(min, max);
        return this;
    }

    @ZenCodeType.Method
    public PlayerPredicate withExactExperienceLevel(final int level) {
        return this.withBoundedExperienceLevel(level, level);
    }

    @ZenCodeType.Method
    public PlayerPredicate withGameMode(final GameMode mode) {
        this.gameMode = mode;
        return this;
    }

    @ZenCodeType.Method
    public PlayerPredicate withMinimumStatistic(final ResourceLocation type, final ResourceLocation name, final int min) {
        final Pair<ResourceLocation, ResourceLocation> key = Pair.of(type, name);
        this.statistics.put(key, IntRangePredicate.mergeLowerBound(this.statistics.get(key), min));
        return this;
    }

    @ZenCodeType.Method
    public PlayerPredicate withMaximumStatistic(final ResourceLocation type, final ResourceLocation name, final int max) {
        final Pair<ResourceLocation, ResourceLocation> key = Pair.of(type, name);
        this.statistics.put(key, IntRangePredicate.mergeUpperBound(this.statistics.get(key), max));
        return this;
    }

    @ZenCodeType.Method
    public PlayerPredicate withBoundedStatistic(final ResourceLocation type, final ResourceLocation name, final int minValue, final int maxValue) {
        this.statistics.put(Pair.of(type, name), IntRangePredicate.bounded(minValue, maxValue));
        return this;
    }

    @ZenCodeType.Method
    public PlayerPredicate withExactStatistic(final ResourceLocation type, final ResourceLocation name, final int value) {
        return this.withBoundedStatistic(type, name, value, value);
    }

    @ZenCodeType.Method
    public PlayerPredicate withUnlockedRecipe(final String name) {
        this.recipes.put(name, true);
        return this;
    }

    @ZenCodeType.Method
    public PlayerPredicate withLockedRecipe(final String name) {
        this.recipes.put(name, false);
        return this;
    }

    @ZenCodeType.Method
    public PlayerPredicate withAdvancementPredicate(final String advancement, final Consumer<AdvancementPredicate> builder) {
        final AdvancementPredicate predicate = new AdvancementPredicate();
        builder.accept(predicate);
        this.advancements.put(advancement, predicate);
        return this;
    }

    @Override
    public boolean isAny() {
        return this.experienceLevels.isAny() && this.gameMode == null && this.statistics.isEmpty() && this.recipes.isEmpty()
                && (this.advancements.isEmpty() || this.advancements.values().stream().allMatch(AdvancementPredicate::isAny));
    }

    public net.minecraft.advancements.criterion.PlayerPredicate toVanilla() {
        if (this.isAny()) return net.minecraft.advancements.criterion.PlayerPredicate.ANY;
        try {
            return new net.minecraft.advancements.criterion.PlayerPredicate(
                    this.experienceLevels.toVanillaPredicate(),
                    this.gameMode == null ? GameType.NOT_SET : this.gameMode.toGameType(),
                    this.toVanillaStats(this.statistics),
                    this.toVanillaRecipes(this.recipes),
                    this.toVanillaAdvancements(this.advancements)
            );
        } catch (final Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private Map<Stat<?>, MinMaxBounds.IntBound> toVanillaStats(final Map<Pair<ResourceLocation, ResourceLocation>, IntRangePredicate> map) {
        return map.entrySet().stream().map(this::toVanillaStat).filter(Objects::nonNull).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map.Entry<Stat<?>, MinMaxBounds.IntBound> toVanillaStat(final Map.Entry<Pair<ResourceLocation, ResourceLocation>, IntRangePredicate> entry) {
        final StatType<?> type = ForgeRegistries.STAT_TYPES.getValue(entry.getKey().getFirst());
        if (type == null) return null;
        final Stat<?> stat = this.toStat(type, entry.getKey().getSecond());
        if (stat == null) return null;
        return new AbstractMap.SimpleImmutableEntry<>(stat, entry.getValue().toVanillaPredicate());
    }

    private <T> Stat<T> toStat(final StatType<T> type, final ResourceLocation name) {
        final T thing = type.getRegistry().getOrDefault(name);
        if (thing == null) return null;
        return type.get(thing);
    }

    private Object2BooleanMap<ResourceLocation> toVanillaRecipes(final Map<String, Boolean> map) {
        final Object2BooleanMap<ResourceLocation> vanilla = new Object2BooleanOpenHashMap<>();
        map.forEach((k, v) -> vanilla.put(new ResourceLocation(k), v.booleanValue()));
        return vanilla;
    }

    private Map<ResourceLocation, net.minecraft.advancements.criterion.PlayerPredicate.IAdvancementPredicate> toVanillaAdvancements(final Map<String, AdvancementPredicate> predicateMap) {
        return predicateMap.entrySet().stream()
                .filter(it -> !it.getValue().isAny())
                .map(it -> new AbstractMap.SimpleImmutableEntry<>(new ResourceLocation(it.getKey()), it.getValue().toVanillaPredicate()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
