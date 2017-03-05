package minetweaker.api.world;

import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("minetweaker.world.IDimension")
public interface IDimension extends IBlockGroup {

    @ZenGetter
    boolean isDay();

    @ZenMethod
    int getBrightness(int x, int y, int z);
}
