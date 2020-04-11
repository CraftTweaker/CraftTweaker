package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 * @author Noob
 */
@ZenClass("crafttweaker.event.BlockPlaceEvent")
@ZenRegister
public interface BlockPlaceEvent extends IBlockEvent, IEventCancelable {
    @ZenGetter("player")
    IPlayer getPlayer();

    @ZenGetter("current")
    IBlockState getCurrent();

    @ZenGetter("placedAgainst")
    IBlockState getPlacedAgainst();

    @ZenGetter("hand")
    String getHand();
}
