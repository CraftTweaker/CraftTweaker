package com.blamejared.crafttweaker.impl.commands.crafttweaker;

import com.blamejared.crafttweaker.api.text.FormattedTextComponent;
import com.blamejared.crafttweaker.impl.commands.CTCommands;
import com.blamejared.crafttweaker.impl.commands.CommandCallerPlayer;
import com.blamejared.crafttweaker.impl.commands.CommandUtilities;
import com.blamejared.crafttweaker.impl.fluid.MCFluidStackMutable;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.blamejared.crafttweaker.impl.tag.manager.TagManager;
import com.blamejared.crafttweaker.impl.tag.manager.TagManagerBlock;
import com.blamejared.crafttweaker.impl.tag.manager.TagManagerItem;
import com.blamejared.crafttweaker.impl_native.blocks.ExpandBlock;
import com.blamejared.crafttweaker.impl_native.blocks.ExpandBlockState;
import com.blamejared.crafttweaker.impl_native.entity.attribute.ExpandAttribute;
import com.blamejared.crafttweaker.impl_native.util.ExpandEquipmentSlotType;
import it.unimi.dsi.fastutil.ints.AbstractInt2ObjectMap;
import net.minecraft.block.Block;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBT;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITagCollection;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class HandCommands {
    
    private HandCommands() {}
    
    public static void registerHandCommands() {
        
        CTCommands.registerCommand(CTCommands.playerCommand("hand", "Outputs the name and tags (if any) of the item in your hand", (player, stack) -> {
            final Item item = stack.getItem();
            
            sendBasicItemInformation(player, stack);
            
            if(item instanceof BlockItem) {
                sendBlockInformation(player, (BlockItem) stack.getItem());
            }
            
            if(item instanceof BucketItem && ((BucketItem) item).getFluid() != Fluids.EMPTY) {
                sendBucketInformation(player, (BucketItem) stack.getItem());
            }
            
            stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY)
                    .ifPresent(iFluidHandlerItem -> sendFluidInformation(player, iFluidHandlerItem));
            
            sendTagsInformation(player, item);
            
            return 0;
        }));
        
        subCommand("registryName", "Outputs the registry name of the item in your hand", (player, stack) -> {
            //It cannot be null because we can't get unregistered items here
            sendCopyingHand(player, "Item", Objects.requireNonNull(stack.getItem().getRegistryName()).toString());
            return 0;
        });
        
        subCommand("tags", "Outputs the tags of the item in your hand", (player, stack) -> {
            final Collection<String> tags = sendTagsInformation(player, stack.getItem());
            
            if(tags.isEmpty()) {
                CommandUtilities.send("Item has no tags", player);
                return 0;
            }
            
            tags.stream().findFirst().ifPresent(it -> CommandUtilities.copy(player, it));
            return 0;
        });
        
        subCommand("vanilla", "Outputs the name and tags (if any) of the item in your hand in vanilla format", (player, stack) -> {
            final Item item = stack.getItem();
            
            sendBasicVanillaItemInformation(player, stack);
            
            if(stack.hasTag()) {
                sendVanillaNbtInformation(player, Objects.requireNonNull(stack.getTag()));
            }
            
            if(item instanceof BucketItem && ((BucketItem) item).getFluid() != Fluids.EMPTY) {
                sendVanillaBucketInformation(player, (BucketItem) stack.getItem());
            }
            
            sendVanillaTagsInformation(player, item);
            
            return 0;
        });
        
        subCommand("attributes", "Outputs the AttributeModifiers of the item in your hand", (player, stack) -> {
            
            for(EquipmentSlotType slot : EquipmentSlotType.values()) {
                Map<Attribute, Collection<AttributeModifier>> modifiers = stack.getAttributeModifiers(slot)
                        .asMap();
                if(modifiers.isEmpty()) {
                    continue;
                }
                String equipmentCS = ExpandEquipmentSlotType.getCommandString(slot);
                CommandUtilities.sendCopying(new FormattedTextComponent(TextFormatting.DARK_AQUA + "Attributes for: " + TextFormatting.GREEN + equipmentCS), equipmentCS, player);
                
                modifiers.forEach((attribute, attributeModifiers) -> {
                    String attributeCS = ExpandAttribute.getCommandString(attribute);
                    CommandUtilities.sendCopying(new FormattedTextComponent(TextFormatting.YELLOW + "- " + TextFormatting.GREEN + attributeCS), attributeCS, player);
                    
                    attributeModifiers.forEach(attributeModifier -> {
    
                        sendAttributePropertyInformation(player, "Name", attributeModifier.getName());
                        sendAttributePropertyInformation(player, "ID", attributeModifier.getID().toString());
                        sendAttributePropertyInformation(player, "Operation", attributeModifier.getOperation().name());
                        sendAttributePropertyInformation(player, "Amount", attributeModifier.getAmount() +"");
    
                    });
                    
                });
            }
            
            sendCopyingHand(player, "Item", Objects.requireNonNull(stack.getItem().getRegistryName()).toString());
            return 0;
        });
    }
    
    private static void subCommand(final String name, final String desc, final CommandCallerPlayer caller) {
        
        CTCommands.registerCommand("hand", CTCommands.playerCommand(name, desc, caller));
    }
    
    // <editor-fold desc="CT Functions">
    private static void sendBasicItemInformation(final PlayerEntity player, final ItemStack target) {
        
        final String output = new MCItemStackMutable(target).getCommandString();
        sendCopyingHand(player, "Item", output);
    }
    
    private static void sendBlockInformation(final PlayerEntity player, final BlockItem target) {
        
        sendBlockInformation(player, target.getBlock());
    }
    
    private static void sendBlockInformation(final PlayerEntity player, final Block target) {
        
        sendHand(player, "Block", ExpandBlock.getCommandString(target.getBlock()));
        sendHand(player, "BlockState", ExpandBlockState.getCommandString(target.getDefaultState()));
    }
    
    private static void sendBucketInformation(final PlayerEntity player, final BucketItem target) {
        
        if(target.getFluid() == Fluids.EMPTY) {
            return;
        }
        sendHand(player, "Fluid BlockState", ExpandBlockState.getCommandString(target.getFluid()
                .getDefaultState()
                .getBlockState()));
    }
    
    private static void sendFluidInformation(final PlayerEntity player, final IFluidHandlerItem item) {
        
        final int tanks = item.getTanks();
        if(tanks <= 0) {
            return;
        }
        
        if(item.getTanks() == 1) {
            sendSingleFluidInformation(player, item.getFluidInTank(0), -1);
            return;
        }
        
        sendMultipleFluidInformation(player, item, tanks);
    }
    
    private static void sendMultipleFluidInformation(final PlayerEntity player, final IFluidHandlerItem item, final int tanks) {
        
        IntStream.range(0, tanks)
                .mapToObj(it -> new AbstractInt2ObjectMap.BasicEntry<>(it, item.getFluidInTank(it)))
                .filter(it -> !it.getValue().isEmpty())
                .forEach(it -> sendSingleFluidInformation(player, it.getValue(), it.getIntKey()));
    }
    
    private static void sendSingleFluidInformation(final PlayerEntity player, final FluidStack stack, final int id) {
        
        sendHand(player, id < 0 ? "Fluid" : "Fluid #" + id, new MCFluidStackMutable(stack).getCommandString());
    }
    
    private static Collection<String> sendTagsInformation(final PlayerEntity player, final Item item) {
        
        final List<String> tags = new ArrayList<>(sendItemTagsInformation(player, item));
        if(item instanceof BlockItem) {
            tags.addAll(sendBlockTagsInformation(player, (BlockItem) item));
        }
        return tags;
    }
    
    private static Collection<String> sendItemTagsInformation(final PlayerEntity player, final Item item) {
        
        return sendTagsInformation(player, "Item Tag Entries", TagManagerItem.INSTANCE, ItemTags.getCollection(), item);
    }
    
    private static Collection<String> sendBlockTagsInformation(final PlayerEntity player, final BlockItem item) {
        
        return sendTagsInformation(player, "Block Tag Entries", TagManagerBlock.INSTANCE, BlockTags.getCollection(), item
                .getBlock());
    }
    
    private static <T> Collection<String> sendTagsInformation(final PlayerEntity player, final String header, final TagManager<T> manager,
                                                              final ITagCollection<T> tagCollection, final T target) {
        
        final Collection<ResourceLocation> tags = tagCollection.getOwningTags(target);
        if(tags.isEmpty()) {
            return Collections.emptyList();
        }
        
        CommandUtilities.send(new FormattedTextComponent(CommandUtilities.color(header, TextFormatting.DARK_AQUA)), player);
        return tags.stream()
                .map(id -> new MCTag<>(id, manager))
                .map(MCTag::getCommandString)
                .peek(it -> sendTagHand(player, it))
                .collect(Collectors.toList());
    }
    // </editor-fold>
    
    // <editor-fold desc="Vanilla Functions">
    private static void sendBasicVanillaItemInformation(final PlayerEntity player, final ItemStack target) {
        
        final String output = Objects.requireNonNull(target.getItem().getRegistryName()).toString();
        sendCopyingHand(player, "Item", output);
    }
    
    private static void sendVanillaNbtInformation(final PlayerEntity player, final INBT nbt) {
        
        sendHand(player, "NBT", nbt.getString());
    }
    
    private static void sendVanillaBucketInformation(final PlayerEntity player, final BucketItem target) {
        
        if(target.getFluid() == Fluids.EMPTY) {
            return;
        }
        sendHand(player, "Fluid BlockState", Objects.requireNonNull(target.getFluid().getRegistryName()).toString());
    }
    
    private static void sendVanillaTagsInformation(final PlayerEntity player, final Item item) {
        
        sendVanillaItemTagsInformation(player, item);
        if(item instanceof BlockItem) {
            sendVanillaBlockTagsInformation(player, (BlockItem) item);
        }
    }
    
    private static void sendVanillaItemTagsInformation(final PlayerEntity player, final Item item) {
        
        sendVanillaTagsInformation(player, "Item Tag Entries", ItemTags.getCollection(), item);
    }
    
    private static void sendVanillaBlockTagsInformation(final PlayerEntity player, final BlockItem item) {
        
        sendVanillaTagsInformation(player, "Block Tag Entries", BlockTags.getCollection(), item.getBlock());
    }
    
    private static <T> void sendVanillaTagsInformation(final PlayerEntity player, final String header, final ITagCollection<T> tagCollection, final T target) {
        
        final Collection<ResourceLocation> tags = tagCollection.getOwningTags(target);
        if(tags.isEmpty()) {
            return;
        }
        
        CommandUtilities.send(new FormattedTextComponent(CommandUtilities.color(header, TextFormatting.DARK_AQUA)), player);
        tags.stream()
                .map(id -> "#" + id)
                .forEach(it -> sendTagHand(player, it));
    }
    
    private static void sendAttributePropertyInformation(final PlayerEntity player, final String propertyName, final String value) {
        
        CommandUtilities.sendCopying(new FormattedTextComponent(TextFormatting.YELLOW + "\t- %s: " + TextFormatting.AQUA + value, propertyName), value, player);
    }
    // </editor-fold>
    
    private static void sendHand(final PlayerEntity receiver, final String messagePrefix, final String target) {
        
        sendHand(receiver, messagePrefix, target, CommandUtilities::sendCopying);
    }
    
    @SuppressWarnings("SameParameterValue")
    private static void sendCopyingHand(final PlayerEntity receiver, final String messagePrefix, final String target) {
        
        sendHand(receiver, messagePrefix, target, CommandUtilities::sendCopyingAndCopy);
    }
    
    private static void sendHand(final PlayerEntity receiver, final String messagePrefix, final String target, final TriConsumer<TextComponent, String, PlayerEntity> consumer) {
        
        consumer.accept(
                // TODO("Localization")
                new FormattedTextComponent(messagePrefix + ": %s", CommandUtilities.color(target, TextFormatting.GREEN)),
                target,
                receiver
        );
    }
    
    private static void sendTagHand(final PlayerEntity receiver, final String tag) {
        
        CommandUtilities.sendCopying(
                new FormattedTextComponent("\t%s %s", CommandUtilities.color("- ", TextFormatting.YELLOW), CommandUtilities
                        .color(tag, TextFormatting.AQUA)),
                tag,
                receiver
        );
    }
    
}
