package com.blamejared.crafttweaker.natives.loot;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.storage.loot.IntRange;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/IntRange")
@NativeTypeRegistration(value = IntRange.class, zenCodeName = "crafttweaker.api.loot.IntRange")
public final class ExpandIntRange {
    
    @ZenCodeType.StaticExpansionMethod
    public static IntRange exactly(final int value) {
        
        return IntRange.exact(value);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static IntRange between(final int min, final int max) {
        
        return IntRange.range(min, max);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static IntRange atLeast(final int min) {
        
        return IntRange.lowerBound(min);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static IntRange atMost(final int max) {
        
        return IntRange.upperBound(max);
    }
    
}
