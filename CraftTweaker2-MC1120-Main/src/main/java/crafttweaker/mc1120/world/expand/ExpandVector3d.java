package crafttweaker.mc1120.world.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.util.Position3f;
import crafttweaker.api.world.IVector3d;
import crafttweaker.mc1120.util.MCPosition3f;
import crafttweaker.mc1120.world.MCVector3d;
import net.minecraft.util.math.Vec3d;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethodStatic;

@ZenExpansion("crafttweaker.world.IVector3d")
@ZenRegister
public class ExpandVector3d {
    
    @ZenMethodStatic
    public static IVector3d create(double x, double y, double z) {
        return new MCVector3d(new Vec3d(x, y, z));
    }
}
