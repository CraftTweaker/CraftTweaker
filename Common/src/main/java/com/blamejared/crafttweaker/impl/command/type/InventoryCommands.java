package com.blamejared.crafttweaker.impl.command.type;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker.api.tag.manager.TagManagerItem;
import com.blamejared.crafttweaker.api.util.ItemStackUtil;
import com.blamejared.crafttweaker.impl.command.CTCommands;
import com.blamejared.crafttweaker.impl.command.CommandUtilities;
import com.blamejared.crafttweaker.impl.command.boilerplate.CommandImpl;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker.platform.helper.inventory.IInventoryWrapper;
import com.mojang.brigadier.Command;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class InventoryCommands {
    
    private InventoryCommands() {}
    
    public static void registerCommands() {
        
        CTCommands.registerCommand(new CommandImpl("inventory", new TranslatableComponent("crafttweaker.command.description.inventory"), builder -> builder.executes(context -> {
            ServerPlayer player = context.getSource().getPlayerOrException();
            
            IInventoryWrapper inventory = Services.PLATFORM.getPlayerInventory(player);
            final String inventoryContents = IntStream.range(0, inventory.getContainerSize())
                    .mapToObj(inventory::getItem)
                    .filter(it -> !it.isEmpty())
                    .map(Services.PLATFORM::createMCItemStack)
                    .map(IItemStack::getCommandString)
                    .collect(Collectors.joining("\n", "Inventory items\n", ""));
            
            CraftTweakerAPI.LOGGER.info(inventoryContents);
            CommandUtilities.send(CommandUtilities.openingLogFile(new TranslatableComponent("crafttweaker.command.list.check.log", CommandUtilities.makeNoticeable(new TranslatableComponent("crafttweaker.command.misc.inventory.list")), CommandUtilities.getFormattedLogFile()).withStyle(ChatFormatting.GREEN)), player);
            
            return Command.SINGLE_SUCCESS;
        })));
        
        CTCommands.registerCommand("inventory", new CommandImpl("tags", new TranslatableComponent("crafttweaker.command.description.inventory.tags"), builder -> builder.executes(context -> {
            ServerPlayer player = context.getSource().getPlayerOrException();
            
            IInventoryWrapper inventory = Services.PLATFORM.getPlayerInventory(player);
            
            final String inventoryContents = IntStream.range(0, inventory.getContainerSize())
                    .mapToObj(inventory::getItem)
                    .filter(it -> !it.isEmpty())
                    .map(it -> Pair.of(ItemStackUtil.getCommandString(it), TagManagerItem.INSTANCE.getAllTagsFor(it.getItem())))
                    .map(it -> it.getFirst() + '\n' + stringify(it.getSecond()))
                    .collect(Collectors.joining("\n", "Inventory item tags\n", ""));
            
            CraftTweakerAPI.LOGGER.info(inventoryContents);
            CommandUtilities.send(CommandUtilities.openingLogFile(new TranslatableComponent("crafttweaker.command.list.check.log", CommandUtilities.makeNoticeable(new TranslatableComponent("crafttweaker.command.misc.inventory.list.tag")), CommandUtilities.getFormattedLogFile()).withStyle(ChatFormatting.GREEN)), player);
            
            return Command.SINGLE_SUCCESS;
        })));
    }
    
    private static String stringify(final Collection<MCTag<Item>> tags) {
        
        if(tags.isEmpty()) {
            return "- No tags";
        }
        
        return tags.stream()
                .map(MCTag::getCommandString)
                .map(it -> String.format("- %s", it))
                .collect(Collectors.joining("\n"));
    }
    
}
