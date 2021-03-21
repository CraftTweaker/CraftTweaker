package crafttweaker.mc1120.events.handling;

import crafttweaker.api.entity.IEntity;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.event.MinecartUpdateEvent;
import crafttweaker.api.minecraft.CraftTweakerMC;

public class MCMinecartUpdateEvent implements MinecartUpdateEvent {
    private net.minecraftforge.event.entity.minecart.MinecartUpdateEvent event;

    public MCMinecartUpdateEvent(net.minecraftforge.event.entity.minecart.MinecartUpdateEvent event) {
        this.event = event;
    }

    @Override
    public IBlockPos getPosition() {
        return CraftTweakerMC.getIBlockPos(event.getPos());
    }

    @Override
    public IEntity getEntity() {
        return CraftTweakerMC.getIEntity(event.getEntity());
    }
}
