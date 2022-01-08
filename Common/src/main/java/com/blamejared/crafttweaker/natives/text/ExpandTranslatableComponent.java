package com.blamejared.crafttweaker.natives.text;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeConstructor;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.network.chat.TranslatableComponent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/text/TranslatableComponent")
@NativeTypeRegistration(value = TranslatableComponent.class, zenCodeName = "crafttweaker.api.text.TranslatableComponent", constructors = {
        @NativeConstructor({
                @NativeConstructor.ConstructorParameter(type = String.class, name = "key")
        }),
        @NativeConstructor({
                @NativeConstructor.ConstructorParameter(type = String.class, name = "key"),
                @NativeConstructor.ConstructorParameter(type = Object[].class, name = "args")
        })
})
public class ExpandTranslatableComponent {
    
    @ZenCodeType.Method
    public static String getKey(TranslatableComponent internal) {
        
        return internal.getKey();
    }
    
    @ZenCodeType.Method
    public static Object[] getArgs(TranslatableComponent internal) {
        
        return internal.getArgs();
    }
    
}
