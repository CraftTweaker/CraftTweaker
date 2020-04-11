package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerChangedDimensionEvent")
@ZenRegister
public interface PlayerChangedDimensionEvent extends IPlayerEvent {

    @ZenGetter("from")
    int getFrom();

    @ZenGetter("to")
    int getTo();

    @ZenGetter("fromWorld")
    IWorld getFromWorld();

    @ZenGetter("toWorld")
    IWorld getToWorld();
}
