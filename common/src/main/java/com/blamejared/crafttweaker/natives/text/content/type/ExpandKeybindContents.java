package com.blamejared.crafttweaker.natives.text.content.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.network.chat.contents.KeybindContents;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/text/content/type/KeybindContents")
@NativeTypeRegistration(value = KeybindContents.class, zenCodeName = "crafttweaker.api.text.content.type.KeybindContents")
public class ExpandKeybindContents {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("name")
    public static String getName(KeybindContents internal) {
        
        return internal.getName();
    }
    
}
