package crafttweaker.api.world;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenMethodStatic;

@ZenClass("crafttweaker.world.Facing")
@ZenRegister
public class Facing {
    @ZenMethod
    public static String north() {
        return "NORTH";
    }

    @ZenMethod
    public static String east() {
        return "EAST";
    }

    @ZenMethod
    public static String getsouth() {
        return "SOUTH";
    }

    @ZenMethod
    public static String west() {
        return "WEST";
    }

    @ZenMethod
    public static String up() {
        return "UP";
    }

    @ZenMethod
    public static String down() {
        return "DOWN";
    }
}