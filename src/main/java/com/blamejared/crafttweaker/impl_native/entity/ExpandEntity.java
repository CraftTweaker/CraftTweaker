package com.blamejared.crafttweaker.impl_native.entity;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.data.MapData;
import com.blamejared.crafttweaker.impl.entity.MCEntityType;
import com.blamejared.crafttweaker.impl.util.MCDirection;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;
import java.util.Set;

@ZenRegister
@Document("vanilla/api/entity/MCEntity")
@NativeTypeRegistration(value = Entity.class, zenCodeName = "crafttweaker.api.entity.MCEntity")
public class ExpandEntity {
    
    /**
     * Removes the entity from the world.
     */
    @ZenCodeType.Method
    public static void remove(Entity internal) {
        
        internal.remove();
    }
    
    /**
     * Sets the location and looking angles of the entity.
     *
     * @param x     The new x position.
     * @param y     The new y position.
     * @param z     The new z position.
     * @param yaw   The new yaw value.
     * @param pitch The new pitch value.
     *
     * @docParam x 5
     * @docParam y 1
     * @docParam z 9
     * @docParam yaw 90
     * @docParam pitch 120
     */
    @ZenCodeType.Method
    public static void setLocationAndAngles(Entity internal, double x, double y, double z, float yaw, float pitch) {
        
        internal.setLocationAndAngles(x, y, z, yaw, pitch);
    }
    
    @ZenCodeType.Method
    public static void setPositionAndUpdate(Entity internal, double x, double y, double z) {
        
        internal.setPositionAndUpdate(x, y, z);
    }
    
    @ZenCodeType.Method
    public static boolean startRiding(Entity internal, Entity other, @ZenCodeType.OptionalBoolean(false) boolean forced) {
        
        return internal.startRiding(other, forced);
    }
    
    @ZenCodeType.Method
    public static void removePassengers(Entity internal) {
        
        internal.removePassengers();
    }
    
    @ZenCodeType.Method
    public static void dismount(Entity internal) {
        
        internal.dismount();
    }
    
    
    /**
     * Checks if this Entity is sneaking or not.
     *
     * @return True if sneaking. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("sneaking")
    public static boolean isSneaking(Entity internal) {
        
        return internal.isSneaking();
    }
    
    /**
     * Sets the sneaking value of this Entity.
     *
     * @param value The new sneaking value
     *
     * @docParam value true
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("sneaking")
    public static void setSneaking(Entity internal, boolean value) {
        
        internal.setSneaking(value);
    }
    
    /**
     * Gets the World that this Entity is in.
     *
     * @return The World this Entity is in.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("world")
    public static World getWorld(Entity internal) {
        
        return internal.world;
    }
    
    /**
     * Gets the air value for the Entity.
     * The air value is used to determine when the Entity will start drowning when swimming.
     *
     * @return The air value of the Entity.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("air")
    public static int getAir(Entity internal) {
        
        return internal.getAir();
    }
    
    /**
     * Sets the air value for the Entity
     *
     * @param air The new air value.
     *
     * @docParam air 20
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("air")
    public static void setAir(Entity internal, int air) {
        
        internal.setAir(air);
    }
    
    /**
     * Gets this Entity's position in the world.
     *
     * @return This Entity's position in the world.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("position")
    public static BlockPos getPosition(Entity internal) {
        
        return internal.getPosition();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("positionVec")
    public static Vector3d getPositionVec(Entity internal){
        return internal.getPositionVec();
    }
    
    /**
     * Checks if this Entity is in spectator mode.
     *
     * @return True if this Entity is in spectator mode. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("spectator")
    public static boolean isSpectator(Entity internal) {
        
        return internal.isSpectator();
    }
    
    
    /**
     * Gets this Entity's type.
     *
     * @return The type of this Entity.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("type")
    public static MCEntityType getType(Entity internal) {
        
        return new MCEntityType(internal.getType());
    }
    
    /**
     * Gets this Entity's id that can be used to reference this Entity.
     *
     * @return The id of this Entity.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("id")
    public static int getEntityId(Entity internal) {
        
        return internal.getEntityId();
    }
    
    // TODO REMOVE
    
    /**
     * This method is marked for removal next breaking change.
     *
     * It sets the ID of the entity, which is only used in networking code and should never have to be called by mods or scripts.
     *
     * @param id 0
     *
     * @docParam id 0
     */
    @Deprecated
    @ZenCodeType.Method
    public static void setEntityId(Entity internal, int id) {
        
        internal.setEntityId(id);
        CraftTweakerAPI.logWarning("Entity IDs should not be settable! This method is marked for removal!");
    }
    
    /**
     * Gets all the tags that are attached to the entity.
     *
     * These are **not** tags like MCTag<EntityType>, these are tags that are added by the /tag command.
     *
     * You can read more about how they can be used here:
     * https://minecraft.fandom.com/wiki/Commands/tag
     *
     * @return A set of all the Tags that an Entity has.
     */
    @ZenCodeType.Method
    public static Set<String> getTags(Entity internal) {
        
        return internal.getTags();
    }
    
    /**
     * Adds a new tag to the Entity.
     *
     * There is a limit of 1024 tags per Entity.
     *
     * These are **not** tags like MCTag<EntityType>, these are tags that are added by the /tag command.
     *
     * You can read more about how they can be used here:
     * https://minecraft.fandom.com/wiki/Commands/tag
     *
     * @param tag The name of the tag to add.
     *
     * @return True if the tag could ba added, and it did not replace a preexisting tag. False otherwise.
     *
     * @docParam tag "foundMesa"
     */
    @ZenCodeType.Method
    public static boolean addTag(Entity internal, String tag) {
        
        return internal.addTag(tag);
    }
    
    /**
     * Removes a tag from the Entity.
     *
     * These are **not** tags like MCTag<EntityType>, these are tags that are added by the /tag command.
     *
     * You can read more about how they can be used here:
     * https://minecraft.fandom.com/wiki/Commands/tag
     *
     * @param tag The name of the tag to remove.
     *
     * @return True if the Entity had the tag and it was removed. False otherwise.
     *
     * @docParam tag "foundMesa"
     */
    @ZenCodeType.Method
    public static boolean removeTag(Entity internal, String tag) {
        
        return internal.removeTag(tag);
    }
    
    /**
     * Can be used to simulate the `/kill` command being used on the Entity.
     */
    @ZenCodeType.Method
    public static void onKillCommand(Entity internal) {
        
        internal.onKillCommand();
    }
    
    /**
     * Checks if this Entity is in the given range (distance) of the other Entity.
     *
     * @param entity   The Entity to check if it is in range.
     * @param distance The distance to check for.
     *
     * @return True if this Entity is in range. False otherwise.
     *
     * @docParam entity entity
     * @docParam distance 2.5
     */
    @ZenCodeType.Method
    public static boolean isEntityInRange(Entity internal, Entity entity, double distance) {
        
        return internal.isEntityInRange(entity, distance);
    }
    
    /**
     * Sets the position of this Entity.
     *
     * @param x The new X position of the Entity.
     * @param y The new Y position of the Entity.
     * @param z The new Z position of the Entity.
     *
     * @docParam x 5
     * @docParam y 2
     * @docParam z 59
     */
    @ZenCodeType.Method
    public static void setPosition(Entity internal, double x, double y, double z) {
        
        internal.setPosition(x, y, z);
    }
    
    /**
     * Gets the maximum amount of time the Entity needs to be in the portal before they are teleported.
     *
     * @return The amount of time required for the Entity to be in the nether portal before being teleported.
     */
    @ZenCodeType.Method
    public static int getMaxInPortalTime(Entity internal) {
        
        return internal.getMaxInPortalTime();
    }
    
    /**
     * Sets the Entity on fire for the given amount of **seconds**.
     *
     * This does not take ticks, it only takes full seconds, and you cannot lower the amount of fire ticks the entity has.
     *
     * @param seconds The amount of seconds the Entity should be on fire for.
     *
     * @docParam seconds 5
     */
    @ZenCodeType.Method
    public static void setFire(Entity internal, int seconds) {
        
        internal.setFire(seconds);
    }
    
    /**
     * Sets the Entity on fire for the given amount of **ticks**.
     *
     * This method can be used to override how long the Entity is on fire for.
     *
     * @param ticks The amount of ticks the Entity should burn for.
     *
     * @docParam ticks 25
     */
    @ZenCodeType.Method
    public static void forceFireTicks(Entity internal, int ticks) {
        
        internal.forceFireTicks(ticks);
    }
    
    /**
     * Gets the amount of ticks the Entity will be on fire for.
     *
     * @return The amount of ticks the Entity will be on fire for.
     */
    @ZenCodeType.Method
    public static int getFireTimer(Entity internal) {
        
        return internal.getFireTimer();
    }
    
    /**
     * Extinguishes the Entity if it is on fire.
     */
    @ZenCodeType.Method
    public static void extinguish(Entity internal) {
        
        internal.extinguish();
    }
    
    /**
     * Checks if the offset position from the Entity's current position is inside of a liquid.
     *
     * @param x The X offset.
     * @param y The Y offset.
     * @param z The Z offset.
     *
     * @return True if the offset position is in a liquid. False otherwise.
     *
     * @docParam x 5
     * @docParam y 4
     * @docParam z 5
     */
    @ZenCodeType.Method
    public static boolean isOffsetPositionInLiquid(Entity internal, double x, double y, double z) {
        
        return internal.isOffsetPositionInLiquid(x, y, z);
    }
    
    /**
     * Sets if the Entity should be considered on the ground or not.
     *
     * @param grounded If the Entity is on the ground or not.
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("onGround")
    public static void setOnGround(Entity internal, boolean grounded) {
        
        internal.setOnGround(grounded);
    }
    
    /**
     * Checks whether the Entity is on the ground or not.
     *
     * @return True if the Entity is on the ground. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("onGround")
    public static boolean isOnGround(Entity internal) {
        
        return internal.isOnGround();
    }
    
    /**
     * Checks if this Entity is silent.
     *
     * Silent Entities do not play sounds.
     *
     * @return True if this Entity is silent. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("silent")
    public static boolean isSilent(Entity internal) {
        
        return internal.isSilent();
    }
    
    /**
     * Sets if this Entity is silent or not.
     *
     * silent Entities do not play sounds.
     *
     * @param isSilent If the Entity should be silent or not.
     *
     * @docParam isSilent true
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("silent")
    public static void setSilent(Entity internal, boolean isSilent) {
        
        internal.setSilent(isSilent);
    }
    
    /**
     * Checks if this Entity has no gravity.
     *
     * @return True if this Entity does not have gravity. False otherwise.
     */
    @ZenCodeType.Method
    public static boolean hasNoGravity(Entity internal) {
        
        return internal.hasNoGravity();
    }
    
    /**
     * Sets this Entity to have no gravity.
     *
     * @param noGravity The new gravity value.
     *
     * @docParam noGravity true
     */
    @ZenCodeType.Method
    public static void setNoGravity(Entity internal, boolean noGravity) {
        
        internal.setNoGravity(noGravity);
    }
    
    /**
     * Checks if this Entity is immune to fire.
     *
     * @return True if this Entity is immune to fire. False otherwise.
     */
    @ZenCodeType.Method
    public static boolean isImmuneToFire(Entity internal) {
        
        return internal.isImmuneToFire();
    }
    
    /**
     * Can be used to simulate the Entity falling the given distance with the given damage multiplier.
     *
     * @param distance         The distance the Entity has fallen.
     * @param damageMultiplier The damage multiplier.
     *
     * @return True if the Entity took damage. False otherwise.
     *
     * @docParam distance 5
     * @docParam damageMultiplier 5
     */
    @ZenCodeType.Method
    public static boolean onLivingFall(Entity internal, float distance, float damageMultiplier) {
        
        return internal.onLivingFall(distance, damageMultiplier);
    }
    
    /**
     * Checks if this Entity is in water.
     *
     * @return True if this Entity is in water. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("inWater")
    public static boolean isInWater(Entity internal) {
        
        return internal.isInWater();
    }
    
    /**
     * Checks if this Entity is wet.
     *
     * @return True if this Entity is wet. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("isWet")
    public static boolean isWet(Entity internal) {
        
        return internal.isWet();
    }
    
    /**
     * Checks if this Entity is in rain or a bubble column.
     *
     * @return True if this entity is in rain or a bubble column. False otherwise.
     */
    @ZenCodeType.Method
    public static boolean isInWaterRainOrBubbleColumn(Entity internal) {
        
        return internal.isInWaterRainOrBubbleColumn();
    }
    
    /**
     * Checks if this Entity is in water or a bubble column.
     *
     * @return True if this entity is in water or a bubble column. False otherwise.
     */
    @ZenCodeType.Method
    public static boolean isInWaterOrBubbleColumn(Entity internal) {
        
        return internal.isInWaterOrBubbleColumn();
    }
    
    /**
     * Checks if this Entity can swim.
     *
     * @return True if this Entity can swim. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("canSwim")
    public static boolean canSwim(Entity internal) {
        
        return internal.canSwim();
    }
    
    /**
     * Checks if this Entity is in lava or not.
     *
     * @return True if the this Entity is in lava.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("inLava")
    public static boolean isInLava(Entity internal) {
        
        return internal.isInLava();
    }
    
    /**
     * Gets how bright this Entity is.
     *
     * @return How bright the Entity is.
     */
    @ZenCodeType.Method
    public static float getBrightness(Entity internal) {
        
        return internal.getBrightness();
    }
    
    /**
     * Forcefully moves this Entity to the new position.
     *
     * @param x The new X value.
     * @param y The new Y value.
     * @param z The new Z value.
     *
     * @docParam x 5
     * @docParam y 2
     * @docParam z 9
     */
    @ZenCodeType.Method
    public static void moveForced(Entity internal, double x, double y, double z) {
        
        internal.moveForced(x, y, z);
    }
    
    /**
     * Forcefully sets this Entity to the new position.
     *
     * @param x The new X value.
     * @param y The new Y value.
     * @param z The new Z value.
     *
     * @docParam x 5
     * @docParam y 2
     * @docParam z 9
     */
    @ZenCodeType.Method
    public static void forceSetPosition(Entity internal, double x, double y, double z) {
        
        internal.forceSetPosition(x, y, z);
    }
    
    /**
     * Gets the distance between this Entity and the given Entity.
     *
     * @param other The Entity to get the distance to.
     *
     * @return The distance between this Entity and the other Entity.
     *
     * @docParam other entity
     */
    @ZenCodeType.Method
    public static float getDistance(Entity internal, Entity other) {
        
        return internal.getDistance(other);
    }
    
    /**
     * Gets the squared distance from this Entity's position to the given position.
     *
     * @param x The X value of the position to check against.
     * @param y The Y value of the position to check against.
     * @param z The Z value of the position to check against.
     *
     * @return The squared distance from this Entity to the given position.
     *
     * @docParam x 5
     * @docParam y 6
     * @docParam z 3
     */
    @ZenCodeType.Method
    public static double getDistanceSq(Entity internal, double x, double y, double z) {
        
        return internal.getDistanceSq(x, y, z);
    }
    
    /**
     * Gets the squared distance from this Entity to the given Entity.
     *
     * @param other The other Entity to check the squared distance to.
     *
     * @return The squared distance between this Entity and the other Entity.
     *
     * @docParam other entity
     */
    @ZenCodeType.Method
    public static double getDistanceSq(Entity internal, Entity other) {
        
        return internal.getDistanceSq(other);
    }
    
    /**
     * Triggers the collide effect between this Entity and the player.
     *
     * Some examples of collide effects are:
     * Puffer fish damaging and applying poison.
     * Experience orbs being collected.
     *
     * @param playerEntity The player to collide with.
     *
     * @docParam playerEntity player
     */
    @ZenCodeType.Method
    public static void onCollideWithPlayer(Entity internal, PlayerEntity playerEntity) {
        
        internal.onCollideWithPlayer(playerEntity);
    }
    
    /**
     * Applies entity collision between this Entity and the other Entity, pushing them away from each other.
     *
     * @param other The Entity to collide with.
     *
     * @docParam other entity
     */
    @ZenCodeType.Method
    public static void applyEntityCollision(Entity internal, Entity other) {
        
        internal.applyEntityCollision(other);
    }
    
    /**
     * Adds velocity to this Entity.
     *
     * @param x The amount of X velocity to add.
     * @param y The amount of Y velocity to add.
     * @param z The amount of Z velocity to add.
     *
     * @docParam x 5
     * @docParam y 9
     * @docParam z -1
     */
    @ZenCodeType.Method
    public static void addVelocity(Entity internal, double x, double y, double z) {
        
        internal.addVelocity(x, y, z);
    }
    
    /**
     * Gets the name of the Entity.
     *
     * @return The name of the Entity.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("name")
    public static String getName(Entity internal) {
        
        return internal.getName().getString();
    }
    
    /**
     * Gets which directions the Entity is currently facing.
     *
     * @return An array of direction that the Entity is currently facing
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("facingDirections")
    public static MCDirection[] getFacingDirections(Entity internal) {
        
        return Arrays.stream(Direction.getFacingDirections(internal)).map(MCDirection::get).toArray(MCDirection[]::new);
    }
    
    /**
     * Teleports this Entity to the given world.
     *
     * @param world The new world for the Entity.
     *
     * @docParam world world
     */
    @ZenCodeType.Method
    public static void changeDimension(Entity internal, ServerWorld world) {
        
        internal.changeDimension(world);
    }
    
    /**
     * Teleports the entity, forcing the destination to stay loaded for a short time
     *
     * @docParam x 20
     * @docParam y 40
     * @docParam z 60
     */
    @ZenCodeType.Method
    public static void teleportKeepLoaded(Entity internal, double x, double y, double z) {
        
        internal.teleportKeepLoaded(x, y, z);
    }
    
    /**
     * Gets the UUID of this Entity.
     *
     * @return The UUID of this Entity.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("uuid")
    public static String getUUID(Entity internal) {
        
        return internal.getUniqueID().toString();
    }
    
    /**
     * Gets the NBT data of this Entity.
     *
     * @return The NBT data of this Entity.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("data")
    public static MapData getData(Entity internal) {
        
        return new MapData(internal.serializeNBT());
    }
    
    /**
     * Updates the NBT data of this Entity.
     *
     * @param data The new Data for this Entity
     *
     * @docParam data {key: "value"}
     */
    @ZenCodeType.Method
    public static void updateData(Entity internal, MapData data) {
        
        internal.deserializeNBT(internal.serializeNBT().merge(data.getInternal()));
    }
    
    
    //TODO: Add the other methods
    // Tip: Use IDE's "delegate" method and go from there
}
