package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.ServerTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

public class MCServerTickEvent implements ServerTickEvent {

    private final TickEvent.ServerTickEvent event;

    public MCServerTickEvent(TickEvent.ServerTickEvent event) {
        this.event = event;
    }

    @Override
    public String getSide() {
        return Side.SERVER.name().toUpperCase();
    }

    @Override
    public String getPhase() {
        return event.phase.name().toUpperCase();
    }
}
