package crafttweaker.mc1120.events.handling;

import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.event.EntityLivingFallEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;

public class MCEntityLivingFallEvent implements EntityLivingFallEvent {
    
    private final net.minecraftforge.event.entity.living.LivingFallEvent event;
    
    public MCEntityLivingFallEvent(net.minecraftforge.event.entity.living.LivingFallEvent event) {
        this.event = event;
    }
    
    @Override
    public float getDistance() {
        return event.getDistance();
    }
    
    @Override
    public void setDistance(float distance) {
        event.setDistance(distance);
    }
    
    @Override
    public float getDamageMultiplier() {
        return event.getDamageMultiplier();
    }
    
    @Override
    public void setDamageMultiplier(float damageMultiplier) {
        event.setDamageMultiplier(damageMultiplier);
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
