package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.PotionBrewEvent;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;

public class MCPotionBrewEvent implements PotionBrewEvent {
  private net.minecraftforge.event.brewing.PotionBrewEvent event;

  public MCPotionBrewEvent(net.minecraftforge.event.brewing.PotionBrewEvent event) {
    this.event = event;
  }

  @Override
  public int getLength() {
    return event.getLength();
  }

  @Override
  public IItemStack getItem(int index) {
    return CraftTweakerMC.getIItemStack(event.getItem(index));
  }

  @Override
  public void setItem(int index, IItemStack stack) {
    event.setItem(index, CraftTweakerMC.getItemStack(stack));
  }
}
