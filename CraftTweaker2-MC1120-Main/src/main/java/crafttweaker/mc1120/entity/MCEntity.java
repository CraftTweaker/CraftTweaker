package crafttweaker.mc1120.entity;

import crafttweaker.api.entity.IEntity;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.util.IPosition3f;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IDimension;
import crafttweaker.api.world.IWorld;
import crafttweaker.mc1120.item.MCItemStack;
import crafttweaker.mc1120.util.Position3f;
import crafttweaker.mc1120.world.MCBlockPos;
import crafttweaker.mc1120.world.MCDimension;
import crafttweaker.mc1120.world.MCWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.RayTraceResult;

import java.util.List;
import java.util.stream.Collectors;

public class MCEntity implements IEntity {
    private Entity entity;

    public MCEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public IDimension getDimension() {
        return new MCDimension(entity.getEntityWorld());
    }

    @Override
    public double getX() {
        return entity.posX;
    }

    @Override
    public double getY() {
        return entity.posY;
    }

    @Override
    public double getZ() {
        return entity.posZ;
    }

    @Override
    public Position3f getPosition() {
        return new Position3f((float) entity.posX, (float) entity.posY, (float) entity.posZ);
    }

    @Override
    public void setPosition(IPosition3f position) {
        entity.setPosition(position.getX(), position.getY(), position.getZ());
    }

    @Override
    public void setDead() {
        entity.setDead();
    }

    @Override
    public void setFire(int seconds) {
        entity.setFire(seconds);
    }

    @Override
    public void extinguish() {
        entity.extinguish();
    }

    @Override
    public boolean isWet() {
        return entity.isWet();
    }

    @Override
    public List<IEntity> getPassengers() {
        return entity.getPassengers().stream().map(MCEntity::new).collect(Collectors.toList());
    }

    @Override
    public double getDistanceSqToEntity(IEntity otherEntity) {
        return entity.getDistanceSqToEntity((Entity) otherEntity.getInternal());
    }

    @Override
    public boolean isAlive() {
        return entity.isEntityAlive();
    }

    @Override
    public IEntity getRidingEntity() {
        return new MCEntity(entity.getRidingEntity());
    }

    @Override
    public IItemStack getPickedResult() {
        return new MCItemStack(entity.getPickedResult(new RayTraceResult(entity)));
    }

    @Override
    public String getCustomName() {
        return entity.getCustomNameTag();
    }

    @Override
    public void setCustomName(String name) {
        entity.setCustomNameTag(name);
    }

    @Override
    public boolean isImmuneToFire() {
        return entity.isImmuneToFire();
    }

    @Override
    public int getAir() {
        return entity.getAir();
    }

    @Override
    public void setAir(int amount) {
        entity.setAir(amount);
    }

    @Override
    public Object getInternal() {
        return entity;
    }

    @Override
    public String toString() {
        return entity.toString();
    }

    @Override
    public IWorld getWorld() {
        return new MCWorld(entity.world);
    }

    @Override
    public IBlockPos getBlockPos() {
        return new MCBlockPos(entity.getPosition());
    }
}
