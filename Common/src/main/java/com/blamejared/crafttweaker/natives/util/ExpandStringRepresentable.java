package com.blamejared.crafttweaker.natives.util;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.util.StringRepresentable;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/util/StringRepresentable")
@NativeTypeRegistration(value = StringRepresentable.class, zenCodeName = "crafttweaker.api.util.StringRepresentable")
public class ExpandStringRepresentable {
    
    /**
     * Gets the serialized name.
     *
     * @return the serialized name.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("serializedName")
    public static String getSerializedName(StringRepresentable internal) {
        
        return internal.getSerializedName();
    }
    
}
