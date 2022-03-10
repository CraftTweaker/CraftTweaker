package com.blamejared.crafttweaker.api.plugin;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.MutableComponent;

import java.util.function.Consumer;

public interface ICommandRegistrationHandler {
    
    @FunctionalInterface
    interface CommandBuilder extends Consumer<LiteralArgumentBuilder<CommandSourceStack>> {
        
        default void buildCommand(final LiteralArgumentBuilder<CommandSourceStack> builder) {
            
            this.accept(builder);
        }
        
    }
    
    void registerRootCommand(final String commandId, final MutableComponent description, final CommandBuilder builder);
    
    void registerSubCommand(final String parentCommand, final String commandId, final MutableComponent description, final CommandBuilder builder);
    
    void registerDump(final String dumpId, final MutableComponent description, final CommandBuilder builder);
    
}
