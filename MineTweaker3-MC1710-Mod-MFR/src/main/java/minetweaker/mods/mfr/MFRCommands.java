/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mods.mfr;

import java.util.Map;
import minetweaker.MineTweakerAPI;
import minetweaker.MineTweakerImplementationAPI;
import minetweaker.MineTweakerImplementationAPI.ReloadEvent;
import minetweaker.annotations.OnRegister;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.player.IPlayer;
import minetweaker.api.server.ICommandFunction;
import minetweaker.util.IEventHandler;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;
import cofh.lib.util.WeightedRandomItemStack;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizer;
import powercrystals.minefactoryreloaded.api.IFactoryFruit;

/**
 *
 * @author Stan
 */
public class MFRCommands {
	private static final String[] DESCRIPTION = {
		"    MineFactory commands:",
		"      /minetweaker mfr fertilizers",
		"        Lists all fertilizers",
		"      /minetweaker mfr fruits",
		"        Lists all known fruit blocks",
		"      /minetweaker mfr laserores",
		"        Lists all ores known to the mining laser",
		"      /minetweaker mfr rubbertreebiomes",
		"        Lists all biomes for the rubber tree",
		"      /minetweaker mfr sludgedrops",
		"        Lists all sludge boiler drops"
	};
	
	@OnRegister
	public static void onRegister() {
		MineTweakerImplementationAPI.onReloadEvent(new IEventHandler<ReloadEvent>() {
			@Override
			public void handle(ReloadEvent event) {
				MineTweakerImplementationAPI.addMineTweakerCommand("mfr", DESCRIPTION, new ICommandFunction() {
					@Override
					public void execute(String[] arguments, IPlayer player) {
						if (arguments.length < 1) {
							player.sendChat("Please specify a mfr command");
						} else {
							if (arguments[0].equals("fertilizers")) {
								MineTweakerAPI.logCommand("Fertilizers:");

								for (Map.Entry<Item, IFactoryFertilizer> fertilizer : MFRRegistry.getFertilizers().entrySet()) {
									IItemStack item = MineTweakerMC.getItemStack(fertilizer.getKey(), 1, 0);
									String message = "- " + item + " (" + item.getDisplayName() + ")";
									MineTweakerAPI.logCommand(message);
								}

								player.sendChat("Fertilizer list generated; see minetweaker.log");
							} else if (arguments[0].equals("fruits")) {
								MineTweakerAPI.logCommand("Fruit Blocks:");

								for (Map.Entry<Block, IFactoryFruit> fruitBlock : MFRRegistry.getFruits().entrySet()) {
									Block block = fruitBlock.getKey();
									String message = "- " + block.getUnlocalizedName() + " (" + block.getLocalizedName() + ")";
									MineTweakerAPI.logCommand(message);
								}

								player.sendChat("Fluit blocks list generated; see minetweaker.log");
							} else if (arguments[0].equals("laserores")) {
								MineTweakerAPI.logCommand("Laser ores:");

								for (WeightedRandom.Item randomItem : MFRRegistry.getLaserOres()) {
									if (randomItem instanceof WeightedRandomItemStack) {
										WeightedRandomItemStack randomItemStack = (WeightedRandomItemStack) randomItem;
										IItemStack item = MineTweakerMC.getIItemStack(randomItemStack.getStack());
										MineTweakerAPI.logCommand("    (" + randomItemStack.itemWeight + "): " + item + " (" + item.getDisplayName() + ")");
									}
								}

								player.sendChat("Laser ore list generated; see minetweaker.log");

								for (int i = 0; i < MineTweakerAPI.COLOR_NAMES.length; i++) {
									if (MFRRegistry.getLaserPreferredOres(i) != null && !MFRRegistry.getLaserPreferredOres(i).isEmpty()) {
										MineTweakerAPI.logCommand("Laser affinity " + i + " (" + MineTweakerAPI.COLOR_NAMES[i] + "):");

										for (ItemStack item : MFRRegistry.getLaserPreferredOres(i)) {
											IItemStack iitem = MineTweakerMC.getIItemStack(item);
											MineTweakerAPI.logCommand("    " + iitem + " (" + iitem.getDisplayName() + ")");
										}
									}
								}

								player.sendChat("Laser preferred ore list generated; see minetweaker.log");
							} else if (arguments[0].equals("rubbertreebiomes")) {
								MineTweakerAPI.logCommand("Rubber tree biomes:");
								for (String biome : MFRRegistry.getRubberTreeBiomes()) {
									MineTweakerAPI.logCommand("    " + biome);
								}

								player.sendChat("Rubber tree biome list generated; see minetweaker.log");
							} else if (arguments[0].equals("sludgedrops")) {
								MineTweakerAPI.logCommand("Sludge drops:");

								for (WeightedRandom.Item randItem : MFRRegistry.getSludgeDrops()) {
									if (randItem instanceof WeightedRandomItemStack) {
										WeightedRandomItemStack randomItem = (WeightedRandomItemStack) randItem;
										IItemStack item = MineTweakerMC.getIItemStack(randomItem.getStack());
										MineTweakerAPI.logCommand("    (" + randomItem.itemWeight + "): " + item + " (" + item.getDisplayName() + ")");
									}
								}

								player.sendChat("Sludge drop list generated; see minetweaker.log");
							} else {
								player.sendChat("Unknown mfr command: " + arguments[1]);
							}
						}
					}
				});
			}
		});
	}
}
