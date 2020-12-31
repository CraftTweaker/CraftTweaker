package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.world.IFacing;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 * @author youyihj
 */
@ZenRegister
@ZenClass("crafttweaker.event.BlockNeighborNotifyEvent")
public interface BlockNeighborNotifyEvent extends IBlockEvent, IEventCancelable {
    @ZenGetter("forceRedstoneUpdate")
    boolean getForceRedstoneUpdate();

    @ZenGetter("notifiedSides")
    IFacing[] notifiedSides();
}
