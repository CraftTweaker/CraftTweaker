package com.blamejared.crafttweaker.natives.text.content.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.network.chat.contents.ScoreContents;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/text/content/type/ScoreContents")
@NativeTypeRegistration(value = ScoreContents.class, zenCodeName = "crafttweaker.api.text.content.type.ScoreContents")
public class ExpandScoreContents {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("name")
    public static String getName(ScoreContents internal) {
        
        return internal.getName();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("objective")
    public static String getObjective(ScoreContents internal) {
        
        return internal.getObjective();
    }
    
}
