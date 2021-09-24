package com.blamejared.crafttweaker.impl_native.entity.attribute;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ZenRegister
@Document("vanilla/api/entity/AttributeInstance")
@NativeTypeRegistration(value = ModifiableAttributeInstance.class, zenCodeName = "crafttweaker.api.entity.AttributeInstance")
public class ExpandAttributeInstance {
    @ZenCodeType.Getter("baseValue")
    public static double getBaseValue(ModifiableAttributeInstance internal) {
        return internal.getBaseValue();
    }

    @ZenCodeType.Setter("baseValue")
    public static void setBaseValue(ModifiableAttributeInstance internal, double value) {
        internal.setBaseValue(value);
    }

    @ZenCodeType.Getter("value")
    public static double getValue(ModifiableAttributeInstance internal) {
        return internal.getValue();
    }

    @ZenCodeType.Getter("modifiers")
    public static List<AttributeModifier> getModifiers(ModifiableAttributeInstance internal) {
        return new ArrayList<>(internal.getModifierListCopy());
    }

    @ZenCodeType.Method
    public static void applyNonPersistentModifier(ModifiableAttributeInstance internal, AttributeModifier modifier) {
        internal.applyNonPersistentModifier(modifier);
    }

    @ZenCodeType.Method
    public static void applyPersistentModifier(ModifiableAttributeInstance internal, AttributeModifier modifier) {
        internal.applyPersistentModifier(modifier);
    }

    @ZenCodeType.Method
    public static boolean hasModifier(ModifiableAttributeInstance internal, AttributeModifier modifier) {
        return internal.hasModifier(modifier);
    }

    @ZenCodeType.Method
    @ZenCodeType.Nullable
    public static AttributeModifier getModifier(ModifiableAttributeInstance internal, String uuid) {
        return internal.getModifier(UUID.fromString(uuid));
    }

    @ZenCodeType.Method
    public static void removeModifier(ModifiableAttributeInstance internal, AttributeModifier modifier) {
        internal.removeModifier(modifier);
    }

    @ZenCodeType.Method
    public static void removeModifier(ModifiableAttributeInstance internal, String uuid) {
        internal.removeModifier(UUID.fromString(uuid));
    }
}
