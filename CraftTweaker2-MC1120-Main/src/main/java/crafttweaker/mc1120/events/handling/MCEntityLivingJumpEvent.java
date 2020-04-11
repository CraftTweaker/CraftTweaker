package crafttweaker.mc1120.events.handling;

import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.event.EntityLivingJumpEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraftforge.event.entity.living.LivingEvent;

public class MCEntityLivingJumpEvent implements EntityLivingJumpEvent {

    private final LivingEvent.LivingJumpEvent event;

    public MCEntityLivingJumpEvent(LivingEvent.LivingJumpEvent event) {
        this.event = event;
    }

    @Override
    public IEntityLivingBase getEntityLivingBase() {
        return CraftTweakerMC.getIEntityLivingBase(event.getEntityLiving());
    }
}
