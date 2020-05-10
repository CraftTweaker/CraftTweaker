package crafttweaker.mc1120.events.handling;

import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.event.EnderTeleportEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;

public class MCEnderTeleportEvent implements EnderTeleportEvent {

    private final net.minecraftforge.event.entity.living.EnderTeleportEvent event;

    public MCEnderTeleportEvent(net.minecraftforge.event.entity.living.EnderTeleportEvent event) {
        this.event = event;
    }

    @Override
    public double getTargetX() {
        return event.getTargetX();
    }

    @Override
    public void setTargetX(double targetX) {
        event.setTargetX(targetX);
    }

    @Override
    public double getTargetY() {
        return event.getTargetY();
    }

    @Override
    public void setTargetY(double targetY) {
        event.setTargetY(targetY);
    }

    @Override
    public double getTargetZ() {
        return event.getTargetZ();
    }

    @Override
    public void setTargetZ(double targetZ) {
        event.setTargetZ(targetZ);
    }

    @Override
    public float getAttackDamage() {
        return event.getAttackDamage();
    }

    @Override
    public void setAttackDamage(float attackDamage) {
        event.setAttackDamage(attackDamage);
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
