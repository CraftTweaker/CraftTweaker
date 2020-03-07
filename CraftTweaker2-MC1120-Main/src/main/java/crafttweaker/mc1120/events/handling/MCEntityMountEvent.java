package crafttweaker.mc1120.events.handling;

import crafttweaker.api.entity.IEntity;
import crafttweaker.api.event.EntityMountEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IWorld;

public class MCEntityMountEvent implements EntityMountEvent {
  private net.minecraftforge.event.entity.EntityMountEvent event;

  public MCEntityMountEvent(net.minecraftforge.event.entity.EntityMountEvent event) {
    this.event = event;
  }

  @Override
  public IEntity getMountingEntity() {
    return CraftTweakerMC.getIEntity(event.getEntityMounting());
  }

  @Override
  public IEntity getMountedEntity() {
    return CraftTweakerMC.getIEntity(event.getEntityBeingMounted());
  }

  @Override
  public boolean isMounting() {
    return event.isMounting();
  }

  @Override
  public boolean isDismounting() {
    return event.isDismounting();
  }

  @Override
  public IWorld getWorld() {
    return CraftTweakerMC.getIWorld(event.getWorldObj());
  }

  @Override
  public IEntity getEntity() {
    return getMountedEntity();
  }
}
