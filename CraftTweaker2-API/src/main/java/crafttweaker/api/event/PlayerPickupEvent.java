package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerPickupEvent")
@ZenRegister
public interface PlayerPickupEvent extends IEventCancelable, PlayerEvent {
    
    /**
     * Processes the event (picks up the entity).
     */
    @ZenMethod
    void process();
    
    @ZenGetter("processed")
    boolean isProcessed();
    
    @ZenGetter("entity")
    IEntity getEntity();
}
