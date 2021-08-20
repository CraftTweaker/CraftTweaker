package crafttweaker.mc1120.dispenser;

import crafttweaker.api.block.IBlockState;
import crafttweaker.api.dispenser.IBlockSource;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IFacing;
import crafttweaker.api.world.IWorld;
import net.minecraft.block.BlockDispenser;

/**
 * @author youyihj
 */
public class MCBlockSource implements IBlockSource {
    private final net.minecraft.dispenser.IBlockSource internal;

    public MCBlockSource(net.minecraft.dispenser.IBlockSource internal) {
        this.internal = internal;
    }

    @Override
    public double getX() {
        return internal.getX();
    }

    @Override
    public double getY() {
        return internal.getY();
    }

    @Override
    public double getZ() {
        return internal.getZ();
    }

    @Override
    public IWorld getWorld() {
        return CraftTweakerMC.getIWorld(internal.getWorld());
    }

    @Override
    public IBlockState getBlockState() {
        return CraftTweakerMC.getBlockState(internal.getBlockState());
    }

    @Override
    public IBlockPos getPos() {
        return CraftTweakerMC.getIBlockPos(internal.getBlockPos());
    }

    @Override
    public IFacing getFacing() {
        return CraftTweakerMC.getIFacing(internal.getBlockState().getValue(BlockDispenser.FACING));
    }

    @Override
    public Object getInternal() {
        return internal;
    }
}
