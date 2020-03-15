package crafttweaker.mc1120.events.handling;

import crafttweaker.api.entity.IEntity;
import crafttweaker.api.event.CriticalHitEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;

public class MCCriticalHitEvent implements CriticalHitEvent {
    private net.minecraftforge.event.entity.player.CriticalHitEvent event;

    public MCCriticalHitEvent(net.minecraftforge.event.entity.player.CriticalHitEvent event) {
        this.event = event;
    }

    @Override
    public IEntity getTarget() {
        return CraftTweakerMC.getIEntity(event.getTarget());
    }

    @Override
    public float getDamageModifier() {
        return event.getDamageModifier();
    }

    @Override
    public void setDamageModifier(float modifier) {
        event.setDamageModifier(modifier);
    }

    @Override
    public float getOldDamageModifier() {
        return event.getOldDamageModifier();
    }

    @Override
    public boolean isVanillaCrit() {
        return event.isVanillaCritical();
    }

    @Override
    public boolean isDenied() {
        return event.getResult() == Event.Result.DENY;
    }

    @Override
    public boolean isDefault() {
        return event.getResult() == Event.Result.DEFAULT;
    }

    @Override
    public boolean isAllowed() {
        return event.getResult() == Event.Result.ALLOW;
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

    @Override
    public IPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(event.getEntityPlayer());
    }
}
