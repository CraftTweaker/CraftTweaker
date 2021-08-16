package crafttweaker.mc1120.entity;

import crafttweaker.api.entity.IEntityEquipmentSlot;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.inventory.EntityEquipmentSlot;

import java.util.Objects;

public class MCEntityEquipmentSlot implements IEntityEquipmentSlot {
    private final EntityEquipmentSlot slot;
    
    public MCEntityEquipmentSlot(EntityEquipmentSlot slot) {
        this.slot = slot;
    }
    
    @Override
    public int getIndex() {
        return slot.getIndex();
    }
    
    @Override
    public int getSlotIndex() {
        return slot.getSlotIndex();
    }
    
    @Override
    public String getName() {
        return slot.getName();
    }
    
    @Override
    public int compareTo(IEntityEquipmentSlot other) {
        return slot.compareTo(CraftTweakerMC.getEntityEquipmentSlot(other));
    }
    
    @Override
    public Object getInternal() {
        return slot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MCEntityEquipmentSlot that = (MCEntityEquipmentSlot) o;
        return slot == that.slot;
    }

    @Override
    public int hashCode() {
        return Objects.hash(slot);
    }
}
