package crafttweaker.mc1120.block;

import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.block.*;
import crafttweaker.api.creativetabs.ICreativeTab;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.world.*;
import crafttweaker.mc1120.creativetabs.MCCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Objects;
import java.util.Optional;

import javax.annotation.Nullable;

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
    @SuppressWarnings("deprecation")
    public float getLightOpacity() {
        return block.getLightOpacity(block.getDefaultState());
    }

    @Override
    @SuppressWarnings("deprecation")
    public float getLightOpacity(IBlockState state) {
        return block.getLightOpacity(CraftTweakerMC.getBlockState(state));
    }

    @Override
    public float getLightOpacity(IBlockState state, IWorld world, IBlockPos pos) {
        return block.getLightOpacity(CraftTweakerMC.getBlockState(state), CraftTweakerMC.getWorld(world), CraftTweakerMC.getBlockPos(pos));
    }
    
    @Override
    public void setLightLevel(float lightLevel) {
        block.setLightLevel(lightLevel);
    }

    @Override
    @SuppressWarnings("deprecation")
    public float getLightLevel() {
        return block.getLightValue(block.getDefaultState());
    }

    @Override
    @SuppressWarnings("deprecation")
    public float getLightLevel(IBlockState state) {
        return block.getLightValue(CraftTweakerMC.getBlockState(state));
    }

    @Override
    public float getLightLevel(IBlockState state, IWorld world, IBlockPos pos) {
        return block.getLightValue(CraftTweakerMC.getBlockState(state), CraftTweakerMC.getWorld(world), CraftTweakerMC.getBlockPos(pos));
    }
    
    @Override
    public void setResistance(float resistance) {
        block.setResistance(resistance * 5.0F / 3.0F); // Normalize resistance to accord with getter
    }

    @Override
    @SuppressWarnings("deprecation")
    public float getResistance() {
        return block.getExplosionResistance(null);
    }

    @Override
    public float getResistance(IWorld world, IBlockPos pos, @Nullable IEntity entity, @Nullable IExplosion explosion) {
        return block.getExplosionResistance(CraftTweakerMC.getWorld(world), CraftTweakerMC.getBlockPos(pos), CraftTweakerMC.getEntity(entity), CraftTweakerMC.getExplosion(explosion));
    }
    
    @Override
    public void setHardness(float hardness) {
        block.setHardness(hardness);
    }
    
    @Override
    public float getHardness() {
        return block.blockHardness;
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
    public void setHarvestLevel(String toolClass, int level) {
        setHarvestLevel(toolClass, level, null);
    }

    @Override
    public void setHarvestLevel(String toolClass, int level, @Nullable IBlockState state) {
        if (state == null) {
            block.setHarvestLevel(toolClass, level);
        } else {
            block.setHarvestLevel(toolClass, level, CraftTweakerMC.getBlockState(state));
        }
    }
    
    @Override
    public int getHarvestLevel() {
        return block.getHarvestLevel(block.getDefaultState());
    }
    
    @Override
    public int getHarvestLevel(IBlockState state) {
        return block.getHarvestLevel(CraftTweakerMC.getBlockState(state));
    }
    
    @Override
    public String getHarvestTool() {
        return Optional.ofNullable(block.getHarvestTool(block.getDefaultState())).orElse("");
    }

    @Override
    public String getHarvestTool(IBlockState state) {
        return Optional.ofNullable(block.getHarvestTool(CraftTweakerMC.getBlockState(state))).orElse("");
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
        block.setCreativeTab(CraftTweakerMC.getCreativeTabs(creativeTab));
    }
    
    @Override
    public IBlockState getDefaultState() {
        return new MCBlockState(block.getDefaultState());
    }
    
    @Override
    public float getSlipperiness(IBlockState state, IBlockAccess access, IBlockPos pos, IEntity entity) {
        return block.getSlipperiness((net.minecraft.block.state.IBlockState) state.getInternal(), (net.minecraft.world.IBlockAccess) access.getInternal(), (BlockPos) pos.getInternal(), (Entity) entity.getInternal());
    }
    
    @Override
    public void setDefaultSlipperiness(float defaultSlipperiness) {
        block.setDefaultSlipperiness(defaultSlipperiness);
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return new MCBlockState(block.getStateFromMeta(meta));
    }
    
    @Override
    public boolean isToolEffective(String type, IBlockState state) {
        return block.isToolEffective(type, CraftTweakerMC.getBlockState(state));   
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MCBlockDefinition that = (MCBlockDefinition) o;
        return Objects.equals(block, that.block);
    }

    @Override
    public int hashCode() {
        return Objects.hash(block);
    }
}
