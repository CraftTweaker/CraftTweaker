package com.blamejared.crafttweaker.impl.command.boilerplate;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;

public interface CommandCaller {
    
    int executeCommand(CommandContext<CommandSourceStack> context) throws CommandSyntaxException;
    
}
