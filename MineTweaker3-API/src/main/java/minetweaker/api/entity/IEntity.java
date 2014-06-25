/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.entity;

import minetweaker.api.util.Position3f;
import minetweaker.api.world.IDimension;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;

/**
 *
 * @author Stan
 */
@ZenClass("minetweaker.entity.IEntity")
public interface IEntity {
	@ZenGetter("world")
	public IDimension getWorld();
	
	@ZenGetter("x")
	public float getX();
	
	@ZenGetter("y")
	public float getY();
	
	@ZenGetter("z")
	public float getZ();
	
	@ZenGetter("position")
	public Position3f getPosition();
	
	@ZenSetter("position")
	public void setPosition(Position3f position);
}
