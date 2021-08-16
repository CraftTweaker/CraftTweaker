package crafttweaker.mc1120.block;

import crafttweaker.api.block.*;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Objects;

public class MCBlockProperties implements IBlockProperties {
    private final net.minecraft.block.state.IBlockProperties properties;
    
    public MCBlockProperties(net.minecraft.block.state.IBlockProperties properties) {
        this.properties = properties;
    }
    
    
    @Override
    public int getLightValue(IBlockAccess access, IBlockPos blockPos) {
        return properties.getLightValue((net.minecraft.world.IBlockAccess) access.getInternal(), (BlockPos) blockPos.getInternal());
    }
    
    @Override
    public boolean canProvidePower() {
        return properties.canProvidePower();
    }
    
    @Override
    public int getWeakPower(IBlockAccess access, IBlockPos blockPos, IFacing facing) {
        return properties.getWeakPower((net.minecraft.world.IBlockAccess) access.getInternal(), (BlockPos) blockPos.getInternal(), (EnumFacing) facing.getInternal());
    }
    
    @Override
    public int getComparatorInputOverride(IWorld world, IBlockPos blockPos) {
        return properties.getComparatorInputOverride((World) world.getInternal(), (BlockPos) blockPos.getInternal());
    }
    
    @Override
    public IMobilityFlag getMobilityFlag() {
        return new MCMobilityFlag(properties.getMobilityFlag());
    }
    
    @Override
    public Object getInternal() {
        return properties;
    }
    
    @Override
    public IMaterial getMaterial() {
        return new MCMaterial(properties.getMaterial());
    }
    
    @Override
    public boolean canEntitySpawn(IEntity entity) {
        return entity.getInternal() instanceof Entity && properties.canEntitySpawn((Entity) entity.getInternal());
    }
    
    @Override
    public boolean causesSuffocation() {
        return properties.causesSuffocation();
    }
    
    @Override
    public boolean doesSideBlockRendering(IBlockAccess access, IBlockPos pos, IFacing facing) {
        return properties.doesSideBlockRendering((net.minecraft.world.IBlockAccess) access.getInternal(), (BlockPos) pos.getInternal(), (EnumFacing) facing.getInternal());
    }
    
    @Override
    public crafttweaker.api.block.IBlockState getActualState(IBlockAccess access, IBlockPos pos) {
        return new MCBlockState(properties.getActualState((net.minecraft.world.IBlockAccess) access.getInternal(), (BlockPos) pos.getInternal()));
    }
    
    @Override
    public float getBlockHardness(IWorld world, IBlockPos pos) {
        return properties.getBlockHardness((World) world.getInternal(), (BlockPos) pos.getInternal());
    }
    
    @Override
    public int getLightOpacity(IBlockAccess access, IBlockPos pos) {
        return properties.getLightOpacity((net.minecraft.world.IBlockAccess) access.getInternal(), (BlockPos) pos.getInternal());
    }
    
    @Override
    public float getPlayerRelativeBlockHardness(IPlayer player, IWorld world, IBlockPos pos) {
        if(!(player.getInternal() instanceof EntityPlayer))
            return 0;
        return properties.getPlayerRelativeBlockHardness((EntityPlayer) player.getInternal(), (World) world.getInternal(), (BlockPos) pos.getInternal());
    }
    
    @Override
    public int getStrongPower(IBlockAccess access, IBlockPos pos, IFacing facing) {
        return properties.getStrongPower((net.minecraft.world.IBlockAccess) access.getInternal(), (BlockPos) pos.getInternal(), (EnumFacing) facing.getInternal());
    }
    
    @Override
    public boolean hasCustomBreakingProgress() {
        return properties.hasCustomBreakingProgress();
    }
    
    @Override
    public boolean isBlockNormalCube() {
        return properties.isBlockNormalCube();
    }
    
    @Override
    public boolean isFullBlock() {
        return properties.isFullBlock();
    }
    
    @Override
    public boolean isFullCube() {
        return properties.isFullCube();
    }
    
    @Override
    public boolean isNormalCube() {
        return properties.isNormalCube();
    }
    
    @Override
    public boolean isOpaqueCube() {
        return properties.isOpaqueCube();
    }
    
    @Override
    public boolean isSideSolid(IBlockAccess access, IBlockPos pos, IFacing facing) {
        return properties.isSideSolid((net.minecraft.world.IBlockAccess) access.getInternal(), (BlockPos) pos.getInternal(), (EnumFacing) facing.getInternal());
    }
    
    @Override
    public boolean useNeighborBrightness() {
        return properties.useNeighborBrightness();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MCBlockProperties that = (MCBlockProperties) o;
        return Objects.equals(properties, that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(properties);
    }
}
