package crafttweaker.api.entity;

import crafttweaker.CraftTweakerAPI;
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
    void removePotionEffect(IPotion potion);
    
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

    @ZenMethod
    @ZenGetter
    default boolean isElytraFlying() {
        CraftTweakerAPI.logError("Default method IEntityLivingBase#isElytraFlying is not overwritten in " + getClass() + " please report to the author!");
        return false;
    }

    @ZenMethod
    @ZenGetter("activeItemStack")
    default IItemStack getActiveItemStack() {
        CraftTweakerAPI.logError("Default method IEntityLivingBase#getActiveItemStack is not overwritten in " + getClass() + " please report to the author!");
        return null;
    }

    @ZenMethod
    @ZenGetter
    default boolean isActiveItemStackBlocking() {
        CraftTweakerAPI.logError("Default method IEntityLivingBase#isActiveItemStackBlocking is not overwritten in " + getClass() + " please report to the author!");
        return false;
    }

    @ZenMethod
    @ZenGetter("activeHand")
    default String getActiveHand() {
        CraftTweakerAPI.logError("Default method IEntityLivingBase#getActiveHand is not overwritten in " + getClass() + " please report to the author!");
        return "";
    }

    @ZenSetter("activeHand")
    @ZenMethod
    default void setActiveHand(String hand) {
        CraftTweakerAPI.logError("Default method IEntityLivingBase#setActiveHand is not overwritten in " + getClass() + " please report to the author!");
    }

    @ZenMethod
    @ZenGetter("isHandActive")
    default boolean isHandActive() {
        CraftTweakerAPI.logError("Default method IEntityLivingBase#isHandActive is not overwritten in " + getClass() + " please report to the author!");
        return false;
    }

    @ZenMethod
    default void resetActiveHand() {
        CraftTweakerAPI.logError("Default method IEntityLivingBase#resetActiveHand is not overwritten in " + getClass() + " please report to the author!");
    }

    @ZenMethod
    default void stopActiveHand() {
        CraftTweakerAPI.logError("Default method IEntityLivingBase#stopActiveHand is not overwritten in " + getClass() + " please report to the author!");
    }

    @ZenMethod
    @ZenGetter
    default boolean isSwingInProgress() {
        CraftTweakerAPI.logError("Default method IEntityLivingBase#isSwingInProgress is not overwritten in " + getClass() + " please report to the author!");
        return false;
    }

    @ZenMethod
    @ZenGetter("swingProgress")
    default int getSwingProgress() {
        CraftTweakerAPI.logError("Default method IEntityLivingBase#getSwingProgress is not overwritten in " + getClass() + " please report to the author!");
        return 0;
    }

    @ZenMethod
    @ZenSetter("swingProgress")
    default void setSwingProgress(int swingProgress) {
        CraftTweakerAPI.logError("Default method IEntityLivingBase#setSwingProgress is not overwritten in " + getClass() + " please report to the author!");
    }

    @ZenMethod
    default boolean attemptTeleport(double x, double y, double z) {
        CraftTweakerAPI.logError("Default method IEntityLivingBase#attemptTeleport is not overwritten in " + getClass() + " please report to the author!");
        return false;
    }
}