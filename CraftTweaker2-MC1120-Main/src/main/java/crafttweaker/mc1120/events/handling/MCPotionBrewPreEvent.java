package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.PotionBrewPreEvent;
import net.minecraftforge.event.brewing.PotionBrewEvent;

public class MCPotionBrewPreEvent extends MCPotionBrewEvent implements PotionBrewPreEvent {
  private PotionBrewEvent.Pre event;

  public MCPotionBrewPreEvent(PotionBrewEvent.Pre event) {
    super(event);
    this.event = event;
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
