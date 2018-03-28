package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityXp;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerPickupXpEvent")
@ZenRegister
public interface PlayerPickupXpEvent extends IEventCancelable, IPlayerEvent {
    
    @ZenGetter("entityXp")
    IEntityXp getEntityXp();
    
    @ZenGetter("xp")
    float getXp();
}
