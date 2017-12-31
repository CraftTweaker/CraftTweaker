package crafttweaker.mc1120.util;

import crafttweaker.api.util.IPosition3f;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.mc1120.world.MCBlockPos;

/**
 * @author Stan
 */

public class Position3f implements IPosition3f{

    private float x;
    private float y;
    private float z;

    public Position3f(float x, float y, float z) {
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
    
}
