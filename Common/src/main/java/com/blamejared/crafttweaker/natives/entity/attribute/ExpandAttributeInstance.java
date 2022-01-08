package com.blamejared.crafttweaker.natives.entity.attribute;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ZenRegister
@Document("vanilla/api/entity/attribute/AttributeInstance")
@NativeTypeRegistration(value = AttributeInstance.class, zenCodeName = "crafttweaker.api.entity.attribute.AttributeInstance")
public class ExpandAttributeInstance {
    
    @ZenCodeType.Getter("baseValue")
    public static double getBaseValue(AttributeInstance internal) {
        
        return internal.getBaseValue();
    }
    
    @ZenCodeType.Setter("baseValue")
    public static void setBaseValue(AttributeInstance internal, double value) {
        
        internal.setBaseValue(value);
    }
    
    @ZenCodeType.Getter("value")
    public static double getValue(AttributeInstance internal) {
        
        return internal.getValue();
    }
    
    @ZenCodeType.Getter("modifiers")
    public static List<AttributeModifier> getModifiers(AttributeInstance internal) {
        
        return new ArrayList<>(internal.getModifiers());
    }
    
    @ZenCodeType.Method
    public static void addTransientModifier(AttributeInstance internal, AttributeModifier modifier) {
        
        internal.addTransientModifier(modifier);
    }
    
    @ZenCodeType.Method
    public static void addPermanentModifier(AttributeInstance internal, AttributeModifier modifier) {
        
        internal.addPermanentModifier(modifier);
    }
    
    @ZenCodeType.Method
    public static boolean hasModifier(AttributeInstance internal, AttributeModifier modifier) {
        
        return internal.hasModifier(modifier);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Nullable
    public static AttributeModifier getModifier(AttributeInstance internal, String uuid) {
        
        return internal.getModifier(UUID.fromString(uuid));
    }
    
    @ZenCodeType.Method
    public static void removeModifier(AttributeInstance internal, AttributeModifier modifier) {
        
        internal.removeModifier(modifier);
    }
    
    @ZenCodeType.Method
    public static void removeModifier(AttributeInstance internal, String uuid) {
        
        internal.removeModifier(UUID.fromString(uuid));
    }
    
}
