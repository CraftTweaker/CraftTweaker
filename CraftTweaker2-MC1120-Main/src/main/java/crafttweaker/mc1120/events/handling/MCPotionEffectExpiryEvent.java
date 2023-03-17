package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.PotionEffectExpiryEvent;
import net.minecraftforge.event.entity.living.PotionEvent;

/**
 * @author youyihj
 */
public class MCPotionEffectExpiryEvent extends MCPotionEffectEvent implements PotionEffectExpiryEvent {
    public MCPotionEffectExpiryEvent(PotionEvent.PotionExpiryEvent event) {
        super(event);
    }
}
