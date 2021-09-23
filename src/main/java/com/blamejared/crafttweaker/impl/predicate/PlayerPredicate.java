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

/**
 * Represents a predicate for a player, as a specialization of {@link EntityPredicate}.
 *
 * This predicate can be used to check various properties of the player entity, such as the game mode, experience,
 * unlocked advancements and recipes, or statistics.
 *
 * By default, the entity passes the checks without caring about the entity type. If at least one of the properties is
 * set, then the entity must be a player to pass the checks.
 */
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
    
    /**
     * Sets the minimum value the experience level should be to <code>min</code>.
     *
     * If the experience level had already some bounds specified, then the minimum value of the bound will be
     * overwritten with the value specified in <code>min</code>. On the other hand, if the experience level didn't have
     * any bounds set, the minimum is set, leaving the upper end unbounded.
     *
     * The minimum value is inclusive, meaning that a value that is equal to <code>min</code> will pass the check.
     *
     * @param min The minimum value the experience level should be.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public PlayerPredicate withMinimumExperienceLevel(final int min) {
        
        this.experienceLevels = IntRangePredicate.mergeLowerBound(this.experienceLevels, min);
        return this;
    }
    
    /**
     * Sets the maximum value the experience level should be to <code>max</code>.
     *
     * If the experience level had already some bounds specified, then the maximum value of the bound will be
     * overwritten with the value specified in <code>max</code>. On the other hand, if the experience level didn't have
     * any bounds set, the maximum is set, leaving the lower end unbounded.
     *
     * The maximum value is inclusive, meaning that a value that is equal to <code>max</code> will pass the check.
     *
     * @param max The maximum value the experience level should be.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public PlayerPredicate withMaximumExperienceLevel(final int max) {
        
        this.experienceLevels = IntRangePredicate.mergeUpperBound(this.experienceLevels, max);
        return this;
    }
    
    /**
     * Sets both the minimum and maximum value the experience level should be to <code>min</code> and <code>max</code>
     * respectively.
     *
     * If the experience level had already some bounds specified, then they will be overwritten with the new values.
     *
     * Both minimum and maximum values are inclusive, meaning that a value that is equal to either <code>min</code> or
     * <code>max</code> will pass the check.
     *
     * @param min The minimum value the experience level should be.
     * @param max The maximum value the experience level should be.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public PlayerPredicate withBoundedExperienceLevel(final int min, final int max) {
        
        this.experienceLevels = IntRangePredicate.bounded(min, max);
        return this;
    }
    
    /**
     * Sets the experience level to exactly match the given <code>value</code>.
     *
     * If the experience level had already some bounds specified, then they will be overwritten with the new value.
     *
     * @param level The exact value the experience level should be.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public PlayerPredicate withExactExperienceLevel(final int level) {
        
        return this.withBoundedExperienceLevel(level, level);
    }
    
    /**
     * Sets the {@link GameMode} the player has to be in.
     *
     * @param mode The game mode.
     *
     * @return This player for chaining.
     */
    @ZenCodeType.Method
    public PlayerPredicate withGameMode(final GameMode mode) {
        
        this.gameMode = mode;
        return this;
    }
    
    /**
     * Sets the minimum value the statistic should be to <code>min</code>.
     *
     * If the statistic had already some bounds specified, then the minimum value of the bound will be overwritten with
     * the value specified in <code>min</code>. On the other hand, if the statistic didn't have any bounds set, the
     * minimum is set, leaving the upper end unbounded.
     *
     * The minimum value is inclusive, meaning that a value that is equal to <code>min</code> will pass the check.
     *
     * @param type The statistic's base type.
     * @param name The statistic's unique identifier.
     * @param min  The minimum value the statistic should be.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public PlayerPredicate withMinimumStatistic(final ResourceLocation type, final ResourceLocation name, final int min) {
        
        final Pair<ResourceLocation, ResourceLocation> key = Pair.of(type, name);
        this.statistics.put(key, IntRangePredicate.mergeLowerBound(this.statistics.get(key), min));
        return this;
    }
    
    /**
     * Sets the maximum value the statistic should be to <code>max</code>.
     *
     * If the statistic had already some bounds specified, then the maximum value of the bound will be overwritten with
     * the value specified in <code>max</code>. On the other hand, if the statistic didn't have any bounds set, the
     * maximum is set, leaving the upper end unbounded.
     *
     * The maximum value is inclusive, meaning that a value that is equal to <code>max</code> will pass the check.
     *
     * @param type The statistic's base type.
     * @param name The statistic's unique identifier.
     * @param max  The maximum value the statistic should be.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public PlayerPredicate withMaximumStatistic(final ResourceLocation type, final ResourceLocation name, final int max) {
        
        final Pair<ResourceLocation, ResourceLocation> key = Pair.of(type, name);
        this.statistics.put(key, IntRangePredicate.mergeUpperBound(this.statistics.get(key), max));
        return this;
    }
    
    /**
     * Sets both the minimum and maximum value the statistic should be to <code>minValue</code> and
     * <code>maxValue</code> respectively.
     *
     * If the statistic had already some bounds specified, then they will be overwritten with the new values.
     *
     * Both minimum and maximum values are inclusive, meaning that a value that is equal to either <code>min</code> or
     * <code>max</code> will pass the check.
     *
     * @param type     The statistic's base type.
     * @param name     The statistic's unique identifier.
     * @param minValue The minimum value the statistic should be.
     * @param maxValue The maximum value the statistic should be.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public PlayerPredicate withBoundedStatistic(final ResourceLocation type, final ResourceLocation name, final int minValue, final int maxValue) {
        
        this.statistics.put(Pair.of(type, name), IntRangePredicate.bounded(minValue, maxValue));
        return this;
    }
    
    /**
     * Sets the statistic to exactly match the given <code>value</code>.
     *
     * If the statistic had already some bounds specified, then they will be overwritten with the new value.
     *
     * @param type  The statistic's base type.
     * @param name  The statistic's unique identifier.
     * @param value The exact value the statistic should be.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public PlayerPredicate withExactStatistic(final ResourceLocation type, final ResourceLocation name, final int value) {
        
        return this.withBoundedStatistic(type, name, value, value);
    }
    
    /**
     * Adds the recipe <code>name</code> to the list of recipes that have to be unlocked.
     *
     * If the predicate had already been set to check for this recipe's locked status, the setting is overwritten.
     *
     * @param name The name of the recipe that needs to be unlocked.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public PlayerPredicate withUnlockedRecipe(final String name) {
        
        this.recipes.put(name, true);
        return this;
    }
    
    /**
     * Adds the recipe <code>name</code> to the list of recipes that have to be locked.
     *
     * If the predicate had already been set to check for this recipe's unlocked status, the setting is overwritten.
     *
     * @param name The name of the recipe that needs to be locked.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public PlayerPredicate withLockedRecipe(final String name) {
        
        this.recipes.put(name, false);
        return this;
    }
    
    /**
     * Adds an advancement to the ones that should be checked, along with the {@link AdvancementPredicate} that should
     * be used to validate it.
     *
     * If the same advancement had already been added to the map with a different predicate, then the previous
     * configuration is replaced. Otherwise the addition completes normally.
     *
     * @param advancement The advancement that should be checked.
     * @param builder     A consumer to configure the {@link AdvancementPredicate} for the given advancement.
     *
     * @return This predicate for chaining.
     */
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
                && (this.advancements.isEmpty() || this.advancements.values()
                .stream()
                .allMatch(AdvancementPredicate::isAny));
    }
    
    public net.minecraft.advancements.criterion.PlayerPredicate toVanilla() {
    
        if(this.isAny()) {
            return net.minecraft.advancements.criterion.PlayerPredicate.ANY;
        }
        try {
            return new net.minecraft.advancements.criterion.PlayerPredicate(
                    this.experienceLevels.toVanillaPredicate(),
                    this.gameMode == null ? GameType.NOT_SET : this.gameMode.toGameType(),
                    this.toVanillaStats(this.statistics),
                    this.toVanillaRecipes(this.recipes),
                    this.toVanillaAdvancements(this.advancements)
            );
        } catch(final Throwable e) {
            throw new RuntimeException(e);
        }
    }
    
    private Map<Stat<?>, MinMaxBounds.IntBound> toVanillaStats(final Map<Pair<ResourceLocation, ResourceLocation>, IntRangePredicate> map) {
        
        return map.entrySet()
                .stream()
                .map(this::toVanillaStat)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    
    private Map.Entry<Stat<?>, MinMaxBounds.IntBound> toVanillaStat(final Map.Entry<Pair<ResourceLocation, ResourceLocation>, IntRangePredicate> entry) {
        
        final StatType<?> type = ForgeRegistries.STAT_TYPES.getValue(entry.getKey().getFirst());
        if(type == null) {
            return null;
        }
        final Stat<?> stat = this.toStat(type, entry.getKey().getSecond());
        if(stat == null) {
            return null;
        }
        return new AbstractMap.SimpleImmutableEntry<>(stat, entry.getValue().toVanillaPredicate());
    }
    
    private <T> Stat<T> toStat(final StatType<T> type, final ResourceLocation name) {
        
        final T thing = type.getRegistry().getOrDefault(name);
        if(thing == null) {
            return null;
        }
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
                .map(it -> new AbstractMap.SimpleImmutableEntry<>(new ResourceLocation(it.getKey()), it.getValue()
                        .toVanillaPredicate()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    
}
