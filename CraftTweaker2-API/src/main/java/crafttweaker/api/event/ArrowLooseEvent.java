package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;


@ZenClass("crafttweaker.event.ArrowLooseEvent")
@ZenRegister
public interface ArrowLooseEvent extends IEventCancelable, IPlayerEvent {

    @ZenGetter("bow")
    IItemStack getBow();

    @ZenGetter("charge")
    int getCharge();
    
    @ZenSetter("charge")
    void setCharge(int charge);

    @ZenGetter("world")
    IWorld getWorld();
}