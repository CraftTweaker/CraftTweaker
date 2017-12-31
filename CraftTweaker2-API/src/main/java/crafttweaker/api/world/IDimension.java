package crafttweaker.api.world;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.util.IPosition3f;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

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
}
