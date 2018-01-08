package crafttweaker.mc1120.entity;

import crafttweaker.api.entity.*;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.mc1120.world.MCBlockPos;
import net.minecraft.entity.*;
import net.minecraft.util.math.BlockPos;

public class MCEntityCreature extends MCEntityLiving implements IEntityCreature {
    
    private final EntityCreature entityCreature;
    public MCEntityCreature(EntityCreature entity) {
        super(entity);
        this.entityCreature = entity;
    }
    
    
    @Override
    public boolean hasPath() {
        return entityCreature.hasPath();
    }
    
    @Override
    public boolean isWithinHomeDistance() {
        return entityCreature.isWithinHomeDistanceCurrentPosition();
    }
    
    @Override
    public boolean isPositionWithinHomeDistance(IBlockPos pos) {
        return entityCreature.isWithinHomeDistanceFromPosition((BlockPos) pos.getInternal());
    }
    
    @Override
    public void setHomePositionAndDistance(IBlockPos pos, int distance) {
        entityCreature.setHomePosAndDistance((BlockPos) pos.getInternal(), distance);
    }
    
    @Override
    public IBlockPos getHomePosition() {
        return new MCBlockPos(entityCreature.getHomePosition());
    }
    
    @Override
    public float getMaximumHomeDistance() {
        return entityCreature.getMaximumHomeDistance();
    }
    
    @Override
    public void detachHome() {
        entityCreature.detachHome();
    }
    
    @Override
    public boolean hasHome() {
        return entityCreature.hasHome();
    }
}
