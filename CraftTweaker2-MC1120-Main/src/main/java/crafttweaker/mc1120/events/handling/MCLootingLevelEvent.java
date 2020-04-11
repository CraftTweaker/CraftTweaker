package crafttweaker.mc1120.events.handling;

import crafttweaker.api.damage.IDamageSource;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.event.LootingLevelEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;

public class MCLootingLevelEvent implements LootingLevelEvent {
    private net.minecraftforge.event.entity.living.LootingLevelEvent event;

    public MCLootingLevelEvent(net.minecraftforge.event.entity.living.LootingLevelEvent event) {
        this.event = event;
    }

    @Override
    public int getLootingLevel() {
        return event.getLootingLevel();
    }

    @Override
    public void setLootingLevel(int level) {
        event.setLootingLevel(level);
    }

    @Override
    public IDamageSource getDamageSource() {
        return CraftTweakerMC.getIDamageSource(event.getDamageSource());
    }

    @Override
    public IEntityLivingBase getEntityLivingBase() {
        return CraftTweakerMC.getIEntityLivingBase(event.getEntityLiving());
    }
}
