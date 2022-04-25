package com.blamejared.crafttweaker.api.misc;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Fired when a Cauldron is interacted with.
 *
 * @docParam this (blockState, level, pos, player, hand, stack) => <constant:minecraft:world/interactionresult:success>
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.misc.CTCauldronInteraction")
@Document("vanilla/api/misc/CTCauldronInteraction")
@FunctionalInterface
public interface CTCauldronInteraction {
    
    /**
     * Called when a Cauldron is interacted with.
     *
     * @param blockState The blockstate of the cauldron.
     * @param level      The current level.
     * @param pos        The position of the cauldron.
     * @param player     The player that interacted.
     * @param hand       The hand that was used to interact
     * @param stack      The stack that was used to interact
     *
     * @return An {@link InteractionResult} of what should happen.
     */
    InteractionResult interact(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack);
    
}
