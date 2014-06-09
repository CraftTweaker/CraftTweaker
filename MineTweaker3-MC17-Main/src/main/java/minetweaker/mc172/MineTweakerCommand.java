package minetweaker.mc172;

import java.util.Arrays;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
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
			"minetweaker name <id>[:<meta>]",
			"    Prints the unlocalized name of the specified item," +
			"    can be used as item or block name",
			"minetweaker liquid <id>[:<meta>]",
			"    Prints the unlocalized liquid name in the specified container," +
			"    con be used as liquid name",
			"minetweaker oredict",
			"    Prints a list of all ore dictionary entries",
		    "minetweaker oredict <id>[:<meta>]",
		    "    Prints the ore dictionary name(s) for the specified item",
		    "minetweaker oredict <name>",
		    "    Prints all items for the specified ore dictionary name",
		    "minetweaker recipes <id>[:<meta>]",
		    "    Prints all recipes for the specified item",
		    "minetweaker fuel <id>[:<meta>]",
		    "    Retrieves the fuel value for the specified item",
			"minetweaker reload",
			"    Reloads the server-side script (experimental)",
			"minetweaker logdamage=on",
			"    Enables damage logging",
			"minetweaker logdamage=off",
			"    Disables damage logging",
			"minetweaker reload",
			"    Reloads the server script"
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
			MineTweakerMod.instance.reloadScripts();
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
