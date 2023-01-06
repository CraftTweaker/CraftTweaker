package com.blamejared.crafttweaker.impl.command.type;

import com.blamejared.crafttweaker.api.command.CommandUtilities;
import com.blamejared.crafttweaker.api.plugin.ICommandRegistrationHandler;
import com.blamejared.crafttweaker.api.tag.CraftTweakerTagRegistry;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker.api.util.ItemStackUtil;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker.platform.helper.inventory.IInventoryWrapper;
import com.mojang.brigadier.Command;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class InventoryCommands {
    
    private InventoryCommands() {}
    
    public static void registerCommands(final ICommandRegistrationHandler handler) {
        
        handler.registerRootCommand(
                "inventory",
                Component.translatable("crafttweaker.command.description.inventory"),
                builder -> builder.executes(context -> {
                    final ServerPlayer player = context.getSource().getPlayerOrException();
                    final IInventoryWrapper inventory = Services.PLATFORM.getPlayerInventory(player);
                    final String inventoryContents = IntStream.range(0, inventory.getContainerSize())
                            .mapToObj(inventory::getItem)
                            .filter(it -> !it.isEmpty())
                            .map(ItemStackUtil::getCommandString)
                            .collect(Collectors.joining("\n", "Inventory items\n", ""));
                    
                    CommandUtilities.COMMAND_LOGGER.info(inventoryContents);
                    CommandUtilities.send(CommandUtilities.openingLogFile(Component.translatable("crafttweaker.command.list.check.log", CommandUtilities.makeNoticeable(Component.translatable("crafttweaker.command.misc.inventory.list")), CommandUtilities.getFormattedLogFile())
                            .withStyle(ChatFormatting.GREEN)), player);
                    
                    return Command.SINGLE_SUCCESS;
                })
        );
        
        handler.registerSubCommand(
                "inventory",
                "tags",
                Component.translatable("crafttweaker.command.description.inventory.tags"),
                builder -> builder.executes(context -> {
                    final ServerPlayer player = context.getSource().getPlayerOrException();
                    final IInventoryWrapper inventory = Services.PLATFORM.getPlayerInventory(player);
                    final String inventoryContents = IntStream.range(0, inventory.getContainerSize())
                            .mapToObj(inventory::getItem)
                            .filter(it -> !it.isEmpty())
                            .map(it -> Pair.of(ItemStackUtil.getCommandString(it), CraftTweakerTagRegistry.INSTANCE.knownTagManager(Registry.ITEM_REGISTRY)
                                    .getTagsFor(it.getItem())))
                            .map(it -> it.getFirst() + '\n' + stringify(it.getSecond()))
                            .collect(Collectors.joining("\n", "Inventory item tags\n", ""));
                    
                    CommandUtilities.COMMAND_LOGGER.info(inventoryContents);
                    CommandUtilities.send(CommandUtilities.openingLogFile(Component.translatable("crafttweaker.command.list.check.log", CommandUtilities.makeNoticeable(Component.translatable("crafttweaker.command.misc.inventory.list.tag")), CommandUtilities.getFormattedLogFile())
                            .withStyle(ChatFormatting.GREEN)), player);
                    
                    return Command.SINGLE_SUCCESS;
                })
        );
    }
    
    private static String stringify(final Collection<KnownTag<Item>> tags) {
        
        if(tags.isEmpty()) {
            return "- No tags";
        }
        
        return tags.stream()
                .map(KnownTag::getCommandString)
                .map(it -> String.format("- %s", it))
                .collect(Collectors.joining("\n"));
    }
    
}
