package crafttweaker.mc1120.events.handling;

import crafttweaker.api.entity.IEntityItem;
import crafttweaker.api.event.PlayerItemPickupEvent;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class MCPlayerItemPickupEvent implements PlayerItemPickupEvent {
  private PlayerEvent.ItemPickupEvent event;

  public MCPlayerItemPickupEvent(PlayerEvent.ItemPickupEvent event) {
    this.event = event;
  }

  @Override
  public IItemStack getStackCopy() {
    return CraftTweakerMC.getIItemStack(event.getStack());
  }

  @Override
  public IEntityItem getOriginalEntity() {
    return CraftTweakerMC.getIEntityItem(event.getOriginalEntity());
  }

  @Override
  public IPlayer getPlayer() {
    return CraftTweakerMC.getIPlayer(event.player);
  }
}
