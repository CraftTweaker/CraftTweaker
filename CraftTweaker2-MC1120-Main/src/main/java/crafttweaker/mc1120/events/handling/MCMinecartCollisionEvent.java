package crafttweaker.mc1120.events.handling;

import crafttweaker.api.entity.IEntity;
import crafttweaker.api.event.MinecartCollisionEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;

public class MCMinecartCollisionEvent implements MinecartCollisionEvent {
    private net.minecraftforge.event.entity.minecart.MinecartCollisionEvent event;

    public MCMinecartCollisionEvent(net.minecraftforge.event.entity.minecart.MinecartCollisionEvent event) {
        this.event = event;
    }

    @Override
    public IEntity getCollider() {
        return CraftTweakerMC.getIEntity(event.getCollider());
    }

    @Override
    public IEntity getEntity() {
        return CraftTweakerMC.getIEntity(event.getEntity());
    }
}
