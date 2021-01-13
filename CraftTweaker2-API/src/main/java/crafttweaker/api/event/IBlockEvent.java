package crafttweaker.api.event;


import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlock;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("crafttweaker.event.IBlockEvent")
@ZenRegister
public interface IBlockEvent extends IEventPositionable, IWorldEvent {

    @ZenGetter("world")
    IWorld getWorld();

    @ZenGetter("blockState")
    IBlockState getBlockState();

    @ZenGetter("block")
    default IBlock getBlock() {
        return getWorld().getBlock(getPosition());
    }
}
