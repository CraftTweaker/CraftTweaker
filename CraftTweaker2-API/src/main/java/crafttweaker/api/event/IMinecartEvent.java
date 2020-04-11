package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("crafttweaker.event.MinecartEvent")
@ZenRegister
public interface IMinecartEvent {
    @ZenGetter("minecart")
    IEntity getEntity();
}
