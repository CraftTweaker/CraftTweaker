package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlock;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerInteractEvent")
@ZenRegister
public interface PlayerInteractEvent extends IPlayerEvent, IEventPositionable {

    @ZenMethod
    void damageItem(int amount);

    @ZenGetter("world")
    IWorld getWorld();

    @ZenGetter("block")
    IBlock getBlock();

    @ZenGetter("blockState")
    IBlockState getBlockState();

    @ZenGetter("dimension")
    int getDimension();

    @ZenGetter("hand")
    String getHand();

    @ZenGetter("item")
    IItemStack getUsedItem();
}
