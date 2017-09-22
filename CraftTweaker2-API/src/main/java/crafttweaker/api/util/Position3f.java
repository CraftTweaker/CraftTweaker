package crafttweaker.api.util;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.util.Position3f")
@ZenRegister
public class Position3f {

    private float x;
    private float y;
    private float z;

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
    
    @ZenSetter("x")
    public void setX(float x){
    	this.x = x;
    }
    
    @ZenSetter("y")
    public void setY(float y){
    	this.y = y;
    }
    
    @ZenSetter("z")
    public void setZ(float z){
    	this.z = z;
    }
    
    @ZenMethod
    public static Position3f create(float x, float y, float z){
    	return new Position3f(x,y,z);
    }
    
    @ZenMethod
    public static Position3f randomBetween(float xMin, float xMax, float yMin, float yMax, float zMin, float zMax){
    	return new Position3f(randomBetweenCalc(xMin, xMax), randomBetweenCalc(yMin, yMax), randomBetweenCalc(zMin, zMax));
    }
    
    private static final float randomBetweenCalc(float min, float max){
    	return (float) (min + Math.random() * (max - min));
    }
    
}
