package com.blamejared.crafttweaker.natives.entity;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

@ZenRegister
@Document("vanilla/api/entity/Entity")
@NativeTypeRegistration(value = Entity.class, zenCodeName = "crafttweaker.api.entity.Entity")
public class ExpandEntity {
    
    @ZenCodeType.Method
    public static boolean isColliding(Entity internal, BlockPos pos, BlockState state) {
        
        return internal.isColliding(pos, state);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("level")
    public static Level getLevel(Entity internal) {
        
        return internal.level;
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("teamColor")
    public static int getTeamColor(Entity internal) {
        
        return internal.getTeamColor();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isSpectator")
    public static boolean isSpectator(Entity internal) {
        
        return internal.isSpectator();
    }
    
    @ZenCodeType.Method
    public static void unRide(Entity internal) {
        
        internal.unRide();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("type")
    public static EntityType getType(Entity internal) {
        
        return internal.getType();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("tags")
    public static Set<String> getTags(Entity internal) {
        
        return internal.getTags();
    }
    
    @ZenCodeType.Method
    public static boolean addTag(Entity internal, String tagName) {
        
        return internal.addTag(tagName);
    }
    
    @ZenCodeType.Method
    public static boolean removeTag(Entity internal, String tagName) {
        
        return internal.removeTag(tagName);
    }
    
    @ZenCodeType.Method
    public static void kill(Entity internal) {
        
        internal.kill();
    }
    
    @ZenCodeType.Method
    public static void discard(Entity internal) {
        
        internal.discard();
    }
    
    @ZenCodeType.Method
    public static boolean closerThan(Entity internal, Entity other, double distance) {
        
        return internal.closerThan(other, distance);
    }
    
    @ZenCodeType.Method
    public static void setPos(Entity internal, Vec3 position) {
        
        internal.setPos(position);
    }
    
    @ZenCodeType.Method
    public static void setPos(Entity internal, double x, double y, double z) {
        
        internal.setPos(x, y, z);
    }
    
    @ZenCodeType.Method
    public static void turn(Entity internal, double yaw, double pitch) {
        
        internal.turn(yaw, pitch);
    }
    
    @ZenCodeType.Method
    public static void setPortalCooldown(Entity internal) {
        
        internal.setPortalCooldown();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isOnPortalCooldown")
    public static boolean isOnPortalCooldown(Entity internal) {
        
        return internal.isOnPortalCooldown();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("portalWaitTime")
    public static int getPortalWaitTime(Entity internal) {
        
        return internal.getPortalWaitTime();
    }
    
    @ZenCodeType.Method
    public static void lavaHurt(Entity internal) {
        
        internal.lavaHurt();
    }
    
    @ZenCodeType.Method
    public static void setSecondsOnFire(Entity internal, int seconds) {
        
        internal.setSecondsOnFire(seconds);
    }
    
    @ZenCodeType.Method
    public static void setRemainingFireTicks(Entity internal, int ticks) {
        
        internal.setRemainingFireTicks(ticks);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("remainingFireTicks")
    public static int getRemainingFireTicks(Entity internal) {
        
        return internal.getRemainingFireTicks();
    }
    
    @ZenCodeType.Method
    public static void clearFire(Entity internal) {
        
        internal.clearFire();
    }
    
    @ZenCodeType.Method
    public static boolean isFree(Entity internal, double x, double y, double z) {
        
        return internal.isFree(x, y, z);
    }
    
    @ZenCodeType.Method
    public static void setOnGround(Entity internal, boolean onGround) {
        
        internal.setOnGround(onGround);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isOnGround")
    public static boolean isOnGround(Entity internal) {
        
        return internal.isOnGround();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("onPos")
    public static BlockPos getOnPos(Entity internal) {
        
        return internal.getOnPos();
    }
    
    @ZenCodeType.Method
    public static void playSound(Entity internal, SoundEvent sound, float volume, float pitch) {
        
        internal.playSound(sound, volume, pitch);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isSilent")
    public static boolean isSilent(Entity internal) {
        
        return internal.isSilent();
    }
    
    @ZenCodeType.Method
    public static void setSilent(Entity internal, boolean silent) {
        
        internal.setSilent(silent);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isNoGravity")
    public static boolean isNoGravity(Entity internal) {
        
        return internal.isNoGravity();
    }
    
    @ZenCodeType.Method
    public static void setNoGravity(Entity internal, boolean noGravity) {
        
        internal.setNoGravity(noGravity);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("occludesVibrations")
    public static boolean occludesVibrations(Entity internal) {
        
        return internal.occludesVibrations();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("fireImmune")
    public static boolean fireImmune(Entity internal) {
        
        return internal.fireImmune();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isInWater")
    public static boolean isInWater(Entity internal) {
        
        return internal.isInWater();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isInWaterOrRain")
    public static boolean isInWaterOrRain(Entity internal) {
        
        return internal.isInWaterOrRain();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isInWaterRainOrBubble")
    public static boolean isInWaterRainOrBubble(Entity internal) {
        
        return internal.isInWaterRainOrBubble();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isInWaterOrBubble")
    public static boolean isInWaterOrBubble(Entity internal) {
        
        return internal.isInWaterOrBubble();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isUnderWater")
    public static boolean isUnderWater(Entity internal) {
        
        return internal.isUnderWater();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isInLava")
    public static boolean isInLava(Entity internal) {
        
        return internal.isInLava();
    }
    
    @ZenCodeType.Method
    public static void moveRelative(Entity internal, float amount, Vec3 relative) {
        
        internal.moveRelative(amount, relative);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("brightness")
    public static float getBrightness(Entity internal) {
        
        return internal.getBrightness();
    }
    
    @ZenCodeType.Method
    public static void moveTo(Entity internal, Vec3 vec) {
        
        internal.moveTo(vec);
    }
    
    @ZenCodeType.Method
    public static void moveTo(Entity internal, double x, double y, double z) {
        
        internal.moveTo(x, y, z);
    }
    
    @ZenCodeType.Method
    public static void moveTo(Entity internal, BlockPos pos, float yaw, float pitch) {
        
        internal.moveTo(pos, yaw, pitch);
    }
    
    @ZenCodeType.Method
    public static void moveTo(Entity internal, double x, double y, double z, float yaw, float pitch) {
        
        internal.moveTo(x, y, z, yaw, pitch);
    }
    
    @ZenCodeType.Method
    public static void setOldPosAndRot(Entity internal) {
        
        internal.setOldPosAndRot();
    }
    
    @ZenCodeType.Method
    public static float distanceTo(Entity internal, Entity entity) {
        
        return internal.distanceTo(entity);
    }
    
    @ZenCodeType.Method
    public static double distanceToSqr(Entity internal, double x, double y, double z) {
        
        return internal.distanceToSqr(x, y, z);
    }
    
    @ZenCodeType.Method
    public static double distanceToSqr(Entity internal, Entity entity) {
        
        return internal.distanceToSqr(entity);
    }
    
    @ZenCodeType.Method
    public static double distanceToSqr(Entity internal, Vec3 vec) {
        
        return internal.distanceToSqr(vec);
    }
    
    @ZenCodeType.Method
    public static boolean hurt(Entity internal, DamageSource source, float amount) {
        
        return internal.hurt(source, amount);
    }
    
    @ZenCodeType.Method
    public static Vec3 getViewVector(Entity internal, float partialTicks) {
        
        return internal.getViewVector(partialTicks);
    }
    
    @ZenCodeType.Method
    public static Vec3 getUpVector(Entity internal, float partialTicks) {
        
        return internal.getUpVector(partialTicks);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("eyePosition")
    public static Vec3 getEyePosition(Entity internal) {
        
        return internal.getEyePosition();
    }
    
    @ZenCodeType.Method
    public static Vec3 getEyePosition(Entity internal, float partialTicks) {
        
        return internal.getEyePosition(partialTicks);
    }
    
    @ZenCodeType.Method
    public static Vec3 getPosition(Entity internal, float partialTicks) {
        
        return internal.getPosition(partialTicks);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isPickable")
    public static boolean isPickable(Entity internal) {
        
        return internal.isPickable();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isPushable")
    public static boolean isPushable(Entity internal) {
        
        return internal.isPushable();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isAlive")
    public static boolean isAlive(Entity internal) {
        
        return internal.isAlive();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isInWall")
    public static boolean isInWall(Entity internal) {
        
        return internal.isInWall();
    }
    
    @ZenCodeType.Method
    public static boolean canCollideWith(Entity internal, Entity other) {
        
        return internal.canCollideWith(other);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("canBeCollidedWith")
    public static boolean canBeCollidedWith(Entity internal) {
        
        return internal.canBeCollidedWith();
    }
    
    @ZenCodeType.Method
    public static void positionRider(Entity internal, Entity entity) {
        
        internal.positionRider(entity);
    }
    
    @ZenCodeType.Method
    public static boolean startRiding(Entity internal, Entity entity) {
        
        return internal.startRiding(entity);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("showVehicleHealth")
    public static boolean showVehicleHealth(Entity internal) {
        
        return internal.showVehicleHealth();
    }
    
    @ZenCodeType.Method
    public static boolean startRiding(Entity internal, Entity entity, boolean force) {
        
        return internal.startRiding(entity, force);
    }
    
    @ZenCodeType.Method
    public static void ejectPassengers(Entity internal) {
        
        internal.ejectPassengers();
    }
    
    @ZenCodeType.Method
    public static void removeVehicle(Entity internal) {
        
        internal.removeVehicle();
    }
    
    @ZenCodeType.Method
    public static void stopRiding(Entity internal) {
        
        internal.stopRiding();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("lookAngle")
    public static Vec3 getLookAngle(Entity internal) {
        
        return internal.getLookAngle();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("forward")
    public static Vec3 getForward(Entity internal) {
        
        return internal.getForward();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("dimensionChangingDelay")
    public static int getDimensionChangingDelay(Entity internal) {
        
        return internal.getDimensionChangingDelay();
    }
    
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("handSlots")
    public static Iterable<ItemStack> getHandSlots(Entity internal) {
        
        return internal.getHandSlots();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("armorSlots")
    public static Iterable<ItemStack> getArmorSlots(Entity internal) {
        
        return internal.getArmorSlots();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("allSlots")
    public static Iterable<ItemStack> getAllSlots(Entity internal) {
        
        return internal.getAllSlots();
    }
    
    @ZenCodeType.Method
    public static void setItemSlot(Entity internal, EquipmentSlot slot, ItemStack stack) {
        
        internal.setItemSlot(slot, stack);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isOnFire")
    public static boolean isOnFire(Entity internal) {
        
        return internal.isOnFire();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isPassenger")
    public static boolean isPassenger(Entity internal) {
        
        return internal.isPassenger();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isVehicle")
    public static boolean isVehicle(Entity internal) {
        
        return internal.isVehicle();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("rideableUnderWater")
    public static boolean rideableUnderWater(Entity internal) {
        
        return internal.rideableUnderWater();
    }
    
    @ZenCodeType.Method
    public static void setShiftKeyDown(Entity internal, boolean keyDown) {
        
        internal.setShiftKeyDown(keyDown);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isShiftKeyDown")
    public static boolean isShiftKeyDown(Entity internal) {
        
        return internal.isShiftKeyDown();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isSteppingCarefully")
    public static boolean isSteppingCarefully(Entity internal) {
        
        return internal.isSteppingCarefully();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isSuppressingBounce")
    public static boolean isSuppressingBounce(Entity internal) {
        
        return internal.isSuppressingBounce();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isDiscrete")
    public static boolean isDiscrete(Entity internal) {
        
        return internal.isDiscrete();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isDescending")
    public static boolean isDescending(Entity internal) {
        
        return internal.isDescending();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isCrouching")
    public static boolean isCrouching(Entity internal) {
        
        return internal.isCrouching();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isSprinting")
    public static boolean isSprinting(Entity internal) {
        
        return internal.isSprinting();
    }
    
    @ZenCodeType.Method
    public static void setSprinting(Entity internal, boolean sprinting) {
        
        internal.setSprinting(sprinting);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isSwimming")
    public static boolean isSwimming(Entity internal) {
        
        return internal.isSwimming();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isVisuallySwimming")
    public static boolean isVisuallySwimming(Entity internal) {
        
        return internal.isVisuallySwimming();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isVisuallyCrawling")
    public static boolean isVisuallyCrawling(Entity internal) {
        
        return internal.isVisuallyCrawling();
    }
    
    @ZenCodeType.Method
    public static void setSwimming(Entity internal, boolean swimming) {
        
        internal.setSwimming(swimming);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("hasGlowingTag")
    public static boolean hasGlowingTag(Entity internal) {
        
        return internal.hasGlowingTag();
    }
    
    @ZenCodeType.Method
    public static void setGlowingTag(Entity internal, boolean glowing) {
        
        internal.setGlowingTag(glowing);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isCurrentlyGlowing")
    public static boolean isCurrentlyGlowing(Entity internal) {
        
        return internal.isCurrentlyGlowing();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isInvisible")
    public static boolean isInvisible(Entity internal) {
        
        return internal.isInvisible();
    }
    
    @ZenCodeType.Method
    public static boolean isInvisibleTo(Entity internal, Player player) {
        
        return internal.isInvisibleTo(player);
    }
    
    @ZenCodeType.Method
    public static void setInvisible(Entity internal, boolean invisible) {
        
        internal.setInvisible(invisible);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("maxAirSupply")
    public static int getMaxAirSupply(Entity internal) {
        
        return internal.getMaxAirSupply();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("airSupply")
    public static int getAirSupply(Entity internal) {
        
        return internal.getAirSupply();
    }
    
    @ZenCodeType.Method
    public static void setAirSupply(Entity internal, int air) {
        
        internal.setAirSupply(air);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("ticksFrozen")
    public static int getTicksFrozen(Entity internal) {
        
        return internal.getTicksFrozen();
    }
    
    @ZenCodeType.Method
    public static void setTicksFrozen(Entity internal, int ticks) {
        
        internal.setTicksFrozen(ticks);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("percentFrozen")
    public static float getPercentFrozen(Entity internal) {
        
        return internal.getPercentFrozen();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isFullyFrozen")
    public static boolean isFullyFrozen(Entity internal) {
        
        return internal.isFullyFrozen();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("ticksRequiredToFreeze")
    public static int getTicksRequiredToFreeze(Entity internal) {
        
        return internal.getTicksRequiredToFreeze();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("name")
    public static Component getName(Entity internal) {
        
        return internal.getName();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isAttackable")
    public static boolean isAttackable(Entity internal) {
        
        return internal.isAttackable();
    }
    
    @ZenCodeType.Method
    public static boolean isInvulnerableTo(Entity internal, DamageSource source) {
        
        return internal.isInvulnerableTo(source);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isInvulnerable")
    public static boolean isInvulnerable(Entity internal) {
        
        return internal.isInvulnerable();
    }
    
    @ZenCodeType.Method
    public static void setInvulnerable(Entity internal, boolean invulnerable) {
        
        internal.setInvulnerable(invulnerable);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("maxFallDistance")
    public static int getMaxFallDistance(Entity internal) {
        
        return internal.getMaxFallDistance();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("stringUUID")
    public static String getStringUUID(Entity internal) {
        
        return internal.getStringUUID();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isPushedByFluid")
    public static boolean isPushedByFluid(Entity internal) {
        
        return internal.isPushedByFluid();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("displayName")
    public static Component getDisplayName(Entity internal) {
        
        return internal.getDisplayName();
    }
    
    @ZenCodeType.Method
    public static void setCustomName(Entity internal, @ZenCodeType.Nullable Component name) {
        
        internal.setCustomName(name);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Nullable
    @ZenCodeType.Getter("customName")
    public static Component getCustomName(Entity internal) {
        
        return internal.getCustomName();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("hasCustomName")
    public static boolean hasCustomName(Entity internal) {
        
        return internal.hasCustomName();
    }
    
    @ZenCodeType.Method
    public static void setCustomNameVisible(Entity internal, boolean visible) {
        
        internal.setCustomNameVisible(visible);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isCustomNameVisible")
    public static boolean isCustomNameVisible(Entity internal) {
        
        return internal.isCustomNameVisible();
    }
    
    @ZenCodeType.Method
    public static void teleportTo(Entity internal, double x, double y, double z) {
        
        internal.teleportTo(x, y, z);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("shouldShowName")
    public static boolean shouldShowName(Entity internal) {
        
        return internal.shouldShowName();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("direction")
    public static Direction getDirection(Entity internal) {
        
        return internal.getDirection();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("motionDirection")
    public static Direction getMotionDirection(Entity internal) {
        
        return internal.getMotionDirection();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("boundingBoxForCulling")
    public static AABB getBoundingBoxForCulling(Entity internal) {
        
        return internal.getBoundingBoxForCulling();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("eyeHeight")
    public static float getEyeHeight(Entity internal) {
        
        return internal.getEyeHeight();
    }
    
    @ZenCodeType.Method
    public static void sendMessage(Entity internal, Component message) {
        
        internal.sendMessage(message, CraftTweakerConstants.CRAFTTWEAKER_UUID);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("commandSenderWorld")
    public static Level getCommandSenderWorld(Entity internal) {
        
        return internal.getCommandSenderWorld();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Nullable
    @ZenCodeType.Getter("controllingPassenger")
    public static Entity getControllingPassenger(Entity internal) {
        
        return internal.getControllingPassenger();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("passengers")
    public static List<Entity> getPassengers(Entity internal) {
        
        return internal.getPassengers();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Nullable
    @ZenCodeType.Getter("firstPassenger")
    public static Entity getFirstPassenger(Entity internal) {
        
        return internal.getFirstPassenger();
    }
    
    @ZenCodeType.Method
    public static boolean hasPassenger(Entity internal, Entity entity) {
        
        return internal.hasPassenger(entity);
    }
    
    @ZenCodeType.Method
    public static boolean hasPassenger(Entity internal, Predicate<Entity> predicate) {
        
        return internal.hasPassenger(predicate);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("hasExactlyOnePlayerPassenger")
    public static boolean hasExactlyOnePlayerPassenger(Entity internal) {
        
        return internal.hasExactlyOnePlayerPassenger();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("rootVehicle")
    public static Entity getRootVehicle(Entity internal) {
        
        return internal.getRootVehicle();
    }
    
    @ZenCodeType.Method
    public static boolean isPassengerOfSameVehicle(Entity internal, Entity entity) {
        
        return internal.isPassengerOfSameVehicle(entity);
    }
    
    @ZenCodeType.Method
    public static boolean hasIndirectPassenger(Entity internal, Entity entity) {
        
        return internal.hasIndirectPassenger(entity);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Nullable
    @ZenCodeType.Getter("vehicle")
    public static Entity getVehicle(Entity internal) {
        
        return internal.getVehicle();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("pistonPushReaction")
    public static PushReaction getPistonPushReaction(Entity internal) {
        
        return internal.getPistonPushReaction();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("soundSource")
    public static SoundSource getSoundSource(Entity internal) {
        
        return internal.getSoundSource();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("fluidJumpThreshold")
    public static double getFluidJumpThreshold(Entity internal) {
        
        return internal.getFluidJumpThreshold();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("bbWidth")
    public static float getBbWidth(Entity internal) {
        
        return internal.getBbWidth();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("bbHeight")
    public static float getBbHeight(Entity internal) {
        
        return internal.getBbHeight();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("position")
    public static Vec3 position(Entity internal) {
        
        return internal.position();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("blockPosition")
    public static BlockPos blockPosition(Entity internal) {
        
        return internal.blockPosition();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("feetBlockState")
    public static BlockState getFeetBlockState(Entity internal) {
        
        return internal.getFeetBlockState();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("eyeBlockPosition")
    public static BlockPos eyeBlockPosition(Entity internal) {
        
        return internal.eyeBlockPosition();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("blockX")
    public static int getBlockX(Entity internal) {
        
        return internal.getBlockX();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("x")
    public static double getX(Entity internal) {
        
        return internal.getX();
    }
    
    @ZenCodeType.Method
    public static double getX(Entity internal, double scale) {
        
        return internal.getX(scale);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("blockY")
    public static int getBlockY(Entity internal) {
        
        return internal.getBlockY();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("y")
    public static double getY(Entity internal) {
        
        return internal.getY();
    }
    
    @ZenCodeType.Method
    public static double getY(Entity internal, double scale) {
        
        return internal.getY(scale);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("eyeY")
    public static double getEyeY(Entity internal) {
        
        return internal.getEyeY();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("blockZ")
    public static int getBlockZ(Entity internal) {
        
        return internal.getBlockZ();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("z")
    public static double getZ(Entity internal) {
        
        return internal.getZ();
    }
    
    @ZenCodeType.Method
    public static double getZ(Entity internal, double scale) {
        
        return internal.getZ(scale);
    }
    
    @ZenCodeType.Method
    public static void setPosRaw(Entity internal, double x, double y, double z) {
        
        internal.setPosRaw(x, y, z);
    }
    
    @ZenCodeType.Method
    public static void setIsInPowderSnow(Entity internal, boolean inPowderSnow) {
        
        internal.setIsInPowderSnow(inPowderSnow);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("canFreeze")
    public static boolean canFreeze(Entity internal) {
        
        return internal.canFreeze();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isRemoved")
    public static boolean isRemoved(Entity internal) {
        
        return internal.isRemoved();
    }
    
    
    /**
     * Gets the NBT data of this Entity.
     *
     * @return The NBT data of this Entity.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("data")
    public static MapData getData(Entity internal) {
        
        return new MapData(internal.saveWithoutId(new CompoundTag()));
    }
    
}
