package crafttweaker.mc1120.events.handling;

import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.event.LivingExperienceDropEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;

public class MCLivingExperienceDropEvent implements LivingExperienceDropEvent {
    private net.minecraftforge.event.entity.living.LivingExperienceDropEvent event;

    public MCLivingExperienceDropEvent(net.minecraftforge.event.entity.living.LivingExperienceDropEvent event) {
        this.event = event;
    }

    @Override
    public int getDroppedExperience() {
        return event.getDroppedExperience();
    }

    @Override
    public void setDroppedExperience(int experience) {
        event.setDroppedExperience(experience);
    }

    @Override
    public IPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(event.getAttackingPlayer());
    }

    @Override
    public int getOriginalExperience() {
        return event.getOriginalExperience();
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
    public IEntityLivingBase getEntityLivingBase() {
        return CraftTweakerMC.getIEntityLivingBase(event.getEntityLiving());
    }
}
