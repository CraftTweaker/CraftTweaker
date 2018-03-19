package crafttweaker.mc1120.events;

import crafttweaker.*;
import crafttweaker.api.damage.IDamageSource;
import crafttweaker.api.entity.*;
import crafttweaker.mc1120.events.handling.*;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.recipes.CraftingInfo;
import crafttweaker.mc1120.brackets.*;
import crafttweaker.mc1120.damage.MCDamageSource;
import crafttweaker.mc1120.entity.MCEntity;
import crafttweaker.mc1120.furnace.MCFurnaceManager;
import crafttweaker.mc1120.item.MCItemStack;
import crafttweaker.mc1120.recipes.*;
import crafttweaker.mc1120.world.MCWorld;
import net.minecraft.entity.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.*;

import static net.minecraftforge.fml.common.eventhandler.EventPriority.LOWEST;

public class CommonEventHandler {
    
    @SubscribeEvent(priority = LOWEST)
    public void registerRecipes(RegistryEvent.Register<IRecipe> ev) {
        CraftTweakerAPI.logInfo("CraftTweaker: Building registry");
        BracketHandlerItem.rebuildItemRegistry();
        BracketHandlerLiquid.rebuildLiquidRegistry();
        BracketHandlerEntity.rebuildEntityRegistry();
        BracketHandlerPotion.rebuildRegistry();
        
        CraftTweakerAPI.logInfo("CraftTweaker: Successfully built item registry");
        MinecraftForge.EVENT_BUS.post(new ScriptRunEvent.Pre());
        CrafttweakerImplementationAPI.load();
        MinecraftForge.EVENT_BUS.post(new ScriptRunEvent.Post());
        
    }
    
    @SubscribeEvent
    public void onFurnaceFuelBurnTime(FurnaceFuelBurnTimeEvent event) {
        if(!MCFurnaceManager.fuelMap.isEmpty()) {
            if(!event.getItemStack().isEmpty()) {
                for(Map.Entry<IItemStack, Integer> entry : MCFurnaceManager.fuelMap.entrySet()) {
                    if(entry.getKey().matches(CraftTweakerMC.getIItemStack(event.getItemStack()))) {
                        event.setBurnTime(entry.getValue());
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent ev) {
        CrafttweakerImplementationAPI.events.publishPlayerLoggedIn(new MCPlayerLoggedInEvent(CraftTweakerMC.getIPlayer(ev.player)));
    }
    
    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent ev) {
        CrafttweakerImplementationAPI.events.publishPlayerRespawn(new MCPlayerRespawnEvent(ev));
    }
    
    @SubscribeEvent
    public void onEntityItemPickup(EntityItemPickupEvent ev) {
        MCPlayerPickupItemEvent event = new MCPlayerPickupItemEvent(ev);
        if(CrafttweakerImplementationAPI.events.publishPlayerPickupItem(event))
            ev.setCanceled(true);
    }
    
    @SubscribeEvent
    public void onPlayerItemCrafted(PlayerEvent.ItemCraftedEvent ev) {
        if(ev.craftMatrix instanceof InventoryCrafting && !MCRecipeManager.transformerRecipes.isEmpty()) {
            MCRecipeManager.transformerRecipes.stream().filter(MCRecipeBase.class::isInstance).filter(mcRecipeBase -> mcRecipeBase.matches((InventoryCrafting) ev.craftMatrix, ev.player.world)).forEach(recipe -> recipe.applyTransformers((InventoryCrafting) ev.craftMatrix, CraftTweakerMC.getIPlayer(ev.player)));
        }
        
        IPlayer iPlayer = CraftTweakerMC.getIPlayer(ev.player);
        if(ev.craftMatrix instanceof InventoryCrafting) {
            CraftingManager.REGISTRY.getKeys().stream().filter(key -> CraftingManager.REGISTRY.getObject(key).getRecipeOutput().isItemEqual(ev.crafting)).forEach(i -> {
                IRecipe recipe = CraftingManager.REGISTRY.getObject(i);
                if(recipe instanceof MCRecipeBase) {
                    MCRecipeBase recipeBase = (MCRecipeBase) recipe;
                    if(recipeBase.hasRecipeAction())
                        recipeBase.getRecipeAction().process(CraftTweakerMC.getIItemStack(ev.crafting), new CraftingInfo(new MCCraftingInventorySquared(ev.craftMatrix, iPlayer), iPlayer.getWorld()), iPlayer);
                }
            });
        }
        if(CrafttweakerImplementationAPI.events.hasPlayerCrafted()) {
            CrafttweakerImplementationAPI.events.publishPlayerCrafted(new MCPlayerCraftedEvent(ev));
        }
    }
    
    @SubscribeEvent
    public void onPlayerItemSmelted(PlayerEvent.ItemSmeltedEvent ev) {
        if(CrafttweakerImplementationAPI.events.hasPlayerSmelted()) {
            CrafttweakerImplementationAPI.events.publishPlayerSmelted(new MCPlayerSmeltedEvent(CraftTweakerMC.getIPlayer(ev.player), CraftTweakerMC.getIItemStack(ev.smelting)));
        }
    }
    
    @SubscribeEvent
    public void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent ev) {
        CrafttweakerImplementationAPI.events.publishPlayerLoggedOut(new MCPlayerLoggedOutEvent(CraftTweakerMC.getIPlayer(ev.player)));
    }
    
    
    @SubscribeEvent
    public void onPlayerInteract(net.minecraftforge.event.entity.player.PlayerInteractEvent ev) {
        if(!ev.getWorld().isRemote) {
            MCPlayerInteractEvent event = new MCPlayerInteractEvent(ev);
            if(CrafttweakerImplementationAPI.events.publishPlayerInteract(event))
                ev.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent ev) {
        MCPlayerChangedDimensionEvent event = new MCPlayerChangedDimensionEvent(ev);
        CrafttweakerImplementationAPI.events.publishPlayerChangedDimension(event);
    }
    
    @SubscribeEvent
    public void mobDrop(LivingDropsEvent ev) {
        Entity entity = ev.getEntity();
        IEntityDefinition entityDefinition = CraftTweakerAPI.game.getEntity(EntityList.getEntityString(ev.getEntity()));
        if(entityDefinition != null) {
            if(entityDefinition.shouldClearDrops()) {
                ev.getDrops().clear();
            } else if(!entityDefinition.getDropsToRemove().isEmpty()) {
                List<EntityItem> removedDrops = new ArrayList<>();
                entityDefinition.getDropsToRemove().forEach(drop -> ev.getDrops().forEach(drops -> {
                    if(drop.matches(new MCItemStack(drops.getItem()))) {
                        removedDrops.add(drops);
                    }
                }));
                ev.getDrops().removeAll(removedDrops);
            }
            if(!entityDefinition.getDrops().isEmpty()) {
                Random rand = entity.world.rand;
                entityDefinition.getDrops().forEach(drop -> {
                    if(drop.isPlayerOnly() && !(ev.getSource().getTrueSource() instanceof EntityPlayer))
                        return;
                    if(drop.getChance() > 0 && drop.getChance() < 1 && rand.nextFloat() > drop.getChance())
                        return;
                    EntityItem item;
                    if(drop.getMin() == 0 && drop.getMax() == 0) {
                        item = new EntityItem(entity.world, entity.posX + 0.5, entity.posY + 0.5, entity.posZ + 0.5, ((ItemStack) drop.getItemStack().getInternal()).copy());
                    } else {
                        item = new EntityItem(entity.world, entity.posX + 0.5, entity.posY + 0.5, entity.posZ + 0.5, ((ItemStack) drop.getItemStack().withAmount(drop.getRange().getRandom(rand)).getInternal()).copy());
                    }
                    if(item.getItem().getCount() != 0) {
                        ev.getDrops().add(item);
                    }
                });
            }
            
            if(!entityDefinition.getDropFunctions().isEmpty()) {
                IEntity ent = new MCEntity(ev.getEntity());
                IDamageSource dmgSource = new MCDamageSource(ev.getSource());
                entityDefinition.getDropFunctions().stream().map((fun) -> fun.handle(ent, dmgSource)).filter(Objects::nonNull).filter((item) -> item.getAmount() > 0).map(CraftTweakerMC::getItemStack).map((ItemStack item) -> new EntityItem(entity.world, entity.posX + 0.5, entity.posY + 0.5, entity.posZ + 0.5, item)).forEach(ev.getDrops()::add);
            }
        }
    }
    
    @SubscribeEvent
    public void onPlayerBonemeal(BonemealEvent ev) {
        if(CrafttweakerImplementationAPI.events.hasPlayerBonemeal()) {
            EntityPlayer player = ev.getEntityPlayer();
            if(player != null) {
                boolean shouldCancel = CrafttweakerImplementationAPI.events.publishPlayerBonemeal(new MCPlayerBonemealEvent(ev));
                if(shouldCancel)
                    ev.setCanceled(true);
            }
        }
    }
    
    @SubscribeEvent
    public void onPlayerFillBucket(FillBucketEvent ev) {
        if (CrafttweakerImplementationAPI.events.hasPlayerFillBucket()) {
            CrafttweakerImplementationAPI.events.publishPlayerFillBucket(new MCPlayerFillBucketEvent(ev));
        }
    }
    
    @SubscribeEvent
    public void onPlayerDeathDrops(PlayerDropsEvent ev) {
        if (CrafttweakerImplementationAPI.events.hasPlayerDeathDrops())
                CrafttweakerImplementationAPI.events.publishPlayerDeathDrops(new MCPlayerDeathDropsEvent(ev));
    }
}
