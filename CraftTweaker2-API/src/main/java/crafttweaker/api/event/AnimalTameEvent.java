package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityAnimal;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 * @author Noob
 */
@ZenClass("crafttweaker.event.AnimalTameEvent")
@ZenRegister
public interface AnimalTameEvent extends IEventCancelable, IPlayerEvent {
  @ZenGetter("animal")
  IEntityAnimal getAnimal();

  @ZenGetter("tamer")
  IPlayer getPlayer();
}
