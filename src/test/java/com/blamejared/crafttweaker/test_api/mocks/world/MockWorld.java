package com.blamejared.crafttweaker.test_api.mocks.world;

import com.blamejared.crafttweaker.test_api.helper.UnsafeHelper;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.profiler.IProfiler;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.tags.ITagCollectionSupplier;
import net.minecraft.util.Direction;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.DimensionType;
import net.minecraft.world.ITickList;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.AbstractChunkProvider;
import net.minecraft.world.storage.ISpawnWorldInfo;
import net.minecraft.world.storage.MapData;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MockWorld extends World {
    
    private MockWorld(ISpawnWorldInfo worldInfo, RegistryKey<World> dimension, DimensionType dimensionType, Supplier<IProfiler> profiler, boolean isRemote, boolean isDebug, long seed) {
        
        super(worldInfo, dimension, dimensionType, profiler, isRemote, isDebug, seed);
    }
    
    public static MockWorld getInstance() {
        
        return UnsafeHelper.instantiate(MockWorld.class);
    }
    
    @Override
    public void notifyBlockUpdate(BlockPos pos, BlockState oldState, BlockState newState, int flags) {
    
    }
    
    @Override
    public void playSound(@Nullable PlayerEntity player, double x, double y, double z, SoundEvent soundIn, SoundCategory category, float volume, float pitch) {
    
    }
    
    @Override
    public void playMovingSound(@Nullable PlayerEntity playerIn, Entity entityIn, SoundEvent eventIn, SoundCategory categoryIn, float volume, float pitch) {
    
    }
    
    @Nullable
    @Override
    public Entity getEntityByID(int id) {
        
        throw new UnsupportedOperationException("Mock!");
    }
    
    @Nullable
    @Override
    public MapData getMapData(String mapName) {
        
        throw new UnsupportedOperationException("Mock!");
    }
    
    @Override
    public void registerMapData(MapData mapDataIn) {
    
    }
    
    @Override
    public int getNextMapId() {
        
        return 0;
    }
    
    @Override
    public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress) {
    
    }
    
    @Override
    public Scoreboard getScoreboard() {
        
        throw new UnsupportedOperationException("Mock!");
    }
    
    @Override
    public RecipeManager getRecipeManager() {
        
        throw new UnsupportedOperationException("Mock!");
    }
    
    @Override
    public ITagCollectionSupplier getTags() {
        
        throw new UnsupportedOperationException("Mock!");
    }
    
    @Override
    public ITickList<Block> getPendingBlockTicks() {
        
        throw new UnsupportedOperationException("Mock!");
    }
    
    @Override
    public ITickList<Fluid> getPendingFluidTicks() {
        
        throw new UnsupportedOperationException("Mock!");
    }
    
    @Override
    public AbstractChunkProvider getChunkProvider() {
        
        throw new UnsupportedOperationException("Mock!");
    }
    
    @Override
    public void playEvent(@Nullable PlayerEntity player, int type, BlockPos pos, int data) {
    
    }
    
    @Override
    public DynamicRegistries func_241828_r() {
        
        throw new UnsupportedOperationException("Mock!");
    }
    
    @Override
    public float func_230487_a_(Direction p_230487_1_, boolean p_230487_2_) {
        
        return 0;
    }
    
    @Override
    public List<? extends PlayerEntity> getPlayers() {
        
        throw new UnsupportedOperationException("Mock!");
    }
    
    @Override
    public Biome getNoiseBiomeRaw(int x, int y, int z) {
        
        throw new UnsupportedOperationException("Mock!");
    }
    
}
