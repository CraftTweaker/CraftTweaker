package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityEquipmentSlot;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("crafttweaker.event.EntityLivingEquipmentChangeEvent")
@ZenRegister
public interface EntityLivingEquipmentChangeEvent extends ILivingEvent {

    @ZenMethod
    @ZenGetter("oldItem")
    IItemStack getFrom();

    @ZenMethod
    @ZenGetter("newItem")
    IItemStack getTo();

    @ZenMethod
    @ZenGetter("slot")
    IEntityEquipmentSlot getSlot();
}