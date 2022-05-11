package com.blamejared.crafttweaker.natives.food;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.mixin.common.access.food.AccessFoodProperties;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.function.Consumer;

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
        
        return accessibleModify(internal, accessFoodProperties -> accessFoodProperties.crafttweaker$setNutrition(nutrition));
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("saturationModifier")
    public static float getSaturationModifier(FoodProperties internal) {
        
        return internal.getSaturationModifier();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("saturationModifier")
    public static FoodProperties setSaturationModifier(FoodProperties internal, float saturationModifier) {
        
        return accessibleModify(internal, accessFoodProperties -> accessFoodProperties.crafttweaker$setSaturationModifier(saturationModifier));
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isMeat")
    public static boolean isMeat(FoodProperties internal) {
        
        return internal.isMeat();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("isMeat")
    public static FoodProperties setIsMeat(FoodProperties internal, boolean isMeat) {
        
        return accessibleModify(internal, accessFoodProperties -> accessFoodProperties.crafttweaker$setIsMeat(isMeat));
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("canAlwaysEat")
    public static boolean canAlwaysEat(FoodProperties internal) {
        
        return internal.canAlwaysEat();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("canAlwaysEat")
    public static FoodProperties setCanAlwaysEat(FoodProperties internal, boolean canAlwaysEat) {
        
        return accessibleModify(internal, accessFoodProperties -> accessFoodProperties.crafttweaker$setCanAlwaysEat(canAlwaysEat));
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isFastFood")
    public static boolean isFastFood(FoodProperties internal) {
        
        return internal.isFastFood();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("isFastFood")
    public static FoodProperties setIsFastFood(FoodProperties internal, boolean fastFood) {
        
        return accessibleModify(internal, accessFoodProperties -> accessFoodProperties.crafttweaker$setFastFood(fastFood));
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("effects")
    public static List<Pair<MobEffectInstance, Float>> getEffects(FoodProperties internal) {
        
        return internal.getEffects();
    }
    
    @ZenCodeType.Method
    public static FoodProperties addEffect(FoodProperties internal, MobEffectInstance effect, float probability) {
        
        return modify(internal, properties -> Services.PLATFORM.addFoodPropertiesEffect(properties, effect, probability));
    }
    
    @ZenCodeType.Method
    public static FoodProperties removeEffect(FoodProperties internal, MobEffectInstance effect) {
        
        return modify(internal, properties -> Services.PLATFORM.removeFoodPropertiesEffect(properties, effect));
    }
    
    private static FoodProperties accessibleModify(FoodProperties properties, Consumer<AccessFoodProperties> propertyMutator) {
        
        FoodProperties copy = AccessFoodProperties.crafttweaker$createFoodProperties(properties.getNutrition(), properties.getSaturationModifier(), properties.isMeat(), properties.canAlwaysEat(), properties.isFastFood(), properties.getEffects());
        propertyMutator.accept((AccessFoodProperties) copy);
        return copy;
    }
    
    private static FoodProperties modify(FoodProperties properties, Consumer<FoodProperties> propertyMutator) {
        
        FoodProperties copy = AccessFoodProperties.crafttweaker$createFoodProperties(properties.getNutrition(), properties.getSaturationModifier(), properties.isMeat(), properties.canAlwaysEat(), properties.isFastFood(), properties.getEffects());
        propertyMutator.accept(copy);
        return copy;
    }
    
}
