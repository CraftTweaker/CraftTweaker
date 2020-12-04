package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.IPotionEffectEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.potions.IPotionEffect;
import net.minecraftforge.event.entity.living.PotionEvent;

public class MCPotionEffectEvent implements IPotionEffectEvent {
    private final PotionEvent.PotionAddedEvent event;

    public MCPotionEffectEvent(PotionEvent.PotionAddedEvent event) {
        this.event = event;
    }

    @Override
    public IPotionEffect getPotionEffect() {
        return CraftTweakerMC.getIPotionEffect(event.getPotionEffect());
    }
}