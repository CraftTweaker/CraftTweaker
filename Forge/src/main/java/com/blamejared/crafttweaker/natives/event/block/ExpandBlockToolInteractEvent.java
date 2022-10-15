package com.blamejared.crafttweaker.natives.event.block;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.event.world.BlockEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Fired when this block is right clicked by a tool to change its state.
 * For example: Used to determine if an axe can strip a log, a shovel can turn grass into a path, or a hoe can till dirt into farmland.
 *
 * @docEvent canceled this will prevent the tool from changing the block's state.
 */
@ZenRegister
@Document("forge/api/event/block/BlockToolInteractEvent")
@NativeTypeRegistration(value = BlockEvent.BlockToolInteractEvent.class, zenCodeName = "crafttweaker.api.event.block.BlockToolInteractEvent")
public class ExpandBlockToolInteractEvent {
    
}
