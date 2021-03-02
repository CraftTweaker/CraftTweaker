package com.blamejared.crafttweaker.impl.events;

import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.data.MapData;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import com.blamejared.crafttweaker.impl_native.entity.ExpandPlayerEntity;
import com.blamejared.crafttweaker.impl_native.world.ExpandWorld;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class CTEventHandler {
    
    public static final Map<IItemStack, Integer> BURN_TIMES = new HashMap<>();
    public static final Set<PlayerEntity> BLOCK_INFO_PLAYERS = new HashSet<>();
    
    @SubscribeEvent
    public void burnTimeTweaker(FurnaceFuelBurnTimeEvent e) {
        
        BURN_TIMES.keySet()
                .stream()
                .filter(iItemStack -> iItemStack.matches(new MCItemStackMutable(e.getItemStack())))
                .findFirst()
                .ifPresent(iItemStack -> e.setBurnTime(BURN_TIMES.get(iItemStack)));
    }
    
    public enum ListenBlockInfo implements Consumer<PlayerInteractEvent.RightClickBlock> {
        INSTANCE;
        
        @Override
        public void accept(PlayerInteractEvent.RightClickBlock event) {
            
            PlayerEntity player = event.getPlayer();
            World world = event.getWorld();
            if(BLOCK_INFO_PLAYERS.contains(player) && !world.isRemote) {
                BlockState state = world.getBlockState(event.getPos());
                ExpandPlayerEntity.sendMessage(player, MCTextComponent.createStringTextComponent("Block Name: " + state.getBlock()
                        .getRegistryName()
                        .toString()));
                ExpandPlayerEntity.sendMessage(player, MCTextComponent.createStringTextComponent("Properties: "));
                state.getProperties().forEach(property -> {
                    ExpandPlayerEntity.sendMessage(player, MCTextComponent.createStringTextComponent(property.getName() + ": " + state
                            .get(property)
                            .toString()));
                });
                MapData tileData = (MapData) ExpandWorld.getTileData(world, event.getPos());
                if(!tileData.isEmpty()) {
                    ExpandPlayerEntity.sendMessage(player, MCTextComponent.createStringTextComponent("Tile Data: " + tileData
                            .asString()));
                }
            }
        }
    }
    
}
