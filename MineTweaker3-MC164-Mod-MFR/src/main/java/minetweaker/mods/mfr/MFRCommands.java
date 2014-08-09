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
import powercrystals.core.random.WeightedRandomItemStack;
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
								if (MFRHacks.fertilizers == null) {
									player.sendChat("Could not load the fertilizer list");
								} else {
									MineTweakerAPI.logCommand("Fertilizers:");

									for (Map.Entry<Integer, IFactoryFertilizer> fertilizer : MFRHacks.fertilizers.entrySet()) {
										IItemStack item = MineTweakerMC.getItemStack(Item.itemsList[fertilizer.getKey()], 1, 0);
										String message = "- " + item + " (" + item.getDisplayName() + ")";
										MineTweakerAPI.logCommand(message);
									}

									player.sendChat("Fertilizer list generated; see minetweaker.log");
								}
							} else if (arguments[0].equals("fruits")) {
								if (MFRHacks.fruitBlocks == null) {
									player.sendChat("Could not load the fruit blocks list");
								} else {
									MineTweakerAPI.logCommand("Fruit Blocks:");

									for (Map.Entry<Integer, IFactoryFruit> fruitBlock : MFRHacks.fruitBlocks.entrySet()) {
										Block block = Block.blocksList[fruitBlock.getKey()];
										String message = "- " + block.getUnlocalizedName() + " (" + block.getLocalizedName() + ")";
										MineTweakerAPI.logCommand(message);
									}

									player.sendChat("Fluit blocks list generated; see minetweaker.log");
								}
							} else if (arguments[0].equals("laserores")) {
								if (MFRHacks.laserOres == null) {
									player.sendChat("Could not load the laser ores list");
								} else {
									MineTweakerAPI.logCommand("Laser ores:");

									for (WeightedRandomItemStack randomItem : MFRHacks.laserOres) {
										IItemStack item = MineTweakerMC.getIItemStack(randomItem.getStack());
										MineTweakerAPI.logCommand("    (" + randomItem.itemWeight + "): " + item + " (" + item.getDisplayName() + ")");
									}

									player.sendChat("Laser ore list generated; see minetweaker.log");
								}

								if (MFRHacks.laserPreferredOres == null) {
									player.sendChat("Could not load the laser preferred ores list");
								} else {
									for (int i = 0; i < MineTweakerAPI.COLOR_NAMES.length; i++) {
										if (MFRHacks.laserPreferredOres.containsKey(i) && !MFRHacks.laserPreferredOres.get(i).isEmpty()) {
											MineTweakerAPI.logCommand("Laser affinity " + i + " (" + MineTweakerAPI.COLOR_NAMES[i] + "):");

											for (ItemStack item : MFRHacks.laserPreferredOres.get(i)) {
												IItemStack iitem = MineTweakerMC.getIItemStack(item);
												MineTweakerAPI.logCommand("    " + iitem + " (" + iitem.getDisplayName() + ")");
											}
										}
									}

									player.sendChat("Laser preferred ore list generated; see minetweaker.log");
								}
							} else if (arguments[0].equals("rubbertreebiomes")) {
								if (MFRHacks.rubberTreeBiomes == null) {
									player.sendChat("Could not load the rubber tree biomes");
								} else {
									MineTweakerAPI.logCommand("Rubber tree biomes:");
									for (String biome : MFRHacks.rubberTreeBiomes) {
										MineTweakerAPI.logCommand("    " + biome);
									}

									player.sendChat("Rubber tree biome list generated; see minetweaker.log");
								}
							} else if (arguments[0].equals("sludgedrops")) {
								if (MFRHacks.sludgeDrops == null) {
									player.sendChat("Could not load sludge drops");
								} else {
									MineTweakerAPI.logCommand("Sludge drops:");

									for (WeightedRandomItemStack randomItem : MFRHacks.sludgeDrops) {
										IItemStack item = MineTweakerMC.getIItemStack(randomItem.getStack());
										MineTweakerAPI.logCommand("    (" + randomItem.itemWeight + "): " + item + " (" + item.getDisplayName() + ")");
									}

									player.sendChat("Sludge drop list generated; see minetweaker.log");
								}
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
