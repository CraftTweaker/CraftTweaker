package crafttweaker.mc1120.world;

import crafttweaker.annotations.ZenRegister;
import net.minecraft.util.EnumFacing;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("crafttweaker.world.Facing")
@ZenRegister
public class Facing {
    @ZenMethod
    public static String north() {
        return EnumFacing.NORTH.name();
    }

    @ZenMethod
    public static String east() {
        return EnumFacing.EAST.name();
    }

    @ZenMethod
    public static String getsouth() {
        return EnumFacing.SOUTH.name();
    }

    @ZenMethod
    public static String west() {
        return EnumFacing.WEST.name();
    }

    @ZenMethod
    public static String up() {
        return EnumFacing.UP.name();
    }

    @ZenMethod
    public static String down() {
        return EnumFacing.DOWN.name();
    }
}