package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenRegister
@ZenClass("crafttweaker.event.PlayerSmeltedEvent")
public interface PlayerSmeltedEvent extends PlayerEvent {
    
    @ZenGetter("output")
    IItemStack getOutput();
}
