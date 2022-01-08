package com.blamejared.crafttweaker.natives.world.clip;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.Vec3;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/world/clip/ClipContext")
@NativeTypeRegistration(value = ClipContext.class, zenCodeName = "crafttweaker.api.world.clip.ClipContext")
public class ExpandClipContext {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("to")
    public static Vec3 getTo(ClipContext internal) {
        
        return internal.getTo();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("from")
    public static Vec3 getFrom(ClipContext internal) {
        
        return internal.getFrom();
    }
    
}
