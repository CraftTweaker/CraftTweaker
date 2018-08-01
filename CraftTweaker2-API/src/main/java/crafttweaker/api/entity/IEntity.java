package crafttweaker.api.entity;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.*;
import crafttweaker.api.command.ICommandSender;
import crafttweaker.api.damage.IDamageSource;
import crafttweaker.api.data.IData;
import crafttweaker.api.game.ITeam;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.util.Position3f;
import crafttweaker.api.world.*;
import stanhebben.zenscript.annotations.*;

import java.util.List;

/**
 * Entity interface. Used to obtain information about entities, and modify their
 * data. Entities are any item that is freely movable in the world, such as
 * players, monsters, items on the ground, ...
 *
 * @author Stan Hebben
 */
@ZenClass("crafttweaker.entity.IEntity")
@ZenRegister
public interface IEntity extends ICommandSender {
    
    @ZenGetter("definition")
    IEntityDefinition getDefinition();
    
    
    /**
     * Retrieves the world this entity is in.
     *
     * @return the current world of this entity
     */
    
    @ZenSetter("world")
    void setWorld(IWorld world);
    
    /**
     * Retrieves the dimension id this entity is in.
     *
     * @return current dimension of this entity
     */
    @ZenGetter("dimension")
    @ZenMethod
    int getDimension();
    
    @ZenSetter("dimension")
    void setDimension(int dimensionID);
    
    /**
     * Retrieves the x position of this entity.
     *
     * @return entity x position
     */
    @ZenMethod
    @ZenGetter("x")
    double getX();
    
    /**
     * Retrieves the y position of this entity.
     *
     * @return entity y position
     */
    @ZenMethod
    @ZenGetter("y")
    double getY();
    
    /**
     * Retrieves the z position of this entity.
     *
     * @return entity z position
     */
    @ZenMethod
    @ZenGetter("z")
    double getZ();
    
    /**
     * Retrieves the position of this entity.
     *
     * @return entity position
     */
    @ZenMethod
    @ZenGetter("position3f")
    Position3f getPosition3f();
    
    /**
     * Sets the position of this entity. Instantly moves (teleports) the entity
     * to that position.
     *
     * @param position entity position
     */
    @ZenMethod
    @ZenSetter("position")
    void setPosition(IBlockPos position);
    
    /**
     * Set an entity to dead, will be removed during the next tick.
     */
    @ZenMethod
    void setDead();
    
    /**
     * Lights an entity on fire.
     *
     * @param seconds the number of seconds the fire should last.
     */
    @ZenMethod
    @ZenSetter("fire")
    void setFire(int seconds);
    
    /**
     * Sets an entity to no longer be on fire.
     */
    @ZenMethod
    void extinguish();
    
    /**
     * @return whether an entity is in water or being rained on.
     */
    @ZenMethod
    @ZenGetter("wet")
    boolean isWet();
    
    /**
     * @return a list of all entities riding this entity.
     */
    @ZenMethod
    @ZenGetter("passengers")
    List<IEntity> getPassengers();
    
    /**
     * @param entity the entity to check distance to.
     *
     * @return the distance between this entity and that entity.
     */
    @ZenMethod
    double getDistanceSqToEntity(IEntity entity);
    
    /**
     * @return whether the entity is alive or not.
     */
    @ZenMethod
    @ZenGetter("alive")
    boolean isAlive();
    
    /**
     * @return the entity this entity is riding.
     */
    @ZenMethod
    @ZenGetter("ridingEntity")
    IEntity getRidingEntity();
    
    /**
     * @return an ItemStack Representation of this Entity. (EX. Item Minecart coming from a minecart)
     */
    @ZenMethod
    IItemStack getPickedResult();
    
    /**
     * @return The custom name tag this entity has.
     */
    @ZenMethod
    @ZenGetter("customName")
    String getCustomName();
    
    /**
     * @param name the custom name to set to this entity.
     */
    @ZenMethod
    @ZenSetter("customName")
    void setCustomName(String name);
    
    /**
     * @return Is entity immune to fire
     */
    @ZenMethod
    @ZenGetter("immuneToFire")
    boolean isImmuneToFire();
    
    /**
     * @return amount of air in seconds
     */
    @ZenMethod
    @ZenGetter("air")
    int getAir();
    
    /**
     * @param seconds amount of air in seconds to add.
     */
    @ZenMethod
    @ZenSetter("air")
    void setAir(int seconds);
    
    /**
     * @return The Actual Entity, MUST EXTEND ENTITY.
     */
    Object getInternal();
    
    @ZenMethod
    boolean canTrample(IWorld world, IBlockDefinition block, IBlockPos pos, float fall);
    
    @ZenMethod
    void onEntityUpdate();
    
    @ZenMethod
    void onUpdate();
    
    @ZenGetter
    boolean isSprinting();
    
    @ZenSetter("isSprinting")
    void setSprinting(boolean sprinting);
    
    @ZenGetter
    boolean isGlowing();
    
    @ZenSetter("isGlowing")
    void setGlowing(boolean glowing);
    
    @ZenGetter("id")
    int getID();
    
    @ZenSetter("id")
    void setID(int id);
    
    @ZenGetter("tags")
    List<String> getTags();
    
    @ZenMethod
    void addTag(String tag);
    
    @ZenMethod
    void removeTag(String tag);
    
    @ZenMethod
    void onKillCommand();
    
    @ZenGetter("maxInPortalTime")
    int getMaxInPortalTime();
    
    //@ZenMethod
    //void playSound(ISoundEvent sound, float arg1, float arg2);
    
    @ZenGetter("portalCooldown")
    int getPortalCooldown();
    
    @ZenGetter
    boolean isSilent();
    
    @ZenSetter("isSilent")
    void setSilent(boolean silent);
    
    @ZenGetter
    boolean hasNoGravity();
    
    //@ZenGetter("collisionBoundingBox")
    //IAxisAlignedBB getCollisionBoundingBox();
    
    //@ZenGetter("entityBoundingBox")
    //IAxisAlignedBB getEnityBoundingBox();
    
    //@ZenSetter("entityBoundingBox")
    //void setEntityBoundingBox(IAxisAlignedBB boundingBox);
    
    @ZenSetter("hasNoGravity")
    void setNoGravity(boolean noGravity);
    
    @ZenGetter
    boolean isInWater();
    
    @ZenGetter
    boolean isOverWater();
    
    @ZenMethod
    void spawnRunningParticles();
    
    @ZenMethod
    boolean isInsideOfMaterial(IMaterial material);
    
    @ZenGetter
    boolean isInLava();
    
    @ZenMethod
    boolean attackEntityFrom(IDamageSource source, float amount);
    
    @ZenGetter
    boolean canBeCollidedWith();
    
    @ZenGetter
    boolean canBePushed();
    
    @ZenMethod
    IData getNBT();
    
    @ZenMethod
    IEntityItem dropItem(IItemStack itemStack, @Optional float offset);
    
    @ZenGetter
    boolean isInsideOpaqueBlock();
    
    @ZenMethod
    void removePassengers();
    
    @ZenMethod
    void dismountRidingEntity();
    
    @ZenGetter("heldEquipment")
    List<IItemStack> getHeldEquipment();
    
    @ZenGetter("armorInventory")
    List<IItemStack> getArmorInventoryList();
    
    @ZenGetter("equipmentAndArmor")
    List<IItemStack> getEquipmentAndArmor();
    
    @ZenGetter
    boolean isBurning();
    
    @ZenGetter
    boolean isRiding();
    
    @ZenGetter
    boolean isBeingRidden();
    
    @ZenGetter
    boolean isSneaking();
    
    @ZenSetter("isSneaking")
    void setSneaking(boolean sneaking);
    
    @ZenGetter
    boolean isInvisible();
    
    @ZenGetter("team")
    ITeam getTeam();
    
    @ZenSetter("isInvisible")
    void setInvisible(boolean invisible);
    
    @ZenMethod
    boolean isOnSameTeam(IEntity other);
    
    @ZenMethod
    void setInWeb();
    
    @ZenMethod("parts")
    IEntity[] getParts();
    
    @ZenMethod
    boolean isEntityEqual(IEntity other);
    
    @ZenGetter
    boolean canBeAttackedWithItem();
    
    @ZenMethod
    boolean isInvulnerableTo(IDamageSource source);
    
    @ZenGetter
    boolean isInvulnerable();
    
    @ZenSetter("isInvulnerable")
    void setInvulnerable(boolean invulnerable);
    
    @ZenMethod
    void setToLocationFrom(IEntity other);
    
    @ZenGetter
    boolean isBoss();
    
    @ZenGetter("maxFallHeight")
    int getMaxFallHeight();
    
    @ZenGetter
    boolean doesTriggerPressurePlate();
    
    @ZenGetter
    boolean isPushedByWater();
    
    @ZenGetter
    boolean hasCustomName();
    
    @ZenGetter("alwaysRenderNameTag")
    boolean alwaysRenderNameTag();
    
    @ZenSetter("alwaysRenderNameTag")
    void setAlwaysRenderNameTag(boolean alwaysRenderNameTag);
    
    @ZenGetter("eyeHeight")
    float getEyeHight();
    
    @ZenGetter
    boolean isOutsideBorder();
    
    @ZenSetter("isOutsideBorder")
    void setOutsideBorder(boolean outsideBorder);
    
    @ZenGetter
    boolean isImmuneToExplosions();
    
    @ZenGetter
    boolean shouldRiderSit();
    
    @ZenGetter
    boolean canRiderInteract();
    
    @ZenMethod
    boolean shouldRiderDismountInWater(IEntity rider);
    
    @ZenGetter("controllingPassenger")
    IEntity getControllingPassenger();
    
    @ZenMethod
    boolean isPassenger(IEntity entity);
    
    @ZenGetter("passengersRecursive")
    List<IEntity> getPassengersRecursive();
    
    @ZenGetter("lowestRidingEntity")
    IEntity getLowestRidingEntity();
    
    @ZenMethod
    boolean isRidingSameEntity(IEntity other);
    
    @ZenGetter
    boolean canPassengerSteer();
    
    //@ZenGetter("soundCategory")
    //ISoundCategory getSoundCategory();
    
    @ZenGetter("rotationYaw")
    float getRotationYaw();
    
    @ZenSetter("rotationYaw")
    void setRotationYaw(float rotationYaw);
    
    @ZenGetter("rotationPitch")
    float getRotationPitch();
    
    @ZenSetter("rotationPitch")
    void setRotationPitch(float rotationPitch);
    
    @ZenGetter("motionX")
    double getMotionX();
    
    @ZenSetter("motionX")
    void setMotionX(double motionX);
    
    @ZenGetter("motionY")
    double getMotionY();
    
    @ZenSetter("motionY")
    void setMotionY(double motionY);
    
    @ZenGetter("motionZ")
    double getMotionZ();
    
    @ZenSetter("motionZ")
    void setMotionZ(double motionZ);
    
    @ZenGetter("posX")
    double getPosX();
    
    @ZenSetter("posX")
    void setPosX(double posX);
    
    @ZenGetter("posY")
    double getPosY();
    
    @ZenSetter("posY")
    void setPosY(double posY);
    
    @ZenGetter("posZ")
    double getPosZ();
    
    @ZenSetter("posZ")
    void setPosZ(double posZ);
    
    @ZenGetter("lookingDirection")
    IVector3d getLookingDirection();
    
    @ZenMethod
    IRayTraceResult getRayTrace(double blockReachDistance, float partialTicks);
}
