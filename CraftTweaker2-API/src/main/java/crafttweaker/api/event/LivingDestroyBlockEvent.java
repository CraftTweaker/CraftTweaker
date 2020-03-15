package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("crafttweaker.event.LivingDestroyBlockEvent")
@ZenRegister
public interface LivingDestroyBlockEvent extends ILivingEvent, IEventPositionable, IEventCancelable {
    @ZenGetter("state")
    IBlockState getState();
}
