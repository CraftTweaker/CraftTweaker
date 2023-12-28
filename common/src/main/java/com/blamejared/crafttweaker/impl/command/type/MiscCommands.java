package com.blamejared.crafttweaker.impl.command.type;

import com.blamejared.crafttweaker.api.command.CommandUtilities;
import com.blamejared.crafttweaker.api.command.argument.IItemStackArgument;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.plugin.ICommandRegistrationHandler;
import com.blamejared.crafttweaker.natives.entity.type.player.ExpandPlayer;
import com.blamejared.crafttweaker.platform.services.IEventHelper;
import com.mojang.brigadier.Command;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.stream.Stream;

public final class MiscCommands {
    
    private static final String DOCS = "https://docs.blamejared.com";
    private static final String DISCORD = "https://discord.blamejared.com";
    private static final String ISSUES = "https://github.com/CraftTweaker/CraftTweaker/issues";
    private static final String PATREON = "https://patreon.com/jaredlll08";
    
    private MiscCommands() {}
    
    public static void registerCommands(final ICommandRegistrationHandler handler) {
        
        handler.registerRootCommand(
                "give",
                Component.translatable("crafttweaker.command.description.give"),
                builder -> builder.requires((source) -> source.hasPermission(2)) // TODO("Permission API")
                        .then(Commands.argument("item", IItemStackArgument.get())
                                .executes(context -> {
                                    ExpandPlayer.give(context.getSource()
                                            .getPlayerOrException(), context.getArgument("item", IItemStack.class), -1);
                                    return Command.SINGLE_SUCCESS;
                                })
                        )
        );
        
        handler.registerRootCommand(
                "reload",
                Component.translatable("crafttweaker.command.description.reload"),
                builder -> builder.executes(context -> {
                    final ServerPlayer player = context.getSource().getPlayerOrException();
                    CommandUtilities.send(
                            CommandUtilities.run(
                                    Component.translatable("crafttweaker.command.misc.reload.info")
                                            .withStyle(ChatFormatting.AQUA),
                                    "/reload"
                            ),
                            player
                    );
                    return Command.SINGLE_SUCCESS;
                })
        );
        
        handler.registerRootCommand(
                "discord",
                Component.translatable("crafttweaker.command.description.discord"),
                builder -> builder.executes(context -> {
                    final ServerPlayer player = context.getSource().getPlayerOrException();
                    CommandUtilities.send(CommandUtilities.openingUrl(Component.translatable("crafttweaker.command.misc.link", CommandUtilities.makeNoticeable(DISCORD))
                            .withStyle(ChatFormatting.GREEN), DISCORD), player);
                    return Command.SINGLE_SUCCESS;
                })
        );
        
        handler.registerRootCommand(
                "issues",
                Component.translatable("crafttweaker.command.description.issues"),
                builder -> builder.executes(context -> {
                    final ServerPlayer player = context.getSource().getPlayerOrException();
                    CommandUtilities.send(CommandUtilities.openingUrl(Component.translatable("crafttweaker.command.misc.link", CommandUtilities.makeNoticeable(ISSUES))
                            .withStyle(ChatFormatting.GREEN), ISSUES), player);
                    return Command.SINGLE_SUCCESS;
                })
        );
        
        handler.registerRootCommand(
                "patreon",
                Component.translatable("crafttweaker.command.description.patreon"),
                builder -> builder.executes(context -> {
                    final ServerPlayer player = context.getSource().getPlayerOrException();
                    CommandUtilities.send(CommandUtilities.openingUrl(Component.translatable("crafttweaker.command.misc.link", CommandUtilities.makeNoticeable(PATREON))
                            .withStyle(ChatFormatting.GREEN), PATREON), player);
                    return Command.SINGLE_SUCCESS;
                })
        );
        
        Stream.of("wiki", "docs", "examples").forEach(id -> handler.registerRootCommand(
                id,
                Component.translatable("crafttweaker.command.description.docs"),
                builder -> builder.executes(context -> {
                    final ServerPlayer player = context.getSource().getPlayerOrException();
                    CommandUtilities.send(CommandUtilities.openingUrl(Component.translatable("crafttweaker.command.misc.link", CommandUtilities.makeNoticeable(DOCS))
                            .withStyle(ChatFormatting.GREEN), DOCS), player);
                    return Command.SINGLE_SUCCESS;
                })
        ));
        
        handler.registerRootCommand(
                "ctgui",
                Component.translatable("crafttweaker.command.description.docs"),
                builder -> builder.executes(context -> {
                    final ServerPlayer player = context.getSource().getPlayerOrException();
                    CommandUtilities.send(Component.translatable("crafttweaker.command.misc.ctgui"), player);
                    return Command.SINGLE_SUCCESS;
                })
        );
        
        handler.registerRootCommand(
                "block_info",
                Component.translatable("crafttweaker.command.description.info.block"),
                builder -> builder.executes(context -> {
                    final ServerPlayer player = context.getSource().getPlayerOrException();
                    
                    if(IEventHelper.BLOCK_INFO_PLAYERS.contains(player)) {
                        IEventHelper.BLOCK_INFO_PLAYERS.remove(player);
                        CommandUtilities.send(Component.translatable("crafttweaker.command.info.block.deactivated"), player);
                    } else {
                        IEventHelper.BLOCK_INFO_PLAYERS.add(player);
                        CommandUtilities.send(Component.translatable("crafttweaker.command.info.block.activated"), player);
                    }
                    
                    return Command.SINGLE_SUCCESS;
                })
        );
        
        handler.registerRootCommand(
                "entity_info",
                Component.translatable("crafttweaker.command.description.info.entity"),
                builder -> builder.executes(context -> {
                    final ServerPlayer player = context.getSource().getPlayerOrException();
                    
                    if(IEventHelper.ENTITY_INFO_PLAYERS.contains(player)) {
                        IEventHelper.ENTITY_INFO_PLAYERS.remove(player);
                        CommandUtilities.send(Component.translatable("crafttweaker.command.info.entity.deactivated"), player);
                    } else {
                        IEventHelper.ENTITY_INFO_PLAYERS.add(player);
                        CommandUtilities.send(Component.translatable("crafttweaker.command.info.entity.activated"), player);
                    }
                    
                    return Command.SINGLE_SUCCESS;
                })
        );
    }
    
}
