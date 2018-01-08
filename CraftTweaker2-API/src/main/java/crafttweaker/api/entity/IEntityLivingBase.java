package crafttweaker.api.entity;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.damage.IDamageSource;
import crafttweaker.api.entity.attribute.IEntityAttributeInstance;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.potions.*;
import stanhebben.zenscript.annotations.*;

import java.util.List;

@ZenClass("crafttweaker.entity.IEntityLivingBase")
@ZenRegister
public interface IEntityLivingBase extends IEntity {
    
    @ZenGetter
    boolean canBreatheUnderwater();
    
    @ZenMethod
    boolean isPotionActive(IPotion potion);
    
    @ZenGetter("health")
    float getHealth();
    
    @ZenSetter("health")
    void setHealth(float amount);
    
    @ZenGetter
    boolean isChild();
    
    @ZenMethod
    void clearActivePotions();
    
    @ZenGetter
    boolean isUndead();
    
    @ZenMethod
    void heal(float amount);
    
    @ZenGetter("maxHealth")
    float getMaxHealth();
    
    @ZenGetter("mainHandHeldItem")
    IItemStack getHeldItemMainHand();
    
    @ZenMethod
    @ZenGetter("offHandHeldItem")
    IItemStack getHeldItemOffHand();
    
    @ZenMethod
    IEntityAttributeInstance getAttribute(String name);
    
    @ZenGetter("revengeTarget")
    IEntityLivingBase getRevengeTarget();
    
    @ZenSetter("revengeTarget")
    void setRevengeTarger(IEntityLivingBase target);
    
    @ZenGetter("lastAttackedEntity")
    IEntityLivingBase getLastAttackedEntity();
    
    @ZenSetter("lastAttackedEntity")
    void setLastAttackedEntity(IEntityLivingBase entity);
    
    @ZenGetter("lastAttackedEntityTime")
    int getLastAttackedEntityTime();
    
    @ZenGetter("activePotionEffects")
    List<IPotionEffect> getActivePotionEffects();
    
    @ZenMethod
    IPotionEffect getActivePotionEffect(IPotion potion);
    
    @ZenMethod
    boolean isPotionEffectApplicable(IPotionEffect potionEffect);
    
    @ZenGetter("lastDamageSource")
    IDamageSource getLastDamageSource();
    
    @ZenMethod
    void onDeath(IDamageSource source);
    
    @ZenMethod
    void knockBack(IEntity entity, float one, double two, double three);
    
    @ZenGetter
    boolean isOnLadder();
    
    @ZenGetter("totalArmorValue")
    int getTotalArmorValue();
    
    @ZenGetter("attackingEntity")
    IEntityLivingBase getAttackingEntity();
    
    @ZenGetter("arrowsInEntity")
    int getArrowCountInEntity();
    
    @ZenSetter("arrowsInEntity")
    void setArrowCountInEntity(int arrows);
    
    @ZenGetter("AIMovementSpeed")
    float getAIMoveSpeed();
    
    @ZenSetter("AIMovementSpeed")
    void setAIMoveSpeed(float speed);
    
    @ZenMethod
    void onLivingUpdate();
    
    @ZenMethod
    boolean canEntityBeSeen(IEntity other);
    
    @ZenMethod
    void addPotionEffect(IPotionEffect potionEffect);
    
    @ZenMethod
    void setItemToSlot(IEntityEquipmentSlot slot, IItemStack itemStack);
    
    @ZenMethod
    boolean hasItemInSlot(IEntityEquipmentSlot slot);
    
    @ZenMethod
    IItemStack getItemInSlot(IEntityEquipmentSlot slot);
}
