package com.blamejared.crafttweaker.impl.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public interface CommandCallerPlayer extends CommandCaller {
    
    default int executeCommand(CommandContext<CommandSource> context) throws CommandSyntaxException {
        
        return executeCommand(context.getSource().asPlayer(), context.getSource().asPlayer().getHeldItemMainhand());
    }
    
    int executeCommand(PlayerEntity player, ItemStack stack);
    
}
