package crafttweaker.api.entity;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.entity.IEntityArrow")
@ZenRegister
public interface IEntityArrow extends IEntity {
    
	@ZenMethod
	@ZenSetter("damage")
	IEntityArrow setDamage(double damage);
    
	@ZenMethod
    @ZenGetter("damage")
    double getDamage();
    
    @ZenMethod
	@ZenSetter("critical")
    IEntityArrow setIsCritical(boolean critical);
    
    @ZenMethod
    @ZenGetter("critical")
    boolean getIsCritical();
    
    @ZenMethod
	@ZenSetter("knockbackStrength")
    IEntityArrow setKnockbackStrength(int knockbackStrength);
    
	@ZenMethod
    @ZenGetter("shake")
    int arrowShake();
	
    @ZenMethod
	@ZenSetter("shooter")
    IEntityArrow setShooter(IEntity shooter);
	
	@ZenMethod
    @ZenGetter("shooter")
    IEntity getShooter();
	
	@ZenMethod
    @ZenGetter("pickupStatus")
    String getPickupStatus();
	
    @ZenMethod
	@ZenSetter("pickupStatus")
    IEntityArrow setPickupStatus(String pickupStatus);

    @ZenMethod
    IEntityArrow setPickupDisallowed();

    @ZenMethod
    IEntityArrow setPickupAllowed();

    @ZenMethod
    IEntityArrow setPickupCreativeOnly();
}