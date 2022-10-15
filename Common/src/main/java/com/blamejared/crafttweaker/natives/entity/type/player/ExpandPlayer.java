package com.blamejared.crafttweaker.natives.entity.type.player;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker.platform.helper.inventory.IInventoryWrapper;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.openzen.zencode.java.ZenCodeType;

import java.util.OptionalInt;

@ZenRegister
@Document("vanilla/api/entity/type/player/Player")
@NativeTypeRegistration(value = Player.class, zenCodeName = "crafttweaker.api.entity.type.player.Player")
public class ExpandPlayer {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("abilities")
    public static Abilities getAbilities(Player internal) {
        
        return internal.getAbilities();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isSecondaryUseActive")
    public static boolean isSecondaryUseActive(Player internal) {
        
        return internal.isSecondaryUseActive();
    }
    
    @ZenCodeType.Method
    public static void playNotifySound(Player internal, SoundEvent event, SoundSource source, float volume, float pitch) {
        
        internal.playNotifySound(event, source, volume, pitch);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("score")
    public static int getScore(Player internal) {
        
        return internal.getScore();
    }
    
    @ZenCodeType.Method
    public static void setScore(Player internal, int score) {
        
        internal.setScore(score);
    }
    
    @ZenCodeType.Method
    public static void increaseScore(Player internal, int score) {
        
        internal.increaseScore(score);
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    public static ItemEntity drop(Player internal, ItemStack stack, boolean traceItem) {
        
        return internal.drop(stack, traceItem);
    }
    
    @ZenCodeType.Method
    public static float getDestroySpeed(Player internal, BlockState state) {
        
        return internal.getDestroySpeed(state);
    }
    
    @ZenCodeType.Method
    public static boolean hasCorrectToolForDrops(Player internal, BlockState state) {
        
        return internal.hasCorrectToolForDrops(state);
    }
    
    @ZenCodeType.Method
    public static boolean canHarmPlayer(Player internal, Player player) {
        
        return internal.canHarmPlayer(player);
    }
    
    @ZenCodeType.Method
    public static void attack(Player internal, Entity entity) {
        
        internal.attack(entity);
    }
    
    @ZenCodeType.Method
    public static void disableShield(Player internal, boolean usingAxe) {
        
        internal.disableShield(usingAxe);
    }
    
    @ZenCodeType.Method
    public static void crit(Player internal, Entity entity) {
        
        internal.crit(entity);
    }
    
    @ZenCodeType.Method
    public static void magicCrit(Player internal, Entity entity) {
        
        internal.magicCrit(entity);
    }
    
    @ZenCodeType.Method
    public static void sweepAttack(Player internal) {
        
        internal.sweepAttack();
    }
    
    @ZenCodeType.Method
    public static void respawn(Player internal) {
        
        internal.respawn();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isLocalPlayer")
    public static boolean isLocalPlayer(Player internal) {
        
        return internal.isLocalPlayer();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("inventory")
    public static Inventory getInventory(Player internal) {
        
        return internal.getInventory();
    }
    
    @ZenCodeType.Method
    public static void stopSleeping(Player internal) {
        
        internal.stopSleeping();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isSleepingLongEnough")
    public static boolean isSleepingLongEnough(Player internal) {
        
        return internal.isSleepingLongEnough();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("sleepTimer")
    public static int getSleepTimer(Player internal) {
        
        return internal.getSleepTimer();
    }
    
    @ZenCodeType.Method
    public static void displayClientMessage(Player internal, Component component, boolean actionBar) {
        
        internal.displayClientMessage(component, actionBar);
    }
    
    @ZenCodeType.Method
    public static void awardStat(Player internal, ResourceLocation stat) {
        
        internal.awardStat(stat);
    }
    
    @ZenCodeType.Method
    public static void awardStat(Player internal, ResourceLocation stat, int amount) {
        
        internal.awardStat(stat, amount);
    }
    
    @ZenCodeType.Method
    public static void jumpFromGround(Player internal) {
        
        internal.jumpFromGround();
    }
    
    @ZenCodeType.Method
    public static void giveExperiencePoints(Player internal, int amount) {
        
        internal.giveExperiencePoints(amount);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("enchantmentSeed")
    public static int getEnchantmentSeed(Player internal) {
        
        return internal.getEnchantmentSeed();
    }
    
    @ZenCodeType.Method
    public static void giveExperienceLevels(Player internal, int levels) {
        
        internal.giveExperienceLevels(levels);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("xpNeededForNextLevel")
    public static int getXpNeededForNextLevel(Player internal) {
        
        return internal.getXpNeededForNextLevel();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("experienceLevel")
    public static int getExperienceLevel(Player internal){
    
        return internal.experienceLevel;
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("experienceLevel")
    public static void setExperienceLevel(Player internal, int level){
        
        internal.experienceLevel = level;
    }
    
    @ZenCodeType.Method
    public static void causeFoodExhaustion(Player internal, float exhaustion) {
        
        internal.causeFoodExhaustion(exhaustion);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("foodData")
    public static FoodData getFoodData(Player internal) {
        
        return internal.getFoodData();
    }
    
    @ZenCodeType.Method
    public static boolean canEat(Player internal, boolean ignoreHunger) {
        
        return internal.canEat(ignoreHunger);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isHurt")
    public static boolean isHurt(Player internal) {
        
        return internal.isHurt();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("mayBuild")
    public static boolean mayBuild(Player internal) {
        
        return internal.mayBuild();
    }
    
    @ZenCodeType.Method
    public static boolean addItem(Player internal, ItemStack stack) {
        
        return internal.addItem(stack);
    }
    
    @ZenCodeType.Method
    public static boolean setEntityOnShoulder(Player internal, MapData entityData) {
        
        return internal.setEntityOnShoulder(entityData.getInternal());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isCreative")
    public static boolean isCreative(Player internal) {
        
        return internal.isCreative();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isReducedDebugInfo")
    public static boolean isReducedDebugInfo(Player internal) {
        
        return internal.isReducedDebugInfo();
    }
    
    @ZenCodeType.Method
    public static void setReducedDebugInfo(Player internal, boolean reducedDebugInfo) {
        
        internal.setReducedDebugInfo(reducedDebugInfo);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("mainArm")
    public static HumanoidArm getMainArm(Player internal) {
        
        return internal.getMainArm();
    }
    
    @ZenCodeType.Method
    public static void setMainArm(Player internal, HumanoidArm arm) {
        
        internal.setMainArm(arm);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("shoulderEntityLeft")
    public static MapData getShoulderEntityLeft(Player internal) {
        
        return new MapData(internal.getShoulderEntityLeft());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("shoulderEntityRight")
    public static MapData getShoulderEntityRight(Player internal) {
        
        return new MapData(internal.getShoulderEntityRight());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("currentItemAttackStrengthDelay")
    public static float getCurrentItemAttackStrengthDelay(Player internal) {
        
        return internal.getCurrentItemAttackStrengthDelay();
    }
    
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("cooldowns")
    public static ItemCooldowns getCooldowns(Player internal) {
        
        return internal.getCooldowns();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("luck")
    public static float getLuck(Player internal) {
        
        return internal.getLuck();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("canUseGameMasterBlocks")
    public static boolean canUseGameMasterBlocks(Player internal) {
        
        return internal.canUseGameMasterBlocks();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isScoping")
    public static boolean isScoping(Player internal) {
        
        return internal.isScoping();
    }
    
    @ZenCodeType.Method
    public static void give(Player internal, IItemStack stack, @ZenCodeType.OptionalInt(-1) int slot) {
        
        if(stack.isEmpty()) {
            return;
        }
        
        IInventoryWrapper inventory = Services.PLATFORM.getPlayerInventory(internal);
        Level level = internal.level;
        // How much *wasn't* inserted?
        ItemStack leftOvers = inventory.insertItem(slot, stack.getInternal(), false);
        OptionalInt availableSlot = inventory.getSlotFor(leftOvers);
        
        // While there is an available slot, and items still to insert, insert them
        while(availableSlot.isPresent() && !leftOvers.isEmpty()) {
            leftOvers = inventory.insertItem(availableSlot.getAsInt(), leftOvers, false);
            availableSlot = inventory.getSlotFor(leftOvers);
        }
        
        if(leftOvers.isEmpty() || leftOvers.getCount() != stack.getInternal().getCount()) {
            // Magic numbers from AdvancementRewards
            level.playSound(null, internal.getX(), internal.getY(), internal.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, ((level.random.nextFloat() - level.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
        }
        
        // Drop into the world
        if(!leftOvers.isEmpty() && !level.isClientSide()) {
            ItemEntity itemEntity = internal.drop(leftOvers, true, true);
            if(itemEntity != null) {
                itemEntity.setNoPickUpDelay();
            }
        }
    }
    
    @ZenCodeType.Method
    public static void sendMessage(Player internal, Component text) {
        
        internal.sendSystemMessage(text);
    }
    
}
