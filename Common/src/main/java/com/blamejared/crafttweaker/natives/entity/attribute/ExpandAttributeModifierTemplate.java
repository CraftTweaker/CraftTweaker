package com.blamejared.crafttweaker.natives.entity.attribute;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.effect.AttributeModifierTemplate;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.openzen.zencode.java.ZenCodeType;

import java.util.UUID;

@ZenRegister
@Document("vanilla/api/entity/attribute/AttributeModifierTemplate")
@NativeTypeRegistration(value = AttributeModifierTemplate.class, zenCodeName = "crafttweaker.api.entity.attribute.AttributeModifierTemplate")
public class ExpandAttributeModifierTemplate {
    
    @ZenCodeType.Getter("attributeModifierId")
    public static UUID getAttributeModifierId(AttributeModifierTemplate internal) {
        
        return internal.getAttributeModifierId();
    }
    
    @ZenCodeType.Method
    public static AttributeModifier create(AttributeModifierTemplate internal, int amplifier) {
        
        return internal.create(amplifier);
    }
    
}
