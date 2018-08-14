package crafttweaker.mc1120.events;

import crafttweaker.*;
import crafttweaker.api.damage.IDamageSource;
import crafttweaker.api.entity.*;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.recipes.CraftingInfo;
import crafttweaker.mc1120.brackets.*;
import crafttweaker.mc1120.damage.MCDamageSource;
import crafttweaker.mc1120.entity.MCEntity;
import crafttweaker.mc1120.events.handling.*;
import crafttweaker.mc1120.furnace.MCFurnaceManager;
import crafttweaker.mc1120.item.MCItemStack;
import crafttweaker.mc1120.oredict.MCOreDictEntry;
import crafttweaker.mc1120.recipes.*;
import crafttweaker.runtime.ScriptLoader;
import net.minecraft.entity.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.*;
import net.minecraftforge.event.brewing.PlayerBrewedPotionEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.item.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.oredict.OreDictionary;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static net.minecraftforge.fml.common.eventhandler.EventPriority.LOWEST;

public class CommonEventHandler {
    
    @SubscribeEvent(priority = LOWEST)
    public void registerRecipes(RegistryEvent.Register<IRecipe> ev) {
        CraftTweakerAPI.logInfo("CraftTweaker: Building registry");
        BracketHandlerItem.rebuildItemRegistry();
        BracketHandlerLiquid.rebuildLiquidRegistry();
        BracketHandlerEntity.rebuildEntityRegistry();
        BracketHandlerPotion.rebuildRegistry();
        BracketHandlerBiome.rebuildBiomeRegistry();
        BracketHandlerBiomeType.rebuildBiomeTypeRegistry();
        CraftTweakerAPI.logInfo("CraftTweaker: Successfully built item registry");
        MinecraftForge.EVENT_BUS.post(new ScriptRunEvent.Pre());
        final ScriptLoader loader = CraftTweakerAPI.tweaker.getOrCreateLoader("crafttweaker", "recipeEvent");
        loader.setMainName("crafttweaker");
        CraftTweakerAPI.tweaker.loadScript(false, loader);
        MinecraftForge.EVENT_BUS.post(new ScriptRunEvent.Post());
        
    }
    
    @SubscribeEvent
    public void onFurnaceFuelBurnTime(FurnaceFuelBurnTimeEvent event) {
        if(!MCFurnaceManager.fuelMap.isEmpty()) {
            if(!event.getItemStack().isEmpty()) {
                for(Map.Entry<IItemStack, Integer> entry : MCFurnaceManager.fuelMap.entrySet()) {
                    if(entry.getKey().matches(MCItemStack.createNonCopy(event.getItemStack()))) {
                        event.setBurnTime(entry.getValue());
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent ev) {
        if(CrafttweakerImplementationAPI.events.hasPlayerLoggedIn())
            CrafttweakerImplementationAPI.events.publishPlayerLoggedIn(new MCPlayerLoggedInEvent(CraftTweakerMC.getIPlayer(ev.player)));
    }
    
    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent ev) {
        if(CrafttweakerImplementationAPI.events.hasPlayerRespawn())
            CrafttweakerImplementationAPI.events.publishPlayerRespawn(new MCPlayerRespawnEvent(ev));
    }
    
    @SubscribeEvent
    public void onEntityItemPickup(EntityItemPickupEvent ev) {
        if(CrafttweakerImplementationAPI.events.hasPlayerPickupItem())
            CrafttweakerImplementationAPI.events.publishPlayerPickupItem(new MCPlayerPickupItemEvent(ev));
    }
    
    @SubscribeEvent
    public void onPlayerItemCrafted(PlayerEvent.ItemCraftedEvent ev) {
        if(ev.craftMatrix instanceof InventoryCrafting && !MCRecipeManager.transformerRecipes.isEmpty()) {
            MCRecipeManager.transformerRecipes.stream().filter(Objects::nonNull).filter(mcRecipeBase -> mcRecipeBase.matches((InventoryCrafting) ev.craftMatrix, ev.player.world)).forEach(recipe -> recipe.applyTransformers((InventoryCrafting) ev.craftMatrix, CraftTweakerMC.getIPlayer(ev.player)));
        }
        
        IPlayer iPlayer = CraftTweakerMC.getIPlayer(ev.player);
        if(ev.craftMatrix instanceof InventoryCrafting) {
            Stream<IRecipe> recipeStream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(CraftingManager.REGISTRY.iterator(), 0), false);
            // only look at tweaker recipes (Unchecked cast here is necessairy)
            Stream<MCRecipeBase> tweakedRecipeStream = recipeStream.filter(MCRecipeBase.class::isInstance).map(MCRecipeBase.class::cast);
            // check for the presence of a recipe action first since that is cheaper.
            tweakedRecipeStream.filter(MCRecipeBase::hasRecipeAction)
                    .filter(recipe->recipe.getRecipeOutput().isItemEqual(ev.crafting))
                    .forEach(recipe->recipe.getRecipeAction().process(CraftTweakerMC.getIItemStack(ev.crafting), new CraftingInfo(new MCCraftingInventorySquared(ev.craftMatrix, iPlayer), iPlayer.getWorld()), iPlayer));
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
        if(CrafttweakerImplementationAPI.events.hasPlayerLoggedOut())
            CrafttweakerImplementationAPI.events.publishPlayerLoggedOut(new MCPlayerLoggedOutEvent(CraftTweakerMC.getIPlayer(ev.player)));
    }
    
    
    @SubscribeEvent
    public void onPlayerInteract(net.minecraftforge.event.entity.player.PlayerInteractEvent ev) {
        if(CrafttweakerImplementationAPI.events.hasPlayerInteract())
            CrafttweakerImplementationAPI.events.publishPlayerInteract(new MCPlayerInteractEvent(ev));
    }
    
    @SubscribeEvent
    public void onPlayerInteractEntity(PlayerInteractEvent.EntityInteract ev) {
        if(CrafttweakerImplementationAPI.events.hasPlayerInteractEntity())
            CrafttweakerImplementationAPI.events.publishPlayerInteractEntity(new MCPlayerInteractEntityEvent(ev));
    }
    
    @SubscribeEvent
    public void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent ev) {
        if(CrafttweakerImplementationAPI.events.hasPlayerChangedDimension())
            CrafttweakerImplementationAPI.events.publishPlayerChangedDimension(new MCPlayerChangedDimensionEvent(ev));
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
        if(CrafttweakerImplementationAPI.events.hasPlayerBonemeal())
            CrafttweakerImplementationAPI.events.publishPlayerBonemeal(new MCPlayerBonemealEvent(ev));
    }
    
    @SubscribeEvent
    public void onPlayerFillBucket(FillBucketEvent ev) {
        if(CrafttweakerImplementationAPI.events.hasPlayerFillBucket()) {
            CrafttweakerImplementationAPI.events.publishPlayerFillBucket(new MCPlayerFillBucketEvent(ev));
        }
    }
    
    @SubscribeEvent
    public void onPlayerDeathDrops(PlayerDropsEvent ev) {
        if(CrafttweakerImplementationAPI.events.hasPlayerDeathDrops())
            CrafttweakerImplementationAPI.events.publishPlayerDeathDrops(new MCPlayerDeathDropsEvent(ev));
    }
    
    @SubscribeEvent
    public void onPlayerSleepInBed(PlayerSleepInBedEvent ev) {
        if(CrafttweakerImplementationAPI.events.hasPlayerSleepInBed())
            CrafttweakerImplementationAPI.events.publishPlayerSleepInBed(new MCPlayerSleepInBedEvent(ev));
    }
    
    @SubscribeEvent
    public void onPlayerOpenContainer(PlayerContainerEvent.Open ev) {
        if(CrafttweakerImplementationAPI.events.hasPlayerOpenContainer())
            CrafttweakerImplementationAPI.events.publishPlayerOpenContainer(new MCPlayerOpenContainerEvent(ev));
    }
    
    @SubscribeEvent
    public void onUseHoe(UseHoeEvent ev) {
        if(CrafttweakerImplementationAPI.events.hasPlayerUseHoe())
            CrafttweakerImplementationAPI.events.publishPlayerUseHoe(new MCPlayerUseHoeEvent(ev));
    }
    
    @SubscribeEvent
    public void onOreDictEvent(OreDictionary.OreRegisterEvent ev) {
        List<IItemStack> ingredients = MCOreDictEntry.REMOVED_CONTENTS.get(ev.getName());
        if(ingredients != null)
            for(IItemStack ingredient : ingredients)
                if(ingredient.matches(CraftTweakerMC.getIItemStack(ev.getOre())))
                    OreDictionary.getOres(ev.getName()).remove(ev.getOre());
    }
    
    @SubscribeEvent
    public void onPlayerPickupXp(PlayerPickupXpEvent ev) {
        if(CrafttweakerImplementationAPI.events.hasPlayerPickupXp())
            CrafttweakerImplementationAPI.events.publishPlayerPickupXp(new MCPlayerPickupXpEvent(ev));
    }
    
    @SubscribeEvent
    public void onLivingEntityUseItemStartEvent(LivingEntityUseItemEvent.Start ev) {
        if(CrafttweakerImplementationAPI.events.hasEntityLivingUseItemStart()) {
            CrafttweakerImplementationAPI.events.publishEntityLivingUseItemStart(new MCEntityLivingUseItemEvent.Start(ev));
        }
    }
    
    @SubscribeEvent
    public void onLivingEntityUseItemStopEvent(LivingEntityUseItemEvent.Stop ev) {
        if(CrafttweakerImplementationAPI.events.hasEntityLivingUseItemStop()) {
            CrafttweakerImplementationAPI.events.publishEntityLivingUseItemStop(new MCEntityLivingUseItemEvent.Stop(ev));
        }
    }
    
    @SubscribeEvent
    public void onLivingEntityUseItemTickEvent(LivingEntityUseItemEvent.Tick ev) {
        if(CrafttweakerImplementationAPI.events.hasEntityLivingUseItemTick()) {
            CrafttweakerImplementationAPI.events.publishEntityLivingUseItemTick(new MCEntityLivingUseItemEvent.Tick(ev));
        }
    }
    
    @SubscribeEvent
    public void onLivingEntityUseItemFinishEvent(LivingEntityUseItemEvent.Finish ev) {
        if(CrafttweakerImplementationAPI.events.hasEntityLivingUseItemFinish()) {
            CrafttweakerImplementationAPI.events.publishEntityLivingUseItemFinish(new MCEntityLivingUseItemEvent.Finish(ev));
        }
    }
    
    @SubscribeEvent
    public void onLivingEntityUseItemEvent(LivingEntityUseItemEvent ev) {
        if(CrafttweakerImplementationAPI.events.hasEntityLivingUseItem()) {
            CrafttweakerImplementationAPI.events.publishEntityLivingUseItem(new MCEntityLivingUseItemEvent(ev));
        }
    }
    
    @SubscribeEvent
    public void onPlayerAttackEntityEvent(AttackEntityEvent ev) {
        if(CrafttweakerImplementationAPI.events.hasPlayerAttackEntity()) {
            CrafttweakerImplementationAPI.events.publishPlayerAttackEntity(new MCPlayerAttackEntityEvent(ev));
        }
    }
    
    @SubscribeEvent
    public void onEntityStruckByLightningEvent(EntityStruckByLightningEvent ev) {
        if(CrafttweakerImplementationAPI.events.hasEntityStruckByLightning()) {
            CrafttweakerImplementationAPI.events.publishEntityStruckByLightning(new MCEntityStruckByLightningEvent(ev));
        }
    }
    
    @SubscribeEvent
    public void onEnderTeleportEvent(EnderTeleportEvent ev) {
        if(CrafttweakerImplementationAPI.events.hasEnderTeleport()) {
            CrafttweakerImplementationAPI.events.publishEnderTeleport(new MCEnderTeleportEvent(ev));
        }
    }
    
    @SubscribeEvent
    public void onLivingAttackEvent(LivingAttackEvent ev) {
        if(CrafttweakerImplementationAPI.events.hasEntityLivingAttacked())
            CrafttweakerImplementationAPI.events.publishEntityLivingAttacked(new MCEntityLivingAttackedEvent(ev));
    }
    
    
    @SubscribeEvent
    public void onEntityLivingDeathEvent(LivingDeathEvent ev) {
        if(CrafttweakerImplementationAPI.events.hasEntityLivingDeath())
            CrafttweakerImplementationAPI.events.publishEntityLivingDeath(new MCEntityLivingDeathEvent(ev));
    }
    
    @SubscribeEvent
    public void onEntityLivingFallEvent(LivingFallEvent ev) {
        if(CrafttweakerImplementationAPI.events.hasEntityLivingFall())
            CrafttweakerImplementationAPI.events.publishEntityLivingFall(new MCEntityLivingFallEvent(ev));
    }
    
    @SubscribeEvent
    public void onEntityLivingHurtEvent(LivingHurtEvent ev) {
        if(CrafttweakerImplementationAPI.events.hasEntityLivingHurt())
            CrafttweakerImplementationAPI.events.publishEntityLivingHurt(new MCEntityLivingHurtEvent(ev));
    }
    
    @SubscribeEvent
    public void onEntityLivingJumpEvent(LivingEvent.LivingJumpEvent ev) {
        if(CrafttweakerImplementationAPI.events.hasEntityLivingJump())
            CrafttweakerImplementationAPI.events.publishEntityLivingJump(new MCEntityLivingJumpEvent(ev));
    }
    
    @SubscribeEvent
    public void onEntityLivingDeathDropsEvent(LivingDropsEvent ev) {
        if(CrafttweakerImplementationAPI.events.hasEntityLivingDeathDrops())
            CrafttweakerImplementationAPI.events.publishEntityLivingDeathDrops(new MCEntityLivingDeathDropsEvent(ev));
    }
    
    @SubscribeEvent
    public void onItemExpireEvent(ItemExpireEvent ev) {
        if(CrafttweakerImplementationAPI.events.hasItemExpire())
            CrafttweakerImplementationAPI.events.publishItemExpire(new MCItemExpireEvent(ev));
    }
    
    @SubscribeEvent
    public void onItemTossEvent(ItemTossEvent ev) {
        if(CrafttweakerImplementationAPI.events.hasItemToss())
            CrafttweakerImplementationAPI.events.publishItemToss(new MCItemTossEvent(ev));
    }
    
    @SubscribeEvent
    public void onPlayerAnvilRepairEvent(AnvilRepairEvent ev) {
        if(CrafttweakerImplementationAPI.events.hasPlayerAnvilRepair())
            CrafttweakerImplementationAPI.events.publishPlayerAnvilRepair(new MCPlayerAnvilRepairEvent(ev));
    }
    
    @SubscribeEvent
    public void onPlayerSetSpawnEvent(PlayerSetSpawnEvent ev) {
        if(CrafttweakerImplementationAPI.events.hasPlayerSetSpawn())
            CrafttweakerImplementationAPI.events.publishPlayerSetSpawn(new MCPlayerSetSpawnEvent(ev));
    }
    
    @SubscribeEvent
    public void onPlayerDestroyItemEvent(PlayerDestroyItemEvent ev) {
        if(CrafttweakerImplementationAPI.events.hasPlayerDestroyItem())
            CrafttweakerImplementationAPI.events.publishPlayerDestroyItem(new MCPlayerDestroyItemEvent(ev));
    }
    
    @SubscribeEvent
    public void onPlayerBrewedPotionEvent(PlayerBrewedPotionEvent ev) {
        if(CrafttweakerImplementationAPI.events.hasPlayerBrewedPotion())
            CrafttweakerImplementationAPI.events.publishPlayerBrewedPotion(new MCPlayerBrewedPotionEvent(ev));
    }
    
    @SubscribeEvent
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent ev) {
        if(CrafttweakerImplementationAPI.events.hasPlayerTick())
            CrafttweakerImplementationAPI.events.publishPlayerTick(new MCPlayerTickEvent(CraftTweakerMC.getIPlayer(ev.player)));
    }
    
    @SubscribeEvent
    public void onBlockBreakEvent(BlockEvent.BreakEvent ev) {
        if(CrafttweakerImplementationAPI.events.hasBlockBreak())
            CrafttweakerImplementationAPI.events.publishBlockBreak(new MCBlockBreakEvent(ev));
    }
    
    @SubscribeEvent
    public void onBlockHarvestDropsEvent(BlockEvent.HarvestDropsEvent ev) {
        if(CrafttweakerImplementationAPI.events.hasBlockHarvestDrops())
            CrafttweakerImplementationAPI.events.publishBlockHarvestDrops(new MCBlockHarvestDropsEvent(ev));
    }
    
    @SubscribeEvent
    public void onPlayerBreakSpeedEvent(net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed ev) {
        if(CrafttweakerImplementationAPI.events.hasPlayerBreakSpeed())
            CrafttweakerImplementationAPI.events.publishPlayerBreakSpeed(new MCPlayerBreakSpeedEvent(ev));
    }
    
    @SubscribeEvent
    public void onPlayerRightClickBlockEvent(PlayerInteractEvent.RightClickBlock ev) {
        if(CrafttweakerImplementationAPI.events.hasPlayerRightClickBlock())
            CrafttweakerImplementationAPI.events.publishPlayerRightClickBlock(new MCPlayerRightClickBlockEvent(ev));
    }


    @SubscribeEvent
    public void onCommandEvent(CommandEvent ev) {
        if(CrafttweakerImplementationAPI.events.hasCommand())
            CrafttweakerImplementationAPI.events.publishCommand(new MCCommandEvent(ev));
    }


}
