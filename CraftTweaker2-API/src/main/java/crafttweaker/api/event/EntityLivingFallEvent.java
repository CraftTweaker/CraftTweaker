package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenClass("crafttweaker.event.EntityLivingFallEvent")
@ZenRegister
public interface EntityLivingFallEvent extends ILivingEvent, IEventCancelable {

    @ZenGetter("distance")
    float getDistance();

    @ZenSetter("distance")
    void setDistance(float distance);

    @ZenGetter("damageMultiplier")
    float getDamageMultiplier();

    @ZenSetter("damageMultiplier")
    void setDamageMultiplier(float damageMultiplier);
}
