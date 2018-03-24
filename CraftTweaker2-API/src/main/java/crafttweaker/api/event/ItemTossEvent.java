package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityItem;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.event.ItemTossEvent")
@ZenRegister
public interface ItemTossEvent extends IEntityEvent, IEventCancelable {
    
    @ZenGetter("player")
    IPlayer getPlayer();
    
    @ZenGetter("item")
    IEntityItem getItem();
}
