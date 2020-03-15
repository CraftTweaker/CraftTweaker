package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityXp;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerPickupXpEvent")
@ZenRegister
public interface PlayerPickupXpEvent extends IEventCancelable, IPlayerEvent {

    @ZenGetter("entityXp")
    IEntityXp getEntityXp();

    @ZenGetter("xp")
    float getXp();
}
