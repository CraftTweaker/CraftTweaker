package crafttweaker.mc1120.events.handling;

import crafttweaker.api.entity.IEntity;
import crafttweaker.api.event.PlayerInteractEntityEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class MCPlayerInteractEntityEvent extends MCPlayerInteractEvent implements PlayerInteractEntityEvent {

    private final PlayerInteractEvent.EntityInteract event;

    public MCPlayerInteractEntityEvent(PlayerInteractEvent.EntityInteract event) {
        super(event);
        this.event = event;
    }

    @Override
    public IEntity getTarget() {
        return CraftTweakerMC.getIEntity(event.getTarget());
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
