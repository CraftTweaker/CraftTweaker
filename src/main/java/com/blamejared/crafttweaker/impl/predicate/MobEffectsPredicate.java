package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.potion.Effect;
import org.openzen.zencode.java.ZenCodeType;

import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.predicate.MobEffectsPredicate")
@Document("vanilla/api/predicate/MobEffectsPredicate")
public final class MobEffectsPredicate extends IVanillaWrappingPredicate.AnyDefaulting<net.minecraft.advancements.criterion.MobEffectsPredicate> {
    private final Map<Effect, EffectData> effects;

    public MobEffectsPredicate() {
        super(net.minecraft.advancements.criterion.MobEffectsPredicate.ANY);
        this.effects = new LinkedHashMap<>();
    }

    @ZenCodeType.Method
    public MobEffectsPredicate withEffect(final Effect effect, final Consumer<EffectData> builder) {
        final EffectData data = new EffectData();
        builder.accept(data);
        this.effects.put(effect, data);
        return this;
    }

    @Override
    public boolean isAny() {
        return this.effects.isEmpty();
    }

    @Override
    public net.minecraft.advancements.criterion.MobEffectsPredicate toVanilla() {
        return new net.minecraft.advancements.criterion.MobEffectsPredicate(
                this.effects.entrySet().stream()
                        .map(it -> new AbstractMap.SimpleImmutableEntry<>(it.getKey(), it.getValue().toVanillaPredicate()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
        );
    }
}
