package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.*;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.event.EntityStruckByLightningEvent")
@ZenRegister
public interface EntityStruckByLightningEvent extends IEventCancelable, IEntityEvent{
    
    @ZenGetter("lightning")
    IEntity getLightning();
}
