package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.PlayerTickEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class MCPlayerTickEvent implements PlayerTickEvent {
    
    private final IPlayer player;
    private final String phase;
    
    
    public MCPlayerTickEvent(IPlayer player, String phase) {
        this.player = player;
        this.phase = phase;
    }
    
    public MCPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        this(CraftTweakerMC.getIPlayer(event.player), event.phase.name().toUpperCase());
    }
    
    @Override
    public IPlayer getPlayer() {
        return player;
    }
    
    
    @Override
    public String getPhase() {
        return phase;
    }
}
