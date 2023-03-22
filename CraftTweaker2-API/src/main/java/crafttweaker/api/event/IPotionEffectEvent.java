package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.potions.IPotionEffect;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("crafttweaker.event.IPotionEffectEvent")
@ZenRegister
public interface IPotionEffectEvent extends ILivingEvent {

    @ZenGetter("potionEffect")
    IPotionEffect getPotionEffect();
}