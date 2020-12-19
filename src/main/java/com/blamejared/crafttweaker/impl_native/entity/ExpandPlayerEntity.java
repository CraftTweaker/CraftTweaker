package com.blamejared.crafttweaker.impl_native.entity;

import com.blamejared.crafttweaker_annotations.annotations.NativeExpansion;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.DocumentAsType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@DocumentAsType
@NativeExpansion(PlayerEntity.class)
public class ExpandPlayerEntity {
    
    @ZenCodeType.Method
    public static boolean isSecondaryUseActive(PlayerEntity internal) {
        return internal.isSecondaryUseActive();
    }
    
    @ZenCodeType.Method
    public static int getMaxInPortalTime(PlayerEntity internal) {
        return internal.getMaxInPortalTime();
    }
    
    @ZenCodeType.Method
    public static int getPortalCooldown(PlayerEntity internal) {
        return internal.getPortalCooldown();
    }
    
    @ZenCodeType.Method
    public static boolean drop(PlayerEntity internal, boolean p_225609_1_) {
        return internal.drop(p_225609_1_);
    }
    
    @ZenCodeType.Method
    public static boolean isUser(PlayerEntity internal) {
        return internal.isUser();
    }
    
    @ZenCodeType.Method
    public static void wakeUp(PlayerEntity internal) {
        internal.wakeUp();
    }
    
    @ZenCodeType.Method
    public static int getSleepTimer(PlayerEntity internal) {
        return internal.getSleepTimer();
    }
    
    @ZenCodeType.Method
    public static void unlockRecipes(PlayerEntity internal, ResourceLocation[] p_193102_1_) {
        internal.unlockRecipes(p_193102_1_);
    }
    
    @ZenCodeType.Method
    public static boolean isSpectator(PlayerEntity internal) {
        return internal.isSpectator();
    }
    
    @ZenCodeType.Method
    public static boolean isSwimming(PlayerEntity internal) {
        return internal.isSwimming();
    }
    
    @ZenCodeType.Method
    public static boolean isCreative(PlayerEntity internal) {
        return internal.isCreative();
    }
    
    @ZenCodeType.Method
    public static boolean isPushedByWater(PlayerEntity internal) {
        return internal.isPushedByWater();
    }
    
    @ZenCodeType.Method
    public static float getLuck(PlayerEntity internal) {
        return internal.getLuck();
    }
    
    @ZenCodeType.Method
    public static boolean canUseCommandBlock(PlayerEntity internal) {
        return internal.canUseCommandBlock();
    }
}
