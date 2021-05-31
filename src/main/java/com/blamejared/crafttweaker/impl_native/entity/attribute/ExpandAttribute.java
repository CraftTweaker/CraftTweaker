package com.blamejared.crafttweaker.impl_native.entity.attribute;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.entity.ai.attributes.Attribute;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/Attribute")
@NativeTypeRegistration(value = Attribute.class, zenCodeName = "crafttweaker.api.entity.Attribute")
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
     * Clamps the given value to this Attribute's range if it exist.
     *
     * @param value The given value to clamp.
     *
     * @return The clamped value if this Attribute has a range, otherwise the value is returned as is.
     *
     * @docParam value 4
     */
    @ZenCodeType.Method
    public static double clampValue(Attribute internal, double value) {
        
        return internal.clampValue(value);
    }
    
    /**
     * Should this attribute be synced to the client.
     *
     * @return True if synced. False otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("shouldWatch")
    public static boolean shouldWatch(Attribute internal) {
        
        return internal.getShouldWatch();
    }
    
    /**
     * Gets the attribute bracket handler syntax for this Attribute.
     *
     * E.G.
     * <code>
     * <attribute:minecraft:generic.max_health>
     * </code>
     *
     * @return The attribute bracket handler syntax for this Block.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(Attribute internal) {
        
        return "<attribute:" + internal.getRegistryName() + ">";
    }
    
}
