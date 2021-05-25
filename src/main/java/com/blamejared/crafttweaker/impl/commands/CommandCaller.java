package com.blamejared.crafttweaker.impl.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;

public interface CommandCaller {
    
    int executeCommand(CommandContext<CommandSource> context) throws CommandSyntaxException;
    
}
