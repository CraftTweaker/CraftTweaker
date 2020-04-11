package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.container.IContainer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerOpenContainerEvent")
@ZenRegister
public interface PlayerOpenContainerEvent extends IPlayerEvent {

    @ZenGetter("container")
    IContainer getContainer();
}
