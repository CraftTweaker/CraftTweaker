package crafttweaker;

import crafttweaker.api.block.*;
import crafttweaker.api.data.IData;
import crafttweaker.api.entity.IEntityDefinition;
import crafttweaker.api.event.*;
import crafttweaker.api.formatting.IFormatter;
import crafttweaker.api.game.IGame;
import crafttweaker.api.item.*;
import crafttweaker.api.liquid.ILiquidDefinition;
import crafttweaker.api.logger.MTLogger;
import crafttweaker.api.mods.*;
import crafttweaker.api.oredict.*;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.recipes.*;
import crafttweaker.api.server.*;
import crafttweaker.api.vanilla.IVanilla;
import crafttweaker.api.world.IBiome;
import crafttweaker.runtime.IScriptProvider;
import crafttweaker.util.IEventHandler;
import sun.awt.HeadlessToolkit;

import java.awt.*;
import java.awt.datatransfer.*;
import java.util.*;
import java.util.List;

import static crafttweaker.CraftTweakerAPI.*;


/**
 * The implementation API is used by API implementations for internal
 * communication and initialization.
 *
 * @author Stan Hebben
 */
public class CrafttweakerImplementationAPI {
    
    /**
     * Access point to the event handler implementation.
     */
    public static final MTEventManager events = new MTEventManager();
    /**
     * Access point to the internal logger instance.
     */
    public static final MTLogger logger = new MTLogger();
    private static final Map<String, CraftTweakerCommand> crafttweakerCommands;
    
    private static final Comparator<IItemDefinition> ITEM_COMPARATOR = new ItemComparator();
    private static final Comparator<ILiquidDefinition> LIQUID_COMPARATOR = new LiquidComparator();
    private static final Comparator<IBlockDefinition> BLOCK_COMPARATOR = new BlockComparator();
    private static final Comparator<IEntityDefinition> ENTITY_COMPARATOR = new EntityComparator();
    
    private static final ListenPlayerLoggedIn LISTEN_LOGIN = new ListenPlayerLoggedIn();
    private static final ListenPlayerLoggedOut LISTEN_LOGOUT = new ListenPlayerLoggedOut();
    private static final ListenBlockInfo LISTEN_BLOCK_INFO = new ListenBlockInfo();
    /**
     * Access point to general platform functions.
     */
    public static IPlatformFunctions platform = null;
    private static Set<IPlayer> blockInfoPlayers = new HashSet<>();
    private static IEventHandle blockEventHandler = null;
    static {
        crafttweakerCommands = new TreeMap<>();
        
        crafttweakerCommands.put("names", new CraftTweakerCommand("names", new String[]{"/crafttweaker names", "§a-§rOutputs a list of all item names in the game to the crafttweaker log"}, (arguments, player) -> {
            List<IItemDefinition> items = CraftTweakerAPI.game.getItems();
            items.sort(ITEM_COMPARATOR);
            for(IItemDefinition item : items) {
                String displayName;
                
                try {
                    displayName = ", " + item.makeStack(0).getDisplayName();
                } catch(Throwable ex) {
                    displayName = " -- Name could not be retrieved due to an error: " + ex;
                }
                
                CraftTweakerAPI.logCommand("<" + item.getId() + ">" + displayName);
            }
            
            if(player != null) {
                player.sendChat("List generated; see crafttweaker.log in your minecraft dir");
            }
        }));
        
        crafttweakerCommands.put("liquids", new CraftTweakerCommand("liquids", new String[]{"/crafttweaker liquids", "§a-§rOutputs a list of all liquid names in the game to the crafttweaker log"}, (arguments, player) -> {
            List<ILiquidDefinition> liquids = CraftTweakerAPI.game.getLiquids();
            liquids.sort(LIQUID_COMPARATOR);
            
            CraftTweakerAPI.logCommand("Liquids:");
            for(ILiquidDefinition liquid : liquids) {
                CraftTweakerAPI.logCommand("<liquid:" + liquid.getName() + ">, " + liquid.getDisplayName());
            }
            
            if(player != null) {
                player.sendChat("List generated; see crafttweaker.log in your minecraft dir");
            }
        }));
        
        crafttweakerCommands.put("blocks", new CraftTweakerCommand("blocks", new String[]{"/crafttweaker blocks", "§a-§rOutputs a list of all blocks in the game to the crafttweaker log"}, (arguments, player) -> {
            List<IBlockDefinition> blocks = CraftTweakerAPI.game.getBlocks();
            blocks.sort(BLOCK_COMPARATOR);
            
            CraftTweakerAPI.logCommand("Blocks:");
            for(IBlockDefinition block : blocks) {
                CraftTweakerAPI.logCommand("<block:" + block.getId() + ">, " + block.getDisplayName());
            }
            
            if(player != null) {
                player.sendChat("List generated; see crafttweaker.log in your minecraft dir");
            }
        }));
        
        crafttweakerCommands.put("entities", new CraftTweakerCommand("entities", new String[]{"/crafttweaker entities", "§a-§rOutputs a list of all entity definitions in the game to the crafttweaker log"}, (arguments, player) -> {
            List<IEntityDefinition> entities = CraftTweakerAPI.game.getEntities();
            entities.sort(ENTITY_COMPARATOR);
            
            CraftTweakerAPI.logCommand("Entities:");
            for(IEntityDefinition entity : entities) {
                CraftTweakerAPI.logCommand(entity.getId() + " -- " + entity.getName());
            }
            
            if(player != null) {
                player.sendChat("List generated; see crafttweaker.log in your minecraft dir");
            }
        }));
        
        crafttweakerCommands.put("recipes", new CraftTweakerCommand("recipes", new String[]{"/crafttweaker recipes", "§a-§rLists all crafting recipes in the game", "/crafttweaker recipes hand", "§a-§rLists all crafting recipes for the item in your hand", "§a-§rAlso copies the recipes to clipboard", "/crafttweaker recipes furnace", "§a-§rlists all furnace recipes in the game"}, (arguments, player) -> {
            if(arguments.length == 0) {
                if(player != null) {
                    player.sendChat("Generating recipe list, this could take a while...");
                }
                
                CraftTweakerAPI.logCommand("Recipes:");
                for(ICraftingRecipe recipe : CraftTweakerAPI.recipes.getAll()) {
                    try {
                        CraftTweakerAPI.logCommand(recipe.toCommandString());
                    } catch(Throwable ex) {
                        if(recipe instanceof ShapedRecipe) {
                            ShapedRecipe shaped = (ShapedRecipe) recipe;
                            IItemStack out = shaped.getOutput();
                            CraftTweakerAPI.logError("Could not dump recipe for " + out, ex);
                        } else if(recipe instanceof ShapelessRecipe) {
                            ShapelessRecipe shapeless = (ShapelessRecipe) recipe;
                            IItemStack out = shapeless.getOutput();
                            CraftTweakerAPI.logError("Could not dump recipe for " + out, ex);
                        } else {
                            CraftTweakerAPI.logError("Could not dump recipe", ex);
                        }
                    }
                }
                
                if(player != null) {
                    player.sendChat("Recipe list generated; see crafttweaker.log in your minecraft dir");
                }
            } else if(arguments[0].equals("hand") && player != null) {
                IItemStack item = player.getCurrentItem();
                if(item != null) {
                    List<ICraftingRecipe> recipes = CraftTweakerAPI.recipes.getRecipesFor(item.anyAmount());
                    if(recipes.isEmpty()) {
                        player.sendChat("No crafting recipes found for that item");
                    } else {
                        StringBuilder recipesString = new StringBuilder();
                        
                        for(ICraftingRecipe recipe : recipes) {
                            CraftTweakerAPI.logCommand(recipe.toCommandString());
                            player.sendChat(recipe.toCommandString());
                            recipesString.append(recipe.toCommandString()).append("\n");
                        }
                        
                        copyToClipboard(recipesString.toString());
                    }
                } else {
                    player.sendChat("No item was found");
                }
            } else if(arguments[0].equals("furnace") && player != null) {
                player.sendChat("Generating furnace list, this could take a while...");
                
                CraftTweakerAPI.logCommand("Furnace Recipes:");
                for(IFurnaceRecipe recipe : furnace.getAll()) {
                    try {
                        CraftTweakerAPI.logCommand(recipe.toCommandString());
                    } catch(Throwable ex) {
                        CraftTweakerAPI.logError("Could not dump furnace recipe", ex);
                    }
                }
                
                player.sendChat("Furnace Recipe list generated; see crafttweaker.log in your minecraft dir");
            } else {
                if(player != null) {
                    player.sendChat("Invalid arguments for recipes command");
                }
            }
        }));
        
        crafttweakerCommands.put("inventory", new CraftTweakerCommand("inventory", new String[]{"/crafttweaker inventory", "§a-§rLists all items in your inventory"}, (arguments, player) -> {
            for(int i = 0; i < player.getInventorySize(); i++) {
                IItemStack stack = player.getInventoryStack(i);
                if(stack != null) {
                    CraftTweakerAPI.logCommand(stack.toString());
                    player.sendChat("Recipe list generated; see crafttweaker.log in your minecraft dir");
                }
            }
        }));
        
        crafttweakerCommands.put("hand", new CraftTweakerCommand("hand", new String[]{"/crafttweaker hand", "§a-§rOutputs the name of the item in your hand", "§a-§rAlso copies the name to clipboard and prints", "§a-§roredict entries"}, (arguments, player) -> {
            IItemStack hand = player.getCurrentItem();
            if(hand != null) {
                String value = hand.toString();
                player.sendChat(value);
                CraftTweakerAPI.logCommand(value);
                copyToClipboard(value);
                
                List<IOreDictEntry> entries = hand.getOres();
                for(IOreDictEntry entry : entries) {
                    player.sendChat("Is in <ore:" + entry.getName() + ">");
                    CraftTweakerAPI.logCommand("Is in <ore:" + entry.getName() + ">");
                    
                }
                
            }
        }));
        
        crafttweakerCommands.put("oredict", new CraftTweakerCommand("oredict", new String[]{"/crafttweaker oredict", "§a-§rOutputs all ore dictionary entries in the game to the crafttweaker log", "/crafttweaker oredict <name>", "§a-§rOutputs all items in the given ore dictionary entry to the crafttweaker log"}, (arguments, player) -> {
            if(arguments.length > 0) {
                String entryName = arguments[0];
                IOreDictEntry entry = CraftTweakerAPI.oreDict.get(entryName);
                if(entry.isEmpty()) {
                    player.sendChat("Entry doesn't exist");
                    return;
                } else {
                    CraftTweakerAPI.logCommand("Ore entries for " + entryName + ":");
                    for(IItemStack ore : entry.getItems()) {
                        CraftTweakerAPI.logCommand("§a-§r" + ore);
                    }
                }
            } else {
                for(IOreDictEntry entry : CraftTweakerAPI.oreDict.getEntries()) {
                    if(!entry.isEmpty()) {
                        CraftTweakerAPI.logCommand("Ore entries for <ore:" + entry.getName() + "> :");
                        for(IItemStack ore : entry.getItems()) {
                            CraftTweakerAPI.logCommand("§a-§r" + ore);
                        }
                    }
                }
            }
            player.sendChat("List generated; see crafttweaker.log in your minecraft dir");
        }));
        
        crafttweakerCommands.put("mods", new CraftTweakerCommand("mods", new String[]{"/crafttweaker mods", "§a-§rOutputs all active mod IDs and versions in the game"}, (arguments, player) -> {
            CraftTweakerAPI.logCommand("Mods list:");
            for(IMod mod : CraftTweakerAPI.loadedMods) {
                String message = mod.getId() + " - " + mod.getName() + " - " + mod.getVersion();
                player.sendChat(message);
                CraftTweakerAPI.logCommand("Mod: " + message);
            }
        }));
        
        crafttweakerCommands.put("seeds", new CraftTweakerCommand("seeds", new String[]{"/crafttweaker seeds", "§a-§rPrints all seeds registered", "§a-§rfor tall grass"}, (arguments, player) -> {
            CraftTweakerAPI.logCommand("Seeds:");
            for(WeightedItemStack seed : CraftTweakerAPI.vanilla.getSeeds().getSeeds()) {
                String message = seed.getStack() + " - " + (int) seed.getChance();
                player.sendChat(message);
                CraftTweakerAPI.logCommand("Seed: " + message);
            }
        }));
        
        crafttweakerCommands.put("wiki", new CraftTweakerCommand("wiki", new String[]{"/crafttweaker wiki", "§a-§rOpens your browser with the wiki"}, (arguments, player) -> player.openBrowser("http://minetweaker3.powerofbytes.com/wiki/")));
        
        crafttweakerCommands.put("bugs", new CraftTweakerCommand("bugs", new String[]{"/crafttweaker bugs", "§a-§rOpens your browser with the GitHub bug tracker"}, (arguments, player) -> player.openBrowser("https://github.com/jaredlll08/CraftTweaker/issues")));
        
        crafttweakerCommands.put("forum", new CraftTweakerCommand("forum", new String[]{"/crafttweaker forum", "§a-§rOpens your browser with the forum"}, (arguments, player) -> player.openBrowser("http://minetweaker3.powerofbytes.com/forum")));
        
        crafttweakerCommands.put("biomes", new CraftTweakerCommand("biomes", new String[]{"/crafttweaker biomes", "§a-§rLists all the biomes in the game"}, (arguments, player) -> {
            CraftTweakerAPI.logCommand("Biomes:");
            for(IBiome biome : CraftTweakerAPI.game.getBiomes()) {
                CraftTweakerAPI.logCommand("§a-§r" + biome.getName());
            }
            player.sendChat("Biome list generated; see crafttweaker.log in your minecraft dir");
        }));
        
        crafttweakerCommands.put("blockinfo", new CraftTweakerCommand("blockinfo", new String[]{"/crafttweaker blockinfo", "§a-§rActivates or deactivates block reader. In block info mode,", "§a-§rright-click a block to see ID, meta and tile entity data"}, (arguments, player) -> {
            if(blockInfoPlayers.isEmpty()) {
                blockEventHandler = events.onPlayerInteract(LISTEN_BLOCK_INFO);
            }
            
            if(blockInfoPlayers.contains(player)) {
                blockInfoPlayers.remove(player);
                player.sendChat("Block info mode deactivated.");
            } else {
                blockInfoPlayers.add(player);
                player.sendChat("Block info mode activated. Right-click a block to see its data.");
            }
            
            if(blockInfoPlayers.isEmpty()) {
                blockEventHandler.close();
            }
        }));
        
    }
    
    /**
     * Initializes the CraftTweaker API.
     *
     * @param oreDict   ore dictionary interface
     * @param recipes   recipe manager interface
     * @param furnace   furnace manager interface
     * @param game      game interface
     * @param mods      mods interface
     * @param formatter formatter interface
     * @param vanilla   vanilla interface
     */
    public static void init(IOreDict oreDict, IRecipeManager recipes, IFurnaceManager furnace, IGame game, ILoadedMods mods, IFormatter formatter, IVanilla vanilla) {
        CraftTweakerAPI.oreDict = oreDict;
        CraftTweakerAPI.recipes = recipes;
        CraftTweakerAPI.furnace = furnace;
        CraftTweakerAPI.game = game;
        CraftTweakerAPI.loadedMods = mods;
        CraftTweakerAPI.format = formatter;
        CraftTweakerAPI.vanilla = vanilla;
    }
    
    /**
     * Must be called upon server start.
     *
     * @param server server interface
     */
    public static void onServerStart(IServer server) {
        CraftTweakerAPI.server = server;
        events.onPlayerLoggedIn(LISTEN_LOGIN);
        events.onPlayerLoggedOut(LISTEN_LOGOUT);
        if(!CraftTweakerAPI.server.isCommandAdded("crafttweaker")) {
            server.addCommand("crafttweaker", "", new String[]{"ct"}, (arguments, player) -> {
                if(arguments.length == 0) {
                    player.sendChat("Please provide a command. Use /ct help for more info.");
                } else if(arguments[0].equals("help")) {
                    String[] keys = crafttweakerCommands.keySet().toArray(new String[crafttweakerCommands.size()]);
                    Arrays.sort(keys);
                    for(String key : keys) {
                        for(String helpMessage : crafttweakerCommands.get(key).description) {
                            player.sendChat(helpMessage);
                        }
                    }
                } else {
                    CraftTweakerCommand command = crafttweakerCommands.get(arguments[0]);
                    if(command == null) {
                        player.sendChat("No such crafttweaker command available");
                    } else {
                        command.function.execute(Arrays.copyOfRange(arguments, 1, arguments.length), player);
                    }
                }
            }, server::isOp, null);
        }
        
    }
    
    /**
     * Must be called upon server stop.
     */
    public static void onServerStop() {
        CraftTweakerAPI.server = null;
    }
    
    /**
     * Sets the script provider.
     *
     * @param provider script provider
     */
    public static void setScriptProvider(IScriptProvider provider) {
        CraftTweakerAPI.tweaker.setScriptProvider(provider);
    }
    
    /**
     * Called to reload scripts. Must be called after setting a new script
     * provider in order to reload scripts.
     */
    public static void load() {
        CraftTweakerAPI.tweaker.load();
    }
    
    
    // ##############################
    // ### Private static methods ###
    // ##############################
    
    private static void copyToClipboard(String value) {
        StringSelection stringSelection = new StringSelection(value);
        if(!(Toolkit.getDefaultToolkit() instanceof HeadlessToolkit)) {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        }
    }
    
    
    // #############################
    // ### Private inner classes ###
    // #############################
    
    private static class CraftTweakerCommand {
        
        private final String name;
        private final String[] description;
        private final ICommandFunction function;
        
        public CraftTweakerCommand(String name, String[] description, ICommandFunction function) {
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
            if(CraftTweakerAPI.server != null && CraftTweakerAPI.server.isOp(event.getPlayer())) {
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
            if(blockInfoPlayers.contains(event.getPlayer())) {
                IBlock block = event.getBlock();
                event.getPlayer().sendChat("Block ID: " + block.getDefinition().getId());
                event.getPlayer().sendChat("Meta value: " + block.getMeta());
                IData data = block.getTileData();
                if(data != null) {
                    event.getPlayer().sendChat("Tile entity data: " + data.asString());
                }
            }
        }
    }
}
