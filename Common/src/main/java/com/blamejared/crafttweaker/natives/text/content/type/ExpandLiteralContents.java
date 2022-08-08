package com.blamejared.crafttweaker.natives.text.content.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.network.chat.contents.LiteralContents;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/text/content/type/LiteralContents")
@NativeTypeRegistration(value = LiteralContents.class, zenCodeName = "crafttweaker.api.text.content.type.LiteralContents")
public class ExpandLiteralContents {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("text")
    public static String text(LiteralContents internal) {
        
        return internal.text();
    }
    
}
