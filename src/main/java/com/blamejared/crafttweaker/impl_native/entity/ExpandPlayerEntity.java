package com.blamejared.crafttweaker.impl_native.entity;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.util.text.*;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import org.openzen.zencode.java.ZenCodeType;

import java.util.UUID;

@ZenRegister
@Document("vanilla/api/entity/MCPlayerEntity")
@NativeTypeRegistration(value = PlayerEntity.class, zenCodeName = "crafttweaker.api.player.MCPlayerEntity")
public class ExpandPlayerEntity {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("fake")
    public static boolean isFake(PlayerEntity internal) {
        return internal instanceof FakePlayer;
    }
    
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
    
    @ZenCodeType.Method
    public static void sendMessage(PlayerEntity internal, MCTextComponent text) {
        internal.sendMessage(text.getInternal(), CraftTweaker.CRAFTTWEAKER_UUID);
    }
    
}
