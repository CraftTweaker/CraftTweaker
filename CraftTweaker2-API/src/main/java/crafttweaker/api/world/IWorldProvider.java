package crafttweaker.api.world;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.world.IWorldProvider")
@ZenRegister
public interface IWorldProvider {
    
    @ZenMethod
    @ZenGetter("actualHeight")
    int getActualHeight();
    
    @ZenGetter("actualGroundLevel")
    @ZenMethod
    int getAverageGroundLevel();
    
    @ZenMethod
    IBiome getBiomeForCoords(IBlockPos pos);
    
    @ZenGetter("cloudHeight")
    @ZenMethod
    float getCloudHeight();
    
    @ZenGetter("currentMoonPhaseFactor")
    @ZenMethod
    float getCurrentMoonPhaseFactor();
    
    @ZenGetter("dimensionID")
    @ZenMethod
    int getDimensionID();
    
    @ZenGetter("height")
    @ZenMethod
    int getHeight();
    
    @ZenGetter("horizon")
    @ZenMethod
    double getHorizon();
    
    @ZenGetter("lightBrightnesTable")
    @ZenMethod
    float[] getLightBrightnesTable();
    
    @ZenMethod
    int getMoonPhase(long time);
    
    @ZenGetter("movementFactor")
    @ZenMethod
    double getMovementFactor();
    
    @ZenGetter("randomizedSpawnPoint")
    @ZenMethod
    IBlockPos getRandomizedSpawnPoint();
    
    @ZenMethod
    IWorldProvider getRespawnDimension(IPlayer player);
    
    @ZenGetter("saveFolder")
    @ZenMethod
    String getSaveFolder();
    
    @ZenGetter("seed")
    @ZenMethod
    long getSeed();
    
    @ZenGetter("spawnCoordinate")
    @ZenMethod
    IBlockPos getSpawnCoordinate();
    
    @ZenGetter("spawnPoint")
    @ZenMethod
    IBlockPos getSpawnPoint();
    
    @ZenMethod
    float getStarBrightness(float something);
    
    @ZenMethod
    float getSunBrightness(float something);
    
    @ZenMethod
    float getSunBrightnessFactor(float something);
    
    @ZenGetter("voidFogYFactor")
    @ZenMethod
    double getVoidFogYFactor();
    
    @ZenGetter("worldTime")
    @ZenMethod
    long getWorldTime();
    
    @ZenGetter("canRespawnHere")
    @ZenMethod
    boolean canRespawnHere();
    
    @ZenGetter("waterVaporize")
    @ZenMethod
    boolean doesWaterVaporize();
    
    @ZenGetter("skylight")
    @ZenMethod
    boolean hasSkyLight();
    
    @ZenMethod
    boolean isBlockHighHumidity(IBlockPos pos);
    
    @ZenGetter("daytime")
    @ZenMethod
    boolean isDaytime();
    
    @ZenGetter("nether")
    @ZenMethod
    boolean isNether();
    
    @ZenGetter("skyColored")
    @ZenMethod
    boolean isSkyColored();
    
    @ZenGetter("surfaceWorld")
    @ZenMethod
    boolean isSurfaceWorld();
}
