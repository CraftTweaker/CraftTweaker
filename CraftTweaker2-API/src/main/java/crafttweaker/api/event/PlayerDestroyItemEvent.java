package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.event.PlayerDestroyItemEvent")
@ZenRegister
public interface PlayerDestroyItemEvent extends IPlayerEvent{
    
    @ZenGetter("originalItem")
    IItemStack getOriginalItem();
    
    @ZenGetter("hand")
    String getHand();
    
}
