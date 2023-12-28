package com.blamejared.crafttweaker.natives.text.content.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.SelectorContents;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/text/content/type/SelectorContents")
@NativeTypeRegistration(value = SelectorContents.class, zenCodeName = "crafttweaker.api.text.content.type.SelectorContents")
public class ExpandSelectorContents {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("pattern")
    public static String getPattern(SelectorContents internal) {
        
        return internal.getPattern();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("separator")
    @ZenCodeType.Nullable
    public static Component getSeparator(SelectorContents internal) {
        
        return internal.getSeparator().orElse(null);
    }
    
}
