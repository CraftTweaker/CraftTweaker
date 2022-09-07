package com.blamejared.crafttweaker.impl.command.type;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.command.CommandUtilities;
import com.blamejared.crafttweaker.api.loot.LootManager;
import com.blamejared.crafttweaker.api.plugin.ICommandRegistrationHandler;
import com.blamejared.crafttweaker.api.tag.CraftTweakerTagRegistry;
import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.api.villager.CTVillagerTrades;
import com.blamejared.crafttweaker.impl.command.CtCommands;
import com.blamejared.crafttweaker.mixin.common.access.recipe.AccessRecipeManager;
import com.blamejared.crafttweaker.natives.villager.ExpandVillagerProfession;
import com.blamejared.crafttweaker.natives.world.biome.ExpandBiome;
import com.blamejared.crafttweaker.platform.Services;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.npc.VillagerTrades;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public final class DumpCommands {
    
    private DumpCommands() {}
    
    public static void registerCommands(final ICommandRegistrationHandler handler) {
        
        handler.registerRootCommand(
                "dump_brackets",
                new TranslatableComponent("crafttweaker.command.description.dump.brackets"),
                builder -> builder.executes(context -> {
                    doFullBracketsDump(context);
                    return Command.SINGLE_SUCCESS;
                })
        );
        
        handler.registerRootCommand(
                "dump",
                new TranslatableComponent("crafttweaker.command.description.dump"),
                builder -> builder.executes(context -> {
                    ServerPlayer player = context.getSource().getPlayerOrException();
                    CommandUtilities.send(new TranslatableComponent("crafttweaker.command.dump.types").append(": "), player);
                    CtCommands.get()
                            .commands()
                            .get("dump")
                            .subCommands()
                            .keySet()
                            .stream()
                            .map(it -> CommandUtilities.run(new TextComponent("- ").append(new TextComponent(it).withStyle(ChatFormatting.GREEN)), "/ct dump " + it))
                            .forEach(it -> CommandUtilities.send(it, player));
                    return Command.SINGLE_SUCCESS;
                })
        );
    }
    
    public static void registerDumpers(final ICommandRegistrationHandler handler) {
        
        registerBracketDumpers(handler);
        registerCustomDumpers(handler);
    }
    
    private static void registerBracketDumpers(final ICommandRegistrationHandler handler) {
        
        CraftTweakerAPI.getRegistry().getAllLoaders()
                .stream()
                .map(CraftTweakerAPI.getRegistry()::getBracketDumpers)
                .map(Map::values)
                .flatMap(Collection::stream)
                .distinct()
                .forEach(it -> {
                    final String subCommandName = it.subCommandName();
                    handler.registerDump(subCommandName, it.description(), builder -> builder.executes(it));
                });
    }
    
    private static void registerCustomDumpers(final ICommandRegistrationHandler handler) {
        
        handler.registerDump(
                "recipes",
                new TranslatableComponent("crafttweaker.command.description.dump.recipes"),
                builder -> builder.executes(context -> {
                    
                    final ServerPlayer player = context.getSource().getPlayerOrException();
                    Services.REGISTRY.recipeTypes().stream()
                            .peek(type -> CraftTweakerAPI.LOGGER.info(type.toString()))
                            .map(it -> ((AccessRecipeManager) player.level.getRecipeManager()).crafttweaker$getRecipes()
                                    .getOrDefault(it, Collections.emptyMap())
                                    .keySet())
                            .flatMap(Collection::stream)
                            .map(ResourceLocation::toString)
                            .forEach(CraftTweakerAPI.LOGGER::info);
                    
                    CommandUtilities.send(CommandUtilities.openingLogFile(new TranslatableComponent("crafttweaker.command.list.check.log", CommandUtilities.makeNoticeable(new TranslatableComponent("crafttweaker.command.misc.recipes")), CommandUtilities.getFormattedLogFile()).withStyle(ChatFormatting.GREEN)), player);
                    
                    return Command.SINGLE_SUCCESS;
                })
        );
        
        handler.registerDump(
                "loot_modifiers",
                new TranslatableComponent("crafttweaker.command.description.dump.loot_modifiers"),
                builder -> builder.executes(context -> {
                    
                    final ServerPlayer player = context.getSource().getPlayerOrException();
                    LootManager.INSTANCE.getModifierManager().getAllNames().forEach(CraftTweakerAPI.LOGGER::info);
                    
                    CommandUtilities.send(CommandUtilities.openingLogFile(new TranslatableComponent("crafttweaker.command.list.check.log", CommandUtilities.makeNoticeable(new TranslatableComponent("crafttweaker.command.misc.loot_modifiers")), CommandUtilities.getFormattedLogFile()).withStyle(ChatFormatting.GREEN)), player);
                    
                    return Command.SINGLE_SUCCESS;
                })
        );
        
        handler.registerDump(
                "loot_tables",
                new TranslatableComponent("crafttweaker.command.description.dump.loot_tables"),
                builder -> builder.executes(context -> {
                    
                    final ServerPlayer player = context.getSource().getPlayerOrException();
                    final MinecraftServer server = context.getSource().getServer();
                    server.getLootTables()
                            .getIds()
                            .stream()
                            .map(ResourceLocation::toString)
                            .sorted()
                            .forEach(CraftTweakerAPI.LOGGER::info);
                    
                    CommandUtilities.send(CommandUtilities.openingLogFile(new TranslatableComponent("crafttweaker.command.list.check.log", CommandUtilities.makeNoticeable(new TranslatableComponent("crafttweaker.command.misc.loot_tables")), CommandUtilities.getFormattedLogFile()).withStyle(ChatFormatting.GREEN)), player);
                    
                    return Command.SINGLE_SUCCESS;
                })
        );
        
        handler.registerDump(
                "biomes",
                new TranslatableComponent("crafttweaker.command.description.dump.biomes"),
                builder -> builder.executes(context -> {
                    
                    final ServerPlayer player = context.getSource().getPlayerOrException();
                    final MinecraftServer server = context.getSource().getServer();
                    server.registryAccess().registry(Registry.BIOME_REGISTRY).ifPresent(biomes -> {
                        biomes.stream()
                                .map(ExpandBiome::getCommandString)
                                .sorted()
                                .forEach(CraftTweakerAPI.LOGGER::info);
                    });
                    
                    CommandUtilities.send(CommandUtilities.openingLogFile(new TranslatableComponent("crafttweaker.command.list.check.log", CommandUtilities.makeNoticeable(new TranslatableComponent("crafttweaker.command.misc.biomes")), CommandUtilities.getFormattedLogFile()).withStyle(ChatFormatting.GREEN)), player);
                    
                    return Command.SINGLE_SUCCESS;
                })
        );
        
        
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
        
        handler.registerDump(
                "villager_trades",
                new TranslatableComponent("crafttweaker.command.description.dump.villager.trades"),
                builder -> builder.executes(context -> {
                    final ServerPlayer player = context.getSource().getPlayerOrException();
                    VillagerTrades.TRADES.forEach((villagerProfession, levelToTrades) -> {
                        CraftTweakerAPI.LOGGER.info("Trades for: " + ExpandVillagerProfession.getCommandString(villagerProfession));
                        levelToTrades.keySet()
                                .intStream()
                                .sorted()
                                .filter(level -> levelToTrades.getOrDefault(level, new VillagerTrades.ItemListing[0]).length > 0)
                                .peek(level -> CraftTweakerAPI.LOGGER.info("Level " + level + " trades"))
                                .mapToObj(level -> levelToTrades.getOrDefault(level, new VillagerTrades.ItemListing[0]))
                                .flatMap(Arrays::stream)
                                .forEach(iTrade -> {
                                    String tradeStr = " - Unable to display trade.";
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
                })
        );
        
        handler.registerDump(
                "wandering_trades",
                new TranslatableComponent("crafttweaker.command.description.dump.wandering.trades"),
                builder -> builder.executes(context -> {
                    final ServerPlayer player = context.getSource().getPlayerOrException();
                    CraftTweakerAPI.LOGGER.info("Wandering Trader Trades");
                    VillagerTrades.WANDERING_TRADER_TRADES.keySet()
                            .intStream()
                            .sorted()
                            .filter(level -> VillagerTrades.WANDERING_TRADER_TRADES.getOrDefault(level, new VillagerTrades.ItemListing[0]).length > 0)
                            .peek(level -> CraftTweakerAPI.LOGGER.info("Level " + level + " trades"))
                            .mapToObj(level -> VillagerTrades.WANDERING_TRADER_TRADES.getOrDefault(level, new VillagerTrades.ItemListing[0]))
                            .flatMap(Arrays::stream)
                            .forEach(iTrade -> {
                                String tradeStr = " - Unable to display trade.";
                                if(CTVillagerTrades.TRADE_CONVERTER.containsKey(iTrade.getClass())) {
                                    tradeStr = CTVillagerTrades.TRADE_CONVERTER.get(iTrade.getClass())
                                            .apply(iTrade)
                                            .toString();
                                }
                                CraftTweakerAPI.LOGGER.info(iTrade.getClass().getSimpleName() + tradeStr);
                            });
                    
                    CommandUtilities.send(CommandUtilities.openingLogFile(new TranslatableComponent("crafttweaker.command.list.check.log", CommandUtilities.makeNoticeable(new TranslatableComponent("crafttweaker.command.misc.wandering.trades")), CommandUtilities.getFormattedLogFile()).withStyle(ChatFormatting.GREEN)), player);
                    return Command.SINGLE_SUCCESS;
                })
        );
        
        handler.registerDump(
                "tag_contents",
                new TranslatableComponent("crafttweaker.command.description.dump.tag.contents"),
                builder -> builder.executes(context -> {
                    final ServerPlayer player = context.getSource().getPlayerOrException();
                    
                    CraftTweakerAPI.LOGGER.info("All Tag Contents");
                    CraftTweakerTagRegistry.INSTANCE.managers()
                            .stream()
                            .sorted(Comparator.comparing(ITagManager::tagFolder))
                            .peek(it -> CraftTweakerAPI.LOGGER.info("Contents of '{}' tags:", it.tagFolder()))
                            .flatMap(it -> it.tags().stream())
                            .peek(it -> CraftTweakerAPI.LOGGER.info(it.getCommandString()))
                            .flatMap(it -> it.idElements()
                                    .stream()
                                    .map(o -> getTagAsString(player, it, o)))
                            .forEach(it -> {
                                CraftTweakerAPI.LOGGER.info("\t- {}", it);
                            });
                    
                    CommandUtilities.send(CommandUtilities.openingLogFile(new TranslatableComponent("crafttweaker.command.list.check.log", CommandUtilities.makeNoticeable(new TranslatableComponent("crafttweaker.command.misc.tag.contents")), CommandUtilities.getFormattedLogFile()).withStyle(ChatFormatting.GREEN)), player);
                    return Command.SINGLE_SUCCESS;
                })
        );
    }
    
    private static String getTagAsString(ServerPlayer player, MCTag tag, Object o) {
        
        if(o instanceof ResourceLocation) {
            return o.toString();
        } else {
            Optional<? extends Registry<Object>> foundRegistry = player.server.registryAccess()
                    .registry(tag.manager().resourceKey());
            if(foundRegistry.isPresent()) {
                return foundRegistry
                        .map(objects -> objects.getKey(o))
                        .map(ResourceLocation::toString)
                        .orElse(o.toString());
            }
            
        }
        
        return o.toString();
    }
    
    
    private static void doFullBracketsDump(final CommandContext<CommandSourceStack> context) {
        
        final Path directory = Services.PLATFORM.getPathFromGameDirectory("./ct_dumps");
        try {
            Files.createDirectories(directory);
        } catch(final IOException e) {
            CraftTweakerAPI.LOGGER.error("Could not create output folder '{}'", directory);
            return;
        }
        CraftTweakerAPI.getRegistry().getAllLoaders()
                .stream()
                .map(CraftTweakerAPI.getRegistry()::getBracketDumpers)
                .map(Map::values)
                .flatMap(Collection::stream)
                .distinct()
                .forEach(it -> {
                    final String dumpedFileName = it.dumpedFileName() + ".txt";
                    final Iterable<String> iterable = () -> it.values().sorted().iterator();
                    try {
                        Files.write(directory.resolve(dumpedFileName), iterable);
                    } catch(final IOException e) {
                        CraftTweakerAPI.LOGGER.error("Error writing to file '" + dumpedFileName + "'", e);
                    }
                });
        CommandUtilities.send(CommandUtilities.openingFile(new TranslatableComponent("crafttweaker.command.files.created").withStyle(ChatFormatting.GREEN), "ct_dumps"), context.getSource());
    }
    
}
