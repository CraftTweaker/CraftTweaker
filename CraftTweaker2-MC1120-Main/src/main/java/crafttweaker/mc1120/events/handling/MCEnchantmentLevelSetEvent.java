package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.EnchantmentLevelSetEvent;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;

public class MCEnchantmentLevelSetEvent implements EnchantmentLevelSetEvent {
  private net.minecraftforge.event.enchanting.EnchantmentLevelSetEvent event;

  public MCEnchantmentLevelSetEvent(net.minecraftforge.event.enchanting.EnchantmentLevelSetEvent event) {
    this.event = event;
  }

  @Override
  public IWorld getWorld() {
    return CraftTweakerMC.getIWorld(event.getWorld());
  }

  @Override
  public int getEnchantRow() {
    return event.getEnchantRow();
  }

  @Override
  public int getPower() {
    return event.getPower();
  }

  @Override
  public IItemStack getItem() {
    return CraftTweakerMC.getIItemStack(event.getItem());
  }

  @Override
  public int getOriginalLevel() {
    return event.getOriginalLevel();
  }

  @Override
  public int getLevel() {
    return event.getLevel();
  }

  @Override
  public void setLevel(int level) {
    event.setLevel(level);
  }

  @Override
  public IBlockPos getPosition() {
    return CraftTweakerMC.getIBlockPos(event.getPos());
  }
}
