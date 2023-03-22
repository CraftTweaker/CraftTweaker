package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.PotionEffectApplicableEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * @author youyihj
 */
public class MCPotionEffectApplicableEvent extends MCPotionEffectEvent implements PotionEffectApplicableEvent {
    private final PotionEvent.PotionApplicableEvent event;

    public MCPotionEffectApplicableEvent(PotionEvent.PotionApplicableEvent event) {
        super(event);
        this.event = event;
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
