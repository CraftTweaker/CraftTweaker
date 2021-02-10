package com.blamejared.crafttweaker.impl_native.event.entity.living;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.EventCancelable;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingDestroyBlockEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/event/living/MCLivingDestroyBlockEvent")
@EventCancelable
@NativeTypeRegistration(value = LivingDestroyBlockEvent.class, zenCodeName = "crafttweaker.api.event.entity.living.LivingDestroyBlockEvent")
public class ExpandLivingDestroyBlockEvent {
    @ZenCodeType.Getter("state")
    public static BlockState getState(LivingDestroyBlockEvent internal) {
        return internal.getState();
    }
    
    @ZenCodeType.Getter("pos")
    public static BlockPos getPos(LivingDestroyBlockEvent internal) {
        return internal.getPos();
    }
}
