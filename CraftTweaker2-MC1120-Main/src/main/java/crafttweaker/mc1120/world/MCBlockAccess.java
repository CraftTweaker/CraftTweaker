package crafttweaker.mc1120.world;

import crafttweaker.api.block.IBlockState;
import crafttweaker.api.world.*;
import crafttweaker.mc1120.block.MCBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class MCBlockAccess implements IBlockAccess {
    private final net.minecraft.world.IBlockAccess access;
    
    public MCBlockAccess(net.minecraft.world.IBlockAccess access) {
        this.access = access;
    }
    
    @Override
    public IBlockState getBlockState(IBlockPos pos) {
        return new MCBlockState(access.getBlockState((BlockPos) pos.getInternal()));
    }
    
    @Override
    public boolean isAirBlock(IBlockPos pos) {
        return access.isAirBlock((BlockPos) pos.getInternal());
    }
    
    @Override
    public int getStrongPower(IBlockPos pos, IFacing facing) {
        return access.getStrongPower((BlockPos) pos.getInternal(), (EnumFacing) facing.getInternal());
    }
    
    @Override
    public Object getInternal() {
        return access;
    }
}
