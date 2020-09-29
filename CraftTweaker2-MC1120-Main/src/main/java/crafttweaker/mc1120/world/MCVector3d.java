package crafttweaker.mc1120.world;

import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IVector3d;
import net.minecraft.util.math.Vec3d;

public class MCVector3d implements IVector3d {
    
    private final Vec3d vec;
    
    public MCVector3d(Vec3d vec) {
        this.vec = vec;
    }
    
    @Override
    public double getX() {
        return vec.x;
    }
    
    @Override
    public double getY() {
        return vec.y;
    }
    
    @Override
    public double getZ() {
        return vec.z;
    }
    
    
    @Override
    public IVector3d getNormalized() {
        return new MCVector3d(vec.normalize());
    }
    
    @Override
    public double dotProduct(IVector3d other) {
        return vec.dotProduct(CraftTweakerMC.getVec3d(other));
    }
    
    @Override
    public IVector3d crossProduct(IVector3d other) {
        return CraftTweakerMC.getIVector3d(vec.crossProduct(CraftTweakerMC.getVec3d(other)));
    }
    
    @Override
    public IVector3d subtract(IVector3d other) {
        return CraftTweakerMC.getIVector3d(vec.subtract(CraftTweakerMC.getVec3d(other)));
    }

    @Override
    public IVector3d subtractReverse(IVector3d other) {
        return CraftTweakerMC.getIVector3d(vec.subtractReverse(CraftTweakerMC.getVec3d(other)));
    }

    @Override
    public IVector3d add(IVector3d other) {
        return CraftTweakerMC.getIVector3d(vec.add(CraftTweakerMC.getVec3d(other)));
    }
    
    @Override
    public double distanceTo(IVector3d other) {
        return vec.distanceTo(CraftTweakerMC.getVec3d(other));
    }
    
    @Override
    public IVector3d scale(double factor) {
        return CraftTweakerMC.getIVector3d(vec.scale(factor));
    }
    
    @Override
    public Vec3d getInternal() {
        return vec;
    }
}
