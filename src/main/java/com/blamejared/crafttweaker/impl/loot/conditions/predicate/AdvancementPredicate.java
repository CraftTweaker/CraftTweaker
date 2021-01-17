package com.blamejared.crafttweaker.impl.loot.conditions.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.loot.conditions.TriState;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import org.openzen.zencode.java.ZenCodeType;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;
import java.util.Map;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.conditions.predicate.AdvancementPredicate")
@Document("vanilla/api/loot/conditions/predicate/AdvancementPredicate")
public final class AdvancementPredicate {
    private static final MethodHandle COMPLETED_CONSTRUCTOR;
    private static final MethodHandle CRITERIA_CONSTRUCTOR;

    private final Map<String, Boolean> criteria;

    private TriState passed;

    public AdvancementPredicate() {
        this.criteria = new LinkedHashMap<>();
        this.passed = TriState.UNSET;
    }

    static {
        try {
            final MethodHandles.Lookup lookup = MethodHandles.lookup();

            final Class<?> completedClass = Class.forName("net.minecraft.advancements.criterion.PlayerPredicate$CompletedAdvancementPredicate");
            final Class<?> criteriaClass = Class.forName("net.minecraft.advancements.criterion.PlayerPredicate$CriteriaPredicate");

            final Constructor<?> completedConstructor = completedClass.getDeclaredConstructor(boolean.class);
            final Constructor<?> criteriaConstructor = criteriaClass.getDeclaredConstructor(Object2BooleanMap.class);

            completedConstructor.setAccessible(true);
            criteriaConstructor.setAccessible(true);

            COMPLETED_CONSTRUCTOR = lookup.unreflectConstructor(completedConstructor);
            CRITERIA_CONSTRUCTOR = lookup.unreflectConstructor(criteriaConstructor);
        } catch (final ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
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

    boolean isAny() {
        return this.passed == TriState.UNSET && this.criteria.isEmpty();
    }

    Object toVanilla() {
        if (this.passed != TriState.UNSET && !this.criteria.isEmpty()) {
            throw new IllegalStateException("An advancement can be checked only for full completion or criteria, not both");
        }
        try {
            if (this.passed != TriState.UNSET) {
                //noinspection UnnecessaryUnboxing
                return COMPLETED_CONSTRUCTOR.invoke(this.passed.toBoolean().booleanValue());
            } else {
                return CRITERIA_CONSTRUCTOR.invoke(this.toVanilla(this.criteria));
            }
        } catch (final Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private Object2BooleanMap<String> toVanilla(final Map<String, Boolean> map) {
        final Object2BooleanMap<String> vanilla = new Object2BooleanOpenHashMap<>();
        map.forEach((k, v) -> vanilla.put(k, v.booleanValue()));
        return vanilla;
    }
}
