package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerInteractEntityEvent")
@ZenRegister
public interface PlayerInteractEntityEvent extends IEventCancelable, PlayerInteractEvent {

    @ZenGetter("target")
    IEntity getTarget();
}
