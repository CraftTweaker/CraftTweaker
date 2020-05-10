package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("crafttweaker.event.PlayerBrewedPotionEvent")
@ZenRegister
public interface PlayerBrewedPotionEvent extends IPlayerEvent {

    @ZenGetter("potion")
    IItemStack getPotion();

}
