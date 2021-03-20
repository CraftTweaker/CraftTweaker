package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.minecraft.advancements.criterion.PlayerPredicate;
import org.openzen.zencode.java.ZenCodeType;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents a predicate around an advancement.
 *
 * This predicate can be used to check the completion status of the advancement, either specifying specific criteria to
 * check against, or the full advancement. It is not possible to check against both, since the two checks are
 * effectively incompatible with each other.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.predicate.AdvancementPredicate")
@Document("vanilla/api/predicate/AdvancementPredicate")
public final class AdvancementPredicate implements IVanillaWrappingPredicate<PlayerPredicate.IAdvancementPredicate> {
    private final Map<String, Boolean> criteria;

    private TriState passed;

    public AdvancementPredicate() {
        this.criteria = new LinkedHashMap<>();
        this.passed = TriState.UNSET;
    }

    /**
     * Specifies that the advancement must be completed to pass the check.
     *
     * If this predicate had already been set to check for an in-progress advancement status, this setting is
     * overwritten. If the predicate had been set to check for a specific set of criteria, the predicate will be in an
     * invalid state.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public AdvancementPredicate withCompleted() {
        this.passed = TriState.TRUE;
        return this;
    }

    /**
     * Specifies that the advancement must not be completed, that is being in-progress, to pass the check.
     *
     * If this predicate had already been set to check for completion, this setting is overwritten. If the predicate had
     * been set to check for a specific set of criteria, the predicate will be in an invalid state.
     *
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public AdvancementPredicate withInProgress() {
        this.passed = TriState.FALSE;
        return this;
    }

    /**
     * Adds the criterion <code>name</code> to the list of criteria to check for completion.
     *
     * If the predicate had already been set to check for this criterion's in-progress status, the setting is
     * overwritten. If the predicate had been set to check for a completion status, the predicate will be in an invalid
     * state.
     *
     * @param name The name of the criterion to check for completion.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public AdvancementPredicate withPassedCriterion(final String name) {
        this.criteria.put(name, true);
        return this;
    }

    /**
     * Adds the criterion <code>name</code> to the list of criteria to check for in-progress status.
     *
     * If the predicate had already been set to check for this criterion's completion, the setting is overwritten. If
     * the predicate had been set to check for a completion status, the predicate will be in an invalid state.
     *
     * @param name The name of the criterion to check for in-progress status.
     * @return This predicate for chaining.
     */
    @ZenCodeType.Method
    public AdvancementPredicate withInProgressCriterion(final String name) {
        this.criteria.put(name, false);
        return this;
    }

    public boolean isAny() {
        return this.passed.isUnset() && this.criteria.isEmpty();
    }

    @Override
    public PlayerPredicate.IAdvancementPredicate toVanillaPredicate() {
        if (!this.passed.isUnset() && !this.criteria.isEmpty()) {
            throw new IllegalStateException("An advancement can be checked only for full completion or criteria, not both");
        }
        if (!this.passed.isUnset()) {
            return new PlayerPredicate.CompletedAdvancementPredicate(this.passed.toBoolean());
        } else {
            return new PlayerPredicate.CriteriaPredicate(this.toVanilla(this.criteria));
        }
    }

    private Object2BooleanMap<String> toVanilla(final Map<String, Boolean> map) {
        final Object2BooleanMap<String> vanilla = new Object2BooleanOpenHashMap<>();
        map.forEach((k, v) -> vanilla.put(k, v.booleanValue()));
        return vanilla;
    }
}
