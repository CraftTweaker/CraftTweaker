package crafttweaker.api.event;


import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.*;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.event.IBlockEvent")
@ZenRegister
public interface IBlockEvent extends IEventPositionable {
    
    @ZenGetter("world")
    IWorld getWorld();
    
    @ZenGetter("blockState")
    IBlockState getBlockState();
    
    @ZenGetter("block")
    default IBlock getBlock(){
        return getBlockState().getBlock();
    }
}
