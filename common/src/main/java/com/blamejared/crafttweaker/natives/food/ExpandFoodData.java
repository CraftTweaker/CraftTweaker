package com.blamejared.crafttweaker.natives.food;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.food.FoodData;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/food/FoodData")
@NativeTypeRegistration(value = FoodData.class, zenCodeName = "crafttweaker.api.food.FoodData")
public class ExpandFoodData {
    
    @ZenCodeType.Method
    public static void eat(FoodData internal, int foodLevelModifier, float saturationLevelModifier) {
        
        internal.eat(foodLevelModifier, saturationLevelModifier);
    }
    
    @ZenCodeType.Method
    public static void eat(FoodData internal, IItemStack stack) {
        
        internal.eat(stack.getInternal().getItem(), stack.getInternal());
    }
    
    @ZenCodeType.Method
    public static int getFoodLevel(FoodData internal) {
        
        return internal.getFoodLevel();
    }
    
    @ZenCodeType.Method
    public static int getLastFoodLevel(FoodData internal) {
        
        return internal.getLastFoodLevel();
    }
    
    @ZenCodeType.Method
    public static boolean needsFood(FoodData internal) {
        
        return internal.needsFood();
    }
    
    @ZenCodeType.Method
    public static void addExhaustion(FoodData internal, float exhaustion) {
        
        internal.addExhaustion(exhaustion);
    }
    
    @ZenCodeType.Method
    public static float getExhaustionLevel(FoodData internal) {
        
        return internal.getExhaustionLevel();
    }
    
    @ZenCodeType.Method
    public static float getSaturationLevel(FoodData internal) {
        
        return internal.getSaturationLevel();
    }
    
    @ZenCodeType.Method
    public static void setFoodLevel(FoodData internal, int foodLevel) {
        
        internal.setFoodLevel(foodLevel);
    }
    
    @ZenCodeType.Method
    public static void setSaturation(FoodData internal, float saturationLevel) {
        
        internal.setSaturation(saturationLevel);
    }
    
    @ZenCodeType.Method
    public static void setExhaustion(FoodData internal, float exhaustionLevel) {
        
        internal.setExhaustion(exhaustionLevel);
    }
    
}
