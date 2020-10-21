package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.RenderTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

public class MCRenderTickEvent implements RenderTickEvent {

    private final TickEvent.RenderTickEvent event;

    public MCRenderTickEvent(TickEvent.RenderTickEvent event) {
        this.event = event;
    }

    @Override
    public float getRenderTickTime() {
        return event.renderTickTime;
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
