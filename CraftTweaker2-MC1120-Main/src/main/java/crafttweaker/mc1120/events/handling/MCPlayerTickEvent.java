package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.ITickEvent;
import crafttweaker.api.event.PlayerTickEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;

public class MCPlayerTickEvent implements PlayerTickEvent, ITickEvent {
    private final net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent event;

    public MCPlayerTickEvent(net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent event) {
        this.event = event;
    }

    @Override
    public IPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(event.player);
    }

    @Override
    public String getPhase() {
        return event.phase.toString();
    }

	@Override
	public String getSide() {
		return event.side.toString();
	}

	@Override
	public boolean isClient() {
		return event.side.isClient();
	}

	@Override
	public boolean isServer() {
		return event.side.isServer();
	}
}
