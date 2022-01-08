package com.blamejared.crafttweaker.natives.event.block;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.world.BlockEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docEvent canceled the block will not be placed
 */
@ZenRegister
@Document("vanilla/api/event/block/BlockPlaceEvent")
@NativeTypeRegistration(value = BlockEvent.EntityPlaceEvent.class, zenCodeName = "crafttweaker.api.event.block.BlockPlaceEvent")
public class ExpandBlockPlaceEvent {
    
    @ZenCodeType.Getter("entity")
    @ZenCodeType.Method
    public static Entity getEntity(BlockEvent.EntityPlaceEvent internal) {
        
        return internal.getEntity();
    }
    
    @ZenCodeType.Getter("placedBlock")
    @ZenCodeType.Method
    public static BlockState getPlacedBlock(BlockEvent.EntityPlaceEvent internal) {
        
        return internal.getPlacedBlock();
    }
    
    @ZenCodeType.Getter("placedAgainst")
    @ZenCodeType.Method
    public static BlockState getPlacedAgainst(BlockEvent.EntityPlaceEvent internal) {
        
        return internal.getPlacedAgainst();
    }
    
    @ZenCodeType.Getter("replacedBlock")
    @ZenCodeType.Method
    public static BlockState getReplacedBlock(BlockEvent.EntityPlaceEvent internal) {
        
        return internal.getBlockSnapshot().getReplacedBlock();
    }
    
}
