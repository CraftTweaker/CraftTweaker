package com.blamejared.crafttweaker.impl_native.entity;

import com.blamejared.crafttweaker.api.annotations.NativeExpansion;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.entity.MCEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Set;

@ZenRegister
@NativeExpansion(Entity.class)
public class ExpandEntity {
    
    @ZenCodeType.Method
    public static boolean isSpectator(Entity internal) {
        return internal.isSpectator();
    }
    
    @ZenCodeType.Method
    public static MCEntityType getType(Entity internal) {
        return new MCEntityType(internal.getType());
    }
    
    @ZenCodeType.Method
    public static int getEntityId(Entity internal) {
        return internal.getEntityId();
    }
    
    @ZenCodeType.Method
    public static void setEntityId(Entity internal, int id) {
        internal.setEntityId(id);
    }
    
    @ZenCodeType.Method
    public static Set<String> getTags(Entity internal) {
        return internal.getTags();
    }
    
    @ZenCodeType.Method
    public static boolean addTag(Entity internal, String tag) {
        return internal.addTag(tag);
    }
    
    @ZenCodeType.Method
    public static boolean removeTag(Entity internal, String tag) {
        return internal.removeTag(tag);
    }
    
    @ZenCodeType.Method
    public static void onKillCommand(Entity internal) {
        internal.onKillCommand();
    }
    
    @ZenCodeType.Method
    public static boolean isEntityInRange(Entity internal, Entity entity, double distance) {
        return internal.isEntityInRange(entity, distance);
    }
    
    @ZenCodeType.Method
    public static void setPosition(Entity internal, double x, double y, double z) {
        internal.setPosition(x, y, z);
    }
    
    @ZenCodeType.Method
    public static int getMaxInPortalTime(Entity internal) {
        return internal.getMaxInPortalTime();
    }
    
    @ZenCodeType.Method
    public static void setFire(Entity internal, int seconds) {
        internal.setFire(seconds);
    }
    
    @ZenCodeType.Method
    public static void forceFireTicks(Entity internal, int ticks) {
        internal.forceFireTicks(ticks);
    }
    
    @ZenCodeType.Method
    public static int getFireTimer(Entity internal) {
        return internal.getFireTimer();
    }
    
    @ZenCodeType.Method
    public static void extinguish(Entity internal) {
        internal.extinguish();
    }
    
    @ZenCodeType.Method
    public static boolean isOffsetPositionInLiquid(Entity internal, double x, double y, double z) {
        return internal.isOffsetPositionInLiquid(x, y, z);
    }
    
    @ZenCodeType.Method
    public static void setOnGround(Entity internal, boolean grounded) {
        internal.setOnGround(grounded);
    }
    
    @ZenCodeType.Method
    public static boolean isOnGround(Entity internal) {
        return internal.isOnGround();
    }
    
    @ZenCodeType.Method
    public static boolean isSilent(Entity internal) {
        return internal.isSilent();
    }
    
    @ZenCodeType.Method
    public static void setSilent(Entity internal, boolean isSilent) {
        internal.setSilent(isSilent);
    }
    
    @ZenCodeType.Method
    public static boolean hasNoGravity(Entity internal) {
        return internal.hasNoGravity();
    }
    
    @ZenCodeType.Method
    public static void setNoGravity(Entity internal, boolean noGravity) {
        internal.setNoGravity(noGravity);
    }
    
    @ZenCodeType.Method
    public static boolean isImmuneToFire(Entity internal) {
        return internal.isImmuneToFire();
    }
    
    @ZenCodeType.Method
    public static boolean onLivingFall(Entity internal, float distance, float damageMultiplier) {
        return internal.onLivingFall(distance, damageMultiplier);
    }
    
    @ZenCodeType.Method
    public static boolean isInWater(Entity internal) {
        return internal.isInWater();
    }
    
    @ZenCodeType.Method
    public static boolean isWet(Entity internal) {
        return internal.isWet();
    }
    
    @ZenCodeType.Method
    public static boolean isInWaterRainOrBubbleColumn(Entity internal) {
        return internal.isInWaterRainOrBubbleColumn();
    }
    
    @ZenCodeType.Method
    public static boolean isInWaterOrBubbleColumn(Entity internal) {
        return internal.isInWaterOrBubbleColumn();
    }
    
    @ZenCodeType.Method
    public static boolean canSwim(Entity internal) {
        return internal.canSwim();
    }
    
    @ZenCodeType.Method
    public static boolean isInLava(Entity internal) {
        return internal.isInLava();
    }
    
    @ZenCodeType.Method
    public static float getBrightness(Entity internal) {
        return internal.getBrightness();
    }
    
    @ZenCodeType.Method
    public static void moveForced(Entity internal, double x, double y, double z) {
        internal.moveForced(x, y, z);
    }
    
    @ZenCodeType.Method
    public static void forceSetPosition(Entity internal, double x, double y, double z) {
        internal.forceSetPosition(x, y, z);
    }
    
    @ZenCodeType.Method
    public static float getDistance(Entity internal, Entity entityIn) {
        return internal.getDistance(entityIn);
    }
    
    @ZenCodeType.Method
    public static double getDistanceSq(Entity internal, double x, double y, double z) {
        return internal.getDistanceSq(x, y, z);
    }
    
    @ZenCodeType.Method
    public static double getDistanceSq(Entity internal, Entity entityIn) {
        return internal.getDistanceSq(entityIn);
    }
    
    @ZenCodeType.Method
    public static void onCollideWithPlayer(Entity internal, PlayerEntity entityIn) {
        internal.onCollideWithPlayer(entityIn);
    }
    
    @ZenCodeType.Method
    public static void applyEntityCollision(Entity internal, Entity entityIn) {
        internal.applyEntityCollision(entityIn);
    }
    
    @ZenCodeType.Method
    public static void addVelocity(Entity internal, double x, double y, double z) {
        internal.addVelocity(x, y, z);
    }
    
    //TODO: Add the other methods
    // Tip: Use IDE's "delegate" method and go from there
}
