package crafttweaker.mc1120.util;

import crafttweaker.api.util.Position3f;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.mc1120.world.MCBlockPos;

import java.util.Objects;

/**
 * @author Stan
 */

public class MCPosition3f implements Position3f {

    private float x;
    private float y;
    private float z;

    public MCPosition3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    
    public float getX() {
        return x;
    }

    
    public float getY() {
        return y;
    }

    
    public float getZ() {
        return z;
    }
    
    
    public void setX(float x){
    	this.x = x;
    }
    
    
    public void setY(float y){
    	this.y = y;
    }
    
    
    public void setZ(float z){
    	this.z = z;
    }
    
    @Override
    public IBlockPos asBlockPos() {
    	return new MCBlockPos((int)x, (int)y, (int)z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MCPosition3f that = (MCPosition3f) o;
        return Float.compare(that.x, x) == 0 && Float.compare(that.y, y) == 0 && Float.compare(that.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
