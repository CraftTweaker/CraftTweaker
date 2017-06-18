package crafttweaker.api.util;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.util.Position3f")
@ZenRegister
public class Position3f {

    private final float x;
    private final float y;
    private final float z;

    public Position3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @ZenGetter("x")
    public float getX() {
        return x;
    }

    @ZenGetter("y")
    public float getY() {
        return y;
    }

    @ZenGetter("z")
    public float getZ() {
        return z;
    }
}
