package com.blamejared.crafttweaker.natives.text.content.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.network.chat.contents.PlainTextContents;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/text/content/type/PlainTextContents")
@NativeTypeRegistration(value = PlainTextContents.class, zenCodeName = "crafttweaker.api.text.content.type.PlainTextContents")
public class ExpandPlainTextContents {
    
    @ZenCodeType.Getter("text")
    public static String text(PlainTextContents internal) {
        
        return internal.text();
    }
    
}
