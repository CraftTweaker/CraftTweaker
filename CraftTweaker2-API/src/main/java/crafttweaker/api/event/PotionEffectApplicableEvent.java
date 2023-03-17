package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;

/**
 * @author youyihj
 */
@ZenRegister
@ZenClass("crafttweaker.event.PotionEffectApplicableEvent")
public interface PotionEffectApplicableEvent extends IPotionEffectEvent, IEventHasResult {
}
