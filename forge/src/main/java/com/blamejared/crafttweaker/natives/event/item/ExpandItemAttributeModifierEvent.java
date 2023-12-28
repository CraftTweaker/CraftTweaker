package com.blamejared.crafttweaker.natives.event.item;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * This event is fired when the attributes for an ItemStack are being calculated.
 * Attributes are calculated on the server when equipping and un-equipping an item to add and remove attributes respectively, both must be consistent.
 * Attributes are calculated on the client when rendering an item's tooltip to show relevant attributes.
 */
@ZenRegister
@ZenEvent
@Document("forge/api/event/item/ItemAttributeModifierEvent")
@NativeTypeRegistration(value = ItemAttributeModifierEvent.class, zenCodeName = "crafttweaker.forge.api.event.item.ItemAttributeModifierEvent")
public class ExpandItemAttributeModifierEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<ItemAttributeModifierEvent> BUS = IEventBus.direct(
            ItemAttributeModifierEvent.class,
            ForgeEventBusWire.of()
    );
    
    /**
     * Gets the modifiers on the ItemStack
     *
     * @return A Map of Attribute to a List of AttributeModifier.
     */
    @ZenCodeType.Getter("modifiers")
    public static Map<Attribute, List<AttributeModifier>> getModifiers(ItemAttributeModifierEvent internal) {
        
        // I don't think we expose Collection, so just convert it to a list.
        return internal.getModifiers()
                .asMap()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, attributeAttributeModifierEntry -> new ArrayList<>(attributeAttributeModifierEntry
                        .getValue())));
    }
    
    /**
     * Gets the original modifiers on the ItemStack before being changed by any other event listener.
     *
     * @return A Map of Attribute to a List of AttributeModifier.
     */
    @ZenCodeType.Getter("originalModifiers")
    public static Map<Attribute, List<AttributeModifier>> getOriginalModifiers(ItemAttributeModifierEvent internal) {
        
        // I don't think we expose Collection, so just convert it to a list.
        return internal.getOriginalModifiers()
                .asMap()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, attributeAttributeModifierEntry -> new ArrayList<>(attributeAttributeModifierEntry
                        .getValue())));
    }
    
    /**
     * Adds a new AttributeModifier to the ItemStack.
     *
     * @param attribute The Attribute to add.
     * @param name      The name of the modifier to add
     * @param value     The value of the modifier.
     * @param operation The operation of the modifier.
     *
     * @return True if the modifier was added. False otherwise.
     *
     * @docParam attribute <attribute:minecraft:generic.attack_damage>
     * @docParam name "Extra Power"
     * @docParam value 10
     * @docParam operation AttributeOperation.ADDITION
     */
    @ZenCodeType.Method
    public static boolean addModifier(ItemAttributeModifierEvent internal, Attribute attribute, String name, double value, AttributeModifier.Operation operation) {
        
        return internal.addModifier(attribute, new AttributeModifier(name, value, operation));
    }
    
    /**
     * Adds a new AttributeModifier to the ItemStack.
     *
     * @param attribute The Attribute to add.
     * @param uuid      The UUID of the modifier.
     * @param name      The name of the modifier.
     * @param value     The value of the modifier.
     * @param operation The operation of the modifier.
     *
     * @return True if the modifier was added. False otherwise.
     *
     * @docParam attribute <attribute:minecraft:generic.attack_damage>
     * @docParam uuid "8c1b5535-9f79-448b-87ae-52d81480aaa3"
     * @docParam name "Extra Power"
     * @docParam value 10
     * @docParam operation AttributeOperation.ADDITION
     */
    @ZenCodeType.Method
    public static boolean addModifier(ItemAttributeModifierEvent internal, Attribute attribute, String uuid, String name, double value, AttributeModifier.Operation operation) {
        
        return internal.addModifier(attribute, new AttributeModifier(UUID.fromString(uuid), name, value, operation));
    }
    
    /**
     * Removes an AttributeModifier from the ItemStack based on the modifier's UUID.
     *
     * @param attribute The Attribute to remove.
     * @param uuid      The UUID of the modifier.
     *
     * @return True if the modifier was remove. False otherwise.
     *
     * @docParam attribute <attribute:minecraft:generic.attack_damage>
     * @docParam uuid "8c1b5535-9f79-448b-87ae-52d81480aaa3"
     */
    @ZenCodeType.Method
    public static boolean removeModifier(ItemAttributeModifierEvent internal, Attribute attribute, String uuid) {
        
        final boolean[] doneWork = {false};
        internal.getModifiers()
                .values()
                .stream()
                .filter(attributeModifier -> attributeModifier.getId().equals(UUID.fromString(uuid)))
                .forEach(attributeModifier -> doneWork[0] |= internal.removeModifier(attribute, attributeModifier));
        return doneWork[0];
    }
    
    /**
     * Removes an AttributeModifier from the ItemStack.
     *
     * @param attribute The Attribute to remove.
     * @param modifier  The modifier to remove.
     *
     * @return True if the modifier was remove. False otherwise.
     *
     * @docParam attribute <attribute:minecraft:generic.attack_damage>
     * @docParam modifier modifier
     */
    @ZenCodeType.Method
    public static boolean removeModifier(ItemAttributeModifierEvent internal, Attribute attribute, AttributeModifier modifier) {
        
        return internal.removeModifier(attribute, modifier);
    }
    
    /**
     * Removes an Attribute from the ItemStack.
     *
     * @param attribute The Attribute to remove.
     *
     * @return A List of the AttributeModifiers for the removed Attribute.
     *
     * @docParam attribute <attribute:minecraft:generic.attack_damage>
     */
    @ZenCodeType.Method
    public static List<AttributeModifier> removeAttribute(ItemAttributeModifierEvent internal, Attribute attribute) {
        
        return new ArrayList<>(internal.removeAttribute(attribute));
    }
    
    /**
     * Clears all AttributeModifiers from the ItemStack.
     */
    @ZenCodeType.Method
    public static void clearModifiers(ItemAttributeModifierEvent internal) {
        
        internal.clearModifiers();
    }
    
    /**
     * Gets the EquipmentSlotType that this event is being ran for.
     *
     * If you only want to add / remove a modifier from a specific slot, you can use this to filter based on the slot.
     *
     * @return The EquipmentSlotType of this event.
     */
    @ZenCodeType.Getter("slotType")
    public static EquipmentSlot getSlotType(ItemAttributeModifierEvent internal) {
        
        return internal.getSlotType();
    }
    
    /**
     * Gets the ItemStack that this event is being ran for.
     *
     * @return The ItemStack this event is being ran for.
     */
    @ZenCodeType.Getter("itemStack")
    public static IItemStack getItemStack(ItemAttributeModifierEvent internal) {
        
        return IItemStack.of(internal.getItemStack());
    }
    
}
