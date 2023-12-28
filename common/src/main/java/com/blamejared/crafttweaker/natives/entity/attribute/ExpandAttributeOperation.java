package com.blamejared.crafttweaker.natives.entity.attribute;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/attribute/AttributeOperation")
@NativeTypeRegistration(value = AttributeModifier.Operation.class, zenCodeName = "crafttweaker.api.entity.attribute.AttributeOperation")
@BracketEnum("minecraft:attribute/operation")
public class ExpandAttributeOperation {
    
    /**
     * Gets the value of this operation.
     *
     * @return The value of this operation.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("value")
    public static int getValue(AttributeModifier.Operation internal) {
        
        return internal.toValue();
    }
    
}
