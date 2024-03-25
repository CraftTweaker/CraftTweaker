package com.blamejared.crafttweaker.natives.entity.type.misc;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/type/misc/Mob")
@NativeTypeRegistration(value = Mob.class, zenCodeName = "crafttweaker.api.entity.type.misc.Mob")
public class ExpandMob {
    
    @ZenCodeType.Setter("target")
    public static void setTarget(Mob internal, @ZenCodeType.Nullable LivingEntity target) {
        
        internal.setTarget(target);
    }
    
    @ZenCodeType.Method
    public static boolean canFireProjectileWeapon(Mob internal, ProjectileWeaponItem weapon) {
        
        return internal.canFireProjectileWeapon(weapon);
    }
    
    @ZenCodeType.Method
    public static void ate(Mob internal) {
        
        internal.ate();
    }
    
    @ZenCodeType.Getter("ambientSoundInterval")
    public static int getAmbientSoundInterval(Mob internal) {
        
        return internal.getAmbientSoundInterval();
    }
    
    @ZenCodeType.Method
    public static void playAmbientSound(Mob internal) {
        
        internal.playAmbientSound();
    }
    
    @ZenCodeType.Method
    public static ItemStack equipItemIfPossible(Mob internal, ItemStack stack) {
        
        return internal.equipItemIfPossible(stack);
    }
    
    @ZenCodeType.Method
    public static void setGuaranteedDrop(Mob internal, EquipmentSlot slot) {
        
        internal.setGuaranteedDrop(slot);
    }
    
    @ZenCodeType.Method
    public static boolean canReplaceEqualItem(Mob internal, ItemStack toReplace, ItemStack with) {
        
        return internal.canReplaceEqualItem(toReplace, with);
    }
    
    @ZenCodeType.Method
    public static boolean canHoldItem(Mob internal, ItemStack stack) {
        
        return internal.canHoldItem(stack);
    }
    
    @ZenCodeType.Method
    public static boolean wantsToPickUp(Mob internal, ItemStack stack) {
        
        return internal.wantsToPickUp(stack);
    }
    
    @ZenCodeType.Getter("maxHeadXRot")
    public static int getMaxHeadXRot(Mob internal) {
        
        return internal.getMaxHeadXRot();
    }
    
    @ZenCodeType.Getter("maxHeadYRot")
    public static int getMaxHeadYRot(Mob internal) {
        
        return internal.getMaxHeadYRot();
    }
    
    @ZenCodeType.Getter("maxHeadRotSpeed")
    public static int getHeadRotSpeed(Mob internal) {
        
        return internal.getHeadRotSpeed();
    }
    
    @ZenCodeType.Method
    public static void lookAt(Mob internal, Entity entity, float maxXRotIncrease, float maxYRotIncrease) {
        
        internal.lookAt(entity, maxXRotIncrease, maxYRotIncrease);
    }
    
    @ZenCodeType.Method
    public static boolean checkSpawnRules(Mob internal, LevelAccessor level, MobSpawnType spawnType) {
        
        return internal.checkSpawnRules(level, spawnType);
    }
    
    @ZenCodeType.Method
    public static boolean checkSpawnObstruction(Mob internal, LevelReader level) {
        
        return internal.checkSpawnObstruction(level);
    }
    
    @ZenCodeType.Getter("maxSpawnClusterSize")
    public static int getMaxSpawnClusterSize(Mob internal) {
        
        return internal.getMaxSpawnClusterSize();
    }
    
    @ZenCodeType.Method
    public static boolean isMaxGroupSizeReached(Mob internal, int size) {
        
        return internal.isMaxGroupSizeReached(size);
    }
    
    @ZenCodeType.Method
    public static void setPersistenceRequired(Mob internal) {
        
        internal.setPersistenceRequired();
    }
    
    @ZenCodeType.Method
    public static void setDropChance(Mob internal, EquipmentSlot slot, float chance) {
        
        internal.setDropChance(slot, chance);
    }
    
    @ZenCodeType.Getter("canPickUpLoot")
    public static boolean canPickUpLoot(Mob internal) {
        
        return internal.canPickUpLoot();
    }
    
    @ZenCodeType.Setter("canPickUpLoot")
    public static void setCanPickUpLoot(Mob internal, boolean value) {
        
        internal.setCanPickUpLoot(value);
    }
    
    @ZenCodeType.Getter("isPersistenceRequired")
    public static boolean isPersistenceRequired(Mob internal) {
        
        return internal.isPersistenceRequired();
    }
    
    @ZenCodeType.Getter("isWithinRestriction")
    public static boolean isWithinRestriction(Mob internal) {
        
        return internal.isWithinRestriction();
    }
    
    @ZenCodeType.Method
    public static boolean isWithinRestriction(Mob internal, BlockPos position) {
        
        return internal.isWithinRestriction(position);
    }
    
    @ZenCodeType.Method
    public static void restrictTo(Mob internal, BlockPos restrictCenter, int restrictRadius) {
        
        internal.restrictTo(restrictCenter, restrictRadius);
    }
    
    @ZenCodeType.Getter("getRestrictCenter")
    public static BlockPos getRestrictCenter(Mob internal) {
        
        return internal.getRestrictCenter();
    }
    
    @ZenCodeType.Getter("getRestrictRadius")
    public static float getRestrictRadius(Mob internal) {
        
        return internal.getRestrictRadius();
    }
    
    @ZenCodeType.Method
    public static void clearRestriction(Mob internal) {
        
        internal.clearRestriction();
    }
    
    @ZenCodeType.Getter("hasRestriction")
    public static boolean hasRestriction(Mob internal) {
        
        return internal.hasRestriction();
    }
    
    @ZenCodeType.Method
    public static void dropLeash(Mob internal, boolean broadcastPacket, boolean dropLeash) {
        
        internal.dropLeash(broadcastPacket, dropLeash);
    }
    
    @ZenCodeType.Method
    public static boolean canBeLeashed(Mob internal, Player leashHolder) {
        
        return internal.canBeLeashed(leashHolder);
    }
    
    @ZenCodeType.Getter("leashed")
    public static boolean isLeashed(Mob internal) {
        
        return internal.isLeashed();
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Getter("getRestrictCenter")
    public static Entity getLeashHolder(Mob internal) {
        
        return internal.getLeashHolder();
    }
    
    @ZenCodeType.Method
    public static void setLeashedTo(Mob internal, Entity leashHolder, boolean broadcastPacket) {
        
        internal.setLeashedTo(leashHolder, broadcastPacket);
    }
    
    @ZenCodeType.Setter("noAi")
    public static void setNoAi(Mob internal, boolean value) {
        
        internal.setNoAi(value);
    }
    
    @ZenCodeType.Setter("leftHanded")
    public static void setLeftHanded(Mob internal, boolean value) {
        
        internal.setLeftHanded(value);
    }
    
    @ZenCodeType.Setter("aggressive")
    public static void setAggressive(Mob internal, boolean value) {
        
        internal.setAggressive(value);
    }
    
    @ZenCodeType.Getter("noAi")
    public static boolean isNoAi(Mob internal) {
        
        return internal.isNoAi();
    }
    
    @ZenCodeType.Getter("leftHanded")
    public static boolean isLeftHanded(Mob internal) {
        
        return internal.isLeftHanded();
    }
    
    @ZenCodeType.Getter("aggressive")
    public static boolean isAggressive(Mob internal) {
        
        return internal.isAggressive();
    }
    
    @ZenCodeType.Setter("baby")
    public static void setBaby(Mob internal, boolean value) {
        
        internal.setBaby(value);
    }
    
    @ZenCodeType.Method
    public static boolean isWithinMeleeAttackRange(Mob internal, LivingEntity entity) {
        
        return internal.isWithinMeleeAttackRange(entity);
    }
    
}
