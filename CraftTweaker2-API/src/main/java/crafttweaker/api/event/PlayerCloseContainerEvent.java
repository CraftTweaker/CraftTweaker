package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.container.IContainer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("crafttweaker.event.PlayerCloseContainerEvent")
@ZenRegister
public interface PlayerCloseContainerEvent extends IPlayerEvent {
    @ZenGetter("container")
    IContainer getContainer();
}
