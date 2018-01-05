package crafttweaker.mc1120.entity;

import crafttweaker.api.entity.IEntityEquipmentSlot;
import net.minecraft.inventory.EntityEquipmentSlot;

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
        return slot.compareTo((EntityEquipmentSlot) other.getInternal());
    }
    
    @Override
    public Object getInternal() {
        return slot;
    }
}
