package com.blamejared.crafttweaker.natives.event.block;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.level.BlockEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("neoforge/api/event/block/BlockEvent")
@NativeTypeRegistration(value = BlockEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.block.BlockEvent")
public class ExpandBlockEvent {
    
    @ZenCodeType.Getter("level")
    public static LevelAccessor getLevel(BlockEvent internal) {
        
        return internal.getLevel();
    }
    
    @ZenCodeType.Getter("pos")
    public static BlockPos getPos(BlockEvent internal) {
        
        return internal.getPos();
    }
    
    @ZenCodeType.Getter("state")
    public static BlockState getState(BlockEvent internal) {
        
        return internal.getState();
    }
    
}
