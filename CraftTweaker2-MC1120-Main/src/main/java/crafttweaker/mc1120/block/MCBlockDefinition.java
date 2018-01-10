package crafttweaker.mc1120.block;

import crafttweaker.api.block.*;
import crafttweaker.api.creativetabs.ICreativeTab;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.world.*;
import crafttweaker.mc1120.creativetabs.MCCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author Stan
 */
public class MCBlockDefinition implements IBlockDefinition {
    
    private final Block block;
    
    public MCBlockDefinition(Block block) {
        this.block = block;
    }
    
    public Block getInternalBlock() {
        return block;
    }
    
    @Override
    public String getId() {
        return block.getRegistryName().toString();
    }
    
    @Override
    public String getDisplayName() {
        return block.getLocalizedName();
    }
    
    @Override
    public Object getInternal() {
        return block;
    }
    
    @Override
    public void setLightOpacity(int lightOpacity) {
        block.setLightOpacity(lightOpacity);
    }
    
    @Override
    public void setLightLevel(float lightLevel) {
        block.setLightLevel(lightLevel);
    }
    
    @Override
    public void setResistance(float resistance) {
        block.setResistance(resistance);
    }
    
    @Override
    public void setHardness(float hardness) {
        block.setHardness(hardness);
    }
    
    @Override
    public void setUnbreakable() {
        block.setBlockUnbreakable();
    }
    
    @Override
    public boolean getTickRandomly() {
        return block.getTickRandomly();
    }
    
    @Override
    public void setTickRandomly(boolean tickRandomly) {
        block.setTickRandomly(tickRandomly);
    }
    
    @Override
    public int tickRate(IWorld world) {
        return block.tickRate((World) world.getInternal());
    }
    
    @Override
    public boolean canPlaceBlockOnSide(IWorld world, IBlockPos pos, IFacing facing) {
        return block.canPlaceBlockOnSide((World) world.getInternal(), (BlockPos) pos.getInternal(), (EnumFacing) facing.getInternal());
    }
    
    @Override
    public boolean canPlaceBlockAt(IWorld world, IBlockPos pos) {
        return block.canPlaceBlockAt((World) world.getInternal(), (BlockPos) pos.getInternal());
    }
    
    @Override
    public boolean canSpawnInBlock() {
        return block.canSpawnInBlock();
    }
    
    @Override
    public String getUnlocalizedName() {
        return block.getUnlocalizedName();
    }
    
    @Override
    public ICreativeTab getCreativeTabToDisplayOn() {
        return MCCreativeTab.getICreativeTab(block.getCreativeTabToDisplayOn());
    }
    
    @Override
    public void setCreativeTab(ICreativeTab creativeTab) {
        block.setCreativeTab((CreativeTabs) creativeTab.getInternal());
    }
    
    @Override
    public IBlockState getDefaultState() {
        return new MCBlockState(block.getDefaultState());
    }
    
    @Override
    public float getSlipperiness(IBlockState state, IBlockAccess access, IBlockPos pos, IEntity entity) {
        return block.getSlipperiness((net.minecraft.block.state.IBlockState) state.getInternal(), (net.minecraft.world.IBlockAccess) access.getInternal(), (BlockPos) pos.getInternal(),(Entity)entity.getInternal());
    }
    
    @Override
    public void setDefaultSlipperiness(float defaultSlipperiness) {
        block.setDefaultSlipperiness(defaultSlipperiness);
    }
}
