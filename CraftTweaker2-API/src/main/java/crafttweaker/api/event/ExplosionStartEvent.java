package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;

/**
 * @author Noob
 */
@ZenClass("crafttweaker.event.ExplosionStartEvent")
@ZenRegister
public interface ExplosionStartEvent extends IEventCancelable, IExplosionEvent {
}
