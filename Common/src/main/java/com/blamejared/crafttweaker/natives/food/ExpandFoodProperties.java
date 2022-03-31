package com.blamejared.crafttweaker.natives.food;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.mixin.common.access.food.AccessFoodPropertiesBuilder;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

@ZenRegister
@Document("vanilla/api/food/FoodProperties")
@NativeTypeRegistration(value = FoodProperties.class, zenCodeName = "crafttweaker.api.food.FoodProperties")
public class ExpandFoodProperties {
    
    @ZenCodeType.StaticExpansionMethod
    public static FoodProperties create(int nutrition, float saturationModifier) {
        
        return new FoodProperties.Builder().nutrition(nutrition).saturationMod(saturationModifier).build();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("nutrition")
    public static int getNutrition(FoodProperties internal) {
        
        return internal.getNutrition();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("nutrition")
    public static FoodProperties setNutrition(FoodProperties internal, int nutrition) {
        
        return getbuilder(internal).nutrition(nutrition).build();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("saturationModifier")
    public static float getSaturationModifier(FoodProperties internal) {
        
        return internal.getSaturationModifier();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("saturationModifier")
    public static FoodProperties setSaturationModifier(FoodProperties internal, float saturationModifier) {
        
        return getbuilder(internal).saturationMod(saturationModifier).build();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isMeat")
    public static boolean isMeat(FoodProperties internal) {
        
        return internal.isMeat();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("isMeat")
    public static FoodProperties setIsMeat(FoodProperties internal, boolean isMeat) {
        
        FoodProperties.Builder builder = getbuilder(internal);
        ((AccessFoodPropertiesBuilder) builder).crafttweaker$setIsMeat(isMeat);
        return builder.build();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("canAlwaysEat")
    public static boolean canAlwaysEat(FoodProperties internal) {
        
        return internal.canAlwaysEat();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("canAlwaysEat")
    public static FoodProperties setCanAlwaysEat(FoodProperties internal, boolean canAlwaysEat) {
        
        FoodProperties.Builder builder = getbuilder(internal);
        ((AccessFoodPropertiesBuilder) builder).crafttweaker$setCanAlwaysEat(canAlwaysEat);
        return builder.build();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isFastFood")
    public static boolean isFastFood(FoodProperties internal) {
        
        return internal.isFastFood();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("isFastFood")
    public static FoodProperties setIsFastFood(FoodProperties internal, boolean fastFood) {
        
        FoodProperties.Builder builder = getbuilder(internal);
        ((AccessFoodPropertiesBuilder) builder).crafttweaker$setFastFood(fastFood);
        return builder.build();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("effects")
    public static List<Pair<MobEffectInstance, Float>> getEffects(FoodProperties internal) {
        
        return internal.getEffects();
    }
    
    @ZenCodeType.Method
    public static FoodProperties addEffect(FoodProperties internal, MobEffectInstance effect, float probability) {
        
        FoodProperties.Builder builder = getbuilder(internal);
        ((AccessFoodPropertiesBuilder) builder).crafttweaker$getEffects().add(Pair.of(effect, probability));
        return builder.build();
    }
    
    @ZenCodeType.Method
    public static FoodProperties removeEffect(FoodProperties internal, MobEffectInstance effect) {
        
        FoodProperties.Builder builder = getbuilder(internal);
        ((AccessFoodPropertiesBuilder) builder).crafttweaker$getEffects().removeIf(pair -> pair.getFirst().equals(effect));
        return builder.build();
    }
    
    private static FoodProperties.Builder getbuilder(FoodProperties internal) {
        
        FoodProperties.Builder builder = new FoodProperties.Builder();
        if(internal.isMeat()) {
            builder.meat();
        }
        if(internal.isFastFood()) {
            builder.fast();
        }
        builder.saturationMod(internal.getSaturationModifier());
        builder.nutrition(internal.getNutrition());
        for(Pair<MobEffectInstance, Float> effect : internal.getEffects()) {
            builder.effect(effect.getFirst(), effect.getSecond());
        }
        return builder;
    }
    
}
