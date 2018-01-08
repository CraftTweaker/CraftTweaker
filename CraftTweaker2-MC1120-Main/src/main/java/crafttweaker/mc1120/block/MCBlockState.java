package crafttweaker.mc1120.block;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.block.*;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.*;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

public class MCBlockState extends MCBlockProperties implements crafttweaker.api.block.IBlockState {
    
    private final IBlockState blockState;
    private IBlock block;
    
    public MCBlockState(IBlockState blockState) {
        super(blockState);
        this.blockState = blockState;
        Block block = blockState.getBlock();
        int meta = block.getMetaFromState(blockState);
        this.block = new MCSpecificBlock(block, meta);
    }
    
    @Override
    public IBlock getBlock() {
        return block;
    }
    
    @Override
    public int getMeta() {
        return block.getMeta();
    }
    
    @Override
    public boolean isReplaceable(IWorld world, IBlockPos blockPos) {
        return blockState.getBlock().isReplaceable((World) world.getInternal(), (BlockPos) blockPos.getInternal());
    }
    
    @Override
    public int compare(crafttweaker.api.block.IBlockState other) {
        int result = 0;
        if(!this.getInternal().equals(other.getInternal())) {
            if(blockState.getBlock().equals(((IBlockState) other.getInternal()).getBlock())) {
                result = Integer.compare(this.getMeta(), other.getMeta());
            } else {
                int blockId = ((ForgeRegistry<Block>) ForgeRegistries.BLOCKS).getID(((IBlockState) this.getInternal()).getBlock());
                int otherBlockId = ((ForgeRegistry<Block>) ForgeRegistries.BLOCKS).getID(((IBlockState) other.getInternal()).getBlock());
                result = Integer.compare(blockId, otherBlockId);
            }
        }
        return result;
    }
    
}
