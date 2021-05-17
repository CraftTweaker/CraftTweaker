package com.blamejared.crafttweaker.impl_native.entity;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.data.MapData;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.items.ItemHandlerHelper;
import org.openzen.zencode.java.ZenCodeType;

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
    @ZenCodeType.Getter("name")
    public static MCTextComponent getName(PlayerEntity internal) {
        
        return new MCTextComponent(internal.getName());
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
    
    @ZenCodeType.Method
    public static void sendStatusMessage(PlayerEntity internal, MCTextComponent text, boolean actionBar) {
        
        internal.sendStatusMessage(text.getInternal(), actionBar);
    }

    @ZenCodeType.Method
    public static IItemStack getCurrentItem(PlayerEntity internal) {

        return new MCItemStack(internal.inventory.getCurrentItem());
    }

    @ZenCodeType.Method
    public static IItemStack getInventoryItemStack(PlayerEntity internal, int slotIndex) {

        return new MCItemStack(internal.inventory.getStackInSlot(slotIndex));
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("foodLevel")
    public static int getFoodLevel(PlayerEntity internal) {
        
        return internal.getFoodStats().getFoodLevel();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("saturationLevel")
    public static float getSaturationLevel(PlayerEntity internal) {
        
        return internal.getFoodStats().getSaturationLevel();
    }
    
    @ZenCodeType.Method
    public static boolean needFood(PlayerEntity internal) {
        
        return internal.getFoodStats().needFood();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("foodLevel")
    public static void setFoodLevel(PlayerEntity internal, int value) {
        
        internal.getFoodStats().setFoodLevel(value);
    }
    
    @ZenCodeType.Method
    public static void addExhaustion(PlayerEntity internal, float exhaustion) {
        
        internal.addExhaustion(exhaustion);
    }
    
    /**
     * Gets the persisted NBT tag that is saved between deaths.
     * Many mods use this to keep track of if they have given the player an item or not.
     */
    @ZenCodeType.Method
    public static MapData getPersistentData(PlayerEntity internal) {
    
        return new MapData(internal.getPersistentData().getCompound(PlayerEntity.PERSISTED_NBT_TAG));
    }
    
    /**
     * Updates the player's persisted data that is saved between deaths.
     */
    @ZenCodeType.Method
    public static void updatePersistentData(PlayerEntity internal, MapData data) {
    
        CompoundNBT persistentData = internal.getPersistentData();
        if(persistentData.contains(PlayerEntity.PERSISTED_NBT_TAG, 10)) {
            persistentData.getCompound(PlayerEntity.PERSISTED_NBT_TAG).merge(data.getInternal());
        } else {
            persistentData.put(PlayerEntity.PERSISTED_NBT_TAG, data.getInternal());
        }
    }
    
    @ZenCodeType.Method
    public static void give(PlayerEntity internal, IItemStack stack) {
        
        ItemHandlerHelper.giveItemToPlayer(internal, stack.getInternal());
    }
}
