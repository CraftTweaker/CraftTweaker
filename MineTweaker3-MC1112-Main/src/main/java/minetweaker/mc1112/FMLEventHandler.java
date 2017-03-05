package minetweaker.mc1112;

import minetweaker.*;
import minetweaker.api.event.*;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.player.IPlayer;
import minetweaker.api.recipes.*;
import minetweaker.mc1112.item.MCItemStack;
import minetweaker.mc1112.network.MineTweakerLoadScriptsPacket;
import minetweaker.mc1112.player.MCPlayer;
import minetweaker.mc1112.recipes.MCCraftingInventory;
import minetweaker.mc1112.world.MCDimension;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

/**
 * @author Stan
 */
public class FMLEventHandler {

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent ev) {
        if(ev.player instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) ev.player;
            MineTweakerMod.NETWORK.sendTo(new MineTweakerLoadScriptsPacket(MineTweakerAPI.tweaker.getScriptData()), player);
        }

        MineTweakerImplementationAPI.events.publishPlayerLoggedIn(new PlayerLoggedInEvent(MineTweakerMC.getIPlayer(ev.player)));
    }

    @SubscribeEvent
    public void onPlayerItemCrafted(PlayerEvent.ItemCraftedEvent ev) {
        IPlayer iPlayer = MineTweakerMC.getIPlayer(ev.player);
        if(MineTweakerMod.INSTANCE.recipes.hasTransformerRecipes()) {
            MineTweakerMod.INSTANCE.recipes.applyTransformations(MCCraftingInventory.get(ev.craftMatrix, ev.player), iPlayer);
        }
        if(ev.craftMatrix instanceof InventoryCrafting) {

            CraftingManager.getInstance().getRecipeList().stream().filter(i -> i instanceof IMTRecipe && i.getRecipeOutput().isItemEqual(ev.crafting)).forEach(i -> {
                IMTRecipe rec = (IMTRecipe) i;
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
        if(MineTweakerImplementationAPI.events.hasPlayerCrafted()) {
            MineTweakerImplementationAPI.events.publishPlayerCrafted(new PlayerCraftedEvent(iPlayer, MineTweakerMC.getIItemStack(ev.crafting), MCCraftingInventory.get(ev.craftMatrix, ev.player)));
        }
    }

    @SubscribeEvent
    public void onPlayerItemSmelted(PlayerEvent.ItemSmeltedEvent ev) {
        if(MineTweakerImplementationAPI.events.hasPlayerSmelted()) {
            MineTweakerImplementationAPI.events.publishPlayerSmelted(new PlayerSmeltedEvent(MineTweakerMC.getIPlayer(ev.player), MineTweakerMC.getIItemStack(ev.smelting)));
        }
    }

    @SubscribeEvent
    public void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent ev) {
        MineTweakerImplementationAPI.events.publishPlayerLoggedOut(new PlayerLoggedOutEvent(MineTweakerMC.getIPlayer(ev.player)));
    }

}
