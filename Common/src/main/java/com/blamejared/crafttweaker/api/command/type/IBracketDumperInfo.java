package com.blamejared.crafttweaker.api.command.type;

import com.mojang.brigadier.Command;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.MutableComponent;

import java.util.stream.Stream;

public interface IBracketDumperInfo extends Command<CommandSourceStack> {
    
    String subCommandName();
    
    MutableComponent description();
    
    String dumpedFileName();
    
    Stream<String> values();
    
}
