package com.blamejared.crafttweaker.impl.loot.conditions;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.loot.ILootCondition;
import com.blamejared.crafttweaker.impl.loot.conditions.predicate.TargetedEntity;
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
@ZenCodeType.Name("crafttweaker.api.loot.conditions.EntityScores")
@Document("vanilla/api/loot/conditions/EntityScores")
public final class EntityScoresLootConditionTypeBuilder implements ILootConditionTypeBuilder {
    private static final class IntPair {
        private final int min;
        private final int max;

        private IntPair(final int min, final int max) {
            this.min = min;
            this.max = max;
        }

        public boolean match(final int target) {
            return this.min <= target && target <= this.max;
        }
    }

    private final Map<String, IntPair> ranges;
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
    public EntityScoresLootConditionTypeBuilder withScore(final String name, final int min, final int max) {
        this.ranges.put(name, new IntPair(min, max));
        return this;
    }

    @ZenCodeType.Method
    public EntityScoresLootConditionTypeBuilder withScore(final String name, final int value) {
        return this.withScore(name, value, value);
    }

    @Override
    public ILootCondition finish() {
        if (this.targetedEntity == null) {
            throw new IllegalStateException("Targeted entity not defined for an 'EntityScores' condition");
        }
        if (this.ranges.isEmpty()) {
            CraftTweakerAPI.logWarning("An 'EntityScores' condition has an empty set of scores to check: this will always pass!");
        }
        if (this.ranges.values().stream().anyMatch(it -> it.min > it.max)) {
            CraftTweakerAPI.logWarning("An 'EntityScores' condition has a score value whose minimum is higher than the maximum: this will never pass! Map: {}", this.ranges);
        }

        final List<BiPredicate<Entity, Scoreboard>> matchers = this.ranges
                .entrySet()
                .stream()
                .map(this::convertToMatcher)
                .collect(Collectors.toList());

        return context -> {
            final Entity entity = this.targetedEntity.getDiscriminator().apply(context).getInternal();
            if (entity == null) return false;

            final Scoreboard scoreboard = entity.getEntityWorld().getScoreboard();
            return matchers.stream().allMatch(it -> it.test(entity, scoreboard));
        };
    }

    private BiPredicate<Entity, Scoreboard> convertToMatcher(final Map.Entry<String, IntPair> entry) {
        return this.makeMatcher(entry.getKey(), entry.getValue());
    }

    private BiPredicate<Entity, Scoreboard> makeMatcher(final String name, final IntPair range) {
        return (entity, scoreboard) -> {
            final ScoreObjective objective = scoreboard.getObjective(name);
            if (objective == null) return false;

            final String scoreboardName = entity.getScoreboardName();
            return scoreboard.entityHasObjective(scoreboardName, objective) && range.match(scoreboard.getOrCreateScore(scoreboardName, objective).getScorePoints());
        };
    }
}
