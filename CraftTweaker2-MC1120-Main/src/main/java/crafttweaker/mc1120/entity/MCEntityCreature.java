package crafttweaker.mc1120.entity;

import crafttweaker.api.entity.*;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBlockPos;
import net.minecraft.entity.*;

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
        return entityCreature.isWithinHomeDistanceFromPosition(CraftTweakerMC.getBlockPos(pos));
    }
    
    @Override
    public void setHomePositionAndDistance(IBlockPos pos, int distance) {
        entityCreature.setHomePosAndDistance(CraftTweakerMC.getBlockPos(pos), distance);
    }
    
    @Override
    public IBlockPos getHomePosition() {
        return CraftTweakerMC.getIBlockPos(entityCreature.getHomePosition());
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
