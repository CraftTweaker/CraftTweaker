package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.container.IContainer;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerOpenContainerEvent")
@ZenRegister
public interface PlayerOpenContainerEvent extends IEventCancelable, PlayerEvent {
    
    @ZenGetter("container")
    IContainer getContainer();
}
