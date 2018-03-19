package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerInteractEntityEvent")
@ZenRegister
public interface PlayerInteractEntityEvent extends IEventCancelable, PlayerEvent {
    
    @ZenGetter("entity")
    public IEntity getEntity();
}
