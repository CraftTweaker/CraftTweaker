package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.damage.IDamageSource;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.event.EntityLivingAttackedEvent")
@ZenRegister
public interface EntityLivingAttackedEvent extends ILivingEvent, IEventCancelable {
    
    @ZenGetter("damageSource")
    IDamageSource getDamageSource();
    
    @ZenGetter("amount")
    float getAmount();
}
