package com.blamejared.crafttweaker.impl.registry.zencode;


import com.blamejared.crafttweaker.api.command.CommandUtilities;
import com.blamejared.crafttweaker.api.command.type.IBracketDumperInfo;
import com.blamejared.crafttweaker.api.plugin.IBracketParserRegistrationHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;

import java.util.Locale;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

final class BracketDumperInfo implements IBracketDumperInfo {
    
    private final String bepHandlerName;
    private final String dumpedFileName;
    private final String subCommandName;
    private final Supplier<Stream<String>> data;
    
    BracketDumperInfo(final String bepHandlerName, final String subCommandName, final String dumpedFileName, final Supplier<Stream<String>> data) {
        
        this.bepHandlerName = bepHandlerName;
        this.subCommandName = Optional.ofNullable(subCommandName).orElseGet(() -> makePlural(this.bepHandlerName));
        this.dumpedFileName = Optional.ofNullable(dumpedFileName)
                .orElseGet(() -> this.bepHandlerName.toLowerCase(Locale.ENGLISH));
        this.data = data;
    }
    
    BracketDumperInfo(final String name, final IBracketParserRegistrationHandler.DumperData data) {
        
        this(name, data.subCommandName(), data.outputFileName(), data.data());
    }
    
    private static String makePlural(final String s) {
        
        if(s.endsWith("s") || s.endsWith("x")) {
            return s;
        } else {
            return s + "s";
        }
    }
    
    @Override
    public String subCommandName() {
        
        return this.subCommandName;
    }
    
    @Override
    public MutableComponent description() {
        
        return Component.translatable("crafttweaker.command.description.dump.info", CommandUtilities.makeNoticeable(this.bepHandlerName));
    }
    
    @Override
    public String dumpedFileName() {
        
        return this.dumpedFileName;
    }
    
    @Override
    public Stream<String> values() {
        
        return this.data.get();
    }
    
    @Override
    public int run(final CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        
        final ServerPlayer player = context.getSource().getPlayerOrException();
        this.values().forEach(bepCall -> CommandUtilities.COMMAND_LOGGER.info("- " + bepCall));
        CommandUtilities.send(Component.translatable("crafttweaker.command.dump.generated", CommandUtilities.makeNoticeable(this.bepHandlerName))
                .withStyle(ChatFormatting.GREEN), player);
        CommandUtilities.send(CommandUtilities.openingLogFile(Component.translatable("crafttweaker.command.check.log", CommandUtilities.getFormattedLogFile())
                .withStyle(ChatFormatting.GREEN)), player);
        return Command.SINGLE_SUCCESS;
    }
    
}
