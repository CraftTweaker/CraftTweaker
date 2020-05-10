package crafttweaker.mc1120.events.handling;

import crafttweaker.api.entity.IEntity;
import crafttweaker.api.entity.IEntityItem;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraftforge.event.entity.item.ItemExpireEvent;

public class MCItemExpireEvent implements crafttweaker.api.event.ItemExpireEvent {
    private final ItemExpireEvent event;

    public MCItemExpireEvent(ItemExpireEvent event) {
        this.event = event;
    }

    @Override
    public IEntityItem getItem() {
        return CraftTweakerMC.getIEntityItem(event.getEntityItem());
    }

    @Override
    public int getExtraLife() {
        return event.getExtraLife();
    }

    @Override
    public void setExtraLife(int extraLife) {
        event.setExtraLife(extraLife);
    }

    @Override
    public IEntity getEntity() {
        return getItem();
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
