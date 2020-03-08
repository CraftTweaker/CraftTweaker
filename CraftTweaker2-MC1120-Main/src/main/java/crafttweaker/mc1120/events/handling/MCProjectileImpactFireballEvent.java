package crafttweaker.mc1120.events.handling;

import crafttweaker.api.entity.IEntity;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.event.ProjectileImpactArrowEvent;
import crafttweaker.api.event.ProjectileImpactFireballEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraftforge.event.entity.ProjectileImpactEvent;

public class MCProjectileImpactFireballEvent extends MCProjectileImpactEvent implements ProjectileImpactFireballEvent {
  private ProjectileImpactEvent.Fireball event;
  private EntityFireball fireball;

  public MCProjectileImpactFireballEvent(ProjectileImpactEvent.Fireball event) {
    super(event);
    this.event = event;
    this.fireball = event.getFireball();
  }

  @Override
  public IEntity getFireball() {
    return CraftTweakerMC.getIEntity(fireball);
  }

  @Override
  public IEntityLivingBase getShooter() {
    return CraftTweakerMC.getIEntityLivingBase(fireball.shootingEntity);
  }

  @Override
  public double getAccelerationX() {
    return fireball.accelerationX;
  }

  @Override
  public void setAccelerationX(double accelerationX) {
    fireball.accelerationX = accelerationX;
  }

  @Override
  public double getAccelerationY() {
    return fireball.accelerationY;
  }

  @Override
  public void setAccelerationY(double accelerationY) {
    fireball.accelerationY = accelerationY;
  }

  @Override
  public double getAccelerationZ() {
    return fireball.accelerationZ;
  }

  @Override
  public void setAccelerationZ(double accelerationZ) {
    fireball.accelerationZ = accelerationZ;
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
