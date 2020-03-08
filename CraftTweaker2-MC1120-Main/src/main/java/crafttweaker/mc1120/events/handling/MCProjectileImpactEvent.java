package crafttweaker.mc1120.events.handling;

import crafttweaker.api.entity.IEntity;
import crafttweaker.api.event.ProjectileImpactEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IRayTraceResult;

public class MCProjectileImpactEvent implements ProjectileImpactEvent {
  private net.minecraftforge.event.entity.ProjectileImpactEvent event;

  public MCProjectileImpactEvent(net.minecraftforge.event.entity.ProjectileImpactEvent event) {
    this.event = event;
  }

  @Override
  public IRayTraceResult getRayTraceResult() {
    return CraftTweakerMC.getIRayTraceResult(event.getRayTraceResult());
  }

  @Override
  public IEntity getEntity() {
    return CraftTweakerMC.getIEntity(event.getEntity());
  }
}
