package com.blamejared.crafttweaker.natives.event.block;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.world.BlockEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/event/block/BlockEvent")
@NativeTypeRegistration(value = BlockEvent.class, zenCodeName = "crafttweaker.api.event.block.BlockEvent")
public class ExpandBlockEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("state")
    public static BlockState getBlockState(BlockEvent internal) {
        
        return internal.getState();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("world")
    public static Level getWorld(BlockEvent internal) {
        
        if(internal.getWorld() instanceof Level) {
            return (Level) internal.getWorld();
        }
        throw new IllegalStateException("The event was not fired on a client world or a server world.");
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("pos")
    public static BlockPos getPos(BlockEvent internal) {
        
        return internal.getPos();
    }
    
}
