package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenRegister
@ZenClass("crafttweaker.event.PlayerSmeltedEvent")
public interface PlayerSmeltedEvent extends IPlayerEvent {
    
    @ZenGetter("output")
    IItemStack getOutput();
}
