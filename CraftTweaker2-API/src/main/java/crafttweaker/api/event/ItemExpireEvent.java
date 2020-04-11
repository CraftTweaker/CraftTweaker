package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityItem;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenClass("crafttweaker.event.ItemExpireEvent")
@ZenRegister
public interface ItemExpireEvent extends IEntityEvent, IEventCancelable {

    @ZenGetter("item")
    IEntityItem getItem();

    @ZenGetter("extraLife")
    int getExtraLife();

    @ZenSetter("extraLife")
    void setExtraLife(int extraLife);

}
