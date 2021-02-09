package com.blamejared.crafttweaker.impl_native.event.block;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.world.BlockEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @author youyihj
 */
@ZenRegister
@Document("vanilla/api/event/block/MCBlockPlaceEvent")
@NativeTypeRegistration(value = BlockEvent.EntityPlaceEvent.class, zenCodeName = "crafttweaker.api.event.block.MCBlockPlaceEvent")
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
