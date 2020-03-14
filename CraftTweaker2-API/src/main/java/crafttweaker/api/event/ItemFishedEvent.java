package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityItem;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenClass("crafttweaker.event.ItemFishedEvent")
@ZenRegister
public interface ItemFishedEvent extends IPlayerEvent, IEventCancelable {
  @ZenGetter("damage")
  int getRodDamage();

  @ZenSetter("additionalDamage")
  void additionalDamage(int damage);

  @ZenGetter("drops")
  IItemStack[] getDrops ();
}
