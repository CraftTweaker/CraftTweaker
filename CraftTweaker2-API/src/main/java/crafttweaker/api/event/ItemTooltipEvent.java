package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenClass("crafttweaker.event.ItemTooltipEvent")
@ZenRegister
public interface ItemTooltipEvent extends IPlayerEvent {
  @ZenGetter("isAdvanced")
  boolean isAdvanced();

  @ZenGetter("item")
  IItemStack getItem();

  @ZenGetter("player")
  IPlayer getPlayer();

  @ZenGetter("tooltip")
  String[] getTooltip();

  @ZenSetter("tooltip")
  void setTooltip(String[] tooltip);
}
