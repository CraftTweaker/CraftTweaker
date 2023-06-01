package com.blamejared.crafttweaker.natives.entity.attribute;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.blamejared.crafttweaker_annotations.annotations.TaggableElement;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/attribute/Attribute")
@NativeTypeRegistration(value = Attribute.class, zenCodeName = "crafttweaker.api.entity.attribute.Attribute")
@TaggableElement("minecraft:attribute")
public class ExpandAttribute {
    
    /**
     * Gets the default value for this Attribute.
     *
     * @return The default value for this Attribute.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("defaultValue")
    public static double getDefaultValue(Attribute internal) {
        
        return internal.getDefaultValue();
    }
    
    /**
     * Sanitizes the given value for this Attribute.
     *
     * @param value The given value to sanitized.
     *
     * @return The sanitized value.
     *
     * @docParam value 4
     */
    @ZenCodeType.Method
    public static double sanitizeValue(Attribute internal, double value) {
        
        return internal.sanitizeValue(value);
    }
    
    /**
     * Should this attribute be synced to the client.
     *
     * @return True if synced. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("clientSyncable")
    public static boolean isClientSyncable(Attribute internal) {
        
        return internal.isClientSyncable();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("registryName")
    public static ResourceLocation getRegistryName(Attribute internal) {
        
        return BuiltInRegistries.ATTRIBUTE.getKey(internal);
    }
    
    /**
     * Gets the attribute bracket handler syntax for this Attribute.
     *
     * E.G.
     * {@code <attribute:minecraft:generic.max_health>}
     *
     * @return The attribute bracket handler syntax for this Block.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("commandString")
    @ZenCodeType.Caster
    public static String getCommandString(Attribute internal) {
        
        return "<attribute:" + BuiltInRegistries.ATTRIBUTE.getKey(internal) + ">";
    }
    
}
