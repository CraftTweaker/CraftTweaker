package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 * @author youyihj
 */
@ZenRegister
@ZenClass("crafttweaker.event.PortalSpawnEvent")
public interface PortalSpawnEvent extends IBlockEvent, IEventCancelable {
    @ZenGetter("valid")
    boolean isValid();

    @ZenGetter("height")
    int getHeight();

    @ZenGetter("width")
    int getWidth();
}
