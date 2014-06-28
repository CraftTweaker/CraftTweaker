package minetweaker.mc1710;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import minetweaker.MineTweakerAPI;
import minetweaker.mc1710.item.MCItemStack;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;
import stanhebben.zenscript.util.StringUtil;

/**
 * Implements the /minetweaker command set.
 * 
 * @author Stan Hebben
 */
public class MineTweakerCommand implements ICommand {
	// ###########################
	// ## Public static methods ##
	// ###########################
	
	public static void sendChatMessage(ICommandSender sender, String message) {
		sender.addChatMessage(new ChatComponentText(message));
	}
	
	private static final List<String> aliases = Arrays.asList("mt");
	
	// #######################################
	// ## ICommand interface implementation ##
	// #######################################
	
	@Override
	public int compareTo(Object arg0) {
		return 0;
	}

	@Override
	public String getCommandName() {
		return "minetweaker";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return StringUtil.join(new String[] {
			"",
			"minetweaker reload",
			"    Reloads all scripts",
			"minetweaker names",
			"    Outputs a list of all item names in the game to the minetweaker log",
			"minetweaker liquids",
			"    Outputs a list of all liquid names in the game to the minetweaker log",
			"minetweaker inventory",
			"    Lists all items in your inventory",
			"minetweaker hand",
			"    Outputs the name of the item in your hand",
			"minetweaker oredict",
			"    Outputs all ore dictionary entries in the game to the minetweaker log",
			"minetweaker oredict <name>",
			"    Outputs all items in the given ore dictionary entry to the minetweaker log",
			"minetweaker mods",
			"    Outputs all active mod IDs in the game",
			"minetweaker name <id>",
			"    Outputs the name for the given item ID"
		}, "\n");
	}

	@Override
	public List<String> getCommandAliases() {
		return aliases;
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] arguments) {
		if (arguments.length == 0) {
			sendChatMessage(icommandsender, "Please provide a command. Use /help minetweaker for more info.");
			return;
		}
		
		if (arguments[0].equals("reload")) {
			MineTweakerMod.INSTANCE.reload();
			
			String message = "Scripts have been reloaded";
			sendChatMessage(icommandsender, message);
		} else if (arguments[0].equals("names")) {
			Set<String> keys = Item.itemRegistry.getKeys();
			String[] sortedKeys = new String[keys.size()];
			int sortedKeyIndex = 0;
			for (String key : keys) sortedKeys[sortedKeyIndex++] = key;
			Arrays.sort(sortedKeys);
			
			for (String key : sortedKeys) {
				Item item = (Item) Item.itemRegistry.getObject(key);
				
				String displayName = "";
				
				try {
					displayName = " -- " + new ItemStack(item, 1, 0).getDisplayName();
				} catch (Exception ex) {
					// some mods (such as buildcraft) may throw exceptions when calling
					// getDisplayName on a item stack that doesn't contain valid NBT data
				}
				
				String message = "<" + key + ">" + displayName;
				MineTweakerAPI.logger.logCommand(message);
			}
			sendChatMessage(icommandsender, "List generated; see minetweaker.log in your minecraft dir");
		} else if (arguments[0].equals("liquids")) {
			for (Map.Entry<String, Fluid> entry : FluidRegistry.getRegisteredFluids().entrySet()) {
				String message = "<liquid:" + entry.getKey() + "> -- " + entry.getValue().getLocalizedName();
				MineTweakerAPI.logger.logCommand(message);
			}
			sendChatMessage(icommandsender, "List generated; see minetweaker.log in your minecraft dir");
		} else if (arguments[0].equals("inventory")) {
			if (icommandsender instanceof EntityPlayer) {
				InventoryPlayer inventory = ((EntityPlayer) icommandsender).inventory;
				for(int i = 0; i < inventory.getSizeInventory(); i++) {
					ItemStack stack = inventory.getStackInSlot(i);
					if (stack != null) {
						StringBuilder description = new StringBuilder();
						description.append('<');
						description.append(Item.itemRegistry.getNameForObject(stack.getItem()));
						if (stack.getItemDamage() > 0) {
							description.append(':').append(stack.getItemDamage());
						}
						description.append('>');
						
						if (stack.stackSize != 1) {
							description.append('*').append(stack.stackSize);
						}
						
						sendChatMessage(icommandsender, new MCItemStack(stack).toString());
					}
				}
			} else {
				sendChatMessage(icommandsender, "Inventory command can only be executed by players");
			}
		} else if (arguments[0].equals("hand")) {
			if (icommandsender instanceof EntityPlayer) {
				InventoryPlayer inventory = ((EntityPlayer) icommandsender).inventory;
				ItemStack stack = inventory.getCurrentItem();
				if (stack != null) {
					StringBuilder description = new StringBuilder();
					description.append('<');
					description.append(Item.itemRegistry.getNameForObject(stack.getItem()));
					if (stack.getItemDamage() > 0) {
						description.append(':').append(stack.getItemDamage());
					}
					description.append('>');

					if (stack.stackSize != 1) {
						description.append('*').append(stack.stackSize);
					}

					sendChatMessage(icommandsender, new MCItemStack(stack).toString());
				}
			} else {
				sendChatMessage(icommandsender, "Hand command can only be executed by players");
			}
		} else if (arguments[0].equals("oredict")) {
			if (arguments.length > 2) {
				String entryName = arguments[2];
				List<ItemStack> ores = OreDictionary.getOres(entryName);
				if (ores.isEmpty()) {
					sendChatMessage(icommandsender, "Entry doesn't exist");
					return;
				} else {
					MineTweakerAPI.logger.logCommand("Ore entries for " + entryName + ":");
					for (ItemStack ore : ores) {
						MineTweakerAPI.logger.logCommand("    " + new MCItemStack(ore).toString());
					}
				}
			} else {
				for (String entryName : OreDictionary.getOreNames()) {
					MineTweakerAPI.logger.logCommand("Ore entries for " + entryName + ":");
					List<ItemStack> ores = OreDictionary.getOres(entryName);
					for (ItemStack ore : ores) {
						MineTweakerAPI.logger.logCommand("    " + new MCItemStack(ore).toString());
					}
				}
			}
			sendChatMessage(icommandsender, "List generated; see minetweaker.log in your minecraft dir");
		} else if (arguments[0].equals("mods")) {
			List<ModContainer> mods = Loader.instance().getActiveModList();
			MineTweakerAPI.logger.logCommand("Mods list:");
			for (ModContainer mod : mods) {
				sendChatMessage(icommandsender, mod.getModId());
				MineTweakerAPI.logger.logCommand("Mod: " + mod.getModId());
			}
		} else if (arguments[0].equals("name")) {
			if (arguments.length < 2) {
				sendChatMessage(icommandsender, "missing id parameter");
			} else {
				try {
					int id = Integer.parseInt(arguments[1]);
					Item item = Item.getItemById(id);
					StringBuilder description = new StringBuilder();
					description.append('<');
					description.append(Item.itemRegistry.getNameForObject(item));
					description.append('>');
					sendChatMessage(icommandsender, description.toString());
				} catch (NumberFormatException e) {
					MineTweakerAPI.logger.logCommand("ID must be an integer");
				}
			}
		}
		
		/*if (arguments[0].equals("name")) {
			if (arguments.length < 2) {
				sendChatMessage(icommandsender, "Please provide an item id");
			} else {
				try {
					ITweakerItem item = TweakerItem.parse(arguments[1]);
					sendChatMessage(icommandsender, "Name for " + item.toIdString() + ": " + MineTweakerUtil.formatItemName(item.getName()));
				} catch (TweakerExecuteException ex) {
					sendChatMessage(icommandsender, ex.getMessage());
				} catch (TweakerException ex) {
					sendChatMessage(icommandsender, ex.getExplanation());
				}
			}
		} else if (arguments[0].equals("liquid")) {
			if (arguments.length < 2) {
				sendChatMessage(icommandsender, "Please provide a liquid container item id");
			} else {
				try {
					TweakerItem item = TweakerItem.parse(arguments[1]);
					//#ifdef MC152
					//+if (LiquidContainerRegistry.isLiquid(item.make(1))) {
						//+sendChatMessage(icommandsender, "Liquid name: " + item.getName());
					//+} else if (LiquidContainerRegistry.isFilledContainer(item.make(1))) {
						//+sendChatMessage(icommandsender, "Liquid name: " + LiquidContainerRegistry.getLiquidForFilledItem(item.make(1)).asItemStack().getItemName());
					//+} else {
						//+sendChatMessage(icommandsender, "This item is not a liquid or liquid container");
					//+}
					//#else
					if (item.getItemId() == 9) {
						sendChatMessage(icommandsender, "Liquid name: fluid.tile.water");
					} else if (item.getItemId() == 11) {
						sendChatMessage(icommandsender, "Liquid name: fluid.tile.lava");
					} else if (Block.blocksList[item.getItemId()] != null && Block.blocksList[item.getItemId()] instanceof IFluidBlock) {
						sendChatMessage(icommandsender, "Liquid name: " + ((IFluidBlock)(Block.blocksList[item.getItemId()])).getFluid().getUnlocalizedName());
					} else if (FluidContainerRegistry.isFilledContainer(item.make(1))) {
						sendChatMessage(icommandsender, "Liquid name: " + FluidContainerRegistry.getFluidForFilledItem(item.make(1)).getFluid().getUnlocalizedName());
					} else {
						sendChatMessage(icommandsender, "This item is not a liquid or liquid container");
					}
					//#endif
				} catch (TweakerExecuteException ex) {
					sendChatMessage(icommandsender, ex.getMessage());
				} catch (TweakerException ex) {
					sendChatMessage(icommandsender, ex.getExplanation());
				}
			}
		} else if (arguments[0].equals("oredict")) {
			if (arguments.length == 1) {
				String[] names = OreDictionary.getOreNames();
				Arrays.sort(names);
				for (String name : names) {
					sendChatMessage(icommandsender, "[" + name + "]");
					ArrayList<ItemStack> entries = OreDictionary.getOres(name);
					for (ItemStack stack : entries) {
						//#ifdef MC152
						//+sendChatMessage(icommandsender, 
								//+"  " + MineTweakerUtil.getItemString(stack) + " - " +
								//+MineTweakerUtil.formatItemName(stack.getItemDamage() == OreDictionary.WILDCARD_VALUE ? stack.getItem().getUnlocalizedName() : stack.getItemName()));
						//#else
						sendChatMessage(icommandsender, 
								"  " + MineTweakerUtil.getItemString(stack) + " - " +
								MineTweakerUtil.formatItemName(stack.getItemDamage() == OreDictionary.WILDCARD_VALUE ? stack.getItem().getUnlocalizedName() : stack.getUnlocalizedName()));
						//#endif
					}
				}
			} else {
				try {
					if (Character.isDigit(arguments[1].charAt(0))) {
						TweakerItemPattern item = TweakerItem.parse(arguments[1]).asItemPattern();
						for (String name : OreDictionary.getOreNames()) {
							for (ItemStack stack : OreDictionary.getOres(name)) {
								if (item.matches(stack)) {
									sendChatMessage(icommandsender, " " + name);
									break;
								}
							}
						}
					} else {
						for (ItemStack stack : OreDictionary.getOres(arguments[1])) {
							//#ifdef MC152
							//+sendChatMessage(icommandsender, 
									//+"  " + MineTweakerUtil.getItemString(stack) + " - " +
									//+MineTweakerUtil.formatItemName(stack.getItemDamage() == OreDictionary.WILDCARD_VALUE ? stack.getItem().getUnlocalizedName() : stack.getItemName()));
							//#else
							sendChatMessage(icommandsender, 
									"  " + MineTweakerUtil.getItemString(stack) + " - " +
									MineTweakerUtil.formatItemName(stack.getItemDamage() == OreDictionary.WILDCARD_VALUE ? stack.getItem().getUnlocalizedName() : stack.getUnlocalizedName()));
							//#endif
						}
					}
				} catch (TweakerExecuteException ex) {
					sendChatMessage(icommandsender, "Invalid item id or ore dictionary entry");
				} catch (TweakerException ex) {
					sendChatMessage(icommandsender, "Invalid item id or ore dictionary entry");
				}
			}
		} else if (arguments[0].equals("recipes")) {
			if (arguments.length < 2) {
				sendChatMessage(icommandsender, "Please provide an item id");
				return;
			}
			try {
				TweakerItem item = TweakerItem.parse(arguments[1]);
				sendChatMessage(icommandsender, "Recipes for item " + item.toIdString());
				
				TweakerItemPattern pattern = item.asItemPattern();
				
				@SuppressWarnings("unchecked")
				List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
				
				for (IRecipe recipe : recipes) {
					if (pattern.matches(recipe.getRecipeOutput())) {
						sendChatMessage(icommandsender, "  " + MineTweakerUtil.getRecipeString(recipe));
					}
				}
			} catch (TweakerExecuteException ex) {
				sendChatMessage(icommandsender, "Invalid item id");
			} catch (TweakerException ex) {
				sendChatMessage(icommandsender, "Invalid item id");
			}
		} else if (arguments[0].equals("fuel")) {
			if (arguments.length < 2) {
				sendChatMessage(icommandsender, "Please provide an item id");
				return;
			}
			try {
				TweakerItem item = TweakerItem.parse(arguments[1]);
				ItemStack stack = item.make(1);
				int value = TileEntityFurnace.getItemBurnTime(stack);
				int regValue = GameRegistry.getFuelValue(stack);
				
				sendChatMessage(icommandsender, "Fuel for " + item.toIdString() + " = " + value);
				if (value != regValue) {
					sendChatMessage(icommandsender, "The value is hardcoded and cannot be altered.");
				}
			} catch (TweakerExecuteException ex) {
				sendChatMessage(icommandsender, "Invalid item id");
			} catch (TweakerException ex) {
				sendChatMessage(icommandsender, "Invalid item id");
			}
		} else if (arguments[0].equals("reload")) {
			if (!MineTweaker.instance.canRollback()) {
				sendChatMessage(icommandsender, "Previous server script contained permanent actions, cannot reload. A restart is required to reload scripts.");
			} else {
				MineTweaker.instance.reloadScripts();
				sendChatMessage(icommandsender, "Script reloaded, updating all players");
			}
		} else if (arguments[0].equals("logdamage=on")) {
			DamageTweaker.logDamage = true;
			sendChatMessage(icommandsender, "Damage logging turned on");
			sendChatMessage(icommandsender, "Info: logging is sent to the development console");
		} else if (arguments[0].equals("logdamage=off")) {
			DamageTweaker.logDamage = false;
			sendChatMessage(icommandsender, "Damage logging turned off");
		} else {
			sendChatMessage(icommandsender, "Unrecognized minetweaker command");
		}*/
		
		//MineCraftCompat.sendChatMessage(icommandsender, "[" + Arrays2.join(arguments, ", ") + "]");
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender icommandsender) {
		return true;
	}

	@Override
	public List<?> addTabCompletionOptions(ICommandSender icommandsender,
			String[] astring) {
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] astring, int i) {
		return false;
	}
}
