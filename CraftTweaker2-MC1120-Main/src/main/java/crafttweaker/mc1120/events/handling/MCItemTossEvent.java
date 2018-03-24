package crafttweaker.mc1120.events.handling;

import crafttweaker.api.entity.*;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraftforge.event.entity.item.ItemTossEvent;

public class MCItemTossEvent implements crafttweaker.api.event.ItemTossEvent {
    private final ItemTossEvent event;
    
    public MCItemTossEvent(ItemTossEvent event) {
        this.event = event;
    }
    
    @Override
    public IPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(event.getPlayer());
    }
    
    @Override
    public IEntityItem getItem() {
        return CraftTweakerMC.getIEntityItem(event.getEntityItem());
    }
    
    @Override
    public IEntity getEntity() {
        return getItem();
    }
    
    @Override
    public boolean isCanceled() {
        return event.isCanceled();
    }
    
    @Override
    public void setCanceled(boolean canceled) {
        event.setCanceled(canceled);
    }
}
