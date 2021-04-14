package crafttweaker.api.entity;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.entity.IEntityLiving")
@ZenRegister
public interface IEntityLiving extends IEntityLivingBase {
    
    @ZenGetter("attackTarget")
    IEntityLivingBase getAttackTarget();
    
    @ZenSetter("attackTarget")
    void setAttackTarget(IEntityLivingBase attackTarget);
    
    @ZenGetter("talkInterval")
    int getTalkInterval();
    
    @ZenMethod
    void playLivingSound();
    
    @ZenMethod
    void spawnExplosionParticle();
    
    @ZenGetter
    boolean canSpawnHere();
    
    @ZenGetter
    boolean isColliding();
    
    @ZenGetter("renderSizeModifier")
    float getRenderSizeModifier();
    
    @ZenGetter("maxSpawnedInChunk")
    int getMaxSpawnedInChunk();
    
    @ZenGetter
    boolean canBeSteered();
    
    @ZenMethod
    void setDropChance(IEntityEquipmentSlot slot, float chance);
    
    @ZenGetter
    boolean canPickUpLoot();
    
    @ZenSetter("canPickUpLoot")
    void setCanPickUpLoot(boolean canPickUpLoot);
    
    @ZenMethod
    void enablePersistence();
    
    @ZenGetter
    boolean isNoDespawnRequired();
    
    @ZenMethod
    void clearLeashed(boolean sendPacket, boolean dropLead);
    
    @ZenMethod
    boolean canBeLeashedTo(IPlayer player);
    
    @ZenGetter
    boolean isLeashed();
    
    @ZenGetter("leashedToEntity")
    IEntity getLeashedToEntity();
    
    @ZenMethod
    void setLeashedToEntity(IEntity entity, boolean sendAttachNotification);
    
    @ZenGetter
    boolean isAIDisabled();
    
    @ZenSetter("isAIDisabled")
    void setAIDisabled(boolean isAIDisabled);
    
    @ZenGetter
    boolean isLeftHanded();
    
    @ZenSetter("isLeftHanded")
    void setLeftHanded(boolean isLeftHanded);
    
}
