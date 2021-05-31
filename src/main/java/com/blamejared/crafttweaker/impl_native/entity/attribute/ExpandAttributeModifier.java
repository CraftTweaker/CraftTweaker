package com.blamejared.crafttweaker.impl_native.entity.attribute;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import org.openzen.zencode.java.ZenCodeType;


@ZenRegister
@Document("vanilla/api/entity/AttributeModifier")
@NativeTypeRegistration(value = AttributeModifier.class, zenCodeName = "crafttweaker.api.entity.AttributeModifier")
public class ExpandAttributeModifier {
    
    /**
     * Gets the ID of this AttributeModifier.
     *
     * @return The ID of this AttributeModifier
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("id")
    public static String getID(AttributeModifier internal) {
        
        return internal.getID().toString();
    }
    
    /**
     * Gets the name of this AttributeModifier.
     *
     * @return The name of this AttributeModifier.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("name")
    public static String getName(AttributeModifier internal) {
        
        return internal.getName();
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
