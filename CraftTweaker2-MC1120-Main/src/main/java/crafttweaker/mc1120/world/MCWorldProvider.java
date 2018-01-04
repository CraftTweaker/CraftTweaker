package crafttweaker.mc1120.world;

import crafttweaker.api.player.IEntityPlayer;
import crafttweaker.api.world.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.DimensionManager;

public class MCWorldProvider implements IWorldProvider {
    
    private final WorldProvider provider;
    
    public MCWorldProvider(int id) {
        this.provider = DimensionManager.getProvider(id);
    }
    
    public MCWorldProvider(WorldProvider provider) {
        this.provider = provider;
    }
    
    @Override
    public int getActualHeight() {
        return provider.getActualHeight();
    }
    
    @Override
    public int getAverageGroundLevel() {
        return provider.getAverageGroundLevel();
    }
    
    @Override
    public IBiome getBiomeForCoords(IBlockPos pos) {
        return new MCBiome(provider.getBiomeForCoords((BlockPos) pos.getInternal()));
    }
    
    @Override
    public float getCloudHeight() {
        return provider.getCloudHeight();
    }
    
    @Override
    public float getCurrentMoonPhaseFactor() {
        return provider.getCurrentMoonPhaseFactor();
    }
    
    @Override
    public int getDimensionID() {
        return provider.getDimension();
    }
    
    @Override
    public int getHeight() {
        return provider.getHeight();
    }
    
    @Override
    public double getHorizon() {
        return provider.getHorizon();
    }
    
    @Override
    public float[] getLightBrightnesTable() {
        return provider.getLightBrightnessTable();
    }
    
    @Override
    public int getMoonPhase(long time) {
        return provider.getMoonPhase(time);
    }
    
    @Override
    public double getMovementFactor() {
        return provider.getMovementFactor();
    }
    
    @Override
    public IBlockPos getRandomizedSpawnPoint() {
        return new MCBlockPos(provider.getRandomizedSpawnPoint());
    }
    
    @Override
    public IWorldProvider getRespawnDimension(IEntityPlayer player) {
        if(!(player.getInternal() instanceof EntityPlayerMP))
            return null;
        return new MCWorldProvider(provider.getRespawnDimension((EntityPlayerMP) player.getInternal()));
    }
    
    @Override
    public String getSaveFolder() {
        return provider.getSaveFolder();
    }
    
    @Override
    public long getSeed() {
        return provider.getSeed();
    }
    
    @Override
    public IBlockPos getSpawnCoordinate() {
        return new MCBlockPos(provider.getSpawnCoordinate());
    }
    
    @Override
    public IBlockPos getSpawnPoint() {
        return new MCBlockPos(provider.getSpawnPoint());
    }
    
    @Override
    public float getStarBrightness(float something) {
        return provider.getStarBrightness(something);
    }
    
    @Override
    public float getSunBrightness(float something) {
        return provider.getSunBrightness(something);
    }
    
    @Override
    public float getSunBrightnessFactor(float something) {
        return provider.getSunBrightnessFactor(something);
    }
    
    @Override
    public double getVoidFogYFactor() {
        return provider.getVoidFogYFactor();
    }
    
    @Override
    public long getWorldTime() {
        return provider.getWorldTime();
    }
    
    @Override
    public boolean canRespawnHere() {
        return provider.canRespawnHere();
    }
    
    @Override
    public boolean doesWaterVaporize() {
        return provider.doesWaterVaporize();
    }
    
    @Override
    public boolean hasSkyLight() {
        return provider.hasSkyLight();
    }
    
    @Override
    public boolean isBlockHighHumidity(IBlockPos pos) {
        return provider.isBlockHighHumidity((BlockPos) pos.getInternal());
    }
    
    @Override
    public boolean isDaytime() {
        return provider.isDaytime();
    }
    
    @Override
    public boolean isNether() {
        return provider.isNether();
    }
    
    @Override
    public boolean isSkyColored() {
        return provider.isSkyColored();
    }
    
    @Override
    public boolean isSurfaceWorld() {
        return provider.isSurfaceWorld();
    }
}
