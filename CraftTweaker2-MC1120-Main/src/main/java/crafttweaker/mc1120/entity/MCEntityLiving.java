package crafttweaker.mc1120.entity;

import crafttweaker.api.entity.*;
import crafttweaker.api.player.IPlayer;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;

public class MCEntityLiving extends MCEntityLivingBase implements IEntityLiving {
    private final EntityLiving entityLiving;
    public MCEntityLiving(EntityLiving entity) {
        super(entity);
        this.entityLiving = entity;
    }
    
    @Override
    public IEntityLivingBase getAttackTarget() {
        return new MCEntityLivingBase(entityLiving.getAttackTarget());
    }
    
    @Override
    public void setAttackTarget(IEntityLivingBase attackTarget) {
        entityLiving.setAttackTarget((EntityLivingBase) attackTarget.getInternal());
    }
    
    @Override
    public int getTalkInterval() {
        return entityLiving.getTalkInterval();
    }
    
    @Override
    public void playLivingSound() {
        entityLiving.playLivingSound();
    }
    
    @Override
    public void spawnExplosionParticle() {
        entityLiving.spawnExplosionParticle();
    }
    
    @Override
    public void setMoveForward(float moveForward) {
        entityLiving.setMoveForward(moveForward);
    }
    
    @Override
    public void setMoveVertical(float moveVertical) {
        entityLiving.setMoveVertical(moveVertical);
    }
    
    @Override
    public void setMoveStrafing(float moveStrafing) {
        entityLiving.setMoveStrafing(moveStrafing);
    }
    
    @Override
    public boolean canSpawnHere() {
        return entityLiving.getCanSpawnHere();
    }
    
    @Override
    public boolean isColliding() {
        return !entityLiving.isNotColliding();
    }
    
    @Override
    public float getRenderSizeModifier() {
        return entityLiving.getRenderSizeModifier();
    }
    
    @Override
    public int getMaxSpawnedInChunk() {
        return entityLiving.getMaxSpawnedInChunk();
    }
    
    @Override
    public boolean canBeSteered() {
        return entityLiving.canBeSteered();
    }
    
    @Override
    public void setDropChance(IEntityEquipmentSlot slot, float chance) {
        entityLiving.setDropChance((EntityEquipmentSlot) slot.getInternal(), chance);
    }
    
    @Override
    public boolean canPickUpLoot() {
        return entityLiving.canPickUpLoot();
    }
    
    @Override
    public void setCanPickUpLoot(boolean canPickUpLoot) {
        entityLiving.setCanPickUpLoot(canPickUpLoot);
    }
    
    @Override
    public void enablePersistence() {
        entityLiving.enablePersistence();
    }
    
    @Override
    public boolean isNoDespawnRequired() {
        return entityLiving.isNoDespawnRequired();
    }
    
    @Override
    public void clearLeashed(boolean sendPacket, boolean dropLead) {
        entityLiving.clearLeashed(sendPacket, dropLead);
    }
    
    @Override
    public boolean canBeLeashedTo(IPlayer player) {
        return entityLiving.canBeLeashedTo((EntityPlayer) player.getInternal());
    }
    
    @Override
    public boolean isLeashed() {
        return entityLiving.getLeashed();
    }
    
    @Override
    public IEntity getLeashedToEntity() {
        return new MCEntity(entityLiving.getLeashHolder());
    }
    
    @Override
    public void setLeashedToEntity(IEntity entity, boolean sendAttachNotification) {
        entityLiving.setLeashHolder((Entity) entity.getInternal(), sendAttachNotification);
    }
    
    @Override
    public boolean isAIDisabled() {
        return entityLiving.isAIDisabled();
    }
    
    @Override
    public void setAIDisabled(boolean isAIDisabled) {
        entityLiving.setNoAI(isAIDisabled);
    }
    
    @Override
    public boolean isLeftHanded() {
        return entityLiving.isLeftHanded();
    }
    
    @Override
    public void setLeftHanded(boolean isLeftHanded) {
        entityLiving.setLeftHanded(isLeftHanded);
    }
}
