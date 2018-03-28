package crafttweaker.mc1120.events.handling;

import crafttweaker.api.damage.IDamageSource;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.event.EntityLivingDeathEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;

public class MCEntityLivingDeathEvent implements EntityLivingDeathEvent {
    
    private final net.minecraftforge.event.entity.living.LivingDeathEvent event;
    
    public MCEntityLivingDeathEvent(net.minecraftforge.event.entity.living.LivingDeathEvent event) {
        this.event = event;
    }
    
    @Override
    public IDamageSource getDamageSource() {
        return CraftTweakerMC.getIDamageSource(event.getSource());
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
