package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.ITickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class MCTickEvent implements ITickEvent {
    private final TickEvent event;

    public MCTickEvent(TickEvent event) {
        this.event = event;
    }

    @Override
    public String getPhase() {
        return event.phase.toString();
    }

    @Override
    public String getSide() {
        return event.side.toString();
    }

    @Override
    public boolean isClient() {
        return event.side.isClient();
    }

    @Override
    public boolean isServer() {
        return event.side.isServer();
    }
}