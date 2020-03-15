package crafttweaker.mc1120.events.handling;

import crafttweaker.api.entity.IEntityAnimal;
import crafttweaker.api.event.AnimalTameEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;

public class MCAnimalTameEvent implements AnimalTameEvent {
    private final net.minecraftforge.event.entity.living.AnimalTameEvent event;

    public MCAnimalTameEvent(net.minecraftforge.event.entity.living.AnimalTameEvent event) {
        this.event = event;
    }

    @Override
    public IEntityAnimal getAnimal() {
        return CraftTweakerMC.getIEntityAnimal(event.getAnimal());
    }

    @Override
    public IPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(event.getTamer());
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
