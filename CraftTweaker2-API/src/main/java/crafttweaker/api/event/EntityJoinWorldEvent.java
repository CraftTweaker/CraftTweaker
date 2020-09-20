package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("crafttweaker.event.EntityJoinWorldEvent")
@ZenRegister
public interface EntityJoinWorldEvent extends IEntityEvent, IEventCancelable {

    @ZenGetter("world")
    IWorld getWorld();
}
