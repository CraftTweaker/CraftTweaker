/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc164;

import cpw.mods.fml.common.ICraftingHandler;
import minetweaker.MineTweakerImplementationAPI;
import minetweaker.api.event.PlayerCraftedEvent;
import minetweaker.api.event.PlayerSmeltedEvent;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.player.IPlayer;
import minetweaker.mc164.recipes.MCCraftingInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/**
 *
 * @author Stan
 */
public class MTCraftingHandler implements ICraftingHandler {
	@Override
	public void onCrafting(EntityPlayer player, ItemStack crafting, IInventory craftMatrix) {
		IPlayer iplayer = MineTweakerMC.getIPlayer(player);
		
		if (MineTweakerMod.INSTANCE.recipes.hasTransformerRecipes()) {
			MineTweakerMod.INSTANCE.recipes.applyTransformations(MCCraftingInventory.get(craftMatrix, player), iplayer);
		}
		
		if (MineTweakerImplementationAPI.events.hasPlayerCrafted()) {
			MineTweakerImplementationAPI.events.publishPlayerCrafted(new PlayerCraftedEvent(
					iplayer,
					MineTweakerMC.getIItemStack(crafting),
					MCCraftingInventory.get(craftMatrix, player)));
		}
	}

	@Override
	public void onSmelting(EntityPlayer ep, ItemStack is) {
		if (MineTweakerImplementationAPI.events.hasPlayerSmelted()) {
			MineTweakerImplementationAPI.events.publishPlayerSmelted(new PlayerSmeltedEvent(
					MineTweakerMC.getIPlayer(ep),
					MineTweakerMC.getIItemStack(is)));
		}
	}
}
