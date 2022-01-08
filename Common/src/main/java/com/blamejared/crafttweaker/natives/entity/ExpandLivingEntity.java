package com.blamejared.crafttweaker.natives.entity;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;

@ZenRegister
@Document("vanilla/api/entity/LivingEntity")
@NativeTypeRegistration(value = LivingEntity.class, zenCodeName = "crafttweaker.api.entity.LivingEntity")
public class ExpandLivingEntity {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("canBreatheUnderwater")
    public static boolean canBreatheUnderwater(LivingEntity internal) {
        
        return internal.canBreatheUnderwater();
    }
    
    @ZenCodeType.Method
    public static float getSwimAmount(LivingEntity internal, float partialTicks) {
        
        return internal.getSwimAmount(partialTicks);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("canBreatheUnderwater")
    public static boolean canSpawnSoulSpeedParticle(LivingEntity internal) {
        
        return internal.canSpawnSoulSpeedParticle();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isBaby")
    public static boolean isBaby(LivingEntity internal) {
        
        return internal.isBaby();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("scale")
    public static float getScale(LivingEntity internal) {
        
        return internal.getScale();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("random")
    public static Random getRandom(LivingEntity internal) {
        
        return internal.getRandom();
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    @ZenCodeType.Getter("lastHurtByMob")
    public static LivingEntity getLastHurtByMob(LivingEntity internal) {
        
        return internal.getLastHurtByMob();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("lastHurtByMobTimestamp")
    public static int getLastHurtByMobTimestamp(LivingEntity internal) {
        
        return internal.getLastHurtByMobTimestamp();
    }
    
    @ZenCodeType.Method
    public static void setLastHurtByPlayer(LivingEntity internal, @ZenCodeType.Nullable Player player) {
        
        internal.setLastHurtByPlayer(player);
    }
    
    @ZenCodeType.Method
    public static void setLastHurtByMob(LivingEntity internal, @ZenCodeType.Nullable LivingEntity entity) {
        
        internal.setLastHurtByMob(entity);
    }
    
    @Nullable
    @ZenCodeType.Method
    @ZenCodeType.Getter("lastHurtMob")
    public static LivingEntity getLastHurtMob(LivingEntity internal) {
        
        return internal.getLastHurtMob();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("lastHurtMobTimestamp")
    public static int getLastHurtMobTimestamp(LivingEntity internal) {
        
        return internal.getLastHurtMobTimestamp();
    }
    
    @ZenCodeType.Method
    public static void setLastHurtMob(LivingEntity internal, Entity entity) {
        
        internal.setLastHurtMob(entity);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("noActionTime")
    public static int getNoActionTime(LivingEntity internal) {
        
        return internal.getNoActionTime();
    }
    
    @ZenCodeType.Method
    public static void setNoActionTime(LivingEntity internal, int idleTime) {
        
        internal.setNoActionTime(idleTime);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("shouldDiscardFriction")
    public static boolean shouldDiscardFriction(LivingEntity internal) {
        
        return internal.shouldDiscardFriction();
    }
    
    @ZenCodeType.Method
    public static void setDiscardFriction(LivingEntity internal, boolean discardFriction) {
        
        internal.setDiscardFriction(discardFriction);
    }
    
    @ZenCodeType.Method
    public static double getVisibilityPercent(LivingEntity internal, @ZenCodeType.Nullable Entity lookingEntity) {
        
        return internal.getVisibilityPercent(lookingEntity);
    }
    
    @ZenCodeType.Method
    public static boolean canAttack(LivingEntity internal, LivingEntity target) {
        
        return internal.canAttack(target);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("canBeSeenAsEnemy")
    public static boolean canBeSeenAsEnemy(LivingEntity internal) {
        
        return internal.canBeSeenAsEnemy();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("canBeSeenByAnyone")
    public static boolean canBeSeenByAnyone(LivingEntity internal) {
        
        return internal.canBeSeenByAnyone();
    }
    
    @ZenCodeType.Method
    public static boolean removeAllEffects(LivingEntity internal) {
        
        return internal.removeAllEffects();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("activeEffects")
    public static Collection<MobEffectInstance> getActiveEffects(LivingEntity internal) {
        
        return internal.getActiveEffects();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("activeEFfectsMap")
    public static Map<MobEffect, MobEffectInstance> getActiveEffectsMap(LivingEntity internal) {
        
        return internal.getActiveEffectsMap();
    }
    
    @ZenCodeType.Method
    public static boolean hasEffect(LivingEntity internal, MobEffect effect) {
        
        return internal.hasEffect(effect);
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    public static MobEffectInstance getEffect(LivingEntity internal, MobEffect effect) {
        
        return internal.getEffect(effect);
    }
    
    @ZenCodeType.Method
    public static boolean addEffect(LivingEntity internal, MobEffectInstance effectInstance) {
        
        return internal.addEffect(effectInstance);
    }
    
    @ZenCodeType.Method
    public static boolean addEffect(LivingEntity internal, MobEffectInstance effectInstance, @ZenCodeType.Nullable Entity entity) {
        
        return internal.addEffect(effectInstance, entity);
    }
    
    @ZenCodeType.Method
    public static boolean canBeAffected(LivingEntity internal, MobEffectInstance effectInstance) {
        
        return internal.canBeAffected(effectInstance);
    }
    
    @ZenCodeType.Method
    public static void forceAddEffect(LivingEntity internal, MobEffectInstance effectInstance, @ZenCodeType.Nullable Entity entity) {
        
        internal.forceAddEffect(effectInstance, entity);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isInvertedHealAndHarm")
    public static boolean isInvertedHealAndHarm(LivingEntity internal) {
        
        return internal.isInvertedHealAndHarm();
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    public static MobEffectInstance removeEffectNoUpdate(LivingEntity internal, @ZenCodeType.Nullable MobEffect effect) {
        
        return internal.removeEffectNoUpdate(effect);
    }
    
    @ZenCodeType.Method
    public static boolean removeEffect(LivingEntity internal, MobEffect effect) {
        
        return internal.removeEffect(effect);
    }
    
    @ZenCodeType.Method
    public static void heal(LivingEntity internal, float amount) {
        
        internal.heal(amount);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("health")
    public static float getHealth(LivingEntity internal) {
        
        return internal.getHealth();
    }
    
    @ZenCodeType.Method
    public static void setHealth(LivingEntity internal, float health) {
        
        internal.setHealth(health);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isDeadOrDying")
    public static boolean isDeadOrDying(LivingEntity internal) {
        
        return internal.isDeadOrDying();
    }
    
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    @ZenCodeType.Getter("lastDamageSource")
    public static DamageSource getLastDamageSource(LivingEntity internal) {
        
        return internal.getLastDamageSource();
    }
    
    @ZenCodeType.Method
    public static boolean isDamageSourceBlocked(LivingEntity internal, DamageSource source) {
        
        return internal.isDamageSourceBlocked(source);
    }
    
    @ZenCodeType.Method
    public static void die(LivingEntity internal, DamageSource source) {
        
        internal.die(source);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("lootTable")
    public static ResourceLocation getLootTable(LivingEntity internal) {
        
        return internal.getLootTable();
    }
    
    @ZenCodeType.Method
    public static void knockback(LivingEntity internal, double x, double y, double z) {
        
        internal.knockback(x, y, z);
    }
    
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    @ZenCodeType.Getter("lastClimbablePos")
    public static BlockPos getLastClimbablePos(LivingEntity internal) {
        
        return internal.getLastClimbablePos().orElse(null);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("armorValue")
    public static int getArmorValue(LivingEntity internal) {
        
        return internal.getArmorValue();
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    @ZenCodeType.Getter("killCredit")
    public static LivingEntity getKillCredit(LivingEntity internal) {
        
        return internal.getKillCredit();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("maxHealth")
    public static float getMaxHealth(LivingEntity internal) {
        
        return internal.getMaxHealth();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("arrowCount")
    public static int getArrowCount(LivingEntity internal) {
        
        return internal.getArrowCount();
    }
    
    @ZenCodeType.Method
    public static void setArrowCount(LivingEntity internal, int count) {
        
        internal.setArrowCount(count);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("stingerCount")
    public static int getStingerCount(LivingEntity internal) {
        
        return internal.getStingerCount();
    }
    
    @ZenCodeType.Method
    public static void setStingerCount(LivingEntity internal, int count) {
        
        internal.setStingerCount(count);
    }
    
    @ZenCodeType.Method
    public static void swing(LivingEntity internal, InteractionHand hand) {
        
        internal.swing(hand);
    }
    
    @ZenCodeType.Method
    public static void swing(LivingEntity internal, InteractionHand hand, boolean updateSelf) {
        
        internal.swing(hand, updateSelf);
    }
    
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    public static AttributeInstance getAttribute(LivingEntity internal, Attribute attribute) {
        
        return internal.getAttribute(attribute);
    }
    
    @ZenCodeType.Method
    public static double getAttributeValue(LivingEntity internal, Attribute attribute) {
        
        return internal.getAttributeValue(attribute);
    }
    
    @ZenCodeType.Method
    public static double getAttributeBaseValue(LivingEntity internal, Attribute attribute) {
        
        return internal.getAttributeBaseValue(attribute);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("mobType")
    public static MobType getMobType(LivingEntity internal) {
        
        return internal.getMobType();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("mainHandItem")
    public static ItemStack getMainHandItem(LivingEntity internal) {
        
        return internal.getMainHandItem();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("offHandItem")
    public static ItemStack getOffhandItem(LivingEntity internal) {
        
        return internal.getOffhandItem();
    }
    
    @ZenCodeType.Method
    public static boolean isHolding(LivingEntity internal, Item item) {
        
        return internal.isHolding(item);
    }
    
    @ZenCodeType.Method
    public static boolean isHolding(LivingEntity internal, Predicate<ItemStack> predicate) {
        
        return internal.isHolding(predicate);
    }
    
    @ZenCodeType.Method
    public static ItemStack getItemInHand(LivingEntity internal, InteractionHand hand) {
        
        return internal.getItemInHand(hand);
    }
    
    @ZenCodeType.Method
    public static void setItemInHand(LivingEntity internal, InteractionHand hand, ItemStack stack) {
        
        internal.setItemInHand(hand, stack);
    }
    
    @ZenCodeType.Method
    public static boolean hasItemInSlot(LivingEntity internal, EquipmentSlot slot) {
        
        return internal.hasItemInSlot(slot);
    }
    
    @ZenCodeType.Method
    public static ItemStack getItemBySlot(LivingEntity internal, EquipmentSlot slot) {
        
        return internal.getItemBySlot(slot);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("armorCoverPercentage")
    public static float getArmorCoverPercentage(LivingEntity internal) {
        
        return internal.getArmorCoverPercentage();
    }
    
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("voicePitch")
    public static float getVoicePitch(LivingEntity internal) {
        
        return internal.getVoicePitch();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("jumpBoostPower")
    public static double getJumpBoostPower(LivingEntity internal) {
        
        return internal.getJumpBoostPower();
    }
    
    @ZenCodeType.Method
    public static boolean canStandOnFluid(LivingEntity internal, Fluid fluid) {
        
        return internal.canStandOnFluid(fluid);
    }
    
    @ZenCodeType.Method
    public static void travel(LivingEntity internal, Vec3 vec) {
        
        internal.travel(vec);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("speed")
    public static float getSpeed(LivingEntity internal) {
        
        return internal.getSpeed();
    }
    
    @ZenCodeType.Method
    public static void setSpeed(LivingEntity internal, float speed) {
        
        internal.setSpeed(speed);
    }
    
    @ZenCodeType.Method
    public static boolean doHurtTarget(LivingEntity internal, Entity entity) {
        
        return internal.doHurtTarget(entity);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isSensitiveToWater")
    public static boolean isSensitiveToWater(LivingEntity internal) {
        
        return internal.isSensitiveToWater();
    }
    
    @ZenCodeType.Method
    public static void startAutoSpinAttack(LivingEntity internal, int ticks) {
        
        internal.startAutoSpinAttack(ticks);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isAutoSpinAttack")
    public static boolean isAutoSpinAttack(LivingEntity internal) {
        
        return internal.isAutoSpinAttack();
    }
    
    
    @ZenCodeType.Method
    public static void setJumping(LivingEntity internal, boolean jumping) {
        
        internal.setJumping(jumping);
    }
    
    @ZenCodeType.Method
    public static boolean hasLineOfSight(LivingEntity internal, Entity entity) {
        
        return internal.hasLineOfSight(entity);
    }
    
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("absorptionAmount")
    public static float getAbsorptionAmount(LivingEntity internal) {
        
        return internal.getAbsorptionAmount();
    }
    
    @ZenCodeType.Method
    public static void setAbsorptionAmount(LivingEntity internal, float absorption) {
        
        internal.setAbsorptionAmount(absorption);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("mainArm")
    public static HumanoidArm getMainArm(LivingEntity internal) {
        
        return internal.getMainArm();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isUsingItem")
    public static boolean isUsingItem(LivingEntity internal) {
        
        return internal.isUsingItem();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("usedItemHand")
    public static InteractionHand getUsedItemHand(LivingEntity internal) {
        
        return internal.getUsedItemHand();
    }
    
    @ZenCodeType.Method
    public static void startUsingItem(LivingEntity internal, InteractionHand param0) {
        
        internal.startUsingItem(param0);
    }
    
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("useItem")
    public static ItemStack getUseItem(LivingEntity internal) {
        
        return internal.getUseItem();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("useItemRemainingTicks")
    public static int getUseItemRemainingTicks(LivingEntity internal) {
        
        return internal.getUseItemRemainingTicks();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("ticksUsingItem")
    public static int getTicksUsingItem(LivingEntity internal) {
        
        return internal.getTicksUsingItem();
    }
    
    @ZenCodeType.Method
    public static void releaseUsingItem(LivingEntity internal) {
        
        internal.releaseUsingItem();
    }
    
    @ZenCodeType.Method
    public static void stopUsingItem(LivingEntity internal) {
        
        internal.stopUsingItem();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isBlocking")
    public static boolean isBlocking(LivingEntity internal) {
        
        return internal.isBlocking();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isSuppressingSlidingDownLadder")
    public static boolean isSuppressingSlidingDownLadder(LivingEntity internal) {
        
        return internal.isSuppressingSlidingDownLadder();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isFallFlying")
    public static boolean isFallFlying(LivingEntity internal) {
        
        return internal.isFallFlying();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isVisuallySwimming")
    public static boolean isVisuallySwimming(LivingEntity internal) {
        
        return internal.isVisuallySwimming();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("fallFlyingTicks")
    public static int getFallFlyingTicks(LivingEntity internal) {
        
        return internal.getFallFlyingTicks();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isAffectedByPotions")
    public static boolean isAffectedByPotions(LivingEntity internal) {
        
        return internal.isAffectedByPotions();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("attackable")
    public static boolean attackable(LivingEntity internal) {
        
        return internal.attackable();
    }
    
    @ZenCodeType.Method
    public static boolean canTakeItem(LivingEntity internal, ItemStack stack) {
        
        return internal.canTakeItem(stack);
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    @ZenCodeType.Getter("sleepingPos")
    public static BlockPos getSleepingPos(LivingEntity internal) {
        
        return internal.getSleepingPos().orElse(null);
    }
    
    @ZenCodeType.Method
    public static void setSleepingPos(LivingEntity internal, BlockPos pos) {
        
        internal.setSleepingPos(pos);
    }
    
    @ZenCodeType.Method
    public static void clearSleepingPos(LivingEntity internal) {
        
        internal.clearSleepingPos();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isSleeping")
    public static boolean isSleeping(LivingEntity internal) {
        
        return internal.isSleeping();
    }
    
    @ZenCodeType.Method
    public static void startSleeping(LivingEntity internal, BlockPos pos) {
        
        internal.startSleeping(pos);
    }
    
    @ZenCodeType.Method
    public static void stopSleeping(LivingEntity internal) {
        
        internal.stopSleeping();
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    @ZenCodeType.Getter("bedOrientation")
    public static Direction getBedOrientation(LivingEntity internal) {
        
        return internal.getBedOrientation();
    }
    
    @ZenCodeType.Method
    public static ItemStack eat(LivingEntity internal, Level level, ItemStack stack) {
        
        return internal.eat(level, stack);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isCurrentlyGlowing")
    public static boolean isCurrentlyGlowing(LivingEntity internal) {
        
        return internal.isCurrentlyGlowing();
    }
    
}
