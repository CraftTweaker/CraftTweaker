package com.blamejared.crafttweaker.impl.command.type;

import com.blamejared.crafttweaker.api.command.CommandUtilities;
import com.blamejared.crafttweaker.api.command.argument.IItemStackArgument;
import com.blamejared.crafttweaker.api.command.boilerplate.CommandImpl;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.command.CTCommands;
import com.blamejared.crafttweaker.natives.entity.type.player.ExpandPlayer;
import com.blamejared.crafttweaker.platform.services.IEventHelper;
import com.mojang.brigadier.Command;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;

public final class MiscCommands {
    
    private MiscCommands() {}
    
    public static void registerCommands() {
        
        CTCommands.registerCommand(new CommandImpl("give", new TranslatableComponent("crafttweaker.command.description.give"), builder -> {
            builder.requires((source) -> source.hasPermission(2))
                    .then(Commands.argument("item", IItemStackArgument.get())
                            .executes(context -> {
                                ExpandPlayer.give(context.getSource()
                                        .getPlayerOrException(), context.getArgument("item", IItemStack.class), -1);
                                return Command.SINGLE_SUCCESS;
                            })
                    );
        }));
        
        CTCommands.registerCommand(new CommandImpl("reload", new TranslatableComponent("crafttweaker.command.description.reload"), builder -> {
            builder.executes(context -> {
                ServerPlayer player = context.getSource().getPlayerOrException();
                CommandUtilities.send(
                        CommandUtilities.run(
                                new TranslatableComponent("crafttweaker.command.misc.reload.info").withStyle(ChatFormatting.AQUA),
                                "/reload"
                        ),
                        player
                );
                return Command.SINGLE_SUCCESS;
            });
        }));
        
        CTCommands.registerCommand(new CommandImpl("discord", new TranslatableComponent("crafttweaker.command.description.discord"), builder -> {
            builder.executes(context -> {
                ServerPlayer player = context.getSource().getPlayerOrException();
                String link = "https://discord.blamejared.com";
                CommandUtilities.send(CommandUtilities.openingUrl(new TranslatableComponent("crafttweaker.command.misc.link", CommandUtilities.makeNoticeable(link)).withStyle(ChatFormatting.GREEN), link), player);
                return Command.SINGLE_SUCCESS;
            });
        }));
        
        CTCommands.registerCommand(new CommandImpl("issues", new TranslatableComponent("crafttweaker.command.description.issues"), builder -> {
            builder.executes(context -> {
                ServerPlayer player = context.getSource().getPlayerOrException();
                String link = "https://github.com/CraftTweaker/CraftTweaker/issues";
                CommandUtilities.send(CommandUtilities.openingUrl(new TranslatableComponent("crafttweaker.command.misc.link", CommandUtilities.makeNoticeable(link)).withStyle(ChatFormatting.GREEN), link), player);
                return Command.SINGLE_SUCCESS;
            });
        }));
        
        CTCommands.registerCommand(new CommandImpl("patreon", new TranslatableComponent("crafttweaker.command.description.patreon"), builder -> {
            builder.executes(context -> {
                ServerPlayer player = context.getSource().getPlayerOrException();
                String link = "https://patreon.com/jaredlll08";
                CommandUtilities.send(CommandUtilities.openingUrl(new TranslatableComponent("crafttweaker.command.misc.link", CommandUtilities.makeNoticeable(link)).withStyle(ChatFormatting.GREEN), link), player);
                return Command.SINGLE_SUCCESS;
            });
        }));
        
        CTCommands.registerCommand(new CommandImpl("wiki", new TranslatableComponent("crafttweaker.command.description.docs"), builder -> {
            builder.executes(context -> {
                ServerPlayer player = context.getSource().getPlayerOrException();
                String link = "https://docs.blamejared.com";
                CommandUtilities.send(CommandUtilities.openingUrl(new TranslatableComponent("crafttweaker.command.misc.link", CommandUtilities.makeNoticeable(link)).withStyle(ChatFormatting.GREEN), link), player);
                return Command.SINGLE_SUCCESS;
            });
        }));
        
        CTCommands.registerCommand(new CommandImpl("ctgui", new TranslatableComponent("crafttweaker.command.description.ctgui"), builder -> {
            builder.executes(context -> {
                ServerPlayer player = context.getSource().getPlayerOrException();
                CommandUtilities.send(new TranslatableComponent("crafttweaker.command.misc.ctgui"), player);
                return Command.SINGLE_SUCCESS;
            });
        }));
        
        CTCommands.registerCommand(new CommandImpl("block_info", new TranslatableComponent("crafttweaker.command.description.info.block"), builder -> {
            builder.executes(context -> {
                ServerPlayer player = context.getSource().getPlayerOrException();
                
                if(IEventHelper.BLOCK_INFO_PLAYERS.contains(player)) {
                    IEventHelper.BLOCK_INFO_PLAYERS.remove(player);
                    CommandUtilities.send(new TranslatableComponent("crafttweaker.command.info.block.deactivated"), player);
                } else {
                    IEventHelper.BLOCK_INFO_PLAYERS.add(player);
                    CommandUtilities.send(new TranslatableComponent("crafttweaker.command.info.block.activated"), player);
                }
                
                return Command.SINGLE_SUCCESS;
            });
        }));
        
        CTCommands.registerCommand(new CommandImpl("entity_info", new TranslatableComponent("crafttweaker.command.description.info.entity"), builder -> {
            builder.executes(context -> {
                ServerPlayer player = context.getSource().getPlayerOrException();
                
                if(IEventHelper.ENTITY_INFO_PLAYERS.contains(player)) {
                    IEventHelper.ENTITY_INFO_PLAYERS.remove(player);
                    CommandUtilities.send(new TranslatableComponent("crafttweaker.command.info.entity.deactivated"), player);
                } else {
                    IEventHelper.ENTITY_INFO_PLAYERS.add(player);
                    CommandUtilities.send(new TranslatableComponent("crafttweaker.command.info.entity.activated"), player);
                }
                
                return Command.SINGLE_SUCCESS;
            });
        }));
        
    }
    
}
