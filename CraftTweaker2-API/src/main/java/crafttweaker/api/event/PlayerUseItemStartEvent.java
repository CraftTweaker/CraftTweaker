package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerUseItemStartEvent")
@ZenRegister
public interface PlayerUseItemStartEvent extends IEventCancelable, IPlayerEvent {
    
    @ZenGetter("item")
    IItemStack getItem();
}
