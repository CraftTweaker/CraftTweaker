package crafttweaker.mc1120.events.handling;

import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.event.EntityLivingHealEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraftforge.event.entity.living.LivingHealEvent;

public class MCEntityLivingHealEvent implements EntityLivingHealEvent {

    private final LivingHealEvent event;

    public MCEntityLivingHealEvent(LivingHealEvent event) {
        this.event = event;
    }

    @Override
    public float getAmount() {
        return event.getAmount();
    }
    
    @Override
    public void setAmount(float amount) {
        event.setAmount(amount);
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
