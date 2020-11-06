package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

public class MCClientTickEvent implements ClientTickEvent {

    private final TickEvent.ClientTickEvent event;

    public MCClientTickEvent(TickEvent.ClientTickEvent event) {
        this.event = event;
    }

    @Override
    public String getSide() {
        return Side.CLIENT.name().toUpperCase();
    }

    @Override
    public String getPhase() {
        return event.phase.name().toUpperCase();
    }
}
