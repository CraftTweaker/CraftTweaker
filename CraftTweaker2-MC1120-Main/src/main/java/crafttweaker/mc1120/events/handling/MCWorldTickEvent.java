package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.WorldTickEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IWorld;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class MCWorldTickEvent extends MCTickEvent implements WorldTickEvent {
    private final TickEvent.WorldTickEvent event;

    public MCWorldTickEvent(TickEvent.WorldTickEvent event) {
        super(event);
        this.event = event;
    }

    @Override
    public IWorld getWorld() {
        return CraftTweakerMC.getIWorld(event.world);
    }
}