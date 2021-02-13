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

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.vanilla.EntityScores")
@Document("vanilla/api/loot/conditions/vanilla/EntityScores")
public final class EntityScoresLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    private final Map<String, IntRangePredicate> ranges;
    private TargetedEntity targetedEntity;

    EntityScoresLootConditionTypeBuilder() {
        this.ranges = new LinkedHashMap<>();
    }

    @ZenCodeType.Method
    public EntityScoresLootConditionTypeBuilder withTargetedEntity(final TargetedEntity entity) {
        this.targetedEntity = entity;
        return this;
    }

    @ZenCodeType.Method
    public EntityScoresLootConditionTypeBuilder withMinimumScore(final String name, final int min) {
        this.ranges.put(name, IntRangePredicate.mergeLowerBound(this.ranges.get(name), min));
        return this;
    }

    @ZenCodeType.Method
    public EntityScoresLootConditionTypeBuilder withMaximumScore(final String name, final int max) {
        this.ranges.put(name, IntRangePredicate.mergeUpperBound(this.ranges.get(name), max));
        return this;
    }

    @ZenCodeType.Method
    public EntityScoresLootConditionTypeBuilder withRangedScore(final String name, final int min, final int max) {
        this.ranges.put(name, IntRangePredicate.bounded(min, max));
        return this;
    }

    @ZenCodeType.Method
    public EntityScoresLootConditionTypeBuilder withExactScore(final String name, final int value) {
        return this.withRangedScore(name, value, value);
    }

    @Override
    public ILootCondition finish() {
        if (this.targetedEntity == null) {
            throw new IllegalStateException("Targeted entity not defined for an 'EntityScores' condition");
        }
        if (this.ranges.isEmpty()) {
            CraftTweakerAPI.logWarning("An 'EntityScores' condition has an empty set of scores to check: this will always match!");
        }

        final List<BiPredicate<Entity, Scoreboard>> matchers = this.ranges
                .entrySet()
                .stream()
                .map(this::convertToMatcher)
                .collect(Collectors.toList());

        return context -> {
            final Entity entity = this.targetedEntity.getLootContextDiscriminator().apply(context);
            if (entity == null) return false;

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
            if (objective == null) return false;

            final String scoreboardName = entity.getScoreboardName();
            return scoreboard.entityHasObjective(scoreboardName, objective) && range.match(scoreboard.getOrCreateScore(scoreboardName, objective).getScorePoints());
        };
    }
}
