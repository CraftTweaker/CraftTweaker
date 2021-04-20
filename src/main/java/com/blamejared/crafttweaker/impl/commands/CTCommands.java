package com.blamejared.crafttweaker.impl.commands;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerRegistry;
import com.blamejared.crafttweaker.api.ScriptLoadingOptions;
import com.blamejared.crafttweaker.api.data.StringConverter;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.api.text.FormattedTextComponent;
import com.blamejared.crafttweaker.api.util.StringUtils;
import com.blamejared.crafttweaker.api.zencode.impl.loaders.LoaderActions;
import com.blamejared.crafttweaker.impl.brackets.BracketHandlers;
import com.blamejared.crafttweaker.impl.brackets.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.impl.commands.script_examples.ExamplesCommand;
import com.blamejared.crafttweaker.impl.events.CTEventHandler;
import com.blamejared.crafttweaker.impl.fluid.MCFluidStackMutable;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.crafttweaker.impl.loot.CTLootManager;
import com.blamejared.crafttweaker.impl.network.PacketHandler;
import com.blamejared.crafttweaker.impl.network.messages.MessageCopy;
import com.blamejared.crafttweaker.impl.network.messages.MessageOpen;
import com.blamejared.crafttweaker.impl.recipes.writers.DefaultRecipeWriter;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.blamejared.crafttweaker.impl.tag.manager.TagManagerBlock;
import com.blamejared.crafttweaker.impl.tag.manager.TagManagerItem;
import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import com.blamejared.crafttweaker.impl_native.blocks.ExpandBlock;
import com.blamejared.crafttweaker.impl_native.blocks.ExpandBlockState;
import com.blamejared.crafttweaker.impl_native.entity.ExpandPlayerEntity;
import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.block.BlockState;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.ArgumentSerializer;
import net.minecraft.command.arguments.ArgumentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import org.openzen.zenscript.lexer.ParseException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class CTCommands {
    
    private static final Map<String, CommandImpl> COMMANDS = new TreeMap<>(String::compareTo);
    private static final int commandsPerPage = 4;
    public static LiteralArgumentBuilder<CommandSource> root = Commands.literal("ct");
    public static LiteralArgumentBuilder<CommandSource> rootAlternative = Commands.literal("crafttweaker");
    
    
    public static void initArgumentTypes(){
        ArgumentTypes.register("crafttweaker:item_argument", CTItemArgument.class, new ArgumentSerializer<>(() -> CTItemArgument.INSTANCE));
    }
    public static void init(CommandDispatcher<CommandSource> dispatcher) {
    
        
        registerCustomCommand(Commands.literal("copy")
                .then(Commands.argument("toCopy", StringArgumentType.string()).executes(context -> {
                    String toCopy = context.getArgument("toCopy", String.class);
                    ServerPlayerEntity entity = context.getSource().asPlayer();
                    PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> entity), new MessageCopy(toCopy));
                    send(new StringTextComponent("Copied!"), entity);
                    return 0;
                })));
        
        registerCommand(new CommandImpl("reload", "Points people to /reload", (CommandCallerPlayer) (player, stack) -> {
            send(run(new StringTextComponent(color("CraftTweaker reload has been deprecated! Use the vanilla /reload instead!", TextFormatting.AQUA)), "/reload"), player);
            return 0;
        }));
        
        registerCommand(new CommandImpl("recipes", "Outputs information on all recipes.", (CommandCallerPlayer) (player, stack) -> {
            
            CraftTweakerAPI.logInfo("Dumping all recipes!");
            player.world.getRecipeManager().recipes.forEach((iRecipeType, map) -> {
                
                IRecipeManager recipeManager = RecipeTypeBracketHandler.getRecipeManager(iRecipeType.toString());
                if(recipeManager == null) {
                    // Scripts for example don't have a recipe manager
                    return;
                }
                
                StringBuilder builder = new StringBuilder("Recipe type: `").append(recipeManager.getCommandString())
                        .append("`\n");
                
                map.values().stream().sorted((o1, o2) -> {
                    int typeEqual = Objects.requireNonNull(o1.getSerializer()
                            .getRegistryName())
                            .compareTo(Objects.requireNonNull(o2.getSerializer()
                                    .getRegistryName()));
                    
                    if(typeEqual == 0) {
                        return o1.getId().compareTo(o2.getId());
                    }
                    return typeEqual;
                }).forEach(iRecipe -> {
                    CraftTweakerAPI.getRecipeWriters()
                            .getOrDefault(iRecipe.getClass(), DefaultRecipeWriter.INSTANCE)
                            .write(recipeManager, builder, iRecipe);
                    builder.append("\n");
                });
                CraftTweakerAPI.logDump(builder.toString());
            });
            send(new StringTextComponent(color("Recipe list generated! Check the crafttweaker.log file!", TextFormatting.GREEN)), player);
            return 0;
        }));
        
        registerCommand("recipes", new CommandImpl("hand", "Outputs information on all Recipes for the held item", (CommandCallerPlayer) (player, stack) -> {
            if(stack.isEmpty()) {
                // Only done because *a lot* of mods return empty ItemStacks as outputs
                send(new StringTextComponent(color("Cannot get recipes for an empty ItemStack!", TextFormatting.RED)), player);
                return 0;
            }
            CraftTweakerAPI.logInfo("Dumping all recipes that output %s!", new MCItemStackMutable(stack).getCommandString());
            IItemStack workingStack = new MCItemStackMutable(stack).setAmount(1);
            player.world.getRecipeManager().recipes.forEach((iRecipeType, map) -> {
                
                AtomicBoolean handled = new AtomicBoolean(false);
                IRecipeManager recipeManager = RecipeTypeBracketHandler.getRecipeManager(iRecipeType.toString());
                if(recipeManager == null) {
                    // Scripts for example don't have a recipe manager
                    return;
                }
                
                StringBuilder builder = new StringBuilder("Recipe type: `").append(recipeManager.getCommandString())
                        .append("`\n");
                
                
                map.values()
                        .stream()
                        .filter(iRecipe -> workingStack.matches(new MCItemStackMutable(iRecipe.getRecipeOutput())))
                        .sorted((o1, o2) -> {
                            int typeEqual = Objects.requireNonNull(o1.getSerializer()
                                    .getRegistryName())
                                    .compareTo(Objects.requireNonNull(o2.getSerializer()
                                            .getRegistryName()));
                            
                            if(typeEqual == 0) {
                                return o1.getId().compareTo(o2.getId());
                            }
                            return typeEqual;
                        })
                        .forEach(iRecipe -> {
                            handled.set(true);
                            CraftTweakerAPI.getRecipeWriters()
                                    .getOrDefault(iRecipe.getClass(), DefaultRecipeWriter.INSTANCE)
                                    .write(recipeManager, builder, iRecipe);
                            builder.append("\n");
                        });
                if(handled.get()) {
                    CraftTweakerAPI.logDump(builder.toString());
                }
            });
            send(new StringTextComponent(color("Recipe list generated! Check the crafttweaker.log file!", TextFormatting.GREEN)), player);
            return 0;
        }));
        
        registerCommand(new CommandImpl("hand", "Outputs the name and tags (if any) of the item in your hand", (CommandCallerPlayer) (player, stack) -> {
            
            String string = new MCItemStackMutable(stack).getCommandString();
            ITextComponent copy = copy(new FormattedTextComponent("Item: %s", color(string, TextFormatting.GREEN)), string);
            send(copy, player);
            if(player instanceof ServerPlayerEntity) {
                PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new MessageCopy(string));
            }
            if(stack.getItem() instanceof BlockItem) {
                BlockItem item = (BlockItem) stack.getItem();
                string = ExpandBlock.getCommandString(item.getBlock());
                copy = copy(new FormattedTextComponent("Block: %s", color(string, TextFormatting.GREEN)), string);
                send(copy, player);
                
                string = ExpandBlockState.getCommandString(item.getBlock().getDefaultState());
                copy = copy(new FormattedTextComponent("BlockState: %s", color(string, TextFormatting.GREEN)), string);
                send(copy, player);
                
            }
            
            if(stack.getItem() instanceof BucketItem) {
                BucketItem item = (BucketItem) stack.getItem();
                if(item.getFluid() != Fluids.EMPTY) {
                    BlockState blockState = item.getFluid().getDefaultState().getBlockState();
                    
                    string = ExpandBlockState.getCommandString(blockState);
                    copy = copy(new FormattedTextComponent("Fluid BlockState: %s", color(string, TextFormatting.GREEN)), string);
                    send(copy, player);
                }
            }
            stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(iFluidHandlerItem -> {
                for(int i = 0; i < iFluidHandlerItem.getTanks(); i++) {
                    FluidStack fluidInTank = iFluidHandlerItem.getFluidInTank(i);
                    if(fluidInTank.isEmpty()) {
                        continue;
                    }
                    String str = new MCFluidStackMutable(fluidInTank).getCommandString();
                    ITextComponent nCopy = copy(new FormattedTextComponent("Fluid: %s", color(str, TextFormatting.GREEN)), str);
                    send(nCopy, player);
                }
            });
            
            Collection<ResourceLocation> tags = ItemTags.getCollection().getOwningTags(stack.getItem());
            
            if(tags.size() > 0) {
                
                send(copy(new FormattedTextComponent(color("Item Tag Entries", TextFormatting.DARK_AQUA)), tags.stream()
                        .map(resourceLocation -> new MCTag<>(resourceLocation, TagManagerItem.INSTANCE).getCommandString())
                        .collect(Collectors.joining(", "))), player);
                
                tags.stream()
                        .map(resourceLocation -> new MCTag<>(resourceLocation, TagManagerItem.INSTANCE).getCommandString())
                        .forEach(commandString -> send(copy(new FormattedTextComponent("\t%s %s", color("-", TextFormatting.YELLOW), color(commandString, TextFormatting.AQUA)), commandString), player));
            }
            
            if(stack.getItem() instanceof BlockItem) {
                BlockItem item = (BlockItem) stack.getItem();
                tags = BlockTags.getCollection().getOwningTags(item.getBlock());
                
                if(tags.size() > 0) {
                    send(copy(new FormattedTextComponent(color("Block Tag Entries", TextFormatting.DARK_AQUA)), tags.stream()
                            .map(resourceLocation -> new MCTag<>(resourceLocation, TagManagerBlock.INSTANCE).getCommandString())
                            .collect(Collectors.joining(", "))), player);
                    
                    tags.stream()
                            .map(resourceLocation -> new MCTag<>(resourceLocation, TagManagerBlock.INSTANCE).getCommandString())
                            .forEach(commandString -> send(copy(new FormattedTextComponent("\t%s %s", color("-", TextFormatting.YELLOW), color(commandString, TextFormatting.AQUA)), commandString), player));
                }
            }
            return 0;
        }));
        registerCommand("hand", new CommandImpl("registryName", "Outputs the registry name of the item in your hand", (CommandCallerPlayer) (player, stack) -> {
            //It cannot be null because we can't get unregistered items here
            //noinspection ConstantConditions
            String registryName = stack.getItem().getRegistryName().toString();
            ITextComponent copy = copy(new FormattedTextComponent("Item: %s", color(registryName, TextFormatting.GREEN)), registryName);
            send(copy, player);
            if(player instanceof ServerPlayerEntity) {
                PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new MessageCopy(registryName));
            }
            return 0;
        }));
        registerCommand("hand", new CommandImpl("tags", "Outputs the tags of the item in your hand", (CommandCallerPlayer) (player, stack) -> {
            
            Collection<ResourceLocation> tags = ItemTags.getCollection().getOwningTags(stack.getItem());
            if(tags.isEmpty()) {
                send(new StringTextComponent("Item has no tags"), player);
                return 0;
            }
            
            
            send(copy(new FormattedTextComponent(color("Tag Entries", TextFormatting.DARK_AQUA)), tags.stream()
                    .map(ResourceLocation::toString)
                    .collect(Collectors.joining(", "))), player);
            
            tags.stream()
                    .map(resourceLocation -> new MCTag<>(resourceLocation, TagManagerItem.INSTANCE).getCommandString())
                    .forEach(commandString -> send(copy(new FormattedTextComponent("\t%s %s", color("-", TextFormatting.YELLOW), color(commandString, TextFormatting.AQUA)), commandString), player));
            
            if(player instanceof ServerPlayerEntity) {
                //noinspection OptionalGetWithoutIsPresent
                PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new MessageCopy(tags
                        .stream()
                        .map(location -> new MCTag<>(location, TagManagerItem.INSTANCE))
                        .findFirst()
                        .get()
                        .getCommandString()));
            }
            return 0;
        }));
        
        registerCommand("hand", new CommandImpl("vanilla", "Outputs the name and tags (if any) of the item in your hand in the vanilla format", (CommandCallerPlayer) (player, stack) -> {
            
            
            String string = stack.getItem().getRegistryName().toString();
            ITextComponent copy = copy(new FormattedTextComponent("Item: %s", color(string, TextFormatting.GREEN)), string);
            send(copy, player);
            
            if(stack.hasTag()) {
                copy = copy(new FormattedTextComponent("NBT: %s", color(stack.getTag()
                        .getString(), TextFormatting.GREEN)), stack.getTag().getString());
                send(copy, player);
            }
            
            if(player instanceof ServerPlayerEntity) {
                PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new MessageCopy(string));
            }
            
            if(stack.getItem() instanceof BucketItem) {
                BucketItem item = (BucketItem) stack.getItem();
                if(item.getFluid() != Fluids.EMPTY) {
                    string = item.getFluid().getRegistryName().toString();
                    copy = copy(new FormattedTextComponent("Fluid: %s", color(string, TextFormatting.GREEN)), string);
                    send(copy, player);
                }
            }
            
            Collection<ResourceLocation> tags = ItemTags.getCollection().getOwningTags(stack.getItem());
            
            if(tags.size() > 0) {
                send(copy(new FormattedTextComponent(color("Item Tag Entries", TextFormatting.DARK_AQUA)), tags.stream()
                        .map(ResourceLocation::toString)
                        .collect(Collectors.joining(", "))), player);
                
                tags.stream()
                        .map(ResourceLocation::toString)
                        .forEach(commandString -> send(copy(new FormattedTextComponent("\t%s %s", color("- ", TextFormatting.YELLOW), color("#" + commandString, TextFormatting.AQUA)), "#" + commandString), player));
            }
            
            if(stack.getItem() instanceof BlockItem) {
                BlockItem item = (BlockItem) stack.getItem();
                tags = BlockTags.getCollection().getOwningTags(item.getBlock());
                
                if(tags.size() > 0) {
                    send(copy(new FormattedTextComponent(color("Block Tag Entries", TextFormatting.DARK_AQUA)), tags.stream()
                            .map(resourceLocation -> "#" + resourceLocation.toString())
                            .collect(Collectors.joining(", "))), player);
                    
                    tags.stream()
                            .map(ResourceLocation::toString)
                            .forEach(commandString -> send(copy(new FormattedTextComponent("\t%s %s", color("-", TextFormatting.YELLOW), color(commandString, TextFormatting.AQUA)), commandString), player));
                }
            }
            return 0;
        }));
        
        
        registerCommand(new CommandImpl("inventory", "Outputs the names of the item in your inventory", (CommandCallerPlayer) (player, stack) -> {
            StringBuilder builder = new StringBuilder("Inventory items").append("\n");
            for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
                ItemStack slot = player.inventory.getStackInSlot(i);
                if(slot.isEmpty()) {
                    continue;
                }
                builder.append(new MCItemStackMutable(slot).getCommandString()).append("\n");
            }
            CraftTweakerAPI.logDump(builder.toString());
            send(new StringTextComponent(color("Inventory list generated! Check the crafttweaker.log file!", TextFormatting.GREEN)), player);
            return 0;
        }));
        
        registerCommand("inventory", new CommandImpl("tags", "Outputs the tags of the item in your inventory", (CommandCallerPlayer) (player, stack) -> {
            StringBuilder builder = new StringBuilder("Inventory item tags").append("\n");
            for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
                ItemStack slot = player.inventory.getStackInSlot(i);
                if(slot.isEmpty()) {
                    continue;
                }
                builder.append(new MCItemStackMutable(slot).getCommandString()).append("\n");
                
                Collection<ResourceLocation> tags = ItemTags.getCollection().getOwningTags(slot.getItem());
                if(tags.isEmpty()) {
                    builder.append("- No tags").append("\n");
                    continue;
                }
                tags.stream()
                        .map(resourceLocation -> new MCTag<>(resourceLocation, TagManagerItem.INSTANCE).getCommandString())
                        .forEach(s -> builder.append("-").append(s).append("\n"));
            }
            CraftTweakerAPI.logDump(builder.toString());
            send(new StringTextComponent(color("Inventory tag list generated! Check the crafttweaker.log file!", TextFormatting.GREEN)), player);
            return 0;
        }));
        
        registerCommand(new CommandImpl("log", "Opens the log file", (CommandCallerPlayer) (player, stack) -> {
            PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new MessageOpen(new File("logs/crafttweaker.log")
                    .toURI()
                    .toString()));
            return 0;
        }));
        registerCommand(new CommandImpl("scripts", "Opens the scripts folder", (CommandCallerPlayer) (player, stack) -> {
            PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new MessageOpen(CraftTweakerAPI.SCRIPT_DIR
                    .toURI()
                    .toString()));
            return 0;
        }));
        
        registerCommand(new CommandImpl("syntax", "Checks the syntax of the scripts", (CommandCallerPlayer) (player, stack) -> {
            //TODO: get loader name from '/ct syntax loaderName'?
            for(String knownLoaderName : LoaderActions.getKnownLoaderNames()) {
                final String message = color("Starting loading scripts for loader '" + knownLoaderName + "'", TextFormatting.YELLOW);
                send(new StringTextComponent(message), player);
                final ScriptLoadingOptions options = new ScriptLoadingOptions().setLoaderName(knownLoaderName);
                CraftTweakerAPI.loadScripts(options);
            }
            
            return 0;
        }));
        
        registerCommand(new CommandImpl("format", "Checks the syntax of the scripts and formats them into another folder.", (CommandCallerPlayer) (player, stack) -> {
            for(String knownLoaderName : LoaderActions.getKnownLoaderNames()) {
                final String message = color("Loading and formatting scripts for loader '" + knownLoaderName + "'", TextFormatting.YELLOW);
                send(new StringTextComponent(message), player);
                final ScriptLoadingOptions options = new ScriptLoadingOptions().setLoaderName(knownLoaderName);
                CraftTweakerAPI.loadScripts(options.format());
            }
            return 0;
        }));
        
        
        registerCommand(new CommandImpl("dumpBrackets", "Dumps available Bracket Expressions into the /ct_dumps folder", source -> {
            final File folder = new File("ct_dumps");
            if(!folder.exists() && !folder.mkdir()) {
                CraftTweakerAPI.logError("Could not create output folder %s", folder);
            }
            
            CraftTweakerRegistry.getBracketDumpers().forEach((name, dumpSupplier) -> {
                final String dumpedFileName = dumpSupplier.getDumpedFileName() + ".txt";
                try(final PrintWriter writer = new PrintWriter(new FileWriter(new File(folder, dumpedFileName), false))) {
                    dumpSupplier.getDumpedValuesStream().sorted().forEach(writer::println);
                } catch(IOException e) {
                    CraftTweakerAPI.logThrowing("Error writing to file '%s'", e, dumpedFileName);
                }
            });
            
            send(new StringTextComponent("Files Created"), source.getSource());
            
            return 0;
        }));
        
        registerCommand(new CommandImpl("blockInfo", "Activates or deactivates the block reader. In block info mode, right-clicking a block will tell you it's name, metadata and Tile Entity data if applicable.", (CommandCallerPlayer) (player, stack) -> {
            if(CTEventHandler.BLOCK_INFO_PLAYERS.contains(player)) {
                CTEventHandler.BLOCK_INFO_PLAYERS.remove(player);
                ExpandPlayerEntity.sendMessage(player, MCTextComponent.createStringTextComponent("Block info mode deactivated."));
                
                if(CTEventHandler.BLOCK_INFO_PLAYERS.isEmpty()) {
                    MinecraftForge.EVENT_BUS.unregister(CTEventHandler.ListenBlockInfo.INSTANCE);
                }
            } else {
                if(CTEventHandler.BLOCK_INFO_PLAYERS.isEmpty()) {
                    MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, false, PlayerInteractEvent.RightClickBlock.class, CTEventHandler.ListenBlockInfo.INSTANCE);
                }
                
                CTEventHandler.BLOCK_INFO_PLAYERS.add(player);
                ExpandPlayerEntity.sendMessage(player, MCTextComponent.createStringTextComponent("Block info mode activated. Right-click a block to see its data."));
            }
            return 0;
        }));
        
        
        registerCommand(new CommandImpl("discord", "Opens a link to discord", (CommandCallerPlayer) (player, stack) -> {
            PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new MessageOpen("https://discord.blamejared.com"));
            return 0;
        }));
        
        registerCommand(new CommandImpl("issues", "Opens a link to the issue tracker", (CommandCallerPlayer) (player, stack) -> {
            PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new MessageOpen("https://github.com/CraftTweaker/CraftTweaker/issues"));
            return 0;
        }));
        
        registerCommand(new CommandImpl("patreon", "Opens a link to patreon", (CommandCallerPlayer) (player, stack) -> {
            PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new MessageOpen("https://patreon.com/jaredlll08"));
            return 0;
        }));
        
        registerCommand(new ExamplesCommand());
        
        registerCommand(new CommandImpl("dump", "Dumps available sub commands for the dump command", (CommandCallerPlayer) (player, stack) -> {
            send(new StringTextComponent("Dump types: "), player);
            COMMANDS.get("dump")
                    .getSubCommands()
                    .forEach((s, command) -> send(run(new FormattedTextComponent("- " + color(s, TextFormatting.GREEN)), "/ct dump " + s), player));
            return 0;
        }));
        
        
        registerDump("recipes", "Outputs the names of all registered recipes", (CommandCallerPlayer) (player, stack) -> {
            for(IRecipeType<?> type : Registry.RECIPE_TYPE) {
                CraftTweakerAPI.logDump(type.toString());
                for(ResourceLocation location : player.world.getRecipeManager().recipes.getOrDefault(type, new HashMap<>())
                        .keySet()) {
                    CraftTweakerAPI.logDump(location.toString());
                }
            }
            send(new StringTextComponent(color("Recipe list generated! Check the crafttweaker.log file!", TextFormatting.GREEN)), player);
            return 0;
        });
        
        
        registerDump("loot_modifiers", "Outputs the names of all registered loot modifiers", (CommandCallerPlayer) (player, stack) -> {
            CTLootManager.LOOT_MANAGER.getModifierManager().getAllNames().forEach(CraftTweakerAPI::logDump);
            send(new StringTextComponent(color("Loot modifiers list generated! Check the crafttweaker.log file!", TextFormatting.GREEN)), player);
            return 0;
        });
        
        registerDump("loot_tables", "Outputs the names of all registered loot tables", (CommandCallerPlayer) (player, stack) -> {
            ServerLifecycleHooks.getCurrentServer()
                    .getLootTableManager()
                    .getLootTableKeys()
                    .stream()
                    .map(ResourceLocation::toString)
                    .sorted()
                    .forEach(CraftTweakerAPI::logDump);
            send(new StringTextComponent(color("Loot table list generated! Check the crafttweaker.log file!", TextFormatting.GREEN)), player);
            return 0;
        });
        
        registerCustomCommand(Commands.literal("give")
                .then(Commands.argument("item", CTItemArgument.INSTANCE)
                        .executes(context -> {
                            ExpandPlayerEntity.give(context.getSource()
                                    .asPlayer(), context.getArgument("item", IItemStack.class));
                            return 0;
                        })), "give", "Gives the player an item using the Bracket handler syntax. You can also apply tags by appending a .withTag() call.");
        
        
        // Send an event to let others know that we are ready for SubCommands to be registered.
        // SubCommands registered earlier would throw a NPE (because the command itself would not exist yet)
        // SubCommands registered later would not be added to the Command system
        MinecraftForge.EVENT_BUS.post(new CTCommandCollectionEvent());
        
        // We'll add the dump subcommands after the event to find conflicts.
        final CommandImpl dump = COMMANDS.get("dump");
        for(BracketDumperInfo dumperInfo : CraftTweakerRegistry.getBracketDumpers().values()) {
            final String subCommandName = dumperInfo.getSubCommandName();
            
            // This means that a mod used .registerDump on a name for which a @BracketDumper exists as well
            // Let's log a warning because then the .registerDump will win
            if(dump.getSubCommands().containsKey(subCommandName)) {
                CraftTweakerAPI.logWarning("Found both an explicit Dumping command and a BracketDumper annotation for the name ' %s '. This is a (non-fatal) mod issue!", subCommandName);
            } else {
                registerDump(subCommandName, dumperInfo.getDescription(), dumperInfo);
            }
        }
        
        
        registerCustomCommand(Commands.literal("help")
                .executes(context -> executeHelp(context, 1))
                .then(Commands.argument("page", IntegerArgumentType.integer(1, (COMMANDS.size() / commandsPerPage) + 1))
                        .executes(context -> executeHelp(context, context.getArgument("page", int.class)))));
        
        COMMANDS.forEach((s, command) -> registerCommandInternal(root, command));
        COMMANDS.forEach((s, command) -> registerCommandInternal(rootAlternative, command));
        
        
        LiteralCommandNode<CommandSource> rootNode = dispatcher.register(root);
        LiteralCommandNode<CommandSource> rootAltNode = dispatcher.register(rootAlternative);
        
        /*
         * For anyone about to make a PR adding /minetweaker or /mt aliases, keep in mind:
         * for all intents and purposes, CraftTweaker is no longer MineTweaker, things have changed, scripts are not 1:1 with previous versions.
         * Not adding these aliases is a simple way to say "Don't expect previous things to work".
         * Saying that, feel free to try and convince me to add the aliases, if you can give a good argument for them, I may add them back.
         */
    }
    
    public static void registerDump(String name, String desc, CommandCaller caller) {
        
        registerCommand("dump", new CommandImpl(name, desc, caller));
    }
    
    public static void registerCommand(CommandImpl command) {
        
        COMMANDS.put(command.getName(), command);
    }
    
    public static void registerCommand(String command, CommandImpl subCommand) {
        
        COMMANDS.get(command).getSubCommands().put(subCommand.getName(), subCommand);
    }
    
    public static void registerCommand(CommandImpl command, CommandImpl subCommand) {
        
        command.getSubCommands().put(subCommand.getName(), subCommand);
    }
    
    private static void registerCommandInternal(LiteralArgumentBuilder<CommandSource> root, CommandImpl command) {
        
        LiteralArgumentBuilder<CommandSource> literalCommand = Commands.literal(command.getName());
        final Map<String, CommandImpl> subCommands = command.getSubCommands();
        if(!subCommands.isEmpty()) {
            subCommands.forEach((name, subCommand) -> registerCommandInternal(literalCommand, subCommand));
        }
        root.then(literalCommand.executes(command.getCaller()::executeCommand));
    }
    
    
    //helpPageNumber is 1 based, so we reduce it by 1 in clamping
    private static int executeHelp(CommandContext<CommandSource> context, int helpPageNumber) {
        
        final CommandSource source = context.getSource();
        final List<String> keys = new ArrayList<>(COMMANDS.keySet());
        
        final int highestPageIndex = (keys.size() / commandsPerPage);
        
        //The page we are on (0 based)
        //helpPageNumber is 1 based, so we reduce it by 1 in clamping
        final int shownPageIndex = MathHelper.clamp(helpPageNumber - 1, 0, highestPageIndex);
        
        //The range of commands we show on this page, end exclusive
        final int startCommandIndex = shownPageIndex * commandsPerPage;
        final int endCommandIndex = Math.min(startCommandIndex + commandsPerPage, keys.size());
        
        //Actually show the commands
        for(int i = startCommandIndex; i < endCommandIndex; i++) {
            final CommandImpl command = COMMANDS.get(keys.get(i));
            
            final FormattedTextComponent message = new FormattedTextComponent("/ct %s", command.getName());
            source.sendFeedback(run(message, message.getUnformattedComponentText()), true);
            source.sendFeedback(new FormattedTextComponent("- %s", color(command.getDescription(), TextFormatting.DARK_AQUA)), true);
        }
        
        //Which page are we on?
        //We show it 1 based again, so we add 1 to the pages
        source.sendFeedback(new FormattedTextComponent("Page %s of %d", shownPageIndex + 1, highestPageIndex + 1), true);
        return 0;
    }
    
    private static void registerCustomCommand(LiteralArgumentBuilder<CommandSource> literal) {
        
        root.then(literal);
        rootAlternative.then(literal);
    }
    
    private static void registerCustomCommand(LiteralArgumentBuilder<CommandSource> literal, String name, String description) {
        
        registerCustomCommand(literal);
        COMMANDS.put(name, new CommandImpl(name, description, (context -> 0)));
    }
    
    private static void send(ITextComponent component, CommandSource source) {
        
        source.sendFeedback(component, true);
        CraftTweakerAPI.logDump(component.getString());
    }
    
    static void send(ITextComponent component, PlayerEntity player) {
        
        player.sendMessage(component, CraftTweaker.CRAFTTWEAKER_UUID);
        CraftTweakerAPI.logDump(component.getUnformattedComponentText());
    }
    
    static String color(String str, TextFormatting formatting) {
        
        return formatting + str + TextFormatting.RESET;
    }
    
    private static String stripNewLine(String string) {
        
        return string.substring(0, string.lastIndexOf("\n"));
    }
    
    private static String stripNewLine(StringBuilder string) {
        
        return string.substring(0, string.lastIndexOf("\n"));
    }
    
    public static ITextComponent copy(TextComponent base, String toCopy) {
        
        Style style = base.getStyle();
        style = style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new FormattedTextComponent("Click to copy [%s]", color(toCopy, TextFormatting.GOLD))));
        style = style.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ct copy " + StringUtils.quoteAndEscape(toCopy) + ""));
        return base.setStyle(style);
    }
    
    public static ITextComponent open(TextComponent base, String path) {
        
        Style style = base.getStyle();
        style = style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new FormattedTextComponent("Click to open [%s]", color(path, TextFormatting.GOLD))));
        style = style.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, path));
        return base.setStyle(style);
    }
    
    public static TextComponent run(TextComponent base, String command) {
        
        Style style = Style.EMPTY;
        style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new FormattedTextComponent("Click to run [%s]", color(command, TextFormatting.GOLD))));
        style.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        base.setStyle(style);
        
        return base;
    }
    
    public interface CommandCaller {
        
        int executeCommand(CommandContext<CommandSource> context) throws CommandSyntaxException;
        
    }
    
    
    public interface CommandCallerPlayer extends CommandCaller {
        
        default int executeCommand(CommandContext<CommandSource> context) throws CommandSyntaxException {
            
            return executeCommand(context.getSource().asPlayer(), context.getSource().asPlayer().getHeldItemMainhand());
        }
        
        int executeCommand(PlayerEntity player, ItemStack stack);
        
    }
    
    public static class CommandImpl implements Comparable<CommandImpl> {
        
        private final String name;
        private final String description;
        private final CommandCaller caller;
        private final Map<String, CommandImpl> subCommands;
        
        public CommandImpl(String name, String description, CommandCaller caller) {
            
            this.name = name;
            this.description = description;
            this.caller = caller;
            this.subCommands = new TreeMap<>();
        }
        
        public String getName() {
            
            return name;
        }
        
        public CommandCaller getCaller() {
            
            return caller;
        }
        
        
        public String getDescription() {
            
            return description;
        }
        
        public void registerSubCommand(CommandImpl subCommand) {
            
            this.subCommands.put(subCommand.getName(), subCommand);
        }
        
        public Map<String, CommandImpl> getSubCommands() {
            
            return subCommands;
        }
        
        @Override
        public int compareTo(CommandImpl o) {
            
            return getName().compareTo(o.getName());
        }
        
    }
    
    private static enum CTItemArgument implements ArgumentType<IItemStack> {
        INSTANCE;
        private static final Collection<String> EXAMPLES = Lists.newArrayList("<item:minecraft:apple>", "<item:minecraft:iron_ingot>.withTag({display: {Name: \"wow\" as string}})");
        private static final DynamicCommandExceptionType MALFORMED_DATA = new DynamicCommandExceptionType(o -> new LiteralMessage(((ParseException) o).message));
        private static final SimpleCommandExceptionType INVALID_STRING = new SimpleCommandExceptionType(new LiteralMessage("invalid string"));
        private static final Pattern ITEM_PATTERN = Pattern.compile("<item:(\\w+:\\w+)>(.withTag\\((\\{.*})\\))?");
        
        @Override
        public IItemStack parse(StringReader reader) throws CommandSyntaxException {
            
            Matcher matcher = ITEM_PATTERN.matcher(reader.getRemaining());
            if(!matcher.find()) {
                throw INVALID_STRING.createWithContext(reader);
            }
            String itemLocation = matcher.group(1);
            try {
                IItemStack stack = getItem(itemLocation, matcher.group(3));
                reader.setCursor(reader.getCursor() + matcher.group(0).length());
                return stack;
            } catch(ParseException e) {
                reader.setCursor(reader.getCursor() + itemLocation.length() + "<item:>.withTag(".length() + e.position.getFromLineOffset());
                throw MALFORMED_DATA.createWithContext(reader, e);
            }
        }
        
        @Override
        public Collection<String> getExamples() {
            
            return EXAMPLES;
        }
        
        private static IItemStack getItem(String location, String tag) throws ParseException {
            
            IItemStack stack = BracketHandlers.getItem(location).mutable();
            if(tag != null) {
                stack.withTag(StringConverter.convert(tag));
            }
            return stack;
        }
        
    }
    
}
