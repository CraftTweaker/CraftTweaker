package com.blamejared.crafttweaker.impl.commands.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.impl.commands.CTCommands;
import com.blamejared.crafttweaker.impl.commands.CommandUtilities;
import com.blamejared.crafttweaker.impl.helper.ItemStackHelper;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.blamejared.crafttweaker.impl.tag.manager.TagManagerItem;
import com.mojang.datafixers.util.Pair;
import net.minecraft.item.Item;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class InventoryCommands {
    
    private InventoryCommands() {}
    
    public static void registerInventoryCommands() {
        
        CTCommands.registerCommand(CTCommands.playerCommand("inventory", "Outputs the names of the item in your inventory", (player, stack) -> {
            player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(inventory -> {
                final String inventoryContents = IntStream.range(0, inventory.getSlots())
                        .mapToObj(inventory::getStackInSlot)
                        .filter(it -> !it.isEmpty())
                        .map(MCItemStackMutable::new)
                        .map(MCItemStackMutable::getCommandString)
                        .collect(Collectors.joining("\n", "Inventory items\n", ""));
                
                CraftTweakerAPI.logDump(inventoryContents);
                CommandUtilities.send(CommandUtilities.color("Inventory list generated! Check the crafttweaker.log file!", TextFormatting.GREEN), player);
            });
            
            return 0;
        }));
        
        CTCommands.registerCommand("inventory", CTCommands.playerCommand("tags", "Outputs the tags of the items in your inventory", (player, stack) -> {
            player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(inventory -> {
                final String inventoryContents = IntStream.range(0, inventory.getSlots())
                        .mapToObj(inventory::getStackInSlot)
                        .filter(it -> !it.isEmpty())
                        .map(it -> Pair.of(ItemStackHelper.getCommandString(it), TagManagerItem.INSTANCE.getAllTagsFor(it.getItem())))
                        .map(it -> it.getFirst() + '\n' + stringify(it.getSecond()))
                        .collect(Collectors.joining("\n", "Inventory item tags\n", ""));
                
                CraftTweakerAPI.logDump(inventoryContents);
                CommandUtilities.send(CommandUtilities.color("Inventory tag list generated! Check the crafttweaker.log file!", TextFormatting.GREEN), player);
            });
            
            return 0;
        }));
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
