package com.blamejared.crafttweaker.api.command.type;


import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.command.CommandUtilities;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public final class BracketDumperInfo implements Command<CommandSourceStack> {
    
    private final String bepHandlerName;
    private final String dumpedFileName;
    private final String subCommandName;
    private final List<MethodHandle> methodHandles = new ArrayList<>(1);
    
    
    public BracketDumperInfo(String bepHandlerName, String subCommandName, String dumpedFileName) {
        
        this.bepHandlerName = bepHandlerName;
        this.subCommandName = subCommandName.isEmpty() ? makePlural(bepHandlerName) : subCommandName;
        this.dumpedFileName = dumpedFileName.isEmpty() ? bepHandlerName.toLowerCase() : dumpedFileName;
    }
    
    private static String makePlural(String s) {
        
        if(s.endsWith("s")) {
            return s;
        } else if(s.endsWith("x")) {
            return s;
        } else {
            return s + "s";
        }
    }
    
    public void addMethodHandle(MethodHandle methodHandle) {
        
        this.methodHandles.add(methodHandle);
    }
    
    public String getSubCommandName() {
        
        return subCommandName;
    }
    
    public MutableComponent getDescription() {
        
        return new TranslatableComponent("crafttweaker.command.description.dump.info", CommandUtilities.makeNoticeable(bepHandlerName));
    }
    
    public String getDumpedFileName() {
        
        return dumpedFileName;
    }
    
    public Stream<String> getDumpedValuesStream() {
        
        return methodHandles.stream().flatMap(methodHandle -> {
            try {
                final Collection<String> invoke = (Collection<String>) methodHandle.invokeExact();
                return invoke.stream();
            } catch(Throwable throwable) {
                CraftTweakerAPI.LOGGER.error("Error executing Bracket dumper '{}'", bepHandlerName, throwable);
                return Stream.empty();
            }
        });
    }
    
    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        
        ServerPlayer player = context.getSource().getPlayerOrException();
        getDumpedValuesStream().forEach(bepCall -> CraftTweakerAPI.LOGGER.info("- " + bepCall));
        CommandUtilities.send(new TranslatableComponent("crafttweaker.command.dump.generated", CommandUtilities.makeNoticeable(bepHandlerName)).withStyle(ChatFormatting.GREEN), player);
        CommandUtilities.send(CommandUtilities.openingLogFile(new TranslatableComponent("crafttweaker.command.check.log", CommandUtilities.getFormattedLogFile()).withStyle(ChatFormatting.GREEN)), player);
        return Command.SINGLE_SUCCESS;
    }
    
}
