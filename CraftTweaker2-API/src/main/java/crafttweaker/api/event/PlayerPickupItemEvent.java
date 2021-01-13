package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityItem;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerPickupItemEvent")
@ZenRegister
public interface PlayerPickupItemEvent extends IPlayerEvent, IEventHasResult, IEventCancelable {

    @ZenGetter("item")
    IEntityItem getItem();
}
