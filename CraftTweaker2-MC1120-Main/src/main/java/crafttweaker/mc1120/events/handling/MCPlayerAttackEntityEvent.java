package crafttweaker.mc1120.events.handling;

import crafttweaker.api.entity.IEntity;
import crafttweaker.api.event.PlayerAttackEntityEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

public class MCPlayerAttackEntityEvent implements PlayerAttackEntityEvent {
    
    private final AttackEntityEvent event;
    
    public MCPlayerAttackEntityEvent(AttackEntityEvent event) {
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
    public IEntity getTarget() {
        return CraftTweakerMC.getIEntity(event.getTarget());
    }
    
    @Override
    public IPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(event.getEntityPlayer());
    }
}
