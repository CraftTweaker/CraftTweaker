package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityItem;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;


@ZenClass("crafttweaker.event.PlayerItemPickupEvent")
@ZenRegister
public interface PlayerItemPickupEvent extends IPlayerEvent {
    @ZenGetter("stackCopy")
    IItemStack getStackCopy();

    @ZenGetter("originalEntity")
    IEntityItem getOriginalEntity();
}
