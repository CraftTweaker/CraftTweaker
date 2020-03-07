package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenClass("crafttweaker.event.LivingKnockBackEvent")
@ZenRegister
public interface LivingKnockBackEvent extends ILivingEvent, IEventCancelable {
  @ZenGetter("attacker")
  IEntity getAttacker ();

  @ZenSetter("attacker")
  void setAttacker(IEntity attacker);

  @ZenGetter("strength")
  float getStrength();

  @ZenSetter("strength")
  void setStrength (float strength);

  @ZenGetter("ratioX")
  double getRatioX();

  @ZenSetter("ratioX")
  void setRatioX(double ratioX);

  @ZenGetter("ratioZ")
  double getRatioZ();

  @ZenSetter("ratioZ")
  void setRatioZ (double ratioZ);

  @ZenGetter("originalAttacker")
  IEntity getOriginalAttacker();

  @ZenGetter("originalStrength")
  float getOriginalStrength();

  @ZenGetter("originalRatioX")
  double getOriginalRatioX();

  @ZenGetter("originalRatioZ")
  double getOriginalRatioZ();
}
