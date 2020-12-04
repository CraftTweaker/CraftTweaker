package crafttweaker.mc1120.events.handling;

import crafttweaker.api.entity.IEntity;
import crafttweaker.api.event.EntityJoinWorldEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IWorld;


public class MCEntityJoinWorldEvent implements EntityJoinWorldEvent {
    private final net.minecraftforge.event.entity.EntityJoinWorldEvent event;

    public MCEntityJoinWorldEvent(net.minecraftforge.event.entity.EntityJoinWorldEvent event) {
        this.event = event;
    }

    @Override
    public IEntity getEntity() {
        return CraftTweakerMC.getIEntity(event.getEntity());
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
    public IWorld getWorld() {
        return CraftTweakerMC.getIWorld(event.getWorld());
    }
}