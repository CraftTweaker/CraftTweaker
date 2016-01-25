package minetweaker;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static minetweaker.MineTweakerAPI.server;
import minetweaker.api.block.IBlock;
import minetweaker.api.block.IBlockDefinition;
import minetweaker.api.data.IData;
import minetweaker.api.entity.IEntityDefinition;
import minetweaker.api.event.IEventHandle;
import minetweaker.api.event.MTEventManager;
import minetweaker.api.event.PlayerInteractEvent;
import minetweaker.api.event.PlayerLoggedInEvent;
import minetweaker.api.event.PlayerLoggedOutEvent;
import minetweaker.api.formatting.IFormatter;
import minetweaker.api.game.IGame;
import minetweaker.api.item.IItemDefinition;
import minetweaker.api.item.IItemStack;
import minetweaker.api.item.WeightedItemStack;
import minetweaker.api.liquid.ILiquidDefinition;
import minetweaker.api.logger.MTLogger;
import minetweaker.api.mods.ILoadedMods;
import minetweaker.api.mods.IMod;
import minetweaker.api.oredict.IOreDict;
import minetweaker.api.oredict.IOreDictEntry;
import minetweaker.api.player.IPlayer;
import minetweaker.api.recipes.ICraftingRecipe;
import minetweaker.api.recipes.IFurnaceManager;
import minetweaker.api.recipes.IRecipeManager;
import minetweaker.api.recipes.ShapedRecipe;
import minetweaker.api.recipes.ShapelessRecipe;
import minetweaker.api.server.ICommandFunction;
import minetweaker.api.server.ICommandValidator;
import minetweaker.api.server.IServer;
import minetweaker.api.vanilla.IVanilla;
import minetweaker.api.vanilla.LootEntry;
import minetweaker.api.world.IBiome;
import minetweaker.runtime.IScriptProvider;
import minetweaker.util.EventList;
import minetweaker.util.IEventHandler;

/**
 * The implementation API is used by API implementations for internal
 * communication and initialization.
 * 
 * @author Stan Hebben
 */
public class MineTweakerImplementationAPI {
	private static Set<IPlayer> blockInfoPlayers = new HashSet<IPlayer>();
	private static IEventHandle blockEventHandler = null;

	private static final Map<String, MineTweakerCommand> minetweakerCommands;

	private static final Comparator<IItemDefinition> ITEM_COMPARATOR = new ItemComparator();
	private static final Comparator<ILiquidDefinition> LIQUID_COMPARATOR = new LiquidComparator();
	private static final Comparator<IBlockDefinition> BLOCK_COMPARATOR = new BlockComparator();
	private static final Comparator<IEntityDefinition> ENTITY_COMPARATOR = new EntityComparator();

	private static final ListenPlayerLoggedIn LISTEN_LOGIN = new ListenPlayerLoggedIn();
	private static final ListenPlayerLoggedOut LISTEN_LOGOUT = new ListenPlayerLoggedOut();
	private static final ListenBlockInfo LISTEN_BLOCK_INFO = new ListenBlockInfo();
	private static final EventList<ReloadEvent> ONRELOAD = new EventList<ReloadEvent>();
	private static final EventList<ReloadEvent> ONPOSTRELOAD = new EventList<ReloadEvent>();

	static {
		minetweakerCommands = new HashMap<String, MineTweakerCommand>();

		minetweakerCommands.put("reload", new MineTweakerCommand(
				"reload",
				new String[] {
						"/minetweaker reload",
						"    Reloads all scripts"
				}, new ICommandFunction() {
					@Override
					public void execute(String[] arguments, IPlayer player) {
						reload();
						player.sendChat("Scripts reloaded");
					}
				}));

		minetweakerCommands.put("names", new MineTweakerCommand(
				"names",
				new String[] {
						"/minetweaker names",
						"    Outputs a list of all item names in the game to the minetweaker log"
				}, new ICommandFunction() {
					@Override
					public void execute(String[] arguments, IPlayer player) {
						List<IItemDefinition> items = MineTweakerAPI.game.getItems();
						Collections.sort(items, ITEM_COMPARATOR);
						for (IItemDefinition item : items) {
							String displayName;

							try {
								displayName = ", " + item.makeStack(0).getDisplayName();
							} catch (Throwable ex) {
								// some mods (such as buildcraft) may throw
								// exceptions when calling
								// getDisplayName on an item stack that doesn't
								// contain valid NBT data
								// also seems to cause errors in some other
								// cases too
								displayName = " -- Name could not be retrieved due to an error: " + ex;
							}

							MineTweakerAPI.logCommand("<" + item.getId() + ">" + displayName);
						}

						if (player != null) {
							player.sendChat("List generated; see minetweaker.log in your minecraft dir");
						}
					}
				}));

		minetweakerCommands.put("liquids", new MineTweakerCommand(
				"liquids",
				new String[] {
						"/minetweaker liquids",
						"    Outputs a list of all liquid names in the game to the minetweaker log"
				}, new ICommandFunction() {
					@Override
					public void execute(String[] arguments, IPlayer player) {
						List<ILiquidDefinition> liquids = MineTweakerAPI.game.getLiquids();
						Collections.sort(liquids, LIQUID_COMPARATOR);

						MineTweakerAPI.logCommand("Liquids:");
						for (ILiquidDefinition liquid : liquids) {
							MineTweakerAPI.logCommand("<liquid:" + liquid.getName() + ">, " + liquid.getDisplayName());
						}

						if (player != null) {
							player.sendChat("List generated; see minetweaker.log in your minecraft dir");
						}
					}
				}));

		minetweakerCommands.put("blocks", new MineTweakerCommand(
				"blocks",
				new String[] {
						"/minetweaker blocks",
						"    Outputs a list of all blocks in the game to the minetweaker log"
				}, new ICommandFunction() {
					@Override
					public void execute(String[] arguments, IPlayer player) {
						List<IBlockDefinition> blocks = MineTweakerAPI.game.getBlocks();
						Collections.sort(blocks, BLOCK_COMPARATOR);

						MineTweakerAPI.logCommand("Blocks:");
						for (IBlockDefinition block : blocks) {
							MineTweakerAPI.logCommand("<block:" + block.getId() + ">, " + block.getDisplayName());
						}

						if (player != null) {
							player.sendChat("List generated; see minetweaker.log in your minecraft dir");
						}
					}
				}));

		minetweakerCommands.put("entities", new MineTweakerCommand(
				"entities",
				new String[] {
						"/minetweaker entities",
						"    Outputs a list of all entity definitions in the game to the minetweaker log"
				}, new ICommandFunction() {
					@Override
					public void execute(String[] arguments, IPlayer player) {
						List<IEntityDefinition> entities = MineTweakerAPI.game.getEntities();
						Collections.sort(entities, ENTITY_COMPARATOR);

						MineTweakerAPI.logCommand("Entities:");
						for (IEntityDefinition entity : entities) {
							MineTweakerAPI.logCommand(entity.getId() + " -- " + entity.getName());
						}

						if (player != null) {
							player.sendChat("List generated; see minetweaker.log in your minecraft dir");
						}
					}
				}));

		minetweakerCommands.put("recipes", new MineTweakerCommand(
				"recipes",
				new String[] {
						"/minetweaker recipes",
						"   Lists all crafting recipes in the game",
						"/minetweaker recipes hand",
						"   Lists all crafting recipes for the item in your hand",
						"   Also copies the recipes to clipboard"
				}, new ICommandFunction() {
					@Override
					public void execute(String[] arguments, IPlayer player)
					{
						if (arguments.length == 0) {
							if (player != null) {
								player.sendChat("Generating recipe list, this could take a while...");
							}

							MineTweakerAPI.logCommand("Recipes:");
							for (ICraftingRecipe recipe : MineTweakerAPI.recipes.getAll()) {
								try {
									MineTweakerAPI.logCommand(recipe.toCommandString());
								} catch (Throwable ex) {
									// Some recipes can result in an NPE :-(
									if (recipe instanceof ShapedRecipe) {
										ShapedRecipe shaped = (ShapedRecipe) recipe;
										IItemStack out = shaped.getOutput();
										MineTweakerAPI.logError("Could not dump recipe for " + out, ex);
									} else if (recipe instanceof ShapelessRecipe) {
										ShapelessRecipe shapeless = (ShapelessRecipe) recipe;
										IItemStack out = shapeless.getOutput();
										MineTweakerAPI.logError("Could not dump recipe for " + out, ex);
									} else {
										MineTweakerAPI.logError("Could not dump recipe", ex)
									}
								}
							}

							if (player != null) {
								player.sendChat("Recipe list generated; see minetweaker.log in your minecraft dir");
							}
						} else if (arguments[0].equals("hand") && player != null) {
							IItemStack item = player.getCurrentItem();
							if (item != null) {
								List<ICraftingRecipe> recipes = MineTweakerAPI.recipes.getRecipesFor(item.anyAmount());
								if (recipes.isEmpty()) {
									player.sendChat("No crafting recipes found for that item");
								} else {
									StringBuilder recipesString = new StringBuilder();

									for (ICraftingRecipe recipe : recipes) {
										MineTweakerAPI.logCommand(recipe.toCommandString());
										player.sendChat(recipe.toCommandString());
										recipesString.append(recipe.toCommandString()).append("\n");
									}

									copyToClipboard(recipesString.toString());
								}
							} else {
								player.sendChat("No item was found");
							}
						} else {
							if (player != null) {
								player.sendChat("Invalid arguments for recipes command");
							}
						}
					}
				}));

		minetweakerCommands.put("inventory", new MineTweakerCommand(
				"inventory",
				new String[] {
						"/minetweaker inventory",
						"    Lists all items in your inventory"
				}, new ICommandFunction() {
					@Override
					public void execute(String[] arguments, IPlayer player) {
						for (int i = 0; i < player.getInventorySize(); i++) {
							IItemStack stack = player.getInventoryStack(i);
							if (stack != null) {
								player.sendChat(stack.toString());
							}
						}
					}
				}));

		minetweakerCommands.put("hand", new MineTweakerCommand(
				"hand",
				new String[] {
						"/minetweaker hand",
						"    Outputs the name of the item in your hand",
						"    Also copies the name to clipboard and prints",
						"    oredict entries"
				}, new ICommandFunction() {
					@Override
					public void execute(String[] arguments, IPlayer player) {
						IItemStack hand = player.getCurrentItem();
						if (hand != null) {
							String value = hand.toString();
							player.sendChat(value);
							copyToClipboard(value);

							List<IOreDictEntry> entries = hand.getOres();
							for (IOreDictEntry entry : entries) {
								player.sendChat("Is in <ore:" + entry.getName() + ">");
							}
						}
					}
				}));

		minetweakerCommands.put("oredict", new MineTweakerCommand(
				"oredict",
				new String[] {
						"/minetweaker oredict",
						"    Outputs all ore dictionary entries in the game to the minetweaker log",
						"/minetweaker oredict <name>",
						"    Outputs all items in the given ore dictionary entry to the minetweaker log"
				}, new ICommandFunction() {
					@Override
					public void execute(String[] arguments, IPlayer player) {
						if (arguments.length > 0) {
							String entryName = arguments[0];
							IOreDictEntry entry = MineTweakerAPI.oreDict.get(entryName);
							if (entry.isEmpty()) {
								player.sendChat("Entry doesn't exist");
								return;
							} else {
								MineTweakerAPI.logCommand("Ore entries for " + entryName + ":");
								for (IItemStack ore : entry.getItems()) {
									MineTweakerAPI.logCommand("    " + ore);
								}
							}
						} else {
							for (IOreDictEntry entry : MineTweakerAPI.oreDict.getEntries()) {
								if (!entry.isEmpty()) {
									MineTweakerAPI.logCommand("Ore entries for <ore:" + entry.getName() + "> :");
									for (IItemStack ore : entry.getItems()) {
										MineTweakerAPI.logCommand("    " + ore);
									}
								}
							}
						}
						player.sendChat("List generated; see minetweaker.log in your minecraft dir");
					}
				}));

		minetweakerCommands.put("mods", new MineTweakerCommand(
				"mods",
				new String[] {
						"/minetweaker mods",
						"    Outputs all active mod IDs and versions in the game"
				}, new ICommandFunction() {
					@Override
					public void execute(String[] arguments, IPlayer player) {
						MineTweakerAPI.logCommand("Mods list:");
						for (IMod mod : MineTweakerAPI.loadedMods) {
							String message = mod.getId() + " - " + mod.getName() + " - " + mod.getVersion();
							player.sendChat(message);
							MineTweakerAPI.logCommand("Mod: " + message);
						}
					}
				}));

		minetweakerCommands.put("name", new MineTweakerCommand(
				"name",
				new String[] {
						"/minetweaker name <id>",
						"    Outputs the name for the given item ID",
				},
				new ICommandFunction() {
					@Override
					public void execute(String[] arguments, IPlayer player) {
						if (arguments.length < 1) {
							player.sendChat("missing id parameter");
						} else {
							try {
								int id = Integer.parseInt(arguments[0]);
								IItemDefinition definition = platform.getItemDefinition(id);
								if (definition == null) {
									player.sendChat("no such item");
								} else {
									StringBuilder description = new StringBuilder();
									description.append('<');
									description.append(definition.getId());
									description.append('>');
									player.sendChat(description.toString());
								}
							} catch (NumberFormatException e) {
								MineTweakerAPI.logCommand("ID must be an integer");
							}
						}
					}
				}));

		minetweakerCommands.put("seeds", new MineTweakerCommand(
				"seeds",
				new String[] {
						"/minetweaker seeds",
						"    Prints all seeds registered",
						"    for tall grass"
				}, new ICommandFunction() {
					@Override
					public void execute(String[] arguments, IPlayer player) {
						MineTweakerAPI.logCommand("Seeds:");
						for (WeightedItemStack seed : MineTweakerAPI.vanilla.getSeeds().getSeeds()) {
							String message = seed.getStack() + " - " + (int) seed.getChance();
							player.sendChat(message);
							MineTweakerAPI.logCommand("Seed: " + message);
						}
					}
				}));

		minetweakerCommands.put("loot", new MineTweakerCommand(
				"seeds",
				new String[] {
						"/minetweaker seeds",
						"    Prints all seeds registered",
						"    for tall grass"
				}, new ICommandFunction() {
					@Override
					public void execute(String[] arguments, IPlayer player) {
						if (arguments.length == 0) {
							MineTweakerAPI.logCommand("Loot chest contents:");
							List<String> types = MineTweakerAPI.vanilla.getLoot().getLootTypes();
							Collections.sort(types);
							for (String lootType : types) {
								MineTweakerAPI.logCommand("Loot type: " + lootType);

								List<LootEntry> entries = MineTweakerAPI.vanilla.getLoot().getLoot(lootType);
								for (LootEntry entry : entries) {
									MineTweakerAPI.logCommand("    " + entry.toString());
								}
							}

							player.sendChat("List generated; see minetweaker.log in your minecraft dir");
						} else {
							MineTweakerAPI.logCommand("Loot for type: " + arguments[0]);

							List<LootEntry> entries = MineTweakerAPI.vanilla.getLoot().getLoot(arguments[0]);
							for (LootEntry entry : entries) {
								MineTweakerAPI.logCommand("    " + entry.toString());
							}

							player.sendChat("List generated; see minetweaker.log in your minecraft dir");
						}
					}
				}));

		minetweakerCommands.put("wiki", new MineTweakerCommand(
				"wiki",
				new String[] {
						"/minetweaker wiki",
						"    Opens your browser with the wiki"
				}, new ICommandFunction() {
					@Override
					public void execute(String[] arguments, IPlayer player) {
						player.openBrowser("http://minetweaker3.powerofbytes.com/wiki/");
					}
				}));

		minetweakerCommands.put("bugs", new MineTweakerCommand(
				"bugs",
				new String[] {
						"/minetweaker bugs",
						"    Opens your browser with the GitHub bug tracker"
				}, new ICommandFunction() {
					@Override
					public void execute(String[] arguments, IPlayer player) {
						player.openBrowser("https://github.com/stanhebben/MineTweaker3/issues");
					}
				}));

		minetweakerCommands.put("forum", new MineTweakerCommand(
				"forum",
				new String[] {
						"/minetweaker forum",
						"    Opens your browser with the forum"
				}, new ICommandFunction() {
					@Override
					public void execute(String[] arguments, IPlayer player) {
						player.openBrowser("http://minetweaker3.powerofbytes.com/forum");
					}
				}));

		minetweakerCommands.put("biomes", new MineTweakerCommand(
				"biomes",
				new String[] {
						"/minetweaker biomes",
						"    Lists all the biomes in the game"
				}, new ICommandFunction() {
					@Override
					public void execute(String[] arguments, IPlayer player) {
						MineTweakerAPI.logCommand("Biomes:");

						for (IBiome biome : MineTweakerAPI.game.getBiomes()) {
							MineTweakerAPI.logCommand("    " + biome.getName());
						}

						player.sendChat("Biome list generated; see minetweaker.log in your minecraft dir");
					}
				}));

		minetweakerCommands.put("blockinfo", new MineTweakerCommand(
				"blockinfo",
				new String[] {
						"/minetweaker blockinfo",
						"   Activates or deactivates block reader. In block info mode,",
						"   right-click a block to see ID, meta and tile entity data"
				}, new ICommandFunction() {
					@Override
					public void execute(String[] arguments, IPlayer player) {
						if (blockInfoPlayers.isEmpty()) {
							blockEventHandler = events.onPlayerInteract(LISTEN_BLOCK_INFO);
						}

						if (blockInfoPlayers.contains(player)) {
							blockInfoPlayers.remove(player);
							player.sendChat("Block info mode deactivated.");
						} else {
							blockInfoPlayers.add(player);
							player.sendChat("Block info mode activated. Right-click a block to see its data.");
						}

						if (blockInfoPlayers.isEmpty()) {
							blockEventHandler.close();
						}
					}
				}));
	}

	/**
	 * Access point to the event handler implementation.
	 */
	public static final MTEventManager events = new MTEventManager();

	/**
	 * Access point to the internal logger instance.
	 */
	public static final MTLogger logger = new MTLogger();

	/**
	 * Access point to general platform functions.
	 */
	public static IPlatformFunctions platform = null;

	/**
	 * Initializes the MineTweaker API.
	 * 
	 * @param oreDict ore dictionary interface
	 * @param recipes recipe manager interface
	 * @param furnace furnace manager interface
	 * @param game game interface
	 * @param mods mods interface
	 * @param formatter formatter interface
	 * @param vanilla vanilla interface
	 */
	public static void init(
			IOreDict oreDict,
			IRecipeManager recipes,
			IFurnaceManager furnace,
			IGame game,
			ILoadedMods mods,
			IFormatter formatter,
			IVanilla vanilla) {
		MineTweakerAPI.oreDict = oreDict;
		MineTweakerAPI.recipes = recipes;
		MineTweakerAPI.furnace = furnace;
		MineTweakerAPI.game = game;
		MineTweakerAPI.loadedMods = mods;
		MineTweakerAPI.format = formatter;
		MineTweakerAPI.vanilla = vanilla;
	}

	/**
	 * Register an event handler to be fired upon reload.
	 * 
	 * @param handler
	 * @return
	 */
	public static IEventHandle onReloadEvent(IEventHandler<ReloadEvent> handler) {
		return ONRELOAD.add(handler);
	}
	
	public static IEventHandle onPostReload(IEventHandler<ReloadEvent> handler) {
		return ONPOSTRELOAD.add(handler);
	}

	/**
	 * Must be called upon server start.
	 * 
	 * @param server server interface
	 */
	public static void onServerStart(IServer server) {
		MineTweakerAPI.server = server;
		reload();
	}

	/**
	 * Must be called upon server stop.
	 */
	public static void onServerStop() {
		MineTweakerAPI.server = null;
	}

	/**
	 * Sets the script provider.
	 * 
	 * @param provider script provider
	 */
	public static void setScriptProvider(IScriptProvider provider) {
		MineTweakerAPI.tweaker.setScriptProvider(provider);
	}

	/**
	 * Called to reload scripts. Must be called after setting a new script
	 * provider in order to reload scripts.
	 */
	public static void reload() {
		blockInfoPlayers.clear();

		logger.clear();
		events.clear();

		if (MineTweakerAPI.server != null) {
			events.onPlayerLoggedIn(LISTEN_LOGIN);
			events.onPlayerLoggedOut(LISTEN_LOGOUT);
		}
//
//		byte[] currentScript = MineTweakerAPI.tweaker.getScriptData();
//		if (currentScript != null) {
//			System.out.println("Already loaded a script before");
//
//			// alread loaded a script
//			byte[] stagedScript = MineTweakerAPI.tweaker.getStagedScriptData();
//
//			if (Arrays.equals(currentScript, stagedScript)) {
//				System.out.println("No reload needed");
//				return; // no reload necessary
//			}
//
//			if (MineTweakerAPI.game.isLocked()) {
//				System.out.println("Reload blocked");
//				MineTweakerAPI.game.signalLockError();
//				return;
//			}
//		} else {	
//			System.out.println("First time loading a script, go ahead");
//		}

		MineTweakerAPI.tweaker.rollback();

		if (MineTweakerAPI.server != null) {
			server.addCommand("minetweaker", "", new String[] { "mt" }, new ICommandFunction() {
				@Override
				public void execute(String[] arguments, IPlayer player) {
					if (arguments.length == 0) {
						player.sendChat("Please provide a command. Use /mt help for more info.");
					} else if (arguments[0].equals("help")) {
						String[] keys = minetweakerCommands.keySet().toArray(new String[minetweakerCommands.size()]);
						Arrays.sort(keys);
						for (String key : keys) {
							for (String helpMessage : minetweakerCommands.get(key).description) {
								player.sendChat(helpMessage);
							}
						}
					} else {
						MineTweakerCommand command = minetweakerCommands.get(arguments[0]);
						if (command == null) {
							player.sendChat("No such minetweaker command available");
						} else {
							command.function.execute(Arrays.copyOfRange(arguments, 1, arguments.length), player);
						}
					}
				}
			}, new ICommandValidator() {
				@Override
				public boolean canExecute(IPlayer player) {
					return server.isOp(player);
				}
			}, null);
		}

		ONRELOAD.publish(new ReloadEvent());

		MineTweakerAPI.tweaker.load();

		if (MineTweakerAPI.server != null) {
			platform.distributeScripts(MineTweakerAPI.tweaker.getScriptData());
		}
		
		ONPOSTRELOAD.publish(new ReloadEvent());
	}

	/**
	 * Adds a new minetweaker command. Can be called with /mt &lt;command&;gt;
	 * &lt;arguments&gt;.
	 * 
	 * @param name command name
	 * @param description description strings
	 * @param function command implementation
	 */
	public static void addMineTweakerCommand(String name, String[] description, ICommandFunction function) {
		MineTweakerAPI.apply(new AddMineTweakerCommandAction(new MineTweakerCommand(name, description, function)));
	}

	// ##############################
	// ### Private static methods ###
	// ##############################

	private static void copyToClipboard(String value) {
		StringSelection stringSelection = new StringSelection(value);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
	}

	// ############################
	// ### Public inner classes ###
	// ############################

	public static class ReloadEvent {

	}

	// ######################
	// ### Action classes ###
	// ######################

	private static class AddMineTweakerCommandAction implements IUndoableAction {
		private final MineTweakerCommand command;
		private boolean added;

		public AddMineTweakerCommandAction(MineTweakerCommand command) {
			this.command = command;
		}

		@Override
		public void apply() {
			if (!minetweakerCommands.containsKey(command.name)) {
				minetweakerCommands.put(command.name, command);
				added = true;
			} else {
				added = false;
			}
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			if (added) {
				minetweakerCommands.remove(command.name);
			}
		}

		@Override
		public String describe() {
			return "Adding minetweaker command " + command.name;
		}

		@Override
		public String describeUndo() {
			return "Removing minetweaker command " + command.name;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}

	// #############################
	// ### Private inner classes ###
	// #############################

	private static class MineTweakerCommand {
		private final String name;
		private final String[] description;
		private final ICommandFunction function;

		public MineTweakerCommand(String name, String[] description, ICommandFunction function) {
			this.name = name;
			this.description = description;
			this.function = function;
		}
	}

	private static class ItemComparator implements Comparator<IItemDefinition> {
		@Override
		public int compare(IItemDefinition o1, IItemDefinition o2) {
			return o1.getId().compareTo(o2.getId());
		}
	}

	private static class LiquidComparator implements Comparator<ILiquidDefinition> {
		@Override
		public int compare(ILiquidDefinition o1, ILiquidDefinition o2) {
			return o1.getName().compareTo(o2.getName());
		}
	}

	private static class BlockComparator implements Comparator<IBlockDefinition> {
		@Override
		public int compare(IBlockDefinition o1, IBlockDefinition o2) {
			return o1.getId().compareTo(o2.getId());
		}
	}

	private static class EntityComparator implements Comparator<IEntityDefinition> {
		@Override
		public int compare(IEntityDefinition o1, IEntityDefinition o2) {
			return o1.getId().compareTo(o2.getId());
		}
	}

	private static class ListenPlayerLoggedIn implements IEventHandler<PlayerLoggedInEvent> {
		@Override
		public void handle(PlayerLoggedInEvent event) {
			if (MineTweakerAPI.server != null && MineTweakerAPI.server.isOp(event.getPlayer())) {
				logger.addPlayer(event.getPlayer());
			}
		}
	}

	private static class ListenPlayerLoggedOut implements IEventHandler<PlayerLoggedOutEvent> {
		@Override
		public void handle(PlayerLoggedOutEvent event) {
			logger.removePlayer(event.getPlayer());
		}
	}

	private static class ListenBlockInfo implements IEventHandler<PlayerInteractEvent> {
		@Override
		public void handle(PlayerInteractEvent event) {
			if (blockInfoPlayers.contains(event.getPlayer())) {
				IBlock block = event.getBlock();
				event.getPlayer().sendChat("Block ID: " + block.getDefinition().getId());
				event.getPlayer().sendChat("Meta value: " + block.getMeta());
				IData data = block.getTileData();
				if (data != null) {
					event.getPlayer().sendChat("Tile entity data: " + data.asString());
				}
			}
		}
	}
}
