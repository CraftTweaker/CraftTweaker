package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlock;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.world.IRayTraceResult;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerFillBucketEvent")
@ZenRegister
public interface PlayerFillBucketEvent extends IEventCancelable, IPlayerEvent, IProcessableEvent, IEventPositionable {

    @ZenGetter("result")
    IItemStack getResult();

    /**
     * Sets the resulting item. Automatically processes the event, too.
     *
     * @param result resulting item
     */
    @ZenSetter("result")
    void setResult(IItemStack result);

    @ZenGetter("emptyBucket")
    IItemStack getEmptyBucket();

    @ZenGetter("world")
    IWorld getWorld();


    @ZenGetter("block")
    IBlock getBlock();

    @ZenGetter("blockState")
    IBlockState getBlockState();

    @ZenGetter("dimension")
    int getDimension();

    @ZenGetter("rayTraceResult")
    IRayTraceResult getRayTraceResult();
}
