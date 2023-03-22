package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.PotionEffectAddedEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.potions.IPotionEffect;
import net.minecraftforge.event.entity.living.PotionEvent;

public class MCPotionEffectAddedEvent extends MCPotionEffectEvent implements PotionEffectAddedEvent {
    private final PotionEvent.PotionAddedEvent event;

    public MCPotionEffectAddedEvent(PotionEvent.PotionAddedEvent event) {
        super(event);
        this.event = event;
    }

    @Override
    public IPotionEffect getOldEffect() {
        return CraftTweakerMC.getIPotionEffect(event.getOldPotionEffect());
    }
}