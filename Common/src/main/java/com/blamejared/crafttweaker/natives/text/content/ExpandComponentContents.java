package com.blamejared.crafttweaker.natives.text.content;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.network.chat.ComponentContents;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/text/content/ComponentContents")
@NativeTypeRegistration(value = ComponentContents.class, zenCodeName = "crafttweaker.api.text.content.ComponentContents")
public class ExpandComponentContents {
    
    @ZenCodeType.StaticExpansionMethod
    public static ComponentContents empty() {
        
        return ComponentContents.EMPTY;
    }
    
}
