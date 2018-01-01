package crafttweaker.api.world;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.util.IPosition3f;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.world.IDimension")
@ZenRegister
public interface IDimension extends IBlockGroup {
    
    @ZenGetter("day")
    @ZenMethod
    boolean isDay();
    
    @ZenMethod
    int getBrightness(int x, int y, int z);

    @ZenMethod
	IBiome getBiome(IPosition3f position);
    
    @ZenCaster
    @ZenMethod
    IWorld asWorld();
}
