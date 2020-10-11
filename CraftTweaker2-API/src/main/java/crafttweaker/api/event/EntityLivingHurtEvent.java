package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.damage.IDamageSource;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.event.EntityLivingHurtEvent")
@ZenRegister
public interface EntityLivingHurtEvent extends ILivingEvent, IEventCancelable {

    @ZenGetter("damageSource")
    IDamageSource getDamageSource();

    @ZenGetter("amount")
    float getAmount();
    
    @ZenSetter("amount")
    void setAmount(float amount);
}
