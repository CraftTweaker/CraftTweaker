package minetweaker.api.entity;

import minetweaker.api.util.Position3f;
import minetweaker.api.world.IDimension;
import stanhebben.zenscript.annotations.*;

/**
 * Entity interface. Used to obtain information about entities, and modify their
 * data. Entities are any item that is freely movable in the world, such as
 * players, monsters, items on the ground, ...
 *
 * @author Stan Hebben
 */
@ZenClass("minetweaker.entity.IEntity")
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
    @ZenGetter("x")
    float getX();

    /**
     * Retrieves the y position of this entity.
     *
     * @return entity y position
     */
    @ZenGetter("y")
    float getY();

    /**
     * Retrieves the z position of this entity.
     *
     * @return entity z position
     */
    @ZenGetter("z")
    float getZ();

    /**
     * Retrieves the position of this entity.
     *
     * @return entity position
     */
    @ZenGetter("position")
    Position3f getPosition();

    /**
     * Sets the position of this entity. Instantly moves (teleports) the entity
     * to that position.
     *
     * @param position entity position
     */
    @ZenSetter("position")
    void setPosition(Position3f position);
}
