package com.blamejared.crafttweaker.natives.entity.attribute;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.mixin.common.access.entity.AccessAttributeModifier;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.openzen.zencode.java.ZenCodeType;

import java.util.UUID;

@ZenRegister
@Document("vanilla/api/entity/attribute/AttributeModifier")
@NativeTypeRegistration(value = AttributeModifier.class, zenCodeName = "crafttweaker.api.entity.attribute.AttributeModifier")
public class ExpandAttributeModifier {
    
    /**
     * Creates a new AttributeModifier
     *
     * @param name      the name of this attribute modifier
     * @param amount    the amount of this attribute modifier
     * @param operation the operation of this attribute modifier.
     * @param uuid      the uuid of this attribute modifier, if omitted, it will use a random one.
     *
     * @return the new attribute modifier
     *
     * @docParam name "My New Attribute Modifier"
     * @docParam amount 11.4
     * @docParam operation AttributeOperation.ADDITION
     * @docParam uuid "6d79f9c1-a4ab-4e72-a0ab-71870b89b4c6"
     */
    @ZenCodeType.StaticExpansionMethod
    public static AttributeModifier create(String name, double amount, AttributeModifier.Operation operation, @ZenCodeType.OptionalString String uuid) {
        
        if(uuid.isEmpty()) {
            return new AttributeModifier(name, amount, operation);
        } else {
            return new AttributeModifier(UUID.fromString(uuid), name, amount, operation);
        }
    }
    
    /**
     * Gets the ID of this AttributeModifier.
     *
     * @return The ID of this AttributeModifier
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("id")
    public static String getId(AttributeModifier internal) {
        
        return internal.getId().toString();
    }
    
    /**
     * Gets the name of this AttributeModifier.
     *
     * @return The name of this AttributeModifier.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("name")
    public static String getName(AttributeModifier internal) {
        
        return ((AccessAttributeModifier) internal).crafttweaker$getName();
    }
    
    /**
     * Gets the amount of this AttributeModifier.
     *
     * @return The amount of this AttributeModifier.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("amount")
    public static double getAmount(AttributeModifier internal) {
        
        return internal.getAmount();
    }
    
    /**
     * Gets the operation of this AttributeModifier.
     *
     * @return The operation of this AttributeModifier.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("operation")
    public static AttributeModifier.Operation getOperation(AttributeModifier internal) {
        
        return internal.getOperation();
    }
    
}
