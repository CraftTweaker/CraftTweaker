package crafttweaker.mc1120.events.handling;

import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.event.PotionEffectAddedEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.potions.IPotionEffect;
import net.minecraftforge.event.entity.living.PotionEvent.PotionAddedEvent;

public class MCPotionEffectAddedEvent extends MCPotionEffectEvent implements PotionEffectAddedEvent {
    private final PotionAddedEvent event;

    public MCPotionEffectAddedEvent(PotionAddedEvent event) {
        super(event);
        this.event = event;
    }

    @Override
    public IEntityLivingBase getEntityLivingBase() {
        return CraftTweakerMC.getIEntityLivingBase(event.getEntityLiving());
    }

    @Override
    public IPotionEffect getOldEffect() {
        return CraftTweakerMC.getIPotionEffect(event.getOldPotionEffect());
    }
}