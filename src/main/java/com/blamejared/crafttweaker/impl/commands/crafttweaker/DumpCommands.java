package com.blamejared.crafttweaker.impl.commands.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerRegistry;
import com.blamejared.crafttweaker.api.text.FormattedTextComponent;
import com.blamejared.crafttweaker.impl.commands.CTCommands;
import com.blamejared.crafttweaker.impl.commands.CommandImpl;
import com.blamejared.crafttweaker.impl.commands.CommandUtilities;
import com.blamejared.crafttweaker.impl.loot.CTLootManager;
import com.blamejared.crafttweaker.impl.misc.CTVillagerTrades;
import com.blamejared.crafttweaker.impl.tag.manager.TagManager;
import com.blamejared.crafttweaker.impl.tag.registry.CrTTagRegistry;
import com.blamejared.crafttweaker.impl_native.villager.ExpandVillagerProfession;
import com.mojang.authlib.GameProfile;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.blamejared.crafttweaker.impl.commands.CommandUtilities.color;

public final class DumpCommands {
    
    private DumpCommands() {}
    
    public static void registerDumpCommands(final Supplier<Map<String, CommandImpl>> commands) {
        
        CTCommands.registerCommand(CTCommands.command("dump_brackets", "Dumps available Bracket Expressions into the /ct_dumps folder", source -> {
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
            
            CommandUtilities.send(new StringTextComponent("Files Created"), source.getSource());
            
            return 0;
        }));
        
        CTCommands.registerCommand(CTCommands.playerCommand("dump", "Dumps available sub commands for the dump command", (player, stack) -> {
            CommandUtilities.send("Dump types: ", player);
            commands.get()
                    .get("dump")
                    .getChildCommands()
                    .keySet()
                    .stream()
                    .map(it -> CommandUtilities.run(new FormattedTextComponent("- " + color(it, TextFormatting.GREEN)), "/ct dump " + it))
                    .forEach(it -> CommandUtilities.send(it, player));
            return 0;
        }));
    }
    
    public static void registerDumpers() {
        
        CTCommands.registerPlayerDump("recipes", "Outputs the names of all registered recipes", (player, stack) -> {
            
            Registry.RECIPE_TYPE.stream()
                    .peek(type -> CraftTweakerAPI.logDump(type.toString()))
                    .map(it -> player.world.getRecipeManager().recipes.getOrDefault(it, Collections.emptyMap())
                            .keySet())
                    .flatMap(Collection::stream)
                    .map(ResourceLocation::toString)
                    .forEach(CraftTweakerAPI::logDump);
            
            CommandUtilities.send(CommandUtilities.color("Recipe list generated! Check the crafttweaker.log file!", TextFormatting.GREEN), player);
            return 0;
        });
        
        
        CTCommands.registerPlayerDump("loot_modifiers", "Outputs the names of all registered loot modifiers", (player, stack) -> {
            CTLootManager.LOOT_MANAGER.getModifierManager().getAllNames().forEach(CraftTweakerAPI::logDump);
            CommandUtilities.send(CommandUtilities.color("Loot modifiers list generated! Check the crafttweaker.log file!", TextFormatting.GREEN), player);
            return 0;
        });
        
        CTCommands.registerPlayerDump("loot_tables", "Outputs the names of all registered loot tables", (player, stack) -> {
            ServerLifecycleHooks.getCurrentServer()
                    .getLootTableManager()
                    .getLootTableKeys()
                    .stream()
                    .map(ResourceLocation::toString)
                    .sorted()
                    .forEach(CraftTweakerAPI::logDump);
            CommandUtilities.send(CommandUtilities.color("Loot table list generated! Check the crafttweaker.log file!", TextFormatting.GREEN), player);
            return 0;
        });
        
        CTCommands.registerPlayerDump("fake_players", "Outputs the data for all currently available fake players", (player, stack) -> {
            try {
                final Field map = FakePlayerFactory.class.getDeclaredField("fakePlayers");
                map.setAccessible(true);
                final Field mc = FakePlayerFactory.class.getDeclaredField("MINECRAFT");
                mc.setAccessible(true);
                
                //noinspection unchecked
                Stream.concat(((Map<GameProfile, ?>) map.get(null)).keySet()
                                .stream(), Stream.of((GameProfile) mc.get(null)))
                        .map(it -> it.getName() + " -> " + it.getId())
                        .forEach(CraftTweakerAPI::logDump);
                
                CommandUtilities.send(CommandUtilities.color("Fake player data list generated! Check the crafttweaker.log.file!", TextFormatting.GREEN), player);
            } catch(final ReflectiveOperationException e) {
                CommandUtilities.send(CommandUtilities.color("An error occurred while generating the data list", TextFormatting.RED), player);
                CraftTweakerAPI.logThrowing("Error while generating fake player list", e);
            }
            return 0;
        });
        
        CTCommands.registerPlayerDump("villager_trades", "Outputs information on all Villager Trades", (player, stack) -> {
            
            VillagerTrades.VILLAGER_DEFAULT_TRADES.forEach((villagerProfession, levelToTrades) -> {
                CraftTweakerAPI.logDump("\nTrades for: " + ExpandVillagerProfession.getCommandString(villagerProfession));
                levelToTrades.keySet()
                        .stream()
                        .sorted()
                        .filter(level -> levelToTrades.getOrDefault(level, new VillagerTrades.ITrade[0]).length > 0)
                        .peek(level -> CraftTweakerAPI.logDump("Level " + level + " trades"))
                        .map(level -> levelToTrades.getOrDefault(level, new VillagerTrades.ITrade[0]))
                        .flatMap(Arrays::stream)
                        .forEach(iTrade -> {
                            String tradeStr = "Unable to display trade.";
                            if(CTVillagerTrades.TRADE_CONVERTER.containsKey(iTrade.getClass())) {
                                tradeStr = CTVillagerTrades.TRADE_CONVERTER.get(iTrade.getClass())
                                        .apply(iTrade)
                                        .toString();
                            }
                            CraftTweakerAPI.logDump(iTrade.getClass().getSimpleName() + tradeStr);
                        });
            });
            
            CommandUtilities.send(CommandUtilities.color("Trade list generated! Check the crafttweaker.log file!", TextFormatting.GREEN), player);
            return 0;
        });
        
        CTCommands.registerPlayerDump("wandering_trades", "Outputs information on all Wandering Trader Trades", (player, stack) -> {
            
            CraftTweakerAPI.logDump("\nWandering Trader Trades");
            VillagerTrades.field_221240_b.keySet()
                    .stream()
                    .sorted()
                    .filter(level -> VillagerTrades.field_221240_b.getOrDefault(level, new VillagerTrades.ITrade[0]).length > 0)
                    .peek(level -> CraftTweakerAPI.logDump("Level " + level + " trades"))
                    .map(level -> VillagerTrades.field_221240_b.getOrDefault(level, new VillagerTrades.ITrade[0]))
                    .flatMap(Arrays::stream)
                    .forEach(iTrade -> {
                        String tradeStr = "Unable to display trade.";
                        if(CTVillagerTrades.TRADE_CONVERTER.containsKey(iTrade.getClass())) {
                            tradeStr = CTVillagerTrades.TRADE_CONVERTER.get(iTrade.getClass())
                                    .apply(iTrade)
                                    .toString();
                        }
                        CraftTweakerAPI.logDump(iTrade.getClass().getSimpleName() + tradeStr);
                    });
            
            CommandUtilities.send(CommandUtilities.color("Wandering Trade list generated! Check the crafttweaker.log file!", TextFormatting.GREEN), player);
            return 0;
        });
        
        CTCommands.registerPlayerDump("tag_contents", "Outputs the contents of all tags", (player, stack) -> {
            
            CraftTweakerAPI.logDump("\nAll Tag Contents");
            CrTTagRegistry.instance.getAllManagers()
                    .stream()
                    .sorted(Comparator.comparing(TagManager::getTagFolder))
                    .peek(manager -> CraftTweakerAPI.logDump("Contents of `" + manager.getTagFolder() + "` tags:"))
                    .flatMap(tagManager -> tagManager.getAllTags().stream())
                    .peek(mcTag -> CraftTweakerAPI.logDump(mcTag.getCommandString()))
                    .flatMap(mcTag -> mcTag.getElements().stream())
                    .forEach(o -> {
                        if(o instanceof IForgeRegistryEntry) {
                            CraftTweakerAPI.logDump("\t- " + ((IForgeRegistryEntry<?>) o).getRegistryName().toString());
                        } else {
                            CraftTweakerAPI.logDump("\t- " + o);
                        }
                    });
            
            
            CommandUtilities.send(CommandUtilities.color("Tag Contents list generated! Check the crafttweaker.log file!", TextFormatting.GREEN), player);
            return 0;
        });
        
        CTCommands.registerDump("enchantment_types", "Outputs all EnchantmentType values", ctx -> {
            
            CraftTweakerAPI.logDump("\nAll EnchantmentType Values");
            
            for (EnchantmentType type : EnchantmentType.values()) {
                
                CraftTweakerAPI.logDump("\t- " + type.name());
            }
            
            return 0;
        });
    }
    
}
