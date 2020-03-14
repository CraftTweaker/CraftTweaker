package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.ExplosionStartEvent;
import net.minecraftforge.event.world.ExplosionEvent;

public class MCExplosionStartEvent extends MCExplosionEvent implements ExplosionStartEvent {
  private ExplosionEvent.Start event;

  public MCExplosionStartEvent(ExplosionEvent.Start event) {
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
