package com.blamejared.crafttweaker.natives.util.math;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.Direction;
import net.minecraft.core.FrontAndTop;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/util/math/FrontAndTop")
@NativeTypeRegistration(value = FrontAndTop.class, zenCodeName = "crafttweaker.api.util.math.FrontAndTop")
@BracketEnum("minecraft:direction/frontandtop")
public class ExpandFrontAndTop {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("front")
    public static Direction front(FrontAndTop internal) {
        
        return internal.front();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("top")
    public static Direction top(FrontAndTop internal) {
        
        return internal.top();
    }
    
}
