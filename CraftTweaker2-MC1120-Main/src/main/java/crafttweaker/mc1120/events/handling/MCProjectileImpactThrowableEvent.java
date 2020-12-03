package crafttweaker.mc1120.events.handling;

import crafttweaker.api.entity.IEntity;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.event.ProjectileImpactThrowableEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraftforge.event.entity.ProjectileImpactEvent;

public class MCProjectileImpactThrowableEvent extends MCProjectileImpactEvent implements ProjectileImpactThrowableEvent {
    private final ProjectileImpactEvent.Throwable event;
    private final EntityThrowable throwable;

    public MCProjectileImpactThrowableEvent(ProjectileImpactEvent.Throwable event) {
        super(event);
        this.event = event;
        this.throwable = event.getThrowable();
    }

    @Override
    public IEntity getThrowable() {
        return CraftTweakerMC.getIEntity(throwable);
    }

    @Override
    public IEntityLivingBase getThrower() {
        return CraftTweakerMC.getIEntityLivingBase(throwable.getThrower());
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
