package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.damage.IDamageSource;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenClass("crafttweaker.event.LootingLevelEvent")
@ZenRegister
public interface LootingLevelEvent extends ILivingEvent {
  @ZenGetter("lootingLevel")
  int getLootingLevel ();

  @ZenSetter("lootingLevel")
  void setLootingLevel (int level);

  @ZenGetter("damageSource")
  IDamageSource getDamageSource();
}
