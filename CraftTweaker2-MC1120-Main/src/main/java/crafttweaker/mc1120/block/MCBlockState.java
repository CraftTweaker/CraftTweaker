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

public class MCBlockState implements crafttweaker.api.block.IBlockState {
    
    private final IBlockState blockState;
    private IBlock block;
    
    public MCBlockState(IBlockState blockState) {
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
    public int getLightValue(IWorld world, IBlockPos blockPos) {
        return blockState.getLightValue((World) world.getInternal(), (BlockPos) blockPos.getInternal());
    }
    
    @Override
    public boolean canProvidePower() {
        return blockState.canProvidePower();
    }
    
    @Override
    public int getWeakPower(IWorld world, IBlockPos blockPos, IFacing facing) {
        return blockState.getWeakPower((World) world.getInternal(), (BlockPos) blockPos.getInternal(), (EnumFacing) facing.getInternal());
    }
    
    @Override
    public int getComparatorInputOverride(IWorld world, IBlockPos blockPos) {
        return blockState.getComparatorInputOverride((World) world.getInternal(), (BlockPos) blockPos.getInternal());
    }
    
    @Override
    public IMobilityFlag getMobilityFlag() {
        return new MCMobilityFlag(blockState.getMobilityFlag());
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
    
    @Override
    public Object getInternal() {
        return blockState;
    }
    
    @Override
    public IMaterial getMaterial() {
        return new MCMaterial(blockState.getMaterial());
    }
    
    @Override
    public boolean canEntitySpawn(IEntity entity) {
        return entity.getInternal() instanceof Entity && blockState.canEntitySpawn((Entity) entity.getInternal());
    }
    
    @Override
    public boolean causesSuffocation() {
        return blockState.causesSuffocation();
    }
    
    @Override
    public boolean doesSideBlockRendering(IWorld world, IBlockPos pos, IFacing facing) {
        return blockState.doesSideBlockRendering((World) world.getInternal(), (BlockPos) pos.getInternal(), (EnumFacing) facing.getInternal());
    }
    
    @Override
    public crafttweaker.api.block.IBlockState getActualState(IWorld world, IBlockPos pos) {
        return new MCBlockState(blockState.getActualState((World) world.getInternal(), (BlockPos) pos.getInternal()));
    }
    
    @Override
    public float getAmbientOcclusionLightValue() {
        return blockState.getAmbientOcclusionLightValue();
    }
    
    @Override
    public float getBlockHardness(IWorld world, IBlockPos pos) {
        return blockState.getBlockHardness((World) world.getInternal(), (BlockPos) pos.getInternal());
    }
    
    @Override
    public int getLightOpacy() {
        CraftTweakerAPI.logWarning("IBlockState.getLightOpacy is deprecated!");
        return blockState.getLightOpacity();
    }
    
    @Override
    public int getLightOpacy(IWorld world, IBlockPos pos) {
        return blockState.getLightOpacity((World) world.getInternal(), (BlockPos) pos.getInternal());
    }
    
    @Override
    public int getLightValue() {
        CraftTweakerAPI.logWarning("IBlockState.getLightValue is deprecated!");
        return blockState.getLightValue();
    }
    
    @Override
    public int getPackedLightMapCoords(IWorld world, IBlockPos pos) {
        return blockState.getPackedLightmapCoords((World) world.getInternal(), (BlockPos) pos.getInternal());
    }
    
    @Override
    public float getPlayerRelativeBlockHardness(IPlayer player, IWorld world, IBlockPos pos) {
        if(!(player.getInternal() instanceof EntityPlayer))
            return 0;
        return blockState.getPlayerRelativeBlockHardness((EntityPlayer) player.getInternal(), (World) world.getInternal(), (BlockPos) pos.getInternal());
    }
    
    @Override
    public int getStrongPower(IWorld world, IBlockPos pos, IFacing facing) {
        return blockState.getStrongPower((World) world.getInternal(), (BlockPos) pos.getInternal(), (EnumFacing) facing.getInternal());
    }
    
    @Override
    public boolean hasCustomBreakingProgress() {
        return blockState.hasCustomBreakingProgress();
    }
    
    @Override
    public boolean isBlockNormalCube() {
        return blockState.isBlockNormalCube();
    }
    
    @Override
    public boolean isFullBlock() {
        return blockState.isFullBlock();
    }
    
    @Override
    public boolean isFullCube() {
        return blockState.isFullCube();
    }
    
    @Override
    public boolean isNormalCube() {
        return blockState.isNormalCube();
    }
    
    @Override
    public boolean isOpaqueCube() {
        return blockState.isOpaqueCube();
    }
    
    @Override
    public boolean isSideSolid(IWorld world, IBlockPos pos, IFacing facing) {
        return blockState.isSideSolid((World) world.getInternal(), (BlockPos) pos.getInternal(), (EnumFacing) facing.getInternal());
    }
    
    @Override
    public boolean isTopSolid() {
        return blockState.isTopSolid();
    }
    
    @Override
    public boolean isTranslucent() {
        return blockState.isTranslucent();
    }
    
    @Override
    public boolean shouldSideBeRendered(IWorld world, IBlockPos pos, IFacing facing) {
        return blockState.shouldSideBeRendered((World) world.getInternal(), (BlockPos) pos.getInternal(), (EnumFacing) facing.getInternal());
    }
    
    @Override
    public boolean useNeighborBrightness() {
        return blockState.useNeighborBrightness();
    }
    
}
