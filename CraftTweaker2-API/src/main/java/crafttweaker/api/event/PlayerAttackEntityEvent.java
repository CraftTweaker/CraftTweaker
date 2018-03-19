package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerAttackEntityEvent")
@ZenRegister
public interface PlayerAttackEntityEvent extends IEventCancelable, PlayerEvent {
    
    @ZenGetter("entity")
    IEntity getEntity();
}
