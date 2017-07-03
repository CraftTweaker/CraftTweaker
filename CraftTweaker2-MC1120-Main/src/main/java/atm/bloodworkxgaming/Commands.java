package atm.bloodworkxgaming;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.block.IBlockDefinition;
import crafttweaker.api.entity.IEntityDefinition;
import crafttweaker.api.item.IItemDefinition;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.WeightedItemStack;
import crafttweaker.api.liquid.ILiquidDefinition;
import crafttweaker.api.mods.IMod;
import crafttweaker.api.oredict.IOreDictEntry;
import crafttweaker.api.recipes.ICraftingRecipe;
import crafttweaker.api.recipes.IFurnaceRecipe;
import crafttweaker.api.recipes.ShapedRecipe;
import crafttweaker.api.recipes.ShapelessRecipe;
import crafttweaker.api.world.IBiome;
import crafttweaker.mc1120.player.MCPlayer;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static crafttweaker.CraftTweakerAPI.furnace;
import static crafttweaker.CrafttweakerImplementationAPI.*;

/**
 * @author BloodWorkXGaming
 */
public class Commands {

    static void registerCommands(Map<String, CraftTweakerCommand> map){

        map.put("names",
                new CraftTweakerCommand("names", new String[]{"/crafttweaker names", " ├Outputs a list of all item names in the game to the crafttweaker log"}){
                    @Override
                    public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
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
                        sender.sendMessage(new TextComponentString("List generated; see crafttweaker.log in your minecraft dir"));
                    }
                });
        /*
        craftTweakerCommands.put("liquids", new CraftTweakerCommand("liquids", new String[]{"/crafttweaker liquids", "-Outputs a list of all liquid names in the game to the crafttweaker log"}, (arguments, player) -> {
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

        craftTweakerCommands.put("blocks", new CraftTweakerCommand("blocks", new String[]{"/crafttweaker blocks", "-Outputs a list of all blocks in the game to the crafttweaker log"}, (arguments, player) -> {
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

        craftTweakerCommands.put("entities", new CraftTweakerCommand("entities", new String[]{"/crafttweaker entities", "-Outputs a list of all entity definitions in the game to the crafttweaker log"}, (arguments, player) -> {
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

        craftTweakerCommands.put("recipes", new CraftTweakerCommand("recipes", new String[]{"/crafttweaker recipes", "-Lists all crafting recipes in the game", "/crafttweaker recipes hand", "-Lists all crafting recipes for the item in your hand", "-Also copies the recipes to clipboard", "/crafttweaker recipes furnace", "-lists all furnace recipes in the game"}, (arguments, player) -> {
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

        craftTweakerCommands.put("inventory", new CraftTweakerCommand("inventory", new String[]{"/crafttweaker inventory", "-Lists all items in your inventory"}, (arguments, player) -> {
            for(int i = 0; i < player.getInventorySize(); i++) {
                IItemStack stack = player.getInventoryStack(i);
                if(stack != null) {
                    CraftTweakerAPI.logCommand(stack.toString());
                }
            }
            player.sendChat("Recipe list generated; see crafttweaker.log in your minecraft dir");
        }));

        craftTweakerCommands.put("hand", new CraftTweakerCommand("hand", new String[]{"/crafttweaker hand", "-Outputs the name of the item in your hand", "-Also copies the name to clipboard and prints", "-oredict entries"}, (arguments, player) -> {
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

        craftTweakerCommands.put("oredict", new CraftTweakerCommand("oredict", new String[]{"/crafttweaker oredict", "-Outputs all ore dictionary entries in the game to the crafttweaker log", "/crafttweaker oredict <name>", "-Outputs all items in the given ore dictionary entry to the crafttweaker log"}, (arguments, player) -> {
            if(arguments.length > 0) {
                String entryName = arguments[0];
                IOreDictEntry entry = CraftTweakerAPI.oreDict.get(entryName);
                if(entry.isEmpty()) {
                    player.sendChat("Entry doesn't exist");
                    return;
                } else {
                    CraftTweakerAPI.logCommand("Ore entries for " + entryName + ":");
                    for(IItemStack ore : entry.getItems()) {
                        CraftTweakerAPI.logCommand("-" + ore);
                    }
                }
            } else {
                for(IOreDictEntry entry : CraftTweakerAPI.oreDict.getEntries()) {
                    if(!entry.isEmpty()) {
                        CraftTweakerAPI.logCommand("Ore entries for <ore:" + entry.getName() + "> :");
                        for(IItemStack ore : entry.getItems()) {
                            CraftTweakerAPI.logCommand("-" + ore);
                        }
                    }
                }
            }
            player.sendChat("List generated; see crafttweaker.log in your minecraft dir");
        }));

        craftTweakerCommands.put("mods", new CraftTweakerCommand("mods", new String[]{"/crafttweaker mods", "-Outputs all active mod IDs and versions in the game"}, (arguments, player) -> {
            CraftTweakerAPI.logCommand("Mods list:");
            for(IMod mod : CraftTweakerAPI.loadedMods) {
                String message = mod.getId() + " - " + mod.getName() + " - " + mod.getVersion();
                player.sendChat(message);
                CraftTweakerAPI.logCommand("Mod: " + message);
            }
        }));

        craftTweakerCommands.put("seeds", new CraftTweakerCommand("seeds", new String[]{"/crafttweaker seeds", "-Prints all seeds registered", "-for tall grass"}, (arguments, player) -> {
            CraftTweakerAPI.logCommand("Seeds:");
            for(WeightedItemStack seed : CraftTweakerAPI.vanilla.getSeeds().getSeeds()) {
                String message = seed.getStack() + " - " + (int) seed.getChance();
                player.sendChat(message);
                CraftTweakerAPI.logCommand("Seed: " + message);
            }
        }));

        craftTweakerCommands.put("wiki", new CraftTweakerCommand("wiki", new String[]{"/crafttweaker wiki", "-Opens your browser with the wiki"}, (arguments, player) -> player.openBrowser("http://minetweaker3.powerofbytes.com/wiki/")));

        craftTweakerCommands.put("bugs", new CraftTweakerCommand("bugs", new String[]{"/crafttweaker bugs", "-Opens your browser with the GitHub bug tracker"}, (arguments, player) -> player.openBrowser("https://github.com/jaredlll08/CraftTweaker/issues")));

        craftTweakerCommands.put("discord", new CraftTweakerCommand("discord", new String[]{"/crafttweaker discord", "-Opens your browser with a link to the Discord server"}, (arguments, player) -> player.openBrowser("https://discord.gg/3VBK9ar")));

        craftTweakerCommands.put("biomes", new CraftTweakerCommand("biomes", new String[]{"/crafttweaker biomes", "-Lists all the biomes in the game"}, (arguments, player) -> {
            CraftTweakerAPI.logCommand("Biomes:");
            for(IBiome biome : CraftTweakerAPI.game.getBiomes()) {
                CraftTweakerAPI.logCommand("-" + biome.getName());
            }
            player.sendChat("Biome list generated; see crafttweaker.log in your minecraft dir");
        }));

        craftTweakerCommands.put("blockinfo", new CraftTweakerCommand("blockinfo", new String[]{"/crafttweaker blockinfo", "-Activates or deactivates block reader. In block info mode,", "-right-click a block to see ID, meta and tile entity data"}, (arguments, player) -> {
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
        */
    }

}
