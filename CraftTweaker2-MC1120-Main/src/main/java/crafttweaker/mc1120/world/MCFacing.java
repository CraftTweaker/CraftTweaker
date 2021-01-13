package crafttweaker.mc1120.world;

import crafttweaker.api.world.IFacing;
import net.minecraft.util.EnumFacing;

public class MCFacing implements IFacing {
    
    private final EnumFacing facing;
    
    public MCFacing(EnumFacing facing) {
        this.facing = facing;
    }
    
    @Override
    public IFacing opposite() {
        return new MCFacing(facing.getOpposite());
    }
    
    @Override
    public Object getInternal() {
        return facing;
    }
    
    @Override
    public String getName() {
        return facing.name();
    }
    
    @Override
    public int compare(IFacing other) {
        return facing.compareTo((EnumFacing) other.getInternal());
    }
    
    @Override
    public IFacing rotateY() {
        return new MCFacing(facing.rotateY());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MCFacing mcFacing = (MCFacing) o;
        return facing == mcFacing.facing;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + (this.facing != null ? this.facing.hashCode() : 0);
        return hash;
    }
}