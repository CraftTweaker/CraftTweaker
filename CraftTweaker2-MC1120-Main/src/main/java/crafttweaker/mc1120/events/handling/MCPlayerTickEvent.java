package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.PlayerTickEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class MCPlayerTickEvent implements PlayerTickEvent {
    
    private final IPlayer player;
    
    public MCPlayerTickEvent(IPlayer player) {
        this.player = player;
    }
    
    public MCPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        this(CraftTweakerMC.getIPlayer(event.player));
    }
    
    @Override
    public IPlayer getPlayer() {
        return player;
    }
}
