package com.blamejared.crafttweaker.impl.loot.conditions.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.loot.conditions.IntRange;
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

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.predicate.PlayerPredicate")
@Document("vanilla/api/loot/conditions/predicate/PlayerPredicate")
public final class PlayerPredicate {
    private final Map<Pair<ResourceLocation, ResourceLocation>, IntRange> statistics;
    private final Map<String, Boolean> recipes;
    private final Map<String, AdvancementPredicate> advancements;

    private IntRange experienceLevels;
    private String gameMode;

    public PlayerPredicate() {
        this.statistics = new LinkedHashMap<>();
        this.recipes = new LinkedHashMap<>();
        this.advancements = new LinkedHashMap<>();
    }

    @ZenCodeType.Method
    public PlayerPredicate withExperienceLevels(final int min, final int max) {
        this.experienceLevels = new IntRange(min, max);
        return this;
    }

    @ZenCodeType.Method
    public PlayerPredicate withExperienceLevel(final int level) {
        return this.withExperienceLevels(level, level);
    }

    @ZenCodeType.Method
    public PlayerPredicate withGameMode(final String mode) {
        this.gameMode = mode;
        return this;
    }

    @ZenCodeType.Method
    public PlayerPredicate withStatistic(final ResourceLocation type, final ResourceLocation name, final int minValue, final int maxValue) {
        this.statistics.put(Pair.of(type, name), new IntRange(minValue, maxValue));
        return this;
    }

    @ZenCodeType.Method
    public PlayerPredicate withStatistic(final ResourceLocation type, final ResourceLocation name, final int value) {
        return this.withStatistic(type, name, value, value);
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

    boolean isAny() {
        return this.experienceLevels == null && this.gameMode == null && this.statistics.isEmpty() && this.recipes.isEmpty() && this.advancements.isEmpty();
    }

    public net.minecraft.advancements.criterion.PlayerPredicate toVanilla() {
        if (this.isAny()) return net.minecraft.advancements.criterion.PlayerPredicate.ANY;
        try {
            return new net.minecraft.advancements.criterion.PlayerPredicate(
                    this.toVanilla(this.experienceLevels),
                    this.toVanilla(this.gameMode),
                    this.toVanillaStats(this.statistics),
                    this.toVanillaRecipes(this.recipes),
                    this.toVanillaAdvancements(this.advancements)
            );
        } catch (final Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private MinMaxBounds.IntBound toVanilla(final IntRange range) {
        return range == null? MinMaxBounds.IntBound.UNBOUNDED : range.toVanillaIntBound();
    }

    private GameType toVanilla(final String name) {
        return GameType.parseGameTypeWithDefault(name == null? "" : name, GameType.NOT_SET);
    }

    private Map<Stat<?>, MinMaxBounds.IntBound> toVanillaStats(final Map<Pair<ResourceLocation, ResourceLocation>, IntRange> map) {
        return map.entrySet().stream().map(this::toVanillaStat).filter(Objects::nonNull).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map.Entry<Stat<?>, MinMaxBounds.IntBound> toVanillaStat(final Map.Entry<Pair<ResourceLocation, ResourceLocation>, IntRange> entry) {
        final StatType<?> type = ForgeRegistries.STAT_TYPES.getValue(entry.getKey().getFirst());
        if (type == null) return null;
        final Stat<?> stat = this.toStat(type, entry.getKey().getSecond());
        if (stat == null) return null;
        return new AbstractMap.SimpleImmutableEntry<>(stat, this.toVanilla(entry.getValue()));
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
                .map(it -> new AbstractMap.SimpleImmutableEntry<>(new ResourceLocation(it.getKey()), it.getValue().toVanilla()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
