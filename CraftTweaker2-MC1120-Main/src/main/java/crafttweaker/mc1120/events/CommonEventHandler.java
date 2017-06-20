package crafttweaker.mc1120.events;

import crafttweaker.*;
import crafttweaker.api.entity.IEntityDefinition;
import crafttweaker.api.event.*;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.recipes.*;
import crafttweaker.mc1120.CraftTweaker;
import crafttweaker.mc1120.item.MCItemStack;
import crafttweaker.mc1120.player.MCPlayer;
import crafttweaker.mc1120.recipes.MCCraftingInventory;
import crafttweaker.mc1120.world.MCDimension;
import net.minecraft.entity.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.*;

public class CommonEventHandler {
    
    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent ev) {
        CrafttweakerImplementationAPI.events.publishPlayerLoggedIn(new PlayerLoggedInEvent(CraftTweakerMC.getIPlayer(ev.player)));
    }
    
    @SubscribeEvent
    public void onPlayerItemCrafted(PlayerEvent.ItemCraftedEvent ev) {
        IPlayer iPlayer = CraftTweakerMC.getIPlayer(ev.player);
        if(CraftTweaker.INSTANCE.recipes.hasTransformerRecipes()) {
            CraftTweaker.INSTANCE.recipes.applyTransformations(MCCraftingInventory.get(ev.craftMatrix, ev.player), iPlayer);
        }
        if(ev.craftMatrix instanceof InventoryCrafting) {
            CraftingManager.REGISTRY.getKeys().stream().filter(key -> CraftingManager.REGISTRY.getObject(key) instanceof IMTRecipe && CraftingManager.REGISTRY.getObject(key).getRecipeOutput().isItemEqual(ev.crafting)).forEach(i -> {
                IRecipe recipe = CraftingManager.REGISTRY.getObject(i);
                IMTRecipe rec = (IMTRecipe) recipe;
                if(rec.getRecipe() instanceof ShapedRecipe) {
                    ShapedRecipe r = (ShapedRecipe) rec.getRecipe();
                    if(r.getAction() != null) {
                        r.getAction().process(new MCItemStack(ev.crafting), new CraftingInfo(new MCCraftingInventory(ev.craftMatrix, ev.player), new MCDimension(ev.player.world)), new MCPlayer(ev.player));
                    }
                } else if(rec.getRecipe() instanceof ShapelessRecipe) {
                    ShapelessRecipe r = (ShapelessRecipe) rec.getRecipe();
                    if(r.getAction() != null) {
                        r.getAction().process(new MCItemStack(ev.crafting), new CraftingInfo(new MCCraftingInventory(ev.craftMatrix, ev.player), new MCDimension(ev.player.world)), new MCPlayer(ev.player));
                    }
                }
            });
        }
        if(CrafttweakerImplementationAPI.events.hasPlayerCrafted()) {
            CrafttweakerImplementationAPI.events.publishPlayerCrafted(new PlayerCraftedEvent(iPlayer, CraftTweakerMC.getIItemStack(ev.crafting), MCCraftingInventory.get(ev.craftMatrix, ev.player)));
        }
    }
    
    @SubscribeEvent
    public void onPlayerItemSmelted(PlayerEvent.ItemSmeltedEvent ev) {
        if(CrafttweakerImplementationAPI.events.hasPlayerSmelted()) {
            CrafttweakerImplementationAPI.events.publishPlayerSmelted(new PlayerSmeltedEvent(CraftTweakerMC.getIPlayer(ev.player), CraftTweakerMC.getIItemStack(ev.smelting)));
        }
    }
    
    @SubscribeEvent
    public void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent ev) {
        CrafttweakerImplementationAPI.events.publishPlayerLoggedOut(new PlayerLoggedOutEvent(CraftTweakerMC.getIPlayer(ev.player)));
    }
    
    
    @SubscribeEvent
    public void onPlayerInteract(net.minecraftforge.event.entity.player.PlayerInteractEvent ev) {
        if(!ev.getWorld().isRemote) {
            PlayerInteractEvent event = new PlayerInteractEvent(CraftTweakerMC.getIPlayer(ev.getEntityPlayer()), CraftTweakerMC.getDimension(ev.getWorld()), ev.getPos().getX(), ev.getPos().getY(), ev.getPos().getZ());
            CrafttweakerImplementationAPI.events.publishPlayerInteract(event);
        }
    }
    
    @SubscribeEvent
    public void mobDrop(LivingDropsEvent ev) {
        Entity entity = ev.getEntity();
        IEntityDefinition entityDefinition = CraftTweakerAPI.game.getEntity(EntityList.getEntityString(ev.getEntity()));
        if(entityDefinition != null) {
            if(!entityDefinition.getDropsToAdd().isEmpty()) {
                entityDefinition.getDropsToAdd().forEach((key, val) -> {
                    EntityItem item;
                    if(val.getMin() == 0 && val.getMax() == 0) {
                        item = new EntityItem(entity.world, entity.posX + 0.5, entity.posY + 0.5, entity.posZ + 0.5, ((ItemStack) key.getInternal()).copy());
                    } else {
                        item = new EntityItem(entity.world, entity.posX + 0.5, entity.posY + 0.5, entity.posZ + 0.5, ((ItemStack) key.withAmount(val.getRandom()).getInternal()).copy());
                    }
                    ev.getDrops().add(item);
                });
            }
            if(ev.getSource().getTrueSource() instanceof EntityPlayer) {
                if(!entityDefinition.getDropsToAddPlayerOnly().isEmpty()) {
                    entityDefinition.getDropsToAddPlayerOnly().forEach((key, val) -> {
                        EntityItem item;
                        if(val.getMin() == 0 && val.getMax() == 0) {
                            item = new EntityItem(entity.world, entity.posX + 0.5, entity.posY + 0.5, entity.posZ + 0.5, ((ItemStack) key.getInternal()).copy());
                        } else {
                            item = new EntityItem(entity.world, entity.posX + 0.5, entity.posY + 0.5, entity.posZ + 0.5, ((ItemStack) key.withAmount(val.getRandom()).getInternal()).copy());
                        }
                        ev.getDrops().add(item);
                    });
                }
            }
            if(!entityDefinition.getDropsToRemove().isEmpty()) {
                List<EntityItem> removedDrops = new ArrayList<>();
                entityDefinition.getDropsToRemove().forEach(drop -> ev.getDrops().forEach(drops -> {
                    if(drop.matches(new MCItemStack(drops.getItem()))) {
                        removedDrops.add(drops);
                    }
                }));
                ev.getDrops().removeAll(removedDrops);
            }
        }
    }
}
