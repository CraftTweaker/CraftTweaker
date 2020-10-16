package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.PlayerTickEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class MCPlayerTickEvent extends MCTickEvent implements PlayerTickEvent {
    private final TickEvent.PlayerTickEvent event;

    public MCPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        super(event);
        this.event = event;
    }

    @Override
    public IPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(event.player);
    }
}