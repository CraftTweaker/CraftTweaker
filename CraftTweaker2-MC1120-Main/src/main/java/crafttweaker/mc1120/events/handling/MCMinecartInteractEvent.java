package crafttweaker.mc1120.events.handling;

import crafttweaker.api.entity.IEntity;
import crafttweaker.api.event.MinecartInteractEvent;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;

public class MCMinecartInteractEvent implements MinecartInteractEvent {
  private net.minecraftforge.event.entity.minecart.MinecartInteractEvent event;

  public MCMinecartInteractEvent(net.minecraftforge.event.entity.minecart.MinecartInteractEvent event) {
    this.event = event;
  }

  @Override
  public IPlayer getPlayer() {
    return CraftTweakerMC.getIPlayer(event.getPlayer());
  }

  @Override
  public IItemStack getItem() {
    return CraftTweakerMC.getIItemStack(event.getItem());
  }

  @Override
  public String getHand() {
    return String.valueOf(event.getHand());
  }

  @Override
  public IEntity getEntity() {
    return CraftTweakerMC.getIEntity(event.getEntity());
  }

  @Override
  public boolean isCanceled() {
    return event.isCanceled();
  }

  @Override
  public void setCanceled(boolean canceled) {
    event.setCanceled(canceled);
  }
}
