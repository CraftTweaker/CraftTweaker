package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("crafttweaker.event.EntityStruckByLightningEvent")
@ZenRegister
public interface EntityStruckByLightningEvent extends IEventCancelable, IEntityEvent {

    @ZenGetter("lightning")
    IEntity getLightning();
}
