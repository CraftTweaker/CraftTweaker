package crafttweaker.mc1120.entity.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityEquipmentSlot;
import crafttweaker.mc1120.entity.MCEntityEquipmentSlot;
import net.minecraft.inventory.EntityEquipmentSlot;
import stanhebben.zenscript.annotations.*;

@ZenExpansion("crafttweaker.entity.IEntityEquipmentSlot")
@ZenRegister
public class ExpandEntityEquipmentSlot {
    private static final IEntityEquipmentSlot MAIN_HAND = new MCEntityEquipmentSlot(EntityEquipmentSlot.MAINHAND);
    private static final IEntityEquipmentSlot OFFHAND = new MCEntityEquipmentSlot(EntityEquipmentSlot.OFFHAND);
    private static final IEntityEquipmentSlot FEET = new MCEntityEquipmentSlot(EntityEquipmentSlot.FEET);
    private static final IEntityEquipmentSlot LEGS = new MCEntityEquipmentSlot(EntityEquipmentSlot.LEGS);
    private static final IEntityEquipmentSlot CHEST = new MCEntityEquipmentSlot(EntityEquipmentSlot.CHEST);
    private static final IEntityEquipmentSlot HEAD = new MCEntityEquipmentSlot(EntityEquipmentSlot.HEAD);
    
    @ZenMethodStatic
    public static IEntityEquipmentSlot mainHand() {
        return MAIN_HAND;
    }
    
    @ZenMethodStatic
    public static IEntityEquipmentSlot offhand() {
        return OFFHAND;
    }
    
    @ZenMethodStatic
    public static IEntityEquipmentSlot feet() {
        return FEET;
    }
    
    @ZenMethodStatic
    public static IEntityEquipmentSlot legs() {
        return LEGS;
    }
    
    @ZenMethodStatic
    public static IEntityEquipmentSlot chest() {
        return CHEST;
    }
    
    @ZenMethodStatic
    public static IEntityEquipmentSlot head() {
        return HEAD;
    }
    
}
