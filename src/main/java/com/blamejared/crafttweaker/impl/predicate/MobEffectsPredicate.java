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

/**
 * Represents a predicate for an {@link net.minecraft.entity.Entity}'s current effects.
 *
 * This predicate is able to verify the presence of one or multiple {@link Effect}s on the entity, along with verifying
 * that their {@link EffectData} is within some specified constraints. The predicate is <strong>not</strong> able to
 * check the absence of certain effects, and will also require the specified effects to be present on the entity.
 *
 * By default, any effect that is on the entity will match the predicate.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.predicate.MobEffectsPredicate")
@Document("vanilla/api/predicate/MobEffectsPredicate")
public final class MobEffectsPredicate extends IVanillaWrappingPredicate.AnyDefaulting<net.minecraft.advancements.criterion.MobEffectsPredicate> {
    
    private final Map<Effect, EffectData> effects;
    
    public MobEffectsPredicate() {
        
        super(net.minecraft.advancements.criterion.MobEffectsPredicate.ANY);
        this.effects = new LinkedHashMap<>();
    }
    
    /**
     * Adds an {@link Effect} to the ones that should be present on the entity, along with the {@link EffectData} it
     * should have.
     *
     * If the same effect had already been added to the map with a different set of effect data, then the previous
     * configuration is replaced. Otherwise the addition completes normally.
     *
     * @param effect  The effect that should be present on the entity.
     * @param builder A consumer to configure the {@link EffectData} for the given effect.
     *
     * @return This predicate for chaining.
     */
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
                        .map(it -> new AbstractMap.SimpleImmutableEntry<>(it.getKey(), it.getValue()
                                .toVanillaPredicate()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
        );
    }
    
}
