package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.world.IFacing;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 * @author youyihj
 */
@ZenRegister
@ZenClass("crafttweaker.event.BlockNeighbourNotifyEvent")
public interface BlockNeighbourNotifyEvent extends IBlockEvent, IEventCancelable {
    @ZenGetter("forceRedstoneUpdate")
    boolean getForceRedstoneUpdate();

    @ZenGetter("notifiedSides")
    IFacing[] notifiedSides();
}
