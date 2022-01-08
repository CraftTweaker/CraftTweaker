package com.blamejared.crafttweaker.impl.command.boilerplate;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface CommandCallerPlayer extends CommandCaller {
    
    default int executeCommand(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        
        return executeCommand(context.getSource().getPlayerOrException(), context.getSource()
                .getPlayerOrException()
                .getMainHandItem());
    }
    
    int executeCommand(Player player, ItemStack stack);
    
}
