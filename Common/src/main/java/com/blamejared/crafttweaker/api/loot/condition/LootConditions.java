package com.blamejared.crafttweaker.api.loot.condition;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;
import java.util.function.Predicate;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.condition.LootConditions")
@Document("vanilla/api/loot/condition/LootConditions")
@SuppressWarnings("ClassCanBeRecord")
public class LootConditions {
    
    private final Predicate<LootContext> gather;
    
    private LootConditions(final Predicate<LootContext> gather) {
        
        this.gather = gather;
    }
    
    @ZenCodeType.Method
    public static LootConditions none() {
        
        return new LootConditions(it -> true);
    }
    
    @ZenCodeType.Method
    public static LootConditions randomlyIn(final double percentageChance) {
        
        return new LootConditions(it -> it.getRandom().nextDouble() <= percentageChance);
    }
    
    @ZenCodeType.Method
    public static LootConditions only(final LootItemCondition condition) {
        
        return new LootConditions(condition);
    }
    
    @ZenCodeType.Method
    public static LootConditions only(final LootItemCondition.Builder builder) {
        
        return only(builder.build());
    }
    
    @ZenCodeType.Method
    public static LootConditions allOf(final LootItemCondition... conditions) {
        
        return new LootConditions(LootItemConditions.andConditions(conditions));
    }
    
    @ZenCodeType.Method
    public static LootConditions allOf(final LootItemCondition.Builder... builders) {
        
        return allOf(Arrays.stream(builders).map(LootItemCondition.Builder::build).toArray(LootItemCondition[]::new));
    }
    
    @ZenCodeType.Method
    public static LootConditions anyOf(final LootItemCondition... conditions) {
        
        return new LootConditions(LootItemConditions.orConditions(conditions));
    }
    
    @ZenCodeType.Method
    public static LootConditions anyOf(final LootItemCondition.Builder... builders) {
        
        return anyOf(Arrays.stream(builders).map(LootItemCondition.Builder::build).toArray(LootItemCondition[]::new));
    }
    
    @ZenCodeType.Method
    public static LootConditions noneOf(final LootItemCondition... conditions) {
        
        return allOf(conditions).flip();
    }
    
    @ZenCodeType.Method
    public static LootConditions noneOf(final LootItemCondition.Builder... builders) {
        
        return noneOf(Arrays.stream(builders).map(LootItemCondition.Builder::build).toArray(LootItemCondition[]::new));
    }
    
    @ZenCodeType.Method
    public static LootConditions notAllOf(final LootItemCondition... conditions) {
        
        return anyOf(conditions).flip();
    }
    
    @ZenCodeType.Method
    public static LootConditions notAllOf(final LootItemCondition.Builder... builders) {
        
        return notAllOf(Arrays.stream(builders)
                .map(LootItemCondition.Builder::build)
                .toArray(LootItemCondition[]::new));
    }
    
    public Predicate<LootContext> gather() {
        
        return this.gather;
    }
    
    private LootConditions flip() {
        
        return new LootConditions(this.gather.negate());
    }
    
}
