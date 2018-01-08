package crafttweaker.mc1120.world.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.world.IFacing;
import crafttweaker.mc1120.world.MCFacing;
import net.minecraft.util.EnumFacing;
import stanhebben.zenscript.annotations.*;

@ZenExpansion("crafttweaker.world.IFacing")
@ZenRegister
public class ExpandFacing {
    
    static final IFacing NORTH = new MCFacing(EnumFacing.NORTH);
    static final IFacing EAST = new MCFacing(EnumFacing.NORTH);
    static final IFacing WEST = new MCFacing(EnumFacing.NORTH);
    static final IFacing SOUTH = new MCFacing(EnumFacing.NORTH);
    static final IFacing UP = new MCFacing(EnumFacing.NORTH);
    static final IFacing DOWN = new MCFacing(EnumFacing.NORTH);
    
    @ZenMethodStatic
    public static IFacing north() {
        return NORTH;
    }
    
    @ZenMethodStatic
    public static IFacing east() {
        return EAST;
    }
    
    @ZenMethodStatic
    public static IFacing south() {
        return SOUTH;
    }
    
    @ZenMethodStatic
    public static IFacing west() {
        return WEST;
    }
    
    @ZenMethodStatic
    public static IFacing up() {
        return UP;
    }
    
    @ZenMethodStatic
    public static IFacing down() {
        return DOWN;
    }
    
    public static IFacing fromString(String name) {
        return new MCFacing(EnumFacing.valueOf(name));
    }
}
