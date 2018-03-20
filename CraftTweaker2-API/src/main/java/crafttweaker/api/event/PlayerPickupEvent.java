package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerPickupEvent")
@ZenRegister
public interface PlayerPickupEvent extends IEventCancelable, IPlayerEvent, IProcessableEvent {
    
    @ZenGetter("entity")
    IEntity getEntity();
}
