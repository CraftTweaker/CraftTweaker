package com.blamejared.crafttweaker.impl_native.advancement;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.advancements.FrameType;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/advancement/FrameType")
@ZenCodeType.Name("crafttweaker.api.advancement.FrameType")
public enum MCFrameType {
    
    @ZenCodeType.Field TASK(FrameType.TASK),
    @ZenCodeType.Field CHALLENGE(FrameType.CHALLENGE),
    @ZenCodeType.Field GOAL(FrameType.GOAL);
    
    private final FrameType vanillaType;
    
    MCFrameType(final FrameType vanillaType) {
        
        this.vanillaType = vanillaType;
    }
    
    public FrameType toFrameType() {
        
        return this.vanillaType;
    }
}
