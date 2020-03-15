package crafttweaker.mc1120.events.handling;

import crafttweaker.api.entity.IEntity;
import crafttweaker.api.event.EntityStruckByLightningEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;

public class MCEntityStruckByLightningEvent implements EntityStruckByLightningEvent {

    private final net.minecraftforge.event.entity.EntityStruckByLightningEvent event;

    public MCEntityStruckByLightningEvent(net.minecraftforge.event.entity.EntityStruckByLightningEvent event) {
        this.event = event;
    }

    @Override
    public IEntity getLightning() {
        return CraftTweakerMC.getIEntity(event.getLightning());
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
    public IEntity getEntity() {
        return CraftTweakerMC.getIEntity(event.getEntity());
    }
}
