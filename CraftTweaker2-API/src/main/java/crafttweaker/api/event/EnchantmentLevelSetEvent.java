package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;


/**
 * @author Noob
 */
@ZenClass("crafttweaker.event.EnchantmentLevelSetEvent")
@ZenRegister
public interface EnchantmentLevelSetEvent extends IEventPositionable {
  @ZenGetter("world")
  IWorld getWorld();

  @ZenGetter("enchantRow")
  int getEnchantRow ();

  @ZenGetter("power")
  int getPower ();

  @ZenGetter("item")
  IItemStack getItem();

  @ZenGetter("originalLevel")
  int getOriginalLevel();

  @ZenGetter("level")
  int getLevel();

  @ZenSetter("level")
  void setLevel(int level);
}
