package crafttweaker.api.world;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.world.IBlockAccess")
@ZenRegister
public interface IBlockAccess {
    //@ZenMethod
    //ITileEntity getTileEntityAt(BlockPos pos);
    
    @ZenMethod
    IBlockState getBlockState(IBlockPos pos);
    
    @ZenMethod
    boolean isAirBlock(IBlockPos pos);
    
    @ZenMethod
    int getStrongPower(IBlockPos pos, IFacing facing);
    
    Object getInternal();
}
