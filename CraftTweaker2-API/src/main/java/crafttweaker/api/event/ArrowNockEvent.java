package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;


@ZenClass("crafttweaker.event.ArrowNockEvent")
@ZenRegister
public interface ArrowNockEvent extends IPlayerEvent, IEventHasResult {

    @ZenGetter("bow")
    IItemStack getBow();
    
    @ZenGetter("hand")
    String getHand();
}
