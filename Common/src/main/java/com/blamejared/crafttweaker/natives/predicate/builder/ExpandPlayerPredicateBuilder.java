package com.blamejared.crafttweaker.natives.predicate.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.PlayerPredicate;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatType;
import net.minecraft.world.level.GameType;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Map;

@ZenRegister
@Document("vanilla/api/predicate/builder/PlayerPredicateBuilder")
@NativeTypeRegistration(value = PlayerPredicate.Builder.class, zenCodeName = "crafttweaker.api.predicate.builder.PlayerPredicateBuilder")
public final class ExpandPlayerPredicateBuilder {
    
    @ZenCodeType.Method
    public static PlayerPredicate.Builder level(final PlayerPredicate.Builder internal, final MinMaxBounds.Ints level) {
        
        return internal.setLevel(level);
    }
    
    @ZenCodeType.Method
    public static PlayerPredicate.Builder statistic(final PlayerPredicate.Builder internal, final ResourceLocation type, final ResourceLocation name, final MinMaxBounds.Ints value) {
        
        final StatType<?> statType = BuiltInRegistries.STAT_TYPE.getOrThrow(ResourceKey.create(Registries.STAT_TYPE, type));
        return internal.addStat(statistic(statType, name), value);
    }
    
    @ZenCodeType.Method
    public static PlayerPredicate.Builder statistic(final PlayerPredicate.Builder internal, final ResourceLocation type, final String name, final MinMaxBounds.Ints value) {
        
        return statistic(internal, type, new ResourceLocation(name), value);
    }
    
    @ZenCodeType.Method
    public static PlayerPredicate.Builder statistic(final PlayerPredicate.Builder internal, final String type, final ResourceLocation name, final MinMaxBounds.Ints value) {
        
        return statistic(internal, new ResourceLocation(type), name, value);
    }
    
    @ZenCodeType.Method
    public static PlayerPredicate.Builder statistic(final PlayerPredicate.Builder internal, final String type, final String name, final MinMaxBounds.Ints value) {
        
        return statistic(internal, type, new ResourceLocation(name), value);
    }
    
    @ZenCodeType.Method
    public static PlayerPredicate.Builder recipe(final PlayerPredicate.Builder internal, final ResourceLocation name, @ZenCodeType.OptionalBoolean(true) final boolean unlocked) {
        
        return internal.addRecipe(name, unlocked);
    }
    
    @ZenCodeType.Method
    public static PlayerPredicate.Builder recipe(final PlayerPredicate.Builder internal, final String name, @ZenCodeType.OptionalBoolean(true) final boolean unlocked) {
        
        return recipe(internal, new ResourceLocation(name), unlocked);
    }
    
    @ZenCodeType.Method
    public static PlayerPredicate.Builder gameType(final PlayerPredicate.Builder internal, final GameType type) {
        
        return internal.setGameType(type);
    }
    
    @ZenCodeType.Method
    public static PlayerPredicate.Builder lookingAt(final PlayerPredicate.Builder internal, final EntityPredicate predicate) {
        
        return internal.setLookingAt(predicate);
    }
    
    @ZenCodeType.Method
    public static PlayerPredicate.Builder lookingAt(final PlayerPredicate.Builder internal, final EntityPredicate.Builder predicate) {
        
        return lookingAt(internal, predicate.build());
    }
    
    @ZenCodeType.Method
    public static PlayerPredicate.Builder advancement(final PlayerPredicate.Builder internal, final ResourceLocation name, @ZenCodeType.OptionalBoolean(true) final boolean completed) {
        
        return internal.checkAdvancementDone(name, completed);
    }
    
    @ZenCodeType.Method
    public static PlayerPredicate.Builder advancement(final PlayerPredicate.Builder internal, final String name, @ZenCodeType.OptionalBoolean(true) final boolean completed) {
        
        return advancement(internal, new ResourceLocation(name), completed);
    }
    
    @ZenCodeType.Method
    public static PlayerPredicate.Builder advancementCriteria(final PlayerPredicate.Builder internal, final ResourceLocation name, final Map<String, Boolean> criteria) {
        // TODO("Boxing and unboxing might be broken due to boolean -> Boolean; check with ZenDev")
        return internal.checkAdvancementCriterions(name, criteria); // sic
    }
    
    @ZenCodeType.Method
    public static PlayerPredicate.Builder advancementCriteria(final PlayerPredicate.Builder internal, final String name, final Map<String, Boolean> criteria) {
        
        return advancementCriteria(internal, new ResourceLocation(name), criteria);
    }
    
    @ZenCodeType.Method
    public static PlayerPredicate.Builder advancementCriterion(final PlayerPredicate.Builder internal, final ResourceLocation name,
                                                               final String criterion, @ZenCodeType.OptionalBoolean(true) final boolean completed) {
        
        return advancementCriteria(internal, name, Map.of(criterion, completed));
    }
    
    @ZenCodeType.Method
    public static PlayerPredicate.Builder advancementCriterion(final PlayerPredicate.Builder internal, final String name, final String criterion,
                                                               @ZenCodeType.OptionalBoolean(true) final boolean completed) {
        
        return advancementCriterion(internal, new ResourceLocation(name), criterion, completed);
    }
    
    @ZenCodeType.Method
    public static PlayerPredicate build(final PlayerPredicate.Builder internal) {
        
        return internal.build();
    }
    
    private static <T> Stat<T> statistic(final StatType<T> type, final ResourceLocation name) {
        
        return type.get(type.getRegistry().getOrThrow(ResourceKey.create(type.getRegistry().key(), name)));
    }
    
}
