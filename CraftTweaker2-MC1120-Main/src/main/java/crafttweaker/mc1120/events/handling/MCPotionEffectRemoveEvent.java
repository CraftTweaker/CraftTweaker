package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.PotionEffectRemoveEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.potions.IPotion;
import net.minecraftforge.event.entity.living.PotionEvent;

/**
 * @author youyihj
 */
public class MCPotionEffectRemoveEvent extends MCPotionEffectEvent implements PotionEffectRemoveEvent {
    private final PotionEvent.PotionRemoveEvent event;

    public MCPotionEffectRemoveEvent(PotionEvent.PotionRemoveEvent event) {
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
    public IPotion getPotion() {
        return CraftTweakerMC.getIPotion(event.getPotion());
    }
}
