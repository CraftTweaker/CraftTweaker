package crafttweaker.api.world;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.world.IDimension")
@ZenRegister
public interface IDimension extends IBlockGroup {
    
    @ZenGetter
    boolean isDay();
    
    @ZenMethod
    int getBrightness(int x, int y, int z);
}
