package com.blamejared.crafttweaker.natives.text;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeConstructor;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.network.chat.TextComponent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/text/TextComponent")
@NativeTypeRegistration(value = TextComponent.class, zenCodeName = "crafttweaker.api.text.TextComponent", constructors = {
        @NativeConstructor({
                @NativeConstructor.ConstructorParameter(type = String.class, name = "text")
        })
})
public class ExpandTextComponent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("text")
    public static String getText(TextComponent internal) {
        
        return internal.getText();
    }
    
    
}
