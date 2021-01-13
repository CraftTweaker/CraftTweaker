package crafttweaker.mc1120.events.handling;

import crafttweaker.api.entity.IEntityEquipmentSlot;
import crafttweaker.api.entity.IEntityLivingBase;
import crafttweaker.api.event.EntityLivingEquipmentChangeEvent;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;

public class MCEntityLivingEquipmentChangeEvent implements EntityLivingEquipmentChangeEvent {

    private final LivingEquipmentChangeEvent event;

    public MCEntityLivingEquipmentChangeEvent(LivingEquipmentChangeEvent event) {
        this.event = event;
    }

    @Override
    public IEntityLivingBase getEntityLivingBase() {
        return CraftTweakerMC.getIEntityLivingBase(event.getEntityLiving());
    }

    @Override
    public IItemStack getFrom() {
        return CraftTweakerMC.getIItemStack(event.getFrom());
    }

    @Override
    public IItemStack getTo() {
        return CraftTweakerMC.getIItemStack(event.getTo());
    }

    @Override
    public IEntityEquipmentSlot getSlot() {
        return CraftTweakerMC.getIEntityEquipmentSlot(event.getSlot());
    }
}
