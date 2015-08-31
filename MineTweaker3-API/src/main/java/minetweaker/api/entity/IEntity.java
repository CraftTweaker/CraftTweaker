package minetweaker.api.entity;

import minetweaker.api.util.Position3f;
import minetweaker.api.world.IDimension;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;

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
	public IDimension getDimension();

	/**
	 * Retrieves the x position of this entity.
	 * 
	 * @return entity x position
	 */
	@ZenGetter("x")
	public float getX();

	/**
	 * Retrieves the y position of this entity.
	 * 
	 * @return entity y position
	 */
	@ZenGetter("y")
	public float getY();

	/**
	 * Retrieves the z position of this entity.
	 * 
	 * @return entity z position
	 */
	@ZenGetter("z")
	public float getZ();

	/**
	 * Retrieves the position of this entity.
	 * 
	 * @return entity position
	 */
	@ZenGetter("position")
	public Position3f getPosition();

	/**
	 * Sets the position of this entity. Instantly moves (teleports) the entity
	 * to that position.
	 * 
	 * @param position entity position
	 */
	@ZenSetter("position")
	public void setPosition(Position3f position);
}
