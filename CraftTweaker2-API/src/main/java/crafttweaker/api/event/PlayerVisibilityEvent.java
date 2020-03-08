package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("crafttweaker.event.PlayerVisibilityEvent")
@ZenRegister
public interface PlayerVisibilityEvent extends IPlayerEvent {
  @ZenMethod
  void modifyVisibility (double modifier);

  @ZenGetter("modifier")
  double getModifier();
}
