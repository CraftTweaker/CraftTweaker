package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityItem;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerPickupItemEvent")
@ZenRegister
public interface PlayerPickupItemEvent extends PlayerEvent {
    
    @ZenGetter("item")
    IEntityItem getItem();
}
