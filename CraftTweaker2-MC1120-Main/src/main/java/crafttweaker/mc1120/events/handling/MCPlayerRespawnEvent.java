package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.PlayerRespawnEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

/**
 * @author Stan
 */
public class MCPlayerRespawnEvent implements PlayerRespawnEvent{
    
    private final PlayerEvent.PlayerRespawnEvent event;
    
    public MCPlayerRespawnEvent(PlayerEvent.PlayerRespawnEvent event) {
        this.event = event;
    }
    
    @Override
    public boolean isEndConquered() {
        return event.isEndConquered();
    }
    
    @Override
    public IPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(event.player);
    }
}
