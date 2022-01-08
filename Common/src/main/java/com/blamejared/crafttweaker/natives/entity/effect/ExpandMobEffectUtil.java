package com.blamejared.crafttweaker.natives.entity.effect;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.LivingEntity;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/effect/MobEffectUtil")
@NativeTypeRegistration(value = MobEffectUtil.class, zenCodeName = "crafttweaker.api.entity.effect.MobEffectUtil")
public class ExpandMobEffectUtil {
    
    @ZenCodeType.StaticExpansionMethod
    public static String formatDuration(MobEffectInstance instance, float durationFactor) {
        
        return MobEffectUtil.formatDuration(instance, durationFactor);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static boolean hasDigSpeed(LivingEntity entity) {
        
        return MobEffectUtil.hasDigSpeed(entity);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static int getDigSpeedAmplification(LivingEntity entity) {
        
        return MobEffectUtil.getDigSpeedAmplification(entity);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static boolean hasWaterBreathing(LivingEntity entity) {
        
        return MobEffectUtil.hasWaterBreathing(entity);
    }
    
}
