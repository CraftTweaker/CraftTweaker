package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.potions.IPotionEffect;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("crafttweaker.event.PotionEffectAddedEvent")
@ZenRegister
public interface PotionEffectAddedEvent extends IPotionEffectEvent, ILivingEvent {

    @ZenGetter("oldEffect")
    IPotionEffect getOldEffect();

    @ZenGetter("newEffect")
    default IPotionEffect getNewEffect() {
        return getPotionEffect();
    }
}