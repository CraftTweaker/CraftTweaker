package com.blamejared.crafttweaker.impl.commands.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerRegistry;
import com.blamejared.crafttweaker.api.text.FormattedTextComponent;
import com.blamejared.crafttweaker.impl.commands.CTCommands;
import com.blamejared.crafttweaker.impl.commands.CommandImpl;
import com.blamejared.crafttweaker.impl.commands.CommandUtilities;
import com.blamejared.crafttweaker.impl.loot.CTLootManager;
import com.mojang.authlib.GameProfile;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
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
                    .map(it -> player.world.getRecipeManager().recipes.getOrDefault(it, Collections.emptyMap()).keySet())
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
                Stream.concat(((Map<GameProfile, ?>) map.get(null)).keySet().stream(), Stream.of((GameProfile) mc.get(null)))
                        .map(it -> it.getName() + " -> "  + it.getId())
                        .forEach(CraftTweakerAPI::logDump);
                
                CommandUtilities.send(CommandUtilities.color("Fake player data list generated! Check the crafttweaker.log.file!", TextFormatting.GREEN), player);
            } catch (final ReflectiveOperationException e) {
                CommandUtilities.send(CommandUtilities.color("An error occurred while generating the data list", TextFormatting.RED), player);
                CraftTweakerAPI.logThrowing("Error while generating fake player list", e);
            }
            return 0;
        });
    }
}
