package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.potions.IPotion;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * @author youyihj
 */
@ZenClass("crafttweaker.event.PotionEffectRemoveEvent")
@ZenRegister
public interface PotionEffectRemoveEvent extends IPotionEffectEvent, IEventCancelable {
    @ZenGetter("potion")
    @ZenMethod
    IPotion getPotion();
}
