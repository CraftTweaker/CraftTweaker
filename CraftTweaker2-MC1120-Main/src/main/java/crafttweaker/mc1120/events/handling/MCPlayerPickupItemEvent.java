package crafttweaker.mc1120.events.handling;

import crafttweaker.api.entity.IEntityItem;
import crafttweaker.api.event.PlayerPickupItemEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * @author Stan
 */
public class MCPlayerPickupItemEvent implements PlayerPickupItemEvent {

    private final EntityItemPickupEvent event;

    public MCPlayerPickupItemEvent(EntityItemPickupEvent event) {
        this.event = event;
    }

    @Override
    public IEntityItem getItem() {
        return CraftTweakerMC.getIEntityItem(event.getItem());
    }

    @Override
    public IPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(event.getEntityPlayer());
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
    public String getResult() {
        return String.valueOf(event.getResult());
    }

    @Override
    public void setDenied() {
        event.setResult(Event.Result.DENY);
    }

    @Override
    public void setDefault() {
        event.setResult(Event.Result.DEFAULT);
    }

    @Override
    public void setAllowed() {
        event.setResult(Event.Result.ALLOW);
    }
}
