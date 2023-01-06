package com.blamejared.crafttweaker.impl.command.type.script.example;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.command.CommandUtilities;
import com.blamejared.crafttweaker.api.plugin.ICommandRegistrationHandler;
import com.blamejared.crafttweaker.mixin.common.access.server.AccessMinecraftServer;
import com.mojang.brigadier.Command;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.ResourceManager;
import org.apache.commons.io.input.ReaderInputStream;
import org.openzen.zencode.shared.SourceFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ExamplesCommand {
    
    private ExamplesCommand() {}
    
    public static void registerCommand(final ICommandRegistrationHandler handler) {
        
        handler.registerRootCommand(
                "examples",
                Component.translatable("crafttweaker.command.description.examples"),
                builder -> builder.requires((source) -> source.hasPermission(2)) // TODO("Permission API")
                        .executes(ctx -> execute(ctx.getSource().getPlayerOrException()))
        );
    }
    
    private static int execute(final ServerPlayer player) {
        
        final MinecraftServer server = player.server;
        @SuppressWarnings("resource") final MinecraftServer.ReloadableResources reloadableResources = ((AccessMinecraftServer) server).crafttweaker$getResources();
        final ResourceManager resourceManager = reloadableResources.resourceManager();
        
        //Collect all scripts that are in the scripts data pack folder and write them to the example scripts folder
        final int examplesAmount = resourceManager.listResources("scripts", n -> n.getPath().endsWith(".zs"))
                .entrySet()
                .stream()
                .map(ResourceManagerSourceFile::new)
                .mapToInt(ExamplesCommand::writeScriptFile)
                .sum();
        
        player.sendSystemMessage(CommandUtilities.openingFile(Component.translatable("crafttweaker.command.example.generated")
                .withStyle(ChatFormatting.GREEN), getExamplesDir().toString()));
        return examplesAmount;
    }
    
    private static int writeScriptFile(final SourceFile sourceFile) {
        
        final Path file = getExamplesDir().resolve(sourceFile.getFilename());
        if(Files.exists(file)) {
            CommandUtilities.COMMAND_LOGGER.info("Skip writing example file '{}' since it already exists", file);
            return 0;
        }
        
        if(!Files.exists(file.getParent())) {
            try {
                Files.createDirectories(file.getParent());
            } catch(final IOException e) {
                CommandUtilities.COMMAND_LOGGER.error("Could not create folder '" + file.getParent() + "'", e);
                return 0;
            }
        }
        
        try(final Reader reader = sourceFile.open(); final InputStream stream = new ReaderInputStream(reader, StandardCharsets.UTF_8)) {
            Files.copy(stream, file);
        } catch(final IOException e) {
            CommandUtilities.COMMAND_LOGGER.warn("Could not write script example: ", e);
        }
        
        return Command.SINGLE_SUCCESS;
    }
    
    private static Path getExamplesDir() {
        
        return CraftTweakerAPI.getScriptsDirectory().resolve("./examples");
    }
    
}
