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
import crafttweaker.mc1120.data.NBTConverter;
import crafttweaker.mc1120.player.MCPlayer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static atm.bloodworkxgaming.SpecialMessagesChat.*;
import static crafttweaker.CraftTweakerAPI.furnace;
import static crafttweaker.CrafttweakerImplementationAPI.*;

/**
 * @author BloodWorkXGaming, Stan, Jared
 */
public class Commands {

    static void registerCommands() {

        CTChatCommand.registerCommand(new CraftTweakerCommand("names") {
            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                List<IItemDefinition> items = CraftTweakerAPI.game.getItems();
                items.sort(ITEM_COMPARATOR);
                for (IItemDefinition item : items) {
                    String displayName;

                    try {
                        displayName = ", " + item.makeStack(0).getDisplayName();
                    } catch (Throwable ex) {
                        displayName = " -- Name could not be retrieved due to an error: " + ex;
                    }

                    CraftTweakerAPI.logCommand("<" + item.getId() + ">" + displayName);
                }

                sender.sendMessage(getLinkToCraftTweakerLog("List generated", sender));

            }

            @Override
            protected void init() {
                setDescription(
                        getClickableCommandText("§2/ct names", "/ct names", true),
                        getNormalMessage(" §3Outputs a list of all item names in the game to the CraftTweaker log"));
            }
        });

        CTChatCommand.registerCommand(new CraftTweakerCommand("help") {
            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                CTChatCommand.sendUsage(sender);
            }

            @Override
            protected void init() {
                setDescription(
                        getClickableCommandText("§2/ct help","/ct help", true),
                        getNormalMessage(" §3Prints out the this help page"));
            }
        });

        CTChatCommand.registerCommand(new CraftTweakerCommand("liquids") {

            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                List<ILiquidDefinition> liquids = CraftTweakerAPI.game.getLiquids();
                liquids.sort(LIQUID_COMPARATOR);

                CraftTweakerAPI.logCommand("Liquids:");
                for (ILiquidDefinition liquid : liquids) {
                    CraftTweakerAPI.logCommand("<liquid:" + liquid.getName() + ">, " + liquid.getDisplayName());
                }
                sender.sendMessage(getLinkToCraftTweakerLog("List of liquids generated;", sender));
            }

            @Override
            protected void init() {
                setDescription(
                        getClickableCommandText("§2/ct liquids", "/ct liquids", true),
                        getNormalMessage(" §3Outputs a list of all liquid names in the game to the crafttweaker.log"));
            }
        });

        CTChatCommand.registerCommand(new CraftTweakerCommand("blocks") {
            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                List<IBlockDefinition> blocks = CraftTweakerAPI.game.getBlocks();
                blocks.sort(BLOCK_COMPARATOR);

                CraftTweakerAPI.logCommand("Blocks:");
                for (IBlockDefinition block : blocks) {
                    CraftTweakerAPI.logCommand("<block:" + block.getId() + ">, " + block.getDisplayName());
                }

                sender.sendMessage(getLinkToCraftTweakerLog("List of blocks generated", sender));

            }

            @Override
            protected void init() {
                setDescription(
                        getClickableCommandText("§2/ct blocks", "/ct blocks", true),
                        getNormalMessage(" §3Outputs a list of all blocks in the game to the crafttweaker log"));
            }
        });

        CTChatCommand.registerCommand(new CraftTweakerCommand("entities") {

            @Override
            protected void init() {
                setDescription(
                        getClickableCommandText("§2/ct entities", "/ct entities", true),
                        getNormalMessage(" §3Outputs a list of all entity definitions in the game to the crafttweaker log"));
            }

            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {

                List<IEntityDefinition> entities = CraftTweakerAPI.game.getEntities();
                entities.sort(ENTITY_COMPARATOR);

                CraftTweakerAPI.logCommand("Entities:");
                for (IEntityDefinition entity : entities) {
                    CraftTweakerAPI.logCommand(entity.getId() + " -- " + entity.getName());
                }

                sender.sendMessage(getLinkToCraftTweakerLog("List of Entities generated", sender));
            }
        });

        CTChatCommand.registerCommand(new CraftTweakerCommand("recipes") {

            @Override
            protected void init() {
                setDescription(
                        getClickableCommandText("§2/crafttweaker recipes", "/crafttweaker recipes", true),
                        getNormalMessage(" §3Lists all crafting recipes in the game"),
                        getClickableCommandText(" §a/ct recipes hand", "/ct recipes hand", true),
                        getNormalMessage("  §bLists all crafting recipes for the item in your hand"),
                        getNormalMessage("  §bAlso copies the recipes to clipboard"),
                        getClickableCommandText(" §a/ct recipes furnace", "/ct recipes furnace", true),
                        getNormalMessage("  §blists all furnace recipes in the game"));
            }

            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {

                if (args.length == 0) {
                    sender.sendMessage(new TextComponentString("Generating recipe list, this could take a while..."));

                    CraftTweakerAPI.logCommand("Recipes:");
                    for (ICraftingRecipe recipe : CraftTweakerAPI.recipes.getAll()) {
                        try {
                            CraftTweakerAPI.logCommand(recipe.toCommandString());
                        } catch (Throwable ex) {
                            if (recipe instanceof ShapedRecipe) {
                                ShapedRecipe shaped = (ShapedRecipe) recipe;
                                IItemStack out = shaped.getOutput();
                                CraftTweakerAPI.logError("Could not dump recipe for " + out, ex);
                            } else if (recipe instanceof ShapelessRecipe) {
                                ShapelessRecipe shapeless = (ShapelessRecipe) recipe;
                                IItemStack out = shapeless.getOutput();
                                CraftTweakerAPI.logError("Could not dump recipe for " + out, ex);
                            } else {
                                CraftTweakerAPI.logError("Could not dump recipe", ex);
                            }
                        }
                    }

                    sender.sendMessage(getLinkToCraftTweakerLog("Recipe list generated", sender));

                } else if (args[0].equals("hand") && sender.getCommandSenderEntity() instanceof EntityPlayer) {
                    MCPlayer player = new MCPlayer((EntityPlayer) sender.getCommandSenderEntity());
                    IItemStack item = player.getCurrentItem();
                    if (item != null) {
                        List<ICraftingRecipe> recipes = CraftTweakerAPI.recipes.getRecipesFor(item.anyAmount());
                        if (recipes.isEmpty()) {
                            player.sendChat("No crafting recipes found for that item");
                        } else {
                            StringBuilder recipesString = new StringBuilder();

                            for (ICraftingRecipe recipe : recipes) {
                                CraftTweakerAPI.logCommand(recipe.toCommandString());
                                player.sendChat(recipe.toCommandString());
                                recipesString.append(recipe.toCommandString()).append("\n");
                            }

                            player.copyToClipboard(recipesString.toString());
                        }
                    } else {
                        player.sendChat("No item was found");
                    }
                } else if (args[0].equals("furnace")) {
                    sender.sendMessage(new TextComponentString("Generating furnace list, this could take a while..."));

                    CraftTweakerAPI.logCommand("Furnace Recipes:");
                    for (IFurnaceRecipe recipe : furnace.getAll()) {
                        try {
                            CraftTweakerAPI.logCommand(recipe.toCommandString());
                        } catch (Throwable ex) {
                            CraftTweakerAPI.logError("Could not dump furnace recipe", ex);
                        }
                    }
                    sender.sendMessage(getLinkToCraftTweakerLog("Furnace Recipe list generated", sender));

                } else {

                    sender.sendMessage(new TextComponentString("Invalid arguments for recipes command"));

                }
            }

            @Override
            public List<String> getSubSubCommand(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {

                String[] subCommands = new String[]{"hand", "furnace"};
                ArrayList<String> currentPossibleCommands = new ArrayList<>();

                for (String cmd: subCommands) {
                    System.out.println("Trying " + cmd);

                    if (cmd.startsWith(args[0])){
                        currentPossibleCommands.add(cmd);
                    }
                }
                return currentPossibleCommands;
            }
        });

        CTChatCommand.registerCommand(new CraftTweakerCommand("inventory") {
            @Override
            protected void init() {
                setDescription(
                        getClickableCommandText("§2/ct inventory", "/ct inventory", true),
                        getNormalMessage(" §3Lists all items in your inventory"));
            }

            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                Entity entity = sender.getCommandSenderEntity();

                if (entity instanceof EntityPlayer) {
                    MCPlayer player = new MCPlayer((EntityPlayer) entity);

                    for (int i = 0; i < player.getInventorySize(); i++) {
                        IItemStack stack = player.getInventoryStack(i);
                        if (stack != null) {
                            CraftTweakerAPI.logCommand(stack.toString());
                        }
                    }
                    sender.sendMessage(getLinkToCraftTweakerLog("Recipe list generated", sender));
                } else {
                    sender.sendMessage(new TextComponentString("This command can only be used as a Player (inGame)"));
                }
            }
        });

        CTChatCommand.registerCommand(new CraftTweakerCommand("hand") {

            @Override
            protected void init() {
                setDescription(
                        getClickableCommandText("§2/ct hand","/ct hand" , true),
                        getNormalMessage(" §3Outputs the name of the item in your hand"),
                        getNormalMessage(" §3Also copies the name to clipboard and prints"),
                        getNormalMessage(" §3OreDict entries"));
            }

            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {

                if (sender.getCommandSenderEntity() instanceof EntityPlayer){
                    // Gets player and held item
                    EntityPlayer player = (EntityPlayer)sender.getCommandSenderEntity();
                    ItemStack heldItem = player.getHeldItemMainhand();



                    // Tries to get name of held item first
                    if (!heldItem.isEmpty()){
                        List<String> oreDictNames = BloodUtils.getOreDictOfItem(heldItem);

                        String itemName = "<" + heldItem.getItem().getRegistryName() + ":" + heldItem.getMetadata() + ">";

                        String withNBT = "";
                        if (heldItem.serializeNBT().hasKey("tag")){
                            String nbt = NBTConverter.from(heldItem.serializeNBT().getTag("tag"), false).toString();
                            if (nbt.length() > 0) withNBT = ".withTag(" + nbt + ")";
                        }

                        ClipboardHelper.copyStringPlayer(player, itemName + withNBT);
                        ClipboardHelper.sendMessageWithCopy(player, "Item §2" + itemName, itemName + withNBT);

                        // adds the oredict names if it has some
                        if (oreDictNames.size() > 0){
                            sender.sendMessage(new TextComponentString("§3OreDict Entries:"));
                            for (String oreName : oreDictNames) {
                                ClipboardHelper.sendMessageWithCopy(player, "    §e├ §b" + oreName, "<ore:" + oreName + ">");
                            }
                        }else {
                            sender.sendMessage(new TextComponentString("§3No OreDict Entries"));
                        }


                    }else {
                        // if hand is empty, tries to get oreDict of block
                        RayTraceResult rayTraceResult = BloodUtils.getPlayerLookat(player, 100);

                        if (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK){
                            BlockPos blockPos = rayTraceResult.getBlockPos();
                            IBlockState block = server.getEntityWorld().getBlockState(blockPos);

                            String blockName = "<" + block.getBlock().getRegistryName() + ":" + block.getBlock().getMetaFromState(block) + ">";
                            ClipboardHelper.copyStringPlayer(player, blockName);

                            ClipboardHelper.sendMessageWithCopy(player, "Block §2" + blockName +
                                    " §rat §9[" + blockPos.getX() + ", " + blockPos.getY() + ", " + blockPos.getZ() + "]§r", blockName );

                            // adds the oreDict names if it has some
                            try{

                                List<String> oreDictNames = BloodUtils.getOreDictOfItem(new ItemStack(block.getBlock(), 1, block.getBlock().getMetaFromState(block)));
                                if (oreDictNames.size() > 0){
                                    sender.sendMessage(new TextComponentString("§3OreDict Entries:"));

                                    for (String oreName :
                                            oreDictNames) {
                                        ClipboardHelper.sendMessageWithCopy(player, "    §e├ §b" + oreName, "<ore:" + oreName + ">");
                                    }
                                }else {
                                    sender.sendMessage(new TextComponentString("§3No OreDict Entries"));
                                }
                                // catches if it couldn't create a valid ItemStack for the Block
                            }catch (IllegalArgumentException e){
                                sender.sendMessage(new TextComponentString("§3No OreDict Entries"));
                            }

                        } else {
                            sender.sendMessage(new TextComponentString("§4Please hold an Item in your hand or look at a Block."));
                        }
                    }
                }else {
                    sender.sendMessage(new TextComponentString("This command can only be casted by a player inGame"));
                }

            }


        });

        CTChatCommand.registerCommand(new CraftTweakerCommand("mods") {

            @Override
            protected void init() {
                setDescription(
                        getClickableCommandText("§2/ct mods", "/ct mods", true),
                        getNormalMessage(" §3Outputs all active mod IDs and versions in the game"));
            }

            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                CraftTweakerAPI.logCommand("Mods list:");
                for (IMod mod : CraftTweakerAPI.loadedMods) {
                    String message = mod.getId() + " - " + mod.getName() + " - " + mod.getVersion();
                    sender.sendMessage(new TextComponentString(message));
                    CraftTweakerAPI.logCommand("Mod: " + message);
                }

                sender.sendMessage(getLinkToCraftTweakerLog("List of Mods generated", sender));
            }
        });

        CTChatCommand.registerCommand(new CraftTweakerCommand("oredict") {

            @Override
            protected void init() {
                setDescription(
                        getClickableCommandText("§2/ct oredict", "/ct oredict", true),
                        getNormalMessage(" §3Outputs all ore dictionary entries in the game to the crafttweaker log"),
                        getClickableCommandText("§a/ct oredict <name>", "/ct oredict ", false),
                        getNormalMessage("  §bOutputs all items in the given ore dictionary entry to the crafttweaker log")
                );
            }

            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {

                if (args.length > 0) {
                    String entryName = args[0];
                    IOreDictEntry entry = CraftTweakerAPI.oreDict.get(entryName);
                    if (entry.isEmpty()) {
                        sender.sendMessage(new TextComponentString("Entry doesn't exist"));
                        return;
                    } else {
                        CraftTweakerAPI.logCommand("Ore entries for " + entryName + ":");
                        for (IItemStack ore : entry.getItems()) {
                            CraftTweakerAPI.logCommand("-" + ore);
                        }
                    }
                } else {
                    for (IOreDictEntry entry : CraftTweakerAPI.oreDict.getEntries()) {
                        if (!entry.isEmpty()) {
                            CraftTweakerAPI.logCommand("Ore entries for <ore:" + entry.getName() + "> :");
                            for (IItemStack ore : entry.getItems()) {
                                CraftTweakerAPI.logCommand("-" + ore);
                            }
                        }
                    }
                }
                sender.sendMessage(getLinkToCraftTweakerLog("OreDict list generated", sender));
            }
        });

        CTChatCommand.registerCommand(new CraftTweakerCommand("seeds") {

            @Override
            protected void init() {
                setDescription(
                        getClickableCommandText("§2/ct seeds", "/ct seeds", true),
                        getNormalMessage(" §3Prints all seeds registered"),
                        getNormalMessage(" §3for tall grass")
                );
            }

            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                CraftTweakerAPI.logCommand("Seeds:");
                for (WeightedItemStack seed : CraftTweakerAPI.vanilla.getSeeds().getSeeds()) {
                    String message = seed.getStack() + " - " + (int) seed.getChance();
                    sender.sendMessage(new TextComponentString(message));
                    CraftTweakerAPI.logCommand("Seed: " + message);
                }

                sender.sendMessage(getLinkToCraftTweakerLog("Seed list generated", sender));
            }
        });

        CTChatCommand.registerCommand(new CraftTweakerCommand("wiki") {
            @Override
            protected void init() {
                setDescription(
                        getClickableCommandText("§2/ct wiki", "/ct wiki", true),
                        getNormalMessage(" §3Opens your browser with the wiki"));
            }

            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                if (sender.getCommandSenderEntity() instanceof EntityPlayer) {
                    MCPlayer player = new MCPlayer((EntityPlayer) sender.getCommandSenderEntity());
                    player.openBrowser("http://minetweaker3.powerofbytes.com/wiki/");
                    sender.sendMessage(getClickableBrowserLinkText("http://minetweaker3.powerofbytes.com/wiki/", "http://minetweaker3.powerofbytes.com/wiki/"));
                } else {
                        sender.sendMessage(new TextComponentString("http://minetweaker3.powerofbytes.com/wiki/"));
                }
            }
        });

        CTChatCommand.registerCommand(new CraftTweakerCommand("bugs") {
            @Override
            protected void init() {
                setDescription(
                        getClickableCommandText("§2/ct bugs", "/ct bugs", true),
                        getNormalMessage(" §3Opens your browser with the GitHub bug tracker"));
            }

            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                if (sender.getCommandSenderEntity() instanceof EntityPlayer) {
                    MCPlayer player = new MCPlayer((EntityPlayer) sender.getCommandSenderEntity());
                    player.openBrowser("https://github.com/jaredlll08/CraftTweaker/issues");
                    sender.sendMessage(getClickableBrowserLinkText("http://minetweaker3.powerofbytes.com/wiki/", "http://minetweaker3.powerofbytes.com/wiki/"));

                } else {
                    sender.sendMessage(new TextComponentString("https://github.com/jaredlll08/CraftTweaker/issues"));
                }
            }
        });

        CTChatCommand.registerCommand(new CraftTweakerCommand("discord") {
            @Override
            protected void init() {
                setDescription(
                        getClickableCommandText("§2/ct discord", "/ct discord", true),
                        getNormalMessage(" §3Opens your browser with a link to the Discord server"));
            }

            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                if (sender.getCommandSenderEntity() instanceof EntityPlayer) {
                    MCPlayer player = new MCPlayer((EntityPlayer) sender.getCommandSenderEntity());
                    player.openBrowser("https://discord.gg/3VBK9ar");
                    sender.sendMessage(getClickableBrowserLinkText("https://discord.gg/3VBK9ar", "https://discord.gg/3VBK9ar"));

                } else {
                    sender.sendMessage(new TextComponentString("https://discord.gg/3VBK9ar"));
                }
            }
        });

        CTChatCommand.registerCommand(new CraftTweakerCommand("docs") {
            @Override
            protected void init() {
                setDescription(
                        getClickableCommandText("§2/ct docs", "/ct docs", true),
                        getNormalMessage(" §3Opens your browser with the docs"));
            }

            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                if (sender.getCommandSenderEntity() instanceof EntityPlayer) {
                    MCPlayer player = new MCPlayer((EntityPlayer) sender.getCommandSenderEntity());
                    player.openBrowser("http://crafttweaker.readthedocs.io/en/latest/");
                    sender.sendMessage(getClickableBrowserLinkText("http://crafttweaker.readthedocs.io/en/latest/", "http://crafttweaker.readthedocs.io/en/latest/"));

                } else {
                    sender.sendMessage(new TextComponentString("http://crafttweaker.readthedocs.io/en/latest/"));
                }
            }
        });

        CTChatCommand.registerCommand(new CraftTweakerCommand("biomes") {

            @Override
            protected void init() {
                setDescription(
                        getClickableCommandText("§2/ct biomes", "/ct biomes", true),
                        getNormalMessage(" §3Lists all the biomes in the game"));

            }

            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                CraftTweakerAPI.logCommand("Biomes:");
                for (IBiome biome : CraftTweakerAPI.game.getBiomes()) {
                    CraftTweakerAPI.logCommand("-" + biome.getName());
                }

                sender.sendMessage(getLinkToCraftTweakerLog("Biome list generated", sender));
            }
        });

        CTChatCommand.registerCommand(new CraftTweakerCommand("blockinfo") {

            @Override
            protected void init() {
                setDescription(
                        getClickableCommandText("§2/ct blockinfo", "/ct blockinfo", true),
                        getNormalMessage(" §3Activates or deactivates block reader. In block info mode,"),
                        getNormalMessage(" §3right-click a block to see ID, meta and tile entity data"));
            }

            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                if (sender.getCommandSenderEntity() instanceof EntityPlayer) {
                    MCPlayer player = new MCPlayer((EntityPlayer) sender.getCommandSenderEntity());

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
                } else {
                    sender.sendMessage(new TextComponentString("This Command can only be performed from a Player(InGame)"));
                }
            }
        });

        CTChatCommand.registerCommand(new CraftTweakerCommand("copy") {

            @Override
            protected void init() {
                setDescription(
                        getClickableCommandText("§2/ct copy", "/ct copy", false),
                        getNormalMessage(" §3Copies the provided string behind it"));
            }

            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                ClipboardHelper.copyCommandRun(sender, args);
            }
        });

        CTChatCommand.registerCommand(new CraftTweakerCommand("nbt") {

            @Override
            protected void init() {
                setDescription(
                        getClickableCommandText("§2/ct nbt", "/ct nbt", true),
                        getNormalMessage(" §3Shows the NBT of the block you are looking at"),
                        getNormalMessage(" §3or the item you are holding")
                );
            }

            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {

                if (sender.getCommandSenderEntity() instanceof EntityPlayer){

                    // Gets player and held item
                    EntityPlayer player = (EntityPlayer)sender.getCommandSenderEntity();
                    ItemStack heldItem = player.getHeldItemMainhand();


                    // Tries to get name of held item first
                    if (heldItem != ItemStack.EMPTY){

                        String itemName = "<" + heldItem.getItem().getRegistryName() + ":" + heldItem.getMetadata() + ">";

                        String nbt = "";
                        if (heldItem.serializeNBT().hasKey("tag")){
                            nbt = NBTConverter.from(heldItem.serializeNBT().getTag("tag"), false).toString();
                        }
                        String withNBT = "";
                        if (nbt.length() > 0) withNBT = ".withTag(" + nbt + ")";

                        ClipboardHelper.copyStringPlayer(player, itemName + withNBT);

                        ClipboardHelper.sendMessageWithCopy(player, "Item §2" + itemName,
                                itemName + withNBT);


                        // adds the oredict names if it has some
                        if (nbt.length() > 0){
                            sender.sendMessage(new TextComponentString("§3NBT-Data:"));
                            ClipboardHelper.sendMessageWithCopy(player, NBTUtils.getAppealingString(nbt), nbt);

                        }else {
                            sender.sendMessage(new TextComponentString("§3No NBT Data"));
                        }


                        // if hand is empty, tries to get oreDict of block
                    }else {
                        RayTraceResult rayTraceResult = BloodUtils.getPlayerLookat(player, 100);

                        if (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK){
                            BlockPos blockPos = rayTraceResult.getBlockPos();
                            IBlockState block = server.getEntityWorld().getBlockState(blockPos);

                            ClipboardHelper.sendMessageWithCopy(player, "Block §2[" + block.getBlock().getRegistryName() +":" + block.getBlock().getMetaFromState(block) +
                                            "] §rat §9[" + blockPos.getX() + ", " + blockPos.getY() + ", " + blockPos.getZ() + "]§r",
                                    block.getBlock().getRegistryName() +":" + block.getBlock().getMetaFromState(block));

                            TileEntity te = server.getEntityWorld().getTileEntity(blockPos);
                            if (te != null){

                                sender.sendMessage(new TextComponentString("§3NBT-Data:"));

                                String nbt = NBTConverter.from(te.serializeNBT(), false).toString();

                                ClipboardHelper.sendMessageWithCopy(player, NBTUtils.getAppealingString(nbt), nbt);
                            }else {
                                sender.sendMessage(new TextComponentString("§3Block is no TileEntity and has no NBT"));
                            }
                        } else {
                            sender.sendMessage(new TextComponentString("§4Please hold an Item in your hand or look at a Block."));
                        }
                    }
                }
            }
        });


    }

}
