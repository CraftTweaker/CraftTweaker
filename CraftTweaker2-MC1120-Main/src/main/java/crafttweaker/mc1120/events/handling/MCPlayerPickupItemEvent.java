package crafttweaker.mc1120.events.handling;

import crafttweaker.api.entity.IEntityItem;
import crafttweaker.api.event.PlayerPickupItemEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;

/**
 * @author Stan
 */
public class MCPlayerPickupItemEvent implements PlayerPickupItemEvent {

    private final EntityItemPickupEvent event;

    public MCPlayerPickupItemEvent(EntityItemPickupEvent event) {
        this.event = event;
    }

    @Override
    public IEntityItem getItem() {
        return CraftTweakerMC.getIEntityItem(event.getItem());
    }

    @Override
    public IPlayer getPlayer() {
        return CraftTweakerMC.getIPlayer(event.getEntityPlayer());
    }
}
