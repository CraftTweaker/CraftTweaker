package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 * @author Noob
 */
@ZenClass("crafttweaker.event.IExplosionEvent")
@ZenRegister
public interface IExplosionEvent {
    @ZenGetter("world")
    IWorld getWorld();

    @ZenGetter("position")
    IBlockPos getPosition();

    @ZenGetter("x")
    double getX();

    @ZenGetter("y")
    double getY();

    @ZenGetter("z")
    double getZ();
}
