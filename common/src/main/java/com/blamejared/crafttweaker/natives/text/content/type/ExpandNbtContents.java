package com.blamejared.crafttweaker.natives.text.content.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.NbtContents;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/text/content/type/NbtContents")
@NativeTypeRegistration(value = NbtContents.class, zenCodeName = "crafttweaker.api.text.content.type.NbtContents")
public class ExpandNbtContents {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("nbtPath")
    public static String getNbtPath(NbtContents internal) {
        
        return internal.getNbtPath();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isInterpreting")
    public static boolean isInterpreting(NbtContents internal) {
        
        return internal.isInterpreting();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("separator")
    @ZenCodeType.Nullable
    public static Component getSeparator(NbtContents internal) {
        
        return internal.getSeparator().orElse(null);
    }
    
}
