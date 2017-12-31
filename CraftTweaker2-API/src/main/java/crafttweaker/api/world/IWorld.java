package crafttweaker.api.world;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlock;
import crafttweaker.api.util.IPosition3f;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("crafttweaker.world.IWorld")
@ZenRegister
public interface IWorld {
    
    @ZenGetter("day")
    @ZenMethod
    boolean isDay();
    
    @ZenMethod
    int getBrightness(int x, int y, int z);
    
    @ZenMethod
    IBiome getBiome(IPosition3f position);
    
    @ZenGetter("worldInfo")
    @ZenMethod
    IWorldInfo getWorldInfo();
    
    @ZenGetter("remote")
    @ZenMethod
    boolean isRemote();

    @ZenGetter("raining")
    @ZenMethod
    boolean isRaining();

    @ZenGetter("dayTime")
    @ZenMethod
    boolean isDayTime();
    
    @ZenGetter("time")
    @ZenMethod
    long getWorldTime();

    @ZenGetter("surfaceWorld")
    @ZenMethod
    boolean isSurfaceWorld();

    @ZenGetter("moonPhase")
    @ZenMethod
    int getMoonPhase();
    
    @ZenGetter("dimension")
    @ZenMethod
    int getDimension();
    
    @ZenGetter("dimensionType")
    @ZenMethod
    String getDimensionType();

    @ZenMethod
	IBlock getBlock(int x, int y, int z);
    
    @ZenGetter("worldType")
    @ZenMethod
    String getWorldType();
    
    Object getInternal();
}
