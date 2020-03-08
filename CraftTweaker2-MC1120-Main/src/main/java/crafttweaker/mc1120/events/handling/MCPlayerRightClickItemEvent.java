package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.PlayerRightClickEmptyEvent;
import crafttweaker.api.event.PlayerRightClickItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class MCPlayerRightClickItemEvent extends MCPlayerInteractEvent implements PlayerRightClickItemEvent {
  private PlayerInteractEvent.RightClickItem event;

  public MCPlayerRightClickItemEvent(PlayerInteractEvent.RightClickItem event) {
    super(event);
    this.event = event;
  }
}
