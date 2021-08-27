package com.blamejared.crafttweaker.impl.events;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.util.AttributeUtil;
import com.blamejared.crafttweaker.impl.data.MapData;
import com.blamejared.crafttweaker.impl.entity.MCEntityType;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import com.blamejared.crafttweaker.impl_native.entity.ExpandEntity;
import com.blamejared.crafttweaker.impl_native.entity.ExpandPlayerEntity;
import com.blamejared.crafttweaker.impl_native.world.ExpandWorld;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class CTEventHandler {
    
    public static final Map<IIngredient, Integer> BURN_TIMES = new HashMap<>();
    public static final Set<PlayerEntity> BLOCK_INFO_PLAYERS = new HashSet<>();
    public static final Set<PlayerEntity> ENTITY_INFO_PLAYERS = new HashSet<>();
    
    public static final Map<IIngredient, List<Consumer<ItemAttributeModifierEvent>>> ATTRIBUTE_MODIFIERS = new HashMap<>();
    
    @SubscribeEvent
    public void attribute(ItemAttributeModifierEvent e) {
        
        ItemStack stack = e.getItemStack();
        if(stack.hasTag()) {
            CompoundNBT crtData = stack.getChildTag(IItemStack.CRAFTTWEAKER_DATA_KEY);
            if(crtData != null) {
                AttributeUtil.getAttributeModifiers(stack, e.getSlotType())
                        .forEach((attribute, modifiers) -> {
                            modifiers.forEach(modifier -> {
                                // Multimaps are possibly one of the dumbest things I've come across.
                                // So we have to remove the value before we add the value.
                                // Override existing attributes
                                if(e.getModifiers().containsEntry(attribute, modifier)) {
                                    e.removeModifier(attribute, modifier);
                                }
                                e.addModifier(attribute, modifier);
                            });
                        });
                
            }
        }
        
        ATTRIBUTE_MODIFIERS.keySet()
                .stream()
                .filter(ingredient -> ingredient.matches(new MCItemStackMutable(stack)))
                .map(ATTRIBUTE_MODIFIERS::get)
                .flatMap(Collection::stream)
                .forEach(consumer -> consumer.accept(e));
    }
    
    @SubscribeEvent
    public void burnTimeTweaker(FurnaceFuelBurnTimeEvent e) {
        
        BURN_TIMES.keySet()
                .stream()
                .filter(ingredient -> ingredient.matches(new MCItemStackMutable(e.getItemStack())))
                .findFirst()
                .ifPresent(ingredient -> e.setBurnTime(BURN_TIMES.get(ingredient)));
    }
    
    public enum ListenBlockInfo implements Consumer<PlayerInteractEvent.RightClickBlock> {
        INSTANCE;
        
        @Override
        public void accept(PlayerInteractEvent.RightClickBlock event) {
            
            PlayerEntity player = event.getPlayer();
            World world = event.getWorld();
            if(BLOCK_INFO_PLAYERS.contains(player) && !world.isRemote && event.getHand() == Hand.MAIN_HAND) {
                BlockState state = world.getBlockState(event.getPos());
                sendAndLog(player, MCTextComponent.createStringTextComponent("Block Name: " + state.getBlock()
                        .getRegistryName()
                        .toString()));
                if(!state.getProperties().isEmpty()) {
                    
                    sendAndLog(player, MCTextComponent.createStringTextComponent("Properties: "));
                    state.getProperties().forEach(property -> {
                        sendAndLog(player, MCTextComponent.createStringTextComponent(property.getName() + ": " + state
                                .get(property)));
                    });
                }
                MapData tileData = (MapData) ExpandWorld.getTileData(world, event.getPos());
                if(!tileData.isEmpty()) {
                    sendAndLog(player, MCTextComponent.createStringTextComponent("Tile Data: ")
                            .appendSibling(new MCTextComponent(tileData
                                    .asFormattedComponent("  ", 0))));
                }
                event.setCanceled(true);
            }
        }
    }
    
    public enum ListenEntityInfo implements Consumer<PlayerInteractEvent.EntityInteract> {
        INSTANCE;
        
        @Override
        public void accept(PlayerInteractEvent.EntityInteract event) {
            
            PlayerEntity player = event.getPlayer();
            World world = event.getWorld();
            if(ENTITY_INFO_PLAYERS.contains(player) && !world.isRemote && event.getHand() == Hand.MAIN_HAND) {
                Entity target = event.getTarget();
                
                BlockState state = world.getBlockState(event.getPos());
                sendAndLog(player, MCTextComponent.createStringTextComponent("Entity Name: " + ExpandEntity
                        .getName(target)));
                sendAndLog(player, MCTextComponent.createStringTextComponent("EntityType Bracket: " + new MCEntityType(target
                        .getType()).getCommandString()));
                
                MapData data = ExpandEntity.getData(target);
                if(!data.isEmpty()) {
                    sendAndLog(player, MCTextComponent.createStringTextComponent("Entity Data: ")
                            .appendSibling(new MCTextComponent(data
                                    .asFormattedComponent("  ", 0))));
                }
                event.setCanceled(true);
            }
        }
    }
    
    private static void sendAndLog(PlayerEntity player, MCTextComponent component) {
        
        ExpandPlayerEntity.sendMessage(player, component);
        CraftTweakerAPI.logDump(component.asString());
    }
    
}
