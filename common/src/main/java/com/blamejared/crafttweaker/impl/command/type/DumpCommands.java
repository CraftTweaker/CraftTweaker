package com.blamejared.crafttweaker.impl.command.type;

import com.blamejared.crafttweaker.CraftTweakerCommon;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.command.CommandUtilities;
import com.blamejared.crafttweaker.api.loot.LootManager;
import com.blamejared.crafttweaker.api.plugin.ICommandRegistrationHandler;
import com.blamejared.crafttweaker.api.tag.CraftTweakerTagRegistry;
import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.api.util.PathUtil;
import com.blamejared.crafttweaker.api.villager.CTVillagerTrades;
import com.blamejared.crafttweaker.impl.command.CtCommands;
import com.blamejared.crafttweaker.mixin.common.access.level.damage.AccessDamageSources;
import com.blamejared.crafttweaker.mixin.common.access.recipe.AccessRecipeManager;
import com.blamejared.crafttweaker.natives.villager.ExpandVillagerProfession;
import com.blamejared.crafttweaker.natives.world.biome.ExpandBiome;
import com.blamejared.crafttweaker.platform.Services;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public final class DumpCommands {
    
    private DumpCommands() {}
    
    public static void registerCommands(final ICommandRegistrationHandler handler) {
        
        handler.registerRootCommand(
                "dump_brackets",
                Component.translatable("crafttweaker.command.description.dump.brackets"),
                builder -> builder.executes(context -> {
                    doFullBracketsDump(context);
                    return Command.SINGLE_SUCCESS;
                })
        );
        
        handler.registerRootCommand(
                "dump",
                Component.translatable("crafttweaker.command.description.dump"),
                builder -> builder.executes(context -> {
                    CommandSourceStack source = context.getSource();
                    CommandUtilities.send(source, Component.translatable("crafttweaker.command.dump.types")
                            .append(": "));
                    CtCommands.get()
                            .commands()
                            .get("dump")
                            .subCommands()
                            .keySet()
                            .stream()
                            .map(it -> CommandUtilities.run(Component.literal("- ")
                                    .append(Component.literal(it).withStyle(ChatFormatting.GREEN)), "/ct dump " + it))
                            .forEach(it -> CommandUtilities.send(source, it));
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
                Component.translatable("crafttweaker.command.description.dump.recipes"),
                builder -> builder.executes(context -> {
                    CommandSourceStack source = context.getSource();
                    ServerLevel level = source.getLevel();
                    AccessRecipeManager recipeManager = (AccessRecipeManager) level.getRecipeManager();
                    recipeManager.crafttweaker$getByType().keySet().stream()
                            .peek(type -> CommandUtilities.COMMAND_LOGGER.info(type.toString()))
                            .map(it -> recipeManager.crafttweaker$getByType().get(it))
                            .flatMap(Collection::stream)
                            .map(RecipeHolder::id)
                            .map(ResourceLocation::toString)
                            .forEach(CommandUtilities.COMMAND_LOGGER::info);
                    
                    CommandUtilities.openLogFile(context.getSource(), Component.translatable("crafttweaker.command.list.check.log", CommandUtilities.makeNoticeable(Component.translatable("crafttweaker.command.misc.recipes")), CommandUtilities.getFormattedLogFile())
                            .withStyle(ChatFormatting.GREEN));
                    
                    return Command.SINGLE_SUCCESS;
                })
        );
        
        handler.registerDump(
                "loot_modifiers",
                Component.translatable("crafttweaker.command.description.dump.loot_modifiers"),
                builder -> builder.executes(context -> {
                    
                    LootManager.INSTANCE.getModifierManager()
                            .getAllNames()
                            .forEach(CommandUtilities.COMMAND_LOGGER::info);
                    
                    CommandUtilities.openLogFile(context.getSource(), Component.translatable("crafttweaker.command.list.check.log", CommandUtilities.makeNoticeable(Component.translatable("crafttweaker.command.misc.loot_modifiers")), CommandUtilities.getFormattedLogFile())
                            .withStyle(ChatFormatting.GREEN));
                    
                    return Command.SINGLE_SUCCESS;
                })
        );
        
        handler.registerDump(
                "loot_tables",
                Component.translatable("crafttweaker.command.description.dump.loot_tables"),
                builder -> builder.executes(context -> {
                    
                    CommandSourceStack source = context.getSource();
                    CraftTweakerAPI.getAccessibleElementsProvider()
                            .registryAccess()
                            .lookupOrThrow(Registries.LOOT_TABLE)
                            .listElementIds()
                            .map(ResourceKey::location)
                            .map(ResourceLocation::toString)
                            .sorted()
                            .forEach(CommandUtilities.COMMAND_LOGGER::info);
                    
                    CommandUtilities.openLogFile(source, Component.translatable("crafttweaker.command.list.check.log", CommandUtilities.makeNoticeable(Component.translatable("crafttweaker.command.misc.loot_tables")), CommandUtilities.getFormattedLogFile())
                            .withStyle(ChatFormatting.GREEN));
                    
                    return Command.SINGLE_SUCCESS;
                })
        );
        
        handler.registerDump(
                "biomes",
                Component.translatable("crafttweaker.command.description.dump.biomes"),
                builder -> builder.executes(context -> {
                    
                    CommandSourceStack source = context.getSource();
                    final MinecraftServer server = source.getServer();
                    server.registryAccess().registry(Registries.BIOME).ifPresent(biomes -> {
                        biomes.stream()
                                .map(ExpandBiome::getCommandString)
                                .sorted()
                                .forEach(CommandUtilities.COMMAND_LOGGER::info);
                    });
                    
                    CommandUtilities.openLogFile(source, Component.translatable("crafttweaker.command.list.check.log", CommandUtilities.makeNoticeable(Component.translatable("crafttweaker.command.misc.biomes")), CommandUtilities.getFormattedLogFile())
                            .withStyle(ChatFormatting.GREEN));
                    
                    return Command.SINGLE_SUCCESS;
                })
        );
        
        handler.registerDump(
                "villager_trades",
                Component.translatable("crafttweaker.command.description.dump.villager.trades"),
                builder -> builder.executes(context -> {
                    CommandSourceStack source = context.getSource();
                    VillagerTrades.TRADES.forEach((villagerProfession, levelToTrades) -> {
                        CommandUtilities.COMMAND_LOGGER.info("Trades for: " + ExpandVillagerProfession.getCommandString(villagerProfession));
                        levelToTrades.keySet()
                                .intStream()
                                .sorted()
                                .filter(level -> levelToTrades.getOrDefault(level, new VillagerTrades.ItemListing[0]).length > 0)
                                .peek(level -> CommandUtilities.COMMAND_LOGGER.info("Level " + level + " trades"))
                                .mapToObj(level -> levelToTrades.getOrDefault(level, new VillagerTrades.ItemListing[0]))
                                .flatMap(Arrays::stream)
                                .forEach(iTrade -> {
                                    String tradeStr = " - Unable to display trade.";
                                    if(CTVillagerTrades.TRADE_CONVERTER.containsKey(iTrade.getClass())) {
                                        tradeStr = CTVillagerTrades.TRADE_CONVERTER.get(iTrade.getClass())
                                                .apply(iTrade)
                                                .toString();
                                    }
                                    CommandUtilities.COMMAND_LOGGER.info(iTrade.getClass().getSimpleName() + tradeStr);
                                });
                    });
                    CommandUtilities.openLogFile(source, Component.translatable("crafttweaker.command.list.check.log", CommandUtilities.makeNoticeable(Component.translatable("crafttweaker.command.misc.villager.trades")), CommandUtilities.getFormattedLogFile())
                            .withStyle(ChatFormatting.GREEN));
                    return Command.SINGLE_SUCCESS;
                })
        );
        
        handler.registerDump(
                "wandering_trades",
                Component.translatable("crafttweaker.command.description.dump.wandering.trades"),
                builder -> builder.executes(context -> {
                    CommandSourceStack source = context.getSource();
                    CommandUtilities.COMMAND_LOGGER.info("Wandering Trader Trades");
                    VillagerTrades.WANDERING_TRADER_TRADES.keySet()
                            .intStream()
                            .sorted()
                            .filter(level -> VillagerTrades.WANDERING_TRADER_TRADES.getOrDefault(level, new VillagerTrades.ItemListing[0]).length > 0)
                            .peek(level -> CommandUtilities.COMMAND_LOGGER.info("Level " + level + " trades"))
                            .mapToObj(level -> VillagerTrades.WANDERING_TRADER_TRADES.getOrDefault(level, new VillagerTrades.ItemListing[0]))
                            .flatMap(Arrays::stream)
                            .forEach(iTrade -> {
                                String tradeStr = " - Unable to display trade.";
                                if(CTVillagerTrades.TRADE_CONVERTER.containsKey(iTrade.getClass())) {
                                    tradeStr = CTVillagerTrades.TRADE_CONVERTER.get(iTrade.getClass())
                                            .apply(iTrade)
                                            .toString();
                                }
                                CommandUtilities.COMMAND_LOGGER.info(iTrade.getClass().getSimpleName() + tradeStr);
                            });
                    
                    CommandUtilities.openLogFile(source, Component.translatable("crafttweaker.command.list.check.log", CommandUtilities.makeNoticeable(Component.translatable("crafttweaker.command.misc.wandering.trades")), CommandUtilities.getFormattedLogFile())
                            .withStyle(ChatFormatting.GREEN));
                    return Command.SINGLE_SUCCESS;
                })
        );
        
        handler.registerDump(
                "tag_contents",
                Component.translatable("crafttweaker.command.description.dump.tag.contents"),
                builder -> builder.executes(context -> {
                    CommandSourceStack source = context.getSource();
                    
                    CommandUtilities.COMMAND_LOGGER.info("All Tag Contents");
                    RegistryAccess registryAccess = context.getSource().registryAccess();
                    registryAccess.registries()
                            .sorted(Comparator.comparing(o -> o.key().location()))
                            .forEach(entry -> {
                                Registry<?> registry = entry.value();
                                String tagFolder = CraftTweakerTagRegistry.INSTANCE.makeTagFolder(registry.key());
                                CommandUtilities.COMMAND_LOGGER.info("Contents of '{}' tags:", tagFolder);
                                registry.getTags()
                                        .sorted(Comparator.comparing((Pair<? extends TagKey<?>, ? extends HolderSet.Named<?>> o) -> o.getFirst()
                                                .location()))
                                        .forEach(pair -> {
                                            CommandUtilities.COMMAND_LOGGER.info("<tag:" + tagFolder + ":" + pair.getFirst()
                                                    .location() + ">");
                                            
                                            Function<Holder<?>, ResourceLocation> keyExtractor = holder -> holder.unwrap()
                                                    .map(ResourceKey::location, o -> registry.getKey(GenericUtil.uncheck(o)));
                                            pair.getSecond()
                                                    .stream()
                                                    .map(keyExtractor)
                                                    .sorted(ResourceLocation::compareTo)
                                                    .forEach(resourceLocation -> {
                                                        CommandUtilities.COMMAND_LOGGER.info("\t- {}", resourceLocation);
                                                    });
                                        });
                            });
                    CommandUtilities.openLogFile(source, Component.translatable("crafttweaker.command.list.check.log", CommandUtilities.makeNoticeable(Component.translatable("crafttweaker.command.misc.tag.contents")), CommandUtilities.getFormattedLogFile())
                            .withStyle(ChatFormatting.GREEN));
                    return Command.SINGLE_SUCCESS;
                })
        );
        
        handler.registerDump(
                "damage_sources",
                Component.translatable("crafttweaker.command.description.dump.damage_sources"),
                builder -> builder.executes(context -> {
                    
                    CommandSourceStack source = context.getSource();
                    //TODO what format should this print in??
                    ((AccessDamageSources) source.getLevel().damageSources()).crafttweaker$getDamageTypes()
                            .keySet()
                            .stream()
                            .sorted()
                            .forEach(CommandUtilities.COMMAND_LOGGER::info);
                    
                    CommandUtilities.openLogFile(source, Component.translatable("crafttweaker.command.list.check.log", CommandUtilities.makeNoticeable(Component.translatable("crafttweaker.command.misc.biomes")), CommandUtilities.getFormattedLogFile())
                            .withStyle(ChatFormatting.GREEN));
                    
                    return Command.SINGLE_SUCCESS;
                })
        );
        
        handler.registerDump("fake_players", Component.translatable("crafttweaker.command.description.dump.fake_players"), builder -> {
            builder.executes(context -> {
                
                Services.PLATFORM.fakePlayers()
                        .map(it -> it.getName() + " -> " + it.getId())
                        .forEach(it -> CraftTweakerCommon.logger().info(it));
                
                CommandUtilities.openLogFile(context.getSource(), Component.translatable("crafttweaker.command.list.check.log", CommandUtilities.makeNoticeable(Component.translatable("crafttweaker.command.misc.fake_players")), CommandUtilities.getFormattedLogFile())
                        .withStyle(ChatFormatting.GREEN));
                
                return Command.SINGLE_SUCCESS;
            });
        });
    }
    
    private static String getTagAsString(CommandSourceStack source, MCTag tag, Object o) {
        
        if(o instanceof ResourceLocation) {
            return o.toString();
        } else {
            Optional<? extends Registry<Object>> foundRegistry = source.registryAccess()
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
        
        final Path directory = PathUtil.findFromGameDirectory("ct_dumps");
        try {
            Files.createDirectories(directory);
        } catch(final IOException e) {
            CommandUtilities.COMMAND_LOGGER.error("Could not create output folder '{}'", directory);
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
                        CommandUtilities.COMMAND_LOGGER.error("Error writing to file '" + dumpedFileName + "'", e);
                    }
                });
        CommandUtilities.open(context.getSource(), "ct_dumps");
    }
    
}
