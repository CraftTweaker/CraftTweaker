package com.blamejared.crafttweaker.impl.loot.conditions.vanilla;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.conditions.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.ILootConditionTypeBuilder;
import com.blamejared.crafttweaker.impl.predicate.IntRangePredicate;
import com.blamejared.crafttweaker.impl.predicate.TargetedEntity;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import org.openzen.zencode.java.ZenCodeType;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

/**
 * Builder to create an 'EntityScores' loot condition.
 *
 * This condition checks the {@link net.minecraft.loot.LootContext} for an {@link Entity} that matches the targeted one,
 * specified via {@link TargetedEntity}, and then tests its scoreboard properties against a set of values.
 *
 * The condition passes if and only if there exists a valid entity to target and all the specified scoreboard entries
 * are within the given bounds.
 *
 * A valid 'EntityScores' loot condition has to specify a targeted entity. A well-formed 'EntityScores' condition must
 * also specify at least one scoreboard property to check against. Not specifying any scoreboard values makes the
 * condition simply act as a way to check for the presence of an entity of the given type.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.vanilla.EntityScores")
@Document("vanilla/api/loot/conditions/vanilla/EntityScores")
public final class EntityScoresLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    
    private final Map<String, IntRangePredicate> ranges;
    private TargetedEntity targetedEntity;
    
    EntityScoresLootConditionTypeBuilder() {
        
        this.ranges = new LinkedHashMap<>();
    }
    
    /**
     * Sets the entity that should be targeted by the loot condition.
     *
     * Refer to {@link TargetedEntity} for a full list and their respective meaning.
     *
     * This parameter is <strong>required</strong>.
     *
     * @param entity The entity that should be targeted.
     *
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public EntityScoresLootConditionTypeBuilder withTargetedEntity(final TargetedEntity entity) {
        
        this.targetedEntity = entity;
        return this;
    }
    
    /**
     * Adds the scoreboard property <code>name</code> to the ones that should be checked, setting its minimum accepted
     * value to <code>min</code>.
     *
     * If the scoreboard property had already some bounds specified, then the minimum value of the bound will be
     * overwritten with the new value specified in <code>min</code>. On the other hand, if the scoreboard property
     * didn't have any specified bounds, the minimum value is set, leaving the upper end unbounded.
     *
     * The minimum value is inclusive, meaning that a value that is equal to <code>min</code> will pass the check.
     *
     * This parameter is <strong>optional</strong>.
     *
     * @param name The name of the scoreboard property to check.
     * @param min  The minimum value the scoreboard property can have.
     *
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public EntityScoresLootConditionTypeBuilder withMinimumScore(final String name, final int min) {
        
        this.ranges.put(name, IntRangePredicate.mergeLowerBound(this.ranges.get(name), min));
        return this;
    }
    
    /**
     * Adds the scoreboard property <code>name</code> to the ones that should be checked, setting its maximum accepted
     * value to <code>max</code>.
     *
     * If the scoreboard property had already some bounds specified, then the maximum value of the bound will be
     * overwritten with the new value specified in <code>max</code>. On the other hand, if the scoreboard property
     * didn't have any specified bounds, the maximum value is set, leaving the lower end unbounded.
     *
     * The maximum value is inclusive, meaning that a value that is equal to <code>max</code> will pass the check.
     *
     * This parameter is <strong>optional</strong>.
     *
     * @param name The name of the scoreboard property to check.
     * @param max  The maximum value the scoreboard property can have.
     *
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public EntityScoresLootConditionTypeBuilder withMaximumScore(final String name, final int max) {
        
        this.ranges.put(name, IntRangePredicate.mergeUpperBound(this.ranges.get(name), max));
        return this;
    }
    
    /**
     * Adds the scoreboard property <code>name</code> to the ones that should be checked, setting both its minimum and
     * maximum accepted values respectively to <code>min</code> and <code>max</code>.
     *
     * If the scoreboard property had already some bounds specified, then the bounds will get completely overwritten
     * with the new values.
     *
     * Both minimum and maximum values are inclusive, meaning that a value that is equal to either <code>min</code> or
     * <code>max</code> will pass the check.
     *
     * This parameter is <strong>optional</strong>.
     *
     * @param name The name of the scoreboard property to check.
     * @param min  The minimum value the scoreboard property can have.
     * @param max  The maximum value the scoreboard property can have.
     *
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public EntityScoresLootConditionTypeBuilder withRangedScore(final String name, final int min, final int max) {
        
        this.ranges.put(name, IntRangePredicate.bounded(min, max));
        return this;
    }
    
    /**
     * Adds the scoreboard property <code>name</code> to the ones that should be checked, making sure that the values
     * matches exactly the given one.
     *
     * If the scoreboard property had already some bounds specified, then the bounds will get overwritten.
     *
     * This parameter is <strong>optional</strong>.
     *
     * @param name  The name of the scoreboard property to check.
     * @param value The exact value the scoreboard property must have.
     *
     * @return This builder for chaining.
     */
    @ZenCodeType.Method
    public EntityScoresLootConditionTypeBuilder withExactScore(final String name, final int value) {
        
        return this.withRangedScore(name, value, value);
    }
    
    @Override
    public ILootCondition finish() {
        
        if(this.targetedEntity == null) {
            throw new IllegalStateException("Targeted entity not defined for an 'EntityScores' condition");
        }
        if(this.ranges.isEmpty()) {
            CraftTweakerAPI.logWarning("An 'EntityScores' condition has an empty set of scores to check: this will always match!");
        }
        
        final List<BiPredicate<Entity, Scoreboard>> matchers = this.ranges
                .entrySet()
                .stream()
                .map(this::convertToMatcher)
                .collect(Collectors.toList());
        
        return context -> {
            final Entity entity = this.targetedEntity.getLootContextDiscriminator().apply(context);
            if(entity == null) {
                return false;
            }
            
            final Scoreboard scoreboard = entity.getEntityWorld().getScoreboard();
            return matchers.stream().allMatch(it -> it.test(entity, scoreboard));
        };
    }
    
    private BiPredicate<Entity, Scoreboard> convertToMatcher(final Map.Entry<String, IntRangePredicate> entry) {
        
        return this.makeMatcher(entry.getKey(), entry.getValue());
    }
    
    private BiPredicate<Entity, Scoreboard> makeMatcher(final String name, final IntRangePredicate range) {
        
        return (entity, scoreboard) -> {
            final ScoreObjective objective = scoreboard.getObjective(name);
            if(objective == null) {
                return false;
            }
            
            final String scoreboardName = entity.getScoreboardName();
            return scoreboard.entityHasObjective(scoreboardName, objective) && range.match(scoreboard.getOrCreateScore(scoreboardName, objective)
                    .getScorePoints());
        };
    }
    
}
