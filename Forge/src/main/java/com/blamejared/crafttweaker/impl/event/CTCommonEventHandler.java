package com.blamejared.crafttweaker.impl.event;

import com.blamejared.crafttweaker.CraftTweakerCommon;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.action.villager.ActionTradeBase;
import com.blamejared.crafttweaker.api.event.type.GatherReplacementExclusionEvent;
import com.blamejared.crafttweaker.api.item.attribute.ItemAttributeModifierBase;
import com.blamejared.crafttweaker.api.logger.CraftTweakerLogger;
import com.blamejared.crafttweaker.api.recipe.replacement.rule.DefaultExclusionReplacements;
import com.blamejared.crafttweaker.api.villager.CTVillagerTrades;
import com.blamejared.crafttweaker.impl.script.ScriptReloadListener;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.server.ServerLifecycleHooks;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = CraftTweakerConstants.MOD_ID)
public class CTCommonEventHandler {
    
    @SubscribeEvent
    public void onGatherReplacementExclusion(GatherReplacementExclusionEvent event) {
        
        DefaultExclusionReplacements.handleDefaultExclusions(event);
    }
    
    @SubscribeEvent
    public static void blockInteract(PlayerInteractEvent.RightClickBlock e) {
        
        if(Services.EVENT.onBlockInteract(e.getPlayer(), e.getHand(), e.getHitVec())) {
            e.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public static void entityInteract(PlayerInteractEvent.EntityInteract e) {
        
        if(Services.EVENT.onEntityInteract(e.getPlayer(), e.getHand(), e.getTarget())) {
            e.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public static void attribute(ItemAttributeModifierEvent e) {
        
        Services.EVENT.applyAttributeModifiers((ItemAttributeModifierBase) e);
    }
    
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void wanderingTradesTweaker(WandererTradesEvent e) {
        
        CTVillagerTrades.ACTION_WANDERING_TRADES.forEach(ActionTradeBase::undo);
        CTVillagerTrades.ACTION_WANDERING_TRADES.forEach(actionTradeBase -> {
            
            List<VillagerTrades.ItemListing> trades;
            switch(actionTradeBase.getLevel()) {
                case 1:
                    trades = e.getGenericTrades();
                    break;
                case 2:
                    trades = e.getRareTrades();
                    break;
                default:
                    return;
            }
            actionTradeBase.apply(trades);
        });
        CTVillagerTrades.ACTION_WANDERING_TRADES.clear();
    }
    
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void villagerTradesTweaker(VillagerTradesEvent e) {
        
        List<ActionTradeBase> collect = CTVillagerTrades.ACTIONS_VILLAGER_TRADES.stream()
                .filter(actionTradeBase -> actionTradeBase.getProfession() == e.getType()).toList();
        collect.forEach(ActionTradeBase::undo);
        collect.forEach(actionTradeBase -> actionTradeBase.apply(e.getTrades()
                .computeIfAbsent(actionTradeBase.getLevel(), value -> new ArrayList<>())));
        CTVillagerTrades.ACTIONS_VILLAGER_TRADES.removeAll(collect);
        CTVillagerTrades.RAN_EVENTS = true;
    }
    
    @SubscribeEvent
    public static void burnTimeTweaker(FurnaceFuelBurnTimeEvent e) {
        
        Services.EVENT.getBurnTimes().keySet()
                .stream()
                .filter(ingredient -> ingredient.matches(Services.PLATFORM.createMCItemStackMutable(e.getItemStack())))
                .findFirst()
                .ifPresent(ingredient -> e.setBurnTime(Services.EVENT.getBurnTimes().get(ingredient)));
    }
    
    @SubscribeEvent
    public static void playerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        
        CraftTweakerLogger.addPlayer(event.getPlayer());
    }
    
    @SubscribeEvent
    public static void playerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        
        CraftTweakerLogger.removePlayer(event.getPlayer());
    }
    
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        
        CraftTweakerCommon.registerCommands(event.getDispatcher(), event.getEnvironment());
    }
    
    
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void resourceReload(AddReloadListenerEvent event) {
        
        event.addListener(new ScriptReloadListener(event.getDataPackRegistries()::getRecipeManager, CTCommonEventHandler::giveFeedback));
    }
    
    private static void giveFeedback(MutableComponent msg) {
        
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if(server != null) {
            server.getPlayerList().broadcastMessage(msg, ChatType.SYSTEM, CraftTweakerConstants.CRAFTTWEAKER_UUID);
        } else {
            CraftTweakerCommon.LOG.info(msg.getString());
        }
    }
    
}
