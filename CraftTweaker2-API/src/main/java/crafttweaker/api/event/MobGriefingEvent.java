package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;


/**
 * @author Noob
 */
@ZenClass("crafttweaker.event.MobGriefingEvent")
@ZenRegister
public interface MobGriefingEvent extends IEntityEvent, IEventHasResult {
}
