package crafttweaker.mc1120.entity;

import crafttweaker.api.entity.*;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraft.entity.EntityLiving;

public class MCEntityLiving extends MCEntityLivingBase implements IEntityLiving {
    
    private final EntityLiving entityLiving;
    
    public MCEntityLiving(EntityLiving entity) {
        super(entity);
        this.entityLiving = entity;
    }
    
    @Override
    public IEntityLivingBase getAttackTarget() {
        return CraftTweakerMC.getIEntityLivingBase(entityLiving.getAttackTarget());
    }
    
    @Override
    public void setAttackTarget(IEntityLivingBase attackTarget) {
        entityLiving.setAttackTarget(CraftTweakerMC.getEntityLivingBase(attackTarget));
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
        entityLiving.setDropChance(CraftTweakerMC.getEntityEquipmentSlot(slot), chance);
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
        return entityLiving.canBeLeashedTo(CraftTweakerMC.getPlayer(player));
    }
    
    @Override
    public boolean isLeashed() {
        return entityLiving.getLeashed();
    }
    
    @Override
    public IEntity getLeashedToEntity() {
        return CraftTweakerMC.getIEntity(entityLiving.getLeashHolder());
    }
    
    @Override
    public void setLeashedToEntity(IEntity entity, boolean sendAttachNotification) {
        entityLiving.setLeashHolder(CraftTweakerMC.getEntity(entity), sendAttachNotification);
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
