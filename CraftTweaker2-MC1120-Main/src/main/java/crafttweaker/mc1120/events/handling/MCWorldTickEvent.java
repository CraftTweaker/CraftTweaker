package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.ITickEvent;
import crafttweaker.api.event.IWorldEvent;
import crafttweaker.api.event.WorldTickEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IWorld;
import crafttweaker.api.world.IWorldInfo;
import crafttweaker.api.world.IWorldProvider;
import crafttweaker.mc1120.world.MCWorldInfo;
import crafttweaker.mc1120.world.MCWorldProvider;

public class MCWorldTickEvent implements WorldTickEvent, ITickEvent, IWorldEvent {
    private final net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent event;

    public MCWorldTickEvent(net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent event) {
        this.event = event;
    }

    @Override
    public IWorld getWorld() {
        return CraftTweakerMC.getIWorld(event.world);
    }
    
	@Override
	public IWorldInfo getWorldInfo() {
		return new MCWorldInfo(event.world.getWorldInfo());
	}

	@Override
	public IWorldProvider getWorldProvider() {
		return new MCWorldProvider(event.world.provider);
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