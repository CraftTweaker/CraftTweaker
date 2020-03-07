package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.ItemTooltipEvent;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.List;

@SideOnly(Side.CLIENT) // Not sure if this is needed? TODO
public class MCItemTooltipEvent implements ItemTooltipEvent {
  private net.minecraftforge.event.entity.player.ItemTooltipEvent event;

  public MCItemTooltipEvent(net.minecraftforge.event.entity.player.ItemTooltipEvent event) {
    this.event = event;
  }

  @Override
  public boolean isAdvanced() {
    return event.getFlags().isAdvanced();
  }

  @Override
  public IItemStack getItem() {
    return CraftTweakerMC.getIItemStack(event.getItemStack());
  }

  @Override
  public IPlayer getPlayer() {
    return CraftTweakerMC.getIPlayer(event.getEntityPlayer());
  }

  @Override
  public String[] getTooltip() {
    return event.getToolTip().toArray(new String[0]);
  }

  @Override
  public void setTooltip(String[] tooltip) {
    List<String> tooltipInternal = event.getToolTip();
    tooltipInternal.clear();
    tooltipInternal.addAll(Arrays.asList(tooltip));
  }
}
