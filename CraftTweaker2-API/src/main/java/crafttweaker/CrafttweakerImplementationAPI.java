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
import crafttweaker.runtime.IScriptProvider;
import crafttweaker.util.IEventHandler;
import java.io.Serializable;
import java.util.*;



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

    public static final Comparator<IItemDefinition> ITEM_COMPARATOR = new ItemComparator();
    public static final Comparator<ILiquidDefinition> LIQUID_COMPARATOR = new LiquidComparator();
    public static final Comparator<IBlockDefinition> BLOCK_COMPARATOR = new BlockComparator();
    public static final Comparator<IEntityDefinition> ENTITY_COMPARATOR = new EntityComparator();
    
    private static final ListenPlayerLoggedIn LISTEN_LOGIN = new ListenPlayerLoggedIn();
    private static final ListenPlayerLoggedOut LISTEN_LOGOUT = new ListenPlayerLoggedOut();
    public static final ListenBlockInfo LISTEN_BLOCK_INFO = new ListenBlockInfo();
    /**
     * Access point to general platform functions.
     */
    public static IPlatformFunctions platform = null;
    public static Set<IPlayer> blockInfoPlayers = new HashSet<>();
    public static IEventHandle blockEventHandler = null;

    
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
     * @param itemUtils itemUtils interface
     * @param brewing 	brewing interface
     */
    public static void init(IOreDict oreDict, IRecipeManager recipes, IFurnaceManager furnace, IGame game, ILoadedMods mods, IFormatter formatter, IVanilla vanilla, IItemUtils itemUtils, IBrewingManager brewing) {
        CraftTweakerAPI.oreDict = oreDict;
        CraftTweakerAPI.recipes = recipes;
        CraftTweakerAPI.furnace = furnace;
        CraftTweakerAPI.game = game;
        CraftTweakerAPI.loadedMods = mods;
        CraftTweakerAPI.format = formatter;
        CraftTweakerAPI.vanilla = vanilla;
        CraftTweakerAPI.itemUtils = itemUtils;
        CraftTweakerAPI.brewingManager = brewing;
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
    
    // #############################
    // ### Private inner classes ###
    // #############################
    

    private static class ItemComparator implements Comparator<IItemDefinition>, Serializable {
        
        @Override
        public int compare(IItemDefinition o1, IItemDefinition o2) {
            return o1.getId().compareTo(o2.getId());
        }
    }
    
    private static class LiquidComparator implements Comparator<ILiquidDefinition>, Serializable {
        
        @Override
        public int compare(ILiquidDefinition o1, ILiquidDefinition o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }
    
    private static class BlockComparator implements Comparator<IBlockDefinition>, Serializable {
        
        @Override
        public int compare(IBlockDefinition o1, IBlockDefinition o2) {
            return o1.getId().compareTo(o2.getId());
        }
    }
    
    private static class EntityComparator implements Comparator<IEntityDefinition>, Serializable {
        
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
                event.getPlayer().sendChat("Block Name: " + block.getDefinition().getId());
                event.getPlayer().sendChat("Meta value: " + block.getMeta());
                IData data = block.getTileData();
                if(data != null) {
                    event.getPlayer().sendChat("Tile entity data: " + data.asString());
                }
            }
        }
    }
}
