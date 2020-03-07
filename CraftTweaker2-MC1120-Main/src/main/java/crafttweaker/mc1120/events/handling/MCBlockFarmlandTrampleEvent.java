package crafttweaker.mc1120.events.handling;

import crafttweaker.api.entity.IEntity;
import crafttweaker.api.event.BlockFarmlandTrampleEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraftforge.event.world.BlockEvent;

public class MCBlockFarmlandTrampleEvent extends MCBlockEvent implements BlockFarmlandTrampleEvent {
  private BlockEvent.FarmlandTrampleEvent event;

  public MCBlockFarmlandTrampleEvent(BlockEvent.FarmlandTrampleEvent event) {
    super(event);
    this.event = event;
  }

  @Override
  public IEntity getEntity() {
    return CraftTweakerMC.getIEntity(event.getEntity());
  }

  @Override
  public float getFallDistance() {
    return event.getFallDistance();
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
