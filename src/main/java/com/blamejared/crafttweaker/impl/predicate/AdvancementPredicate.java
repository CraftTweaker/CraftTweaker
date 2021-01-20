package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.minecraft.advancements.criterion.PlayerPredicate;
import org.openzen.zencode.java.ZenCodeType;

import java.util.LinkedHashMap;
import java.util.Map;

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

    @ZenCodeType.Method
    public AdvancementPredicate withCompleted() {
        this.passed = TriState.TRUE;
        return this;
    }

    @ZenCodeType.Method
    public AdvancementPredicate withInProgress() {
        this.passed = TriState.FALSE;
        return this;
    }

    @ZenCodeType.Method
    public AdvancementPredicate withPassedCriterion(final String name) {
        this.criteria.put(name, true);
        return this;
    }

    @ZenCodeType.Method
    public AdvancementPredicate withInProgressCriterion(final String name) {
        this.criteria.put(name, false);
        return this;
    }

    public boolean isAny() {
        return this.passed == TriState.UNSET && this.criteria.isEmpty();
    }

    @Override
    public net.minecraft.advancements.criterion.PlayerPredicate.IAdvancementPredicate toVanillaPredicate() {
        if (this.passed != TriState.UNSET && !this.criteria.isEmpty()) {
            throw new IllegalStateException("An advancement can be checked only for full completion or criteria, not both");
        }
        if (this.passed != TriState.UNSET) {
            return new net.minecraft.advancements.criterion.PlayerPredicate.CompletedAdvancementPredicate(this.passed.toBoolean());
        } else {
            return new net.minecraft.advancements.criterion.PlayerPredicate.CriteriaPredicate(this.toVanilla(this.criteria));
        }
    }

    private Object2BooleanMap<String> toVanilla(final Map<String, Boolean> map) {
        final Object2BooleanMap<String> vanilla = new Object2BooleanOpenHashMap<>();
        map.forEach((k, v) -> vanilla.put(k, v.booleanValue()));
        return vanilla;
    }
}
