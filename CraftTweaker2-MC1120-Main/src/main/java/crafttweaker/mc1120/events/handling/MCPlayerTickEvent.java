package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.PlayerTickEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class MCPlayerTickEvent implements PlayerTickEvent {

    private final TickEvent.PlayerTickEvent event;

    @Deprecated
    public MCPlayerTickEvent(IPlayer player, String phase) {
        this(new TickEvent.PlayerTickEvent(TickEvent.Phase.valueOf(phase), CraftTweakerMC.getPlayer(player)));
    }

    public MCPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        this.event = event;
    }

    @Override
    public IPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(event.player);
    }

    @Override
    public String getSide() {
        return event.side.name().toUpperCase();
    }

    @Override
    public String getPhase() {
        return event.phase.name().toUpperCase();
    }
}
