package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.PortalSpawnEvent;
import net.minecraftforge.event.world.BlockEvent;

/**
 * @author youyihj
 */
public class MCPortalSpawnEvent extends MCBlockEvent implements PortalSpawnEvent {
    private final BlockEvent.PortalSpawnEvent event;

    public MCPortalSpawnEvent(BlockEvent.PortalSpawnEvent event) {
        super(event);
        this.event = event;
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
    public boolean isValid() {
        return event.getPortalSize().isValid();
    }

    @Override
    public int getHeight() {
        return event.getPortalSize().getHeight();
    }

    @Override
    public int getWidth() {
        return event.getPortalSize().getWidth();
    }
}
