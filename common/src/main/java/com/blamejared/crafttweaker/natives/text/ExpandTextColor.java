package com.blamejared.crafttweaker.natives.text;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.network.chat.TextColor;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/text/TextColor")
@NativeTypeRegistration(value = TextColor.class, zenCodeName = "crafttweaker.api.text.TextColor")
public class ExpandTextColor {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("value")
    public static int getValue(TextColor internal) {
        
        return internal.getValue();
    }
    
    @ZenCodeType.Method
    public static String serialize(TextColor internal) {
        
        return internal.serialize();
    }
    
}
