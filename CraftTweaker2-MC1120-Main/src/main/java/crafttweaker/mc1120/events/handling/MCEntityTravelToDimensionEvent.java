package crafttweaker.mc1120.events.handling;

import crafttweaker.api.entity.IEntity;
import crafttweaker.api.event.EntityTravelToDimensionEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;

public class MCEntityTravelToDimensionEvent implements EntityTravelToDimensionEvent {
  private net.minecraftforge.event.entity.EntityTravelToDimensionEvent event;

  public MCEntityTravelToDimensionEvent(net.minecraftforge.event.entity.EntityTravelToDimensionEvent event) {
    this.event = event;
  }

  @Override
  public int getDimension() {
    return event.getDimension();
  }

  @Override
  public IEntity getEntity() {
    return CraftTweakerMC.getIEntity(event.getEntity());
  }
}
