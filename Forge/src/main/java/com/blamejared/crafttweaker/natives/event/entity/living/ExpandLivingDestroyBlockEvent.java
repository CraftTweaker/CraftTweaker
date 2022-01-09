package com.blamejared.crafttweaker.natives.event.entity.living;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingDestroyBlockEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docEvent canceled the block will not be destroyed.
 */
@ZenRegister
@Document("forge/api/event/living/LivingDestroyBlockEvent")
@NativeTypeRegistration(value = LivingDestroyBlockEvent.class, zenCodeName = "crafttweaker.api.event.entity.living.LivingDestroyBlockEvent")
public class ExpandLivingDestroyBlockEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("state")
    public static BlockState getState(LivingDestroyBlockEvent internal) {
        
        return internal.getState();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("pos")
    public static BlockPos getPos(LivingDestroyBlockEvent internal) {
        
        return internal.getPos();
    }
    
}
