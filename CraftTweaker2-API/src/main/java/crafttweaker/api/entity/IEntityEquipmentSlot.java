package crafttweaker.api.entity;

import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.entity.IEntityEquipmentSlot")
public interface IEntityEquipmentSlot {
    
    @ZenGetter("index")
    int getIndex();
    
    @ZenGetter("slotIndex")
    int getSlotIndex();
    
    @ZenGetter("name")
    String getName();
    
    @ZenOperator(OperatorType.COMPARE)
    int compareTo(IEntityEquipmentSlot other);
    
    Object getInternal();
    
}
