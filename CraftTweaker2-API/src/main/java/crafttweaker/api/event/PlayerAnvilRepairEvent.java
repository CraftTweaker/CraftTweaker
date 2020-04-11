package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenClass("crafttweaker.event.PlayerAnvilRepairEvent")
@ZenRegister
public interface PlayerAnvilRepairEvent extends IPlayerEvent {

    @ZenGetter("itemResult")
    IItemStack getItemResult();

    @ZenGetter("itemIngredient")
    IItemStack getItemIngredient();

    @ZenGetter("itemInput")
    IItemStack getItemInput();

    @ZenGetter("breakChance")
    float getBreakChance();

    @ZenSetter("breakChance")
    void setBreakChance(float breakChance);
}
