package crafttweaker.mc1120.events.handling;

import crafttweaker.api.entity.IEntity;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.event.LivingKnockBackEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;

public class MCLivingKnockBackEvent implements LivingKnockBackEvent {
  private net.minecraftforge.event.entity.living.LivingKnockBackEvent event;

  public MCLivingKnockBackEvent(net.minecraftforge.event.entity.living.LivingKnockBackEvent event) {
    this.event = event;
  }

  @Override
  public IEntity getAttacker() {
    return CraftTweakerMC.getIEntity(event.getAttacker());
  }

  @Override
  public void setAttacker(IEntity attacker) {
    event.setAttacker(CraftTweakerMC.getEntity(attacker));
  }

  @Override
  public float getStrength() {
    return event.getStrength();
  }

  @Override
  public void setStrength(float strength) {
    event.setStrength(strength);
  }

  @Override
  public double getRatioX() {
    return event.getRatioX();
  }

  @Override
  public void setRatioX(double ratioX) {
    event.setRatioX(ratioX);
  }

  @Override
  public double getRatioZ() {
    return event.getRatioZ();
  }

  @Override
  public void setRatioZ(double ratioZ) {
    event.setRatioZ(ratioZ);
  }

  @Override
  public IEntity getOriginalAttacker() {
    return CraftTweakerMC.getIEntity(event.getOriginalAttacker());
  }

  @Override
  public float getOriginalStrength() {
    return event.getOriginalStrength();
  }

  @Override
  public double getOriginalRatioX() {
    return event.getOriginalRatioX();
  }

  @Override
  public double getOriginalRatioZ() {
    return event.getOriginalRatioZ();
  }

  @Override
  public boolean isCanceled() {
    return event.isCanceled();
  }

  @Override
  public void setCanceled(boolean canceled) {
    event.setCanceled(canceled);
  }

  @Override
  public IEntityLivingBase getEntityLivingBase() {
    return CraftTweakerMC.getIEntityLivingBase(event.getEntityLiving());
  }
}
