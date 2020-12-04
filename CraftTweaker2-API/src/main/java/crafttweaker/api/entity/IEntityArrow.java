package crafttweaker.api.entity;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.entity.IEntityArrow")
@ZenRegister
public interface IEntityArrow extends IEntity, IProjectile {
    
    @ZenMethod
    @ZenSetter("damage")
    void setDamage(double damage);
    
    @ZenMethod
    @ZenGetter("damage")
    double getDamage();
    
    @ZenMethod
    @ZenSetter("isCritical")
    void setIsCritical(boolean critical);
    
    @ZenMethod
    @ZenGetter("isCritical")
    boolean getIsCritical();

    @ZenMethod
    @ZenGetter("knockbackStrength")
    int getKnockbackStrength();
    
    @ZenMethod
    @ZenSetter("knockbackStrength")
    void setKnockbackStrength(int knockbackStrength);
    
    @ZenMethod
    @ZenGetter("shake")
    int arrowShake();

    @ZenMethod
    @ZenSetter("shooter")
    void setShooter(IEntity shooter);

    @ZenMethod
    @ZenGetter("shooter")
    IEntity getShooter();

    @ZenMethod
    @ZenGetter("pickupStatus")
    String getPickupStatus();

    @ZenMethod
    @ZenSetter("pickupStatus")
    void setPickupStatus(String pickupStatus);

    @ZenMethod
    void setPickupDisallowed();

    @ZenMethod
    void setPickupAllowed();

    @ZenMethod
    void setPickupCreativeOnly();

    @ZenMethod
    void shoot(IEntity shooter, float pitch, float yaw, float roll, float velocity, float inaccuracy);
}