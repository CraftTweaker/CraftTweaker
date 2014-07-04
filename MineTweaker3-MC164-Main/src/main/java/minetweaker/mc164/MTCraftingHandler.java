/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc164;

import cpw.mods.fml.common.ICraftingHandler;
import minetweaker.MineTweakerImplementationAPI;
import minetweaker.api.event.PlayerCraftedEvent;
import minetweaker.api.minecraft.MineTweakerMC;
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
		if (MineTweakerMod.INSTANCE.recipes.hasTransformerRecipes()) {
			MineTweakerMod.INSTANCE.recipes.applyTransformations(MCCraftingInventory.get(craftMatrix, player));
		}
		
		if (MineTweakerImplementationAPI.events.hasPlayerCrafted()) {
			MineTweakerImplementationAPI.events.publishPlayerCrafted(new PlayerCraftedEvent(
					MineTweakerMC.getIPlayer(player),
					MineTweakerMC.getIItemStack(crafting),
					MCCraftingInventory.get(craftMatrix, player)));
		}
	}

	@Override
	public void onSmelting(EntityPlayer ep, ItemStack is) {
		
	}
}
