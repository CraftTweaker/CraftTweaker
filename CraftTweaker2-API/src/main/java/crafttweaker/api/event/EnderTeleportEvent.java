package crafttweaker.api.event;


import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.*;

/**
 * Called when endermen, shulkers or players (using enderpearls) teleport.
 */

@ZenRegister
@ZenClass("crafttweaker.event.EnderTeleportEvent")
public interface EnderTeleportEvent  extends IEventCancelable, ILivingEvent {
    
    @ZenGetter("targetX")
    double getTargetX();
    
    @ZenSetter("targetX")
    void setTargetX(double targetX);
    
    @ZenGetter("targetY")
    double getTargetY();
    
    @ZenSetter("targetY")
    void setTargetY(double targetY);
    
    @ZenGetter("targetZ")
    double getTargetZ();
    
    @ZenSetter("targetZ")
    void setTargetZ(double targetZ);
    
    @ZenGetter("attackDamage")
    float getAttackDamage();
    
    @ZenSetter("attackDamage")
    void setAttackDamage(float attackDamage);
}
