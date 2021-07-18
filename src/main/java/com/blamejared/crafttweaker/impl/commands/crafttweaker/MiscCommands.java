package com.blamejared.crafttweaker.impl.commands.crafttweaker;

import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.commands.CTCommands;
import com.blamejared.crafttweaker.impl.commands.CTItemArgument;
import com.blamejared.crafttweaker.impl.commands.CommandUtilities;
import com.blamejared.crafttweaker.impl.events.CTEventHandler;
import com.blamejared.crafttweaker.impl.network.PacketHandler;
import com.blamejared.crafttweaker.impl.network.messages.MessageOpen;
import com.blamejared.crafttweaker.impl_native.entity.ExpandPlayerEntity;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.network.PacketDistributor;
import org.apache.logging.log4j.util.TriConsumer;

public final class MiscCommands {
    private MiscCommands() {}
    
    public static void registerMiscCommands(final TriConsumer<LiteralArgumentBuilder<CommandSource>, String, String> registerCustomCommand) {
        registerCustomCommand.accept(
                Commands.literal("copy")
                        .then(Commands.argument("toCopy", StringArgumentType.string())
                                .executes(context -> {
                                    ServerPlayerEntity entity = context.getSource().asPlayer();
                                    CommandUtilities.copy(entity, context.getArgument("toCopy", String.class));
                                    CommandUtilities.send("Copied!", entity);
                                    return 0;
                                })
                        ),
                null,
                null
        );
    
        registerCustomCommand.accept(
                Commands.literal("give").requires((source) -> source.hasPermissionLevel(2))
                        .then(Commands.argument("item", CTItemArgument.INSTANCE)
                                .executes(context -> {
                                    ExpandPlayerEntity.give(context.getSource().asPlayer(), context.getArgument("item", IItemStack.class));
                                    return 0;
                                })
                        ),
                "give",
                "Gives the player an item using the Bracket handler syntax. You can also apply tags by appending a .withTag() call."
        );
        
        CTCommands.registerCommand(CTCommands.playerCommand("reload", "Points people to /reload", (player, stack) -> {
            CommandUtilities.send(
                    CommandUtilities.run(
                            // TODO("Localization")
                            new StringTextComponent(CommandUtilities.color("CraftTweaker reload has been deprecated! Use the vanilla /reload instead!", TextFormatting.AQUA)),
                            "/reload"
                    ),
                    player
            );
            return 0;
        }));
    
        CTCommands.registerCommand(CTCommands.playerCommand("discord", "Opens a link to discord", (player, stack) -> {
            PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new MessageOpen("https://discord.blamejared.com"));
            return 0;
        }));
    
        CTCommands.registerCommand(CTCommands.playerCommand("issues", "Opens a link to the issue tracker", (player, stack) -> {
            PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new MessageOpen("https://github.com/CraftTweaker/CraftTweaker/issues"));
            return 0;
        }));
    
        CTCommands.registerCommand(CTCommands.playerCommand("patreon", "Opens a link to patreon", (player, stack) -> {
            PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new MessageOpen("https://patreon.com/jaredlll08"));
            return 0;
        }));
    
        CTCommands.registerCommand(CTCommands.playerCommand(
                "block_info",
                "Activates or deactivates the block reader. In block info mode, right-clicking a block will tell you it's name, metadata and Tile Entity data if applicable.",
                (player, stack) -> {
                    if (CTEventHandler.BLOCK_INFO_PLAYERS.contains(player)) {
                        CTEventHandler.BLOCK_INFO_PLAYERS.remove(player);
                        CommandUtilities.send("Block info mode deactivated", player);
                        
                        if (CTEventHandler.BLOCK_INFO_PLAYERS.isEmpty()) {
                            MinecraftForge.EVENT_BUS.unregister(CTEventHandler.ListenBlockInfo.INSTANCE);
                        }
                    } else {
                        if (CTEventHandler.BLOCK_INFO_PLAYERS.isEmpty()) {
                            MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, false, PlayerInteractEvent.RightClickBlock.class, CTEventHandler.ListenBlockInfo.INSTANCE);
                        }
                        
                        CTEventHandler.BLOCK_INFO_PLAYERS.add(player);
                        CommandUtilities.send("Block info mode activated. Right-click a block to see its data.", player);
                    }
                    return 0;
                })
        );
    
        CTCommands.registerCommand(CTCommands.playerCommand(
                "entity_info",
                "Activates or deactivates the entity reader. In entity info mode, right-clicking an entity will tell you it's name and data.",
                (player, stack) -> {
                    if (CTEventHandler.ENTITY_INFO_PLAYERS.contains(player)) {
                        CTEventHandler.ENTITY_INFO_PLAYERS.remove(player);
                        CommandUtilities.send("Entity info mode deactivated", player);
                    
                        if (CTEventHandler.ENTITY_INFO_PLAYERS.isEmpty()) {
                            MinecraftForge.EVENT_BUS.unregister(CTEventHandler.ListenBlockInfo.INSTANCE);
                        }
                    } else {
                        if (CTEventHandler.ENTITY_INFO_PLAYERS.isEmpty()) {
                            MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, false, PlayerInteractEvent.EntityInteract.class, CTEventHandler.ListenEntityInfo.INSTANCE);
                        }
                    
                        CTEventHandler.ENTITY_INFO_PLAYERS.add(player);
                        CommandUtilities.send("Entity info mode activated. Right-click an entity to see its data.", player);
                    }
                    return 0;
                })
        );
    }
}
