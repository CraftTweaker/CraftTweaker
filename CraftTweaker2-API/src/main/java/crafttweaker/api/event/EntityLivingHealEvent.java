package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.event.EntityLivingHealEvent")
@ZenRegister
public interface EntityLivingHealEvent extends ILivingEvent, IEventCancelable {

    @ZenGetter("amount")
    float getAmount();
    
    @ZenSetter("amount")
    void setAmount(float amount);
}
