package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.container.IContainer;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerOpenContainerEvent")
@ZenRegister
public interface PlayerOpenContainerEvent extends IEventCancelable, IPlayerEvent {
    
    @ZenGetter("container")
    IContainer getContainer();
}
