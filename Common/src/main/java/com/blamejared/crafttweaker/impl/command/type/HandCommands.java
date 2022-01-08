package com.blamejared.crafttweaker.impl.command.type;

import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.base.visitor.DataToTextComponentVisitor;
import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.api.tag.manager.TagManagerBlock;
import com.blamejared.crafttweaker.api.tag.manager.TagManagerItem;
import com.blamejared.crafttweaker.impl.command.CTCommands;
import com.blamejared.crafttweaker.impl.command.CommandUtilities;
import com.blamejared.crafttweaker.impl.command.boilerplate.CommandImpl;
import com.blamejared.crafttweaker.natives.block.ExpandBlock;
import com.blamejared.crafttweaker.natives.block.ExpandBlockState;
import com.blamejared.crafttweaker.natives.entity.attribute.ExpandAttribute;
import com.blamejared.crafttweaker.natives.entity.equipment.ExpandEquipmentSlot;
import com.blamejared.crafttweaker.platform.Services;
import com.mojang.brigadier.Command;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagCollection;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluids;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public final class HandCommands {
    
    private HandCommands() {}
    
    public static void registerCommands() {
        
        CTCommands.registerCommand(new CommandImpl("hand", new TranslatableComponent("crafttweaker.command.description.hand"), builder -> builder.executes(context -> {
            ServerPlayer player = context.getSource().getPlayerOrException();
            ItemStack stack = player.getMainHandItem();
            final Item item = stack.getItem();
            
            sendBasicItemInformation(player, stack);
            
            if(item instanceof BlockItem) {
                sendBlockInformation(player, (BlockItem) stack.getItem());
            }
            
            if(item instanceof BucketItem && Services.PLATFORM.getBucketContent(((BucketItem) item)) != Fluids.EMPTY) {
                sendBucketInformation(player, (BucketItem) stack.getItem());
            }
            // TODO forge fluid handlers
            //                stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY)
            //                        .ifPresent(iFluidHandlerItem -> sendFluidInformation(player, iFluidHandlerItem));
            
            sendTagsInformation(player, item);
            return Command.SINGLE_SUCCESS;
        })));
        
        CTCommands.registerCommand("hand", new CommandImpl("registry_name", new TranslatableComponent("crafttweaker.command.description.hand.registryname"), builder -> builder.executes(context -> {
            ItemStack mainHandItem = context.getSource()
                    .getPlayerOrException()
                    .getMainHandItem();
            sendCopyingHand(context.getSource()
                    .getPlayerOrException(), new TranslatableComponent("crafttweaker.command.misc.item"), Services.REGISTRY.getRegistryKey(mainHandItem.getItem())
                    .toString());
            return Command.SINGLE_SUCCESS;
        })));
        
        CTCommands.registerCommand("hand", new CommandImpl("data", new TranslatableComponent("crafttweaker.command.description.hand.data"), builder -> builder.executes(context -> {
            ServerPlayer player = context.getSource()
                    .getPlayerOrException();
            ItemStack stack = player.getMainHandItem();
            if(!stack.hasTag()) {
                CommandUtilities.send(new TranslatableComponent("crafttweaker.command.hand.no.data"), player);
                return 0;
            }
            
            sendCopyingHand(player, new TranslatableComponent("crafttweaker.command.misc.data"), new MapData(stack.getTag()).accept(new DataToTextComponentVisitor(" ", 0))
                    .getString());
            return Command.SINGLE_SUCCESS;
        })));
        
        CTCommands.registerCommand("hand", new CommandImpl("tags", new TranslatableComponent("crafttweaker.command.description.hand.tags"), builder -> builder.executes(context -> {
            ServerPlayer player = context.getSource()
                    .getPlayerOrException();
            ItemStack stack = player.getMainHandItem();
            final Collection<String> tags = sendTagsInformation(player, stack.getItem());
            
            if(tags.isEmpty()) {
                CommandUtilities.send(new TranslatableComponent("crafttweaker.command.hand.no.tags"), player);
                return Command.SINGLE_SUCCESS;
            }
            
            tags.stream()
                    .findFirst()
                    .ifPresent(it -> CommandUtilities.copy(player, it));
            return Command.SINGLE_SUCCESS;
        })));
        
        
        CTCommands.registerCommand("hand", new CommandImpl("vanilla", new TranslatableComponent("crafttweaker.command.description.hand.vanilla"), builder -> builder.executes(context -> {
            ServerPlayer player = context.getSource()
                    .getPlayerOrException();
            ItemStack stack = player.getMainHandItem();
            
            final Item item = stack.getItem();
            
            sendBasicVanillaItemInformation(player, stack);
            
            if(stack.hasTag()) {
                sendVanillaDataInformation(player, Objects.requireNonNull(stack.getTag()));
            }
            
            if(item instanceof BucketItem && Services.PLATFORM.getBucketContent(((BucketItem) item)) != Fluids.EMPTY) {
                sendVanillaBucketInformation(player, (BucketItem) stack.getItem());
            }
            
            sendVanillaTagsInformation(player, item);
            return Command.SINGLE_SUCCESS;
        })));
        
        CTCommands.registerCommand("hand", new CommandImpl("attributes", new TranslatableComponent("crafttweaker.command.description.hand.attributes"), builder -> builder.executes(context -> {
            ServerPlayer player = context.getSource()
                    .getPlayerOrException();
            ItemStack stack = player.getMainHandItem();
            
            
            for(EquipmentSlot slot : EquipmentSlot.values()) {
                Map<Attribute, Collection<AttributeModifier>> modifiers = stack.getAttributeModifiers(slot)
                        .asMap();
                if(modifiers.isEmpty()) {
                    continue;
                }
                String equipmentCS = ExpandEquipmentSlot.getCommandString(slot);
                CommandUtilities.sendCopying(new TranslatableComponent("crafttweaker.command.hand.header.attributes").append(": ")
                        .append(new TextComponent(equipmentCS).withStyle(ChatFormatting.GREEN))
                        .withStyle(ChatFormatting.DARK_AQUA), equipmentCS, player);
                
                modifiers.forEach((attribute, attributeModifiers) -> {
                    String attributeCS = ExpandAttribute.getCommandString(attribute);
                    CommandUtilities.sendCopying(new TextComponent("- ").withStyle(ChatFormatting.YELLOW)
                            .append(new TextComponent(attributeCS).withStyle(ChatFormatting.GREEN)), attributeCS, player);
                    
                    attributeModifiers.forEach(attributeModifier -> {
                        
                        sendAttributePropertyInformation(player, "Name", attributeModifier.getName());
                        sendAttributePropertyInformation(player, "ID", attributeModifier.getId()
                                .toString());
                        sendAttributePropertyInformation(player, "Operation", attributeModifier.getOperation()
                                .name());
                        sendAttributePropertyInformation(player, "Amount", attributeModifier.getAmount() + "");
                        sendAttributePropertyInformation(player, "IData", new MapData(attributeModifier.save()).asString());
                    });
                });
            }
            sendCopyingHand(player, new TranslatableComponent("crafttweaker.command.misc.item"), Services.REGISTRY.getRegistryKey(stack.getItem())
                    .toString());
            return Command.SINGLE_SUCCESS;
        })));
    }
    
    
    // <editor-fold desc="CT Functions">
    private static void sendBasicItemInformation(final Player player, final ItemStack target) {
        
        final String output = Services.PLATFORM.createMCItemStackMutable(target)
                .getCommandString();
        sendCopyingHand(player, new TranslatableComponent("crafttweaker.command.misc.item"), output);
    }
    
    private static void sendBlockInformation(final Player player, final BlockItem target) {
        
        sendBlockInformation(player, target.getBlock());
    }
    
    private static void sendBlockInformation(final Player player, final Block target) {
        
        sendHand(player, new TranslatableComponent("crafttweaker.command.misc.block"), ExpandBlock.getCommandString(target));
        sendHand(player, new TranslatableComponent("crafttweaker.command.misc.blockstate"), ExpandBlockState.getCommandString(target.defaultBlockState()));
    }
    
    private static void sendBucketInformation(final Player player, final BucketItem target) {
        
        if(Services.PLATFORM.getBucketContent(target) == Fluids.EMPTY) {
            return;
        }
        sendHand(player, new TranslatableComponent("crafttweaker.command.misc.fluidblockstate"), ExpandBlockState.getCommandString(Services.PLATFORM.getBucketContent(target)
                .defaultFluidState()
                .createLegacyBlock()));
    }
    
    //    private static void sendFluidInformation(final Player player, final IFluidHandlerItem item) {
    //
    //        final int tanks = item.getTanks();
    //        if(tanks <= 0) {
    //            return;
    //        }
    //
    //        if(item.getTanks() == 1) {
    //            sendSingleFluidInformation(player, item.getFluidInTank(0), -1);
    //            return;
    //        }
    //
    //        sendMultipleFluidInformation(player, item, tanks);
    //    }
    
    //    private static void sendMultipleFluidInformation(final Player player, final IFluidHandlerItem item, final int tanks) {
    //
    //        IntStream.range(0, tanks)
    //                .mapToObj(it -> new AbstractInt2ObjectMap.BasicEntry<>(it, item.getFluidInTank(it)))
    //                .filter(it -> !it.getValue().isEmpty())
    //                .forEach(it -> sendSingleFluidInformation(player, it.getValue(), it.getIntKey()));
    //    }
    
    //    private static void sendSingleFluidInformation(final Player player, final FluidStack stack, final int id) {
    //
    //        sendHand(player, id < 0 ? "Fluid" : "Fluid #" + id, new MCFluidStackMutable(stack).getCommandString());
    //    }
    
    private static Collection<String> sendTagsInformation(final Player player, final Item item) {
        
        final List<String> tags = new ArrayList<>(sendItemTagsInformation(player, item));
        if(item instanceof BlockItem) {
            tags.addAll(sendBlockTagsInformation(player, (BlockItem) item));
        }
        return tags;
    }
    
    private static Collection<String> sendItemTagsInformation(final Player player, final Item item) {
        
        return sendTagsInformation(player, new TranslatableComponent("crafttweaker.command.hand.header.tags.item"), TagManagerItem.INSTANCE, ItemTags.getAllTags(), item);
    }
    
    private static Collection<String> sendBlockTagsInformation(final Player player, final BlockItem item) {
        
        return sendTagsInformation(player, new TranslatableComponent("crafttweaker.command.hand.header.tags.block"), TagManagerBlock.INSTANCE, BlockTags.getAllTags(), item
                .getBlock());
    }
    
    private static <T> Collection<String> sendTagsInformation(final Player player, final MutableComponent header, final ITagManager<T> manager,
                                                              final TagCollection<T> tagCollection, final T target) {
        
        final Collection<ResourceLocation> tags = tagCollection.getMatchingTags(target);
        if(tags.isEmpty()) {
            return Collections.emptyList();
        }
        
        CommandUtilities.send(header.withStyle(ChatFormatting.DARK_AQUA), player);
        return tags.stream()
                .map(id -> new MCTag<>(id, manager))
                .map(MCTag::getCommandString)
                .peek(it -> sendTagHand(player, it))
                .collect(Collectors.toList());
    }
    // </editor-fold>
    
    // <editor-fold desc="Vanilla Functions">
    private static void sendBasicVanillaItemInformation(final Player player, final ItemStack target) {
        
        final String output = Objects.requireNonNull(Services.REGISTRY.getRegistryKey(target.getItem()))
                .toString();
        sendCopyingHand(player, new TranslatableComponent("crafttweaker.command.misc.item"), output);
    }
    
    private static void sendVanillaDataInformation(final Player player, final Tag nbt) {
        
        sendHand(player, new TranslatableComponent("crafttweaker.command.misc.data"), nbt.toString());
    }
    
    private static void sendVanillaBucketInformation(final Player player, final BucketItem target) {
        
        if(Services.PLATFORM.getBucketContent(target) == Fluids.EMPTY) {
            return;
        }
        sendHand(player, new TranslatableComponent("crafttweaker.command.misc.fluidblockstate"), Services.REGISTRY.getRegistryKey(Services.PLATFORM.getBucketContent(target))
                .toString());
    }
    
    private static void sendVanillaTagsInformation(final Player player, final Item item) {
        
        sendVanillaItemTagsInformation(player, item);
        if(item instanceof BlockItem) {
            sendVanillaBlockTagsInformation(player, (BlockItem) item);
        }
    }
    
    private static void sendVanillaItemTagsInformation(final Player player, final Item item) {
        
        sendVanillaTagsInformation(player, new TranslatableComponent("crafttweaker.command.hand.header.tags.item"), ItemTags.getAllTags(), item);
    }
    
    private static void sendVanillaBlockTagsInformation(final Player player, final BlockItem item) {
        
        sendVanillaTagsInformation(player, new TranslatableComponent("crafttweaker.command.hand.header.tags.block"), BlockTags.getAllTags(), item.getBlock());
    }
    
    private static <T> void sendVanillaTagsInformation(final Player player, final MutableComponent header, final TagCollection<T> tagCollection, final T target) {
        
        final Collection<ResourceLocation> tags = tagCollection.getMatchingTags(target);
        if(tags.isEmpty()) {
            return;
        }
        
        CommandUtilities.send(header.withStyle(ChatFormatting.DARK_AQUA), player);
        tags.stream()
                .map(id -> "#" + id)
                .forEach(it -> sendTagHand(player, it));
    }
    
    private static void sendAttributePropertyInformation(final Player player, final String propertyName, final String value) {
        
        CommandUtilities.sendCopying(new TextComponent("    - ").append(propertyName)
                .append(": ").withStyle(ChatFormatting.YELLOW)
                .append(new TextComponent(value).withStyle(ChatFormatting.AQUA)), value, player);
    }
    // </editor-fold>
    
    private static void sendHand(final Player receiver, final MutableComponent messagePrefix, final String target) {
        
        sendHand(receiver, messagePrefix, target, CommandUtilities::sendCopying);
    }
    
    @SuppressWarnings("SameParameterValue")
    private static void sendCopyingHand(final Player receiver, final MutableComponent messagePrefix, final String target) {
        
        sendHand(receiver, messagePrefix, target, CommandUtilities::sendCopyingAndCopy);
    }
    
    private static void sendHand(final Player receiver, final MutableComponent messagePrefix, final String target, final TriConsumer<MutableComponent, String, Player> consumer) {
        
        consumer.accept(
                messagePrefix.append(": ").append(new TextComponent(target).withStyle(ChatFormatting.GREEN)),
                target,
                receiver
        );
    }
    
    private static void sendTagHand(final Player receiver, final String tag) {
        
        CommandUtilities.sendCopying(
                new TranslatableComponent("    ").append(new TextComponent("- ").withStyle(ChatFormatting.YELLOW))
                        .append(" ")
                        .append(new TextComponent(tag).withStyle(ChatFormatting.AQUA)),
                tag,
                receiver
        );
    }
    
}
