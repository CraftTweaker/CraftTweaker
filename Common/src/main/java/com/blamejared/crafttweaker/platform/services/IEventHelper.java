package com.blamejared.crafttweaker.platform.services;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.command.CommandUtilities;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.base.visitor.DataToTextComponentVisitor;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.attribute.ItemAttributeModifierBase;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.recipe.replacement.event.IGatherReplacementExclusionEvent;
import com.blamejared.crafttweaker.api.util.AttributeUtil;
import com.blamejared.crafttweaker.natives.block.ExpandBlock;
import com.blamejared.crafttweaker.natives.block.ExpandBlockState;
import com.blamejared.crafttweaker.natives.entity.ExpandEntity;
import com.blamejared.crafttweaker.natives.entity.ExpandEntityType;
import com.blamejared.crafttweaker.natives.entity.type.player.ExpandPlayer;
import com.blamejared.crafttweaker.natives.world.ExpandLevel;
import com.blamejared.crafttweaker.platform.Services;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.*;
import java.util.function.Consumer;

public interface IEventHelper {
    
    Map<RecipeType<?>, List<Pair<IIngredient, Integer>>> BURN_TIMES = new HashMap<>();
    
    Set<Player> BLOCK_INFO_PLAYERS = new HashSet<>();
    Set<Player> ENTITY_INFO_PLAYERS = new HashSet<>();
    
    Map<IIngredient, List<Consumer<ItemAttributeModifierBase>>> ATTRIBUTE_MODIFIERS = new HashMap<>();
    
    IGatherReplacementExclusionEvent fireGatherReplacementExclusionEvent(final IRecipeManager<?> manager);
    
    default void setBurnTime(IIngredient ingredient, int burnTime, RecipeType<?> type) {
        
        getBurnTimes().computeIfAbsent(type, recipeType -> new ArrayList<>()).add(Pair.of(ingredient, burnTime));
    }
    
    int getBurnTime(IItemStack stack);
    
    default Map<RecipeType<?>, List<Pair<IIngredient, Integer>>> getBurnTimes() {
        
        return BURN_TIMES;
    }
    
    default Map<IIngredient, List<Consumer<ItemAttributeModifierBase>>> getAttributeModifiers() {
        
        return ATTRIBUTE_MODIFIERS;
    }
    
    default void applyAttributeModifiers(ItemAttributeModifierBase modifierBase) {
        
        ItemStack stack = modifierBase.getItemStack();
        if(stack.hasTag()) {
            CompoundTag crtData = stack.getTagElement(IItemStack.CRAFTTWEAKER_DATA_KEY);
            if(crtData != null) {
                AttributeUtil.getAttributeModifiers(stack, modifierBase.getSlotType())
                        .forEach((attribute, modifiers) -> modifiers.forEach(modifier -> {
                            // Multimaps are possibly one of the dumbest things I've come across.
                            // So we have to remove the value before we add the value.
                            // Override existing attributes
                            if(modifierBase.getModifiers().containsEntry(attribute, modifier)) {
                                modifierBase.removeModifier(attribute, modifier);
                            }
                            modifierBase.addModifier(attribute, modifier);
                        }));
                
            }
        }
        
        Services.EVENT.getAttributeModifiers().keySet()
                .stream()
                .filter(ingredient -> ingredient.matches(Services.PLATFORM.createMCItemStackMutable(stack)))
                .map(Services.EVENT.getAttributeModifiers()::get)
                .flatMap(Collection::stream)
                .forEach(consumer -> consumer.accept(modifierBase));
    }
    
    
    default boolean onBlockInteract(Player player, InteractionHand hand, BlockHitResult hitResult) {
        
        Level world = player.level;
        BlockPos pos = hitResult.getBlockPos();
        
        if(BLOCK_INFO_PLAYERS.contains(player)) {
            if(!world.isClientSide() && hand == InteractionHand.MAIN_HAND) {
                BlockState state = world.getBlockState(pos);
                sendAndLog(player, new TranslatableComponent("crafttweaker.command.info.block.name", Services.REGISTRY.getRegistryKey(state.getBlock())));
                String blockCS = ExpandBlock.getCommandString(state.getBlock());
                String blockStateCS = ExpandBlockState.getCommandString(state);
                CommandUtilities.sendCopying(new TranslatableComponent("crafttweaker.command.misc.block")
                        .append(": ")
                        .append(new TextComponent(blockCS).withStyle(ChatFormatting.GREEN)), blockCS, player);
                CommandUtilities.sendCopying(new TranslatableComponent("crafttweaker.command.misc.blockstate")
                        .append(": ")
                        .append(new TextComponent(blockStateCS).withStyle(ChatFormatting.GREEN)), blockStateCS, player);
                if(!state.getProperties().isEmpty()) {
                    
                    sendAndLog(player, new TranslatableComponent("crafttweaker.command.info.block.properties"));
                    state.getProperties()
                            .forEach(property -> sendAndLog(player, new TextComponent("    " + property.getName()).withStyle(ChatFormatting.YELLOW)
                                    .append(new TextComponent(": ").withStyle(ChatFormatting.WHITE))
                                    .append(new TextComponent(state.getValue(property)
                                            .toString()).withStyle(ChatFormatting.AQUA))));
                }
                MapData tileData = ExpandLevel.getBlockEntityData(world, pos);
                if(!tileData.isEmpty()) {
                    sendAndLog(player, new TranslatableComponent("crafttweaker.command.info.block.entity.data", tileData.accept(new DataToTextComponentVisitor("    ", 0))));
                }
            }
            return true;
        }
        return false;
    }
    
    default boolean onEntityInteract(Player player, InteractionHand hand, Entity target) {
        
        Level world = player.level;
        
        if(ENTITY_INFO_PLAYERS.contains(player)) {
            if(!world.isClientSide() && hand == InteractionHand.MAIN_HAND) {
                sendAndLog(player, new TranslatableComponent("crafttweaker.command.info.entity.name", ExpandEntity.getName(target)));
                sendAndLog(player, new TranslatableComponent("crafttweaker.command.info.entity.type.bracket", ExpandEntityType.getCommandString(target.getType())));
                
                MapData data = ExpandEntity.getData(target);
                if(!data.isEmpty()) {
                    sendAndLog(player, new TranslatableComponent("crafttweaker.command.info.entity.data", data.accept(new DataToTextComponentVisitor("    ", 0))));
                }
            }
            return true;
        }
        return false;
    }
    
    private static void sendAndLog(Player player, MutableComponent component) {
        
        ExpandPlayer.sendMessage(player, component);
        CraftTweakerAPI.LOGGER.info(component.getString());
    }
    
}
