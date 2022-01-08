package com.blamejared.crafttweaker.impl.command.type;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerRegistry;
import com.blamejared.crafttweaker.api.loot.LootManager;
import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.api.tag.registry.CrTTagRegistry;
import com.blamejared.crafttweaker.api.villager.CTVillagerTrades;
import com.blamejared.crafttweaker.impl.command.CTCommands;
import com.blamejared.crafttweaker.impl.command.CommandUtilities;
import com.blamejared.crafttweaker.impl.command.boilerplate.CommandImpl;
import com.blamejared.crafttweaker.mixin.common.access.recipe.AccessRecipeManager;
import com.blamejared.crafttweaker.natives.villager.ExpandVillagerProfession;
import com.blamejared.crafttweaker.platform.Services;
import com.mojang.brigadier.Command;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.npc.VillagerTrades;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;


public final class DumpCommands {
    
    private DumpCommands() {}
    
    public static void registerCommands() {
        
        CTCommands.registerCommand(new CommandImpl("dump_brackets", new TranslatableComponent("crafttweaker.command.description.dump.brackets"), builder -> {
            
            builder.executes(context -> {
                final File folder = new File("ct_dumps");
                if(!folder.exists() && !folder.mkdir()) {
                    CraftTweakerAPI.LOGGER.error("Could not create output folder '{}'", folder);
                }
                
                CraftTweakerRegistry.getBracketDumpers().forEach((name, dumpSupplier) -> {
                    final String dumpedFileName = dumpSupplier.getDumpedFileName() + ".txt";
                    try(final PrintWriter writer = new PrintWriter(new FileWriter(new File(folder, dumpedFileName), false))) {
                        dumpSupplier.getDumpedValuesStream().sorted().forEach(writer::println);
                    } catch(IOException e) {
                        CraftTweakerAPI.LOGGER.error("Error writing to file '{}'", dumpedFileName, e);
                    }
                });
                
                CommandUtilities.send(CommandUtilities.openingFile(new TranslatableComponent("crafttweaker.command.files.created").withStyle(ChatFormatting.GREEN), "ct_dumps"), context.getSource());
                return Command.SINGLE_SUCCESS;
            });
        }));
        
        CTCommands.registerCommand(new CommandImpl("dump", new TranslatableComponent("crafttweaker.command.description.dump"), builder -> {
            
            builder.executes(context -> {
                ServerPlayer player = context.getSource().getPlayerOrException();
                CommandUtilities.send(new TranslatableComponent("crafttweaker.command.dump.types").append(": "), player);
                CTCommands.getCommands()
                        .get("dump")
                        .getSubCommands()
                        .keySet()
                        .stream()
                        .map(it -> CommandUtilities.run(new TextComponent("- ").append(new TextComponent(it).withStyle(ChatFormatting.GREEN)), "/ct dump " + it))
                        .forEach(it -> CommandUtilities.send(it, player));
                return Command.SINGLE_SUCCESS;
            });
        }));
    }
    
    public static void registerDumpers() {
        
        CTCommands.registerDump(new CommandImpl("recipes", new TranslatableComponent("crafttweaker.command.description.dump.recipes"), builder -> {
            
            builder.executes(context -> {
                
                ServerPlayer player = context.getSource().getPlayerOrException();
                Services.REGISTRY.recipeTypes().stream()
                        .peek(type -> CraftTweakerAPI.LOGGER.info(type.toString()))
                        .map(it -> ((AccessRecipeManager) player.level.getRecipeManager()).getRecipes()
                                .getOrDefault(it, Collections.emptyMap())
                                .keySet())
                        .flatMap(Collection::stream)
                        .map(ResourceLocation::toString)
                        .forEach(CraftTweakerAPI.LOGGER::info);
                
                CommandUtilities.send(CommandUtilities.openingLogFile(new TranslatableComponent("crafttweaker.command.list.check.log", CommandUtilities.makeNoticeable(new TranslatableComponent("crafttweaker.command.misc.recipes")), CommandUtilities.getFormattedLogFile()).withStyle(ChatFormatting.GREEN)), player);
                
                return Command.SINGLE_SUCCESS;
            });
        }));
    
        CTCommands.registerDump(new CommandImpl("loot_modifiers", new TranslatableComponent("crafttweaker.command.description.dump.loot_modifiers"), builder -> {
        
            builder.executes(context -> {
            
                final ServerPlayer player = context.getSource().getPlayerOrException();
                LootManager.INSTANCE.getModifierManager().getAllNames().forEach(CraftTweakerAPI.LOGGER::info);
            
                CommandUtilities.send(CommandUtilities.openingLogFile(new TranslatableComponent("crafttweaker.command.list.check.log", CommandUtilities.makeNoticeable(new TranslatableComponent("crafttweaker.command.misc.loot_modifiers")), CommandUtilities.getFormattedLogFile()).withStyle(ChatFormatting.GREEN)), player);
            
                return Command.SINGLE_SUCCESS;
            });
        }));
        
        //TODO Needs more looking into
        //        CTCommands.registerPlayerDump("loot_tables", "Outputs the names of all registered loot tables", (player, stack) -> {
        //            ServerLifecycleHooks.getCurrentServer()
        //                    .getLootTableManager()
        //                    .getLootTableKeys()
        //                    .stream()
        //                    .map(ResourceLocation::toString)
        //                    .sorted()
        //                    .forEach(CraftTweakerAPI::logDump);
        //            CommandUtilities.send(CommandUtilities.color("Loot table list generated! Check the crafttweaker.log file!", TextFormatting.GREEN), player);
        //            return 0;
        //        });
        
        //TODO Forge specific
        //        CTCommands.registerPlayerDump("fake_players", "Outputs the data for all currently available fake players", (player, stack) -> {
        //            try {
        //                final Field map = FakePlayerFactory.class.getDeclaredField("fakePlayers");
        //                map.setAccessible(true);
        //                final Field mc = FakePlayerFactory.class.getDeclaredField("MINECRAFT");
        //                mc.setAccessible(true);
        //
        //                //noinspection unchecked
        //                Stream.concat(((Map<GameProfile, ?>) map.get(null)).keySet()
        //                                .stream(), Stream.of((GameProfile) mc.get(null)))
        //                        .map(it -> it.getName() + " -> " + it.getId())
        //                        .forEach(CraftTweakerAPI::logDump);
        //
        //                CommandUtilities.send(CommandUtilities.color("Fake player data list generated! Check the crafttweaker.log.file!", TextFormatting.GREEN), player);
        //            } catch(final ReflectiveOperationException e) {
        //                CommandUtilities.send(CommandUtilities.color("An error occurred while generating the data list", TextFormatting.RED), player);
        //                CraftTweakerAPI.logThrowing("Error while generating fake player list", e);
        //            }
        //            return 0;
        //        });
        
        CTCommands.registerDump(new CommandImpl("villager_trades", new TranslatableComponent("crafttweaker.command.description.dump.villager.trades"), builder -> {
            
            builder.executes(context -> {
                ServerPlayer player = context.getSource().getPlayerOrException();
                VillagerTrades.TRADES.forEach((villagerProfession, levelToTrades) -> {
                    CraftTweakerAPI.LOGGER.info("Trades for: " + ExpandVillagerProfession.getCommandString(villagerProfession));
                    levelToTrades.keySet()
                            .stream()
                            .sorted()
                            .filter(level -> levelToTrades.getOrDefault(level, new VillagerTrades.ItemListing[0]).length > 0)
                            .peek(level -> CraftTweakerAPI.LOGGER.info("Level " + level + " trades"))
                            .map(level -> levelToTrades.getOrDefault(level, new VillagerTrades.ItemListing[0]))
                            .flatMap(Arrays::stream)
                            .forEach(iTrade -> {
                                String tradeStr = "Unable to display trade.";
                                if(CTVillagerTrades.TRADE_CONVERTER.containsKey(iTrade.getClass())) {
                                    tradeStr = CTVillagerTrades.TRADE_CONVERTER.get(iTrade.getClass())
                                            .apply(iTrade)
                                            .toString();
                                }
                                CraftTweakerAPI.LOGGER.info(iTrade.getClass().getSimpleName() + tradeStr);
                            });
                });
                
                CommandUtilities.send(CommandUtilities.openingLogFile(new TranslatableComponent("crafttweaker.command.list.check.log", CommandUtilities.makeNoticeable(new TranslatableComponent("crafttweaker.command.misc.villager.trades")), CommandUtilities.getFormattedLogFile()).withStyle(ChatFormatting.GREEN)), player);
                return Command.SINGLE_SUCCESS;
            });
        }));
        
        CTCommands.registerDump(new CommandImpl("wandering_trades", new TranslatableComponent("crafttweaker.command.description.dump.wandering.trades"), builder -> {
            builder.executes(context -> {
                ServerPlayer player = context.getSource().getPlayerOrException();
                CraftTweakerAPI.LOGGER.info("Wandering Trader Trades");
                VillagerTrades.WANDERING_TRADER_TRADES.keySet()
                        .stream()
                        .sorted()
                        .filter(level -> VillagerTrades.WANDERING_TRADER_TRADES.getOrDefault(level, new VillagerTrades.ItemListing[0]).length > 0)
                        .peek(level -> CraftTweakerAPI.LOGGER.info("Level " + level + " trades"))
                        .map(level -> VillagerTrades.WANDERING_TRADER_TRADES.getOrDefault(level, new VillagerTrades.ItemListing[0]))
                        .flatMap(Arrays::stream)
                        .forEach(iTrade -> {
                            String tradeStr = "Unable to display trade.";
                            if(CTVillagerTrades.TRADE_CONVERTER.containsKey(iTrade.getClass())) {
                                tradeStr = CTVillagerTrades.TRADE_CONVERTER.get(iTrade.getClass())
                                        .apply(iTrade)
                                        .toString();
                            }
                            CraftTweakerAPI.LOGGER.info(iTrade.getClass().getSimpleName() + tradeStr);
                        });
                
                CommandUtilities.send(CommandUtilities.openingLogFile(new TranslatableComponent("crafttweaker.command.list.check.log", CommandUtilities.makeNoticeable(new TranslatableComponent("crafttweaker.command.misc.wandering.trades")), CommandUtilities.getFormattedLogFile()).withStyle(ChatFormatting.GREEN)), player);
                return Command.SINGLE_SUCCESS;
            });
        }));
        
        CTCommands.registerDump(new CommandImpl("tag_contents", new TranslatableComponent("crafttweaker.command.description.dump.tag.contents"), builder -> {
            builder.executes(context -> {
                ServerPlayer player = context.getSource().getPlayerOrException();
                CraftTweakerAPI.LOGGER.info("All Tag Contents");
                CrTTagRegistry.INSTANCE.getAllManagers()
                        .stream()
                        .sorted(Comparator.comparing(ITagManager::getTagFolder))
                        .peek(manager -> CraftTweakerAPI.LOGGER.info("Contents of '{}' tags:", manager.getTagFolder()))
                        .flatMap(tagManager -> tagManager.getAllTags().stream())
                        .peek(mcTag -> CraftTweakerAPI.LOGGER.info(mcTag.getCommandString()))
                        .flatMap(mcTag -> mcTag.getElements().stream())
                        .forEach(o -> CraftTweakerAPI.LOGGER.info("\t- {}", Services.REGISTRY.maybeGetRegistryKey(o)
                                .map(ResourceLocation::toString)
                                .orElse(o.toString())));
                
                
                CommandUtilities.send(CommandUtilities.openingLogFile(new TranslatableComponent("crafttweaker.command.list.check.log", CommandUtilities.makeNoticeable(new TranslatableComponent("crafttweaker.command.misc.tag.contents")), CommandUtilities.getFormattedLogFile()).withStyle(ChatFormatting.GREEN)), player);
                return Command.SINGLE_SUCCESS;
            });
        }));
        
    }
    
}
