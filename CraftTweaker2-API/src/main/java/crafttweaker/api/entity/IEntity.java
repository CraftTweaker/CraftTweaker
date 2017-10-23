package crafttweaker.api.entity;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.util.Position3f;
import crafttweaker.api.world.IDimension;
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
public interface IEntity {
    
    /**
     * Retrieves the dimension this entity is in.
     *
     * @return current dimension of this entity
     */
    @ZenGetter("dimension")
    IDimension getDimension();
    
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
    @ZenGetter("position")
    Position3f getPosition();
    
    /**
     * Sets the position of this entity. Instantly moves (teleports) the entity
     * to that position.
     *
     * @param position entity position
     */
    @ZenMethod
    @ZenSetter("position")
    void setPosition(Position3f position);
    
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
}
