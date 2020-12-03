package crafttweaker.mc1120.events.handling;

import crafttweaker.api.entity.IEntity;
import crafttweaker.api.event.ProjectileImpactArrowEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraftforge.event.entity.ProjectileImpactEvent;

public class MCProjectileImpactArrowEvent extends MCProjectileImpactEvent implements ProjectileImpactArrowEvent {
    private final ProjectileImpactEvent.Arrow event;
    private final EntityArrow arrow;

    public MCProjectileImpactArrowEvent(ProjectileImpactEvent.Arrow event) {
        super(event);
        this.event = event;
        this.arrow = event.getArrow();
    }

    @Override
    public IEntity getArrow() {
        return CraftTweakerMC.getIEntity(arrow);
    }

    @Override
    public IEntity getShooter() {
        return CraftTweakerMC.getIEntity(arrow.shootingEntity);
    }

    @Override
    public double getDamage() {
        return arrow.getDamage();
    }

    @Override
    public void setDamage(double damage) {
        arrow.setDamage(damage);
    }

    @Override
    public void setKnockbackStrength(int knockbackStrength) {
        arrow.setKnockbackStrength(knockbackStrength);
    }

    @Override
    public boolean getIsCritical() {
        return arrow.getIsCritical();
    }

    @Override
    public void setIsCritical(boolean isCritical) {
        arrow.setIsCritical(isCritical);
    }

    @Override
    public String getPickupStatus() {
        return String.valueOf(arrow.pickupStatus);
    }

    @Override
    public void setPickupDisallowed() {
        arrow.pickupStatus = EntityArrow.PickupStatus.DISALLOWED;
    }

    @Override
    public void setPickupAllowed() {
        arrow.pickupStatus = EntityArrow.PickupStatus.ALLOWED;
    }

    @Override
    public void setPickupCreativeOnly() {
        arrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
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
