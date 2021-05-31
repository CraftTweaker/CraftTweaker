package com.blamejared.crafttweaker.impl_native.entity.attribute;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/AttributeOperation")
@NativeTypeRegistration(value = AttributeModifier.Operation.class, zenCodeName = "crafttweaker.api.entity.AttributeOperation")
public class ExpandAttributeOperation {
    
    /**
     * Gets the ID of this operation.
     *
     * @return The ID of this operation.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("id")
    public static int getId(AttributeModifier.Operation internal) {
        
        return internal.getId();
    }
    
}
