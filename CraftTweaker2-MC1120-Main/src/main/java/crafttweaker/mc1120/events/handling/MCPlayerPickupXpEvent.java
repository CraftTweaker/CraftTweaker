package crafttweaker.mc1120.events.handling;

import crafttweaker.api.entity.IEntityXp;
import crafttweaker.api.event.PlayerPickupXpEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;

public class MCPlayerPickupXpEvent implements PlayerPickupXpEvent {
    
    private final net.minecraftforge.event.entity.player.PlayerPickupXpEvent event;
    
    public MCPlayerPickupXpEvent(net.minecraftforge.event.entity.player.PlayerPickupXpEvent event) {
        this.event = event;
    }
    
    @Override
    public IEntityXp getEntityXp() {
        return CraftTweakerMC.getIEntityXp(event.getOrb());
    }
    
    @Override
    public float getXp() {
        return event.getOrb().getXpValue();
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
    public IPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(event.getEntityPlayer());
    }
}
