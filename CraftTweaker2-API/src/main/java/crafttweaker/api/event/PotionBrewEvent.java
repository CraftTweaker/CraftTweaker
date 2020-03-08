package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("crafttweaker.event.PotionBrewEvent")
@ZenRegister
public interface PotionBrewEvent {
  @ZenGetter("length")
  int getLength();

  @ZenMethod
  IItemStack getItem(int index);

  @ZenMethod
  void setItem(int index, IItemStack stack);
}
