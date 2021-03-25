package com.blamejared.crafttweaker.impl_native.entity;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.entity.MCEntityType;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;

@ZenRegister
@Document("vanilla/api/entity/MCLivingEntity")
@NativeTypeRegistration(value = LivingEntity.class, zenCodeName = "crafttweaker.api.entity.MCLivingEntity")
public class ExpandLivingEntity {
    
    @ZenCodeType.Method
    public static boolean canAttack(LivingEntity internal, MCEntityType typeIn) {
        return internal.canAttack(typeIn.getInternal());
    }
    
    @ZenCodeType.Method
    public static boolean canBreatheUnderwater(LivingEntity internal) {
        return internal.canBreatheUnderwater();
    }
    
    @ZenCodeType.Method
    public static boolean isChild(LivingEntity internal) {
        return internal.isChild();
    }
    
    @ZenCodeType.Method
    public static boolean canBeRiddenInWater(LivingEntity internal) {
        return internal.canBeRiddenInWater();
    }
    
    @Nullable
    @ZenCodeType.Method
    public static LivingEntity getRevengeTarget(LivingEntity internal) {
        return internal.getRevengeTarget();
    }
    
    @ZenCodeType.Method
    public static int getRevengeTimer(LivingEntity internal) {
        return internal.getRevengeTimer();
    }
    
    @ZenCodeType.Method
    public static void setRevengeTarget(LivingEntity internal, @Nullable LivingEntity livingBase) {
        internal.setRevengeTarget(livingBase);
    }
    
    @Nullable
    @ZenCodeType.Method
    public static LivingEntity getLastAttackedEntity(LivingEntity internal) {
        return internal.getLastAttackedEntity();
    }
    
    @ZenCodeType.Method
    public static int getLastAttackedEntityTime(LivingEntity internal) {
        return internal.getLastAttackedEntityTime();
    }
    
    @ZenCodeType.Method
    public static void setLastAttackedEntity(LivingEntity internal, Entity entityIn) {
        internal.setLastAttackedEntity(entityIn);
    }
    
    @ZenCodeType.Method
    public static int getIdleTime(LivingEntity internal) {
        return internal.getIdleTime();
    }
    
    @ZenCodeType.Method
    public static void setIdleTime(LivingEntity internal, int idleTimeIn) {
        internal.setIdleTime(idleTimeIn);
    }
    
    @ZenCodeType.Method
    public static boolean canAttack(LivingEntity internal, LivingEntity target) {
        return internal.canAttack(target);
    }
    
    @ZenCodeType.Method
    public static boolean clearActivePotions(LivingEntity internal) {
        return internal.clearActivePotions();
    }
    
    @ZenCodeType.Method
    public static Collection<EffectInstance> getActivePotionEffects(LivingEntity internal) {
        return internal.getActivePotionEffects();
    }
    
    @ZenCodeType.Method
    public static Map<Effect, EffectInstance> getActivePotionMap(LivingEntity internal) {
        return internal.getActivePotionMap();
    }
    
    @ZenCodeType.Method
    public static boolean isPotionActive(LivingEntity internal, Effect potionIn) {
        return internal.isPotionActive(potionIn);
    }
    
    @Nullable
    @ZenCodeType.Method
    public static EffectInstance getActivePotionEffect(LivingEntity internal, Effect potionIn) {
        return internal.getActivePotionEffect(potionIn);
    }
    
    @ZenCodeType.Method
    public static boolean addPotionEffect(LivingEntity internal, EffectInstance effectInstanceIn) {
        return internal.addPotionEffect(effectInstanceIn);
    }
    
    @ZenCodeType.Method
    public static boolean isPotionApplicable(LivingEntity internal, EffectInstance potioneffectIn) {
        return internal.isPotionApplicable(potioneffectIn);
    }
    
    @ZenCodeType.Method
    public static boolean isEntityUndead(LivingEntity internal) {
        return internal.isEntityUndead();
    }
    
    @Nullable
    @ZenCodeType.Method
    public static EffectInstance removeActivePotionEffect(LivingEntity internal, @Nullable Effect potioneffectin) {
        return internal.removeActivePotionEffect(potioneffectin);
    }
    
    @ZenCodeType.Method
    public static boolean removePotionEffect(LivingEntity internal, Effect effectIn) {
        return internal.removePotionEffect(effectIn);
    }
    
    @ZenCodeType.Method
    public static void heal(LivingEntity internal, float healAmount) {
        internal.heal(healAmount);
    }
    
    @ZenCodeType.Method
    public static float getHealth(LivingEntity internal) {
        return internal.getHealth();
    }
    
    @ZenCodeType.Method
    public static void setHealth(LivingEntity internal, float health) {
        internal.setHealth(health);
    }
    
    @ZenCodeType.Method
    public static boolean getShouldBeDead(LivingEntity internal) {
        return internal.getShouldBeDead();
    }
    
    @ZenCodeType.Method
    public static boolean isOnLadder(LivingEntity internal) {
        return internal.isOnLadder();
    }
    
    @ZenCodeType.Method
    public static boolean isAlive(LivingEntity internal) {
        return internal.isAlive();
    }
    
    @ZenCodeType.Method
    public static int getTotalArmorValue(LivingEntity internal) {
        return internal.getTotalArmorValue();
    }
    
    @Nullable
    @ZenCodeType.Method
    public static LivingEntity getAttackingEntity(LivingEntity internal) {
        return internal.getAttackingEntity();
    }
    
    @ZenCodeType.Method
    public static float getMaxHealth(LivingEntity internal) {
        return internal.getMaxHealth();
    }
    
    @ZenCodeType.Method
    public static int getArrowCountInEntity(LivingEntity internal) {
        return internal.getArrowCountInEntity();
    }
    
    @ZenCodeType.Method
    public static void setArrowCountInEntity(LivingEntity internal, int count) {
        internal.setArrowCountInEntity(count);
    }
    
    @ZenCodeType.Method
    public static int getBeeStingCount(LivingEntity internal) {
        return internal.getBeeStingCount();
    }
    
    @ZenCodeType.Method
    public static void setBeeStingCount(LivingEntity internal, int p_226300_1_) {
        internal.setBeeStingCount(p_226300_1_);
    }
    
    @ZenCodeType.Method
    public static ItemStack getHeldItemMainhand(LivingEntity internal) {
        return internal.getHeldItemMainhand();
    }
    
    @ZenCodeType.Method
    public static ItemStack getHeldItemOffhand(LivingEntity internal) {
        return internal.getHeldItemOffhand();
    }
    
    @ZenCodeType.Method
    public static boolean canEquip(LivingEntity internal, Item item) {
        return internal.canEquip(item);
    }
    
    @ZenCodeType.Method
    public static Iterable<ItemStack> getArmorInventoryList(LivingEntity internal) {
        return internal.getArmorInventoryList();
    }
    
    @ZenCodeType.Method
    public static float getArmorCoverPercentage(LivingEntity internal) {
        return internal.getArmorCoverPercentage();
    }
    
    @ZenCodeType.Method
    public static float getAIMoveSpeed(LivingEntity internal) {
        return internal.getAIMoveSpeed();
    }
    
    @ZenCodeType.Method
    public static void setAIMoveSpeed(LivingEntity internal, float speedIn) {
        internal.setAIMoveSpeed(speedIn);
    }
    
    @ZenCodeType.Method
    public static boolean attackEntityAsMob(LivingEntity internal, Entity entityIn) {
        return internal.attackEntityAsMob(entityIn);
    }
    
    @ZenCodeType.Method
    public static boolean isWaterSensitive(LivingEntity internal) {
        return internal.isWaterSensitive();
    }
    
    @ZenCodeType.Method
    public static void attackEntityFrom(LivingEntity internal, DamageSource damageSource, float amount) {
        internal.attackEntityFrom(damageSource, amount);
    }
    
    /**
     * Gets the ItemStack in the specific slot.
     *
     * @return The ItemStack in the specific slot.
     */
    @ZenCodeType.Method
    public static IItemStack getItemStackFromSlot(LivingEntity internal, EquipmentSlotType slot) {

        return new MCItemStack(internal.getItemStackFromSlot(slot));
    }
    
    /**
     * Sets a copied given itemStack to the slot
     */
    @ZenCodeType.Method
    public static void setItemStackToSlot(LivingEntity internal, EquipmentSlotType slot, IItemStack itemStack) {
        
        internal.setItemStackToSlot(slot, itemStack.getInternal().copy());
    }
    
}
