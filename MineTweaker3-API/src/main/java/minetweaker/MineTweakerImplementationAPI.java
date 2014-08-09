package minetweaker;

import minetweaker.api.logger.MTLogger;
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
import minetweaker.api.data.IData;
import minetweaker.api.event.IEventHandle;
import minetweaker.api.event.IPlayerInteractEventHandler;
import minetweaker.api.event.IPlayerLoggedInEventHandler;
import minetweaker.api.event.IPlayerLoggedOutEventHandler;
import minetweaker.api.event.MTEventManager;
import minetweaker.api.event.PlayerInteractEvent;
import minetweaker.api.event.PlayerLoggedInEvent;
import minetweaker.api.event.PlayerLoggedOutEvent;
import minetweaker.api.game.IGame;
import minetweaker.api.item.IItemDefinition;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidDefinition;
import minetweaker.api.mods.ILoadedMods;
import minetweaker.api.mods.IMod;
import minetweaker.api.oredict.IOreDict;
import minetweaker.api.oredict.IOreDictEntry;
import minetweaker.api.player.IPlayer;
import minetweaker.api.recipes.IFurnaceManager;
import minetweaker.api.recipes.IRecipeManager;
import minetweaker.api.server.ICommandFunction;
import minetweaker.api.server.ICommandValidator;
import minetweaker.api.server.IServer;
import minetweaker.runtime.IScriptProvider;

/**
 * The implementation API is used by API implementations for internal communication
 * and initialization.
 * 
 * @author Stan Hebben
 */
public class MineTweakerImplementationAPI {
	private static Set<IPlayer> blockInfoPlayers = new HashSet<IPlayer>();
	private static IEventHandle blockEventHandler = null;
	
	private static final Map<String, MineTweakerCommand> minetweakerCommands;
	
	private static final Comparator<IItemDefinition> ITEM_COMPARATOR = new ItemComparator();
	private static final Comparator<ILiquidDefinition> LIQUID_COMPARATOR = new LiquidComparator();
	private static final ListenPlayerLoggedIn LISTEN_LOGIN = new ListenPlayerLoggedIn();
	private static final ListenPlayerLoggedOut LISTEN_LOGOUT = new ListenPlayerLoggedOut();
	private static final ListenBlockInfo LISTEN_BLOCK_INFO = new ListenBlockInfo();
	
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
				player.sendChat(platform.getMessage("Scripts reloaded"));
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
					String displayName = "";

					try {
						displayName = " -- " + item.makeStack(0).getDisplayName();
					} catch (Exception ex) {
						// some mods (such as buildcraft) may throw exceptions when calling
						// getDisplayName on an item stack that doesn't contain valid NBT data
					}

					MineTweakerAPI.logCommand("<" + item.getId() + ">" + displayName);
				}
				
				if (player != null) {
					player.sendChat(platform.getMessage("List generated; see minetweaker.log in your minecraft dir"));
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
				System.out.println("Liquids: " + liquids.size());
				Collections.sort(liquids, LIQUID_COMPARATOR);
				for (ILiquidDefinition liquid : liquids) {
					System.out.println("Liquid " + liquid.getName());
					MineTweakerAPI.logCommand("<" + liquid.getName() + "> -- " + liquid.getDisplayName());
				}
				
				if (player != null) {
					player.sendChat(platform.getMessage("List generated; see minetweaker.log in your minecraft dir"));
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
						player.sendChat(platform.getMessage(stack.toString()));
					}
				}
			}
		}));
		
		minetweakerCommands.put("hand", new MineTweakerCommand(
				"hand",
				new String[] {
					"/minetweaker hand",
					"    Outputs the name of the item in your hand"
				}, new ICommandFunction() {
			@Override
			public void execute(String[] arguments, IPlayer player) {
				IItemStack hand = player.getCurrentItem();
				if (hand != null) {
					player.sendChat(platform.getMessage(hand.toString()));
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
						player.sendChat(platform.getMessage("Entry doesn't exist"));
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
				player.sendChat(platform.getMessage("List generated; see minetweaker.log in your minecraft dir"));
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
					player.sendChat(platform.getMessage(message));
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
					player.sendChat(platform.getMessage("missing id parameter"));
				} else {
					try {
						int id = Integer.parseInt(arguments[0]);
						IItemDefinition definition = platform.getItemDefinition(id);
						if (definition == null) {
							player.sendChat(platform.getMessage("no such item"));
						} else {
							StringBuilder description = new StringBuilder();
							description.append('<');
							description.append(definition.getId());
							description.append('>');
							player.sendChat(platform.getMessage(description.toString()));
						}
					} catch (NumberFormatException e) {
						MineTweakerAPI.logCommand("ID must be an integer");
					}
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
					player.sendChat(platform.getMessage("Block info mode deactivated."));
				} else {
					blockInfoPlayers.add(player);
					player.sendChat(platform.getMessage("Block info mode activated. Right-click a block to see its data."));
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
	 */
	public static void init(
			IOreDict oreDict,
			IRecipeManager recipes,
			IFurnaceManager furnace,
			IGame game,
			ILoadedMods mods) {
		MineTweakerAPI.oreDict = oreDict;
		MineTweakerAPI.recipes = recipes;
		MineTweakerAPI.furnace = furnace;
		MineTweakerAPI.game = game;
		MineTweakerAPI.loadedMods = mods;
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
		
		MineTweakerAPI.tweaker.rollback();
		
		if (MineTweakerAPI.server != null) {
			server.addCommand("minetweaker", "", new String[] { "mt" }, new ICommandFunction() {
				@Override
				public void execute(String[] arguments, IPlayer player) {
					if (arguments.length == 0) {
						player.sendChat(platform.getMessage("Please provide a command. Use /mt help for more info."));
					} else if (arguments[0].equals("help")) {
						String[] keys = minetweakerCommands.keySet().toArray(new String[minetweakerCommands.size()]);
						Arrays.sort(keys);
						for (String key : keys) {
							for (String helpMessage : minetweakerCommands.get(key).description) {
								player.sendChat(platform.getMessage(helpMessage));
							}
						}
					} else {
						MineTweakerCommand command = minetweakerCommands.get(arguments[0]);
						if (command == null) {
							player.sendChat(platform.getMessage("No such minetweaker command available"));
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
		
		MineTweakerAPI.tweaker.load();
		
		if (MineTweakerAPI.server != null) {
			platform.distributeScripts(MineTweakerAPI.tweaker.getScriptData());
		}
	}
	
	/**
	 * Adds a new minetweaker command. Can be called with /mt &lt;command&;gt; &lt;arguments&gt;.
	 * 
	 * @param name command name
	 * @param description description strings
	 * @param function command implementation
	 */
	public static void addMineTweakerCommand(String name, String[] description, ICommandFunction function) {
		MineTweakerAPI.apply(new AddMineTweakerCommandAction(new MineTweakerCommand(name, description, function)));
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class AddMineTweakerCommandAction implements IUndoableAction {
		private final MineTweakerCommand command;
		
		public AddMineTweakerCommandAction(MineTweakerCommand command) {
			this.command = command;
		}

		@Override
		public void apply() {
			minetweakerCommands.put(command.name, command);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			minetweakerCommands.remove(command.name);
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
	
	private static class ListenPlayerLoggedIn implements IPlayerLoggedInEventHandler {
		@Override
		public void handle(PlayerLoggedInEvent event) {
			if (MineTweakerAPI.server != null && MineTweakerAPI.server.isOp(event.getPlayer())) {
				logger.addPlayer(event.getPlayer());
			}
		}
	}
	
	private static class ListenPlayerLoggedOut implements IPlayerLoggedOutEventHandler {
		@Override
		public void handle(PlayerLoggedOutEvent event) {
			logger.removePlayer(event.getPlayer());
		}
	}
	
	private static class ListenBlockInfo implements IPlayerInteractEventHandler {
		@Override
		public void handle(PlayerInteractEvent event) {
			if (blockInfoPlayers.contains(event.getPlayer())) {
				IBlock block = event.getBlock();
				event.getPlayer().sendChat(platform.getMessage("Block ID: " + block.getDefinition().getId()));
				event.getPlayer().sendChat(platform.getMessage("Meta value: " + block.getMeta()));
				IData data = block.getTileData();
				if (data != null) {
					event.getPlayer().sendChat(platform.getMessage("Tile entity data: " + data.asString()));
				}
			}
		}
	}
}
