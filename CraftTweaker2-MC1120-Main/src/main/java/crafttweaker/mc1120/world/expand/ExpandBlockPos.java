package crafttweaker.mc1120.world.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.mc1120.world.MCBlockPos;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethodStatic;

@ZenExpansion("crafttweaker.world.IBlockPos")
@ZenRegister
public class ExpandBlockPos {

    @ZenMethodStatic
    public static IBlockPos create(int x, int y, int z) {
        return new MCBlockPos(x, y, z);
    }
}
