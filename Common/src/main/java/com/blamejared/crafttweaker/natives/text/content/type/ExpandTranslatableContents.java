package com.blamejared.crafttweaker.natives.text.content.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;
import java.util.Objects;

@ZenRegister
@Document("vanilla/api/text/content/type/TranslatableContents")
@NativeTypeRegistration(value = TranslatableContents.class, zenCodeName = "crafttweaker.api.text.content.type.TranslatableContents")
public class ExpandTranslatableContents {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("key")
    public static String getKey(TranslatableContents internal) {
        
        return internal.getKey();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("args")
    public static String[] getArgs(TranslatableContents internal) {
        
        return Arrays.stream(internal.getArgs()).map(Objects::toString).toArray(String[]::new);
    }
    
}
