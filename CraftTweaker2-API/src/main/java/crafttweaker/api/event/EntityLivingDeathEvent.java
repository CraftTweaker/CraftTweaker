package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.damage.IDamageSource;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("crafttweaker.event.EntityLivingDeathEvent")
@ZenRegister
public interface EntityLivingDeathEvent extends ILivingEvent, IEventCancelable {

    @ZenGetter("damageSource")
    IDamageSource getDamageSource();
}
