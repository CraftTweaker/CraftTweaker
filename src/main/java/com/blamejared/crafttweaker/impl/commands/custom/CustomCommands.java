package com.blamejared.crafttweaker.impl.commands.custom;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.*;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.commands.custom.CustomCommands")
@Document("vanilla/api/commands/custom/CustomCommands")
public class CustomCommands {
    
    private static final List<LiteralArgumentBuilder<CommandSource>> BUILDERS = new ArrayList<>();
    private static boolean SERVER_STARTED = false;
    
    private CustomCommands() {
    }
    
    public static void init(CommandDispatcher<CommandSource> dispatcher) {
        SERVER_STARTED = true;
        BUILDERS.forEach(dispatcher::register);
    }
    
    @ZenCodeType.Method
    public static void registerCommand(MCLiteralArgumentBuilder builder) {
        
        if(SERVER_STARTED) {
            CraftTweakerAPI.logError("You can only add commands in '#loader setupCommon'!");
            return;
        }
        BUILDERS.add(builder.getInternal());
    }
    
    @ZenCodeType.Method
    public static MCLiteralArgumentBuilder literal(String name) {
        return new MCLiteralArgumentBuilder(Commands.literal(name));
    }
    
    @ZenCodeType.Method
    public static MCRequiredArgumentBuilder argument(String name) {
        return new MCRequiredArgumentBuilder(Commands.argument(name, StringArgumentType.string()));
    }
}
