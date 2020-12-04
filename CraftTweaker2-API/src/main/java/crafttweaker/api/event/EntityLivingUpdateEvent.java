package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("crafttweaker.event.EntityLivingUpdateEvent")
@ZenRegister
public interface EntityLivingUpdateEvent extends ILivingEvent, IEventCancelable {

}