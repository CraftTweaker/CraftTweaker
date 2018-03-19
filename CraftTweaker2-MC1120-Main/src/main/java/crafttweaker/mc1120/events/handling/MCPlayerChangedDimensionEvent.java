package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.PlayerChangedDimensionEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IWorld;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

/**
 * @author Stan
 */
public class MCPlayerChangedDimensionEvent implements PlayerChangedDimensionEvent {
    
    private final PlayerEvent.PlayerChangedDimensionEvent event;
    
    public MCPlayerChangedDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event) {
        this.event = event;
    }
    
    @Override
    public int getFrom() {
        return event.fromDim;
    }
    
    @Override
    public int getTo() {
        return event.toDim;
    }
    
    @Override
    public IWorld getFromWorld() {
        return CraftTweakerMC.getWorldByID(getFrom());
    }
    
    @Override
    public IWorld getToWorld() {
        return CraftTweakerMC.getWorldByID(getTo());
    }
    
    @Override
    public IPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(event.player);
    }
}
