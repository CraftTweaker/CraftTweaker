package crafttweaker.mc1120.commands;

import com.google.gson.JsonPrimitive;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.block.IBlockDefinition;
import crafttweaker.api.entity.IEntityDefinition;
import crafttweaker.api.item.*;
import crafttweaker.api.liquid.ILiquidDefinition;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.mods.IMod;
import crafttweaker.api.oredict.IOreDictEntry;
import crafttweaker.api.potions.IPotion;
import crafttweaker.api.recipes.ICraftingRecipe;
import crafttweaker.api.recipes.IFurnaceRecipe;
import crafttweaker.api.world.IBiome;

import crafttweaker.mc1120.brackets.*;

import crafttweaker.mc1120.commands.dumpzscommand.*;
import crafttweaker.mc1120.data.NBTConverter;
import crafttweaker.mc1120.player.MCPlayer;
import crafttweaker.mc1120.recipes.MCRecipeBase;
import crafttweaker.runtime.ScriptLoader;
import crafttweaker.socket.CrTSocketHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.ICommandSender;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.io.File;
import java.io.Serializable;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static crafttweaker.CraftTweakerAPI.furnace;
import static crafttweaker.CrafttweakerImplementationAPI.*;
import static crafttweaker.mc1120.commands.SpecialMessagesChat.*;

/**
 * @author BloodWorkXGaming, Stan, Jared
 */
public class Commands {
    
    public static final PotionComparator POTION_COMPARATOR = new PotionComparator();
    public static final Comparator<Item> ITEM_COMPARATOR = new ItemComparator();
    
    
    static void registerCommands() {
        CTChatCommand.registerCommand(new CraftTweakerCommand("foods") {
            
            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                
                CraftTweakerAPI.logCommand("Foods:");
                for(IItemDefinition item : CraftTweakerAPI.game.getItems()) {
                    for(IItemStack stack : item.getSubItems()) {
                        if(stack.isFood()) {
                            CraftTweakerAPI.logCommand("- " + stack.toCommandString() + " Heal amount: " + stack.getHealAmount() + " Saturation amount: " + stack.getSaturation());
                        }
                    }
                }
                sender.sendMessage(getLinkToCraftTweakerLog("List of foods generated;", sender));
            }
            
            @Override
            protected void init() {
                setDescription(getClickableCommandText("\u00A72/ct foods", "/ct foods", true), getNormalMessage(" \u00A73Outputs a list of all foods in the game to the crafttweaker.log"));
            }
        });
        
        CTChatCommand.registerCommand(new CraftTweakerCommand("liquids") {
            
            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                List<ILiquidDefinition> liquids = CraftTweakerAPI.game.getLiquids();
                liquids.sort(LIQUID_COMPARATOR);
                
                CraftTweakerAPI.logCommand("Liquids:");
                
                for(ILiquidDefinition liquid : liquids) {
                    StringBuilder builder = new StringBuilder("<liquid:" + liquid.getName() + ">").append("\n");
                    builder.append("- Name: ").append(liquid.getName()).append("\n");
                    builder.append("- Display Name: ").append(liquid.getDisplayName()).append("\n");
                    builder.append("- Luminosity:").append(liquid.getLuminosity()).append("\n");
                    builder.append("- Density: ").append(liquid.getDensity()).append("\n");
                    builder.append("- Temperature: ").append(liquid.getTemperature()).append("\n");
                    builder.append("- Viscosity: ").append(liquid.getViscosity()).append("\n");
                    builder.append("- Gaseous: ").append(liquid.isGaseous());
                    CraftTweakerAPI.logCommand(builder.toString());
                }
                sender.sendMessage(getLinkToCraftTweakerLog("List of liquids generated;", sender));
            }
            
            @Override
            protected void init() {
                setDescription(getClickableCommandText("\u00A72/ct liquids", "/ct liquids", true), getNormalMessage(" \u00A73Outputs a list of all liquid names in the game to the crafttweaker.log"));
            }
        });
        
        CTChatCommand.registerCommand(new CraftTweakerCommand("blocks") {
            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                List<IBlockDefinition> blocks = CraftTweakerAPI.game.getBlocks();
                blocks.sort(BLOCK_COMPARATOR);
                
                CraftTweakerAPI.logCommand("Blocks:");
                for(IBlockDefinition block : blocks) {
                    CraftTweakerAPI.logCommand("<block:" + block.getId() + ">, " + block.getDisplayName());
                }
                
                sender.sendMessage(getLinkToCraftTweakerLog("List of blocks generated", sender));
                
            }
            
            @Override
            protected void init() {
                setDescription(getClickableCommandText("\u00A72/ct blocks", "/ct blocks", true), getNormalMessage(" \u00A73Outputs a list of all blocks in the game to the crafttweaker log"));
            }
        });
        
        CTChatCommand.registerCommand(new CraftTweakerCommand("entities") {
            
            @Override
            protected void init() {
                setDescription(getClickableCommandText("\u00A72/ct entities", "/ct entities", true), getNormalMessage(" \u00A73Outputs a list of all entity definitions in the game to the crafttweaker log"));
            }
            
            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                
                List<IEntityDefinition> entities = CraftTweakerAPI.game.getEntities();
                entities.sort(ENTITY_COMPARATOR);
                
                CraftTweakerAPI.logCommand("Entities:");
                for(IEntityDefinition entity : entities) {
                    CraftTweakerAPI.logCommand(entity.getId() + " -- " + entity.getName());
                }
                
                sender.sendMessage(getLinkToCraftTweakerLog("List of Entities generated", sender));
            }
        });
        
        CTChatCommand.registerCommand(new CraftTweakerCommand("potions") {
            
            @Override
            protected void init() {
                setDescription(getClickableCommandText("\u00A72/ct potions", "/ct potions", true), getNormalMessage(" \u00A73Outputs a list of all potions to the crafttweaker.log"));
            }
            
            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                
                List<IPotion> potions = CraftTweakerAPI.game.getPotions();
                potions.sort(POTION_COMPARATOR);
                
                CraftTweakerAPI.logCommand("Potions:");
                BracketHandlerPotion.getPotionNames().forEach((k, v) -> {
                    int id = Potion.REGISTRY.getIDForObject(v);
                    CraftTweakerAPI.logCommand(k + " -- color: " + v.getLiquidColor() + (v.isBadEffect() ? " -- is bad effect" : " -- is good effect") + " -- PotionID: " + id);
                });
                
                sender.sendMessage(getLinkToCraftTweakerLog("List of Potions generated", sender));
            }
        });
        CTChatCommand.registerCommand(new CraftTweakerCommand("recipeNames") {
            
            @Override
            protected void init() {
                setDescription(getClickableCommandText("\u00A72/ct recipeNames", "/ct recipeNames", true), getNormalMessage(" \u00A73A modid can be provided to filter results"), getNormalMessage(" \u00A73/ct recipeNames <modid>"), getNormalMessage(" \u00A73Lists all crafting recipe names in the game"));
            }
            
            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                CraftTweakerAPI.logCommand("Recipe names:");
                String filter = args.length == 0 ? "" : args[0];
                
                for(Map.Entry<ResourceLocation, IRecipe> entry : ForgeRegistries.RECIPES.getEntries()) {
                    if(!filter.isEmpty() && !entry.getKey().getResourceDomain().equalsIgnoreCase(filter)) {
                        continue;
                    }
                    CraftTweakerAPI.logCommand(entry.getKey().toString() + " - " + entry.getValue().getRecipeOutput());
                }
                sender.sendMessage(getLinkToCraftTweakerLog("Recipe list generated", sender));
            }
        });
        
        CTChatCommand.registerCommand(new CraftTweakerCommand("reload") {
            
            @Override
            protected void init() {
                setDescription(getClickableCommandText("\u00A72/ct reload", "/ct reload", true), getNormalMessage(" \u00A73Gives information as to why reloading is not possible"));
            }
            
            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                sender.sendMessage(getNormalMessage("Unfortunately reloading is not possible due to a forge change."));
                sender.sendMessage(getClickableBrowserLinkText("Please read this", "https://www.reddit.com/r/feedthebeast/comments/6oxjtd/lets_talk_crafttweaker_reload/"));
            }
        });
        CTChatCommand.registerCommand(new CraftTweakerCommand("recipes") {
            
            @Override
            protected void init() {
                setDescription(getClickableCommandText("\u00A72/ct recipes", "/ct recipes", true), getNormalMessage(" \u00A73Lists all crafting recipes in the game"), getClickableCommandText(" \u00A7a/ct recipes hand", "/ct recipes hand", true), getNormalMessage("  \u00A7bLists all crafting recipes for the item in your hand"), getNormalMessage("  \u00A7bAlso copies the recipes to clipboard"), getClickableCommandText(" \u00A7a/ct recipes furnace", "/ct recipes furnace", true), getNormalMessage("  \u00A7blists all furnace recipes in the game"));
            }
            
            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                
                if(args.length == 0) {
                    sender.sendMessage(new TextComponentString("Generating recipe list, this could take a while..."));
                    
                    CraftTweakerAPI.logCommand("Recipes:");
                    for(ICraftingRecipe recipe : CraftTweakerAPI.recipes.getAll()) {
                        try {
                            CraftTweakerAPI.logCommand(recipe.toCommandString());
                            
                        } catch(Exception ex) {
                            if(recipe instanceof MCRecipeBase) {
                                
                                MCRecipeBase recipeBase = (MCRecipeBase) recipe;
                                IItemStack out = recipeBase.getOutput();
                                CraftTweakerAPI.logError("Could not dump recipe for " + out, ex);
                            } else {
                                CraftTweakerAPI.logError("Could not dump recipe", ex);
                            }
                        }
                    }
                    
                    sender.sendMessage(getLinkToCraftTweakerLog("Recipe list generated", sender));
                    
                } else if(args[0].equals("hand") && sender.getCommandSenderEntity() instanceof EntityPlayer) {
                    MCPlayer player = new MCPlayer((EntityPlayer) sender.getCommandSenderEntity());
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
                            
                            player.copyToClipboard(recipesString.toString());
                        }
                    } else {
                        player.sendChat("No item was found");
                    }
                } else if(args[0].equals("furnace")) {
                    sender.sendMessage(new TextComponentString("Generating furnace list, this could take a while..."));
                    
                    CraftTweakerAPI.logCommand("Furnace Recipes:");
                    for(IFurnaceRecipe recipe : furnace.getAll()) {
                        try {
                            CraftTweakerAPI.logCommand(recipe.toCommandString());
                            
                        } catch(Exception ex) {
                            
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
                
                
                for(String cmd : subCommands) {
                    
                    if(cmd.startsWith(args[0])) {
                        
                        currentPossibleCommands.add(cmd);
                    }
                }
                return currentPossibleCommands;
            }
        });
        
        CTChatCommand.registerCommand(new CraftTweakerCommand("inventory") {
            @Override
            protected void init() {
                setDescription(getClickableCommandText("\u00A72/ct inventory", "/ct inventory", true), getNormalMessage(" \u00A73Lists all items in your inventory"));
            }
            
            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                Entity entity = sender.getCommandSenderEntity();
                
                if(entity instanceof EntityPlayer) {
                    MCPlayer player = new MCPlayer((EntityPlayer) entity);
                    
                    for(int i = 0; i < player.getInventorySize(); i++) {
                        IItemStack stack = player.getInventoryStack(i);
                        if(stack != null) {
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
                setDescription(getClickableCommandText("\u00A72/ct hand", "/ct hand", true), getNormalMessage(" \u00A73Outputs the name of the item in your hand"), getNormalMessage(" \u00A73Also copies the name to clipboard and prints"), getNormalMessage(" \u00A73OreDict entries"));
            }
            
            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                
                if(sender.getCommandSenderEntity() instanceof EntityPlayer) {
                    // Gets player and held item
                    EntityPlayer player = (EntityPlayer) sender.getCommandSenderEntity();
                    ItemStack heldItem = player.getHeldItemMainhand();
                    
                    
                    // Tries to get name of held item first
                    if(!heldItem.isEmpty()) {
                        List<String> oreDictNames = CommandUtils.getOreDictOfItem(heldItem);
                        
                        int meta = heldItem.getMetadata();
                        String itemName = "<" + heldItem.getItem().getRegistryName() + (meta == 0 ? "" : ":" + meta) + ">";
                        
                        String withNBT = "";
                        if(heldItem.serializeNBT().hasKey("tag")) {
                            String nbt = NBTConverter.from(heldItem.serializeNBT().getTag("tag"), false).toString();
                            if(nbt.length() > 0)
                                withNBT = ".withTag(" + nbt + ")";
                        }
                        
                        ClipboardHelper.copyStringPlayer(player, itemName + withNBT);
                        ClipboardHelper.sendMessageWithCopy(player, "Item \u00A72" + itemName + "\u00A7a" + withNBT, itemName + withNBT);
                        
                        // adds liquid the item contains

                        ILiquidStack liquidStack = CraftTweakerMC.getILiquidStack(FluidUtil.getFluidContained(heldItem));
                        if (liquidStack != null) {
                            String liquidCommandString = liquidStack.toCommandString();
                            ClipboardHelper.sendMessageWithCopy(player, "Contains Liquid \u00A72" + liquidCommandString, liquidCommandString);
                        }
                        
                        // adds the oredict names if it has some
                        
                        if(!oreDictNames.isEmpty()) {
                            
                            sender.sendMessage(new TextComponentString("\u00A73OreDict Entries:"));
                            for(String oreName : oreDictNames) {
                                ClipboardHelper.sendMessageWithCopy(player, "    \u00A7e- \u00A7b" + oreName, "<ore:" + oreName + ">");
                            }
                        } else {
                            sender.sendMessage(new TextComponentString("\u00A73No OreDict Entries"));
                        }
                        
                        
                    } else {
                        // if hand is empty, tries to get oreDict of block
                        RayTraceResult rayTraceResult = CommandUtils.getPlayerLookat(player, 100);
                        
                        if(rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
                            BlockPos blockPos = rayTraceResult.getBlockPos();
                            IBlockState block = server.getEntityWorld().getBlockState(blockPos);
                            
                            
                            int meta = block.getBlock().getMetaFromState(block);
                            String blockName = "<" + block.getBlock().getRegistryName() + (meta == 0 ? "" : ":" + meta) + ">";
                            ClipboardHelper.copyStringPlayer(player, blockName);
                            
                            ClipboardHelper.sendMessageWithCopy(player, "Block \u00A72" + blockName + " \u00A7rat \u00A79[" + blockPos.getX() + ", " + blockPos.getY() + ", " + blockPos.getZ() + "]\u00A7r", blockName);
                            
                            // adds the oreDict names if it has some
                            try {
                                
                                List<String> oreDictNames = CommandUtils.getOreDictOfItem(new ItemStack(block.getBlock(), 1, block.getBlock().getMetaFromState(block)));
                                if(!oreDictNames.isEmpty()) {
                                    sender.sendMessage(new TextComponentString("\u00A73OreDict Entries:"));
                                    
                                    for(String oreName : oreDictNames) {
                                        ClipboardHelper.sendMessageWithCopy(player, "    \u00A7e- \u00A7b" + oreName, "<ore:" + oreName + ">");
                                    }
                                } else {
                                    sender.sendMessage(new TextComponentString("\u00A73No OreDict Entries"));
                                }
                                // catches if it couldn't create a valid ItemStack for the Block
                            } catch(IllegalArgumentException e) {
                                sender.sendMessage(new TextComponentString("\u00A73No OreDict Entries"));
                            }
                            
                        } else {
                            sender.sendMessage(new TextComponentString("\u00A74Please hold an Item in your hand or look at a Block."));
                        }
                    }
                } else {
                    sender.sendMessage(new TextComponentString("This command can only be casted by a player inGame"));
                }
                
            }
            
            
        });
        
        CTChatCommand.registerCommand(new CraftTweakerCommand("json") {
            
            @Override
            protected void init() {
                setDescription(getClickableCommandText("\u00A72/ct json", "/ct json", true), getNormalMessage(" \u00A73Outputs the nbt of the item in your hand"), getNormalMessage(" \u00A73This format is in json format, different from the one used in ZenScript"), getClickableCommandText(" \u00A7a/ct json escaped", "/ct json escaped", true), getNormalMessage("  \u00A7bAutomatically escapes the string to use in json."));
            }
            
            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                
                if(sender.getCommandSenderEntity() instanceof EntityPlayer) {
                    // Gets player and held item
                    EntityPlayer player = (EntityPlayer) sender.getCommandSenderEntity();
                    ItemStack heldItem = player.getHeldItemMainhand();
                    
                    
                    // Tries to get name of held item first
                    if(!heldItem.isEmpty()) {
                        int meta = heldItem.getMetadata();
                        String itemName = "[" + heldItem.getItem().getRegistryName() + (meta == 0 ? "" : ":" + meta) + "]";
                        
                        String withNBT = "";
                        if(heldItem.serializeNBT().hasKey("tag")) {
                            String nbt = heldItem.serializeNBT().getTag("tag").toString();
                            
                            if(args.length == 1) {
                                nbt = new JsonPrimitive(nbt).toString();
                            }
                            
                            if(nbt.length() > 0)
                                withNBT = " NBT: [" + nbt + "]";
                        }
                        
                        ClipboardHelper.copyStringPlayer(player, itemName + withNBT);
                        ClipboardHelper.sendMessageWithCopy(player, "Item \u00A72" + itemName + "\u00A7a" + withNBT, itemName + withNBT);
                    } else {
                        sender.sendMessage(new TextComponentString("\u00A74Please hold an Item in your hand."));
                    }
                } else {
                    sender.sendMessage(new TextComponentString("This command can only be casted by a player inGame"));
                }
                
            }
            
            
        });
        
        CTChatCommand.registerCommand(new CraftTweakerCommand("mods") {
            
            @Override
            protected void init() {
                setDescription(getClickableCommandText("\u00A72/ct mods", "/ct mods", true), getNormalMessage(" \u00A73Outputs all active mod IDs and versions in the game"));
            }
            
            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                CraftTweakerAPI.logCommand("Mods list:");
                for(IMod mod : CraftTweakerAPI.loadedMods) {
                    String message = "\u00A75" + mod.getId() + "\u00A7r - \u00A7d" + mod.getName() + "\u00A7 - " + mod.getVersion();
                    String messageWithoutColor = mod.getId() + " - " + mod.getName() + " - " + mod.getVersion();
                    sender.sendMessage(new TextComponentString(message));
                    CraftTweakerAPI.logCommand(messageWithoutColor);
                }
                
                sender.sendMessage(getLinkToCraftTweakerLog("List of Mods generated", sender));
            }
        });
        
        CTChatCommand.registerCommand(new CraftTweakerCommand("oredict") {
            
            @Override
            protected void init() {
                setDescription(getClickableCommandText("\u00A72/ct oredict", "/ct oredict", true), getNormalMessage(" \u00A73Outputs all ore dictionary entries in the game to the crafttweaker log"), getClickableCommandText("\u00A7a/ct oredict <name>", "/ct oredict ", false), getNormalMessage("  \u00A7bOutputs all items in the given ore dictionary entry to the crafttweaker log"));
            }
            
            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                
                if(args.length > 0) {
                    String entryName = args[0];
                    IOreDictEntry entry = CraftTweakerAPI.oreDict.get(entryName);
                    if(entry.isEmpty()) {
                        sender.sendMessage(new TextComponentString("Entry doesn't exist"));
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
                sender.sendMessage(getLinkToCraftTweakerLog("OreDict list generated", sender));
            }
        });
        
        CTChatCommand.registerCommand(new CraftTweakerCommand("seeds") {
            
            @Override
            protected void init() {
                setDescription(getClickableCommandText("\u00A72/ct seeds", "/ct seeds", true), getNormalMessage(" \u00A73Prints all seeds registered"), getNormalMessage(" \u00A73for tall grass"));
            }
            
            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                CraftTweakerAPI.logCommand("Seeds:");
                for(WeightedItemStack seed : CraftTweakerAPI.vanilla.getSeeds().getSeeds()) {
                    String itemname = "<" + seed.getStack().getName() + ":" + seed.getStack().getDamage() + ">";
                    String message = "\u00A72" + itemname + "\u00A7r - \u00A7e" + (int) seed.getChance();
                    sender.sendMessage(getCopyMessage(message, itemname));
                    CraftTweakerAPI.logCommand("Seed: " + message);
                }
                
                sender.sendMessage(getLinkToCraftTweakerLog("Seed list generated", sender));
            }
        });
        
        CTChatCommand.registerCommand(new CraftTweakerCommand("wiki") {
            @Override
            protected void init() {
                setDescription(getClickableCommandText("\u00A72/ct wiki", "/ct wiki", true), getNormalMessage(" \u00A73Opens your browser with the wiki"));
            }
            
            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                String url = "https://docs.blamejared.com/1.12";
                if(sender.getCommandSenderEntity() instanceof EntityPlayer) {
                    MCPlayer player = new MCPlayer((EntityPlayer) sender.getCommandSenderEntity());
                    player.openBrowser(url);
                    sender.sendMessage(getClickableBrowserLinkText(url, url));
                    
                } else {
                    sender.sendMessage(new TextComponentString(url));
                }
            }
        });
        
        CTChatCommand.registerCommand(new CraftTweakerCommand("bugs") {
            @Override
            protected void init() {
                setDescription(getClickableCommandText("\u00A72/ct bugs", "/ct bugs", true), getNormalMessage(" \u00A73Opens your browser with the GitHub bug tracker"));
            }
            
            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                if(sender.getCommandSenderEntity() instanceof EntityPlayer) {
                    MCPlayer player = new MCPlayer((EntityPlayer) sender.getCommandSenderEntity());
                    player.openBrowser("https://github.com/CraftTweaker/CraftTweaker/issues");
                    sender.sendMessage(getClickableBrowserLinkText("https://github.com/CraftTweaker/CraftTweaker/issues", "https://github.com/CraftTweaker/CraftTweaker/issues"));
                    
                } else {
                    sender.sendMessage(new TextComponentString("https://github.com/CraftTweaker/CraftTweaker/issues"));
                }
            }
        });
        
        CTChatCommand.registerCommand(new CraftTweakerCommand("discord") {
            @Override
            protected void init() {
                setDescription(getClickableCommandText("\u00A72/ct discord", "/ct discord", true), getNormalMessage(" \u00A73Opens your browser with a link to the Discord server"));
            }
            
            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                if(sender.getCommandSenderEntity() instanceof EntityPlayer) {
                    MCPlayer player = new MCPlayer((EntityPlayer) sender.getCommandSenderEntity());
                    player.openBrowser("https://discord.blamejared.com/");
                    sender.sendMessage(getClickableBrowserLinkText("https://discord.blamejared.com/", "https://discord.blamejared.com/"));
                    
                } else {
                    sender.sendMessage(new TextComponentString("https://discord.blamejared.com/"));
                }
            }
        });
        
        CTChatCommand.registerCommand(new CraftTweakerCommand("docs") {
            @Override
            protected void init() {
                setDescription(getClickableCommandText("\u00A72/ct docs", "/ct docs", true), getNormalMessage(" \u00A73Opens your browser with the docs"));
            }
            
            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                if(sender.getCommandSenderEntity() instanceof EntityPlayer) {
                    MCPlayer player = new MCPlayer((EntityPlayer) sender.getCommandSenderEntity());
                    player.openBrowser("http://docs.blamejared.com/en/");
                    sender.sendMessage(getClickableBrowserLinkText("http://docs.blamejared.com/en/", "http://docs.blamejared.com/en/"));
                    
                } else {
                    sender.sendMessage(new TextComponentString("http://docs.blamejared.com/en/"));
                }
            }
        });
        
        CTChatCommand.registerCommand(new CraftTweakerCommand("biomes") {
            
            @Override
            protected void init() {
                setDescription(getClickableCommandText("\u00A72/ct biomes", "/ct biomes", true), getNormalMessage(" \u00A73Lists all the biomes in the game"));
                
            }
            
            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                CraftTweakerAPI.logCommand("Biomes:");
                for(IBiome biome : CraftTweakerAPI.game.getBiomes()) {
                    CraftTweakerAPI.logCommand("-" + biome.getId() + "\t" + biome.getName());
                }
                
                sender.sendMessage(getLinkToCraftTweakerLog("Biome list generated", sender));
            }
        });
        
        CTChatCommand.registerCommand(new CraftTweakerCommand("biomeTypes") {
            
            @Override
            protected void init() {
                setDescription(getClickableCommandText("\u00A72/ct biomeTypes", "/ct biomeTypes", true), getNormalMessage(" \u00A73Lists all the biome types in the game"));
                
            }
            
            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                CraftTweakerAPI.logCommand("Biome Types:");
                BracketHandlerBiomeType.biomeTypes.forEach((s, iBiomeType) -> {
                    CraftTweakerAPI.logCommand("-" + s);
                });
                
                sender.sendMessage(getLinkToCraftTweakerLog("Biome Types list generated", sender));
            }
        });
        
        CTChatCommand.registerCommand(new CraftTweakerCommand("enchants") {
            @Override
            protected void init() {
                setDescription(getClickableCommandText("\u00A72/ct enchants", "/ct enchants", true), getNormalMessage(" \u00A73Lists all the enchants in the game."));
            }
            
            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                CraftTweakerAPI.logCommand("Enchants:");
                BracketHandlerEnchantments.enchantments.forEach((s, iEnchant) -> {
                    String name = ((Enchantment) iEnchant.getInternal()).getTranslatedName(1);
                    CraftTweakerAPI.logCommand(String.format("-<enchantment:%s> - %s (at level 1, max level %d, treasure: %s, enchant id %d)", s, name, iEnchant.getMaxLevel(), (iEnchant.isTreasureEnchantment()) ? "yes" : "no", iEnchant.getID()));
                });
                
                sender.sendMessage(getLinkToCraftTweakerLog("Enchants list generated", sender));
            }
        });
        
        CTChatCommand.registerCommand(new CraftTweakerCommand("blockinfo") {
            
            @Override
            protected void init() {
                setDescription(getClickableCommandText("\u00A72/ct blockinfo", "/ct blockinfo", true), getNormalMessage(" \u00A73Activates or deactivates block reader. In block info mode,"), getNormalMessage(" \u00A73right-click a block to see ID, meta and tile entity data"));
            }
            
            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                if(sender.getCommandSenderEntity() instanceof EntityPlayer) {
                    MCPlayer player = new MCPlayer((EntityPlayer) sender.getCommandSenderEntity());
                    
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
                } else {
                    sender.sendMessage(new TextComponentString("This Command can only be performed from a Player(InGame)"));
                }
            }
        });
        
        CTChatCommand.registerCommand(new CraftTweakerCommand("copy") {
            
            @Override
            protected void init() {
                setDescription(getClickableCommandText("\u00A72/ct copy", "/ct copy", false), getNormalMessage(" \u00A73Copies the provided string behind it"));
            }
            
            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                ClipboardHelper.copyCommandRun(sender, args);
            }
        });
        
        CTChatCommand.registerCommand(new CraftTweakerCommand("nbt") {
            
            @Override
            protected void init() {
                setDescription(getClickableCommandText("\u00A72/ct nbt", "/ct nbt", true), getNormalMessage(" \u00A73Shows the NBT of the block you are looking at"), getNormalMessage(" \u00A73or the item you are holding"));
            }
            
            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                
                if(sender.getCommandSenderEntity() instanceof EntityPlayer) {
                    
                    // Gets player and held item
                    EntityPlayer player = (EntityPlayer) sender.getCommandSenderEntity();
                    ItemStack heldItem = player.getHeldItemMainhand();
                    
                    
                    // Tries to get name of held item first
                    if(heldItem != ItemStack.EMPTY) {
                        
                        String itemName = "<" + heldItem.getItem().getRegistryName() + ":" + heldItem.getMetadata() + ">";
                        
                        String nbt = "";
                        if(heldItem.serializeNBT().hasKey("tag")) {
                            nbt = NBTConverter.from(heldItem.serializeNBT().getTag("tag"), false).toString();
                        }
                        String withNBT = "";
                        if(nbt.length() > 0)
                            withNBT = ".withTag(" + nbt + ")";
                        
                        ClipboardHelper.copyStringPlayer(player, itemName + withNBT);
                        
                        ClipboardHelper.sendMessageWithCopy(player, "Item \u00A72" + itemName, itemName + withNBT);
                        
                        
                        // adds the oredict names if it has some
                        if(nbt.length() > 0) {
                            sender.sendMessage(new TextComponentString("\u00A73NBT-Data:"));
                            ClipboardHelper.sendMessageWithCopy(player, NBTUtils.getAppealingString(nbt), nbt);
                            
                        } else {
                            sender.sendMessage(new TextComponentString("\u00A73No NBT Data"));
                        }
                        
                        
                        // if hand is empty, tries to get oreDict of block
                    } else {
                        RayTraceResult rayTraceResult = CommandUtils.getPlayerLookat(player, 100);
                        
                        if(rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
                            BlockPos blockPos = rayTraceResult.getBlockPos();
                            IBlockState block = server.getEntityWorld().getBlockState(blockPos);
                            
                            ClipboardHelper.sendMessageWithCopy(player, "Block \u00A72[" + block.getBlock().getRegistryName() + ":" + block.getBlock().getMetaFromState(block) + "] \u00A7rat \u00A79[" + blockPos.getX() + ", " + blockPos.getY() + ", " + blockPos.getZ() + "]\u00A7r", block.getBlock().getRegistryName() + ":" + block.getBlock().getMetaFromState(block));
                            
                            TileEntity te = server.getEntityWorld().getTileEntity(blockPos);
                            if(te != null) {
                                
                                sender.sendMessage(new TextComponentString("\u00A73NBT-Data:"));
                                
                                String nbt = NBTConverter.from(te.serializeNBT(), false).toString();
                                
                                ClipboardHelper.sendMessageWithCopy(player, NBTUtils.getAppealingString(nbt), nbt);
                            } else {
                                sender.sendMessage(new TextComponentString("\u00A73Block is no TileEntity and has no NBT"));
                            }
                        } else {
                            sender.sendMessage(new TextComponentString("\u00A74Please hold an Item in your hand or look at a Block."));
                        }
                    }
                }
            }
        });
        
        CTChatCommand.registerCommand(new CraftTweakerCommand("log") {
            @Override
            protected void init() {
                setDescription(getClickableCommandText("\u00A72/ct log", "/ct log", true), getNormalMessage(" \u00A73Sends a clickable link to open the crafttweaker.log"));
            }
            
            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                if(sender.getCommandSenderEntity() instanceof EntityPlayer) {
                    
                    sender.sendMessage(getLinkToCraftTweakerLog("", sender));
                } else {
                    sender.sendMessage(getNormalMessage("Command must be executed as a Player (inGame)"));
                }
            }
        });
        
        CTChatCommand.registerCommand(new CraftTweakerCommand("scripts") {
            @Override
            protected void init() {
                setDescription(getClickableCommandText("\u00A72/ct scripts", "/ct scripts", true), getNormalMessage(" \u00A73Sends a clickable link to open the scripts directory"));
            }
            
            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                if(sender.getCommandSenderEntity() instanceof EntityPlayer) {
                    
                    sender.sendMessage(getFileOpenText("Click to open the \u00A7a/scripts/ \u00A7rDirectory [\u00A76Click here to open\u00A7r]", new File("scripts/").getAbsolutePath()));
                } else {
                    sender.sendMessage(getNormalMessage("Path to the scripts: " + new File("scripts/").getAbsolutePath()));
                    sender.sendMessage(getNormalMessage("Command must be executed as a Player (inGame) to be clickable"));
                }
            }
        });
        
        CTChatCommand.registerCommand(new CraftTweakerCommand("syntax") {
            @Override
            protected void init() {
                setDescription(getClickableCommandText("\u00A72/ct syntax", "/ct syntax", true), getNormalMessage(" \u00A73Checks the Syntax of the scripts"), getNormalMessage(" \u00A73To see the effect you have to restart the game"), getNormalMessage(" \u00A73Will print errors of the Bracket Handler"));
            }
            
            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                if(args.length > 0 && args[0].equals("debug")) {
                    CraftTweakerAPI.tweaker.enableDebug();
                }
                
                sender.sendMessage(getNormalMessage("\u00A7bBeginning load of the scripts"));
                
                List<ScriptLoader> erroredLoaders = new ArrayList<>();
                for(ScriptLoader loader : CraftTweakerAPI.tweaker.getLoaders()) {
                    CraftTweakerAPI.tweaker.loadScript(true, loader);
                    if(loader.getLoaderStage() == ScriptLoader.LoaderStage.ERROR)
                        erroredLoaders.add(loader);
                }
                
                if(erroredLoaders.isEmpty()) {
                    sender.sendMessage(getNormalMessage("Syntax of scripts is \u00A7acorrect\u00A7r, to see the effect \u00A7erestart the game"));
                    sender.sendMessage(getNormalMessage("Please be advised that \u00A7bbrackets (<>) \u00A7rmay have \u00A74errored, see above."));
                    sender.sendMessage(getNormalMessage("If no errors appeared above everything was fine."));
                } else {
                    sender.sendMessage(getNormalMessage("These loaders failed to load correctly:"));
                    sender.sendMessage(getNormalMessage(erroredLoaders.stream().map(ScriptLoader::getMainName).collect(Collectors.joining(", "))));
                    sender.sendMessage(getLinkToCraftTweakerLog("\u00A74Syntax of the scripts is incorrect!", sender));
                }
            }
            
            @Override
            public List<String> getSubSubCommand(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
                List<String> commands = new ArrayList<>(1);
                commands.add("debug");
                return commands;
            }
        });
        
        CTChatCommand.registerCommand(new CraftTweakerCommand("zslint") {
            @Override
            protected void init() {
                setDescription(getClickableCommandText("\u00A72/ct zslint", "/ct zslint", true), getNormalMessage(" \u00A73Starts the ZsLint socket."));
            }
            
            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                sender.sendMessage(getNormalMessage("\u00A7bStarting socket."));
                CrTSocketHandler.enableSocket();
            }
        });
        
        CTChatCommand.registerCommand(new GiveCommand());
        
        CTChatCommand.registerCommand(new DumpZsCommand(new TargetLog(), new TargetHtml(), new TargetJson()));
        CTChatCommand.registerCommand(new HelpCommand());
        
        CTChatCommand.registerCommand(new NamesCommand());
    
    
        CTChatCommand.registerCommand(new CraftTweakerCommand("keyNames") {
    
            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                CraftTweakerAPI.logCommand("\"KeyName\" -> keyCode");
        
                Field[] fields = Keyboard.class.getFields();
                try {
                    for(Field field : fields) {
                        if(Modifier.isStatic(field.getModifiers()) && Modifier.isPublic(field.getModifiers()) && Modifier.isFinal(field.getModifiers()) && field.getType().equals(int.class) && field.getName().startsWith("KEY_") && !field.getName().endsWith("WIN")) { /* Don't use deprecated names */
                    
                            int key = field.getInt(null);
                            String name = field.getName().substring(4);
                            CraftTweakerAPI.logCommand("\"" + name  + "\" -> " + key);
                        }
                
                    }
                } catch(Exception e) {
                }
        
                sender.sendMessage(getLinkToCraftTweakerLog("List of Key names generated", sender));
            }
        
            @Override
            protected void init() {
                setDescription(getClickableCommandText("\u00A72/ct keyNames", "/ct keyNames", true), getNormalMessage(" \u00A73Outputs a list of all LWJGL keynames to their respective KeyCodes in the game to the crafttweaker.log"));
            }
        });
    }
    
    
    private static class ItemComparator implements Comparator<Item>, Serializable {
        
        private static final long serialVersionUID = 5425653624832997108L;

        @Override
        public int compare(Item o1, Item o2) {
            return o1.getRegistryName().toString().compareTo(o2.getRegistryName().toString());
        }
    }
    
    private static class PotionComparator implements Comparator<IPotion>, Serializable {
        
        private static final long serialVersionUID = -56375350098392167L;

        @Override
        public int compare(IPotion o1, IPotion o2) {
            return o1.name().compareTo(o2.name());
        }
    }
    
    
}
