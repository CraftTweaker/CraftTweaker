package crafttweaker.api.event;


import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("crafttweaker.event.MinecartInteractEvent")
@ZenRegister
public interface MinecartInteractEvent extends IMinecartEvent, IEventCancelable {
  @ZenGetter("player")
  IPlayer getPlayer();

  @ZenGetter("item")
  IItemStack getItem();

  @ZenGetter("hand")
  String getHand();
}
