package com.blamejared.crafttweaker.natives.text;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.mojang.brigadier.Message;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/text/Message")
@NativeTypeRegistration(value = Message.class, zenCodeName = "crafttweaker.api.text.Message")
public class ExpandMessage {
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public static String getString(Message internal) {
        
        return internal.getString();
    }
    
}
