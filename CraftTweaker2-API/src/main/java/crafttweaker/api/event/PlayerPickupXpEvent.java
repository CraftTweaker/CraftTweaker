package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityXp;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerPickupXpEvent")
@ZenRegister
public interface PlayerPickupXpEvent extends IEventCancelable, PlayerEvent {
    
    @ZenGetter("entity")
    IEntityXp getEntity();
    
    @ZenGetter("xp")
    float getXp();
}
