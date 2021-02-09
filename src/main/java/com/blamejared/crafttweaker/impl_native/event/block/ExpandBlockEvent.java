package com.blamejared.crafttweaker.impl_native.event.block;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @author youyihj
 */
@ZenRegister
@Document("vanilla/api/event/block/MCBlockEvent")
@NativeTypeRegistration(value = BlockEvent.class, zenCodeName = "crafttweaker.api.event.block.MCBlockEvent")
public class ExpandBlockEvent {
    @ZenCodeType.Getter("state")
    @ZenCodeType.Method
    public static BlockState getBlockState(BlockEvent internal) {
        return internal.getState();
    }
    
    @ZenCodeType.Getter("world")
    @ZenCodeType.Method
    public static World getWorld(BlockEvent internal) {
        if (internal.getWorld() instanceof World) {
            return (World) internal.getWorld();
        }
        throw new IllegalStateException("the event is not fired on client world or server world.");
    }
    
    @ZenCodeType.Getter("pos")
    @ZenCodeType.Method
    public static BlockPos getPos(BlockEvent internal) {
        return internal.getPos();
    }
}
